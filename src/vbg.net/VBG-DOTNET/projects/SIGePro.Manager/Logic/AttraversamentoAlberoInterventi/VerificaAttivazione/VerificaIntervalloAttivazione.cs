using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	internal class VerificaIntervalloAttivazione: IVerificaaAlbero
	{
		public bool PuoAnalizzare(IIntervento intervento)
		{
			return intervento.DataInizioAttivazione.HasValue;
		}

		public bool GetRisultato(IIntervento intervento)
		{
			return DateTime.Now >= intervento.DataInizioAttivazione && DateTime.Now <= intervento.DataFineAttivazione;
		}
	}
}
