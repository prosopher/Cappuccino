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
package com.cappuccino.preference;

import java.util.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.cappuccino.R;

// TimePreference 수정해서 만듬
public class TimeRangePreference extends DialogPreference {

	private TimePicker timePicker;
	private TimeRange timeRange;

	private String dialogTitleBegin;
	private String dialogTitleEnd;

	private static final int PHASE_BEGIN = 0;
	private static final int PHASE_END = 1;
	private int phase = PHASE_BEGIN;

	public TimeRangePreference(Context context) {
		this(context, null);
	}

	public TimeRangePreference(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TimeRangePreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// 초기화
		timeRange = new TimeRange(context);

		// attrs 추출
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.TimeRangePreference);
		int n = ta.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = ta.getIndex(i);
			if (attr == R.styleable.TimeRangePreference_dialog_title_begin) {
				dialogTitleBegin = ta.getString(attr);
			} else if (attr == R.styleable.TimeRangePreference_dialog_title_end) {
				dialogTitleEnd = ta.getString(attr);
			}
		}
		ta.recycle();

		// 뷰 초기화
		setDialogTitle(dialogTitleBegin);
		setPositiveButtonText(android.R.string.ok);
		setNegativeButtonText(android.R.string.cancel);
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return a.getString(index);
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {

		if (restoreValue) {
			if (defaultValue == null) {
				timeRange
						.setTimeRange(getPersistedString("00:00 AM ~ 08:00 AM"));
			} else {
				timeRange
						.setTimeRange(getPersistedString((String) defaultValue));
			}
		} else {
			if (defaultValue == null) {
				timeRange.setTimeRange("00:00 AM ~ 08:00 AM");
			} else {
				timeRange.setTimeRange((String) defaultValue);
			}
		}

		setSummary(timeRange.toString());
	}

	@Override
	protected View onCreateDialogView() {
		timePicker = new TimePicker(getContext());
		return timePicker;
	}

	@Override
	protected void onBindDialogView(View v) {
		super.onBindDialogView(v);

		// phase 에 따라 다른 뷰 생성
		if (phase == PHASE_BEGIN) {
			timePicker
					.setCurrentHour(timeRange.begin.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(timeRange.begin.get(Calendar.MINUTE));
		} else if (phase == PHASE_END) {
			timePicker.setCurrentHour(timeRange.end.get(Calendar.HOUR_OF_DAY));
			timePicker.setCurrentMinute(timeRange.end.get(Calendar.MINUTE));
		} else {
			throw new RuntimeException("알 수 없는 phase");
		}
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);

		if (positiveResult) {

			// phase 에 따라 다른 calendar 에 결과 저장
			if (phase == PHASE_BEGIN) {
				// calendar 에 선택한 시간 임시 저장
				timeRange.begin.set(Calendar.HOUR_OF_DAY,
						timePicker.getCurrentHour());
				timeRange.begin.set(Calendar.MINUTE,
						timePicker.getCurrentMinute());

				// phase 변경
				phase = PHASE_END;

				// 다이얼로그 제목 변경
				setDialogTitle(dialogTitleEnd);

				// 다시 한번 PHASE_END 에 해당하는 다이얼로그 생성
				showDialog(null);

			} else if (phase == PHASE_END) {
				// calendar 에 선택한 시간 임시 저장
				timeRange.end.set(Calendar.HOUR_OF_DAY,
						timePicker.getCurrentHour());
				timeRange.end.set(Calendar.MINUTE,
						timePicker.getCurrentMinute());

				// phase 변경
				// 객체가 사라지는 건 아니므로 재활용을 위해 처음으로 돌려놓는다.
				phase = PHASE_BEGIN;

				// 다이얼로그 제목 변경
				setDialogTitle(dialogTitleBegin);

				// 임시로 저장한 두개의 calendar 를 SP 에 최종 저장 (persist)
				String newTimeRange = timeRange.toString();
				if (callChangeListener(newTimeRange)) {
					persistString(newTimeRange);
					notifyChanged();
				}

				// 결과를 summary 에 최종 표시
				setSummary(newTimeRange);

			} else {
				throw new RuntimeException("알 수 없는 phase");
			}
		}
	}
}
