using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.IntegrazioneLDP;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.ConfigurazioneIntervento;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.DataAccess;
using System.ServiceModel.Activation;
using static Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.ConfigurazioneIntervento.ConfigurazioneAlberoprocLDP;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.IntegrazioneLDP
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsIntegrazioneLDPService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsIntegrazioneLDPService.svc or WsIntegrazioneLDPService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsIntegrazioneLDPService : WcfServiceBase, IWsIntegrazioneLDPService
    {
        public ConfigurazioneAlberoprocLDPDto GetConfigurazioneAlberoprocLDP(string token, int idIntervento)
        {
            var auth = this.CheckToken(token);

            using (var db = auth.CreateDatabase())
            {
                var svc = new ConfigurazioneAlberoprocLDPService(new LdpDecodificheMgr(db, auth.IdComune), new AlberoProcMgr(db), auth.IdComune);

                var cfg = svc.GetConfigurazione(idIntervento);

                if (cfg == null)
                {
                    return null;
                }

                return new ConfigurazioneAlberoprocLDPDto
                {
                    LdpDolQString = cfg.LdpDolQString,
                    TipologiaGeometria = Convert(cfg.TipologiaGeometria),
                    TipologiaOccupazione = Convert(cfg.TipologiaOccupazione),
                    TipologiaPeriodo = Convert(cfg.TipologiaPeriodo)
                };
            }


        }

        private static ConfigurazioneAlberoprocLDPItemDto Convert(ConfigurazioneAlberoprocLDPItem item)
        {
            if (item == null) return null;

            return new ConfigurazioneAlberoprocLDPItemDto
            {
                Codice = item.Codice,
                Contesto = item.Contesto,
                Descrizione = item.Descrizione,
                Id = item.Id,
                IdComune = item.IdComune
            };
        }

        public ConfigurazioneAlberoprocLDPDto GetConfigurazioneAlberoprocLDPDaCodiceIstanza(string token, int codiceIstanza)
        {
            var auth = this.CheckToken(token);

            using (var db = auth.CreateDatabase())
            {
                var svc = new ConfigurazioneAlberoprocLDPService(new LdpDecodificheMgr(db, auth.IdComune), new AlberoProcMgr(db), auth.IdComune);

                var cfg = svc.GetConfigurazioneDaCodiceIstanza(codiceIstanza);

                if (cfg == null)
                {
                    return null;
                }

                return new ConfigurazioneAlberoprocLDPDto
                {
                    LdpDolQString = cfg.LdpDolQString,
                    TipologiaGeometria = Convert(cfg.TipologiaGeometria),
                    TipologiaOccupazione = Convert(cfg.TipologiaOccupazione),
                    TipologiaPeriodo = Convert(cfg.TipologiaPeriodo)
                };
            }
        }
    }
}
