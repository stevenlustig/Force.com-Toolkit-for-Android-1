package com.sforce.android.soap.partner;

import java.io.StringWriter;
import java.util.HashMap;
import org.xmlpull.v1.XmlSerializer;


import android.util.Xml;

public class DeleteSoapRequest implements Request{
	static final String ENV="http://schemas.xmlsoap.org/soap/envelope/";
	static final String URN="urn:partner.soap.sforce.com";
	static final String SOAPENV="soapenv";
	static final String URN_STRING="urn";
	static final String ENVELOPE="Envelope";
	static final String HEADER="Header";
	static final String SESSION_HEADER="SessionHeader";
	static final String SESSION_ID="sessionId";
	static final String BODY="Body";
	static final String DELETE="delete";
	static final String IDS="ids";
	static final String SEPARATOR=",";
	
	private String soapRequest=null;
	
	public DeleteSoapRequest(HashMap<String, String> requestFields) {
		this.soapRequest=createSoapRequest(requestFields);
	}

	public String getRequest() {
		return this.soapRequest;
	}
	
	public void setRequest(String soapRequest) {
		this.soapRequest=soapRequest;
	}
	
	private String createSoapRequest(HashMap<String, String> requestFields){
    	XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.setPrefix(SOAPENV, ENV);
			serializer.setPrefix(URN_STRING, URN);
			serializer.startTag(ENV, ENVELOPE);
			serializer.startTag(ENV, HEADER);
			serializer.startTag(ENV, SESSION_HEADER);
			serializer.startTag(URN, SESSION_ID);
			serializer.text(requestFields.get(SESSION_ID));
			serializer.endTag(URN, SESSION_ID);
			serializer.endTag(ENV, SESSION_HEADER);
			serializer.endTag(ENV, HEADER);
			serializer.startTag(ENV, BODY);
			serializer.startTag(URN, DELETE);
			
			String[] fields = (requestFields.get(IDS)).split(SEPARATOR);
			for (String id:fields){
				serializer.startTag(URN, IDS);
				serializer.text(id.trim());
				serializer.endTag(URN, IDS);
			}
			
			serializer.endTag(URN, DELETE);
			serializer.endTag(ENV, BODY);
			serializer.endTag(ENV, ENVELOPE);
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
