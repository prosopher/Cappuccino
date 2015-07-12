package com.cappuccino.log;

public abstract class Log {

	private static Log logger;

	static {
		// 프로젝트 실행 환경에 따라 다른 초기화
		try {
			Class.forName("android.app.Activity");

			// 안드로이드 프로젝트용 객체 생성
			logger = new AndroidLog();

		} catch (ClassNotFoundException e) {
			// 기본 자바 프로젝트용 객체 생성
			logger = new JavaLog();
		}
	}

	protected static final int VERBOSE = android.util.Log.VERBOSE;
	protected static final int DEBUG = android.util.Log.DEBUG;
	protected static final int INFO = android.util.Log.INFO;
	protected static final int WARN = android.util.Log.WARN;
	protected static final int ERROR = android.util.Log.ERROR;

	public static int v() {
		return logger.internalV();
	}

	private int internalV() {
		return internalLog(VERBOSE, " ");
	}

	public static int d() {
		return logger.internalD();
	}

	private int internalD() {
		return internalLog(DEBUG, " ");
	}

	public static int i() {
		return logger.internalI();
	}

	private int internalI() {
		return internalLog(INFO, " ");
	}

	public static int w() {
		return logger.internalW();
	}

	private int internalW() {
		return internalLog(WARN, " ");
	}

	public static int e() {
		return logger.internalE();
	}

	private int internalE() {
		return internalLog(ERROR, " ");
	}

	public static int v(boolean b) {
		return logger.internalV(b);
	}

	private int internalV(boolean b) {
		return internalLog(VERBOSE, Boolean.toString(b));
	}

	public static int d(boolean b) {
		return logger.internalD(b);
	}

	private int internalD(boolean b) {
		return internalLog(DEBUG, Boolean.toString(b));
	}

	public static int i(boolean b) {
		return logger.internalI(b);
	}

	private int internalI(boolean b) {
		return internalLog(INFO, Boolean.toString(b));
	}

	public static int w(boolean b) {
		return logger.internalW(b);
	}

	private int internalW(boolean b) {
		return internalLog(WARN, Boolean.toString(b));
	}

	public static int e(boolean b) {
		return logger.internalE(b);
	}

	private int internalE(boolean b) {
		return internalLog(ERROR, Boolean.toString(b));
	}

	public static int v(int i) {
		return logger.internalV(i);
	}

	private int internalV(int i) {
		return internalLog(VERBOSE, Integer.toString(i));
	}

	public static int d(int i) {
		return logger.internalD(i);
	}

	private int internalD(int i) {
		return internalLog(DEBUG, Integer.toString(i));
	}

	public static int i(int i) {
		return logger.internalI(i);
	}

	private int internalI(int i) {
		return internalLog(INFO, Integer.toString(i));
	}

	public static int w(int i) {
		return logger.internalW(i);
	}

	private int internalW(int i) {
		return internalLog(WARN, Integer.toString(i));
	}

	public static int e(int i) {
		return logger.internalE(i);
	}

	private int internalE(int i) {
		return internalLog(ERROR, Integer.toString(i));
	}

	public static int v(long l) {
		return logger.internalV(l);
	}

	private int internalV(long l) {
		return internalLog(VERBOSE, Long.toString(l));
	}

	public static int d(long l) {
		return logger.internalD(l);
	}

	private int internalD(long l) {
		return internalLog(DEBUG, Long.toString(l));
	}

	public static int i(long l) {
		return logger.internalI(l);
	}

	private int internalI(long l) {
		return internalLog(INFO, Long.toString(l));
	}

	public static int w(long l) {
		return logger.internalW(l);
	}

	private int internalW(long l) {
		return internalLog(WARN, Long.toString(l));
	}

	public static int e(long l) {
		return logger.internalE(l);
	}

	private int internalE(long l) {
		return internalLog(ERROR, Long.toString(l));
	}

	public static int v(float f) {
		return logger.internalV(f);
	}

	private int internalV(float f) {
		return internalLog(VERBOSE, Float.toString(f));
	}

