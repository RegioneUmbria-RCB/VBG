using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit;
using Init.SIGePro.Sit.IntegrazioneSitBari;

namespace SIGePro.SIT.Tests.IntegrazioneSitBari
{
	public class SitBariTestClass : SIT_BARI
	{
		ISitNautilusBariService _mockOrStub;

		public SitBariTestClass(ISitNautilusBariService mockOrStub)
		{
			this._mockOrStub = mockOrStub;
		}

		protected override ISitNautilusBariService CreaServizioSit()
		{
			return this._mockOrStub;
		}
	}
}
