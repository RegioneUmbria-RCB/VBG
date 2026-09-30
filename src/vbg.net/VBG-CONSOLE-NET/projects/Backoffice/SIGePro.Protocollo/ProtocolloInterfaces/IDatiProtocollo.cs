using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.ProtocolloInterfaces
{
    /// <summary>
    /// Questa Interfaccia serve ad individuare i mittenti ed i destinatari, tutti i necessari controlli sul flusso vengono fatti 
    /// sulle classi che implementano questa interfaccia.
    /// </summary>
    public interface IDatiProtocollo
    {
        string Uo { get; }
        string Ruolo { get; }
        string DescrizioneAmministrazione { get; }
        Amministrazioni Amministrazione { get; }
        string Flusso { get; }

        List<ProtocolloAnagrafe> AnagraficheProtocollo { get; }

        /// <summary>
        /// Usato per la protocollazione mista, ossia quando i destinatari / mittenti (protocollo in parteza / arrivo) siano sia anagrafiche che amministrazioni interne.
        /// </summary>
        List<Amministrazioni> AmministrazioniProtocollo { get; }

        /// <summary>
        /// Recupera solamente la lista delle amministrazioni interne presenti, quelle con PROT_UO valorizzato.
        /// </summary>
        List<Amministrazioni> AmministrazioniInterne { get; }

        /// <summary>
        /// Recupera solamente le amministrazioni esterne, quelle con PROT_UO e PROT_RUOLO non valorizzati.
        /// </summary>
        List<Amministrazioni> AmministrazioniEsterne { get; }

        /// <summary>
        /// Sta ad indicare se, tra le amministrazioni (proprietà AmministrazioniProtocollo) ne sono presenti anche interne.
        /// </summary>
        bool IsAmministrazioneInterna { get; }

        DatiProtocolloIn ProtoIn { get; }

        /// <summary>
        /// Indica eventuali ulteriori destinatari relativi ad uffici interni (amministrazioni con valorizzata la uo)
        /// </summary>
        List<Amministrazioni> AltriDestinatariInterni { get; }
    }
}
