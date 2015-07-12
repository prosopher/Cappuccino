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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cappuccino.R;

public class RatingBar extends LinearLayout implements OnTouchListener {

	private int starOffResId = -1;
	private int starOnResId = -1;
	private int layoutHeight = -1;
	private boolean clickable = true;

	class Star {

		public ImageView ivStar;
		public boolean rated;

		public Star(ImageView ivStar) {
			this.ivStar = ivStar;
		}
	}

	private Star[] stars;

	public RatingBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RatingBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// 가로 방향
		setOrientation(HORIZONTAL);

		// resId 를 바탕으로 각 속성 값 추출
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.RatingBar);
		int n = ta.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = ta.getIndex(i);
			if (attr == R.styleable.RatingBar_star_off) {
				starOffResId = ta.getResourceId(attr, starOffResId);
			} else if (attr == R.styleable.RatingBar_star_on) {
				starOnResId = ta.getResourceId(attr, starOnResId);
			} else if (attr == R.styleable.RatingBar_android_layout_height) {
				layoutHeight = ta.getDimensionPixelSize(attr, layoutHeight);
			} else if (attr == R.styleable.RatingBar_android_clickable) {
				clickable = ta.getBoolean(attr, clickable);
			}
		}
		ta.recycle();

		// 이미지 뷰 5개 추가
		MarginLayoutParams lpStar = new MarginLayoutParams(layoutHeight,
				layoutHeight);
		MarginLayoutParams lpEs = new MarginLayoutParams(layoutHeight / 4,
				layoutHeight);

		ImageView ivStar1 = new ImageView(context);
		EmptySpace es1 = new EmptySpace(context);
		ImageView ivStar2 = new ImageView(context);
		EmptySpace es2 = new EmptySpace(context);
		ImageView ivStar3 = new ImageView(context);
		EmptySpace es3 = new EmptySpace(context);
		ImageView ivStar4 = new ImageView(context);
		EmptySpace es4 = new EmptySpace(context);
		ImageView ivStar5 = new ImageView(context);

		ivStar1.setBackgroundResource(starOffResId);
		ivStar2.setBackgroundResource(starOffResId);
		ivStar3.setBackgroundResource(starOffResId);
		ivStar4.setBackgroundResource(starOffResId);
		ivStar5.setBackgroundResource(starOffResId);

		// 뷰 추가
		addView(ivStar1, lpStar);
		addView(es1, lpEs);
		addView(ivStar2, lpStar);
		addView(es2, lpEs);
		addView(ivStar3, lpStar);
		addView(es3, lpEs);
		addView(ivStar4, lpStar);
		addView(es4, lpEs);
		addView(ivStar5, lpStar);

		stars = new Star[5];
		stars[0] = new Star(ivStar1);
		stars[1] = new Star(ivStar2);
		stars[2] = new Star(ivStar3);
		stars[3] = new Star(ivStar4);
		stars[4] = new Star(ivStar5);

		// 리스너 등록
		if (clickable) {
			setOnTouchListener(this);
		}
	}

	public int getRating() {
		int rating = 0;
		for (int i = 0; i < 5; i++) {
			if (stars[i].rated == true) {
				rating++;
			}
		}
		return rating;
	}

	public void setRating(int rating) {
		for (int i = 0; i < 5; i++) {
			if (i < rating) {
				stars[i].ivStar.setBackgroundResource(starOnResId);
				stars[i].rated = true;
			} else {
				stars[i].ivStar.setBackgroundResource(starOffResId);
				stars[i].rated = false;
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE
				|| event.getAction() == MotionEvent.ACTION_UP) {
			int selected = 0;
			if (event.getX() < getWidth() / 10) {
				selected = 0;
			} else if (event.getX() < getWidth() * 1 / 5) {
				selected = 1;
			} else if (event.getX() < getWidth() * 2 / 5) {
				selected = 2;
			} else if (event.getX() < getWidth() * 3 / 5) {
				selected = 3;
			} else if (event.getX() < getWidth() * 4 / 5) {
				selected = 4;
			} else {
				selected = 5;
			}

			setRating(selected);
			return true;
		}

		return false;
	}
}
