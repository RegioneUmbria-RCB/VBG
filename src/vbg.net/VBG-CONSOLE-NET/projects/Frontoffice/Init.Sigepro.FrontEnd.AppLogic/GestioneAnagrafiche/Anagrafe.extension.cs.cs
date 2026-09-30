using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService
{
	public partial class Anagrafe
	{
		public override string ToString()
		{
			string n = this.NOMINATIVO;

			if (!string.IsNullOrEmpty(this.NOME))
				n += " " + this.NOME;

			return n;
		}
	}
}