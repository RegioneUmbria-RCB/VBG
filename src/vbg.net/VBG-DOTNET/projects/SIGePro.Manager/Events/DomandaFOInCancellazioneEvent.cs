using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Vbg.EventBus.Abstractions;

namespace Init.SIGePro.Manager.Events
{
    public class DomandaFOInCancellazioneEvent: IEvent
    {
        public readonly int IdDomanda;

        public DomandaFOInCancellazioneEvent( int idDomanda )
        {
            this.IdDomanda = idDomanda;
        }
    }
}
