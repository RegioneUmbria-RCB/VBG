using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.Segnatura;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    /// <summary>
    /// Quest'interfaccia è stata creata perchè i dati dei mittenti / destinatari possono arrivare, in base alla versione, sulle classi Mittente e Destinatario 
    /// oppure sulle classi MittenteMulti[] e DestinataroMulti[], in entrambi i casi le classi hanno le stesse proprietà che vengono esposte in quest'interfaccia.
    /// </summary>
    public interface IMittentiDestinatariVersioneProtocollo
    {
        Amministrazione AmministrazioneProtocollo { get; }
        TipoDestinatario TipoDestinatarioProtocollo { get; }
        Persona PersonaProtocollo { get; }
        IndirizzoPostale IndirizzoPostaleProtocollo { get; }

    }
}
