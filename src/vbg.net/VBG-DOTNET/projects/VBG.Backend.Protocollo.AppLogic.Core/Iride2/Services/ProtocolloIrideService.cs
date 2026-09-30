using ProtocolloIride2Service;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services
{
    internal class ProtocolloIrideService : IProtocolloIrideService
    {
        ProtocolloClientServiceCreator _client;

        public ProtocolloIrideService(ProtocolloClientServiceCreator client)
        {
            _client = client;
        }

        #region IProtocolloIrideService Members

        public ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.InserisciProtocolloEAnagrafiche(protocolloIn);
        }

        public ProtocolloOut InserisciDocumento(ProtocolloIn protocolloIn)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.InserisciDocumentoEAnagrafiche(protocolloIn);
        }

        public DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.LeggiProtocollo(annoProtocollo, numeroProtocollo, operatore, ruolo);
        }

        public DocumentoOut LeggiDocumento(int idProtocollo, string operatore, string ruolo)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.LeggiDocumento(idProtocollo, operatore, ruolo);
        }

        public string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.LeggiAnagrafica(String.Empty, codiceFiscale, operatore, ruolo, String.Empty, String.Empty);
        }

        public string CollegaDocumento(string collegaDocumentoIn)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.CollegaDocumento(collegaDocumentoIn, "", "");
        }

        #endregion
    }
}
