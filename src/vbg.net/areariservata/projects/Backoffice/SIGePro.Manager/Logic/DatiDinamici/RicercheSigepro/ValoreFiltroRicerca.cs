// -----------------------------------------------------------------------
// <copyright file="ValoreFiltroRicerca.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.DatiDinamici.RicercheSigepro
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    public class FiltriRicerca
    {
        List<ValoreFiltroRicerca> _filtri;

        public FiltriRicerca(IEnumerable<ValoreFiltroRicerca> filtri)
        {
            this._filtri = filtri.ToList();
        }

        public string GetValoreCampo(string nomeCampo, string defValue = "")
        {
            var filtro = this._filtri.Where(x => x.nome == nomeCampo).FirstOrDefault();

            if (filtro == null)
            {
                return defValue;
            }

            return filtro.val;
        }
    }

    public class ValoreFiltroRicerca
    {
        public string nome { get; set; }
        public string val { get; set; }
    }
}