	public static int d(float f) {
		return logger.internalD(f);
	}

	private int internalD(float f) {
		return internalLog(DEBUG, Float.toString(f));
	}

	public static int i(float f) {
		return logger.internalI(f);
	}

	private int internalI(float f) {
		return internalLog(INFO, Float.toString(f));
	}

	public static int w(float f) {
		return logger.internalW(f);
	}

	private int internalW(float f) {
		return internalLog(WARN, Float.toString(f));
	}

	public static int e(float f) {
		return logger.internalE(f);
	}

	private int internalE(float f) {
		return internalLog(ERROR, Float.toString(f));
	}

	public static int v(double d) {
		return logger.internalV(d);
	}

	private int internalV(double d) {
		return internalLog(VERBOSE, Double.toString(d));
	}

	public static int d(double d) {
		return logger.internalD(d);
	}

	private int internalD(double d) {
		return internalLog(VERBOSE, Double.toString(d));
	}

	public static int i(double d) {
		return logger.internalI(d);
	}

	private int internalI(double d) {
		return internalLog(INFO, Double.toString(d));
	}

	public static int w(double d) {
		return logger.internalW(d);
	}

	private int internalW(double d) {
		return internalLog(WARN, Double.toString(d));
	}

	public static int e(double d) {
		return logger.internalE(d);
	}

	private int internalE(double d) {
		return internalLog(ERROR, Double.toString(d));
	}

	public static int v(String msg) {
		return logger.internalV(msg);
	}

	private int internalV(String msg) {
		return internalLog(VERBOSE, msg);
	}

	public static int d(String msg) {
		return logger.internalD(msg);
	}

	private int internalD(String msg) {
		return internalLog(DEBUG, msg);
	}

	public static int i(String msg) {
		return logger.internalI(msg);
	}

	private int internalI(String msg) {
		return internalLog(INFO, msg);
	}

	public static int w(String msg) {
		return logger.internalW(msg);
	}

	private int internalW(String msg) {
		return internalLog(WARN, msg);
	}

	public static int e(String msg) {
		return logger.internalE(msg);
	}

	private int internalE(String msg) {
		return internalLog(ERROR, msg);
	}

	public static int v(Object obj) {
		return logger.internalV(obj);
	}

	private int internalV(Object obj) {
		return internalLog(VERBOSE, (obj == null) ? null : obj.toString());
	}

	public static int d(Object obj) {
		return logger.internalD(obj);
	}

	private int internalD(Object obj) {
		return internalLog(DEBUG, (obj == null) ? null : obj.toString());
	}

	public static int i(Object obj) {
		return logger.internalI(obj);
	}

	private int internalI(Object obj) {
		return internalLog(INFO, (obj == null) ? null : obj.toString());
	}

	public static int w(Object obj) {
		return logger.internalW(obj);
	}

	private int internalW(Object obj) {
		return internalLog(WARN, (obj == null) ? null : obj.toString());
	}

	public static int e(Object obj) {
		return logger.internalE(obj);
	}

	private int internalE(Object obj) {
		return internalLog(ERROR, (obj == null) ? null : obj.toString());
	}

	private static String getIndent(int indentLevel) {
		String indent = "";
		for (int i = 0; i < indentLevel; i++) {
			indent += "  ";
		}
		return indent;
	}

	public static int v(int indentLevel, boolean b) {
		return logger.internalV(indentLevel, b);
	}

	private int internalV(int indentLevel, boolean b) {
		return internalLog(VERBOSE,
				getIndent(indentLevel) + Boolean.toString(b));
	}

	public static int d(int indentLevel, boolean b) {
		return logger.internalD(indentLevel, b);
	}

	private int internalD(int indentLevel, boolean b) {
		return internalLog(DEBUG, getIndent(indentLevel) + Boolean.toString(b));
	}

	public static int i(int indentLevel, boolean b) {
		return logger.internalI(indentLevel, b);
	}

