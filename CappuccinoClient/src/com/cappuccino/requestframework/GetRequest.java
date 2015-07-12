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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.cappuccino.log.Log;
import com.cappuccino.requestframework.type.RequestResult;

// 중요 abstract 클래스 상속 관계 설명
//
// AsyncExtendedTask		: ProgressDialog 등 다른 작업에 의해 Wrapped up 된 작업을 위해 
//		↑
// Request					: 각 endpoint 공통 기능 포함
//		↑
// PostRequest / GetRequest	: EndpointTask 에서 특화된 기능 구현 포함
public class GetRequest extends Request {

	private GetRequestHelper gHelper;

	GetRequest(GetRequestHelper gHelper,
			PostExecuteListener<RequestResult> postExecuteListener) {
		super(gHelper, postExecuteListener);
		this.gHelper = gHelper;
	}

	@Override
	protected HttpResponse makeRequest(DefaultHttpClient client,
			boolean cookieNeedLoaded) {

		if (client == null) {
			throw new IllegalArgumentException();
		}

		if (RequestConfig.debuggable() == true) {
			Log.e();
			Log.e("<---------- " + gHelper.getRequestName()
					+ " 요청 시작 -----------");
			Log.e(gHelper);
		}

		// 쿠키 불러오기
		if (cookieNeedLoaded == true) {
			CookieManager.loadCookie(client);
		}

		// 요청 생성
		HttpGet request = new HttpGet(gHelper.getEndpointWithParams());

		// 요청 실행 후 응답 반환
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// in case of an http protocol error
			throw new RuntimeException(e);
		} catch (IOException e) {
			// in case of a problem or the connection was aborted
			throw new RuntimeException(e);
		}

		return response;
	}

}
