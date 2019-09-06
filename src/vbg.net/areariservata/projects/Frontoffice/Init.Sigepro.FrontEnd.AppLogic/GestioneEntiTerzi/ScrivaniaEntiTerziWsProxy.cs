using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.WsEntiTerzi;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi
{
    public class ScrivaniaEntiTerziWsProxy : IScrivaniaEntiTerziWsProxy
    {
        private readonly string _webServiceUrl;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;

        internal ScrivaniaEntiTerziWsProxy(IConfigurazione<ParametriSigeproSecurity> configurazione, ITokenApplicazioneService tokenApplicazioneService)
        {
            this._webServiceUrl = configurazione.Parametri.UrlServizioEntiTerzi;
            this._tokenApplicazioneService = tokenApplicazioneService;
        }

        public ETAmministrazioneCollegata GetAmministrazioneCollegataAdAnagrafica(ETCodiceAnagrafe codiceAnagrafe)
        {
            var amministrazione = CallService(ws => ws.GetDatiAmministrazione(this._tokenApplicazioneService.GetToken(), codiceAnagrafe.Value));

            if (amministrazione == null)
            {
                return null;
            }

            return new ETAmministrazioneCollegata
            {
                Codice = amministrazione.Codice,
                Descrizione = amministrazione.Descrizione,
                PartitaIva = amministrazione.PartitaIva
            };
        }

        public IEnumerable<ETPratica> GetListaPratiche(ETCodiceAnagrafe codiceAnagrafe, ETFiltriRicerca filtri)
        {
            var token = this._tokenApplicazioneService.GetToken();

            return CallService(ws => ws.GetListaPratiche(token, new ETFiltriPraticheEntiTerzi
            {
                DallaData = filtri.DallaData,
                AllaData = filtri.AllaData,
                CodiceAnagrafe = codiceAnagrafe.Value,
                Elaborata = filtri.Elaborata,
                Modulo = filtri.Software,
                NumeroIstanza = filtri.NumeroIstanza,
                NumeroProtocollo = filtri.NumeroProtocollo
            }))
            .Select( x => new ETPratica
            {
                CodiceIstanza = x.CodiceIstanza,
                NumeroIstanza = x.NumeroIstanza,
                DataPresentazione = x.DataPresentazione,
                DataProtocollo = x.DataProtocollo,
                Localizzazione = x.Localizzazione,
                NumeroProtocollo = x.NumeroProtocollo,
                Oggetto = x.Oggetto,
                Richiedente = x.Richiedente,
                StatoLavorazione = x.StatoLavorazione,
                TipoIntervento = x.TipoIntervento,
                UUID = x.UUID,
                Modulo = x.SoftwareDescrizione
            });
        }

        public IEnumerable<ETSoftwareConPratiche> GetListaSoftwareConPratiche(int codiceAnagrafe)
        {
            var token = this._tokenApplicazioneService.GetToken();

            return CallService(ws => ws.GetListaSoftwareConPratiche(token, codiceAnagrafe)).Select(x => new ETSoftwareConPratiche
            {
                 Codice = x.Codice,
                 Descrizione = x.Descrizione
            });
        }

        public void MarcaPraticaComeElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            var token = this._tokenApplicazioneService.GetToken();

            CallService(ws => ws.MarcaPraticaComeElaborata(token, codiceIStanza.Value, codiceAnagrafe.Value));
        }

        public void MarcaPraticaComeNonElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            var token = this._tokenApplicazioneService.GetToken();

            CallService(ws => ws.MarcaPraticaComeNonElaborata(token, codiceIStanza.Value, codiceAnagrafe.Value));
        }

        public bool PraticaElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe)
        {
            var token = this._tokenApplicazioneService.GetToken();

            return CallService(ws => ws.PraticaElaborata(token, codiceIStanza.Value, codiceAnagrafe.Value));
        }

        public bool PuoEffettuareMovimenti(ETCodiceAnagrafe codiceAnagrafe)
        {
            var token = this._tokenApplicazioneService.GetToken();

            return CallService(ws => ws.PuoEffettuareMovimenti(token, codiceAnagrafe.Value));
        }

        private void CallService(Action<ws_enti_terziSoapClient> callback)
        {
            var endpoint = new EndpointAddress(this._webServiceUrl);
            var binding = new BasicHttpBinding("defaultServiceBinding");

            using (var ws = new ws_enti_terziSoapClient(binding, endpoint))
            {
                try
                {
                    callback(ws);
                }
                catch (Exception ex)
                {
                    ws.Abort();
                    throw;
                }
            }
        }

        private T CallService<T>(Func<ws_enti_terziSoapClient, T> callback)
        {
            var endpoint = new EndpointAddress(this._webServiceUrl);
            var binding = new BasicHttpBinding("defaultServiceBinding"); 

            using (var ws = new ws_enti_terziSoapClient(binding, endpoint))
            {
                try
                {
                    return callback(ws);
                }
                catch (Exception ex)
                {
                    ws.Abort();
                    throw;
                }
            }
        }
    }
}
