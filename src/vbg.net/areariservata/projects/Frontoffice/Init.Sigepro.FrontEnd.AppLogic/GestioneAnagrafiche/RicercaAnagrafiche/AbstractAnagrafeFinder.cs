// -----------------------------------------------------------------------
// <copyright file="AbstractAnagrafeFinder.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.RicercaAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

	internal abstract class AbstractAnagrafeFinder
	{
		internal abstract AnagraficaDomanda Find(TipoPersonaEnum tipoPersona, string codiceFiscalePartitaIva);
	}
}
