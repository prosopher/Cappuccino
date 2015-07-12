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

import java.util.List;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.cappuccino.log.Log;

// final : subclassing 금지
// final 대신 abstract 를 쓰면
// instantiation은 막을 수 있지만 subclassing 은 막을 수 없다.
public final class CookieManager {

	// private constructor : instantiation 금지
	private CookieManager() {
	}

	// android 의 CookieManager 를 사용해야 하는가?
	// CookieManager 를 사용하면 WebView 와 연동가능하지만 지금은 필요없을 듯
	// 필요하다면 Rev:225 => Util / LoginRequest / InitActivity 에서 CookieManager 부분을
	// 찾는다.
	//
	// 내장 CookieManager 를 사용하면 안 좋은 점
	// 각 키/값 쌍을 csv 형태로 저장해야돼서, parsing 이 번거롭다.
	// 따라서 차라리 SharedPreferences 형태를 이용한다.

	private static CookieApplication application;
	private static boolean initialized = false;

	public static void initialize(CookieApplication application) {

		// 초기화 시작
		CookieManager.application = application;

		// 초기화 완료
		CookieManager.initialized = true;
	}

	public static void loadCookie(DefaultHttpClient client) {

		// 초기화 검사
		if (CookieManager.initialized == false) {
			throw new RuntimeException("초기화 안됨");
		}
		if (client == null) {
			throw new IllegalArgumentException();
		}

		// 쿠키가 있을 때만 설정
		if (application.hasCookie() == false) {
			// 저장된 쿠키가 없을 경우 그냥 종료
			return;
		} else {
			// 저장된 쿠키가 있을 경우
			String cookieName = application.getCookieName();
			String cookieValue = application.getCookieValue();
			String cookieDomain = application.getCookieDomain();

			if (RequestConfig.debuggable() == true) {
				Log.e();
				Log.e("불러온 쿠키 목록");
				Log.e("쿠키 이름 : " + cookieName);
				Log.e("쿠키 값 : " + cookieValue);
				Log.e("쿠키 도메인 : " + cookieDomain);
			}

			// 기본 쿠키 정보 입력
			BasicClientCookie cookie = new BasicClientCookie(cookieName,
					cookieValue);
			cookie.setDomain(cookieDomain);

			// 쿠키 추가
			client.getCookieStore().addCookie(cookie);
		}
	}

	public static void saveCookie(DefaultHttpClient client) {

		// 초기화 검사
		if (CookieManager.initialized == false) {
			throw new RuntimeException("초기화 안됨");
		}
		if (client == null) {
			throw new IllegalArgumentException();
		}

		// 쿠키 추출 후 매니저에 등록
		List<Cookie> cookieList = client.getCookieStore().getCookies();
		if (!cookieList.isEmpty()) {
			// 쿠키가 있을 경우
			for (Cookie cookie : cookieList) {

				// 쿠키를 sp 에 저장
				String cookieName = cookie.getName();
				String cookieValue = cookie.getValue();
				String cookieDomain = cookie.getDomain();
				// Date cookieExpiryDate = cookie.getExpiryDate();

				application.setCookieName(cookieName);
				application.setCookieValue(cookieValue);
				application.setCookieDomain(cookieDomain);
				// application.setCookieExpiryDate(cookieExpiryDate);

				if (RequestConfig.debuggable() == true) {
					Log.e();
					Log.e("저장된 쿠키 목록");
					Log.e("쿠키 이름 : " + cookieName);
					Log.e("쿠키 값 : " + cookieValue);
					Log.e("쿠키 도메인 : " + cookieDomain);
				}
			}
		}
	}

}
