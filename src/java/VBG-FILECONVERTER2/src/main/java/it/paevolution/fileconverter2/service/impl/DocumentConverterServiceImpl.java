package it.paevolution.fileconverter2.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.paevolution.fileconverter2.jod.ServiceStatus;
import it.paevolution.fileconverter2.service.DocumentConverterService;
import it.paevolution.fileconverter2.util.Configurazione;

@Service
public class DocumentConverterServiceImpl implements DocumentConverterService {

	private static final Logger log = LoggerFactory.getLogger(DocumentConverterServiceImpl.class);

	@Autowired
	private Configurazione conf;

	@Autowired
	private DocumentConverter documentConverter;

	public boolean convert(File in, File out, String outputFormat) {

		FileOutputStream fos = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

			final DocumentFormat targetFormat = DefaultDocumentFormatRegistry.getFormatByExtension(outputFormat);
			Assert.notNull(targetFormat, "targetFormat must not be null");
			documentConverter.convert(new FileInputStream(in)).to(baos).as(targetFormat).execute();

			fos = new FileOutputStream(out);
			fos.write(baos.toByteArray());
			return true;
		} catch (OfficeException | IOException e) {
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}

	}

	public ServiceStatus[] getServiceStatus() {

		// TODO
		ServiceStatus[] status = new ServiceStatus[1];

		return status;
	}

	public void restartService() {
		// TODO
	}

}
