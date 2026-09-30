package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadCommand {

    MultipartFile file;

    public MultipartFile getFile() {

	return file;
    }

    public void setFile(MultipartFile file) {

	this.file = file;
    }
}
