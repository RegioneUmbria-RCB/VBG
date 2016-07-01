using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.Core.Config
{
	public class ParametriServizioTributi
	{
		internal string UrlServizioImu { get; set; }
		internal string UrlServizioFirmaCidPin { get; set; }
		internal string UrlServizioTares { get; set; }
		internal string UrlServizioTasi { get; set; }
		internal string IdentificativoUtente { get; set; }
		internal string Password { get; set; }
        internal string CodiceFiscaleCafFittizio { get; set; }
        internal string EmailCafFittizio { get; set; }
	}
}
