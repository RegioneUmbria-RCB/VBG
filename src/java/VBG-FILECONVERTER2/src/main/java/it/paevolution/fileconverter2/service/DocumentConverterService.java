package it.paevolution.fileconverter2.service;

import java.io.File;

import it.paevolution.fileconverter2.jod.ServiceStatus;

public interface DocumentConverterService {
	public boolean convert(File in, File out, String outputFormat);

	public ServiceStatus[] getServiceStatus();

	public void restartService();

}
