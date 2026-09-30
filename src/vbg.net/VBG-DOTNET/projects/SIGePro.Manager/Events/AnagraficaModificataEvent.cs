using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Events
{
    public class AnagraficaModificataEvent : IEvent
    {
        public string CodiceFiscale;

        public AnagraficaModificataEvent(string cf)
        {
            CodiceFiscale = cf;
        }
    }
}
