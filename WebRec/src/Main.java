/**
 * 
 * Copyright 2012 System Data Recorder
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.sdr.webrec.core.WebRecManager;
import org.sdr.webrec.crawler.model.Configuration;
import org.sdr.webrec.crawler.model.Workload;

public class Main {

	private static Hashtable<String, String> workloadsParameters = new Hashtable<String, String>();
	static Configuration confi = new Configuration();
	static String logs_dir = "log/current/";
	
	static Logger  logger = Logger.getLogger("org.sdr");


	public static void main(String[] args) {

		String VERSION_INFO = "webrec 0.74.2";
        String USAGE = "Usage: webrec workload1 ... workloadN";
 
        Date now = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter;
        String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
        formatter = new SimpleDateFormat(pattern);

        
        
        System.out.println(formatter.format(now) + " Starting...");
		System.out.println(VERSION_INFO);
		
		
		boolean spiderMode = false; //currently not implemented
	
		// get command line parameters
		for (int i = 0; i < args.length; i++) {
			if ("-s".equals(args[i])) {  // -s option: spider mode
				spiderMode = true;
				System.out.println("Currently spidering option is not supported");
				System.exit(0);
			} else  if (args[i].startsWith("-h") || args[i].startsWith("-help")) {  // -h option: list help
                            System.out.println(USAGE);
			    System.exit(0);
			} else if (  (args[i].startsWith("-") && !args[i].startsWith("h", 1)) ||
				     (args[i].startsWith("-") && !args[i].startsWith("s", 1)) ){
			    System.out.println(USAGE);
			    System.exit(0);
			} else { // name of workload
				workloadsParameters.put(args[i], args[i]);
			}
		}

		try {
			readProperties(workloadsParameters);
		} catch (Exception e) {
			logger.error("ERROR:" + e.getLocalizedMessage());
			e.printStackTrace();
		}

		if (workloadsParameters.size() < 1) {
                        System.out.println(USAGE);
			System.exit(1);
		}
		
		WebRecManager manager = new WebRecManager(confi);

		//perform a test round for all URLS. If some doesn't work (HTTP OK CODE 200), 
		//then exit program
		manager.testRound();
		manager.start();

	}

			

	private static void readProperties(Hashtable<String, String> parameters)
			throws Exception {

		// Read properties file.
		
		try {

			String home = "/opt/sdr/";
			
			if( System.getProperty("sdr.home")!= null)
				if( System.getProperty("sdr.home").length() > 1)
					home = System.getProperty("sdr.home");
			
			if(!home.endsWith("/"))
				home = home + "/";
			
			
			
			
			Conf conf = new Conf();
			conf.setParameters(parameters);
			confi = conf.readConfiguration(home, conf);

			checkLogFile(home);

			
			confi.setLogDirectory(home + logs_dir);
			logger.debug("SDR home dir:" + home);
			logger.debug("SDR log dir:" + confi.getLogDirectory());
			logger.debug("SDR conf dir:" + home + "etc/webrec.conf");
			
			
		} catch (IOException e) {
			logger.error("ERROR:" + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}

	private static void checkLogFile(String homeDirectory) {

		String directoryForLogs = homeDirectory + logs_dir;
		
		// all workloads
		LinkedList<Workload> workloads = confi.getWorkloads();
		for (int i = 0; i < workloads.size(); i++) {
            Workload oneWorkload = workloads.get(i);
			// every workload has it's own log file
			File file = new File(directoryForLogs + confi.getLogfile_prefix()
					+ oneWorkload.getName() + confi.getLogfile_postfix());

			boolean exists = file.exists();
			if (!exists) {
				// It returns false if File or directory does not exist
				
				// Create file if it does not exist
				try {
					file.createNewFile();
					confi.setLogFile(file);
					logger.info("Set to log file:" + confi.getLogFile());
				} catch (IOException e) {
					logger.error("Error creating a log file." + file.getAbsolutePath());
					System.exit(-1);
					e.printStackTrace();
				}

			} 

		}
		
		
	}
}
