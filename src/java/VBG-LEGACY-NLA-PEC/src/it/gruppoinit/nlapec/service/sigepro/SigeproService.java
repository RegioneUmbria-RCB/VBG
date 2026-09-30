package it.gruppoinit.nlapec.service.sigepro;

import it.gruppoinit.nlapec.dao.SigeproDAO;
import it.gruppoinit.nlapec.util.InfoIstanzaBean;
import it.gruppoinit.nlapec.util.PECMessage;
import it.gruppoinit.nlapec.util.SigeproPECInbox;
import it.init.sigepro.rte.types.PersonaFisicaType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SigeproService {

    private SigeproDAO sigeproDAO;

    public void setSigeproDAO(SigeproDAO sigeproDAO) {

	this.sigeproDAO = sigeproDAO;
    }

    public List<SigeproPECInbox> leggiPECInbox(Properties connectionProps, String software) {

	return sigeproDAO.leggiPECInbox(connectionProps, software);
    }

    public int insertPECInbox(Properties connectionProps, String software, PECMessage pecMessage, String loginNameMail, Integer idAccount) {

	return sigeproDAO.insertPECInbox(connectionProps, software, pecMessage, loginNameMail, idAccount);
    }

    public boolean isProcessed(Properties connectionProps, String software, String messageId) {

	return sigeproDAO.isProcessed(connectionProps, software, messageId);
    }

    public int setProcessed(Properties connectionProps, String software, String messageId, PECMessage pecMessage, String loginNameMail,BigInteger idAccount) {

	return sigeproDAO.setProcessed(connectionProps, software, messageId, pecMessage, loginNameMail,idAccount);
    }

    public int setCodicePratica(Properties connectionProps, String software, String messageId, PECMessage pecMessage, String codicePratica) {

	return sigeproDAO.setCodicePratica(connectionProps, software, messageId, pecMessage, codicePratica);
    }

    public ArrayList<String> checkVerticalizzazioneAttiva(Properties connectionProps) {

	return sigeproDAO.checkVerticalizzazioneAttiva(connectionProps);
    }

    public Map<String, String> getParametriTipologiePEC(Properties connectionProps, String software) {

	return sigeproDAO.getParametriTipologiePEC(connectionProps, software);
    }

    public Map<String, String> getAltriParametriVerticalizzazione(Properties connectionProps, String software) {

	return sigeproDAO.getAltriParametriVerticalizzazione(connectionProps, software);
    }

    public PersonaFisicaType getAnagrafePF(Properties connectionProps, String idAnagrafe) {

	return sigeproDAO.getAnagrafePF(connectionProps, idAnagrafe);
    }

    public boolean isComuneAssociato(Properties connectionProps, String idcomune) {

	return sigeproDAO.isComuneAssociato(connectionProps, idcomune);
    }

    public String getCodiceComuneAssociato(Properties connectionProps, String software, String codiceAccreditamento) {

	return sigeproDAO.getCodiceComuneAssociato(connectionProps, software, codiceAccreditamento);
    }

    public InfoIstanzaBean getInfoPratica(Properties connectionProps, String identificatoreMsgIdVBG, String software) {

	return sigeproDAO.getInfoPratica(connectionProps, identificatoreMsgIdVBG, software);
    }
}
