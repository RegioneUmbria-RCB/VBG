using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi
{
    public class SchedaDinamicaRimossaDaAttivita: IEvent
    {
        public int CodiceAttivita { get; }
        public int IdModello { get; }
        public IEnumerable<int> ListaCampiEliminati { get; }

        public SchedaDinamicaRimossaDaAttivita(int codiceAttivita, int idModello, IEnumerable<int> listaCampiEliminati)
        {
            this.CodiceAttivita = codiceAttivita;
            this.IdModello = idModello;
            this.ListaCampiEliminati = listaCampiEliminati;
        }
    }
}
