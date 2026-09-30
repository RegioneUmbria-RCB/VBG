using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    public interface IDatiProtocollo
    {
        string Uo { get; }
        string Ruolo { get; }
        string DescrizioneAmministrazione { get; }
        ProtocolloAmministrazioni Amministrazione { get; }
        string Flusso { get; }

        List<ProtocolloAnagrafe> AnagraficheProtocollo { get; }

        /// <summary>
        /// Usato per la protocollazione mista, ossia quando i destinatari / mittenti (protocollo in parteza / arrivo) siano sia anagrafiche che amministrazioni interne.
        /// </summary>
        List<ProtocolloAmministrazioni> AmministrazioniProtocollo { get; }

        /// <summary>
        /// Recupera solamente la lista delle amministrazioni interne presenti, quelle con PROT_UO valorizzato.
        /// </summary>
        List<ProtocolloAmministrazioni> AmministrazioniInterne { get; }

        /// <summary>
        /// Recupera solamente le amministrazioni esterne, quelle con PROT_UO e PROT_RUOLO non valorizzati.
        /// </summary>
        List<ProtocolloAmministrazioni> AmministrazioniEsterne { get; }

        /// <summary>
        /// Sta ad indicare se, tra le amministrazioni (proprietà AmministrazioniProtocollo) ne sono presenti anche interne.
        /// </summary>
        bool IsAmministrazioneInterna { get; }

        DatiProtocolloIn ProtoIn { get; }

        /// <summary>
        /// Indica eventuali ulteriori destinatari relativi ad uffici interni (amministrazioni con valorizzata la uo)
        /// </summary>
        List<ProtocolloAmministrazioni> AltriDestinatariInterni { get; }
    }
}
