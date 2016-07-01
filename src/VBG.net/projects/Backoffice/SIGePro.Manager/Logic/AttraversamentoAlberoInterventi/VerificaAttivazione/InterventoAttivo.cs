using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	internal class InterventoAttivo : InterventoPubblicato
	{
		internal InterventoAttivo(IEnumerable<IIntervento> alberaturaInterventi) : 
			base(new InterventiReverseEnumerator(alberaturaInterventi.ToList()),new VerificaIntervalloAttivazione())
		{
		}

		protected override bool GetValoreDefault()
		{
			return true;
		}
	}
}
