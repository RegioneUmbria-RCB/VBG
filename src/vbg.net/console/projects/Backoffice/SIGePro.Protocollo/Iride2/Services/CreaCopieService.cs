using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Iride2.Proxies;

namespace Init.SIGePro.Protocollo.Iride2.Services
{
    internal class CreaCopieService
    {
        private string _urlWebService;
        string _proxyAddress;
        private ProtocolloLogs _protocolloLogs;
        private ProtocolloSerializer _protocolloSerializer;

        internal CreaCopieService(string urlWebService, string proxyAddress, ProtocolloLogs protocolloLog, ProtocolloSerializer protocolloSerializer)
        {

            if (String.IsNullOrEmpty(urlWebService))
                throw new Exception("URL WEB SERVICE NON VALORIZZATO");

            if (protocolloLog ==null)
                throw new Exception("PROTOCOLLOLOG E' NULL");

            if (protocolloSerializer == null)
                throw new Exception("PROTOCOLLOSERIALIZER E' NULL");

            _urlWebService = urlWebService;
            _protocolloLogs = protocolloLog;
            _protocolloSerializer = protocolloSerializer;
            _proxyAddress = proxyAddress;
        }

        public CreaCopieOut CreaCopie(string ruolo, int idDocumento, string annoProtocollo, string numeroProtocollo, string operatoreIride, string codiceEnte, UODestinataria[] destinatari)
        {
            if (String.IsNullOrEmpty(ruolo))
                throw new Exception("RUOLO NON VALORIZZATO");

            if (idDocumento <= 0)
                throw new Exception("ID PROTOCOLLO (DOCID) NON VALIDO");

            if (String.IsNullOrEmpty(operatoreIride))
                throw new Exception("OPERATORE NON VALORIZZATO");
            try
            {
                using (var proxyIride = new ProxyProtIride(_urlWebService, _proxyAddress))
                {
                    proxyIride.Url = _urlWebService;
                    _protocolloLogs.Debug("Valorizzazione di CreaCopieIn");

                    var creaCopieIn = new CreaCopieIn
                    {
                        AnnoProtocollo = annoProtocollo,
                        NumeroProtocollo = numeroProtocollo,
                        IdDocumento = idDocumento.ToString(),
                        UODestinatarie = destinatari,
                        Utente = operatoreIride,
                        Ruolo = ruolo
                    };
                    _protocolloLogs.Debug("Fine Valorizzazione di CreaCopieIn");

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaCopieRequestFileName, creaCopieIn);
                    _protocolloLogs.InfoFormat("Chiamata a web method CreaCopie, CodiceAmministrazione (CodiceEnte): {0}, numero protocollo: {1}, anno protocollo: {2}, id: {3}", codiceEnte, numeroProtocollo, annoProtocollo, idDocumento);

                    var creaCopieOut = proxyIride.CreaCopie(creaCopieIn, codiceEnte, string.Empty);

                    _protocolloLogs.Info("CREAZIONE COPIA AVVENUTA CON SUCCESSO");

                    _protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaCopieResponseFileName, creaCopieOut);

                    return creaCopieOut;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DELLE COPIE", ex);
            }

        }

        public CreaCopieOut CreaCopie(string uo, string ruolo, int idDocumento, string annoProtocollo, string numeroProtocollo, string operatoreIride, string codiceEnte)
        {
            if (String.IsNullOrEmpty(uo))
                throw new Exception("UO NON VALORIZZATA");

            var destinatari = new UODestinataria[]{ 
                new UODestinataria{ 
                    Carico = uo, 
                    Data =DateTime.Now.ToString("dd/MM/yyyy"), 
                    NumeroCopie = "1", 
                    TipoUO = "UO" 
                }
            };

            return CreaCopie(ruolo, idDocumento, annoProtocollo, numeroProtocollo, operatoreIride, codiceEnte, destinatari);

        }
    }
}
