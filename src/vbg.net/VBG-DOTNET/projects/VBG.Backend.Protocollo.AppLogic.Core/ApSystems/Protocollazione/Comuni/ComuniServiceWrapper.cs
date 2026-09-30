using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloApSystemsService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Comuni
{
    public class ComuniServiceWrapper
    {
        private readonly ProtocolloLogs _log;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly AuthenticationDetails _authenticationDetails;

        public ComuniServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, AuthenticationDetails authenticationDetails, string url, IBindingFactory bindingFactory)
        {
            this._log = logs;
            this._serializer = serializer;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
            this._authenticationDetails = authenticationDetails;
        }

        public comuni.comuneRow GetComuneByCodiceIstat(string codiceIstat)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    if (String.IsNullOrEmpty(codiceIstat))
                    {
                        _log.Warn("WARNING GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT NON VALORIZZATO, LE FUNZIONALITA' ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO");
                        return null;
                    }

                    string codiceIstatNumerico = Convert.ToInt32(codiceIstat).ToString();

                    var response = ws.Service.GetComune(_authenticationDetails, "", codiceIstatNumerico, "", "", "");
                    var ds = new comuni();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                    {
                        _log.WarnFormat("WARNING RESTITUITO DAL WEB METHOD GETCOMUNE() DEL WEB SERVICE DURANTE LA RICHIESTA PER CODICE ISTAT {0}, DETTAGLIO ERRORE: {1}, LE FUNZIONALITÀ ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO", codiceIstat, ds.GetDescrizioneErrore());
                        return null;
                    }

                    if (ds.comune.Rows.Count > 1)
                    {
                        _log.WarnFormat("WARNING GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT: {0}, TROVATO PIU' DI UN RISULTATO, LE FUNZIONALITÀ ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO", codiceIstat);
                        return null;
                    }

                    if (ds.comune.Rows.Count == 0)
                    {
                        _log.WarnFormat("WARNING GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT: {0}, COMUNE NON TROVATO, LE FUNZIONALITÀ ANDRANNO COMUNQUE AVANTI SENZA QUESTO DATO", codiceIstat);
                        return null;
                    }

                    return ds.comune[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEL COMUNE DAL CODICE ISTAT {0}", codiceIstat, ex.Message), ex);
            }
        }
    }
}
