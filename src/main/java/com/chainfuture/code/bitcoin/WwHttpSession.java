package com.chainfuture.code.bitcoin;


import java.io.IOException;
import java.net.URI;
import java.security.acl.LastOwnerException;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;


public class WwHttpSession
{
  private static final String JSON_CONTENT_TYPE = "application/json";
  private static final String POST_CONTENT_TYPE = "text/plain";
  private HttpClient client = null;
  private URI uri = null;
  private Credentials credentials = null;
    public static final Logger LOGGER = Logger.getLogger(WwHttpSession.class);
  
  	private String error="";
  	public String getError() {
  		if(error==null)
  			return "";
		return error.replaceAll("\"", "\\\\\"");
	}

	public void setError(String error) {
		this.error = error;
	}
  
  public WwHttpSession(URI uri, Credentials credentials)
  {
    this.uri = uri;
    this.credentials = credentials;
  }
  
  public JSONObject sendAndReceive(JSONObject message)
  {
	 
    PostMethod method = new PostMethod(this.uri.toString());
    try
    {
//      method.setRequestHeader("Content-Type", "text/plain");
        method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
      RequestEntity requestEntity = new StringRequestEntity(message.toString(), "application/json", null);
      method.setRequestEntity(requestEntity);
//      method.setRequestEntity(new StringRequestEntity(message.toString(),"application/json","UTF-8"));
        int i = getHttpClient().executeMethod(method);

        int statusCode = method.getStatusCode();
      if (statusCode != 200) {
          //throw new HttpSessionException("HTTP Status - " + HttpStatus.getStatusText(statusCode) + " (" + statusCode + ")");
    	  String ss=method.getResponseBodyAsString();
    	  setError(ss);// "HTTP Status - " + HttpStatus.getStatusText(statusCode) + " (" + statusCode + ")";
    	  
          return null;
      }
      
      JSONTokener tokener = new JSONTokener(method.getResponseBodyAsString());
      Object rawResponseMessage = tokener.nextValue();
      JSONObject response = (JSONObject)rawResponseMessage;
      if (response == null) {
          //throw new HttpSessionException("Invalid response type");
    	  setError("Invalid response type:" + method.getResponseBodyAsString());
          return null;
      }
      return response;
    }
    catch (HttpException e)
    {
        //throw new HttpSessionException(e);
    	setError(e.getMessage());
    	 return null;
    }
    catch (IOException e)
    {
    	//throw new HttpSessionException(e);
    	setError(e.getMessage());
    	 return null;
    }
    catch (JSONException e)
    {
    	//throw new HttpSessionException(e);
    	setError(e.getMessage());
    	 return null;
    }catch (Exception e)
    {
    	//throw new HttpSessionException(e);
    	setError(e.getMessage());
    	 return null;
    }
    finally
    {
      method.releaseConnection();
    }
  }
  
  private HttpClient getHttpClient()
  {
    if (this.client == null)
    {
      this.client = new HttpClient();
      this.client.getState().setCredentials(AuthScope.ANY, this.credentials);
    }
    return this.client;
  }


}
