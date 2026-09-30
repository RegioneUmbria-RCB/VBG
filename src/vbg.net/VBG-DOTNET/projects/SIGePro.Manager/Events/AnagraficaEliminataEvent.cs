using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Events
{
    public class AnagraficaEliminataEvent : IEvent
    {
        public AnagraficaEliminataEvent(int codiceAnagrafe)
        {
            CodiceAnagrafe = codiceAnagrafe;
        }

        public int CodiceAnagrafe { get; }
    }
}
