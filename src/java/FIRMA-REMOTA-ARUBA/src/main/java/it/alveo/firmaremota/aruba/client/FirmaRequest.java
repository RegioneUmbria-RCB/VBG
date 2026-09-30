package it.alveo.firmaremota.aruba.client;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.alveo.firmaremota.aruba.firma.ProcessoBean;

public class FirmaRequest {

    private String tipoFirma;
    private String certId;
    private String emailNotifica;
    private boolean detached;
    private boolean marcaTemporaleRichiesta;
    private boolean firmaCongiuntaCADES;
    private boolean returnDER;
    private String posRettFirmaLeftPades;
    private String posRettFirmaRightPades;
    private String testoFirmaPades;
    private String motivoFirmaPades;
    private String profiloFirmaPades;
    private Integer numeroPaginaFirma;
    private List<ArubaDocumentoBean> documenti;
    private ProcessoBean infoProcesso;
    private Path filesPath;

    public String getTipoFirma() {

	return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {

	this.tipoFirma = tipoFirma;
    }

    public String getCertId() {

	return certId;
    }

    public void setCertId(String certId) {

	this.certId = certId;
    }

    public String getEmailNotifica() {

	return emailNotifica;
    }

    public void setEmailNotifica(String emailNotifica) {

	this.emailNotifica = emailNotifica;
    }

    public boolean isDetached() {

	return detached;
    }

    public void setDetached(boolean detached) {

	this.detached = detached;
    }

    public boolean isMarcaTemporaleRichiesta() {

	return marcaTemporaleRichiesta;
    }

    public void setMarcaTemporaleRichiesta(boolean marcaTemporaleRichiesta) {

	this.marcaTemporaleRichiesta = marcaTemporaleRichiesta;
    }

    public boolean isFirmaCongiuntaCADES() {

	return firmaCongiuntaCADES;
    }

    public void setFirmaCongiuntaCADES(boolean firmaCongiuntaCADES) {

	this.firmaCongiuntaCADES = firmaCongiuntaCADES;
    }

    public boolean isReturnDER() {

	return returnDER;
    }

    public void setReturnDER(boolean returnDER) {

	this.returnDER = returnDER;
    }

    public String getPosRettFirmaLeftPades() {

	return posRettFirmaLeftPades;
    }

    public void setPosRettFirmaLeftPades(String posRettFirmaLeftPades) {

	this.posRettFirmaLeftPades = posRettFirmaLeftPades;
    }

    public String getPosRettFirmaRightPades() {

	return posRettFirmaRightPades;
    }

    public void setPosRettFirmaRightPades(String posRettFirmaRightPades) {

	this.posRettFirmaRightPades = posRettFirmaRightPades;
    }

    public List<ArubaDocumentoBean> getDocumenti() {

	if (documenti == null) {
	    documenti = new ArrayList<>();
	}
	return documenti;
    }

    public void setDocumenti(List<ArubaDocumentoBean> documenti) {

	this.documenti = documenti;
    }

    public String getTestoFirmaPades() {

	return testoFirmaPades;
    }

    public void setTestoFirmaPades(String testoFirmaPades) {

	this.testoFirmaPades = testoFirmaPades;
    }

    public String getMotivoFirmaPades() {

	return motivoFirmaPades;
    }

    public void setMotivoFirmaPades(String motivoFirmaPades) {

	this.motivoFirmaPades = motivoFirmaPades;
    }

    public String getProfiloFirmaPades() {

	return profiloFirmaPades;
    }

    public void setProfiloFirmaPades(String profiloFirmaPades) {

	this.profiloFirmaPades = profiloFirmaPades;
    }

    public int getPossXRettLeft() {

	return getPos(this.getPosRettFirmaLeftPades(), 0);
    }

    public int getPossYRettLeft() {

	return getPos(this.getPosRettFirmaLeftPades(), 1);
    }

    public int getPossXRettRight() {

	return getPos(this.getPosRettFirmaRightPades(), 0);
    }

    public int getPossYRettRight() {

	return getPos(this.getPosRettFirmaRightPades(), 1);
    }

    private int getPos(String posRettangoloFirmaPades, int idx) {

	int pos = 0;
	posRettangoloFirmaPades = StringUtils.defaultString(posRettangoloFirmaPades).trim();
	if (StringUtils.isNotBlank(posRettangoloFirmaPades)) {
	    String[] posLeft = StringUtils.split(posRettangoloFirmaPades, ",");
	    try {
		pos = Integer.parseInt(posLeft[idx]);
	    } catch (Exception ne) {
	    }
	}
	return pos;
    }

    public Integer getNumeroPaginaFirma() {

	return numeroPaginaFirma;
    }

    public void setNumeroPaginaFirma(Integer numeroPaginaFirma) {

	this.numeroPaginaFirma = numeroPaginaFirma;
    }

    public ProcessoBean getInfoProcesso() {

	return infoProcesso;
    }

    public void setInfoProcesso(ProcessoBean infoProcesso) {

	this.infoProcesso = infoProcesso;
    }

    public Path getFilesPath() {

	return filesPath;
    }

    public void setFilesPath(Path filesPath) {

	this.filesPath = filesPath;
    }
}