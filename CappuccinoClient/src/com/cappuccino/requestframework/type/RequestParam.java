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
package com.cappuccino.requestframework.type;

public class RequestParam {

	// cs : client side
	// ss : server side
	private String csVariableKey;
	private String ssVariableKey;
	private String value;

	public RequestParam(String csVariableKey, String ssVariableKey, String value) {

		this.csVariableKey = csVariableKey;
		this.ssVariableKey = ssVariableKey;
		this.value = value;
	}

	public String getCSVariableKey() {
		return csVariableKey;
	}

	public String getSSVariableKey() {
		return ssVariableKey;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		String ret = csVariableKey + " ( " + ssVariableKey + " ) : " + value;
		return ret;
	}
}
