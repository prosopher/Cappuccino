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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cappuccino.R;
import com.cappuccino.autoscaling.AutoScalingUtil;
import com.cappuccino.autoscaling.AutoScalingUtil.ScaleFactor;

public abstract class AutoScalingKeywordView extends LinearLayout {

	// 생성자 ///////////////////////////////////////////////////////////////////

	public AutoScalingKeywordView(Context context) {
		super(context);
	}

	public AutoScalingKeywordView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(attrs);
	}

	public AutoScalingKeywordView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		init(attrs);
	}

	protected ScaleFactor sf;

	// 픽셀 단위로 scale 된 글자 크기
	// 변환 공식
	// http://stackoverflow.com/questions/15314549/android-what-is-the-default-textsize-of-an-edittext
	private final float SCALED_DENSITY = getResources().getDisplayMetrics().scaledDensity;
	private final float DEF_TEXT_SIZE = new TextView(getContext())
			.getTextSize();
	private float textSize = DEF_TEXT_SIZE / SCALED_DENSITY;

	private void init(AttributeSet attrs) {

		// 제일 외곽 레이아웃 세팅
		setOrientation(VERTICAL);

		// 스크린 정보를 불러오기
		sf = AutoScalingUtil.getScaleFactor(getContext(),
				AutoScalingUtil.FULL_HD_PX_W, AutoScalingUtil.FULL_HD_PX_H,
				false);

		// TypedArray with xml 참고자료
		// http://stackoverflow.com/questions/2695646/declaring-a-custom-android-ui-element-using-xml
		// http://stackoverflow.com/questions/8037101/how-to-get-attributeset-properties
		// http://stackoverflow.com/questions/3441396/defining-custom-attrs

		// 글자 크기 추출
		// dp 단위일 경우
		// textSize = ta.getDimension(0, DEF_TEXT_SIZE) / SCALED_DENSITY;
		// px 단위일 경우
		TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.AutoScalingKeywordView);
		int n = ta.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = ta.getIndex(i);
			if (attr == R.styleable.AutoScalingKeywordView_android_textSize) {
				textSize = ta.getDimension(attr, DEF_TEXT_SIZE);
			}
		}
		ta.recycle();
	}

	public float getTextSize() {
		return textSize;
	}

	// 텍스트 내용 설정 부분
	// ///////////////////////////////////////////////////////////////////

	public String getText() {
		// EditText (instanceof TextView) 나 TextView 등에서
		// getText 한 결과를 모두 연결해서 반환한다.
		String text = "";

		// LinearLayout 탐색
		for (int i = 0; i < getChildCount(); i++) {
			LinearLayout ll = (LinearLayout) getChildAt(i);

			// 탐색 된 LinearLayout 의 child view 탐색하며 텍스트 연결
			for (int j = 0; j < ll.getChildCount(); j++) {
				View childView = ll.getChildAt(j);
				if (childView instanceof TextView) {
					text += ((TextView) childView).getText();
				}
			}
		}

		return text;
	}

	public String getValueOf(String keyword) {
		// 모든 View 에서 tag 가 keyword 인 경우 값을 반환한다.
		String text = "";

		// LinearLayout 탐색
		for (int i = 0; i < getChildCount(); i++) {
			LinearLayout ll = (LinearLayout) getChildAt(i);

			// 탐색 된 LinearLayout 의 child view 탐색하며 텍스트 검색
			for (int j = 0; j < ll.getChildCount(); j++) {
				View childView = ll.getChildAt(j);
				Object tag = childView.getTag();
				if (tag instanceof String && tag.equals(keyword)) {
					return ((TextView) childView).getText().toString();
				}
			}
		}

		return text;
	}

	public boolean isAnyInputEmpty() {

		boolean anyEmpty = false;

		// LinearLayout 탐색
		for (int i = 0; i < getChildCount(); i++) {
			LinearLayout ll = (LinearLayout) getChildAt(i);

			// 탐색 된 LinearLayout 의 child view 탐색하며 텍스트 비어있는지 검사
			for (int j = 0; j < ll.getChildCount(); j++) {
				View childView = ll.getChildAt(j);
				if (childView instanceof EditText) {
					EditText curChild = (EditText) childView;

					// 하나라도 비어있으면 true
					if (curChild.length() <= 0) {
						return true;
					}
				}
			}
		}

		// 하나도 비어있지 않으면 anyEmpty
		return anyEmpty;
	}

	// asHint : keyword 를 보여줄지 hint 를 보여줄지 표시해둔 플래그
	public void setText(final String text, final String[] keywords,
			final String[] hints, final boolean asHint) {

		// 이전 뷰 전부 제거
		removeAllViews();

		if (text != null && keywords != null && hints != null) {

			// 모든 작업이 끝나기 전까지(현재 레이아웃 크기가 정해질 때까지)
			// 작업을 미루고, 끝나면 text 에 기반해서 새로운 뷰를 추가한다.
			post(new Runnable() {

				@Override
				public void run() {
					// 새로운 뷰 추가
					addViews(text, keywords, hints, asHint);
				}
			});
		}
	}

	// 자식 뷰 추가 부분
	// ///////////////////////////////////////////////////////////////////

	private LinearLayout llHorizontal;

	private void addViews(String text, String[] keywords, String[] hints,
			boolean asHint) {

		// 미리 Horizontal ll 하나 추가
		llHorizontal = new LinearLayout(getContext());
		llHorizontal.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		llHorizontal.setOrientation(HORIZONTAL);
		addView(llHorizontal);

		// 입력받은 텍스트에서 텍스트와 키워드 분리
		// 키워드 배열이 {"a", "b", "c"} 일 경우 "a|b|c" 로 변환
		// | 는 정규식에서 사용하는 multiple delimeter 구분 문자
		String keywordDelimeter = "";
		for (int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];
			if (i < keywords.length - 1) {
				// 마지막이 아닐 경우
				keywordDelimeter += keyword + "|";
			} else {
				// 마지막인 경우
				keywordDelimeter += keyword;
			}
		}

		// http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
		String regExForSplitWithDelimeter = "(?<=(" + keywordDelimeter
				+ "))|(?=(" + keywordDelimeter + "))";

		// 분리된 각 텍스트를 fragment 라고 칭한다.
		String[] fragmentList = text.split(regExForSplitWithDelimeter);

		// 분리된 텍스트와 키워드를 뷰로 전환 후 추가
		for (String fragment : fragmentList) {
			addFragment(fragment, keywords, hints, asHint);
		}
	}

	private void addFragment(String fragment, String[] keywords,
			String[] hints, boolean asHint) {

		// fragment 가 키워드 목록 중에 하나인지 검사한다.
		boolean isKeyword = false;
		String hint = null;
		for (int i = 0; i < keywords.length; i++) {
			String keyword = keywords[i];
			hint = hints[i];
			if (fragment.equals(keyword)) {
				isKeyword = true;
				break;
			}
		}

		if (isKeyword) {
			// fragment 가 키워드라면
			addKeyword(fragment, hint, asHint);
		} else {
			// fragment 가 키워드가 아니라면
			addText(fragment);
		}
	}

	protected int etId = 1;
	protected EditText etLast;

	protected abstract void addKeyword(String keyword, String hint,
			boolean asHint);

	private void addText(String text) {
		TextView tv = new TextView(getContext());
		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		tv.setSingleLine();
		tv.setText(text);

		// dp 단위일 경우
		// tv.setTextSize(getTextSize());
		// px 단위일 경우
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize() * sf.min);
		llHorizontal.setPadding(0, 0, 0, 0);

		addViewWithAlignment(tv);
	}

	protected void addViewWithAlignment(View nextView) {

		// 중요 변수 검사
		if (llHorizontal == null) {
			throw new IllegalArgumentException();
		}

		// 가로 레이아웃이 꽉 찾는지 검사
		nextView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int widthNextView = nextView.getMeasuredWidth();

		llHorizontal.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int widthLLHorizontal = llHorizontal.getMeasuredWidth();

		// 다음 예상 가로길이 = 왼쪽패딩 + 오른쪽패딩 + 현재 가로 컨테이너 가로길이 + 다음 뷰 가로길이
		int expectedNextWidth = getPaddingLeft() + getPaddingRight()
				+ widthLLHorizontal + widthNextView;

		// 현재 컨테이너 가로길이
		int widthOfContainingLayout = getWidth();

		if (expectedNextWidth > widthOfContainingLayout) {
			// 다음 예상 가로길이가, 현재 컨테이너의 가로길이를 초과할 경우

			// 새로운 가로 레이아웃을 생성 후 추가
			llHorizontal = new LinearLayout(getContext());
			llHorizontal.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			llHorizontal.setOrientation(HORIZONTAL);
			addView(llHorizontal);
		}

		llHorizontal.addView(nextView);
	}
}
