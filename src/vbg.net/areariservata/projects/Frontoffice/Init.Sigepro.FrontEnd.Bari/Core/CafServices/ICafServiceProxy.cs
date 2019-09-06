// -----------------------------------------------------------------------
// <copyright file="ITaresServiceProxy.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Core.CafServices
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.BariTaresSigeproServiceReference;

	public interface ICafServiceProxy
	{
		bool UtenteAppartieneACaf(string codiceUtente);
		string GetCodiceFiscaleCafDaCodiceFiscaleOperatore(string codiceFiscaleOperatore);
		RiferimentiCaf GetRiferimentiCafDaCodiceFiscaleoperatore(string codiceFiscaleOperatore);
	}
}
