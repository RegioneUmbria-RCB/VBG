package it.gruppoinit.pal.gp.backoffice.web.rest;

import org.springframework.web.multipart.MultipartFile;

public class PostFileRestBean {

    private MultipartFile file;

    public MultipartFile getFile() {

	return file;
    }

    public void setFile(MultipartFile file) {

	this.file = file;
    }
}
