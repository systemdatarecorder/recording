package org.sdr.webrec.core;


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



import java.io.BufferedWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.sdr.webrec.crawler.model.Configuration;
import org.sdr.webrec.crawler.model.Transaction;

import java.io.OutputStreamWriter;


import org.apache.log4j.Logger;



public class WebRec extends Thread {


	private Object[] url;
	private int delay; // in milliseconds
	private String threadName;
	private int timeout; // in milliseconds
	private int interval; // in milliseconds
	BufferedWriter out;
	Configuration settings;
	long t1, t2, tTask;
	
	// Create HTTP parameters
    HttpParams params; 
    SchemeRegistry schemeRegistry;
    ThreadSafeClientConnManager cm;
    HttpGet httpget;
    DefaultHttpClient  httpclient;
    RequestConfig requestConfig;
    
    
	static final Logger  LOGGER = Logger.getLogger("org.sdr");
	   
	public WebRec(Configuration settings, String name, int interval, int timeout, int delay, Object[] urls) {
		super(name);
		this.settings = settings;
		this.threadName = name;
		this.timeout = timeout;
		this.interval = interval * 1000;
		this.url = urls;
		this.delay = delay * 1000;
		try {
			out = new BufferedWriter(new OutputStreamWriter(System.out));
		} catch (Exception e) {
			LOGGER.error("ERROR:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	public void run() {


		initParameters();
	
		String transactionName = "";
	    HttpResponse response = null;
	    
	    DecimalFormat formatter = new DecimalFormat("#####0.00");
        DecimalFormatSymbols dfs = formatter.getDecimalFormatSymbols();
        formatter.setMinimumFractionDigits(3);
		formatter.setMaximumFractionDigits(3);
        
        
        // make sure delimeter is a '.' instead of locale default ','
        dfs.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dfs);

		do {
			t1=System.currentTimeMillis();
			URL siteURL = null;
			try {

				for (int i = 0; i < url.length; i++) {

					
					LOGGER.debug("url:" + ((Transaction) url[i]).getUrl());

					Transaction transaction = (Transaction) url[i];
					siteURL = new URL(transaction.getUrl());
					transactionName = transaction.getName();

					//if transaction requires server authentication
					if(transaction.isAutenticate())
						httpclient.getCredentialsProvider().setCredentials(
			                	new AuthScope(siteURL.getHost(), siteURL.getPort()), 
			                	new UsernamePasswordCredentials(transaction.getWorkload().getUsername(), transaction.getWorkload().getPassword()));

					
					// Get HTTP GET method
					httpget = new HttpGet(((Transaction) url[i]).getUrl());

					double startTime = System.nanoTime();
					
					double endTime = 0.00d;
					
					response = null;

					// Execute HTTP GET
					try{
				        response = httpclient.execute(httpget);
						endTime = System.nanoTime();
						
					} catch (Exception e){
						httpget.abort();
						LOGGER.error("ERROR in receiving response:" + e.getLocalizedMessage());
						e.printStackTrace();
					}
	                
					double timeLapse = 0;
					
					if (response != null){
						if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
					
							timeLapse = endTime - startTime;
							
							LOGGER.debug("starttime:" + (new Double (startTime)).toString());
							LOGGER.debug("timeLapse:" + endTime);
							LOGGER.debug("timeLapse:" + timeLapse);
							
					
						
							//move nanos to millis
							timeLapse = timeLapse / 1000000L;
							LOGGER.debug("response time:" + formatter.format(timeLapse) + "ms.");
							out.write(System.currentTimeMillis()/1000 + ":");
							out.write( threadName + '.' + transactionName + ":" + formatter.format(timeLapse) +"\n");


							//content must be consumed just because 
							//otherwice apache httpconnectionmanager does not release connection back to pool
							HttpEntity entity = response.getEntity();
							if (entity != null) {
								EntityUtils.toByteArray(entity);
							}
					
						} else {
							LOGGER.error("Status code of transaction:" + transactionName +" was not "+ HttpURLConnection.HTTP_OK+" but " + response.getStatusLine().getStatusCode());
						}
						
					}
					int sleepTime = delay;
					try {
						LOGGER.debug("Sleeping " + delay / 1000 + "s...");
						sleep(sleepTime);
					} catch (InterruptedException ie) {
						LOGGER.error("ERROR:" + ie);
						ie.printStackTrace();
					}
				}
			} catch (Exception e) {
				LOGGER.error("Error in thread " + threadName +  " with url:" + siteURL+ " " + e.getMessage());
				e.printStackTrace();
			}
			try {
				out.flush();
				t2=System.currentTimeMillis();
				tTask=t2-t1;

				LOGGER.debug("Total time consumed:" + tTask / 1000 + "s.");
				
				if(tTask <= interval){
					 //when task takes less than preset time
					LOGGER.debug("Sleeping interval:" + (interval-tTask) / 1000 + "s.");
					sleep(interval-tTask);
				}
				
								
				cm.closeExpiredConnections();
			} catch (InterruptedException ie) {
				LOGGER.error("Error:" + ie);
				ie.printStackTrace();
			} catch (Exception e) {
				LOGGER.error("Error:" + e);
				e.printStackTrace();
			}
		} while (true);
	}

