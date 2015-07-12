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
package com.cappuccino.autoscaling;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.cappuccino.R;
import com.cappuccino.autoscaling.AutoScalingUtil.ScaleFactor;
import com.cappuccino.autoscaling.layout.AutoScalingFragmentLinearLayout;
import com.cappuccino.autoscaling.layout.AutoScalingFragmentRelativeLayout;
import com.cappuccino.autoscaling.layout.AutoScalingLinearLayout;
import com.cappuccino.autoscaling.layout.AutoScalingRelativeLayout;

public class AutoScalingManager {

	private TypefaceManager tm;
	private boolean debuggable;
	private ScaleFactor sf;

	public AutoScalingManager(ViewGroup root, AttributeSet attrs) {

		if (attrs != null) {

			// 현재 root 의 종류에 따라 사용할 attrsResId 및 각 resId 결정
			int[] attrsResId = null;
			int typefaceAttrResId = -1;
			int debuggableResId = -1;
			if (root instanceof AutoScalingFragmentLinearLayout) {
				attrsResId = R.styleable.AutoScalingFragmentLinearLayout;
				typefaceAttrResId = R.styleable.AutoScalingFragmentLinearLayout_typeface;
				debuggableResId = R.styleable.AutoScalingFragmentLinearLayout_debuggable;
			} else if (root instanceof AutoScalingFragmentRelativeLayout) {
				attrsResId = R.styleable.AutoScalingFragmentRelativeLayout;
				typefaceAttrResId = R.styleable.AutoScalingFragmentRelativeLayout_typeface;
				debuggableResId = R.styleable.AutoScalingFragmentRelativeLayout_debuggable;
			} else if (root instanceof AutoScalingLinearLayout) {
				attrsResId = R.styleable.AutoScalingLinearLayout;
				typefaceAttrResId = R.styleable.AutoScalingLinearLayout_typeface;
				debuggableResId = R.styleable.AutoScalingLinearLayout_debuggable;
			} else if (root instanceof AutoScalingRelativeLayout) {
				attrsResId = R.styleable.AutoScalingRelativeLayout;
				typefaceAttrResId = R.styleable.AutoScalingRelativeLayout_typeface;
				debuggableResId = R.styleable.AutoScalingRelativeLayout_debuggable;
			} else {
				throw new IllegalArgumentException("알 수 없는 레이아웃 : " + root);
			}

			// resId 를 바탕으로 각 속성 값 추출
			int typefaceIndex = -1;
			TypedArray ta = root.getContext().obtainStyledAttributes(attrs,
					attrsResId);
			int n = ta.getIndexCount();
			for (int i = 0; i < n; i++) {
				int attr = ta.getIndex(i);
				if (attr == typefaceAttrResId) {
					typefaceIndex = ta.getInt(attr, typefaceIndex);
				} else if (attr == debuggableResId) {
					this.debuggable = ta.getBoolean(attr, false);
				}
			}
			ta.recycle();

			// 결정한 매개변수를 활용해서 typeface 관리자 생성
			this.tm = new TypefaceManager(root.getContext(), typefaceIndex);
		}

		// 스크린 정보를 불러오기
		this.sf = AutoScalingUtil.getScaleFactor(root.getContext(),
				AutoScalingUtil.FULL_HD_PX_W, AutoScalingUtil.FULL_HD_PX_H,
				debuggable);
	}

	private boolean notScaled = true;

	public void scaleViews(ViewGroup root) {

		// 처음 한번만 스케일 한다.
		if (notScaled) {
			notScaled = false;

			// 현재 root 의 종류에 따라
			// 다른 방식으로 초기화 스케일한다.
			if (root instanceof AutoScalingFragmentLinearLayout) {
				// 현재 상태로 스케일
				AutoScalingUtil.scaleViews(root, sf, tm.getTypeface(),
						debuggable);
			} else if (root instanceof AutoScalingFragmentRelativeLayout) {
				// 좌표를 원점으로 돌리고 스케일
				AutoScalingUtil.nullifyAndScaleViews(root, sf,
						tm.getTypeface(), debuggable);
			} else if (root instanceof AutoScalingLinearLayout) {
				// 현재 상태로 스케일
				AutoScalingUtil.scaleViews(root, sf, tm.getTypeface(),
						debuggable);
			} else if (root instanceof AutoScalingRelativeLayout) {
				// 현재 상태로 스케일
				AutoScalingUtil.scaleViews(root, sf, tm.getTypeface(),
						debuggable);
			} else {
				throw new IllegalArgumentException("알 수 없는 레이아웃 : " + root);
			}
		}
	}
}
