using FascicolazioneItalSoftService;
using messageResult = FascicolazioneItalSoftService.messageResult;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.FascicolaProtocollo
{
    public class FascicolaProtocolloResponse
    {
        public Esito Esito { get; private set; }
        public string CodiceProtocollo { get; private set; }
        public string CodiceFascicolo { get; private set; }
        public string CodiceSottoFascicolo { get; private set; }

        internal static FascicolaProtocolloResponse FromretFascicolo(retFascicolo response, messageResult messageResult)
        {
            if (response == null)
            {
                return new FascicolaProtocolloResponse
                {
                    Esito = Esito.FromFascicoloMessageResult(messageResult)
                };
            }

            return new FascicolaProtocolloResponse
            {
                Esito = Esito.FromFascicoloMessageResult(messageResult),
                CodiceProtocollo = response.codiceProtocollo,
                CodiceFascicolo = response.codiceFascicolo,
                CodiceSottoFascicolo = response.codiceSottoFascicolo
            };
        }
    }
}