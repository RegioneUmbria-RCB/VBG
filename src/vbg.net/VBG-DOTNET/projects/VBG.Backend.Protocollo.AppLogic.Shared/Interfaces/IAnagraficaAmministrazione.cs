using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    public interface IAnagraficaAmministrazione
    {
        string Codice { get; }
        string Nome { get; }
        string Cognome { get; }
        string CodiceFiscale { get; }
        string PartitaIva { get; }
        string Denominazione { get; }
        string Indirizzo { get; }
        string Email { get; }
        string Pec { get; }
        string Tipo { get; }
        string Sesso { get; }
        string NomeCognome { get; }
        ProtocolloComune ComuneResidenza { get; }
        string Localita { get; }
        string Provincia { get; }
        string Cap { get; }
        string CodiceFiscalePartitaIva { get; }
        string ModalitaTrasmissione { get; }
        string MezzoInvio { get; }
        string Comune { get; }
        string CodiceIstatResidenza { get; }
        string Telefono { get; }
        string Fax { get; }
        string TipologiaAnagrafica { get; }

        string Uo { get; }

    }
}
