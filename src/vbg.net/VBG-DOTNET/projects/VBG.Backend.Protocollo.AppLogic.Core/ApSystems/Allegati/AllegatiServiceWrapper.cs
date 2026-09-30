using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloApSystemsService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Allegati
{
    public class AllegatiServiceWrapper
    {
        private readonly ProtocolloLogs _log;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly AuthenticationDetails _auth;
        private string _operatore;

        public AllegatiServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url, string operatore, IBindingFactory bindingFactory)
        {
            _log = logs;
            _serializer = serializer;
            _protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
            _auth = new AuthenticationDetails { UserName = username, Password = password };
            _operatore = operatore;
        }

        public void InserisciAllegatoaProtocolloGenerale(string codiceProtocollo, string numeroProtocollo, string dataProtocollo, byte[] oggetto, string nomeFile, string codiceAllegato)
        {
            try
            {
                using(var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _log.InfoFormat("INSERIMENTO ALLEGATO {0} CODICE {1} AL PROTOCOLLO NUMERO {2} DEL {3} CODICE PROTOCOLLO {4}", nomeFile, codiceAllegato, numeroProtocollo, dataProtocollo, codiceProtocollo);
                    ws.Service.InsertAllegatoProtocolloGenerale(_auth, codiceProtocollo, oggetto, nomeFile, _operatore);
                    _log.InfoFormat("INSERIMENTO ALLEGATO {0} CODICE {1} AL PROTOCOLLO NUMERO {2} DEL {3} CODICE PROTOCOLLO {4} AVVENUTO CON SUCCESSO", nomeFile, codiceAllegato, numeroProtocollo, dataProtocollo, codiceProtocollo);
                }
            }
            catch (Exception ex)
            {
                _log.WarnFormat("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'ALLEGATO {0} CODICE {1} AL PROTOCOLLO GENERALE NUMERO {2} DEL {3} CODICE PROTOCOLLO {4}, ERRORE {5}", nomeFile, codiceAllegato, numeroProtocollo, dataProtocollo, codiceProtocollo, ex.Message);
            }
        }

        public byte[] DownloadAllegato(string codiceAllegato, string numeroProtocolo, string annoProtocollo)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _log.InfoFormat("RICHIESTA DI DOWNLOAD ALLEGATO CODICE {0} RELATIVO AL PROTOCOLLO NUMERO {1} ANNO {2}", codiceAllegato, numeroProtocolo, annoProtocollo);
                    var response = ws.Service.GetAllegato(_auth, codiceAllegato, false);
                    _log.InfoFormat("RICHIESTA DI DOWNLOAD ALLEGATO CODICE {0} RELATIVO AL PROTOCOLLO NUMERO {1} ANNO {2} AVVENUTA CON SUCCESSO", codiceAllegato, numeroProtocolo, annoProtocollo);
                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL DOWNLOAD DELL'ALLEGATO CODICE {0} RELATIVO AL PROTOCOLLO NUMERO {1} ANNO {2}, {3}", codiceAllegato, numeroProtocolo, annoProtocollo, ex.Message), ex);
            }
        }
    }
}
