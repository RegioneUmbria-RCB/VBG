using FascicolazioneJIrideService;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Fascicolazione
{
    public class FascicolazioneServiceWrapper : IFascicolazione
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly DocWSFascicoliClientServiceCreator _docWSFascicoliClientServiceCreator;
        private readonly string _codiceAmministrazione;
        private readonly string _codiceAoo;

        public FascicolazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string codiceAmministrazione, string codiceAoo, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._docWSFascicoliClientServiceCreator = new DocWSFascicoliClientServiceCreator(logs, bindingFactory, url);

            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
        }

        public FascicoloOutXml CreaFascicolo(FascicolazioneInfo info)
        {
            return this._docWSFascicoliClientServiceCreator.Call(ws =>
            {
                try
                {
                    var fascicoloIn = new FascicoloInXml
                    {
                        Anno = info.Anno,
                        Data = info.Data,
                        Numero = info.Numero,
                        Oggetto = info.Oggetto,
                        Classifica = info.Classifica,
                        Utente = info.Utente,
                        Ruolo = info.Ruolo,
                        Eterogeneo = true
                    };

                    this._logs.Info("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN");
                    var request = this._serializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, fascicoloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                    this._logs.InfoFormat("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN AVVENUTA CORRETTAMENTE, XML: {0}", request);
                    this._logs.Info("CHIAMATA A CREAFASCICOLOSTRING");
                    var response = ws.CreaFascicoloString(request, this._codiceAmministrazione, this._codiceAoo);
                    this._logs.InfoFormat("RISPOSTA DA CREAFASCICOLOSTRING: {0}", response);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREAFASCICOLOSTRING");
                    var fascicoloOut = this._serializer.Deserialize<FascicoloOutXml>(response);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREAFASCICOLOSTRING AVVENUTA CON SUCCESSO");

                    if (fascicoloOut.Id == 0 || !String.IsNullOrEmpty(fascicoloOut.Errore))
                    {
                        throw new Exception(fascicoloOut.Errore);
                    }

                    this._logs.InfoFormat("CREAZIONE FASCICOLO AVVENUTA CON SUCCESSO, ID FASCICOLO: {0}, NUMERO FASCICOLO: {1}, ANNO FASCICOLO: {2}", fascicoloOut.Id, fascicoloOut.Numero, fascicoloOut.Anno);

                    return fascicoloOut;

                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE DURANTE LA CREAZIONE DEL FASCICOLO, {ex.Message}", ex);
                }
            });
        }

        public EsitoOperazione FascicolaDocumento(int IDFascicolo, int IDDocumento, string AggiornaClassifica, string Utente, string Ruolo, string idProtocollo)
        {
            return this._docWSFascicoliClientServiceCreator.Call(ws =>
            {
                try
                {
                    var principale = "";
                    this._logs.InfoFormat("IDPROTOCOLLO = {0}", idProtocollo);
                    if (!String.IsNullOrEmpty(idProtocollo))
                    {
                        var arrIdProtocollo = idProtocollo.Split('-');
                        if (arrIdProtocollo.Length > 1 && arrIdProtocollo[1] == "COPIA")
                        {
                            this._logs.Info("E' UNA COPIA");
                            principale = "N";
                        }
                    }

                    this._logs.InfoFormat("CHIAMATA A FASCICOLADOCUMENTO J_IRIDE, IDFascicolo: {0}, IDDocumento: {1}, AggiornaClassifica: {2}, Utente: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAOO: {6}, principale: {7}", IDFascicolo, IDDocumento, AggiornaClassifica, Utente, Ruolo, this._codiceAmministrazione, this._codiceAoo, principale);
                    var esito = ws.FascicolaDocumento(IDFascicolo, IDDocumento, AggiornaClassifica, Utente, Ruolo, this._codiceAmministrazione, this._codiceAoo, principale);

                    this._logs.InfoFormat("RISPOSTA A FASCICOLADOCUMENTO J-IRIDE, ESITO: {0}", esito.Esito);

                    if (!esito.Esito)
                    {
                        throw new Exception(esito.Errore);
                    }

                    this._logs.Info("FASCICOLAZIONE DEL DOCUMENTO AVVENUTA CORRETTAMENTE");

                    return esito;
                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE DURANTE LA FASCICOLAZIONE DEL DOCUMENTO ID {IDDocumento} NEL FASCICOLO ID {IDFascicolo}, {ex.Message}");
                }
            });
        }
    }
}
