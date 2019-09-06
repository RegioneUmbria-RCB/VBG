using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager.Utils;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione
{
    public class ProtocollazioneIstanza : IProtocollazione
    {
        VerticalizzazioniConfiguration _vert;
        ResolveDatiProtocollazioneService _resolveDati;
        Istanze _istanza;

        public ProtocollazioneIstanza(ResolveDatiProtocollazioneService resolveDati, VerticalizzazioniConfiguration vert)
        {
            _resolveDati = resolveDati;
            _istanza = resolveDati.Istanza;
            _vert = vert;
        }

        public string Codice
        {
            get { return String.Format("I-{0}-{1}", _istanza.IDCOMUNE, _istanza.CODICEISTANZA); }
        }


        public string UrlUpdate
        {
            get 
            {
                var mac = Md5Utils.GetMd5(String.Concat(_resolveDati.IdComuneAlias, _istanza.CODICEISTANZA, "istanza", "update", "secret"));
                return String.Format("{0}/{1}/i/{2}/mac/{3}/update", _vert.UrlResponseService, _resolveDati.IdComuneAlias, _istanza.CODICEISTANZA, mac); 
            }
        }

        public string UrlError
        {
            get 
            {
                var mac = Md5Utils.GetMd5(String.Concat(_resolveDati.IdComuneAlias, _istanza.CODICEISTANZA, "istanza", "error", "secret"));
                return String.Format("{0}/{1}/i/{2}/mac/{3}/error", _vert.UrlResponseService, _resolveDati.IdComuneAlias, _istanza.CODICEISTANZA, mac); 
            }
        }
    }
}
