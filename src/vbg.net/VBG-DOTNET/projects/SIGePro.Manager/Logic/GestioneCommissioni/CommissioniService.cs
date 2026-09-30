using Init.SIGePro.Manager.DTO.Commissioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneCommissioni
{
    public class CommissioniService : ICommissioniService
    {
        private readonly ICommissioniDao _dao;
        private readonly ICommissioniAuditingService _auditingService;
        private readonly ILog _log = LogManager.GetLogger(typeof(CommissioniService));

        public CommissioniService(ICommissioniDao dao, ICommissioniAuditingService auditingService)
        {
            _dao = dao;
            _auditingService = auditingService;
        }

        public IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(int codiceAnagrafe)
        {
            return this._dao.GetCommissioniAperteByCodiceAnagrafe(codiceAnagrafe);
        }

        public DettaglioCommissioneDto GetDettaglioCommissione(int idCommissione, int codiceAnagrafe)
        {
            if (!this._dao.VerificaAccessoACommissione(idCommissione, codiceAnagrafe))
            {
                _auditingService.AccessoACommissioneNegatoAUtenteFrontend(idCommissione, codiceAnagrafe);
                return null;
            }

            try
            {
                var idAppello = this._dao.GetIdAppelloByCodiceAnagrafe(idCommissione, codiceAnagrafe);
                var dettaglio = new DettaglioCommissioneDto();
                dettaglio.Testata = this._dao.CaricaTestata(idCommissione);
                dettaglio.Convocazioni = this._dao.CaricaConvocazioniDaIdTestata(idCommissione).ToArray();
                dettaglio.Pratiche = this._dao.CaricaPraticheCommissione(idCommissione, codiceAnagrafe).ToArray();
                dettaglio.Documenti = this._dao.CaricaDocumentiCommissione(idCommissione, idAppello).ToArray();

                // TODO: loggare l'accesso alla commissione
                this._auditingService.AccessoACommissioneDaFrontend(idCommissione, codiceAnagrafe);

                return dettaglio;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nella lettura dei dati della commissione {idCommissione} da parte di {codiceAnagrafe}: {ex}");
                throw;
            }
        }

        public PraticaCommissioneEstesaDto GetDettaglioPratica(int idCommissione, int codiceAnagrafe, int codiceIstanza)
        {
            this._auditingService.AccessoAPraticaDaFrontend(idCommissione, codiceAnagrafe, codiceIstanza);

            if (!this._dao.VerificaAccessoACommissione(idCommissione, codiceAnagrafe))
            {
                // L'utente sta cercando di accedere a una commissione a cui non è stato autorizzato
                // Loggo un messaggio di errore nel log dell'auditing
                this._auditingService.AccessoACommissioneNegatoAUtenteFrontend(idCommissione, codiceAnagrafe);

                return null;
            }

            var pratica = this._dao.CaricaPratica(idCommissione, codiceAnagrafe, codiceIstanza);

            if (pratica == null)
            {
                // l'utente non ha accesso alla pratica per questa commissione, loggo l'evento nell'audit
                this._auditingService.AccessoAPraticaNegatoAUtenteFrontend(idCommissione, codiceAnagrafe, codiceIstanza);

                return null;
            }

            return pratica;
        }

        public bool AccessoAFile(int idCommissione, int codiceAnagrafe, int codiceIstanza, int codiceOggetto)
        {
            this._auditingService.AccessoAFileDaFrontend(idCommissione, codiceAnagrafe, codiceIstanza, codiceOggetto);

            var pratica = this._dao.CaricaPratica(idCommissione, codiceAnagrafe, codiceIstanza);

            if (pratica == null)
            {
                // l'utente non ha accesso alla pratica per questa commissione, loggo l'evento nell'audit
                this._auditingService.AccessoAFileNegatoAUtenteFrontend(idCommissione, codiceAnagrafe, codiceIstanza, codiceOggetto);

                return false;
            }

            var documento = pratica.Documenti.Istanza
                                             .Union(pratica.Documenti.Endoprocedimenti)
                                             .Union(pratica.Documenti.Movimenti)
                                             .FirstOrDefault(x => x.CodiceOggetto == codiceOggetto);

            if (documento == null)
            {
                this._auditingService.AccessoAFileNegatoAUtenteFrontend(idCommissione, codiceAnagrafe, codiceIstanza, codiceOggetto);

                return false;
            }

            return true;
        }

        public bool ApprovaAllegato(int idCommissione, int idAllegato, int codiceAnagrafe)
        {
            if (!this._dao.VerificaAccessoACommissione(idCommissione, codiceAnagrafe))
            {
                _auditingService.AccessoACommissioneNegatoAUtenteFrontend(idCommissione, codiceAnagrafe);
                return false;
            }

            try
            {
                var idAppello = this._dao.GetIdAppelloByCodiceAnagrafe(idCommissione, codiceAnagrafe);
                var allegato = this._dao.CaricaDocumentiCommissione(idCommissione, idAppello).Where(x => x.Id == idAllegato).FirstOrDefault();

                if (allegato == null)
                {
                    _auditingService.ApprovazioneAllegatoFallita(idCommissione, codiceAnagrafe, idAllegato);

                    return false;
                }

                this._dao.ApprovaAllegato(idAppello, idAllegato, allegato.Sha256);

                this._auditingService.AllegatoApprovato(idCommissione, codiceAnagrafe, allegato.CodiceOggetto, allegato.Sha256);

                return true;
            }
            catch(Exception ex)
            {
                this._log.Error($"Errore in ApprovaAllegato (idCommissione={idCommissione}, idAllegato={idAllegato}, codiceAnagrafe={codiceAnagrafe}): {ex}");

                throw;
            }
        }
    }
}
