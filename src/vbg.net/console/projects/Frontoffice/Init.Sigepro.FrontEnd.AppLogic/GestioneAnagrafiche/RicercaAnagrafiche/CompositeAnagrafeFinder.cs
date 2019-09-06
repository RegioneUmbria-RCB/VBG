// -----------------------------------------------------------------------
// <copyright file="CompositeAnagrafeFinder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
	using CuttingEdge.Conditions;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

	internal class CompositeAnagrafeFinder : AbstractAnagrafeFinder
	{
		IEnumerable<AbstractAnagrafeFinder> _finders;

		public CompositeAnagrafeFinder(IEnumerable<AbstractAnagrafeFinder> finders)
		{
			Condition.Requires(finders, "finders").IsNotNull()
												  .IsNotEmpty();

			_finders = finders;
		}

		internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
		{
			foreach (var finder in _finders)
			{
				var result = finder.Find(tipoPersona, codiceFiscalePartitaIva);

				if (result != null)
					return result;
			}

			throw new InvalidOperationException("Non è stato possibile trovare un anagrafica per il codice fiscale/p.iva " + codiceFiscalePartitaIva + " e tipo soggetto " + tipoPersona.ToString());
		}
	}
}
