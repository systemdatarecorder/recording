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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;
import org.sdr.webrec.crawler.model.Configuration;
import org.sdr.webrec.crawler.model.Transaction;
import org.sdr.webrec.crawler.model.Workload;

import com.twicom.qdparser.DocHandler;
import com.twicom.qdparser.QDParser;
import com.twicom.qdparser.XMLParseException;

/**
 * Parse a WebRec XML configuration file.
 * 
 * A Quick & Dirty parser is used to avoid dependency to XML APIs. This will not
 * allow any validation or advanced XML feature.
 */

public class Conf implements DocHandler {

	private Workload workload;

	private Configuration configuration = new Configuration();

	private Hashtable parameters;
	

	/* fields */
	private Stack nodes; // this should be FIFO
	private boolean mode = false;

	// the set of data to get for the node..
	private DefaultMutableTreeNode current = null;

	public Conf() {
		nodes = new Stack();
	}

	// The stack is used to get back to where we were
	// when we hit an endElement()
	Stack stack;

	// the object we are currently processing
	Object model = new Configuration();

	Logger  logger = Logger.getLogger("org.sdr");
	
	public Configuration readConfiguration(String home, Conf conf)
			throws FileNotFoundException, Exception {
		File file = new File(home + "etc/webrec.conf");

		boolean exists = file.exists();
		if (!exists) {
			System.err.println("ERROR: no webrec.conf found!");
			System.exit(-1);

		}
		QDParser.parse(conf, new FileReader(home + "etc/webrec.conf"));

		logger.debug("Configuration:" + configuration);

		return configuration;
	}

	// we aren't really doing anything with this in this
	// example.
	public void text(String s) {
	}

	public void startElement(String tag, Map h) {

		if (stack.empty()) {
			stack.push(model);
		} else {
			stack.push(model);
		}

		if (tag.equals("logfile")) {
			String prefix = (String) h.get("prefix");
			String postfix = (String) h.get("postfix");
			configuration.setLogfile_prefix(prefix);
			configuration.setLogfile_postfix(postfix);
		} else if (tag.equals("keepHttpAlive")) {
			String keepAlive = (String) h.get("value");
			configuration.setKeepConnectionAlive((new Boolean(keepAlive)
					.booleanValue()));
		} else if (tag.equals("workload")) {
			String name = (String) h.get("name");
			String delay = (String) h.get("delay");
			String interval = (String) h.get("interval");
			String timeout = "0";
			if(h.get("timeout") != null)
				timeout = (String) h.get("timeout");
			String username = "";
			if(h.get("username") != null)
				username = (String) h.get("username");
			String password = "";
			if(h.get("password") != null)
				password = (String) h.get("password");
			this.mode = false;
			if (parameters.contains(name)) {
				this.mode = true;
				workload = new Workload();
				workload.setName(name);
				workload.setInterval((new Integer(interval).intValue()));
				workload.setDelay((new Integer(delay).intValue()));
				workload.setTimeout((new Integer(timeout).intValue()));
				workload.setUsername(username);
				workload.setPassword(password);
				nodes.push(workload);
				configuration.addWorkLoad(workload);
			}
		} else if (tag.equals("transaction")) {
			if (this.mode) {
				String url = (String) h.get("url");
				String id = (String) h.get("id");
				boolean autentication = false;
				if(h.get("autenticate") != null){
					String value = (String)h.get("autenticate");
					if(value != null)
						autentication = Boolean.valueOf(value);
				}
				Transaction transaction = new Transaction();
				transaction.setUrl(url);
				transaction.setId(id);
				transaction.setAutenticate(autentication);
				//set the parent workload
				transaction.setWorkload(this.workload);
				nodes.push(transaction);
				workload.addTransaction(transaction);
			}
		}
	}

	// return to the previous object.
	public void endElement(String name) {
		model = stack.pop();
	}

	public void startDocument() {
		stack = new Stack();
	}

	public void endDocument() {
		stack = null;
	}

	/**
	 * Convert an xml-style name to a java-style name. Example: primary-weapon
	 * becomes primaryWeapon.
	 */
	public String jname(String s) {
		boolean ucase = false;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '-') {
				ucase = true;
			} else if (ucase) {
				sb.append(Character.toUpperCase(c));
				ucase = false;
			} else {
				sb.append(Character.toLowerCase(c));
			}
		}
		return sb.toString();
	}

	public Hashtable getParameters() {
		return parameters;
	}

	public void setParameters(Hashtable parameters) {
		this.parameters = parameters;
	}

	public void endElement(String arg0, String arg1) throws XMLParseException {

	}

	public void startElement(String arg0, String arg1,
			Map<String, String> arg2, int arg3, int arg4)
			throws XMLParseException {
		startElement(arg1, arg2);
	}

	public void text(String arg0, int arg1, int arg2) throws XMLParseException {
		// TODO Auto-generated method stub

	}

	public void text(String arg0, boolean arg1, int arg2, int arg3)
			throws XMLParseException {
		// TODO Auto-generated method stub

	}

}
