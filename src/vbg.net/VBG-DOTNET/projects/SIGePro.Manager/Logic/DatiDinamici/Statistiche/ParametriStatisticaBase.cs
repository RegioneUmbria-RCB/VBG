using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche
{
	public partial class ParametriStatisticaBase
	{
		public class Intervallo<T>
		{
			public T Inizio{get;set;}
			public T Fine { get;set; }

			public Intervallo()
			{
				Inizio = default(T);
				Fine = default(T);
			}
		}

		public DsFiltriStatisticheDatiDinamici FiltriDatiDinamici{get;set;}
	}
}
