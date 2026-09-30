using Init.SIGePro.Manager.DTO.Commissioni.Votazioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Auditing;
using Init.SIGePro.Manager.Logic.GestioneCommissioni.Votazioni;
using Ninject;
using Sigepro.net.WsProtocollo;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Collections.Generic;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsVotazioniCommissione" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsVotazioniCommissione.svc or WsVotazioniCommissione.svc.cs at the Solution Explorer and start debugging.

    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsVotazioniCommissione : WcfServiceBase, IWsVotazioniCommissione
    {
        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }

        [Inject]
        public ProtocolloServiceCreator _protocolloServiceCreator { get; set; }

        public IEnumerable<CommissioniVotiBaseDto> GetListaPareri(string token)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                VotazioniCommissioniService service = CreateService(db, authInfo.IdComune);

                return service.GetListaPareri();
            }
        }

        private static VotazioniCommissioniService CreateService(PersonalLib2.Data.DataBase db, string idComune)
        {
            var votazioniDao = new VotazioniCommissioniDao(db, idComune);
            var commissioniDao = new CommissioniDao(db, idComune);
            var auditing = new DatabaseCommissioniAuditingService(db, idComune);
            var service = new VotazioniCommissioniService(db, idComune, votazioniDao, commissioniDao, auditing);

            return service;
        }

        public VotazionePraticaCommissioneDto GetVotoUtente(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                VotazioniCommissioniService service = CreateService(db, authInfo.IdComune);

                return service.GetVotoUtente(idCommissione, codiceAnagrafe, uuidIstanza);
            }
        }

        public bool UtentePuoEsprimereVoto(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                VotazioniCommissioniService service = CreateService(db, authInfo.IdComune);

                return service.UtentePuoEsprimereVoto(idCommissione, codiceAnagrafe, uuidIstanza);
            }
        }

        public void EsprimiVotoPerUtente(string token, int idCommissione, int codiceAnagrafe, string uuidIstanza, VotoPraticaCommissioneDto voto)
        {
            var authInfo = CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var protocollazioneCommissioneService = new ProtocollazioneCommissioniService(authInfo, _verticalizzazioniFactory, _protocolloServiceCreator);

                var pareriService = CreateService(db, authInfo.IdComune);

                pareriService.EsprimiVotoPerUtente(idCommissione, codiceAnagrafe, uuidIstanza, voto, protocollazioneCommissioneService);
            }
        }
    }
}
