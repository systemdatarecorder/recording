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

package org.sdr.webrec.core;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.sdr.webrec.crawler.model.Configuration;
import org.sdr.webrec.crawler.model.Workload;

public class WebRecManager extends Thread {

	private Configuration settings;
	private WebRec[] webrec;
	
	static final Logger LOGGER = Logger.getLogger("org.sdr");

	public WebRecManager(Configuration configuration) {
		this.settings = configuration;
		initialize();
	}

	private void initialize() {
		// all workloads
		LinkedList<Workload> workloads = settings.getWorkloads();
		webrec = new WebRec[workloads.size()];
		for (int i = 0; i < workloads.size(); i++) {
			org.sdr.webrec.core.WebRec tmp;
			Workload oneWorkload = (Workload) workloads.get(i);
			Object[] arrayS = oneWorkload.getTransactionsAsArray();
			
			tmp = new WebRec(settings, 
					oneWorkload.getName(), oneWorkload.getInterval(), oneWorkload.getTimeout(), oneWorkload.getDelay(), arrayS);
			webrec[i] = tmp;

		}
	}

	public void run() {
		for (int i = 0; i < webrec.length; i++) {
			LOGGER.info("Starting Thread : " + webrec[i].getThreadName());
			webrec[i].start();
		}
		for (int i = 0; i < webrec.length; i++) {
			try {
				webrec[i].join();
			} catch (InterruptedException e) {
				LOGGER.error("ERROR:" + e.getLocalizedMessage());
				e.printStackTrace();
			}
		}

		LOGGER.info("Started #" + webrec.length + " threads");
	}

	@SuppressWarnings("deprecation")
	public void finalize() {
		for (int i = 0; i < settings.getThreadNumber(); i++) {
			LOGGER.info("Stopping Thread : " + webrec[i].getThreadName());
			webrec[i].stop();
		}
	}
	
	
	public void testRound() {
		for (int i = 0; i < webrec.length; i++) {
			LOGGER.info("Starting Tests for: " + webrec[i].getThreadName());
			webrec[i].testURLS();
		}
	
		LOGGER.debug("Tested all configured Workloads");
	}


}
