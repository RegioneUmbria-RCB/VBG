/**
 * MailServiceSkeleton.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package it.gruppoinit.mailservice;

import it.gruppoinit.mailservice.ws.SenderMail;
import it.gruppoinit.mailservice.ws.SenderMail2;

/**
 * MailServiceSkeleton java skeleton for the axisService
 */
public class MailServiceSkeleton implements MailServiceSkeletonInterface {

    /**
     * Auto generated method signature
     * 
     * @param messageRequest0
     * @return messageResponse1
     */
    public it.gruppoinit.mailservice.schemas.messages.MessageResponse sendMail(
	    it.gruppoinit.mailservice.schemas.messages.MessageRequest messageRequest0) {

	try {
	    SenderMail sender = new SenderMail();
	    return sender.sendMail(messageRequest0);
	} catch (Exception e) {
	    throw new java.lang.RuntimeException("[MAILSERVICE] " + e.getMessage());
	}
    }

    /**
     * Auto generated method signature
     * 
     * @param messageRequest22
     * @return messageResponse23
     */
    public it.gruppoinit.mailservice.schemas.messages.MessageResponse2 sendMail2(
	    it.gruppoinit.mailservice.schemas.messages.MessageRequest2 messageRequest2) {

	try {
	    SenderMail2 sender2 = new SenderMail2();
	    return sender2.sendMail2(messageRequest2);
	} catch (Exception e) {
	    throw new java.lang.RuntimeException("[MAILSERVICE] " + e.getMessage());
	}
    }
}
