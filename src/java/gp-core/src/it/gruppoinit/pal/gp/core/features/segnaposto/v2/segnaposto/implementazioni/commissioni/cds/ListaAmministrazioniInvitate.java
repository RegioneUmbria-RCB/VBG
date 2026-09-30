package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import java.util.ArrayList;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;

public class ListaAmministrazioniInvitate extends SegnapostoTestualeBaseConValoreMultiplo {

    private static final String LISTAAMMINVITATE = "LISTAAMMINVITATE";
    private CommissioniToCdsDAO commissioniToCdsDAO;

    @Override
    public String getNome() {

	return LISTAAMMINVITATE;
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
		CommissioniToCdsDAO.TIPO_SOGGETTO_INVITATO.AMMINISTRAZIONE);
	ArrayList<String> ret = new ArrayList<String>();
	if (!soggetti.isEmpty()) {
	    ret.addAll(soggetti);
	}
	return ret.toArray(new String[0]);
    }
    //    if (placeholder.equalsIgnoreCase("LISTAAMMINVITATE")) {
    //	    StringBuilder sb = new StringBuilder();
    //	    List<Cdsinvitati> cdsis = data.getCdsInvitati();
    //	    for (Cdsinvitati cdsi : cdsis) {
    //		sb.append(FormatUtils.stringFormat(cdsi.getAmministrazioni().getAmministrazione())).append(RtfConstants.RTF_CRLF);
    //	    }
    //	    return sb.toString();
    //	}
}
