using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	internal interface IConfigurazioneBuilder<T> where T : class,IParametriConfigurazione
	{
		T Build();
	}
}
