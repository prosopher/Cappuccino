package com.cappuccino.log;

import android.content.Context;

// final : subclassing 금지
// final 대신 abstract 를 쓰면
// instantiation은 막을 수 있지만 subclassing 은 막을 수 없다.
public final class LogConfig {

	// private constructor : instantiation 금지
	private LogConfig() {
	}

	public static String serverHostName;

	public static void initialize(Context context, String serverHostName) {

		LogConfig.serverHostName = serverHostName;
	}

}
