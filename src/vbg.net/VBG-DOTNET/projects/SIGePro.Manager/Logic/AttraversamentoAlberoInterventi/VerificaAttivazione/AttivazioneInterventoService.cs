using Init.SIGePro.Manager.DTO.Common;
using PersonalLib2.Data;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{

    public class AttivazioneInterventoService
    {
        private readonly DataBase _db;
        private readonly string _idComune;

        public AttivazioneInterventoService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(TipoPubblicazione tipoPubblicazione, LivelloAutenticazioneBOEnum livelloAutenticazione, int codiceIntervento, string codiceComune)
        {
            var mgr = new AlberoProcMgr(this._db);
            var interventi = mgr.GetAlberaturaIntervento(this._idComune, codiceIntervento)
                                .Select(x => new InterventoReadOnly(x));

            InterventoPubblicato verifica = new InterventoPubblicatoNellAreaRiservata(interventi);

            if (tipoPubblicazione == TipoPubblicazione.Frontoffice)
            {
                verifica = new InterventoPubblicatoNelFrontoffice(interventi);
            }

            if (!verifica.IsTrue())
            {
                return new RisultatoVerificaAccessoIntervento(TipoAccessibilitaIntervento.NonPubblicato, verifica.GetMessaggioErrore());
            }

            verifica = new InterventoAccessibileConLivelloAutenticazione(interventi, livelloAutenticazione);

            if (!verifica.IsTrue())
            {
                return new RisultatoVerificaAccessoIntervento(TipoAccessibilitaIntervento.LivelloAutenticazioneNonSufficiente, verifica.GetMessaggioErrore());
            }

            verifica = new InterventoAttivo(interventi);

            if (!verifica.IsTrue())
            {
                return new RisultatoVerificaAccessoIntervento(TipoAccessibilitaIntervento.NonPubblicato, verifica.GetMessaggioErrore());
            }

            verifica = new InterventoAttivoSuComune(interventi, this._db, this._idComune, codiceComune);

            if (!verifica.IsTrue())
            {
                return new RisultatoVerificaAccessoIntervento(TipoAccessibilitaIntervento.NonPubblicato, verifica.GetMessaggioErrore());
            }

            return new RisultatoVerificaAccessoIntervento(TipoAccessibilitaIntervento.Accessibile);
        }

        public int GetLivelloDiAutenticazioneRichiesto(int codiceIntervento)
        {
            var mgr = new AlberoProcMgr(this._db);

            var interventi = mgr.GetAlberaturaIntervento(this._idComune, codiceIntervento)
                                 .Select(x => new InterventoReadOnly(x));

            var iterator = new InterventiReverseEnumerator<InterventoReadOnly>(interventi);

            while (iterator.MoveNext())
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
