using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
	public interface IDatiDomandaUpgrader
	{
		byte[] Ugrade(byte[] dati);
	}
}