	/**
	 * Setter for property url.
	 * 
	 * @param url
	 *            New value of property url.
	 */
	public void setUrl(String[] url) {
		this.url = url;
	}

	/**
	 * Getter for property delay.
	 * 
	 * @return Value of property delay.
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Setter for property delay.
	 * 
	 * @param delay New value of property delay.
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(java.lang.String threadName) {
		this.threadName = threadName;
	}

	
	
	private void initParameters(){
		

		// initialize HTTP parameters
       params = new BasicHttpParams();
       ConnManagerParams.setMaxTotalConnections(params, 100);
       HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
     
       httpclient = new DefaultHttpClient(cm, params);

       //TODO: CloseableHttpClient httpclient = HttpClients.createDefault();
       
       
       //set proxy if available in settings
       if(settings.getProxyHost()!=null && settings.getProxyHost().length()> 0){
        	HttpHost proxy = new HttpHost(settings.getProxyHost(), settings.getProxyPort());
        	httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy); 
        		
        	//set authentication to proxy is available is settings
        	if(settings.getProxyUserName()!=null && settings.getProxyUserName().length()> 0
        			&& settings.getProxyPasswd() != null){
        		httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(settings.getProxyHost(), settings.getProxyPort()),
                    new UsernamePasswordCredentials(settings.getProxyUserName(), settings.getProxyPasswd()));
        		LOGGER.debug("autentication for proxy on");
        	}
      
       }
        
        
        // initialize scheme registry 
        schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        
		// Create an HttpClient with the ThreadSafeClientConnManager.
        // This connection manager must be used if more than one thread will
        // be using the HttpClient.
        cm = new ThreadSafeClientConnManager (params, schemeRegistry);
		
        httpclient.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(
                        response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName(); 
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value);
                        } catch(NumberFormatException ignore) {
                        	
                        }
                    }
                }
                    //otherwise keep alive for 30 seconds
                    return 30 * 1000;  
            }

			
            
        });

  
		httpget = null;
		
		httpclient = new DefaultHttpClient(cm, params);	
			
		
		 // Create global request configuration
        RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.BEST_MATCH)
            .setExpectContinueEnabled(true)
            .setStaleConnectionCheckEnabled(settings.isKeepConnectionAlive())
            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
            .build();
		
		 requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .build();
		
            
		
	}
	
	
	public void testURLS() {

		initParameters();

        String transactionName = "";
        HttpResponse response = null;
        URL siteURL = null;
		
        
        try {
        	
        	for (int i = 0; i < url.length; i++) {
				
				Transaction transaction = (Transaction) url[i];
				siteURL = new URL(transaction.getUrl());
				transactionName = transaction.getName();

				//if transaction requires server authentication
				if(transaction.isAutenticate())
					httpclient.getCredentialsProvider().setCredentials(
		                	new AuthScope(siteURL.getHost(), siteURL.getPort()), 
		                	new UsernamePasswordCredentials(transaction.getWorkload().getUsername(), transaction.getWorkload().getPassword()));

					
				
				
				// Get HTTP GET method
				httpget = new HttpGet(((Transaction) url[i]).getUrl());
				
				httpget.setConfig(requestConfig);
				
				response = null;
				// Execute HTTP GET

					
				response = httpclient.execute(httpget);
				
			
				if (response != null){
					if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
						LOGGER.debug("HTTP Status code:" + HttpURLConnection.HTTP_OK + " for " + siteURL.toURI());
					}	
					if(response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
						LOGGER.error("HTTP response for transaction:" + transactionName + ", " + siteURL.toURI()+" was faulty. It was:" + response.getStatusLine().getStatusCode());
						LOGGER.error("WebRec will not start. Please fix the above errors.");
						System.exit(-1);
					}
					//content must be consumed just because 
					//otherwice apache httpconnectionmanager does not release connection back to pool
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						EntityUtils.toByteArray(entity);
					}
				
				}
					
				}
			} catch (Exception e) {
				LOGGER.error("Error in testRun " + threadName +  " with url:" + siteURL+ " " + e.getMessage());
				LOGGER.error("WebRec will exit. Please correct above errors.");
				e.printStackTrace();
				System.exit(-1);
			
			}
	}//testURLs

	
	
	
	
}
