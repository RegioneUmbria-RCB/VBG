package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import java.util.ArrayList;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;

public class ListaSoggettiInvitati extends SegnapostoTestualeBaseConValoreMultiplo {

    private static final String LISTASOGGETTIINVITATI = "LISTASOGGETTIINVITATI";
    private CommissioniToCdsDAO commissioniToCdsDAO;

    @Override
    public String getNome() {

	return LISTASOGGETTIINVITATI;
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

	Set<String> soggetti = commissioniToCdsDAO.findInvitatiCommissioniPerIstanza(data,
		CommissioniToCdsDAO.TIPO_SOGGETTO_INVITATO.ANAGRAFICA_RESPONSABILI);
	ArrayList<String> ret = new ArrayList<String>();
	if (!soggetti.isEmpty()) {
	    ret.addAll(soggetti);
	}
	return ret.toArray(new String[0]);
    }
    //  //cdsinvitati2 denominazioni (cdsinvitati.anagrafe.nome + " " + cdsinvitati.anagrafe.nominativo)
    //  	if (placeholder.equalsIgnoreCase("LISTASOGGETTIINVITATI")) {
    //  	    StringBuilder sb = new StringBuilder();
    //  	    StringBuilder sbRow = null;
    //  	    List<Cdsinvitati2> cdsis = data.getCdsInvitati2();
    //  	    for (Cdsinvitati2 cdsi : cdsis) {
    //  		//cdsi.getAmministrazioni()
    //  		sbRow = new StringBuilder();
    //  		sbRow.append(FormatUtils.stringFormat(cdsi.getAnagrafe().getNome()));
    //  		if (sbRow.length() > 0) {
    //  		    sbRow.append(" ");
    //  		}
    //  		sbRow.append(cdsi.getAnagrafe().getNominativo()).append(RtfConstants.RTF_CRLF);
    //  		sb.append(sbRow);
    //  	    }
    //  	    return sb.toString();
    //  	}
}
