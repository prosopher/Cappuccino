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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.content.Context;

public class TimeRange {

	private java.text.DateFormat formatter;

	public Calendar begin;
	public Calendar end;

	public TimeRange(Context context) {
		this.formatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US);
		this.begin = new GregorianCalendar();
		this.end = new GregorianCalendar();
	}

	public TimeRange(Context context, String timeRange) {
		this.formatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.US);
		this.begin = new GregorianCalendar();
		this.end = new GregorianCalendar();

		setTimeRange(timeRange);
	}

	public void setTimeRange(String timeRange) {		
		int indexOfDelim = timeRange.indexOf(" ~ ");
		if (indexOfDelim == -1) {
			throw new RuntimeException("잘못된 timeRange 형식 : " + timeRange);
		}

		String timeRangeBegin = timeRange.substring(0, indexOfDelim);
		String timeRangeEnd = timeRange.substring(indexOfDelim + 3,
				timeRange.length());

		try {
			begin.setTime(formatter.parse(timeRangeBegin));
			end.setTime(formatter.parse(timeRangeEnd));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean containsCurrentTime() {
		// 현재 시간 설정
		Calendar cur = new GregorianCalendar();
		cur.setTimeInMillis(System.currentTimeMillis());

		//
		int beginH = begin.get(Calendar.HOUR_OF_DAY);
		int beginM = begin.get(Calendar.MINUTE);
		int beginMinOfDay = 60 * beginH + beginM;

		int endH = end.get(Calendar.HOUR_OF_DAY);
		int endM = end.get(Calendar.MINUTE);
		int endMinOfDay = 60 * endH + endM;

		int curH = cur.get(Calendar.HOUR_OF_DAY);
		int curM = cur.get(Calendar.MINUTE);
		int curMinOfDay = 60 * curH + curM;

		if (beginMinOfDay < endMinOfDay) {
			// 시작과 끝 범위 같은 날인 경우
			return beginMinOfDay < curMinOfDay && curMinOfDay < endMinOfDay;
		} else {
			// 시작 다음 날 끝 범위가 있는 경우
			return beginMinOfDay < curMinOfDay || curMinOfDay < endMinOfDay;
		}
	}

	@Override
	public String toString() {
		return formatter.format(begin.getTime()) + " ~ "
				+ formatter.format(end.getTime());
	}
}
