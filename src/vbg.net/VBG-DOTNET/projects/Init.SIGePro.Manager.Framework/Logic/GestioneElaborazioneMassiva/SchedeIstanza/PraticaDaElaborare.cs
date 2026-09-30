using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    public class PraticaDaElaborare
    {
        public readonly int IdElaborazione;
        public readonly int CodiceIstanza;

        public IEnumerable<string> Errori => this._errori;

        public bool HaErrori => this._errori.Count > 0;

        private List<string> _errori = new List<string>();

        public PraticaDaElaborare(int idElaborazione, int codiceIstanza)
        {
            IdElaborazione = idElaborazione;
            CodiceIstanza = codiceIstanza;
        }


        public void AggiungiErrore(string messaggio)
        {
            this._errori.Add(messaggio);
        }
    }
}