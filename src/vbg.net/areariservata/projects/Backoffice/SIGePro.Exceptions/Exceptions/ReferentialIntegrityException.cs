using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Exceptions
{
	public partial class ReferentialIntegrityException : BaseException
	{
		public ReferentialIntegrityException(string nomeTabella, string valoriDelRiferimento)
			: base("Il record contiene riferimenti nella tabella " + nomeTabella + 
					(String.IsNullOrEmpty(valoriDelRiferimento) ? 
						String.Empty : 
						", dettagli: " + valoriDelRiferimento)) { }
		public ReferentialIntegrityException(string nomeTabella) : base("Il record contiene riferimenti nella tabella " + nomeTabella) { }
	}
}
