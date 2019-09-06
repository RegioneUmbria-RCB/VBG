using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
	public class FirmaValidaSpecification : ISpecification<EsitoVerificaFirmaDigitale>
	{
		#region ISpecification<EsitoVerificaFirmaDigitale> Members

		public bool IsSatisfiedBy(EsitoVerificaFirmaDigitale item)
		{
			return item.Stato == StatoVerificaFirma.FirmaValida;
		}

		#endregion
	}
}
