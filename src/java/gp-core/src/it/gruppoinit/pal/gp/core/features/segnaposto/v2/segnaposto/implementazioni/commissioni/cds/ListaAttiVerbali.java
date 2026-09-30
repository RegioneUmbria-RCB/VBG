package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;

public class ListaAttiVerbali extends SegnapostoTestualeBaseConValoreMultiplo {

    private static final String LISTAATTIVERBALI = "LISTAATTIVERBALI";
    private CommissioniToCdsDAO commissioniToCdsDAO;

    @Override
    public String getNome() {

	return LISTAATTIVERBALI;
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.commissioniToCdsDAO = kernel.getBeanOfType(CommissioniToCdsDAO.class);
    }

    @Override
    protected String[] onGetValori(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	String[] ret = new String[0];
	return ret;
    }
    //
    //
    //SELECT 
    //	commedilizie_allegati.codicecommissione, 
    //	commedilizie_allegati.descrizione, 	
    //	commedilizie_allegati.note 	
    //	FROM 
    //	commedilizie_allegati 
    //	INNER JOIN commissioniedilizie_r ON 
    //	commissioniedilizie_r.idcomune=commedilizie_allegati.idcomune AND
    //	commissioniedilizie_r.codicecommissione=commedilizie_allegati.codicecommissione
    //	INNER JOIN movimenti ON 
    //	movimenti.idcomune=commissioniedilizie_r.idcomune AND
    //	movimenti.codicemovimento=commissioniedilizie_r.codicemovimento
    //WHERE movimenti.idcomune='E256'
    //AND MOVIMENTI.CODICEISTANZA=8411
    //	    StringBuilder sb = new StringBuilder();
    //	    for (Cdsatti atto : atti) {
    //		if (atto.getCodiceatto() != null) {
    //		    sb.append(FormatUtils.integerFormat(atto.getCodiceatto())).append(" ");
    //		}
    //		sb.append("del ").append(FormatUtils.dateFormat(atto.getData())).append(" ");
    //		sb.append(FormatUtils.stringFormat(atto.getOra())).append(" ha avuto esito ");
    //		sb.append(atto.getPositivia() ? "positivo" : "negativo");
    //		boolean chiusa = StringUtils.isNotBlank(atto.getChiusa()) && atto.getChiusa().toUpperCase().equals("S") ? true : false;
    //		sb.append(chiusa ? " ed \\\\'e8 " : " e non \\\\'e8 ").append("stata chiusa").append(RtfConstants.RTF_CRLF);
    //		sb.append("Le prossime convocazioni: ").append(RtfConstants.RTF_CRLF);
    //		if (atto.getDataconvocazione() != null) {
    //		    sb.append(FormatUtils.dateFormat(atto.getDataconvocazione())).append(" ");
    //		    sb.append(FormatUtils.stringFormat(atto.getOraconvocazione())).append(RtfConstants.RTF_CRLF);
    //		}
    //		if (atto.getDataconvocazione2() != null) {
    //		    sb.append(FormatUtils.dateFormat(atto.getDataconvocazione2())).append(" ");
    //		    sb.append(FormatUtils.stringFormat(atto.getOraconvocazione2())).append(RtfConstants.RTF_CRLF);
    //		}
}
