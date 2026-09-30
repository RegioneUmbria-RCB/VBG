/**
 * SigeprorendererSOAPImpl.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package it.gruppoinit.sigeprorenderer;

import it.gruppoinit.visualizer.Main;

import org.apache.log4j.Logger;

public class SigeprorendererSOAPImpl {

    private static Logger logger = Logger.getLogger(SigeprorendererSOAPImpl.class);

    public byte[] generaGraficoDaDBConToken(java.lang.String token, int codiceprocedura, java.lang.String color) throws java.rmi.RemoteException {

	Main m = new Main();
	byte[] b = null;
	try {
	    b = m.generaGraficoDaToken(token, codiceprocedura, color);
	} catch (Exception e) {
	    logger.error(e);
	    throw new java.rmi.RemoteException("Errore nella generazione del grafico.");
	}
	return b;
    }
}
