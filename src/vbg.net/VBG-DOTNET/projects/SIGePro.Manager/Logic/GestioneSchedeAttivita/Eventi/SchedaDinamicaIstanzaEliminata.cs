using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi
{
    public class SchedaDinamicaIstanzaEliminata : IEvent
    {
        public readonly int CodiceIstanza;
        public readonly int IdScheda;

        public SchedaDinamicaIstanzaEliminata(int codiceIstanza, int idScheda)
        {
            this.CodiceIstanza = codiceIstanza;
            this.IdScheda = idScheda;
        }
    }
}
