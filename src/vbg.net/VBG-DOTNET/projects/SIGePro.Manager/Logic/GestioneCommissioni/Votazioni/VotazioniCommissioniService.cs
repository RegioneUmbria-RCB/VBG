using Init.SIGePro.Manager.DTO.Commissioni;
using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Protocollazione;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using static Init.SIGePro.Manager.IstanzeMgr;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni.Votazioni
{
    public class VotazioniCommissioniService : IVotazioniCommissioniService
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly IVotazioniCommissioniDao _votazioniDao;
        private readonly ICommissioniDao _commissioniDao;
        private readonly ICommissioniAuditingService _auditingService;
        private readonly ILog _log = LogManager.GetLogger(typeof(VotazioniCommissioniService));

        public VotazioniCommissioniService(DataBase db, string idComune, IVotazioniCommissioniDao votazioniDao, ICommissioniDao commissioniDao, ICommissioniAuditingService auditingService)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            this._db = db ?? throw new ArgumentNullException(nameof(db));
            this._idComune = idComune;
            this._votazioniDao = votazioniDao ?? throw new ArgumentNullException(nameof(votazioniDao));
            this._commissioniDao = commissioniDao ?? throw new ArgumentNullException(nameof(commissioniDao));
            this._auditingService = auditingService ?? throw new ArgumentNullException(nameof(auditingService));
        }

        private RiferimentiIstanzaDaUuid GetCodiceIstanzaByUuid(string uuid)
        {
            var riferimenti = new IstanzeMgr(this._db).GetCodiceIstanzaDaUuid(this._idComune, uuid);

            if (riferimenti == null || riferimenti.IdComune != this._idComune)
            {
                throw new Exception($"Uuid istanza {uuid} non valido");
            }

            return riferimenti;
        }

        public void EsprimiVotoPerUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza, VotoPraticaCommissioneDto nuovoVoto, IProtocollazioneCommissioneService protocollazioneService)
        {

            try
            {
                this._db.BeginTransaction();

                // throw new Exception("Eccezione di test per errore parere");

                var codiceIstanza = this.GetCodiceIstanzaByUuid(uuidIstanza);

                if (!this.UtentePuoEsprimereVoto(idCommissione, codiceAnagrafe, codiceIstanza.CodiceIstanza))
                {
                    this._auditingService.AccessoAVotoNegatoDaFrontend(idCommissione, codiceAnagrafe, codiceIstanza.CodiceIstanza);
                }

                var vecchioParere = this.GetVotoUtente(idCommissione, codiceAnagrafe, uuidIstanza);

                if (vecchioParere.Voto != null)
                {
                    // TODO: Un utente può esprimere un solo voto per la pratica.
                    //   Il voto NON è modificabile
                    throw new Exception($"L'utente {codiceAnagrafe} sta modificando il proprio voto nella commissione {idCommissione}. L'operazione non è consentita");
                }

                EsitoProtocollazioneCommissione esitoProtocollazione = null;

                // Se il voto contiene un codice oggetto allora il parere va protocollato
                if (nuovoVoto.CodiceOggetto.HasValue)
                {
                    var risolviMittente = new RisolviMittenteProtocolloCommissione(this._db, this._idComune, idCommissione, codiceAnagrafe);
                    var risolviOggetto = new RisolviOggettoProtocolloCommissione(this._db, this._idComune, idCommissione, codiceAnagrafe, codiceIstanza.CodiceIstanza);
                    var risolviAllegati = new RisolviAllegatiProtocolloCommissione(this._db, this._idComune, nuovoVoto.CodiceOggetto.Value, nuovoVoto.NomeFile);

                    esitoProtocollazione = protocollazioneService.ProtocollaParereEsterno(codiceIstanza.CodiceIstanza, risolviMittente, risolviOggetto, risolviAllegati);

                    if (esitoProtocollazione.Esito)
                    {
                        this._auditingService.ParereCommissioneProtocollato(idCommissione, codiceAnagrafe, esitoProtocollazione.IdProtocollo, esitoProtocollazione.NumeroProtocollo, esitoProtocollazione.DataProtocollo);
                    }
                    else
                    {
                        this._auditingService.ErroreProtocollazioneVoto(idCommissione, codiceAnagrafe, esitoProtocollazione.MessaggioErrore);

                        this._log.Error($"Si è verificato un errore durante la protocollazione della commissione {idCommissione} " +
                            $"(codiceAnagrafe: {codiceAnagrafe}, codiceIstanza: {codiceIstanza.CodiceIstanza}): " +
                            $"{esitoProtocollazione.MessaggioErrore}\r\n{esitoProtocollazione.ErroreEsteso}");
                        throw new Exception($"{esitoProtocollazione.MessaggioErrore}->{esitoProtocollazione.ErroreEsteso}");
                    }
                }


                var idAppello = this.GetIdAppelloByCodiceAnagrafe(idCommissione, codiceAnagrafe);
                var idRiga = this.GetIdRigaByCodiceIstanza(idCommissione, codiceIstanza.CodiceIstanza);

                this.InserisciVoto(idCommissione, idAppello, codiceAnagrafe, idRiga, nuovoVoto, esitoProtocollazione);

                this._auditingService.VotoInseritoDaFrontend(idCommissione, codiceAnagrafe, codiceIstanza.CodiceIstanza, nuovoVoto.DescrizioneParere, nuovoVoto.CodiceOggetto, esitoProtocollazione);


                this._db.CommitTransaction();


            }
            catch (Exception)
            {
                this._db.RollbackTransaction();

                throw;
            }

        }

        private void InserisciVoto(int idCommissione, int idAppello, int codiceAnagrafe, int idRiga, VotoPraticaCommissioneDto voto, IEstremiProtocollazioneCommissione estremiProtocollazione)
        {
            // se non presente aggiorna l'appello con il codice anagrafe corretto
            var verificaIdAppello = this._commissioniDao.GetIdAppelloByCodiceAnagrafeInvitato(idCommissione, codiceAnagrafe);

            if (!verificaIdAppello.HasValue || verificaIdAppello.Value != idAppello)
            {
                // TODO: loggare l'aggiornamento anagrafe
                this._votazioniDao.AggiornaAnagraficaAppello(idAppello, codiceAnagrafe);
            }

            this._votazioniDao.InserisciVoto(idCommissione, idRiga, voto.CodiceParere.Value, idAppello, voto.Note, voto.CodiceOggetto, estremiProtocollazione);
        }

        private int GetIdRigaByCodiceIstanza(int idCommissione, int codiceIstanza)
        {
            return this._votazioniDao.GetIdRigaByCodiceIstanza(idCommissione, codiceIstanza);
        }

        public IEnumerable<CommissioniVotiBaseDto> GetListaPareri()
        {
            return this._votazioniDao.GetListaPareri();
        }

        public VotazionePraticaCommissioneDto GetVotoUtente(int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            try
            {
                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"Inizio della chiamata a GetVotoUtente con idCommissione={idCommissione}, uuidIstanza={uuidIstanza}, codiceAnagrafe={codiceAnagrafe}");
                }

                var codiceIstanza = this.GetCodiceIstanzaByUuid(uuidIstanza);
                var idAppello = this.GetIdAppelloByCodiceAnagrafe(idCommissione, codiceAnagrafe);
                var pratica = this.GetPraticaCommissione(idCommissione, codiceAnagrafe, codiceIstanza.CodiceIstanza);

                if (pratica == null)
                {
                    this._log.Error($"Pratica commissione {codiceIstanza.CodiceIstanza} non trovata per idCommissione={idCommissione} e codiceAnagrafe={codiceAnagrafe}");

                    this._auditingService.AccessoAPraticaNegatoAUtenteFrontend(idCommissione, codiceAnagrafe, codiceIstanza.CodiceIstanza);

                    return null;
                }

                this._log.Debug($"Lettura del voto esistente con idCommissione={idCommissione}, idCommissioneR={pratica.DatiPratica.IdCommissioniR}, idAppello={idAppello}");

                return this._votazioniDao.GetVotoUtente(idCommissione, pratica.DatiPratica.IdCommissioniR, idAppello);
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella chiamata a GetVotoUtente (idCommissione={idCommissione}, uuidIstanza={uuidIstanza}, codiceAnagrafe={codiceAnagrafe}): {ex}");

                return null;
            }
            finally
            {
                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug("Chiamata a GetVotoUtente terminata");
                }
            }
        }

        private PraticaCommissioneEstesaDto GetPraticaCommissione(int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            return this._commissioniDao.CaricaPratica(idCommissione, codiceAnagrafe, codiceIstanza);
        }

        public bool UtentePuoEsprimereVoto(int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            var codiceIstanza = this.GetCodiceIstanzaByUuid(uuidIstanza);

            return this.UtentePuoEsprimereVoto(idCommissione, codiceAnagrafe, codiceIstanza.CodiceIstanza);
        }

        private bool UtentePuoEsprimereVoto(int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            if (!this._commissioniDao.CommissioneAperta(idCommissione))
            {
                return false;
            }

            if (!this._commissioniDao.PraticaAperta(idCommissione, codiceIstanza))
            {
                return false;
            }

            var idAppello = this.GetIdAppelloByCodiceAnagrafe(idCommissione, codiceAnagrafe);

            if (!this._votazioniDao.CaricaDellUtentePermetteVoto(idAppello))
            {
                return false;
            }

            return true;
        }

        private int GetIdAppelloByCodiceAnagrafe(int idCommissione, int codiceAnagrafe)
        {
            return this._commissioniDao.GetIdAppelloByCodiceAnagrafe(idCommissione, codiceAnagrafe);
        }
    }
}
