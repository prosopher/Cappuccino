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

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;

public class AutoScalingKeywordEditText extends AutoScalingKeywordView {

	public AutoScalingKeywordEditText(Context context) {
		super(context);
	}

	public AutoScalingKeywordEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void addKeyword(String keyword, String hint, boolean asHint) {

		final EditText et = new EditText(getContext());
		et.setId(etId++);
		et.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		et.setSingleLine();

		// hint 또는 글씨를 설정한다.
		if (asHint) {
			et.setHint(hint);
		} else {
			et.setHint(keyword);
		}

		// 검색을 위해 keyword 를 tag 로 설정해 둔다.
		et.setTag(keyword);

		// 현재 layout_width 가 wrap_content 이기 때문에
		// 사용할 때 글씨를 쓰면 계속해서 글자 길이에 맞춰 EditText 자체 길이도 늘어나 버린다.
		// 늘어나다보면 화면 영역을 벗어나기 때문에 더 늘어나지 않게 만들어야 한다.
		// 임시 방편으로, wrap_content 로 생성해서 hint 영역까지만 wrapping 한 뒤,
		// 계산된 width 를 추후에 layout_width 로 재설정해버리면,
		// 재설정 된 width 이상으로 EditText 의 가로 길이가 늘어나지 않는다.
		et.post(new Runnable() {

			@Override
			public void run() {
				ViewGroup.LayoutParams lp = et.getLayoutParams();
				lp.width = et.getWidth();
				et.setLayoutParams(lp);
			}
		});

		// dp 단위일 경우
		// et.setTextSize(getTextSize());
		// px 단위일 경우
		et.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize() * sf.min);

		// nextFocusDownId 추가
		if (etLast != null) {
			etLast.setNextFocusDownId(et.getId());
		}
		etLast = et;

		addViewWithAlignment(et);
	}
}
