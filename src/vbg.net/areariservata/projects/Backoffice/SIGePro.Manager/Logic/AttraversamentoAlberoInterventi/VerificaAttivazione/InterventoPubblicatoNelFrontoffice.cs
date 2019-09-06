using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaPubblicazione;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	internal class InterventoPubblicatoNelFrontoffice : InterventoPubblicato
	{
		public InterventoPubblicatoNelFrontoffice(IEnumerable<IIntervento> alberaturaInterventi): 
			base(new InterventiReverseEnumerator<IIntervento>(alberaturaInterventi),new VerificaPubblicazioneFrontoffice())
		{
		}
	}
}
