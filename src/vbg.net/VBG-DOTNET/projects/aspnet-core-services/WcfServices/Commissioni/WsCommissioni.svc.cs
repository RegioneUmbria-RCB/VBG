using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Commissioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing;
using log4net;
// using System.ServiceModel;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsCommissioni" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsCommissioni.svc or WsCommissioni.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsCommissioni : WcfServiceBase, IWsCommissioni
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(WsCommissioni));

        public WsCommissioni(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public IEnumerable<CommissioneTestataDto> GetCommissioniAperteByCodiceAnagrafe(string token, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            this._log.Debug($"Lettura della lista commissioni per il token {token} e codice anagrafe {codiceAnagrafe}");

            using (var db = authInfo.CreateDatabase())
            {
                var auditing = new DatabaseCommissioniAuditingService(db, authInfo.IdComune);
                var commissioniDao = new CommissioniDao(db, authInfo.IdComune);
                var svc = new CommissioniService(commissioniDao, auditing);

                var commissioni = svc.GetCommissioniAperteByCodiceAnagrafe(codiceAnagrafe);

                this._log.Debug($"Trovate {commissioni.Count()} per il codice anagrafe {codiceAnagrafe}");

                return commissioni;
            }
        }

        public DettaglioCommissioneDto GetDettaglioCommissione(string token, int idCommissione, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);

            this._log.Debug($"Lettura del dettaglio della commissione {idCommissione} per il token {token} e codice anagrafe {codiceAnagrafe}");

            using (var db = authInfo.CreateDatabase())
            {
                var auditing = new DatabaseCommissioniAuditingService(db, authInfo.IdComune);
                var commissioniDao = new CommissioniDao(db, authInfo.IdComune);
                var svc = new CommissioniService(commissioniDao, auditing);

                var commissione = svc.GetDettaglioCommissione(idCommissione, codiceAnagrafe);

                if (commissione == null)
                {
                    this._log.Error($"Commissione {idCommissione} non trovata per il codice anagrafe {codiceAnagrafe} e token {token}");
                }

                return commissione;
            }
        }

        public PraticaCommissioneEstesaDto GetDettaglioPratica(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            var authInfo = this.CheckToken(token);


            using (var db = authInfo.CreateDatabase())
            {
                var riferimentoIstanza = new IstanzeMgr(db).GetCodiceIstanzaDaUuid(authInfo.IdComune, uuidIstanza);

                if (riferimentoIstanza == null)
                {
                    this._log.Error($"Non sono state trovate istanze per l'uuid {uuidIstanza} (token={token}, idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe})");
                    return null;
                }
                var codiceIstanza = riferimentoIstanza.CodiceIstanza;

                this._log.Debug($"Lettura del dettaglio della pratica {codiceIstanza} della commissione {idCommissione} per il token {token} e codice anagrafe {codiceAnagrafe}");

                var auditing = new DatabaseCommissioniAuditingService(db, authInfo.IdComune);
                var commissioniDao = new CommissioniDao(db, authInfo.IdComune);
                var svc = new CommissioniService(commissioniDao, auditing);

                var pratica = svc.GetDettaglioPratica(idCommissione, codiceAnagrafe, codiceIstanza);

                if (pratica == null)
                {
                    this._log.Error($"Pratica {codiceIstanza} non trovata nella commissione {idCommissione} per il codice anagrafe {codiceAnagrafe} e token {token}");
                }

                return pratica;
            }
        }

        public bool VerificaAccessoFile(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza, int codiceOggetto)
        {
            var authInfo = this.CheckToken(token);


            using (var db = authInfo.CreateDatabase())
            {
                var riferimentoIstanza = new IstanzeMgr(db).GetCodiceIstanzaDaUuid(authInfo.IdComune, uuidIstanza);

                if (riferimentoIstanza == null)
                {
                    this._log.Error($"Non sono state trovate istanze per l'uuid {uuidIstanza} (token={token}, idCommissione={idCommissione}, codiceAnagrafe={codiceAnagrafe})");
                    return false;
                }
                var codiceIstanza = riferimentoIstanza.CodiceIstanza;

                this._log.Debug($"Lettura del dettaglio della pratica {codiceIstanza} della commissione {idCommissione} per il token {token} e codice anagrafe {codiceAnagrafe}");

                var auditing = new DatabaseCommissioniAuditingService(db, authInfo.IdComune);
                var commissioniDao = new CommissioniDao(db, authInfo.IdComune);
                var svc = new CommissioniService(commissioniDao, auditing);

                return svc.AccessoAFile(idCommissione, codiceAnagrafe, codiceIstanza, codiceOggetto);
            }
        }

        public bool ApprovaAllegato(string token, int idCommissione, int idAllegato, int codiceAnagrafe)
        {
            var authInfo = this.CheckToken(token);


            using (var db = authInfo.CreateDatabase())
            {
                this._log.Debug($"approvazione allegato {idAllegato} della commissione {idCommissione} per il token {token} e codice anagrafe {codiceAnagrafe}");

                var auditing = new DatabaseCommissioniAuditingService(db, authInfo.IdComune);
                var commissioniDao = new CommissioniDao(db, authInfo.IdComune);
                var svc = new CommissioniService(commissioniDao, auditing);

                return svc.ApprovaAllegato(idCommissione, idAllegato, codiceAnagrafe);
            }
        }
    }
}
