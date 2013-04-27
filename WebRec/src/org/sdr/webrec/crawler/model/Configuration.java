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

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.codec.binary.StringUtils;

public class Configuration {
		
		boolean keepConnectionAlive = false;
		
		
		String proxyHost;
		int proxyPort;
		String proxyPasswd;
		String proxyUserName;
		boolean proxyPasswdEncrypted;
		
		
		
		public String getProxyHost() {
			return proxyHost;
		}


		public void setProxyHost(String proxyHost) {
			this.proxyHost = proxyHost;
		}


		public int getProxyPort() {
			return proxyPort;
		}


		public void setProxyPort(int proxyPort) {
			this.proxyPort = proxyPort;
		}


		public String getProxyPasswd() {
			return proxyPasswd;
		}


		public void setProxyPasswd(String proxyPasswd) {
			this.proxyPasswd = proxyPasswd;
		}


		public String getProxyUserName() {
			return proxyUserName;
		}


		public void setProxyUserName(String proxyUserName) {
			this.proxyUserName = proxyUserName;
		}


		public boolean isProxyPasswdEncrypted() {
			return proxyPasswdEncrypted;
		}


		public void setProxyPasswdEncrypted(boolean proxyPasswdEncrypted) {
			this.proxyPasswdEncrypted = proxyPasswdEncrypted;
		}

		int threadNumber;
		LinkedList<Workload> workloads = new LinkedList<Workload>();
		
		public void addWorkLoad(Workload workload){
			workloads.add(workload);
		}
		
		
		
		public LinkedList<Workload> getWorkloads() {
			return workloads;
		}
		public void setWorkloads(LinkedList<Workload> workloads) {
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
			 result.append(" Proxy: " + proxyHost +":" + proxyPort + NEW_LINE);
			 result.append(" # of threads: " + getThreadNumber() + NEW_LINE);
			 result.append(" Workloads: " + workloads + NEW_LINE);
				      
			    Iterator<Workload> i = workloads.iterator();
			    
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
	
