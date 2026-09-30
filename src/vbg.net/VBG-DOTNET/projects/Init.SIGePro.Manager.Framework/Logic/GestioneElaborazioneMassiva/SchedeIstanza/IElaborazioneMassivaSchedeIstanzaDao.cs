using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    public interface IElaborazioneMassivaSchedeIstanzaDao
    {
        bool IsElaborazioneInCorso(int idElaborazione);
        IEnumerable<PraticaDaElaborare> GetPraticheDaElaborare(int idElaborazione);
        IEnumerable<SchedaDaElaborare> GetSchedeDaElaborare(int idElaborazione);
        bool PraticaContieneScheda(int codiceIstanza, int idSchedaDinamica);
        void ImpostaErroriElaborazione(int idElaborazione, int codiceIstanza, IEnumerable<string> messaggi);
        void AvviaElaborazione(int idElaborazione);
        void TerminaElaborazione(int idElaborazione);
        void AvviaElaborazioneIstanza(int idElaborazione, int codiceIstanza);
        void TerminaElaborazioneIstanza(int idElaborazione, int codiceIstanza);
    }
}
