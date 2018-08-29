package com.docusign.api.send;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.docusign.api.types.Connection;
import com.docusign.api.types.eSigner;
import com.docusign.esign.api.*;
import com.docusign.esign.client.*;
import com.docusign.esign.model.*;
import com.docusign.esign.client.auth.OAuth.UserInfo;

public class DocuSignExample {
	public static void main(String[] args) throws IOException{
		DocuSignExample ds = new DocuSignExample();
		
		String baseUrl = "https://demo.docusign.net/restapi";
		String integratorKey = "71cf114f-b77b-478d-9337-92c49cfe6be1";
		String oAuthBaseUrl = "account-d.docusign.com";
		String privateKeyFilename = "C:\\Users\\tmoraes\\Desktop\\DocuSign\\privateKey.pem";
		String publicKeyFilename = "C:\\Users\\tmoraes\\Desktop\\DocuSign\\publicKey.pem";
		String userId = "2741e4a7-afc9-4a2d-92b0-a4ce25412d02";
		
		Connection connection = new Connection();
		connection.setBaseUrl(baseUrl);
		connection.setIntegratorKey(integratorKey);
		connection.setoAuthBaseUrl(oAuthBaseUrl);
		connection.setPrivateKeyFilename(privateKeyFilename);
		connection.setPublicKeyFilename(publicKeyFilename);
		connection.setUserId(userId);
		
		String signerHereAnchorString = "@@_Sign01_@@";
		String signerEmail = "tmoraes@gocn.com.br";
		String signerName = "Taiwan Moraes";
		String signerRecipientId = "1";
		String signerRoleName = "Role 01";
		String signerRoutingOrder = "1";
		String signerHeretabLabel = "label";
		
		eSigner esigner = new eSigner();
		esigner.setsignerHereAnchorString(signerHereAnchorString);
		esigner.setSignerEmail(signerEmail);
		esigner.setSignerName(signerName);
		esigner.setSignerRecipientId(signerRecipientId);
		esigner.setSignerRoleName(signerRoleName);
		esigner.setSignerRoutingOrder(signerRoutingOrder);
		esigner.setSignerHeretabLabe(signerHeretabLabel);
		
		String signerHereAnchorString2 = "@@_Sign02_@@";
		String signerEmail2= "taiwan.moraes@outlook.com";
		String signerName2 = "Taiwan_Moraes_id02";
		String signerRecipientId2 = "2";
		String signerRoleName2 = "Role 02";
		String signerRoutingOrder2 = "2";
		String signerHeretabLabel2 = "label2";
		
		eSigner esigner2 = new eSigner();
		esigner2.setsignerHereAnchorString(signerHereAnchorString2);
		esigner2.setSignerEmail(signerEmail2);
		esigner2.setSignerName(signerName2);
		esigner2.setSignerRecipientId(signerRecipientId2);
		esigner2.setSignerRoleName(signerRoleName2);
		esigner2.setSignerRoutingOrder(signerRoutingOrder2);
		esigner2.setSignerHeretabLabe(signerHeretabLabel2);
		
		List<eSigner> eSignerList = new ArrayList<>();
		eSignerList.add(esigner);
		eSignerList.add(esigner2);
		
		String pathToDoc = "C:\\Users\\tmoraes\\Desktop\\DocuSign\\DECLARACAO_CASA.pdf";
		
		String ret = ds.SendEnvelope(connection, eSignerList, pathToDoc);
		
		System.out.println(ret);
	}
	public String SendEnvelope(Connection connection, List<eSigner> eSignerList, String pathToDoc) throws IOException
	{
		System.setProperty("http.proxyHost", "web.prod.proxy.cargill.com");
		System.setProperty("http.proxyPort", "4200");
		System.setProperty("https.proxyHost", "web.prod.proxy.cargill.com");
		System.setProperty("https.proxyPort", "4200");
		
		final String authUser = "T509288";
		final String authPassword = "Cargil1807";
		Authenticator.setDefault(
		   new Authenticator() {
		      @Override
		      public PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication(
		               authUser, authPassword.toCharArray());
		      }
		   }
		);
		System.setProperty("http.proxyUser", authUser);
		System.setProperty("http.proxyPassword", authPassword);	

