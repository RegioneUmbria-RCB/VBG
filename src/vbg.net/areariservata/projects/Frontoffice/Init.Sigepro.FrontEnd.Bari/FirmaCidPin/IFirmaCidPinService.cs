using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.FirmaCidPin
{
	public interface IFirmaCidPinService
	{
		byte[] Firma(CidPinSignRequest request);
	}
}
