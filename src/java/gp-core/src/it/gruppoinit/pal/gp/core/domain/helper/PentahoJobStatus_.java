package it.gruppoinit.pal.gp.core.domain.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "jobstatus")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "jobname", "id", "status_desc", "error_desc", "timestamp", "result_file" })
public class PentahoJobStatus_ {

    private String jobname;
    private String id;
    private String status_desc;
    private String error_desc;
    private String timestamp;
    private Result_file result_file;

    public String getJobname() {

	return jobname;
    }

    public void setJobname(String jobname) {

	this.jobname = jobname;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getStatus_desc() {

	return status_desc;
    }

    public void setStatus_desc(String status_desc) {

	this.status_desc = status_desc;
    }

    public String getError_desc() {

	return error_desc;
    }

    public void setError_desc(String error_desc) {

	this.error_desc = error_desc;
    }

    public String getTimestamp() {

	return timestamp;
    }

    public void setTimestamp(String timestamp) {

	this.timestamp = timestamp;
    }

    public Result_file getResult_file() {

	return result_file;
    }

    public void setResult_file(Result_file result_file) {

	this.result_file = result_file;
    }

    @XmlType(propOrder = { "file" })
    public static class Result_file {

	private String file;

	@XmlElement(name = "file")
	public String getFile() {

	    return file;
	}

	public void setFile(String file) {

	    this.file = file;
	}
    }
}
