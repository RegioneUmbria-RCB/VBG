using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Iride.Proxies;

namespace Init.SIGePro.Protocollo.Iride.Services
{
    internal class ProtocolloIrideService : IProtocolloIrideService
    {
        ProxyProtIride _client;

        public ProtocolloIrideService(ProxyProtIride client)
        {
            _client = client;
        }

        #region IProtocolloIrideService Members

        public ProtocolloOut InserisciProtocollo(ProtocolloIn protocolloIn)
        {
            return _client.InserisciProtocollo(protocolloIn);
        }

        public DocumentoOut LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo)
        {
            return _client.LeggiProtocollo(annoProtocollo, numeroProtocollo, operatore, ruolo);
        }

        public DocumentoOut LeggiDocumento(int idProtocollo, string operatore, string ruolo)
        {
            return _client.LeggiDocumento(idProtocollo, operatore, ruolo);
        }

        #endregion
    }
}
