package com.cappuccino.log.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.cappuccino.log.Log;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = -5062745924467139465L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Log.d(getTime() + " " + getTag());

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Log.d(getTime() + " " + getTag());

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
	}

	private String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	private String getTag() {
		// 현재 스레드의 스택을 불러와서 메소드 호출 순서를 알아낸다.
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();

		// for (StackTraceElement a : stacktrace) {
		// System.out.println(a.getClassName() + " " + a.getMethodName());
		// }
		// System.out.println();

		// 0번은 Thread.java :: getStackTrace()
		// 1번은 BaseServlet.java :: getTag()
		// 2번은 BaseServlet.java :: doGet() or doPost()
		// 3번은 이전 메소드를 나타내므로 3번 클래스 이름을 선택한다.
		String fullClassName = stacktrace[3].getClassName();
		String[] tokens = fullClassName.split("\\.");
		String simpleClassName = tokens[tokens.length - 1];
		String methodName = stacktrace[3].getMethodName();

		return simpleClassName + "::" + methodName + "()";
	}

	// change to true to allow GET calls
	static final boolean DEBUG = true;

	protected String getParam(HttpServletRequest req, String key)
			throws ServletException, UnsupportedEncodingException {
		String value = req.getParameter(key);
		if (isEmptyOrNull(value)) {
			if (DEBUG) {
				StringBuilder parameters = new StringBuilder();

				Enumeration<String> names = req.getParameterNames();
				while (names.hasMoreElements()) {
					String name = names.nextElement();
					String param = req.getParameter(name);
					parameters.append(name).append("=").append(param)
							.append("\n");
				}
				Log.d("parameters: " + parameters);
			}
			throw new ServletException("Parameter " + key + " not found");
		} else {
			value = URLDecoder.decode(value.trim(), "UTF-8");
		}
		return value;
	}

	public String[] getParams(HttpServletRequest req, String key)
			throws ServletException, UnsupportedEncodingException {
		String[] values = req.getParameterValues(key);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				if (isEmptyOrNull(values[i])) {
					if (DEBUG) {
						StringBuilder parameters = new StringBuilder();

						Enumeration<String> names = req.getParameterNames();
						while (names.hasMoreElements()) {
							String name = names.nextElement();
							String param = req.getParameter(name);
							parameters.append(name).append("=").append(param)
									.append("\n");
						}
						Log.d("parameters: " + parameters);
					}
					throw new ServletException("Parameter " + key
							+ " not found");
				} else {
					values[i] = URLDecoder.decode(values[i].trim(), "UTF-8");
				}
			}
		}
		return values;
	}

	protected String getParam(HttpServletRequest req, String key,
			String defaultValue) throws UnsupportedEncodingException {
		String value = req.getParameter(key);
		if (isEmptyOrNull(value)) {
			value = defaultValue;
		} else {
			URLDecoder.decode(value.trim(), "UTF-8");
		}
		return value;
	}

	protected void setSuccess(HttpServletResponse resp) throws IOException {

		// TODO 반환 방법을 HTTP 200 으로 바꾸기
		// Cappuccino 라이브러리도 바꾸기
		try {
			JSONObject retJsonObj = new JSONObject();
			retJsonObj.put("msg", "OK");

			PrintWriter writer = resp.getWriter();
			writer.write(retJsonObj.toString());
			writer.close();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	protected boolean isEmptyOrNull(String value) {
		return value == null || value.trim().length() == 0;
	}

}
