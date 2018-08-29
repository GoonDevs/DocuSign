package com.docusign.api.types;

public class eSigner {
	private String signerEmail;
	private String signerName;
	private String signerRecipientId;
	private String signerRoutingOrder;
	private String signerRoleName;

	private String signerHeretabLabel;
	private String signerHereanchorString;
	
	public eSigner(String signerEmail, String signerName, String signerRecipientId, String signerRoutingOrder,
			String signerRoleName, String recipientId, String tabLabel, String anchorString) {
		super();
		this.signerEmail = signerEmail;
		this.signerName = signerName;
		this.signerRecipientId = signerRecipientId;
		this.signerRoutingOrder = signerRoutingOrder;
		this.signerRoleName = signerRoleName;
		this.signerHeretabLabel = tabLabel;
		this.signerHereanchorString =  anchorString;
	}
	public eSigner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSignerEmail() {
		return signerEmail;
	}
	public void setSignerEmail(String signerEmail) {
		this.signerEmail = signerEmail;
	}
	public String getSignerName() {
		return signerName;
	}
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}
	public String getSignerRecipientId() {
		return signerRecipientId;
	}
	public void setSignerRecipientId(String signerRecipientId) {
		this.signerRecipientId = signerRecipientId;
	}
	public String getSignerRoutingOrder() {
		return signerRoutingOrder;
	}
	public void setSignerRoutingOrder(String signerRoutingOrder) {
		this.signerRoutingOrder = signerRoutingOrder;
	}
	public String getSignerRoleName() {
		return signerRoleName;
	}
	public void setSignerRoleName(String signerRoleName) {
		this.signerRoleName = signerRoleName;
	}
	public String getSignerHeretabLabel() {
		return signerHeretabLabel;
	}
	public void setSignerHeretabLabe(String signerHeretabLabel) {
		this.signerHeretabLabel = signerHeretabLabel;
	}
	public String getsignerHereAnchorString() {
		return signerHereanchorString;
	}
	public void setsignerHereAnchorString(String signerHereanchorString) {
		this.signerHereanchorString = signerHereanchorString;
	}	
		
}
