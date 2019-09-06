// -----------------------------------------------------------------------
// <copyright file="IInvioDomandaStrategy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
	
	public interface IInvioDomandaStrategy
	{
		InvioIstanzaResult Send(DomandaOnline domanda, string pecDestinatario);
	}
}
