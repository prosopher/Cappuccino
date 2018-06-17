package com.cappuccino.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

// Android/Java 상관없이 사용하기 위해 Apache Base64 도입
import org.apache.commons.codec_new.binary.Base64;

// URLEncoder 관련
// https://stackoverflow.com/questions/19515616/how-to-handle-special-characters-in-url-as-parameter-values
public class EncodeUtil {

	public static synchronized String encodeToUrlBase64(Serializable s) {
		try {
			byte[] bytes = ByteUtil.serialize(s);
			String base64Str = Base64.encodeBase64String(bytes);
			String urlBase64Str = URLEncoder.encode(base64Str, "UTF-8");
			// Log.i("bytes length " + bytes.length);
			// Log.i("bytes MD5 " + ByteUtil.getMD5Checksum(bytes));
			// Log.i("base64Str length " + base64Str.length());
			// Log.i("base64Str " + base64Str + " / " + base64Str.length());
			// Log.i("urlBase64Str length " + urlBase64Str.length());
			// Log.i("urlBase64Str " + urlBase64Str + " / " + urlBase64Str.length());
			return urlBase64Str;
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static synchronized Object decodeFromUrlBase64(String urlBase64Str) {
		try {
			String base64Str = URLDecoder.decode(urlBase64Str, "UTF-8");
			byte[] bytes = Base64.decodeBase64(base64Str);
			// Log.i("bytes length " + bytes.length);
			// Log.i("bytes MD5 " + ByteUtil.getMD5Checksum(bytes));
			// Log.i("base64Str length " + base64Str.length());
			// Log.i("base64Str " + base64Str + " / " + base64Str.length());
			// Log.i("urlBase64Str length " + urlBase64Str.length());
			// Log.i("urlBase64Str " + urlBase64Str + " / " + urlBase64Str.length());
			return ByteUtil.deserialize(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

}
