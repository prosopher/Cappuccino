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

import com.cappuccino.autoscaling.view.AutoScalingKeywordEditText;
import com.cappuccino.autoscaling.view.AutoScalingKeywordFirstLetterView;
import com.cappuccino.log.Log;

public class KeywordLayoutTestActivity extends Activity {

	public String[] keywords = { "location", "venue", "category", "time",
			"menu" };
	public String[] hints = { "위치", "장소", "업종", "시간", "메뉴" };
	public static final String[] templates = {
			// "location 에서 menu 맛있는 venue 어딘가요?",
			"time 에 붐비지 않는 location 근처에 있는 category 추천해 주세요",
			"location multiple locations one venue and location " };

	private AutoScalingKeywordFirstLetterView kflv0;
	private AutoScalingKeywordEditText ket0;
	private AutoScalingKeywordFirstLetterView kflv1;
	private AutoScalingKeywordEditText ket1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyword_layout_test_activity);

		// 뷰 연결
		kflv0 = (AutoScalingKeywordFirstLetterView) findViewById(R.id.kfll0);
		ket0 = (AutoScalingKeywordEditText) findViewById(R.id.ketl0);
		kflv1 = (AutoScalingKeywordFirstLetterView) findViewById(R.id.kfll1);
		ket1 = (AutoScalingKeywordEditText) findViewById(R.id.ketl1);

		kflv0.setText(templates[0], keywords, hints, true);
		ket0.setText(templates[0], keywords, hints, false);
		kflv1.setText(templates[1], keywords, hints, true);
		ket1.setText(templates[0], keywords, hints, true);

		// ketl0 가 전개된 후에 글자 출력
		ket0.post(new Runnable() {

			@Override
			public void run() {
				Log.e(ket0.getText());
			}
		});
	}
}
