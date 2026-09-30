package it.gruppoinit.nlapec.util;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;

public class OriginalMessage {

    private String id;
    private List<Address> from;
    private List<Address> to;
    private String subject;
    private String contentPlain;
    private String contentHtml;
    private String contentType;
    private List<OriginalMessageAttachment> attachments = new ArrayList<OriginalMessageAttachment>();

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public List<Address> getFrom() {

	return from;
    }

    public void setFrom(List<Address> from) {

	this.from = from;
    }

    public void addFrom(Address a) {

	from.add(a);
    }

    public List<Address> getTo() {

	return to;
    }

    public void setTo(List<Address> to) {

	this.to = to;
    }

    public void addTo(Address a) {

	to.add(a);
    }

    public String getSubject() {

	return subject;
    }

    public void setSubject(String subject) {

	this.subject = subject;
    }

    public String getContentPlain() {

	return contentPlain;
    }

    public void setContentPlain(String contentPlain) {

	this.contentPlain = contentPlain;
    }

    public String getContentHtml() {

	return contentHtml;
    }

    public void setContentHtml(String contentHtml) {

	this.contentHtml = contentHtml;
    }

    public List<OriginalMessageAttachment> getAttachments() {

	return attachments;
    }

    public void setAttachments(List<OriginalMessageAttachment> attachments) {

	this.attachments = attachments;
    }

    public void addAttachment(OriginalMessageAttachment oma) {

	attachments.add(oma);
    }

    public void setContentType(String contentType) {

	this.contentType = contentType;
    }

    public String getContentType() {

	return contentType;
    }
}