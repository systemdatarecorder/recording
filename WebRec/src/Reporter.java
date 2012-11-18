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


import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Map;

import java.io.FileReader;

import com.twicom.qdparser.DocHandler;
import com.twicom.qdparser.QDParser;
import com.twicom.qdparser.XMLParseException;

/** 
  This class is the most basic possible
  implementation of the DocHandler class.
  It simply reports all events to System.out
  as they occur.
*/
public class Reporter implements DocHandler {

  // We only instantiate one copy of the DocHandler
  static Reporter reporter = new Reporter();

  // Implementation of DocHandler is below this line
  public void startDocument() {
    System.out.println("  start document");
  }
  public void endDocument() {
    System.out.println("  end document");
  }
  public void startElement(String elem,Hashtable h) {
    System.out.println("    start elem: "+elem);
    Enumeration e = h.keys();
    while(e.hasMoreElements()) {
      String key = (String)e.nextElement();
      String val = (String)h.get(key);
      System.out.println("      "+key+" = "+val);
    }
  }
  public void endElement(String elem) {
    System.out.println("    end elem: "+elem);
  }
  public void text(String text) {
    System.out.println("        text: "+text);
  }
  // implementation of DocHandler is above this line

  /** Usage: java Reporter [xml file(s)] */
  public static void main(String[] args) throws Exception {
    for(int i=0;i<args.length;i++)
      reportOnFile(args[0]);
  }

  public static void reportOnFile(String file) throws Exception {
   
    // This is all the code we need to parse
    // a document with our DocHandler.
    FileReader fr = new FileReader(file);
    QDParser.parse(reporter,fr);

    fr.close();
  }
  
public void endElement(String arg0, String arg1) throws XMLParseException {
	// TODO Auto-generated method stub
	
}

public void startElement(String arg0, String arg1, Map<String, String> arg2,
		int arg3, int arg4) throws XMLParseException {
	// TODO Auto-generated method stub
	
}

public void text(String arg0, int arg1, int arg2) throws XMLParseException {
	// TODO Auto-generated method stub
	
}

public void text(String arg0, boolean arg1, int arg2, int arg3)
		throws XMLParseException {
	// TODO Auto-generated method stub
	
}
}
