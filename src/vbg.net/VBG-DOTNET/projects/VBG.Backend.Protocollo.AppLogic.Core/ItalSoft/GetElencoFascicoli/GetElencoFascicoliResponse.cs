using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class GetElencoFascicoliResponse
    {
        public Esito Esito { get; private set; }
        public IEnumerable<DettaglioFascicolo> Fascicoli { get; private set; }

        internal static GetElencoFascicoliResponse FromdettaglioFascicolo(dettaglioFascicolo[] response, messageResult messageResult)
        {
            if (response == null)
            {
                return new GetElencoFascicoliResponse
                {
                    Esito = Esito.FromFascicoloMessageResult(messageResult)
                };
            }

            return new GetElencoFascicoliResponse
            {
                Esito = Esito.FromFascicoloMessageResult(messageResult),
                Fascicoli = response
                                .Select(x => DettaglioFascicolo.FromdettaglioFascicolo(x))
            };
        }
    }
}
