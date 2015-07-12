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

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

// final : subclassing 금지
// final 대신 abstract 를 쓰면
// instantiation은 막을 수 있지만 subclassing 은 막을 수 없다.
public final class RequestConfig {

	// private constructor : instantiation 금지
	private RequestConfig() {
	}

	private static boolean debuggable = false;
	private static IMetaManager metaManager = null;
	private static boolean initialized = false;

	public static void initialize(Context context, IMetaManager metaManager) {

		// 초기화 시작
		RequestConfig.metaManager = metaManager;

		// Menifest 에서 meta-data 를 읽어온다.
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);

			Bundle metaData = ai.metaData;
			RequestConfig.debuggable = metaData
					.getBoolean("com.cappuccino.requestframework.debuggable");
		} catch (NameNotFoundException e) {
			throw new RuntimeException(e);
		}

		// 초기화 완료
		RequestConfig.initialized = true;
	}

	public static void setDebuggable(boolean debuggable) {
		RequestConfig.debuggable = debuggable;
	}

	public static boolean debuggable() {

		// 초기화 검사
		if (RequestConfig.initialized == false) {
			throw new RuntimeException("초기화 안됨");
		}

		return debuggable;
	}

	public static IMetaManager getMetaManager() {
		return metaManager;
	}

}
