using ProtocolloIride2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Services
{
    internal class ProtocolloIrideMultiDbService : IProtocolloIrideService
    {
        string _codiceAmministrazione;
        ProtocolloClientServiceCreator _client;
        ProtocolloLogs _logs;

        public ProtocolloIrideMultiDbService(string codiceAmministrazione, ProtocolloClientServiceCreator client, ProtocolloLogs logs)
        {
            _codiceAmministrazione = codiceAmministrazione;
            _client = client;
            _logs = logs;
            _logs.InfoFormat("PROTOCOLLO DI TIPO MULTIDB, CODICEAMMINISTRAZIONE: {0}", _codiceAmministrazione);
        }

        #region IProtocolloIrideService Members

        public ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.InserisciProtocolloEAnagraficheMultiDB(protocolloIn, _codiceAmministrazione, String.Empty);
        }

        public DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.LeggiProtocolloMultiDB(annoProtocollo, numeroProtocollo, operatore, ruolo, _codiceAmministrazione, String.Empty);
        }

        public DocumentoOut LeggiDocumento(int idProtocollo, string operatore, string ruolo)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.LeggiDocumentoMultiDB(idProtocollo, operatore, ruolo, _codiceAmministrazione, String.Empty);
        }

        public string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.LeggiAnagrafica(String.Empty, codiceFiscale, operatore, ruolo, _codiceAmministrazione, String.Empty);
        }

        public ProtocolloOut InserisciDocumento(ProtocolloIn protocolloIn)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.InserisciDocumentoEAnagrafiche(protocolloIn);
        }

        public string CollegaDocumento(string collegaDocumentoIn)
        {
            using (var ws = _client.CreateClient())
                return ws.Service.CollegaDocumento(collegaDocumentoIn, _codiceAmministrazione, "");
        }

        #endregion
    }
}
