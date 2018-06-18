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
package com.cappuccino.requestframework.type;

import org.json.JSONException;
import org.json.JSONObject;

import com.cappuccino.requestframework.RequestHelper;
import com.cappuccino.util.EncodeUtil;
import com.cappuccino.requestframework.IMetaManager;

// JSON Object 인 경우 쉽게 결과를 분석할 수 있다.
// 아닐 경우 직접 getRawResult 를 이용해서 파싱해야 한다.
public class RequestResult {

	public static final int NA = 0;
	public static final int NO_ERROR = 1;
	public static final int SERVER_ERROR = 2;
	public static final int CONNECTION_ERROR = 3;

	private RequestHelper requestHelper;
	private int statusCode;
	private JSONObject parsedJSONObj;

	private String rawResult;
	private int errorType;
	private String errorMsg;
	private String naReason;

	public RequestResult(RequestHelper requestHelper, int errorStatusCode) {
		// 연결 에러가 있을 때 사용하는 생성자

		// 매개변수 검사
		if (requestHelper == null) {
			throw new RuntimeException("초기화 안됨");
		}

		// 초기화
		this.rawResult = "";
		this.requestHelper = requestHelper;
		this.statusCode = errorStatusCode;
		this.parsedJSONObj = null;

		this.errorType = CONNECTION_ERROR;
		this.errorMsg = "요청 실패 with error code " + errorStatusCode;
	}

	public RequestResult(RequestHelper requestHelper, String rawResult) {

		// 연결 에러는 없지만 서버 에러가 있을 수도 있을 때 사용하는 생성자

		// 매개변수 검사
		if (requestHelper == null || rawResult == null) {
			throw new RuntimeException("초기화 안됨");
		}

		// 초기화
		this.rawResult = rawResult;
		this.requestHelper = requestHelper;
		this.statusCode = 200;

		// JSON 형식 검사
		if (!rawResult.startsWith("{")) {
			// JSON Object 가 아닌 경우

			// 에러를 검출할 키를 활용할 수 없으므로 에러를 알 수 없다고 판단하고 종료
			this.parsedJSONObj = null;
			this.errorType = NA;
			this.errorMsg = "";
			this.naReason = "JSON Object 가 아님";

			return;
		}

		// JSON Object 인 경우
		// 결과를 JSON Object 로 변환
		String parsedJSONString;
		try {
			this.parsedJSONObj = new JSONObject(rawResult);
			parsedJSONString = this.parsedJSONObj.toString(4);
		} catch (JSONException e) {
			// 파싱할 수 없을 경우 예외 발생
			throw new RuntimeException(e + " : " + parsedJSONObj.toString());
		}

		// 에러 예측 상황을 구분하기 위한 값 추출
		boolean errorKeyAlwaysReturned = requestHelper.isErrorKeyAlwaysReturned();
		String ssErrorCheckKey = requestHelper.getSSErrorCheckKey();
		String ssErrorHitValue = requestHelper.getSSErrorHitValue();

		// 에러 예측 상황에 따라 반환할 결과 생성
		if (ssErrorCheckKey == null) {
			// 서버 에러 검사 키가 없을 때,
			// 비교할 검사 값을 찾을 키가 없으므로 에러 유무를 알 수 없다.
			this.errorType = NA;
			this.errorMsg = "";
			this.naReason = "에러 검사 키가 설정되지 않음";
		} else {
			// 서버 에러 검사 키가 있을 때

			// 에러 키 항상 반환 여부 검사
			if (errorKeyAlwaysReturned == false) {
				// 서버 에러 검사 키가 있고, 에러 키가 항상 반환되는 것은 아닐 때
				// => 서버 작업이 실패하면 에러 반환 / 성공하면 다른 메시지를 반환하는 경우 ( err_msg 키 / msg
				// 키 )
				// => 에러 검출 값의 유무에 상관없이 서버 에러 검사 키가 반환됐을 때 에러가 발생했다고 볼 수 있다.

				// 서버 에러 검사 키에 대응하는 값이 있는 지 검사한다.
				try {
					this.errorMsg = this.parsedJSONObj.getString(ssErrorCheckKey);

					// 서버 에러가 있을 경우
					this.errorType = SERVER_ERROR;
				} catch (JSONException e) {
					// 서버 에러가 없을 경우
					this.errorType = NO_ERROR;
					this.errorMsg = "";
				}
			} else {
				// 서버 에러 검사 키가 있고, 에러 키가 항상 반환될 때
				// => 서버 작업이 성공하든 실패하든 해당 성공/실패 메시지를 하나의 키로 반환하는 경우 ( code 키 -
				// 성공 : 1 / 실패 : 0 )
				// => 에러 검출 값을 이용해야만 서버에 에러가 발생했는 지 알 수 있다.

				if (ssErrorHitValue == null) {
					// 에러 키가 항상 반환되고, 서버 에러 검사 키가 있고, 서버 에러 검출 값은 없을 때

					// 비교할 검사 값을 찾을 키가 없으므로 에러 유무를 알 수 없다.
					this.errorType = NA;
					this.errorMsg = "";
					this.naReason = "에러 검사 값이 설정되지 않음";
				} else {
					// 에러 키가 항상 반환되고, 서버 에러 검사 키가 있고, 서버 에러 검출 값도 있을 때

					// 서버의 실제 반환 값과, 주어진 서버 에러 검출 값을 비교한다.
					String ssActualErrorCheckValue;
					try {
						ssActualErrorCheckValue = this.parsedJSONObj.getString(ssErrorCheckKey);
					} catch (JSONException e) {
						// 서버 에러 검사 키가 실제 반환 결과에 없는 경우
						// => 서버 에러로 간주하고 실제 반환 결과를 저장
						//
						// 또는 실제로 파싱 도중 에러가 난 경우
						// => 어쨌든 에러로 간주하고 실제 반환 결과를 저장
						this.errorType = SERVER_ERROR;
						this.errorMsg = parsedJSONString;

						return;
					}

					// 서버 에러 검사 키가 실제 반환 결과에 있을 경우
					// 계속 진행

					if (ssActualErrorCheckValue.equals(ssErrorHitValue)) {
						// 실제 반환 값이 에러 검출 값과 일치할 경우 => 서버 에러가 있다.
						this.errorType = SERVER_ERROR;
						this.errorMsg = parsedJSONString;
					} else {
						// 실제 반환 값이 에러 검출 값과 다를 경우 => 서버 에러가 없다.
						this.errorType = NO_ERROR;
						this.errorMsg = "";
					}
				}
			}
		}
	}

