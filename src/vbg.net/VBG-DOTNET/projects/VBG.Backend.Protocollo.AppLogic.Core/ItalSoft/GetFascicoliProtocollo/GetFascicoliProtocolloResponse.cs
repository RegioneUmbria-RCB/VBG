using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetFascicoliProtocollo
{
    public class GetFascicoliProtocolloResponse
    {
        public Esito Esito { get; private set; }
        public IEnumerable<FascicoloProtocollo> Fascicoli { get; private set; }

        internal static GetFascicoliProtocolloResponse FromdettaglioFascicolazione(dettaglioFascicolazione[] response, messageResult messageResult)
        {
            if (response == null)
            {
                return new GetFascicoliProtocolloResponse
                {
                    Fascicoli = new List<FascicoloProtocollo>(),
                    Esito = Esito.FromFascicoloMessageResult(messageResult)
                };
            }

            return new GetFascicoliProtocolloResponse
            {
                Esito = Esito.FromFascicoloMessageResult(messageResult),
                Fascicoli = response
                                .Select(x => new FascicoloProtocollo
                                {
                                    Codice = x.codiceFascicolo,
                                    CodiceSottoFascicolo = x.codiceSottofascicolo,
                                    Descrizione = x.descrizioneFascicolo,
                                    DescrizioneSottoFascicolo = x.descrizioneSottofascicolo,
                                    Principale = x.principale == "1",
                                    Titolario = x.titolario
                                })
            };
        }
    }
}
