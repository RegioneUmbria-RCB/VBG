using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader
{
	public class NullUpgrader : IDatiDomandaUpgrader
	{
		public byte[] Ugrade(byte[] dati)
		{
			return dati;
		}
	}
}
