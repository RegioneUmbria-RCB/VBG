using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Scadenzario;
using Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice;
using Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice.Scadenzario;
using log4net;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Scadenzario
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsScadenzarioService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsScadenzarioService.svc or WsScadenzarioService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsScadenzarioService : WcfServiceBase, IWsScadenzarioService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsScadenzarioService));

        public WsScadenzarioService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public IEnumerable<ElementoListaScadenzeDto> GetListaScadenze(string token, RichiestaListaScadenzeDto richiesta)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var scadMgr = new ScadenzeManager(db, authInfo.IdComune);
                return scadMgr.GetListaScadenze(richiesta);
            }
        }

        public ScadenzaDto GetScadenzaById(string token, int codiceScadenza)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var scadMgr = new ScadenzeManager(db, authInfo.IdComune);
                return scadMgr.GetScadenza(codiceScadenza);
            }
        }

        /// <summary>
        /// Legge i dati relativi ad un movimento
        /// </summary>
        /// <param name="token">Token ottenuto con l'autenticazione</param>
        /// <param name="strCodiceMovimento">Codice del movimento di cui occorre leggere i dati</param>
        /// <returns></returns>
        public DatiMovimentoDaEffettuareDto GetMovimento(string token, string strCodiceMovimento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new MovimentiFrontofficeService(db, authInfo.IdComune).GetById(Convert.ToInt32(strCodiceMovimento));
            }
        }

        public DatiMovimentoDaEffettuareDto GetMovimentoByUuid(string token, string uuidMovimento)
        {
            // TODO: risolvere l'id movimento a partire dal GUID
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new MovimentiFrontofficeService(db, authInfo.IdComune).GetByUUID(uuidMovimento);
            }
        }


        public DocumentiIstanzaSostituibiliDto GetDocumentiSostituibili(string token, int codiceMovimentodaeffettuare)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                return new MovimentiFrontofficeService(db, authInfo.IdComune).GetDocumentiSostituibili(codiceMovimentodaeffettuare);
            }
        }

        public ConfigurazioneMovimentoDaEffettuareDto GetConfigurazioneMovimento(string token, int codiceMovimento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var config = new MovimentiFrontofficeService(db, authInfo.IdComune).GetFlagsConfigurazioneDaidMovimento(codiceMovimento);

                return new ConfigurazioneMovimentoDaEffettuareDto
                {
                    PermetteSostituzioneDocumentale = config.TipoSostituzioneDocumentale != TipoSostituzioneDocumentaleEnum.NessunaSostituzione,
                    RichiedeFirmaDocumenti = config.RichiedeFirmaDigitale,
                    RichiedeInterazioneConSIT = config.RichiedeInterazioneConSIT
                };
            }
        }

        /// <summary>
        /// Legge i dati json del movimento identificato dall'id univoco specificato
        /// </summary>
        /// <param name="token">Token</param>
        /// <param name="idMovimento">Identificativo del movimento</param>
        /// <returns>Dati in formato json del movimento o null se il movimento non esiste nella base dati</returns>
        public string GetJsonMovimentoFrontoffice(string token, int idMovimento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
                return new FoMovimentiMgr(db).GetDati(authInfo.IdComune, idMovimento);
        }

        /// <summary>
        /// Salva i dati in formato json di un movimento del frontoffice identificato dall'identificativo univoco specificato
        /// </summary>
        /// <param name="token">Token</param>
        /// <param name="idMovimento">Identificativo del movimento</param>
        /// <param name="datiJson">Dati in formato json del movimento</param>
        public void SalvaJsonMovimentoFrontoffice(string token, int idMovimento, string datiJson)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
                new FoMovimentiMgr(db).SalvaDati(authInfo.IdComune, idMovimento, datiJson);
        }

        /// <summary>
        /// Imposta il movimento frontoffice come inviato
        /// </summary>
        /// <param name="token">Token</param>
        /// <param name="idMovimento">Identificativo del movimento</param>
        public void ImpostaFlagTrasmesso(string token, int idMovimento)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
                new FoMovimentiMgr(db).ImpostaMovimentoComeTrasmesso(authInfo.IdComune, idMovimento);
        }

        public ScadenzaAreaPersonaleDto GetScadenzaByUuid(string token, string uuid)
        {
            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    var movFoSvc = new MovimentiFrontofficeService(db, authInfo.IdComune);
                    var scadMgr = new ScadenzeManager(db, authInfo.IdComune);

                    var idMovimento = movFoSvc.GetIdMovimentoByUUID(uuid);

                    if (idMovimento == null)
                    {
                        throw new Exception($"Id movimento non trovato per l'uuid {uuid}");
                    }

                    var scadenza = scadMgr.GetScadenza(idMovimento.Value);

                    if (scadenza == null)
                    {
                        throw new Exception($"Scadenza non trovata per il movimento con uuid {uuid}");
                    }

                    var idMovimentoDiOrigine = Convert.ToInt32(scadenza.CodMovimento);
                    var movimentoDiOrigine = movFoSvc.GetById(idMovimentoDiOrigine);

                    if (movimentoDiOrigine == null)
                    {
                        throw new Exception($"Movimento di origine non trovato per il movimento con uuid {uuid}");
                    }

                    var config = movFoSvc.GetFlagsConfigurazioneDaidMovimento(idMovimento.Value);

                    if (config == null)
                    {
                        throw new Exception($"Configurazione non trovata per il movimento con uuid {uuid}");
                    }

                    var configurazioneDto = new ConfigurazioneMovimentoDaEffettuareDto
                    {
                        PermetteSostituzioneDocumentale = config.TipoSostituzioneDocumentale != TipoSostituzioneDocumentaleEnum.NessunaSostituzione,
                        RichiedeFirmaDocumenti = config.RichiedeFirmaDigitale,
                        RichiedeInterazioneConSIT = config.RichiedeInterazioneConSIT
                    };

                    return new ScadenzaAreaPersonaleDto
                    {
                        UuidMovimentoDaFare = uuid,
                        Scadenza = scadenza,
                        MovimentoDiOrigine = movimentoDiOrigine,
                        Configurazione = configurazioneDto
                    };
                }
                catch (Exception ex)
                {
                    this._log.Error($"{nameof(GetScadenzaByUuid)}: {ex}");

                    throw;
                }
            }
        }
    }
}
