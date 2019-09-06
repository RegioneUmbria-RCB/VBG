using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	public class AttivazioneInterventoService
	{
        public enum RisultatoVerificaAccessoIntervento
        {
            Accessibile,
            NonPubblicato,
            LivelloAutenticazioneNonSufficiente,
            InterventoNonAttivo
        }


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

        public RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(TipoPubblicazione tipoPubblicazione, LivelloAutenticazioneBOEnum livelloAutenticazione, int codiceIntervento)
		{
			var interventi = this._mgr
								 .GetAlberaturaIntervento(this._idComune, codiceIntervento)
								 .Select( x => new InterventoReadOnly(x));
            
            InterventoPubblicato verifica = new InterventoPubblicatoNellAreaRiservata(interventi);

			if (tipoPubblicazione == TipoPubblicazione.Frontoffice)
			{
				verifica = new InterventoPubblicatoNelFrontoffice(interventi);
			}

			if(!verifica.IsTrue())
			{
                return RisultatoVerificaAccessoIntervento.NonPubblicato;
			}

            verifica = new InterventoAccessibileConLivelloAutenticazione(interventi, livelloAutenticazione);

            if (!verifica.IsTrue())
            {
                return RisultatoVerificaAccessoIntervento.LivelloAutenticazioneNonSufficiente;
            }

			verifica = new InterventoAttivo(interventi);

			if(!verifica.IsTrue())
            {
                return RisultatoVerificaAccessoIntervento.NonPubblicato;
            }

            return RisultatoVerificaAccessoIntervento.Accessibile;
		}

        public int GetLivelloDiAutenticazioneRichiesto(int codiceIntervento)
        {
            var interventi = this._mgr
                     .GetAlberaturaIntervento(this._idComune, codiceIntervento)
                     .Select(x => new InterventoReadOnly(x));

            var iterator = new InterventiReverseEnumerator<InterventoReadOnly>(interventi);

            while(iterator.MoveNext())
            {
                var curr = iterator.Current;

                if (curr.LivelloAutenticazione.HasValue)
                {
                    return curr.LivelloAutenticazione.Value;
                }
            }

            return 0;
        }
    }
}
