using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class ParametriSchedaCittadiniExtracomunitari : IParametriConfigurazione
	{
		public readonly int? IdSchedaDinamica;
		public readonly string NomeScheda;
		public readonly bool RichiedeFirma;

		public bool EsisteSchedaDinamicaPerCittadiniExtracomunitari
		{
			get { return this.IdSchedaDinamica.HasValue; }
		}

		public ParametriSchedaCittadiniExtracomunitari(int? idSchedaDinamica, string nomeScheda,bool richiedeFirma)
		{
			this.IdSchedaDinamica = idSchedaDinamica;
			this.NomeScheda = nomeScheda;
			this.RichiedeFirma = richiedeFirma;
		}
	}
}
