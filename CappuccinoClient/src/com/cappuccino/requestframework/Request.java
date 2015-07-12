/*
 *  Copyright 2013-2014 Jin-woo Lee
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cappuccino.requestframework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cappuccino.log.Log;
import com.cappuccino.requestframework.type.RequestResult;
import com.cappuccino.task.AsyncExtendedTask;

// 중요 abstract 클래스 상속 관계 설명
//
// AsyncExtendedTask		: ProgressDialog 등 다른 작업에 의해 Wrapped up 된 작업을 위해 
//		↑
// Request					: 각 endpoint 공통 기능 포함
//		↑
// PostRequest / GetRequest	: EndpointTask 에서 특화된 기능 구현 포함
public abstract class Request extends AsyncExtendedTask<Object, RequestResult> {

	// 생성자 부분
	// /////////////////////////////////////////////////////////////////////////////////////////

	private RequestHelper requestHelper;

	protected Request(RequestHelper requestHelper,
			PostExecuteListener<RequestResult> postExecuteListener) {
		addPostExecuteListener(postExecuteListener);
		this.requestHelper = requestHelper;
	}

	public static Request make(RequestHelper requestHelper,
			PostExecuteListener<RequestResult> postExecuteListener) {
		String type = requestHelper.getType();
		if (type.equals("GET")) {
			return new GetRequest((GetRequestHelper) requestHelper,
					postExecuteListener);
		} else if (type.equals("POST")) {
			return new PostRequest((PostRequestHelper) requestHelper,
					postExecuteListener);
		} else {
			throw new IllegalStateException("알 수 없는 Request Type : " + type);
		}
	}

	// AsyncExtendedTask 프레임워크 인터페이스 부분
	// /////////////////////////////////////////////////////////////////////////////////////////

	private RequestResult result;

	@Override
	public RequestResult doInBackground(Object... params) {

		// 요청
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = makeRequest(client,
				requestHelper.doesCookieNeedLoaded());

		// response 의 유무를 검사한다.
		if (response == null) {
			// 인터넷이 안 될 경우 response 가 null 일 수도 있다.

			// 반환할 결과 생성 ( 408 : Request Timeout )
			result = new RequestResult(requestHelper, 408);
		} else {
			// 인터넷이 사용 가능해서 response 가 null 이 아닌 경우

			// 연결에 문제가 있었는지 검사한다.
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				// 연결 에러가 있을 경우

				// 반환할 결과 생성
				result = new RequestResult(requestHelper, statusCode);
			} else {
				// 연결 에러가 없을 경우

				// 응답 추출
				String rawResult = handleResponse(response);

				// 반환할 결과 생성
				result = new RequestResult(requestHelper, rawResult);

				if (requestHelper.doesCookieNeedSaved() == true) {
					// 서버 에러가 없으면 쿠키 저장
					if (!result.hasError()) {
						CookieManager.saveCookie(client);
					}
				}
			}
		}

		if (RequestConfig.debuggable() == true) {
			Log.e();
			Log.e("----------- " + requestHelper.getRequestName() + " 요청 결과"
					+ " ----------");
			Log.e(result);

			Log.e();
			Log.e("--------------------------------------->");
		}

		// 혹시 waitForCompletion 메소드 내부에서 기다리고 있는 스레드 깨워주기
		synchronized (this) {
			notify();
		}

		return result;
	}

	// Request 고유 기능 부분
	// /////////////////////////////////////////////////////////////////////////////////////////

	protected abstract HttpResponse makeRequest(DefaultHttpClient client,
			boolean cookieNeedLoaded);

	protected String handleResponse(HttpResponse response) {

		if (response == null) {
			throw new IllegalArgumentException();
		}

		String rawResult = "";
		InputStream is = null;
		try {
			is = response.getEntity().getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			// 마지막 개행문자를 제거한다.
			if (sb.length() > 0) {
				rawResult = sb.substring(0, sb.length() - 1);
			}
		} catch (IllegalStateException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return rawResult;
	}

	// synchronized 에 관한 부분
	// http://warmz.tistory.com/370
	// http://stackoverflow.com/questions/5916370/how-to-use-notify-and-wait
	public RequestResult waitForCompletion() {

		// 외부에서 결과값을 추출하고 싶을 때 사용하는 메소드

		try {
			synchronized (this) {
				wait();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

}
