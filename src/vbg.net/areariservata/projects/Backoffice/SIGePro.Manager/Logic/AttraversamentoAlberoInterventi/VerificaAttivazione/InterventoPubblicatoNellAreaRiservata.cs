using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	internal class InterventoPubblicatoNellAreaRiservata : InterventoPubblicato
	{
		public InterventoPubblicatoNellAreaRiservata(IEnumerable<IIntervento> alberaturaInterventi) : 
			base(new InterventiReverseEnumerator<IIntervento>(alberaturaInterventi),new VerificaPubblicazioneAreaRiservata())
		{
		}
	}
}
