package com.docusign.api.send;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WrapperDocuSign {

	public String envelopeId; 
	public String uri; 
	public String statusDateTime; 
	public String status; 
	public static WrapperDocuSign parse(String json) { 
		ObjectMapper mapper = new ObjectMapper();
		 WrapperDocuSign wr = null;
		 try {
			 wr = mapper.readValue(json, WrapperDocuSign.class);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return wr;
	
		} 
}
