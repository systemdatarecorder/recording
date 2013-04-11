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

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

public class Configuration {
		
		boolean keepConnectionAlive = false;
		
		
		String proxy;
		int threadNumber;
		LinkedList workloads = new LinkedList();
		
		public void addWorkLoad(Workload workload){
			workloads.add(workload);
		}
		
		
		public String getProxy() {
			return proxy;
		}
		public void setProxy(String proxy) {
			this.proxy = proxy;
		}
		public LinkedList getWorkloads() {
			return workloads;
		}
		public void setWorkloads(LinkedList workloads) {
			this.workloads = workloads;
		}

		public int getThreadNumber() {
			return workloads.size();
		}

		public void setThreadNumber(int threadNumber) {
			this.threadNumber = threadNumber;
		}

		
		public String toString(){
			 StringBuilder result = new StringBuilder();
			 String NEW_LINE = System.getProperty("line.separator");
			 result.append(NEW_LINE);
			 result.append(" KeepConnectionAlive: " + this.keepConnectionAlive + NEW_LINE);
			 result.append(" Proxy: " + proxy + NEW_LINE);
			 result.append(" # of threads: " + getThreadNumber() + NEW_LINE);
			 result.append(" Workloads: " + workloads + NEW_LINE);
				      
			    Iterator i = workloads.iterator();
			    
			    while(i.hasNext())
			    	result.append( "Workload{" + (Workload) i.next()+ "}" + NEW_LINE);
			    
			    return result.toString();

		}

		public boolean isKeepConnectionAlive() {
			return keepConnectionAlive;
		}

		public void setKeepConnectionAlive(boolean keepConnectionAlive) {
			this.keepConnectionAlive = keepConnectionAlive;
		}
		
}
	
