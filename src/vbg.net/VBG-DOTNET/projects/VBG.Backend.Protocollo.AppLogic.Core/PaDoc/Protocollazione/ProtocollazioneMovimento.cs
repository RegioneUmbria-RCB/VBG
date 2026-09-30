using Init.SIGePro.Data;
using Init.SIGePro.Manager.Utils;
using VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione
{
    public class ProtocollazioneMovimento : IProtocollazionePaDoc
    {
        private readonly IMovimentoDaProtocollare _movimento;
        private readonly VerticalizzazioniConfiguration _vert;
        private readonly ResolveDatiProtocollazioneService _resolveDati;

        public ProtocollazioneMovimento(ResolveDatiProtocollazioneService resolveDati, VerticalizzazioniConfiguration vert)
        {
            this._resolveDati = resolveDati;
            this._movimento = resolveDati.Movimento;
            this._vert = vert;
        }

        public string Codice
        {
            get { return String.Format("I-{0}-{1}", this._movimento.IDCOMUNE, this._movimento.CODICEMOVIMENTO); }
        }


        public string UrlUpdate
        {
            get
            {
                var mac = Md5Utils.GetMd5(String.Concat(this._resolveDati.IdComuneAlias, this._movimento.CODICEMOVIMENTO, "movimento", "update", "secret"));
                return String.Format("{0}/{1}/m/{2}/mac/{3}/update", this._vert.UrlResponseService, this._resolveDati.IdComuneAlias, this._movimento.CODICEMOVIMENTO, mac);
            }
        }

        public string UrlError
        {
            get
            {
                var mac = Md5Utils.GetMd5(String.Concat(this._resolveDati.IdComuneAlias, this._movimento.CODICEMOVIMENTO, "movimento", "error", "secret"));
                return String.Format("{0}/{1}/m/{2}/mac/{3}/error", this._vert.UrlResponseService, this._resolveDati.IdComuneAlias, this._movimento.CODICEMOVIMENTO, mac);
            }
        }
    }
}
