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
package com.cappuccino;

import android.app.Activity;
import android.os.Bundle;

import com.cappuccino.progressdialog.AsyncProgressDialogTask;
import com.cappuccino.task.AsyncExtendedTask;

public class ProgressDialogTestActivity extends Activity {

	private AsyncProgressDialogTask<Object, Object> progressDialogTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_activity);

		this.progressDialogTask = new AsyncProgressDialogTask<Object, Object>(
				this, android.R.style.Theme_Holo_Light_NoActionBar,
				R.layout.progress_dialog, "TESTING...", wrappedTask);
		this.progressDialogTask.execute();
	}

	private AsyncExtendedTask<Object, Object> wrappedTask = new AsyncExtendedTask<Object, Object>() {

		@Override
		public Void doInBackground(Object... params) {
			/*
			 * This is just a code that delays the thread execution 4 times,
			 * during 850 milliseconds and updates the current progress. This is
			 * where the code that is going to be executed on a background
			 * thread must be placed.
			 */
			try {
				// Get the current thread's token
				synchronized (this) {
					// Initialize an integer (that will act as a counter) to
					// zero
					int counter = 0;
					// While the counter is smaller than four
					while (counter <= 4) {
						// Wait 850 milliseconds
						this.wait(850);
						// Increment the counter
						counter++;
						// Set the current progress.
						// This value is going to be passed to the
						// onProgressUpdate() method.
						publishProgress(counter * 25);
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			finish();
		};
	};
}
