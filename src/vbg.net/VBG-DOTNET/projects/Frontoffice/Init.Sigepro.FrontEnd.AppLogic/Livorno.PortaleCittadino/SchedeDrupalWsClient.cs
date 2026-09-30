using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.LivornoServiziDrupal;
using VBG.Shared.Infrastructure.ServiceModel;
using System;
using System.Linq;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino
{
    public class SchedeDrupalWsClient
    {
        private readonly IConfigurazione<ParametriLivornoConfigurazioneDrupal> _config;
        private readonly IBindingFactory _bindingFactory;
        public SchedeDrupalWsClient(IConfigurazione<ParametriLivornoConfigurazioneDrupal> config, IBindingFactory bindingFactory)
        {
            this._config = config;
            this._bindingFactory = bindingFactory;
        }

        public PCScheda GetSchedaById(int nid)
        {
            if (String.IsNullOrEmpty(this._config.Parametri.UrlWebServiceDatiDrupal))
            {
                throw new Exception("l'url dei servizi DRUPAL non è stato configurato correttamente");
            }

            var endpoint = new EndpointAddress(this._config.Parametri.UrlWebServiceDatiDrupal);
            var binding = this._bindingFactory.CreateAndConfigure("ServiziDrupal");
            using (var ws = new DrupalServiceSoapClient(binding, endpoint))
            {
                var data = ws.GetSchedaByNID(nid);
                if (data == null)
                {
                    return null;
                }

                return new PCScheda{Id = nid, Titolo = data.Scheda.Title, Link = data.Scheda.Uri, Modelli = data.Moduli.Select(x => new PCModello{Id = x.NID, Descrizione = x.Description, Link = x.Uri, NomeFile = x.FileName, Obbligatorio = x.Obbligatorio, RichiedeFirma = x.RichiedeFirma}).ToList(), Allegati = data.Allegati.Select(x => new PCAllegato{Id = x.NID, Descrizione = x.Description, Link = x.Uri, NomeFile = x.FileName, Obbligatorio = x.Obbligatorio, RichiedeFirma = x.RichiedeFirma}).ToList()};
            }
        }
    }
}