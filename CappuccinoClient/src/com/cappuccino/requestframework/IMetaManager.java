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
package com.cappuccino.requestframework;

public interface IMetaManager {

	public abstract String getCommonSs(String csKey);

	public abstract String getType(String requestName);

	public abstract String getEndpoint(String requestName);

	public abstract boolean doesCookieNeedLoaded(String requestName);

	public abstract boolean doesCookieNeedSaved(String requestName);

	public abstract boolean isErrorKeyAlwaysReturned(String requestName);

	public abstract String getSSErrorCheckKey(String requestName);

	public abstract String getSSErrorHitValue(String requestName);

	public abstract String getRequestSs(String requestName, String csKey);

	public abstract String getResultSs(String requestName, String csKey);

}
