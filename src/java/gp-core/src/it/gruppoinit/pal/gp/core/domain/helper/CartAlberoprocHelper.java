package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CartAlberoprocHelper {

    private boolean root = false;
    private Alberoproc alberoprocData;
    private String tipologiaEndo;
    private String codiceStp;
    private String codiceEndoRegionale;
    private BigInteger codiceTipologiaEndoRegionale;
    private List<CartAlberoprocHelper> childs = new ArrayList<CartAlberoprocHelper>();
    private GregorianCalendar dataFineValidita;

    public CartAlberoprocHelper() {

	this.alberoprocData = new Alberoproc();
    }

    public Alberoproc getAlberoprocData() {

	return alberoprocData;
    }

    public void setAlberoprocData(Alberoproc alberoprocData) {

	this.alberoprocData = alberoprocData;
    }

    public String getTipologiaEndo() {

	return tipologiaEndo;
    }

    public void setTipologiaEndo(String tipologiaEndo) {

	this.tipologiaEndo = tipologiaEndo;
    }

    public String getCodiceStp() {

	return codiceStp;
    }

    public void setCodiceStp(String codiceStp) {

	this.codiceStp = codiceStp;
    }

    public List<CartAlberoprocHelper> getChilds() {

	return childs;
    }

    public void setChilds(List<CartAlberoprocHelper> childs) {

	this.childs = childs;
    }

    public void setDataFineValidita(GregorianCalendar dataFineValidita) {

	this.dataFineValidita = dataFineValidita;
    }

    public GregorianCalendar getDataFineValidita() {

	return dataFineValidita;
    }

    public void setRoot(boolean root) {

	this.root = root;
    }

    public boolean isRoot() {

	return root;
    }

    public String getCodiceEndoRegionale() {

	return codiceEndoRegionale;
    }

    public void setCodiceEndoRegionale(String codiceEndoRegionale) {

	this.codiceEndoRegionale = codiceEndoRegionale;
    }

    public BigInteger getCodiceTipologiaEndoRegionale() {

	return codiceTipologiaEndoRegionale;
    }

    public void setCodiceTipologiaEndoRegionale(BigInteger codiceTipologiaEndoRegionale) {

	this.codiceTipologiaEndoRegionale = codiceTipologiaEndoRegionale;
    }
}
