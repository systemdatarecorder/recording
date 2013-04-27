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


public class Workload {

			String name;
			int delay;
			int interval; //in seconds
			String username, password;
			
			
			/**
			 * Determines the timeout until a connection is etablished. A value of zero 
			 * means the timeout is not used. The default value is zero.
			 */
			int timeout;  //in milliseconds
			
			LinkedList<Transaction> transactions = new LinkedList<Transaction>();
			
			
			public void addTransaction(Transaction transaction){
				transactions.add(transaction);
			}
			
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public LinkedList<Transaction> getTransactions() {
				return transactions;
			}
			
			public Object[] getTransactionsAsArray() {
				return transactions.toArray();
			}
			
			public void setTransactions(LinkedList<Transaction> transactions) {
				this.transactions = transactions;
			}
			public int getDelay() {
				return delay;
			}
			public void setDelay(int delay) {
				this.delay = delay;
			}
			
			public String toString(){
				 StringBuilder result = new StringBuilder();
				 String NEW_LINE = System.getProperty("line.separator");
				 result.append("Workload:" + NEW_LINE);
				    result.append(" Name: " + this.name + NEW_LINE);
				    result.append(" Delay: " + this.delay + NEW_LINE);
				    result.append(" Timeout: " + this.timeout + NEW_LINE);
				    result.append(" Interval: " + this.interval + NEW_LINE);
				    result.append(" Username: " + this.username + NEW_LINE);
				    result.append(" Transactions: " + NEW_LINE);
				    
				    Iterator<Transaction> i = transactions.iterator();
				    
				    while(i.hasNext())
				    	result.append( "Transaction{" + (Transaction) i.next()+ "}" + NEW_LINE);
				    
				    return result.toString();

			}

			public int getTimeout() {
				return timeout;
			}

			public void setTimeout(int timeout) {
				this.timeout = timeout;
			}
			
			public int getInterval() {
				return interval;
			}

			public void setInterval(int interval) {
				this.interval = interval;
			}

			public String getUsername() {
				return username;
			}

			public void setUsername(String username) {
				this.username = username;
			}

			public String getPassword() {
				return password;
			}

			public void setPassword(String password) {
				this.password = password;
			}
			
}
	

