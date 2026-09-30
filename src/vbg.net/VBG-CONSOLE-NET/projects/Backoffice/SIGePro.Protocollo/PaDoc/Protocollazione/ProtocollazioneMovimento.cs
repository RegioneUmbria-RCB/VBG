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
    public class ProtocollazioneMovimento : IProtocollazione
    {
        Movimenti _movimento;
        VerticalizzazioniConfiguration _vert;
        ResolveDatiProtocollazioneService _resolveDati;

        public ProtocollazioneMovimento(ResolveDatiProtocollazioneService resolveDati, VerticalizzazioniConfiguration vert)
        {
            _resolveDati = resolveDati;
            _movimento = resolveDati.Movimento;
            _vert = vert;
        }

        public string Codice
        {
            get { return String.Format("I-{0}-{1}", _movimento.IDCOMUNE, _movimento.CODICEMOVIMENTO); }
        }


        public string UrlUpdate
        {
            get
            {
                var mac = Md5Utils.GetMd5(String.Concat(_resolveDati.IdComuneAlias, _movimento.CODICEMOVIMENTO, "movimento", "update", "secret"));
                return String.Format("{0}/{1}/m/{2}/mac/{3}/update", _vert.UrlResponseService, _resolveDati.IdComuneAlias, _movimento.CODICEMOVIMENTO, mac);
            }
        }

        public string UrlError
        {
            get
            {
                var mac = Md5Utils.GetMd5(String.Concat(_resolveDati.IdComuneAlias, _movimento.CODICEMOVIMENTO, "movimento", "error", "secret"));
                return String.Format("{0}/{1}/m/{2}/mac/{3}/error", _vert.UrlResponseService, _resolveDati.IdComuneAlias, _movimento.CODICEMOVIMENTO, mac);
            }
        }
    }
}
