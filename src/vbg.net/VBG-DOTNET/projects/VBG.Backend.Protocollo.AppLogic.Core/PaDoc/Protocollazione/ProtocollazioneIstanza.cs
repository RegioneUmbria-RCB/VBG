using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Verticalizzazioni;
using Init.SIGePro.Manager.Utils;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione
{
    public class ProtocollazioneIstanza : IProtocollazionePaDoc
    {
        private readonly VerticalizzazioniConfiguration _vert;
        private readonly ResolveDatiProtocollazioneService _resolveDati;
        private readonly IIstanzaDaProtocollare? _istanza;

        public ProtocollazioneIstanza(ResolveDatiProtocollazioneService resolveDati, VerticalizzazioniConfiguration vert)
        {
            _resolveDati = resolveDati;
            _istanza = resolveDati.Istanza;
            _vert = vert;
        }

        public string Codice
        {
            get { return String.Format("I-{0}-{1}", _istanza?.IDCOMUNE, _istanza?.CODICEISTANZA); }
        }


        public string UrlUpdate
        {
            get 
            {
                var mac = Md5Utils.GetMd5(String.Concat(_resolveDati.IdComuneAlias, _istanza?.CODICEISTANZA, "istanza", "update", "secret"));
                return String.Format("{0}/{1}/i/{2}/mac/{3}/update", _vert.UrlResponseService, _resolveDati.IdComuneAlias, _istanza?.CODICEISTANZA, mac); 
            }
        }

        public string UrlError
        {
            get 
            {
                var mac = Md5Utils.GetMd5(String.Concat(_resolveDati.IdComuneAlias, _istanza?.CODICEISTANZA, "istanza", "error", "secret"));
                return String.Format("{0}/{1}/i/{2}/mac/{3}/error", _vert.UrlResponseService, _resolveDati.IdComuneAlias, _istanza?.CODICEISTANZA, mac); 
            }
        }
    }
}
