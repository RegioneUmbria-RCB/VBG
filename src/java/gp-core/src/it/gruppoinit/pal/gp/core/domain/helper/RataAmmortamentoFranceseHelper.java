package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RataAmmortamentoFranceseHelper {

    private Date dataScadenza;
    private int numeroRata;
    private double importoRata;
    private double quotaCapitale;
    private double quotaInteressi;
    private double debitoResiduo;

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("[");
	sb.append(" Numero rata: ");
	sb.append(formatNumber(this.numeroRata));
	sb.append(" Data Scadenza: ");
	sb.append(formatDate(this.dataScadenza));
	sb.append(" Importo: ");
	sb.append(formatNumber(this.importoRata));
	sb.append(" Quota Capitale: ");
	sb.append(formatNumber(this.quotaCapitale));
	sb.append(" Quota Interesse: ");
	sb.append(formatNumber(this.quotaInteressi));
	sb.append(" Debito Residuo: ");
	sb.append(formatNumber(this.debitoResiduo));
	sb.append(" ]");
	sb.append("\n");
	return sb.toString();
    }

    public int getNumeroRata() {

	return numeroRata;
    }

    public void setNumeroRata(int numeroRata) {

	this.numeroRata = numeroRata;
    }

    public double getImportoRata() {

	return importoRata;
    }

    public void setImportoRata(double importoRata) {

	this.importoRata = importoRata;
    }

    public double getQuotaCapitale() {

	return quotaCapitale;
    }

    public void setQuotaCapitale(double quotaCapitale) {

	this.quotaCapitale = quotaCapitale;
    }

    public double getQuotaInteressi() {

	return quotaInteressi;
    }

    public void setQuotaInteressi(double quotaInteressi) {

	this.quotaInteressi = quotaInteressi;
    }

    public double getDebitoResiduo() {

	return debitoResiduo;
    }

    public void setDebitoResiduo(double debitoResiduo) {

	this.debitoResiduo = debitoResiduo;
    }

    private String formatNumber(double number) {

	NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
	nf.setGroupingUsed(true);
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
	return nf.format(number);
    }

    private String formatDate(Date d) {

	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	return sdf.format(d);
    }
}
