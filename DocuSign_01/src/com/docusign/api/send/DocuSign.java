package com.docusign.api.send;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class DocuSign {

	public static void main(String[] args) throws UnsupportedEncodingException {
		DocuSign doc = new DocuSign();
		doc.sendEnvelope("tmoraes@gocn.com.br", "taiwan");
	}
	
	

	public String sendEnvelope(String email, String name) throws UnsupportedEncodingException{ 


	String accountID = "84431b8a-07ea-4696-97b9-925e614b163b";
	String userName = "2741e4a7-afc9-4a2d-92b0-a4ce25412d02"; 
	String passWord = "Go-On2018#"; 
	String integratorKey = "71cf114f-b77b-478d-9337-92c49cfe6be1";
	String templateID = "1641b5f9-34be-4cd8-afa2-80815a4fc6c6"; 
	String endPoint = "https://demo.docusign.net/restapi/v2/accounts/"+accountID+"/envelopes";  
	
	HttpPost req = new HttpPost(endPoint);

	String authorizationHeader = "<DocuSignCredentials><Username>"+userName+"</Username><Password>"+passWord+"</Password><IntegratorKey>"+integratorKey+"</IntegratorKey></DocuSignCredentials>"; 
	req.setHeader("X-DocuSign-Authentication", authorizationHeader); 
	req.setHeader("Accept","application/json"); 
	req.setHeader("Content-Type","application/json"); 
	req.setHeader("Content-Disposition","form-data"); 

	Map<Object, Object> bodyMap = new HashMap<Object, Object>();
	bodyMap.put("emailSubject", "Agreement");
	bodyMap.put("emailBlurb", "emailBlurb");
	bodyMap.put("templateId", templateID);
	bodyMap.put("envelopeIdStamping", "false");
	
	ArrayList<Map<Object, Object>> list = new ArrayList<>();
	Map<Object, Object> tmap = new HashMap<Object, Object>();
	tmap.put("roleName", "Signer 1");
	tmap.put("name", name);
	tmap.put("email", email);
	tmap.put("recipientId", "1");
	
	list.add(tmap);

	bodyMap.put("templateRoles",list );	
	bodyMap.put("status", "sent");
	
	JSONObject json = new JSONObject(bodyMap);
	
	System.out.println(json.toString());
	req.setEntity(new StringEntity(json.toString()));
	
	HttpResponse res;
	StringBuilder response = new StringBuilder();
	try(CloseableHttpClient client = HttpClientBuilder.create().build()){ 
	
		res = client.execute(req);
	
		System.out.println("DocuSign Response"+ res.getEntity().getContent());  
		
		 BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
	     String line = "";
	    
	     while ((line = rd.readLine()) != null) {
	        response.append(line);        
	     }
	     System.out.println(response.toString());	
	} 	
	catch(Exception e){ 
		e.printStackTrace();	
	
	} 

	/******** The response from the Docusign is sent to wrapper class to deserialize. *********/ 

	WrapperDocuSign wrapInst = WrapperDocuSign.parse(response.toString()); 

	return wrapInst.status; 

	} 

}
