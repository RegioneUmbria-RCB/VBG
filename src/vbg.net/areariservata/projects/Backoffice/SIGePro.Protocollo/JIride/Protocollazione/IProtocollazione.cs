using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocollazioneJIrideService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.JIride.Protocollazione
{
    public interface IProtocollazione
    {
        ProtocolloOutXml InserisciProtocollo(ProtocolloInXml protocolloIn);
        ProtocolloOutXml InserisciDocumento(ProtocolloInXml protocolloIn);
        DocumentoOutXml LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo);
        DocumentoOutXml LeggiDocumento(string idProtocollo, string operatore, string ruolo);
        string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo);
        //string CollegaDocumento(string collegaDocumentoIn);
        DocumentoOutXml GeneraCopia(CreaCopieInfo info);
        void CreaCopiePerAmministrazioniInterne(DatiProtocolloIn protocolloInput, ProtocolloOutXml protocolloOutputOrigine, string operatore);
    }
}
