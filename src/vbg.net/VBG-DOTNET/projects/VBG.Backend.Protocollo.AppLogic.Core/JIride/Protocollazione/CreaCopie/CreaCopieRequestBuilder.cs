using VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.CreaCopie
{
    public class CreaCopieRequestBuilder : IJIrideRequestBuilder<CreaCopieInfo>
    {
        public static class Constants
        {
            public const string conoscenza = "conoscenza";
        }

        private VerticalizzazioneProtocolloJIride _verticalizzazione;
        private readonly string _ruolo;
        private readonly string _uo;
        private readonly string _proxyAddress;
        private readonly LeggiProtocolloResponse _protocolloLetto;
        private readonly string _operatore;

        private ProtocolloLogs _logs;

        public CreaCopieRequestBuilder(VerticalizzazioneProtocolloJIride verticalizzazione, string operatore, string ruolo, string uo, string proxyAddress, LeggiProtocolloResponse protocolloLetto, ProtocolloLogs logs)
        {

            this._verticalizzazione = verticalizzazione;
            this._ruolo = ruolo;
            this._uo = uo;
            this._proxyAddress = proxyAddress;
            this._protocolloLetto = protocolloLetto;
            this._operatore = operatore;
            this._logs = logs;
        }

        public CreaCopieInfo Build()
        {

            var assegnazione = String.IsNullOrEmpty(this._verticalizzazione.TipoAssegnazioneCopiaDef) ? Constants.conoscenza : this._verticalizzazione.TipoAssegnazioneCopiaDef;

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
                            TipoAssegnazione = assegnazione
                        }
                    },
                    Utente = this._operatore,
                    Ruolo = this._ruolo
                }
            };
        }
    }
}
