using FascicolazioneItalSoftService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItalSoft.GetElencoFascicoli
{
    public class DettaglioFascicolo
    {
        public string Codice { get; internal set; }
        public string CodiceSerie { get; internal set; }
        public string DataChiusura { get; internal set; }
        public string Data { get; internal set; }
        public string Descrizione { get; internal set; }
        public string UfficioResponsabile { get; internal set; }
        public string Natura { get; internal set; }
        public string NomeResponsabile { get; internal set; }
        public string ProgressivoSerie { get; internal set; }
        public string Responsabile { get; internal set; }
        public string CodiceClassifica { get; internal set; }
        public string Classifica { get; internal set; }
        public string CodiceUfficioResponsabile { get; internal set; }

        internal static DettaglioFascicolo FromdettaglioFascicolo(dettaglioFascicolo dettaglio)
        {
            return new DettaglioFascicolo
            {
                Codice = dettaglio.codiceFascicolo,
                CodiceSerie = dettaglio.codiceSerie,
                DataChiusura = dettaglio.dataChiusuraFascicolo,
                Data = dettaglio.dataFascicolo,
                Descrizione = dettaglio.descrizioneFascicolo,
                CodiceUfficioResponsabile = dettaglio.ufficioResopnsabile,
                UfficioResponsabile = dettaglio.descUfficioResponsabile,
                Natura = dettaglio.naturaFascicolo,
                NomeResponsabile = dettaglio.nomeResponsabile,
                ProgressivoSerie = dettaglio.progressivoSerie,
                Responsabile = dettaglio.responsabile,
                CodiceClassifica = dettaglio.titolario,
                Classifica = dettaglio.titolarioDescrizione,
            };
        }
    }
}
