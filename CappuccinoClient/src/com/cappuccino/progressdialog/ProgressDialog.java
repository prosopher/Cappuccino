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
package com.cappuccino.progressdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressDialog extends Dialog {

	// 생성자 부분
	// ///////////////////////////////////////////////////////////////////

	private Context context;
	private int contentViewLayoutId;
	private String msg;

	public ProgressDialog(Context context, int theme, int contentViewLayoutId,
			String msg) {
		super(context, theme);

		this.context = context;
		this.contentViewLayoutId = contentViewLayoutId;
		this.msg = msg;

		// 리스너 등록
		setOnKeyListener(keyListener);
	}

	// onCreate 부분
	// ///////////////////////////////////////////////////////////////////

	private TextView tvMsg;
	// private ProgressBar progressBarIndeterminate;
	private ProgressBar progressBarDeterminate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(contentViewLayoutId);

		// 투명 배경 설정
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		// ID 검색
		String packageName = context.getPackageName();
		int resIdTvMsg = context.getResources().getIdentifier("tvMsg", "id",
				packageName);
		// int resIdProgressBarIndeterminate = context.getResources()
		// .getIdentifier("progressBarIndeterminate", "id", packageName);
		int resIdProgressBarDeterminate = context.getResources().getIdentifier(
				"progressBarDeterminate", "id", packageName);

		// 뷰 연결
		// progressBarIndeterminate = (ProgressBar)
		// findViewById(resIdProgressBarIndeterminate);
		progressBarDeterminate = (ProgressBar) findViewById(resIdProgressBarDeterminate);
		tvMsg = (TextView) findViewById(resIdTvMsg);

		// 뷰 초기화
		tvMsg.setText(msg);
	}

	public void setProgress(int progress) {
		if (progressBarDeterminate != null) {
			progressBarDeterminate.setProgress(progress);
		}
	}

	private OnKeyListener keyListener = new OnKeyListener() {

		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				return true;
			}

			return false;
		}
	};
}
