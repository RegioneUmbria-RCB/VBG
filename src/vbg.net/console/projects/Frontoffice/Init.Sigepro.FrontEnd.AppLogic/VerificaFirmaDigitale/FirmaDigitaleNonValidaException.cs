using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale
{
	public class FirmaDigitaleNonValidaException: Exception
	{
		public FirmaDigitaleNonValidaException(string message): base( message)
		{

		}
	}
}
