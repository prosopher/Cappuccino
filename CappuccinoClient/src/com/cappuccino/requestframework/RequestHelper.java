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

import java.io.Serializable;
import java.util.ArrayList;

import com.cappuccino.requestframework.type.RequestParam;
import com.cappuccino.util.EncodeUtil;

public class RequestHelper {

	protected IMetaManager metaManager;
	private String requestName;

	protected RequestHelper(String requestName, IMetaManager metaManager) {
		this.metaManager = metaManager;
		this.requestName = requestName;
	}

	public static RequestHelper make(String requestName) {
		// MetaManager 연결
		IMetaManager metaManager = RequestConfig.getMetaManager();

		if (metaManager == null) {
			throw new IllegalArgumentException("MetaManager 가 초기화되지 않았습니다.");
		}

		String type = metaManager.getType(requestName);
		if (type.equals("GET")) {
			return new GetRequestHelper(requestName, metaManager);
		} else if (type.equals("POST")) {
			return new PostRequestHelper(requestName, metaManager);
		} else {
			throw new IllegalStateException("알 수 없는 Request Type : " + type);
		}
	}

	// 나머지 메소드
	// /////////////////////////////////////////////////////////////////////////////////////////

	protected ArrayList<RequestParam> params = new ArrayList<RequestParam>();

	public void add(String csKey, String value) {
		String ssKey = metaManager.getRequestSs(requestName, csKey);
		RequestParam param = new RequestParam(csKey, ssKey, value);
		params.add(param);
	}

	public void addEncoded(String csKey, Serializable value) {
		String ssKey = metaManager.getRequestSs(requestName, csKey);
		RequestParam param = new RequestParam(csKey, ssKey, EncodeUtil.encodeToUrlBase64(value));
		params.add(param);
	}

	public IMetaManager getMetaManager() {
		return metaManager;
	}

	public String getRequestName() {
		return requestName;
	}

	public String getType() {
		return metaManager.getType(requestName);
	}

	public String getEndpoint() {
		return metaManager.getEndpoint(requestName);
	}

	public boolean doesCookieNeedLoaded() {
		return metaManager.doesCookieNeedLoaded(requestName);
	}

	public boolean doesCookieNeedSaved() {
		return metaManager.doesCookieNeedSaved(requestName);
	}

	public boolean isErrorKeyAlwaysReturned() {
		return metaManager.isErrorKeyAlwaysReturned(requestName);
	}

	public String getSSErrorCheckKey() {
		return metaManager.getSSErrorCheckKey(requestName);
	}

	public String getSSErrorHitValue() {
		return metaManager.getSSErrorHitValue(requestName);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("요청 매개변수\n");
		for (RequestParam param : params) {
			sb.append(param + "\n");
		}

		sb.append(" \n");
		sb.append("요청 공통 매개변수\n");
		sb.append("요청 타입 ( type ) : " + getType() + "\n");
		sb.append("쿠키 불러오기? ( cookieNeedLoaded ) : " + doesCookieNeedLoaded() + "\n");
		sb.append("쿠키 저장하기? ( cookieNeedSaved ) : " + doesCookieNeedSaved() + "\n");
		sb.append("에러 키 항상 반환? ( errorKeyAlwaysReturned ) : " + isErrorKeyAlwaysReturned() + "\n");
		sb.append("서버 에러 검사 키 ( ssErrorCheckKey ) : " + getSSErrorCheckKey() + "\n");
		sb.append("서버 에러 검출 값 ( ssErrorHitValue ) : " + getSSErrorHitValue() + "\n");

		sb.append(" \n");
		sb.append("요청 주소" + "\n");

		return sb.toString();
	}

}
