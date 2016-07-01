namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;
	using Init.Sigepro.FrontEnd.Infrastructure;

	public class CampoCompilatoSpecification : ISpecification<ICompilazioneVerificabile>
	{
		public bool IsSatisfiedBy(ICompilazioneVerificabile item)
		{
			return item.VerificaCompilazione();
		}
	}
}