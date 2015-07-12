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
package com.cappuccino.requestframework.xml;

class Util {

	public static boolean equals(Object a, Object b) {

		if (a == null && b == null) {
			// 둘 다 null 일 때 어차피 같으므로 일치
			return true;
		} else if (a != null && b != null) {
			// 둘 다 null 이 아닐 때 검사
			return a.equals(b);
		} else {
			// 둘 중 하나가 null 일 때 불일치
			return false;
		}
	}

}
