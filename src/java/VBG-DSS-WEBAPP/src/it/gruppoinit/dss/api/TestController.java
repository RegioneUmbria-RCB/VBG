package it.gruppoinit.dss.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }
    
    @PostMapping(value = "/documenti", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadDocumento(
            @RequestPart("documento") MultipartFile documento,
            @RequestPart("cf") List<String> codiciFiscali) {

    	byte[] contenuto;
		try {
			contenuto = documento.getBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

        System.out.println("Dimensione: " + contenuto.length);
        System.out.println("Nome file: " + documento.getOriginalFilename());
        System.out.println("MIME: " + documento.getContentType());
    	
    	System.out.println(codiciFiscali);
        System.out.println(documento);

    	return "Roba passata";
    }
}


