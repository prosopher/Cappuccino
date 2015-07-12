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
package com.cappuccino;

import android.app.Activity;
import android.os.Bundle;

import com.cappuccino.log.Log;
import com.cappuccino.requestframework.IMetaManager;
import com.cappuccino.requestframework.xml.XmlMetaManager;

public class MetaManagerTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_activity);

		String xmlMetaFileName1 = "meta_request.xml";
		IMetaManager metaManager1 = new XmlMetaManager(this, xmlMetaFileName1);

		Log.e("request 테스트");
		if (metaManager1.getRequestSs("AnswerToUser", "toId").equals(
				"to_username")) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager1.getRequestSs("AnswerToUser", "toId"));
		}

		Log.e("type 테스트");
		if (metaManager1.getType("AnswerToUser").equals("POST")) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager1.getType("AnswerToUser"));
		}

		Log.e("enpoint 테스트");
		if (metaManager1
				.getEndpoint("AnswerToUser")
				.equals("http://dmserver1.kaist.ac.kr:8080/ExpertRecommender/api/reply_question")) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager1.getEndpoint("AnswerToUser"));
		}

		Log.e("cookieNeedLoaded 테스트");
		if (metaManager1.doesCookieNeedLoaded("AnswerToUser") == true) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager1.doesCookieNeedLoaded("AnswerToUser"));
		}

		Log.e("cookieNeedSaved 테스트");
		if (metaManager1.doesCookieNeedSaved("AnswerToUser") == false) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager1.doesCookieNeedSaved("AnswerToUser"));
		}

		Log.e("errorKeyAlwaysReturned 테스트");
		if (metaManager1.isErrorKeyAlwaysReturned("AnswerToUser") == false) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : "
					+ metaManager1.isErrorKeyAlwaysReturned("AnswerToUser"));
		}

		Log.e("ssErrorCheckKey 테스트");
		if (metaManager1.getSSErrorCheckKey("AnswerToUser").equals("err_msg")) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager1.getSSErrorCheckKey("AnswerToUser"));
		}

		Log.e("ssErrorHitValue 테스트");
		if (metaManager1.getSSErrorHitValue("AnswerToUser") == null) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager1.getSSErrorHitValue("AnswerToUser"));
		}

		String xmlMetaFileName2 = "meta_gcm.xml";
		IMetaManager metaManager2 = new XmlMetaManager(this, xmlMetaFileName2);

		Log.e("common 테스트");
		if (metaManager2.getCommonSs("msgType").equals("CODE")) {
			Log.e("SUCCESS");
		} else {
			Log.e("FAIL : " + metaManager2.getCommonSs("msgType"));
		}

		finish();
	}
}
