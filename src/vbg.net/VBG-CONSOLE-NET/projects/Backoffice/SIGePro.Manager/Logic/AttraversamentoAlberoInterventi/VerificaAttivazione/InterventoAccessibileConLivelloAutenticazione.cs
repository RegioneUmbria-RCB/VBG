using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    class InterventoAccessibileConLivelloAutenticazione : InterventoPubblicato
	{
        internal InterventoAccessibileConLivelloAutenticazione(IEnumerable<IIntervento> alberaturaInterventi, LivelloAutenticazioneBOEnum livelloAutenticazione) :
            base(new InterventiReverseEnumerator<IIntervento>(alberaturaInterventi), new VerificaLivelloAutenticazioneAttivazione(livelloAutenticazione))
		{
		}

		protected override bool GetValoreDefault()
		{
			return true;
		}
	}
}
