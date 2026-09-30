using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Events
{
    public class DomandaFOCancellataEvent : IEvent
    {
        public int IdDomanda;

        public DomandaFOCancellataEvent(int idDomanda)
        {
            IdDomanda = idDomanda;
        }
    }
}
