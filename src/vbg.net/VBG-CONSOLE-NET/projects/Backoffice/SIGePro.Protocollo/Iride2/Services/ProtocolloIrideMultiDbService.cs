using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Iride2.Proxies;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Iride2.Services
{
    internal class ProtocolloIrideMultiDbService : IProtocolloIrideService
    {
        string _codiceAmministrazione;
        ProxyProtIride _client;
        ProtocolloLogs _logs;

        public ProtocolloIrideMultiDbService(string codiceAmministrazione, ProxyProtIride client, ProtocolloLogs logs)
        {
            _codiceAmministrazione = codiceAmministrazione;
            _client = client;
            _logs = logs;
            _logs.InfoFormat("PROTOCOLLO DI TIPO MULTIDB, CODICEAMMINISTRAZIONE: {0}", _codiceAmministrazione);
        }

        #region IProtocolloIrideService Members

        public ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn)
        {
            return _client.InserisciProtocolloMultiDB(protocolloIn, _codiceAmministrazione, String.Empty);
        }

        public DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo)
        {
            return _client.LeggiProtocolloMultiDB(annoProtocollo, numeroProtocollo, operatore, ruolo, _codiceAmministrazione, String.Empty);
        }

        public DocumentoOut LeggiDocumento(int idProtocollo, string operatore, string ruolo)
        {
            return _client.LeggiDocumentoMultiDB(idProtocollo, operatore, ruolo, _codiceAmministrazione, String.Empty);
        }

        public string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo)
        {
            return _client.LeggiAnagrafica(String.Empty, codiceFiscale, operatore, ruolo, _codiceAmministrazione, String.Empty);
        }

        #endregion
    }
}
