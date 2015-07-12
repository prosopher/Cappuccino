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
package com.cappuccino.task;

import java.util.ArrayList;

import android.os.AsyncTask;

// AsyncExtendedTask : 상속 받으면 다른 wrapper 에 쉽게 wrapped 될 수 있는 작업단위
public abstract class AsyncExtendedTask<Params, Result> extends
		AsyncTask<Params, Integer, Result> {

	// ProgressUpdateListener : 외부에 진행상황을 전달하기 위한 리스너

	private ArrayList<ProgressUpdateListener> progressUpdateListeners = new ArrayList<ProgressUpdateListener>();

	public void addProgressUpdateListener(
			ProgressUpdateListener progressUpdateListener) {
		this.progressUpdateListeners.add(progressUpdateListener);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);

		for (ProgressUpdateListener listener : progressUpdateListeners) {
			if (listener != null) {
				listener.onProgressUpdate(values);
			}
		}
	}

	public interface ProgressUpdateListener {
		public void onProgressUpdate(Integer... values);
	}

	// PostExecuteListener : 외부에 결과를 전달하기 위한 리스너

	private ArrayList<PostExecuteListener<Result>> postExecuteListeners = new ArrayList<PostExecuteListener<Result>>();

	public void addPostExecuteListener(
			PostExecuteListener<Result> postExecuteListener) {
		this.postExecuteListeners.add(postExecuteListener);
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);

		for (PostExecuteListener<Result> listener : postExecuteListeners) {
			if (listener != null) {
				listener.onPostExecute(result);
			}
		}
	}

	public interface PostExecuteListener<Result> {
		public void onPostExecute(Result result);
	}
}