	public String getRequestName() {
		return requestHelper.getRequestName();
	}

	public int getStatusCode() {
		return statusCode;
	}

	public int getErrorType() {
		return errorType;
	}

	public boolean hasError() {
		return errorType == CONNECTION_ERROR || errorType == SERVER_ERROR;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getValue(String csKey) {
		// 값이 있는지 검사한다.
		String value = null;
		try {
			// 값이 있을 경우
			IMetaManager metaManager = requestHelper.getMetaManager();
			String ssKey = metaManager.getResultSs(requestHelper.getRequestName(), csKey);
			value = parsedJSONObj.getString(ssKey);
		} catch (JSONException e) {
			// 값이 없을 경우 기본값 null 사용
		}
		return value;
	}

	public String getRawResult() {
		return rawResult;
	}

	public Object getDecodedResult() {
		return EncodeUtil.decodeFromUrlBase64(rawResult);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");
		sb.append("상태 코드 : " + statusCode + "\n");
		if (errorType == NA) {
			sb.append("에러 타입 : 에러 유무 알 수 없음\n");
			sb.append("이유 : " + naReason + "\n");
			sb.append("결과 원본 : " + rawResult + "\n");
		} else if (errorType == NO_ERROR) {
			sb.append("에러 타입 : 에러 없음\n");
		} else if (errorType == SERVER_ERROR) {
			sb.append("에러 타입 : 서버 내부 에러\n");
			sb.append("에러 메시지 : " + errorMsg + "\n");
		} else if (errorType == CONNECTION_ERROR) {
			sb.append("에러 타입 : 연결 도중 에러\n");
			sb.append("에러 메시지 : " + errorMsg + "\n");
		}
		sb.append("\n");
		if (parsedJSONObj != null) {
			sb.append("파싱 결과\n");
			try {
				sb.append(parsedJSONObj.toString(4));
			} catch (JSONException e) {
				// 파싱할 수 없을 경우 예외 발생
				throw new RuntimeException(e + " : " + parsedJSONObj.toString());
			}
		}

		return sb.toString();
	}

}
