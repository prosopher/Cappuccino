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
package com.cappuccino.log;

final class AndroidLog extends Log {

	@Override
	protected int internalLog(int level, String msg) {

		// 현재 스레드의 스택을 불러와서 메소드 호출 순서를 알아낸다.
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

		// 0 : VMStack.java :: getThreadStackTrace()
		// 1 : Thread.java :: getStackTrace()
		// 2 : AndroidLog.java :: internalLog()
		// 3 : Log.java :: internalE()
		// 4 : Log.java :: e()
		// 5 : 이전 메소드를 나타내므로 5번 클래스 이름을 선택한다.
		String fullClassName = stacktrace[5].getClassName();
		String[] tokens = fullClassName.split("\\.");
		String simpleClassName = tokens[tokens.length - 1];
		String methodName = stacktrace[5].getMethodName();

		String tag = simpleClassName + "::" + methodName + "()";

		// null 포인터를 위해 빈 문자열을 연결해 준다.
		int ret;
		if (level == VERBOSE) {
			ret = android.util.Log.v(tag, msg + " ");
		} else if (level == DEBUG) {
			ret = android.util.Log.d(tag, msg + " ");
		} else if (level == INFO) {
			ret = android.util.Log.i(tag, msg + " ");
		} else if (level == WARN) {
			ret = android.util.Log.w(tag, msg + " ");
		} else if (level == ERROR) {
			ret = android.util.Log.e(tag, msg + " ");
		} else {
			throw new RuntimeException("Unknown error level");
		}

		return ret;
	}

}
