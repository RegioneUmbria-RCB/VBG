using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.JProtocollo2.Services;
using Init.SIGePro.Protocollo.JProtocollo2.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.JProtocollo2.Protocollazione
{
    public class ProtocollazioneConfiguration
    {
        public IDatiProtocollo DatiProto { get; private set; }
        public ProtocolloService Service { get; private set; }
        public VerticalizzazioniConfiguration Vert { get; private set; }
        public string Operatore { get; private set; }

        public ProtocollazioneConfiguration(IDatiProtocollo datiProto, ProtocolloService wrapper, VerticalizzazioniConfiguration vert, string operatore)
        {
            DatiProto = datiProto;
            Service = wrapper;
            Vert = vert;
            Operatore = operatore;
        }
    }
}
