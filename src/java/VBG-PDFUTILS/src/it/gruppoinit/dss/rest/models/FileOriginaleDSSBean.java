package it.gruppoinit.dss.rest.models;


public class FileOriginaleDSSBean {
    
    private byte[] content;
    private String fileName;
    private String contentType;
    
    public byte[] getContent() {
    
        return content;
    }
    
    public void setContent(byte[] content) {
    
        this.content = content;
    }
    
    public String getFileName() {
    
        return fileName;
    }
    
    public void setFileName(String fileName) {
    
        this.fileName = fileName;
    }
    
    public String getContentType() {
    
        return contentType;
    }
    
    public void setContentType(String contentType) {
    
        this.contentType = contentType;
    }
    
}
