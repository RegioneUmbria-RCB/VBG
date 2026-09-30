using System;
using System.Collections.Generic;
using System.Linq;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca
{
    public class RicercaFascicoliResponse
    {
        public bool Ok { get; private set; }
        public string Messaggio { get; private set; }
        public IEnumerable<WSRicercaFascicolo> Fascicoli { get; set; }

        internal static RicercaFascicoliResponse FromWsRicercaFascicoloResponse(WsRicercaFascicoloResponse response)
        {
            if (response == null || response.GetInterrogazioneFascicoloResult == null)
            {
                return new RicercaFascicoliResponse
                {
                    Ok = false,
                    Messaggio = "Nessuna risposta dal servizio di ricerca fascicoli"
                };
            }

            if (!String.IsNullOrEmpty(response.GetInterrogazioneFascicoloResult.ErrorCode))
            {
                return new RicercaFascicoliResponse
                {
                    Ok = false,
                    Messaggio = $"Errore durante la ricerca del fascicolo: {response.GetInterrogazioneFascicoloResult.Message} ({response.GetInterrogazioneFascicoloResult.ErrorCode})"
                };
            }

            if (response.GetInterrogazioneFascicoloResult.NumResult == 0)
            {
                return new RicercaFascicoliResponse
                {
                    Ok = true,
                    Fascicoli = new List<WSRicercaFascicolo>()
                };
            }

            return new RicercaFascicoliResponse
            {
                Ok = true,
                Fascicoli = response
                                .GetInterrogazioneFascicoloResult
                                .SeqFascicolo
                                .Fascicolo
                                .Select(x => new WSRicercaFascicolo
                                {
                                    Id = x.IdFascicolo,
                                    Anno = x.Anno,
                                    DataInizio = DateTime.ParseExact(x.DataInizio, "dd-MM-yyyy", null),
                                    EtichettaClassificazioneEstesa = x.EtichettaClassificazioneEstesa,
                                    NumeroFascicolo = x.NumeroFascicolo,
                                    Oggetto = x.Oggetto
                                })
            };
        }
    }
}