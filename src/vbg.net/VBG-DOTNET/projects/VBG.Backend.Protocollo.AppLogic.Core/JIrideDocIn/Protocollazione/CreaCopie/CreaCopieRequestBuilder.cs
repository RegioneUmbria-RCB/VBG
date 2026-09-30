using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.CreaCopie
{
    public class CreaCopieRequestBuilder : IJIrideDocInRequestBuilder<CreaCopieInfo>
    {
        private VerticalizzazioneProtocolloJIride _verticalizzazione;
        private readonly string _ruolo;
        private readonly string _uo;
        private readonly string _proxyAddress;
        private readonly TipoAssegnazione _tipologiaAssegnazionee;
        private readonly LeggiProtocolloResponse _protocolloLetto;
        private readonly string _operatore;

        public CreaCopieRequestBuilder(VerticalizzazioneProtocolloJIride verticalizzazione, string operatore, string ruolo, string uo, string proxyAddress, LeggiProtocolloResponse protocolloLetto)
        {

            this._verticalizzazione = verticalizzazione;
            this._ruolo = ruolo;
            this._uo = uo;
            this._proxyAddress = proxyAddress;
            this._tipologiaAssegnazionee = TipoAssegnazione.CONOSCENZA;
            this._protocolloLetto = protocolloLetto;
            this._operatore = operatore;
        }

        public CreaCopieInfo Build()
        {
            return new CreaCopieInfo
            {
                AOO = this._verticalizzazione.Aoo,
                CodiceAmministrazione = this._verticalizzazione.Codiceamministrazione,
                Url = this._verticalizzazione.Url,
                Request = new CreaCopieInXml 
                {
                    AnnoProtocollo = this._protocolloLetto.DocumentoOut.AnnoProtocollo.ToString(),
                    NumeroProtocollo = this._protocolloLetto.DocumentoOut.NumeroProtocollo.ToString(),
                    UODestinatarie = new UODestinatariaXml[]
                        {
                        new UODestinatariaXml
                        {
                            Carico = this._uo,
                            TipoAssegnazione = this._tipologiaAssegnazionee.ToString()
                        }
                        },
                    Utente = this._operatore,
                    Ruolo = this._ruolo
                }
            };
        }
    }
}
