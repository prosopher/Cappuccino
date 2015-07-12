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

import java.util.Map;

import org.simpleframework.xml.ElementMap;

class VariableMap {

	// entry : 생략 시 entry
	// key = "name" : 생략 시 string
	// inline = true : 리스트 루트 태그 생략
	// attribute = true : 생략 시 key 를 element 로 생성
	// empty = false : 데이터가 비어있을 경우 null 이 아닌 빈 맵 반환
	// required = false : Optional element 표시
	@ElementMap(entry = "variable", key = "name", inline = true, attribute = true, empty = false, required = false)
	private Map<String, String> variableMap;

	public void setVariableMap(Map<String, String> variableMap) {
		this.variableMap = variableMap;
	}

	public Map<String, String> getVariableMap() {
		return variableMap;
	}

	@Override
	public boolean equals(Object obj) {
		VariableMap that = (VariableMap) obj;

		boolean ret = true;

		ret = ret && Util.equals(this.variableMap, that.variableMap);

		return ret;
	}

}
