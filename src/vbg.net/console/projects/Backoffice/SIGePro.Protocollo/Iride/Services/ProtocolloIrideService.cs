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
            var response = _client.InserisciProtocollo(protocolloIn);

            if (!String.IsNullOrEmpty(response.Errore))
            {
                var errore = "";
                if(response.NumeroProtocollo != 0)
                {
                    errore += String.Format("<br>NUMERO PROTOCOLLO: {0}<br>DATA PROTOCOLLO: {1}", response.NumeroProtocollo, response.DataProtocollo.ToString("dd/MM/yyyy"));
                }

                if (!String.IsNullOrEmpty(response.Messaggio) && response.Messaggio != response.Errore)
                {
                    errore += String.Format("<br>MESSAGGIO: {0}", response.Messaggio);
                }

                errore += String.Format("<br>ERRORE: {0}", response.Errore);

                throw new Exception(response.Errore);
            }

            return response;
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
