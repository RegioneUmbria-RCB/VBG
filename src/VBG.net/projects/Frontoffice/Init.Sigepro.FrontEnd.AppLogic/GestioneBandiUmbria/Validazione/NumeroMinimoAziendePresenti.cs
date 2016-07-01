// -----------------------------------------------------------------------
// <copyright file="IAlmeno6AziendePresenti.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBandiUmbria.Validazione
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
    using Init.Sigepro.FrontEnd.Infrastructure;
    using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class NumeroMinimoAziendePresenti : ISpecification<IDomandaOnlineReadInterface>
    {
        int _numeroMinimo;
        string _nomeTipoSoggettoAziendaRichiedenteContributo;
		string _nomeTipoSoggettoAziendaCapofila;

		public NumeroMinimoAziendePresenti(int numeroMinimo, string nomeTipoSoggettoAziendaRichiedenteContributo, string nomeTipoSoggettoAziendaCapofila)
        {
            this._numeroMinimo = numeroMinimo;
			this._nomeTipoSoggettoAziendaRichiedenteContributo = nomeTipoSoggettoAziendaRichiedenteContributo;
			this._nomeTipoSoggettoAziendaCapofila = nomeTipoSoggettoAziendaCapofila;
        }

        public bool IsSatisfiedBy(IDomandaOnlineReadInterface domanda)
        {
            var aziende = domanda.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Descrizione == this._nomeTipoSoggettoAziendaRichiedenteContributo).Union(
					domanda.Anagrafiche.Anagrafiche.Where(x => x.TipoSoggetto.Descrizione == this._nomeTipoSoggettoAziendaCapofila)
				);

            return aziende.Count() >= this._numeroMinimo;
        }
    }
}
