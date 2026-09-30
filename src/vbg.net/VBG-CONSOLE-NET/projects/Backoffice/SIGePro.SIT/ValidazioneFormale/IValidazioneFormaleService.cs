using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.ValidazioneFormale
{
	internal interface IValidazioneFormaleService
	{
		bool Valida(Init.SIGePro.Sit.Data.Sit sit);
	}
}
