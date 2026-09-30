using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca
{
    public class WSRicercaFascicolo
    {
        public int? Id { get; internal set; }
        public int Anno { get; internal set; }
        public string EtichettaClassificazioneEstesa { get; internal set; }
        public DateTime DataInizio { get; internal set; }
        public int NumeroFascicolo { get; internal set; }
        public string Oggetto { get; internal set; }
    }
}