		String oAuthBaseUrl = connection.getoAuthBaseUrl();
		String baseUrl = connection.getBaseUrl();
		String integratorKey = connection.getIntegratorKey();
		String userId = connection.getUserId();
		String publicKeyFilename = connection.getPublicKeyFilename();
		String privateKeyFilename = connection.getPrivateKeyFilename();
		String pathToDocument = pathToDoc;
		
		ApiClient apiClient = new ApiClient(baseUrl);

		try {
			apiClient.configureJWTAuthorizationFlow(publicKeyFilename, privateKeyFilename, oAuthBaseUrl, integratorKey, userId, 3600); // request for a fresh JWT token valid for 1 hour
			UserInfo userInfo = apiClient.getUserInfo(apiClient.getAccessToken());
			apiClient.setBasePath(userInfo.getAccounts().get(0).getBaseUri() + "/restapi");
			Configuration.setDefaultApiClient(apiClient);
			String accountId = userInfo.getAccounts().get(0).getAccountId();

			byte[] fileBytes = null;			 
			try { 
				Path path = Paths.get(pathToDocument); 
				fileBytes = Files.readAllBytes(path); 
			} catch (IOException e) { 
				e.printStackTrace();
				return ("Exception: " + e);
			} 
			
			// create an envelope that will store the document(s), field(s), and recipient(s) 
			EnvelopeDefinition envDef = new EnvelopeDefinition(); 
			envDef.setEmailSubject("Please sign this document sent from Java SDK)"); 
			
			// add a document to the envelope 
			Document doc = new Document(); 
			String base64Doc = Base64.getEncoder().encodeToString(fileBytes); 
			doc.setDocumentBase64(base64Doc); 
			doc.setName("TestFile"); 
			
			// can be different from actual file name 
			doc.setFileExtension(".pdf"); 
			
			// update if different extension! 
			doc.setDocumentId("1"); 
			
			CompositeTemplate tCompositeTemplate = new CompositeTemplate();
			tCompositeTemplate.setDocument(doc);
			tCompositeTemplate.getDocument().setTransformPdfFields("true");
			tCompositeTemplate.getDocument().setFileExtension("pdf");
			
			Recipients recipients = new Recipients();
			List<Signer> signers = new ArrayList<Signer>();
			List<Signer> t_signers = new ArrayList<Signer>();
			
			//------------------------------------------------------//
			for (eSigner esigner : eSignerList) {
				
				Signer signerApp = new Signer();
				signerApp.setEmail(esigner.getSignerEmail());
				signerApp.setName(esigner.getSignerName());
				signerApp.setRecipientId(esigner.getSignerRecipientId());
				signerApp.setRoutingOrder(esigner.getSignerRoutingOrder());
				signerApp.setRoleName(esigner.getSignerRoleName());
								
				SignHere signHereApp = new SignHere();
				signHereApp.setDocumentId(tCompositeTemplate.getDocument().getDocumentId());
				signHereApp.setRecipientId(esigner.getSignerRecipientId());
				signHereApp.setTabLabel(esigner.getSignerHeretabLabel());
				signHereApp.anchorString(esigner.getsignerHereAnchorString());
				signHereApp.anchorXOffset("0");
				
				List<SignHere> signHereTabs = new ArrayList<SignHere>(); 
				signHereTabs.add(signHereApp);
				Tabs tabs = new Tabs();
				tabs.setSignHereTabs(signHereTabs);				
				signerApp.setTabs(tabs);
				signers.add(signerApp);
			}
			//------------------------------------------------------//
			
			recipients.setSigners(signers);

			InlineTemplate inlineTemplate = new InlineTemplate();
			inlineTemplate.setRecipients(recipients);
			inlineTemplate.setSequence("2");// era 2, pq ???

			List<InlineTemplate> inlineTemplateList = new ArrayList<InlineTemplate>();
			inlineTemplateList.add(inlineTemplate);
			tCompositeTemplate.setInlineTemplates(inlineTemplateList);

			List<CompositeTemplate> compositeTemplateList = new ArrayList<CompositeTemplate>();
			compositeTemplateList.add(tCompositeTemplate);
			envDef.setCompositeTemplates(compositeTemplateList);
			envDef.setStatus("sent");

			EnvelopesApi envelopesApi = new EnvelopesApi();
			EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);	

			return envelopeSummary.getStatus();

		} catch (com.docusign.esign.client.ApiException ex) { 
			return ("Exception: " + ex);
		}
	}
} 
