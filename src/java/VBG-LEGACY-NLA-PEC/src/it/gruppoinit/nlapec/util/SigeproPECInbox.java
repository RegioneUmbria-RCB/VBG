package it.gruppoinit.nlapec.util;

import java.util.Date;

public class SigeproPECInbox {

    private String messageId;
    private String from;
    private String to;
    private String subject;
    private Date date;

    public Date getDate() {

	return date;
    }

    public void setDate(Date date) {

	this.date = date;
    }

    public String getMessageId() {

	return messageId;
    }

    public void setMessageId(String messageId) {

	this.messageId = messageId;
    }

    public String getFrom() {

	return from;
    }

    public void setFrom(String from) {

	this.from = from;
    }

    public String getTo() {

	return to;
    }

    public void setTo(String to) {

	this.to = to;
    }

    public String getSubject() {

	return subject;
    }

    public void setSubject(String subject) {

	this.subject = subject;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	SigeproPECInbox other = (SigeproPECInbox) obj;
	if (messageId == null) {
	    if (other.messageId != null) {
		return false;
	    }
	} else if (!messageId.equals(other.messageId)) {
	    return false;
	}
	return true;
    }
}
