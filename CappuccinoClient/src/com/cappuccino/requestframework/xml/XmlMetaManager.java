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
package com.cappuccino.requestframework.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.content.res.AssetManager;

import com.cappuccino.requestframework.IMetaManager;

public class XmlMetaManager implements IMetaManager {

	private CommonMap commonMap;
	private Map<String, Request> requestMap;
	private String xmlAssetFileName;

	public XmlMetaManager(Context context, String xmlAssetFileName) {

		// simple framework 를 이용한
		// XML deserialization

		// 이 xml 파일은 리소스로 auto generated R 파일에 생성될 필요가 없으므로
		// res/xml 이 아니라 res/assets 디렉토리에 저장해 둔다.
		AssetManager am = context.getResources().getAssets();
		InputStream is = null;
		Requests requests;
		try {
			is = am.open(xmlAssetFileName);

			Serializer s = new Persister();
			requests = s.read(Requests.class, is);
		} catch (IOException e) {
			throw new RuntimeException(e + " : open 도중 에러");
		} catch (Exception e) {
			throw new RuntimeException(e + " : read 도중 에러");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// CommonMap 추출 (Optional 이므로 에러 검사 안함)
		commonMap = requests.getCommonMap();

		// RequestMap 추출 (Optional 이므로 에러 검사 안함)
		requestMap = requests.getRequestMap();
		this.xmlAssetFileName = xmlAssetFileName;
	}

	@Override
	public String getCommonSs(String csKey) {

		// 초기화 검사
		if (commonMap == null) {
			throw new RuntimeException("commonMap 초기화 안됨");
		}

		return getFromVariableMap(commonMap, csKey);
	}

	@Override
	public String getType(String requestName) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// type 추출 (Optional 이므로 에러 검사 안함)
		return a.getType();
	}

	@Override
	public String getEndpoint(String requestName) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// endpoint 추출 (Optional 이므로 에러 검사 안함)
		return a.getEndpoint();
	}

	@Override
	public boolean doesCookieNeedLoaded(String requestName) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// cookieNeedLoaded 추출 (Optional 이므로 에러 검사 안함)
		return a.getCookieNeedLoaded();
	}

	@Override
	public boolean doesCookieNeedSaved(String requestName) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// cookieNeedSaved 추출 (Optional 이므로 에러 검사 안함)
		return a.getCookieNeedSaved();
	}

	@Override
	public boolean isErrorKeyAlwaysReturned(String requestName) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// errorKeyAlwaysReturned 추출 (Optional 이므로 에러 검사 안함)
		return a.getErrorKeyAlwaysReturned();
	}

	@Override
	public String getSSErrorCheckKey(String requestName) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// ssErrorCheckKey 추출 (Optional 이므로 에러 검사 안함)
		return a.getSSErrorCheckKey();
	}

	@Override
	public String getSSErrorHitValue(String requestName) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// ssErrorHitValue 추출 (Optional 이므로 에러 검사 안함)
		return a.getSSErrorHitValue();
	}

	@Override
	public String getRequestSs(String requestName, String csKey) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// request 추출 (Optional 이므로 에러 검사 안함)
		RequestMap requestMap = a.getRequestMap();

		return getFromVariableMap(requestMap, csKey);
	}

	@Override
	public String getResultSs(String requestName, String csKey) {

		// 초기화 검사
		if (requestMap == null) {
			throw new RuntimeException("requestMap 초기화 안됨");
		}

		// requestName 에 대응하는 값 추출
		Request a = requestMap.get(requestName);
		if (a == null) {
			throw new RuntimeException("requestName ( " + requestName + " ) 에 대응하는 값 이 없습니다.");
		}

		// result 추출 (Optional 이므로 에러 검사 안함)
		ResultMap result = a.getResultMap();

		return getFromVariableMap(result, csKey);
	}

	private String getFromVariableMap(VariableMap vm, String csKey) {

		// 초기화 검사
		if (vm == null || csKey == null) {
			throw new RuntimeException("초기화 안됨");
		}

		// VariableMap 추출
		Map<String, String> variableMap = vm.getVariableMap();
		if (variableMap == null) {
			throw new RuntimeException("VariableMap 이 없습니다.");
		}

		// csKey 에 대응하는 ssKey 추출
		String ssKey = variableMap.get(csKey);
		if (ssKey == null) {
			throw new RuntimeException("csKey ( " + csKey + " ) 에 대응하는 값이 없습니다.");
		}

		return ssKey;
	}

	public String getXmlAssetFileName() {
		return xmlAssetFileName;
	}

}
