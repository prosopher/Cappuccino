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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.cappuccino.log.Log;

class TypefaceManager {

	private static final String NANUM_BARUN_GOTHIC_FILE_NAME = "NanumBarunGothic.ttf";

	private static final int SANS = 1;
	private static final int SERIF = 2;
	private static final int MONOSPACE = 3;
	private static final int NANUM_BARUN_GOTHIC = 4;

	public static TypefaceManager createFromAttributes(Context context,
			AttributeSet attrs, int[] attrsResId, final int typefaceAttrResId) {

		int typefaceIndex = -1;

		TypedArray ta = context.obtainStyledAttributes(attrs, attrsResId);
		int n = ta.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = ta.getIndex(i);
			if (attr == typefaceAttrResId) {
				typefaceIndex = ta.getInt(attr, typefaceIndex);
			}
		}
		ta.recycle();

		return new TypefaceManager(context, typefaceIndex);
	}

	private static Typeface typeface;

	public TypefaceManager(Context context, int typefaceIndex) {
		// 초기화 되지 않았을 때만 typeface 생성
		if (typeface == null) {
			typeface = getTypefaceFromAttrs(context, typefaceIndex);
		}
	}

	private Typeface getTypefaceFromAttrs(Context context, int typefaceIndex) {
		Typeface typeface = null;
		switch (typefaceIndex) {
		case SANS:
			typeface = Typeface.SANS_SERIF;
			break;
		case SERIF:
			typeface = Typeface.SERIF;
			break;
		case MONOSPACE:
			typeface = Typeface.MONOSPACE;
			break;
		case NANUM_BARUN_GOTHIC:
			try {
				typeface = Typeface.createFromAsset(context.getAssets(),
						NANUM_BARUN_GOTHIC_FILE_NAME);
			} catch (RuntimeException e) {
				Log.e(e.getMessage());
			}
			break;
		}
		return typeface;
	}

	public Typeface getTypeface() {
		return typeface;
	}

	// private void setTypeFace(ViewGroup parent) {
	//
	// for (int i = 0; i < parent.getChildCount(); i++) {
	// View child = parent.getChildAt(i);
	//
	// if (child instanceof ViewGroup) {
	// setTypeFace((ViewGroup) child);
	// } else if (child instanceof TextView) {
	// // 텍스트 뷰인 경우
	// TextView tv = (TextView) child;
	// // Log.e(tv.getText());
	// Typeface curTf = tv.getTypeface();
	//
	// // 텍스트 뷰에 폰트가 있는지 검사
	// if (curTf == null) {
	// // 폰트가 없는 경우
	// } else {
	// // 폰트가 있는 경우
	// // 스타일 검사
	// if (curTf.getStyle() == Typeface.BOLD) {
	// tv.setTypeface(typefaceBold);
	// } else {
	// tv.setTypeface(typeface);
	// }
	// tv.setPaintFlags(tv.getPaintFlags()
	// | Paint.SUBPIXEL_TEXT_FLAG);
	// }
	// }
	// }
	// }
}
