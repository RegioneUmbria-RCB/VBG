package it.gruppoinit.pal.gp.core.features.messaggimail.model;

import java.util.Date;
import java.util.List;

public interface IMessaggiMail {

    Integer getId();

    void setId(Integer id);

    String getMittente();

    void setMittente(String mittente);

    String getDestinatario();

    void setDestinatario(String destinatario);

    String getDestinatariocc();

    void setDestinatariocc(String destinatariocc);

    String getDestinatariobcc();

    void setDestinatariobcc(String destinatariobcc);

    String getOggetto();

    void setOggetto(String oggetto);

    Date getDataInvio();

    void setDataInvio(Date data);

    String getMessageId();

    void setMessageId(String messageId);

    String getCorpo();

    void setCorpo(String corpo);

    Integer getAccountId();

    void setAccountId(Integer accountId);

    Integer getIdPadre();

    void setIdPadre(Integer idPadre);

    List<IAllegatoMail> getAllegati();
}
