/**
 * 
 * Copyright 2010 System Data Recorder
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
 */


package org.sdr.webrec.crawler.model;

public class Transaction {

		Workload workload; //the parent workload
	
		String method, url, id, value;
		boolean autenticate;
		
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public String toString(){
			 StringBuilder result = new StringBuilder();
			 String NEW_LINE = System.getProperty("line.separator");
			 result.append("Transaction:" + NEW_LINE);
			 	result.append(" Id: " + this.id + NEW_LINE);
			    result.append(" Method: " + this.method + NEW_LINE);
			    result.append(" URI: " + this.url + NEW_LINE);
			    result.append(" Autentication: " + this.autenticate + NEW_LINE);
			    result.append(" Value: " + this.value + NEW_LINE);
			    return result.toString();

		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public boolean isAutenticate() {
			return autenticate;
		}
		public void setAutenticate(boolean autenticate) {
			this.autenticate = autenticate;
		}
		public Workload getWorkload() {
			return workload;
		}
		public void setWorkload(Workload workload) {
			this.workload = workload;
		}
		
}
