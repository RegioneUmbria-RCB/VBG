using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Iride.Proxies;

namespace Init.SIGePro.Protocollo.Iride.Services
{
    internal class ProtocolloIrideMultiDbService : IProtocolloIrideService
    {
        string _codiceAmministrazione;
        ProxyProtIride _client;

        public ProtocolloIrideMultiDbService(string codiceAmministrazione, ProxyProtIride client)
        {
            _codiceAmministrazione = codiceAmministrazione;
            _client = client;
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

        #endregion

    }
}
