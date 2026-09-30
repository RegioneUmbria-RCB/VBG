package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import java.util.ArrayList;
import java.util.Set;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreMultiplo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;

public class CdsAmmIndirizzo extends SegnapostoTestualeBaseConValoreMultiplo {

    private static final String CDSAMMINDIRIZZO = "CDSAMMINDIRIZZO";
    private CommissioniToCdsDAO commissioniToCdsDAO;

    @Override
    public String getNome() {

	return CDSAMMINDIRIZZO;
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
		CommissioniToCdsDAO.TIPO_SOGGETTO_INVITATO.AMMINISTRAZIONE, getTipoFile());
	ArrayList<String> ret = new ArrayList<String>();
	if (!indirizzi.isEmpty()) {
	    ret.addAll(indirizzi);
	}
	return ret.toArray(new String[0]);
    }
    //  //cdsinvitati (cdsinvitati.amministrazioni.amministrazione + indirizzo amministrazione completo)
    //  	if (placeholder.equalsIgnoreCase("CDSAMMINDIRIZZO")) {
    //  	    StringBuilder sb = new StringBuilder();
    //  	    List<Cdsinvitati> cdsis = data.getCdsInvitati();
    //  	    for (Cdsinvitati cdsi : cdsis) {
    //  		Amministrazioni amm = cdsi.getAmministrazioni();
    //  		if (amm.getId().getCodice() == codAmmSportelloUnico) {
    //  		    continue;
    //  		}
    //  		IndirizzoDestinatario ind = new IndirizzoDestinatario();
    //  		ind.setNominativo(amm.getAmministrazione());
    //  		ind.setUfficio(amm.getUfficio());
    //  		ind.setIndirizzo(amm.getIndirizzo());
    //  		ind.setCap(amm.getCap());
    //  		ind.setCitta(amm.getCitta());
    //  		ind.setProvincia(amm.getProvincia());
    //  		sb.append(ind.buildIndirizzo(RtfConstants.RTF_CRLF));
    //  		sb.append(RtfConstants.RTF_CRLF);
    //  	    }
    //  	    return sb.toString();
    //  	}
}
