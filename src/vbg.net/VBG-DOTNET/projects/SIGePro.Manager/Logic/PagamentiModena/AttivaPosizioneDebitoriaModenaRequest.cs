using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.PagamentiModena
{
    public class AttivaPosizioneDebitoriaModenaRequest
    {
        private List<RataModena> _rate = new List<RataModena>();

        public int NumeroRate => this._rate.Count;
        public IEnumerable<RataModena> Rate => this._rate;

        public readonly int CodiceIstanza;
        public readonly int CausaleId;

        public AttivaPosizioneDebitoriaModenaRequest(int codiceIstanza, int causaleId)
        {
            CodiceIstanza = codiceIstanza;
            CausaleId = causaleId;
        }

        public void AggiungiRata(double importo, DateTime dataScadenza)
        {
            this._rate.Add(new RataModena(this._rate.Count + 1, importo, dataScadenza));
        }

        internal object ToDebugString()
        {
            throw new NotImplementedException();
        }
    }
}