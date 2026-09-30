using VBG.Backend.Protocollo.AppLogic.Core.JIride.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione
{
    public class ProtocollazioneServiceWrapper : IProtocollazioneJIride
    {
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        private readonly string _codiceAmministrazione;
        private readonly string _codiceAoo;

        public ProtocollazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string codiceAmministrazione, string codiceAoo, IBindingFactory bindingFactory)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);

            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
        }

        public ProtocolloOutXml InserisciDocumento(ProtocolloInXml protocolloIn)
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                try
                {
                    var requestXml = this._serializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protocolloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                    this._logs.Info("CHIAMATA A INSERISCI DOCUMENTO STRING DI J-IRIDE");

                    var responseXml = ws.InserisciDocumentoEAnagraficheString(requestXml, this._codiceAmministrazione, this._codiceAoo);
                    this._logs.InfoFormat("RISPOSTA A INSERISCI DOCUMENTO STRING DI J-IRIDE, {0}", responseXml);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA");
                    var response = this._serializer.Deserialize<ProtocolloOutXml>(responseXml);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CON SUCCESSO");

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE GENERATO DURANTE L'INSERIMENTO DEL DOCUMENTO {ex.Message}", ex);
                }
            });
        }

        public ProtocolloOutXml InserisciProtocollo(ProtocolloInXml protocolloIn)
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                try
                {
                    var requestXml = this._serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, protocolloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                    this._logs.Info("CHIAMATA A INSERISCI PROTOCOLLO STRING DI J-IRIDE");

                    var responseXml = ws.InserisciProtocolloEAnagraficheString(requestXml, this._codiceAmministrazione, this._codiceAoo);
                    this._logs.InfoFormat("RISPOSTA A INSERISCI PROTOCOLLO STRING DI J-IRIDE, {0}", responseXml);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA");
                    var response = this._serializer.Deserialize<ProtocolloOutXml>(responseXml);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CON SUCCESSO");

                    if (!String.IsNullOrEmpty(response.Errore))
                    {
                        throw new Exception(response.Errore);
                    }

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE GENERATO DURANTE L'INSERIMENTO DEL PROTOCOLLO {ex.Message}", ex);
                }
            });
        }

        public string LeggiAnagraficaPerCodiceFiscale(string codiceFiscale, string operatore, string ruolo)
        {
            throw new NotImplementedException();
        }

        public bool IsCopia(string idProtocollo)
        {
            if (!String.IsNullOrEmpty(idProtocollo))
            {
                var arrIdProtocollo = idProtocollo.Split('-');
                if (arrIdProtocollo.Length > 1 && arrIdProtocollo[1] == "COPIA")
                {
                    this._logs.Info("E' UNA COPIA");
                    return true;
                }
            }

            return false;
        }
    }
}
