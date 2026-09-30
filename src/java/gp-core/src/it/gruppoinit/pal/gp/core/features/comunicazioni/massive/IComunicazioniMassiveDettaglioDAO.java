package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.Date;
import java.util.List;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDocdafirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.upgr.UpgrMassiveDestinatariHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettagliMailComunicazione;

public interface IComunicazioniMassiveDettaglioDAO {

    public void insert(MassiveDettaglio dettaglio);

    public MassiveDettaglio getById(int idDettaglio);

    public List<MassiveDettaglio> getRigheByIdTestata(int idTestata);

    public List<MassiveDettaglio> getRigheByIdTestata(int idTestata, String statoDaEscludere);

    public MassiveDAllegati insertAllegato(int idDettaglio, int codiceOggetto);

    public void salvaErrore(int idDettaglio, String messaggioErrore);

    public void impostaStatoConCommit(int idDettaglio, String nuovoStato);

    public List<MassiveDAllegati> getMassiveDAllegatiByIdDettaglio(int idDettaglio);

    public List<Integer> getCodiciOggettoMassiveDAllegatiByIdDettaglio(int idDettaglio);

    public MassiveDettDocdafirmare insertDocumentoDaFirmare(int idRigaDettaglio, int idDocDaFirmare);

    public List<MassiveDettDocdafirmare> findDocDaFirmarePerDettaglio(int idRigaDettaglio);

    public void aggiornaRiferimentiProtocollo(int idRigaDettaglio, String numeroProtocollo, Date dateDDMMYYYY, String idProtocollo);

    public void salvaMailMessage(MailMessageType messaggio, MassiveDettaglio dettaglio, int senderAccount);

    public void salvaAppioMessage(String guid, MassiveDettaglio dettaglio);

    public List<MassiveDettDocdafirmare> findDocumentiDaFirmareByIdDocDaFirmare(int idDocumentoDaFirmare);

    public long countDocumentiDaFirmareByIdDocDaFirmare(int idDocumentoDaFirmare);

    public List<MassiveDettaglioAggiornaMail> trovaMailDaAggiornarePerAnagrafe(Integer codiceAnagrafe);

    public void aggiornaMailAnagrafe(Integer codiceAnagrafe);

    public List<MassiveDettaglioAggiornaMail> trovaMailDaAggiornarePerAmministrazione(Integer codiceAmministrazione);

    public void aggiornaMailAmministrazioni(Integer codiceAmministrazioni);

    public List<MassiveDettaglioAggiornaMail> trovaMailDaAggiornarePerResponsabile(Integer codiceResponsabile);

    public void aggiornaMailResponsabili(Integer codiceResponsabili);

    public List<RiferimentoPosizioneDebitoria> recuperaDettPosizioneDebitoriaFromMassiva(Integer idDettaglioMassiva);

    public List<Integer> getDocumentiDaFirmarePerIdTestata(int idTestataComunicazione);

    public void eliminaMassiveDocDaFirmareByIdDocDaFirmare(List<Integer> idDocDaFirmare);

    public List<UpgrMassiveDestinatariHelper> upgrDestinatariComunicazioniCommissioniDettagli();

    public int contaRicevuteMailPerDettaglio(int idDettaglio);

    public List<DettagliMailComunicazione> findDettagliMailInviate(int idRiga);

    public List<Integer> getCodiciIstanzeDaDettaglioForIstanzeOneri(int idRiga);

    public List<Integer> getDocumentiDaFirmarePerIdTestataAndDettaglio(Integer idTestata, Integer idDettaglio);

    public void eliminaMassivaDettaglio(Integer idDettaglio);

    public List<String> findDettagliAppIoInviate(int idRiga);

    public void insertMassiveDettDestinatari(MassiveDettDestinatari destinatari);
}
