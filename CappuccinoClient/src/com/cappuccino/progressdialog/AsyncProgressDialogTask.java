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

import android.app.Activity;

import com.cappuccino.R;
import com.cappuccino.task.AsyncExtendedTask;
import com.cappuccino.task.AsyncExtendedTask.PostExecuteListener;
import com.cappuccino.task.AsyncExtendedTask.ProgressUpdateListener;

// AsyncExtendedTask 클래스를 상속 받은 작업단위를
// AsyncProgressDialogTask 를 이용해서 Wrapping 한 후 progress dialog 를 띄워준다.
public class AsyncProgressDialogTask<Params, Result> extends Thread implements
		ProgressUpdateListener, PostExecuteListener<Result> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private AsyncExtendedTask<Params, Result> wrappedTask;

	public AsyncProgressDialogTask(Activity activity, int theme,
			int contentViewLayoutId, String msg,
			AsyncExtendedTask<Params, Result> wrappedTask) {

		// 초기화
		this.activity = activity;

		// 매개변수가 주어지지 않았을 때는 기본 값으로 초기화
		if (theme == -1) {
			theme = android.R.style.Theme_Translucent_NoTitleBar;
		}
		if (contentViewLayoutId == -1) {
			contentViewLayoutId = R.layout.progress_dialog;
		}

		// 매개변수 다이얼로그가 주어졌을 때는 연결해서 사용
		this.progressDialog = new ProgressDialog(activity, theme,
				contentViewLayoutId, msg);

		this.wrappedTask = wrappedTask;

		// 리스너 등록
		this.wrappedTask.addProgressUpdateListener(this);
		this.wrappedTask.addPostExecuteListener(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// wrappedTask 실행
		wrappedTask.execute();
	}

	@Override
	public void onProgressUpdate(Integer... values) {

		// 자체적으로 다이얼로그를 이용해서 진행상황 업데이트
		progressDialog.setProgress(values[0]);
	}

	@Override
	public void onPostExecute(Result result) {

		// ProgressDialog 없애기
		progressDialog.dismiss();
	}

	public void execute() {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				progressDialog.show();
			}
		});
		start();
	}

	// Request 클래스처럼 waitForCompletion 을 만들 수 없는 이유?
	// progressDialog 를 띄우는 것과 waiting 업무가 모두 메인 스레드에서 실행되어야 하기 때문에
	// 둘 다 병행할 수 없고 progressDialog 를 띄우는 일만 하도록 한다.
}
