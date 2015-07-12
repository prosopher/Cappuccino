package com.cappuccino.log;

final class JavaLog extends Log {

	@Override
	protected int internalLog(int level, String msg) {

		// 현재 스레드의 스택을 불러와서 메소드 호출 순서를 알아낸다.
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

		// 0 : Thread.java :: getStackTrace()
		// 1 : AndroidLog.java :: internalLog()
		// 2 : Log.java :: internalE()
		// 3 : Log.java :: e()
		// 4 : 이전 메소드를 나타내므로 4번 클래스 이름을 선택한다.
		String fullClassName = stacktrace[4].getClassName();
		String[] tokens = fullClassName.split("\\.");
		String simpleClassName = tokens[tokens.length - 1];
		String methodName = stacktrace[4].getMethodName();

		String tag = simpleClassName + "::" + methodName + "()";

		// tag 길이를 맞춰준다.
		int lengthTag = tag.length();
		if (lengthTag < 30) {
			// 짧을 경우 공백을 추가한다.
			for (int i = 0; i < 30 - lengthTag; i++) {
				tag += " ";
			}
		} else if (lengthTag > 30) {
			// 길 경우 잘라낸다.
			tag = tag.substring(0, 30);
		}

		// null 포인터를 위해 빈 문자열을 연결해 준다.
		if (level == VERBOSE) {
			System.out.println("LOG_V       " + tag + "\t" + msg);
		} else if (level == DEBUG) {
			System.out.println("LOG_DE      " + tag + "\t" + msg);
		} else if (level == INFO) {
			System.out.println("LOG_INF     " + tag + "\t" + msg);
		} else if (level == WARN) {
			System.out.println("LOG_WARN    " + tag + "\t" + msg);
		} else if (level == ERROR) {
			System.out.println("LOG_ERROR   " + tag + "\t" + msg);
		} else {
			throw new RuntimeException("Unknown error level");
		}

		return 1;
	}

}
