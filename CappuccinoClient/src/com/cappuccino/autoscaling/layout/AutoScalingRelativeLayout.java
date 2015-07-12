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
package com.cappuccino.autoscaling.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.cappuccino.autoscaling.AutoScalingManager;

// http://www.vanteon.com/papersandpublications.html
// Automatically Scaling Android Apps for Multiple Screens 수정해서 만듬
public class AutoScalingRelativeLayout extends RelativeLayout {

	private AutoScalingManager asm;

	public AutoScalingRelativeLayout(Context context) {
		this(context, null);
	}

	public AutoScalingRelativeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AutoScalingRelativeLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		// 관리자 초기화
		asm = new AutoScalingManager(this, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 내부적으로 모든 자식 뷰를 처음 한번만 스케일 한다.
		asm.scaleViews(this);

		// super 의 onMeasure 를 통해서 자식의 크기와 영역을 파악하기 때문에
		// 이것을 호출하기 전에 스케일 해둔다.
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
