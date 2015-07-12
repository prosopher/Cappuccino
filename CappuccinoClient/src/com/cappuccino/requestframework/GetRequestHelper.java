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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.cappuccino.requestframework.type.RequestParam;

public class GetRequestHelper extends RequestHelper {

	GetRequestHelper(String requestName, IMetaManager metaManager) {
		super(requestName, metaManager);
	}

	public String getEndpointWithParams() {

		// 요청주소 생성
		String addr = getEndpoint() + "?";

		// 매개변수 추가
		for (RequestParam param : params) {
			if (param.getValue().length() > 0) {
				// 매개변수 값이 있을 경우 변환 작업 시작
				try {
					String encodedPair = param.getSSVariableKey() + "="
							+ URLEncoder.encode(param.getValue(), "UTF-8");
					addr += encodedPair + "&";
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}
		}

		// 마지막 물음표나 앰퍼샌드 제거
		addr = addr.substring(0, addr.length() - 1);

		return addr;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.toString());
		sb.append(getEndpointWithParams() + "\n");

		return sb.toString();
	}

}
