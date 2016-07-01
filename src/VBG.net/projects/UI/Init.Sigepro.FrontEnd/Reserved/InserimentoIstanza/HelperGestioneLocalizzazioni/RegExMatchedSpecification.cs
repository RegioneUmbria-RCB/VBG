using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Sigepro.FrontEnd.Infrastructure;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
	public class RegExMatchedSpecification : ISpecification<IRegexVerificabile>
	{
		public bool IsSatisfiedBy(IRegexVerificabile item)
		{
			return item.RegexVerificata();
		}
	}
}