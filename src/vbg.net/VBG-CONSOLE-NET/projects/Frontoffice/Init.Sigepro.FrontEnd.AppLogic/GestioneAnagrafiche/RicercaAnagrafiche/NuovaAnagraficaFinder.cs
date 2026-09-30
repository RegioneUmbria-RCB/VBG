// -----------------------------------------------------------------------
// <copyright file="NuovaAnagraficaFinder.cs" company="">
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
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;

	internal class NuovaAnagraficaFinder : AbstractAnagrafeFinder
	{
		DomandaOnline _domanda;

		public NuovaAnagraficaFinder(DomandaOnline domanda )
		{
			this._domanda = domanda;
		}

		internal override AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva)
		{
			return AnagraficaDomanda.DaCodiceFiscaleTipoPersona(tipoPersona, codiceFiscalePartitaIva);
			/*
			this._domanda.WriteInterface.Anagrafiche.Crea(tipoPersona, codiceFiscalePartitaIva);

			var tmpAnagrafica = this._domanda.ReadInterface.Anagrafiche.FindByRiferimentiSoggetto(tipoPersona, codiceFiscalePartitaIva);

			this._domanda.WriteInterface.Anagrafiche.Elimina(tmpAnagrafica.Id.Value);
			*/
		}
	}
}
