package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import java.util.ArrayList;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;

public class CdsSoggIndirizzo extends SegnapostoTestualeBaseConValoreMultiplo {

    private static final String CDSSOGGINDIRIZZO = "CDSSOGGINDIRIZZO";
    private CommissioniToCdsDAO commissioniToCdsDAO;

    @Override
    public String getNome() {

	return CDSSOGGINDIRIZZO;
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

	Set<String> indirizzi = commissioniToCdsDAO.findIndirizziInvitatiCommissioniPerIstanza(data,
		CommissioniToCdsDAO.TIPO_SOGGETTO_INVITATO.ANAGRAFICA_RESPONSABILI, getTipoFile());
	ArrayList<String> ret = new ArrayList<String>();
	if (!indirizzi.isEmpty()) {
	    ret.addAll(indirizzi);
	}
	return ret.toArray(new String[0]);
    }
    //	//cdsinvitati2 denominazioni + iindirizzo completo
    //	if (placeholder.equalsIgnoreCase("CDSSOGGINDIRIZZO")) {
    //	    StringBuilder sb = new StringBuilder();
    //	    StringBuilder sbRow = null;
    //	    List<Cdsinvitati2> cdsis = data.getCdsInvitati2();
    //	    for (Cdsinvitati2 cdsi : cdsis) {
    //		sbRow = new StringBuilder();
    //		Anagrafe an = cdsi.getAnagrafe();
    //		sbRow.append(FormatUtils.stringFormat(cdsi.getAnagrafe().getNome()));
    //		if (sbRow.length() > 0) {
    //		    sbRow.append(" ");
    //		}
    //		sbRow.append(an.getNominativo());//.append(RTF_CRLF)
    //		IndirizzoDestinatario ind = new IndirizzoDestinatario();
    //		ind.setNominativo(sbRow.toString());
    //		ind.setIndirizzo(an.getIndirizzo());
    //		ind.setCap(an.getCap());
    //		ind.setCitta(an.getCitta());
    //		ind.setProvincia(an.getProvincia());
    //		sb.append(ind.buildIndirizzo(RtfConstants.RTF_CRLF));
    //		sb.append(RtfConstants.RTF_CRLF);
    //	    }
    //	    return sb.toString();
    //	}
}
