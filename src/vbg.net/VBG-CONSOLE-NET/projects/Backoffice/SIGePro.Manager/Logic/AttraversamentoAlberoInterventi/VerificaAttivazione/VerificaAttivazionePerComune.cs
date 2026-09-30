using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    internal class VerificaAttivazionePerComune : IVerificaaAlbero
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly string _codiceComune;

        public VerificaAttivazionePerComune(DataBase db, string idComune, string codiceComune)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            this._db = db ?? throw new ArgumentNullException(nameof(db));
            this._idComune = idComune;
            this._codiceComune = codiceComune;
        }

        public bool GetRisultato(IIntervento intervento)
        {
            return false;
        }

        public bool PuoAnalizzare(IIntervento intervento)
        {
            var mgr = new AlberoProcMgr(this._db);

            var attivo = mgr.VerificaAttivazioneSuComune(this._idComune, intervento.Id, this._codiceComune);

            return !attivo;
        }
    }
}
