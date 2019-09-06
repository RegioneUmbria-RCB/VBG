// -----------------------------------------------------------------------
// <copyright file="DomandaAnagrafeFinder.cs" company="">
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
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

	internal class DomandaAnagrafeFinder : AbstractAnagrafeFinder
	{
		IAnagraficheReadInterface _anagraficheReadInterface;

		public DomandaAnagrafeFinder(IAnagraficheReadInterface anagraficheReadInterface)
		{
			this._anagraficheReadInterface = anagraficheReadInterface;
		}


		internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
		{
			var anagraficaEsistente = this._anagraficheReadInterface.FindByRiferimentiSoggetto(tipoPersona, codiceFiscalePartitaIva);
				
			if (anagraficaEsistente != null)
				return anagraficaEsistente.DuplicaRimuovendoIlTipoSoggetto();

			return null;
		}
	}
}
