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

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cappuccino.R;

public class CookieApplication extends Application {

	private SharedPreferences sp;

	@Override
	public void onCreate() {
		super.onCreate();
		sp = PreferenceManager.getDefaultSharedPreferences(this);
	}

	public boolean hasCookie() {
		String name = sp.getString(getString(R.string.COOKIE_KEY_NAME), "");

		// 빈 문자열이 아니면 쿠키가 있는 상태
		return !name.equals("");
	}

	public void setCookieName(String name) {
		sp.edit().putString(getString(R.string.COOKIE_KEY_NAME), name).commit();
	}

	public void setCookieValue(String value) {
		sp.edit().putString(getString(R.string.COOKIE_KEY_VALUE), value)
				.commit();
	}

	public void setCookieDomain(String domain) {
		sp.edit().putString(getString(R.string.COOKIE_KEY_DOMAIN), domain)
				.commit();
	}

	// public void setCookieExpiryDate(Date expiryDate) {
	// sp.edit().putString(getString(R.string.COOKIE_KEY_EXPIRY_DATE),
	// expiryDate.toString()).commit();
	// }

	public String getCookieName() {
		return sp.getString(getString(R.string.COOKIE_KEY_NAME), "");
	}

	public String getCookieValue() {
		return sp.getString(getString(R.string.COOKIE_KEY_VALUE), "");
	}

	public String getCookieDomain() {
		return sp.getString(getResources()
				.getString(R.string.COOKIE_KEY_DOMAIN), "");
	}

	// public Date getCookieExpiryDate() {
	// String cookieExpiryDateString =
	// sp.getString(getString(R.string.COOKIE_KEY_EXPIRY_DATE),
	// "");
	// Date cookieExpiryDate = new Date(cookieExpiryDateString);
	// return cookieExpiryDate;
	// }

	public void removeCookieName() {
		sp.edit().remove(getString(R.string.COOKIE_KEY_NAME)).commit();
	}

	public void removeCookieValue() {
		sp.edit().remove(getString(R.string.COOKIE_KEY_VALUE)).commit();
	}

	public void removeCookieDomain() {
		sp.edit().remove(getString(R.string.COOKIE_KEY_DOMAIN)).commit();
	}

	// public void removeCookieExpiryDate() {
	// sp.edit().remove(getString(R.string.COOKIE_KEY_EXPIRY_DATE)).commit();
	// }

}
