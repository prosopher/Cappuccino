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

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "data")
class Request {

	// required = false : Optional element 표시
	@Element(required = false)
	private String type;

	// required = false : Optional element 표시
	@Element(required = false)
	private String endpoint;

	// required = false : Optional element 표시
	@Element(required = false)
	private Boolean cookieNeedLoaded;

	// required = false : Optional element 표시
	@Element(required = false)
	private Boolean cookieNeedSaved;

	// required = false : Optional element 표시
	@Element(required = false)
	private Boolean errorKeyAlwaysReturned;

	// required = false : Optional element 표시
	@Element(required = false)
	private String ssErrorCheckKey;

	// required = false : Optional element 표시
	@Element(required = false)
	private String ssErrorHitValue;

	// required = false : Optional element 표시
	@Element(name = "request_map", required = false)
	private RequestMap requestMap;

	// required = false : Optional element 표시
	@Element(name = "result_map", required = false)
	private ResultMap resultMap;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Boolean getCookieNeedLoaded() {
		return cookieNeedLoaded;
	}

	public void setCookieNeedLoaded(Boolean cookieNeedLoaded) {
		this.cookieNeedLoaded = cookieNeedLoaded;
	}

	public Boolean getCookieNeedSaved() {
		return cookieNeedSaved;
	}

	public void setCookieNeedSaved(Boolean cookieNeedSaved) {
		this.cookieNeedSaved = cookieNeedSaved;
	}

	public Boolean getErrorKeyAlwaysReturned() {
		return errorKeyAlwaysReturned;
	}

	public void setErrorKeyAlwaysReturned(Boolean errorKeyAlwaysReturned) {
		this.errorKeyAlwaysReturned = errorKeyAlwaysReturned;
	}

	public String getSSErrorCheckKey() {
		return ssErrorCheckKey;
	}

	public void setSSErrorCheckKey(String ssErrorCheckKey) {
		this.ssErrorCheckKey = ssErrorCheckKey;
	}

	public String getSSErrorHitValue() {
		return ssErrorHitValue;
	}

	public void setSSErrorHitValue(String ssErrorHitValue) {
		this.ssErrorHitValue = ssErrorHitValue;
	}

	public RequestMap getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(RequestMap requestMap) {
		this.requestMap = requestMap;
	}

	public ResultMap getResultMap() {
		return resultMap;
	}

	public void setResultMap(ResultMap resultMap) {
		this.resultMap = resultMap;
	}

	@Override
	public boolean equals(Object obj) {
		Request that = (Request) obj;

		boolean ret = true;

		ret = ret && Util.equals(this.type, that.type);
		ret = ret && Util.equals(this.endpoint, that.endpoint);
		ret = ret && Util.equals(this.cookieNeedLoaded, that.cookieNeedLoaded);
		ret = ret && Util.equals(this.cookieNeedSaved, that.cookieNeedSaved);
		ret = ret
				&& Util.equals(this.errorKeyAlwaysReturned,
						that.errorKeyAlwaysReturned);
		ret = ret && Util.equals(this.ssErrorCheckKey, that.ssErrorCheckKey);
		ret = ret && Util.equals(this.ssErrorHitValue, that.ssErrorHitValue);
		ret = ret && Util.equals(this.requestMap, that.requestMap);
		ret = ret && Util.equals(this.resultMap, that.resultMap);

		return ret;
	}

}
