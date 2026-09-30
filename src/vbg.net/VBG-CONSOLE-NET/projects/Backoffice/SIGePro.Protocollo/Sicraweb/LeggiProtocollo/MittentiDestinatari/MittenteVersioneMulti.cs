using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    public class MittenteVersioneMulti : IMittentiDestinatariVersioneProtocollo
    {
        MittenteMulti _mittente;

        public MittenteVersioneMulti(MittenteMulti mittente)
        {
            _mittente = mittente;
        }

        public Amministrazione AmministrazioneProtocollo
        {
            get { return _mittente.Amministrazione; }
        }

        public TipoDestinatario TipoDestinatarioProtocollo
        {
            get { return _mittente.TipoDestinatario; }
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
