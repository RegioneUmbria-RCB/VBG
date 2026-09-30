using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
	public class FirmaValidaSpecification : ISpecification<EsitoVerificaFirmaDigitale>, ISpecificationAsync<EsitoVerificaFirmaDigitale>
	{
		#region ISpecification<EsitoVerificaFirmaDigitale> Members

		public bool IsSatisfiedBy(EsitoVerificaFirmaDigitale item)
		{
			return item.Stato == StatoVerificaFirma.FirmaValida;
		}

        public Task<bool> IsSatisfiedByAsync(EsitoVerificaFirmaDigitale item)
        {
            return Task.FromResult(item.Stato == StatoVerificaFirma.FirmaValida);
        }

        #endregion
    }
}
