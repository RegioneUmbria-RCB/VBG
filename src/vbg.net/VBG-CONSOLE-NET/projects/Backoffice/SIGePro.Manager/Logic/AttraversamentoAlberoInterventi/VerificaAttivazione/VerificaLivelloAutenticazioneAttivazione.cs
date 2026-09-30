using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    class VerificaLivelloAutenticazioneAttivazione : IVerificaaAlbero
    {
        LivelloAutenticazioneBOEnum _livelloUtente;

        public VerificaLivelloAutenticazioneAttivazione(LivelloAutenticazioneBOEnum livelloUtente)
        {
            this._livelloUtente = livelloUtente;
        }

        public bool PuoAnalizzare(IIntervento intervento)
        {
            return intervento.LivelloAutenticazione.HasValue;
        }

        public bool GetRisultato(IIntervento intervento)
        {
            var livelloIntervento = intervento.LivelloAutenticazione.Value;

            return ((int)_livelloUtente) >= livelloIntervento;
        }
    }
}
