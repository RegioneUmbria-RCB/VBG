using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	public class AttivazioneInterventoService
	{
		public enum TipoPubblicazione
		{
			Frontoffice,
			AreaRiservata
		}

		AlberoProcMgr _mgr;
		string _idComune;

		public AttivazioneInterventoService(AlberoProcMgr mgr, string idComune)
		{
			this._mgr = mgr;
			this._idComune = idComune;
		}

		public bool IsInterventoAttivo(TipoPubblicazione tipoPubblicazione,LivelloAutenticazioneBOEnum livelloAutenticazione, int codiceIntervento)
		{
			var interventi = this._mgr
								 .GetAlberaturaIntervento(this._idComune, codiceIntervento)
								 .Select( x => new InterventoReadOnly(x));

			var verifica = (InterventoPubblicato)new InterventoPubblicatoNellAreaRiservata(interventi);

			if (tipoPubblicazione == TipoPubblicazione.Frontoffice)
			{
				verifica = (InterventoPubblicato)new InterventoPubblicatoNelFrontoffice(interventi);
			}

			if(!verifica.IsTrue())
			{
				return false;
			}

            verifica = (InterventoPubblicato)new InterventoAccessibileConLivelloAutenticazione(interventi, livelloAutenticazione);

			verifica = (InterventoPubblicato)new InterventoAttivo(interventi);

			return verifica.IsTrue();
		}
	}
}
