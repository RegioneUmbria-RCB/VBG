using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	internal interface IVerificaaAlbero
	{
		bool PuoAnalizzare(IIntervento intervento);
		bool GetRisultato(IIntervento intervento);
	}
}
