using VBG.DatiDinamici.Agid.WebControls.Controlli.DatiDinamiciSearch;

namespace Init.Sigepro.FrontEnd.CoreServices.DatiDinamici.Ricerche
{
    internal class FiltriRicerca
    {
        private readonly Dictionary<string, ValoreFiltroRicerca> _filtri;

        public FiltriRicerca(IEnumerable<ValoreFiltroRicerca> filtri)
        {
            this._filtri = filtri.ToDictionary(x => x.NomeCampo);
        }

        public string GetValoreCampo(string nomeCampo, string defValue = "")
        {
            if (this._filtri.TryGetValue(nomeCampo, out var filtro))
            {
                return filtro.Valore ?? defValue;
            }

            return defValue;
        }
    }
}