	private int internalI(int indentLevel, boolean b) {
		return internalLog(INFO, getIndent(indentLevel) + Boolean.toString(b));
	}

	public static int w(int indentLevel, boolean b) {
		return logger.internalW(indentLevel, b);
	}

	private int internalW(int indentLevel, boolean b) {
		return internalLog(WARN, getIndent(indentLevel) + Boolean.toString(b));
	}

	public static int e(int indentLevel, boolean b) {
		return logger.internalE(indentLevel, b);
	}

	private int internalE(int indentLevel, boolean b) {
		return internalLog(ERROR, getIndent(indentLevel) + Boolean.toString(b));
	}

	public static int v(int indentLevel, int i) {
		return logger.internalV(indentLevel, i);
	}

	private int internalV(int indentLevel, int i) {
		return internalLog(VERBOSE,
				getIndent(indentLevel) + Integer.toString(i));
	}

	public static int d(int indentLevel, int i) {
		return logger.internalD(indentLevel, i);
	}

	private int internalD(int indentLevel, int i) {
		return internalLog(DEBUG, getIndent(indentLevel) + Integer.toString(i));
	}

	public static int i(int indentLevel, int i) {
		return logger.internalI(indentLevel, i);
	}

	private int internalI(int indentLevel, int i) {
		return internalLog(INFO, getIndent(indentLevel) + Integer.toString(i));
	}

	public static int w(int indentLevel, int i) {
		return logger.internalW(indentLevel, i);
	}

	private int internalW(int indentLevel, int i) {
		return internalLog(WARN, getIndent(indentLevel) + Integer.toString(i));
	}

	public static int e(int indentLevel, int i) {
		return logger.internalE(indentLevel, i);
	}

	private int internalE(int indentLevel, int i) {
		return internalLog(ERROR, getIndent(indentLevel) + Integer.toString(i));
	}

	public static int v(int indentLevel, long l) {
		return logger.internalV(indentLevel, l);
	}

	private int internalV(int indentLevel, long l) {
		return internalLog(VERBOSE, getIndent(indentLevel) + Long.toString(l));
	}

	public static int d(int indentLevel, long l) {
		return logger.internalD(indentLevel, l);
	}

	private int internalD(int indentLevel, long l) {
		return internalLog(DEBUG, getIndent(indentLevel) + Long.toString(l));
	}

	public static int i(int indentLevel, long l) {
		return logger.internalI(indentLevel, l);
	}

	private int internalI(int indentLevel, long l) {
		return internalLog(INFO, getIndent(indentLevel) + Long.toString(l));
	}

	public static int w(int indentLevel, long l) {
		return logger.internalW(indentLevel, l);
	}

	private int internalW(int indentLevel, long l) {
		return internalLog(WARN, getIndent(indentLevel) + Long.toString(l));
	}

	public static int e(int indentLevel, long l) {
		return logger.internalE(indentLevel, l);
	}

	private int internalE(int indentLevel, long l) {
		return internalLog(ERROR, getIndent(indentLevel) + Long.toString(l));
	}

	public static int v(int indentLevel, float f) {
		return logger.internalV(indentLevel, f);
	}

	private int internalV(int indentLevel, float f) {
		return internalLog(VERBOSE, getIndent(indentLevel) + Float.toString(f));
	}

	public static int d(int indentLevel, float f) {
		return logger.internalD(indentLevel, f);
	}

	private int internalD(int indentLevel, float f) {
		return internalLog(DEBUG, getIndent(indentLevel) + Float.toString(f));
	}

	public static int i(int indentLevel, float f) {
		return logger.internalI(indentLevel, f);
	}

	private int internalI(int indentLevel, float f) {
		return internalLog(INFO, getIndent(indentLevel) + Float.toString(f));
	}

	public static int w(int indentLevel, float f) {
		return logger.internalW(indentLevel, f);
	}

	private int internalW(int indentLevel, float f) {
		return internalLog(WARN, getIndent(indentLevel) + Float.toString(f));
	}

	public static int e(int indentLevel, float f) {
		return logger.internalE(indentLevel, f);
	}

