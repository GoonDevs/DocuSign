package com.docusign.api.types;

public class Connection {
	private String oAuthBaseUrl;
	private String baseUrl;
	private String integratorKey;
	private String userId;
	private String publicKeyFilename;
	private String privateKeyFilename;
	
	public Connection(String oAuthBaseUrl, String baseUrl, String integratorKey, String userId,	String publicKeyFilename, String privateKeyFilename) {
		this.oAuthBaseUrl = oAuthBaseUrl;
		this.baseUrl = baseUrl;
		this.integratorKey = integratorKey;
		this.userId = userId;
		this.publicKeyFilename = publicKeyFilename;
		this.privateKeyFilename = privateKeyFilename;
	}
	public Connection() {
	}
	public String getoAuthBaseUrl() {
		return oAuthBaseUrl;
	}
	public void setoAuthBaseUrl(String oAuthBaseUrl) {
		this.oAuthBaseUrl = oAuthBaseUrl;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public String getIntegratorKey() {
		return integratorKey;
	}
	public void setIntegratorKey(String integratorKey) {
		this.integratorKey = integratorKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPublicKeyFilename() {
		return publicKeyFilename;
	}
	public void setPublicKeyFilename(String publicKeyFilename) {
		this.publicKeyFilename = publicKeyFilename;
	}
	public String getPrivateKeyFilename() {
		return privateKeyFilename;
	}
	public void setPrivateKeyFilename(String privateKeyFilename) {
		this.privateKeyFilename = privateKeyFilename;
	}
	
}
