using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloHalleyDizionarioServiceProxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;


namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Services
{
    public class HalleyDizionarioService
    {
        ProtocolloLogs _logs;
        private readonly ClientDizionarioServiceCreator _clientDizionarioServiceCreator;

        internal HalleyDizionarioService(ProtocolloLogs logs, string endPointAddress, string proxy, IBindingFactory bindingFactory)
        {
            _logs = logs;
            this._clientDizionarioServiceCreator = new ClientDizionarioServiceCreator(logs, bindingFactory, endPointAddress, proxy);
        }

        internal string Login(string codiceEnte, string username, string password)
        {

            using (var ws = this._clientDizionarioServiceCreator.CreateClient())
            {
                try
                {
                    _logs.InfoFormat("Chiamata a Login del web service Dizionario, codice ente: {0}, username: {1}, password: {2}", codiceEnte, username, password);
                    var response = ws.Service.Login(codiceEnte, username, password);

                    if (response.lngErrNumber != 0)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                    if (String.IsNullOrEmpty(response.strDST))
                        throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");

                    _logs.InfoFormat("AUTENTICAZIONE AL WEB SERVICE AVVENUTA CORRETTAMENTE, token restituito: {0}", response.strDST);

                    return response.strDST;
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();
                    throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE, ERRORE: {0}", ex.Message), ex);
                }
            }
        }

        internal FascicoliFascicolo GetFascicolo(string username, string token, string codiceAoo, string numeroProtocollo, string annoProtocollo)
        {
            using (var ws = this._clientDizionarioServiceCreator.CreateClient())
            {
                try
                {
                    long errorNumber;
                    string errorDescription = String.Empty;

                    _logs.InfoFormat("Chiamata a metodo SearchFascicoli del webservice Dizionario per il recupero Fascicolo, username: {0}, token: {1}, numero protocollo: {2}, anno protocollo: {3}", username, token, numeroProtocollo, annoProtocollo);

                    var response = ws.Service.SearchFascicoli(username, token, codiceAoo, numeroProtocollo, annoProtocollo, out errorNumber, out errorDescription);

                    if (errorNumber > 0)
                        throw new Exception(String.Format("NUMERO ERRORE: {0}, DETTAGLIO ERRORE: {1}", errorNumber.ToString(), errorDescription));

                    if(response.Length == 0)
                        throw new Exception(String.Format("FASCICOLO NON TROVATO PER IL PROTOCOLLO NUMERO {0}, ANNO: {1}", numeroProtocollo, annoProtocollo));

                    if(response.Length > 1)
                        throw new Exception(String.Format("E' STATO TROVATO PIU' DI UNFASCICOLO PER IL PROTOCOLLO NUMERO {0}, ANNO: {1}", numeroProtocollo, annoProtocollo));

                    _logs.InfoFormat("CHIAMATA A METODO SEARCHFASCICOLI AVVENUTA CON SUCCESSO, username: {0}, token: {1}, numero protocollo: {2}, anno protocollo: {3}", username, token, numeroProtocollo, annoProtocollo);

                    return response[0];
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DIZIONARIO DURANTE LA RICERCA DEL FASCICOLO: ERRORE: {0}", ex.Message), ex);
                }
            }
        }

        internal CodiciTitolarioCodiceTitolario[] GetClassifiche(string username, string token)
        {
            using (var ws = this._clientDizionarioServiceCreator.CreateClient())
            {
                try
                {
                    long errorNumber;
                    string errorDescription = String.Empty;

                    _logs.InfoFormat("Chiamata a metodo ListaCodiceTitolario del webservice Dizionario per il recupero delle classifiche, username: {0}, token: {1}", username, token);

                    var response = ws.Service.ListaCodiceTitolario(username, token, out errorNumber, out errorDescription);

                    if (errorNumber > 0)
                        throw new Exception(String.Format("DETTAGLIO ERRORE: {0}", errorDescription));

                    _logs.InfoFormat("CHIAMATA A METODO LISTACODICETITOLARIO AVVENUTA CON SUCCESSO, username: {0}, token: {1}", username, token);

                    return response;
                }
                catch (Exception ex)
                {
                    ws.Service.Abort();
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE DIZIONARIO DURANTE LA RICERCA DELLA CLASSIFICA, ERRORE: {0}", ex.Message), ex);
                }
            }
        }
    }
}
