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
package com.cappuccino.autoscaling.view;

import java.util.Locale;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

public class AutoScalingKeywordFirstLetterView extends AutoScalingKeywordView {

	public AutoScalingKeywordFirstLetterView(Context context) {
		super(context);
	}

	public AutoScalingKeywordFirstLetterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void addKeyword(String keyword, String hint, boolean asHint) {

		// 첫 글자 추출
		String firstLetter = keyword.substring(0, 1).toUpperCase(
				Locale.getDefault());

		TextView tv = new TextView(getContext());
		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv.setSingleLine();
		tv.setText(firstLetter);

		// 검색을 위해 keyword 를 tag 로 설정해 둔다.
		tv.setTag(keyword);

		// dp 단위일 경우
		// tv.setTextSize(getTextSize());
		// px 단위일 경우
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize() * sf.min);

		addViewWithAlignment(tv);
	}
}
