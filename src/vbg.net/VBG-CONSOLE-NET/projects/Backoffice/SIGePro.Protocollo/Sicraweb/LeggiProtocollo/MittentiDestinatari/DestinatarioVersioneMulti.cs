using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    public class DestinatarioVersioneMulti : IMittentiDestinatariVersioneProtocollo
    {
        DestinatarioMulti _destinatario;

        public DestinatarioVersioneMulti(DestinatarioMulti destinatari)
        {
            _destinatario = destinatari;
        }

        public Amministrazione AmministrazioneProtocollo
        {
            get { return _destinatario.Amministrazione; }
        }

        public TipoDestinatario TipoDestinatarioProtocollo
        {
            get { return _destinatario.TipoDestinatario; }
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