	private int internalE(int indentLevel, float f) {
		return internalLog(ERROR, getIndent(indentLevel) + Float.toString(f));
	}

	public static int v(int indentLevel, double d) {
		return logger.internalV(indentLevel, d);
	}

	private int internalV(int indentLevel, double d) {
		return internalLog(VERBOSE, getIndent(indentLevel) + Double.toString(d));
	}

	public static int d(int indentLevel, double d) {
		return logger.internalD(indentLevel, d);
	}

	private int internalD(int indentLevel, double d) {
		return internalLog(VERBOSE, getIndent(indentLevel) + Double.toString(d));
	}

	public static int i(int indentLevel, double d) {
		return logger.internalI(indentLevel, d);
	}

	private int internalI(int indentLevel, double d) {
		return internalLog(INFO, getIndent(indentLevel) + Double.toString(d));
	}

	public static int w(int indentLevel, double d) {
		return logger.internalW(indentLevel, d);
	}

	private int internalW(int indentLevel, double d) {
		return internalLog(WARN, getIndent(indentLevel) + Double.toString(d));
	}

	public static int e(int indentLevel, double d) {
		return logger.internalE(indentLevel, d);
	}

	private int internalE(int indentLevel, double d) {
		return internalLog(ERROR, getIndent(indentLevel) + Double.toString(d));
	}

	public static int v(int indentLevel, String msg) {
		return logger.internalV(indentLevel, msg);
	}

	private int internalV(int indentLevel, String msg) {
		return internalLog(VERBOSE, getIndent(indentLevel) + msg);
	}

	public static int d(int indentLevel, String msg) {
		return logger.internalD(indentLevel, msg);
	}

	private int internalD(int indentLevel, String msg) {
		return internalLog(DEBUG, getIndent(indentLevel) + msg);
	}

	public static int i(int indentLevel, String msg) {
		return logger.internalI(indentLevel, msg);
	}

	private int internalI(int indentLevel, String msg) {
		return internalLog(INFO, getIndent(indentLevel) + msg);
	}

	public static int w(int indentLevel, String msg) {
		return logger.internalW(indentLevel, msg);
	}

	private int internalW(int indentLevel, String msg) {
		return internalLog(WARN, getIndent(indentLevel) + msg);
	}

	public static int e(int indentLevel, String msg) {
		return logger.internalE(indentLevel, msg);
	}

	private int internalE(int indentLevel, String msg) {
		return internalLog(ERROR, getIndent(indentLevel) + msg);
	}

	public static int v(int indentLevel, Object obj) {
		return logger.internalV(indentLevel, obj);
	}

	private int internalV(int indentLevel, Object obj) {
		return internalLog(VERBOSE, getIndent(indentLevel)
				+ ((obj == null) ? null : obj.toString()));
	}

	public static int d(int indentLevel, Object obj) {
		return logger.internalD(indentLevel, obj);
	}

	private int internalD(int indentLevel, Object obj) {
		return internalLog(DEBUG, getIndent(indentLevel)
				+ ((obj == null) ? null : obj.toString()));
	}

	public static int i(int indentLevel, Object obj) {
		return logger.internalI(indentLevel, obj);
	}

	private int internalI(int indentLevel, Object obj) {
		return internalLog(INFO, getIndent(indentLevel)
				+ ((obj == null) ? null : obj.toString()));
	}

	public static int w(int indentLevel, Object obj) {
		return logger.internalW(indentLevel, obj);
	}

	private int internalW(int indentLevel, Object obj) {
		return internalLog(WARN, getIndent(indentLevel)
				+ ((obj == null) ? null : obj.toString()));
	}

	public static int e(int indentLevel, Object obj) {
		return logger.internalE(indentLevel, obj);
	}

	private int internalE(int indentLevel, Object obj) {
		return internalLog(ERROR, getIndent(indentLevel)
				+ ((obj == null) ? null : obj.toString()));
	}

	protected abstract int internalLog(int level, String msg);

}
