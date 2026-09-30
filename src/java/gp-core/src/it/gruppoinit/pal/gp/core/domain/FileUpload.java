package it.gruppoinit.pal.gp.core.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author francescop
 * 
 */
public class FileUpload {

    private MultipartFile file;

    public MultipartFile getFile() {

	return file;
    }

    public void setFile(MultipartFile file) {

	this.file = file;
    }
}
