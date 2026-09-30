
using ProtocolloIride2Service;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services
{
    public interface IProtocolloIrideService
    {
        ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn);
        ProtocolloOut InserisciDocumento(ProtocolloIn protocolloIn);
        DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo);
        DocumentoOut LeggiDocumento(int idProtocollo, string operatore, string ruolo);
        string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo);
        string CollegaDocumento(string collegaDocumentoIn);
    }
}
