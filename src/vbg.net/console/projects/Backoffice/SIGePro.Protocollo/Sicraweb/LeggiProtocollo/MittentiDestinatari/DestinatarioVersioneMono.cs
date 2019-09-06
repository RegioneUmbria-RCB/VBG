using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    public class DestinatarioVersioneMono : IMittentiDestinatariVersioneProtocollo
    {
        Destinatario _destinatario;

        public DestinatarioVersioneMono(Destinatario destinatario)
        {
            _destinatario = destinatario;
        }

        public Amministrazione AmministrazioneProtocollo
        {
            get { return _destinatario.Amministrazione; }
        }

        public TipoDestinatario TipoDestinatarioProtocollo
        {
            get { return TipoDestinatario.Principale; }
        }

        public Persona PersonaProtocollo
        {
            get { return _destinatario.Persona; }
        }

        public IndirizzoPostale IndirizzoPostaleProtocollo
        {
            get { return _destinatario.IndirizzoPostale; }
        }
    }
}
