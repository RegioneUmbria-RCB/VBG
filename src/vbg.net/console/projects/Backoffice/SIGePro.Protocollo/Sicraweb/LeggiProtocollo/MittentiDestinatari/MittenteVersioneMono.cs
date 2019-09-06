using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    public class MittenteVersioneMono : IMittentiDestinatariVersioneProtocollo
    {
        Mittente _mittente;

        public MittenteVersioneMono(Mittente mittente)
        {
            _mittente = mittente;
        }

        public Amministrazione AmministrazioneProtocollo
        {
            get { return _mittente.Amministrazione; }
        }

        public TipoDestinatario TipoDestinatarioProtocollo
        {
            get { return TipoDestinatario.Principale; }
        }

        public Persona PersonaProtocollo
        {
            get { return _mittente.Persona; }
        }

        public IndirizzoPostale IndirizzoPostaleProtocollo
        {
            get { return _mittente.IndirizzoPostale; }
        }
    }
}
