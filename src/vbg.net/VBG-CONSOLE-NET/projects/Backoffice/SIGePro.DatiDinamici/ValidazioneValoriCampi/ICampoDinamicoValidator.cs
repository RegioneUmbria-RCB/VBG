using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Globalization;

namespace Init.SIGePro.DatiDinamici.ValidazioneValoriCampi
{
	internal interface ICampoDinamicoValidator
	{
		IEnumerable<ErroreValidazione> GetErroriDiValidazione();
	}
}
