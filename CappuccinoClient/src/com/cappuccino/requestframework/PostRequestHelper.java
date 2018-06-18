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
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.cappuccino.requestframework.type.RequestParam;

public class PostRequestHelper extends RequestHelper {

	PostRequestHelper(String requestName, IMetaManager metaManager) {
		super(requestName, metaManager);
	}

	public HttpEntity getHttpEntity() {
		// 매개변수 쌍 생성
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		for (RequestParam param : params) {
			nameValuePairs.add(new BasicNameValuePair(param.getSsKey(), param.getValue()));
		}

		// HttpEtity 생성
		HttpEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return entity;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.toString());
		sb.append(getEndpoint() + "\n");

		return sb.toString();
	}

}
