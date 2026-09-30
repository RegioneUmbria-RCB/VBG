using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneTipiSoggetto
{
    public class SsuLogicaSincronizzazioneTipiSoggetto : ILogicaSincronizzazioneTipiSoggetto
    {
        readonly Dictionary<int, TipoSoggetto> _soggettiById;

        public SsuLogicaSincronizzazioneTipiSoggetto(IEnumerable<TipoSoggetto> tipiSoggetto)
        {
            this._soggettiById = tipiSoggetto.ToDictionary(x => x.Id);
        }

        public void Sincronizza(PresentazioneIstanzaDbV2.ANAGRAFERow row)
        {
            if (row.TIPOSOGGETTO == null)
                return;

            if (this._soggettiById.TryGetValue(row.TIPOSOGGETTO.Value, out var tipoSoggetto))
            {
                row.DescrSoggetto = tipoSoggetto.Descrizione;
                row.FlagTipoSoggetto = this.DecodificaRuolo(tipoSoggetto.Ruolo);
                row.FlagRichiedeAnagraficaCollegata = tipoSoggetto.RichiedeAnagraficaCollegata;
            }
            else
            {
                row.DescrSoggetto = "Attenzione, il tipo soggetto precedentemente selezionato non è più valido, assegnare all'anagrafica un nuovo tipo soggetto";
                row.FlagTipoSoggetto = "";
            }
        }

        private string DecodificaRuolo(FlagRuoloSoggettoEnum ruolo)
        {
            return ruolo switch
            {
                FlagRuoloSoggettoEnum.Richiedente => "R",
                FlagRuoloSoggettoEnum.Tecnico => "T",
                FlagRuoloSoggettoEnum.Azienda => "A",
                _ => "",
            };
        }
    }
}
