package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OggettiFileSystemStatusBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3549576017234464827L;
    private int countTotal = 0;
    private int countHandled = 0;
    private int countMoved = 0;
    private int minCodiceOggetto = 0;
    private int codiceOggettoCorrente = 0;
    private boolean running = false;
    private String rootPath;
    private List<OggettiFileSystemError> errors = new ArrayList<OggettiFileSystemError>();
    private List<OggettiFileSystemError> warnings = new ArrayList<OggettiFileSystemError>();

    /**
     * @return the countMoved
     */
    public int getCountMoved() {

	return countMoved;
    }

    /**
     * @param countMoved
     *            the countMoved to set
     */
    public void setCountMoved(int countMoved) {

	this.countMoved = countMoved;
    }

    /**
     * @return the rootPath
     */
    public String getRootPath() {

	return rootPath;
    }

    /**
     * @param rootPath
     *            the rootPath to set
     */
    public void setRootPath(String rootPath) {

	this.rootPath = rootPath;
    }

    /**
     * @return the errors
     */
    public List<OggettiFileSystemError> getErrors() {

	return errors;
    }

    public void addError(OggettiFileSystemError error) {

	this.errors.add(error);
    }

    public int getCountErrors() {

	int retVal = 0;
	if (this.errors != null) {
	    retVal = this.errors.size();
	}
	return retVal;
    }

    /**
     * @return the warnings
     */
    public List<OggettiFileSystemError> getWarnings() {

	return warnings;
    }

    public void addWarning(OggettiFileSystemError error) {

	this.warnings.add(error);
    }

    public int getCountWarnings() {

	int retVal = 0;
	if (this.warnings != null) {
	    retVal = this.warnings.size();
	}
	return retVal;
    }

    /**
     * @return the running
     */
    public boolean isRunning() {

	return running;
    }

    /**
     * @param running
     *            the running to set
     */
    public void setRunning(boolean running) {

	this.running = running;
    }

    /**
     * @return the countTotal
     */
    public int getCountTotal() {

	return countTotal;
    }

    /**
     * @param countTotal
     *            the countTotal to set
     */
    public void setCountTotal(int countTotal) {

	this.countTotal = countTotal;
    }

    /**
     * @return the countHandled
     */
    public int getCountHandled() {

	return countHandled;
    }

    /**
     * @param countHandled
     *            the countHandled to set
     */
    public void setCountHandled(int countHandled) {

	this.countHandled = countHandled;
    }

    public int getMinCodiceOggetto() {

	return minCodiceOggetto;
    }

    public void setMinCodiceOggetto(int minCodiceOggetto) {

	this.minCodiceOggetto = minCodiceOggetto;
    }

    public int getCodiceOggettoCorrente() {

	return codiceOggettoCorrente;
    }

    public void setCodiceOggettoCorrente(int codiceOggettoCorrente) {

	this.codiceOggettoCorrente = codiceOggettoCorrente;
    }
}
