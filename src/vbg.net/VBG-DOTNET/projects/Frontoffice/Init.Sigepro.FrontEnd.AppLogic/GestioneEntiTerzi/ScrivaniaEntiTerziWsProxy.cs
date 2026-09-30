using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.WsEntiTerzi;
using VBG.Shared.Infrastructure.ServiceModel;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi
{
    public class ScrivaniaEntiTerziWsProxy : ServiceCreatorBase<WsEntiTerziServiceClient>, IScrivaniaEntiTerziWsProxy
    {
        public ScrivaniaEntiTerziWsProxy(IConfigurazione<ParametriSigeproSecurity> cfg, ITokenApplicazioneService tokenApplicazioneService, IBindingFactory bindingFactory) : base(cfg, tokenApplicazioneService, bindingFactory)
        {
        }

        public ETAmministrazioneCollegata GetAmministrazioneCollegataAdAnagrafica(ETCodiceAnagrafe codiceAnagrafe)
        {
            var amministrazione = this.Call(ws => ws.Service.GetDatiAmministrazione(ws.Token, codiceAnagrafe.Value));
            if (amministrazione == null)
            {
                return null;
            }

            return new ETAmministrazioneCollegata { Codice = amministrazione.Codice, Descrizione = amministrazione.Descrizione, PartitaIva = amministrazione.PartitaIva };
        }

        public IEnumerable<ETPratica> GetListaPratiche(ETCodiceAnagrafe codiceAnagrafe, ETFiltriRicerca filtri)
        {
            return this.Call(ws => ws.Service.GetListaPratiche(ws.Token, new ETFiltriPraticheEntiTerzi { DallaData = filtri.DallaData, AllaData = filtri.AllaData, CodiceAnagrafe = codiceAnagrafe.Value, Elaborata = filtri.Elaborata, Modulo = filtri.Software, NumeroIstanza = filtri.NumeroIstanza, NumeroProtocollo = filtri.NumeroProtocollo })).Select(x => new ETPratica { CodiceIstanza = x.CodiceIstanza, NumeroIstanza = x.NumeroIstanza, DataPresentazione = x.DataPresentazione, DataProtocollo = x.DataProtocollo, Localizzazione = x.Localizzazione, NumeroProtocollo = x.NumeroProtocollo, Oggetto = x.Oggetto, Richiedente = x.Richiedente, StatoLavorazione = x.StatoLavorazione, TipoIntervento = x.TipoIntervento, UUID = x.UUID, Modulo = x.SoftwareDescrizione });
        }

        public IEnumerable<ETSoftwareConPratiche> GetListaSoftwareConPratiche(int codiceAnagrafe)
        {
            return this.Call(ws => ws.Service.GetListaSoftwareConPratiche(ws.Token, codiceAnagrafe)).Select(x => new ETSoftwareConPratiche { Codice = x.Codice, Descrizione = x.Descrizione });
        }

        public void MarcaPraticaComeElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            this.CallVoid(ws => ws.Service.MarcaPraticaComeElaborata(ws.Token, codiceIStanza.Value, codiceAnagrafe.Value));
        }

        public void MarcaPraticaComeNonElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            this.CallVoid(ws => ws.Service.MarcaPraticaComeNonElaborata(ws.Token, codiceIStanza.Value, codiceAnagrafe.Value));
        }

        public bool PraticaElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            return this.Call(ws => ws.Service.PraticaElaborata(ws.Token, codiceIStanza.Value, codiceAnagrafe.Value));
        }

        public bool PuoEffettuareMovimenti(ETCodiceAnagrafe codiceAnagrafe)
        {
            return this.Call(ws => ws.Service.PuoEffettuareMovimenti(ws.Token, codiceAnagrafe.Value));
        }

        protected override string GetBindingName() => "defaultServiceBinding";
        protected override WsEntiTerziServiceClient CreateClient(EndpointAddress endpoint, BasicHttpBinding binding)
        {
            return new WsEntiTerziServiceClient(binding, endpoint);
        }

        protected override string GetEndpointUrl(ParametriSigeproSecurity config)
        {
            return config.UrlServizioEntiTerzi;
        }
    }
}