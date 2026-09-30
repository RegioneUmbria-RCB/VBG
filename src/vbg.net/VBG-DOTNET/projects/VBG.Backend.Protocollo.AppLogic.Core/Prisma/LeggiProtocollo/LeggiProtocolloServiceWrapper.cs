using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper
    {
        private readonly ProtocolloLogs _log;
        private IProtocolloSerializer _serializer;
        private readonly ExtendedClientServiceCreator _protocolloClientServiceCreator;
        private readonly string _username;
        private readonly string _token;

        public LeggiProtocolloServiceWrapper(string url, ProtocolloLogs logs, IProtocolloSerializer serializer, IBindingFactory bindingFactory, CredentialsInfo credentials)
        {
            _log = logs;
            _serializer = serializer;
            _protocolloClientServiceCreator = new ExtendedClientServiceCreator(logs, bindingFactory, credentials, url);
            _username = credentials.Username;
            _token = credentials.Token;
        }

        public LeggiProtocolloOutXML Leggi(LeggiProtocolloInXML request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();
                        var requestXml = this._serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);
                        _log.Info($"CHIAMATA A LEGGI PROTOCOLLO (getDocumento), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente}");
                        var responseXml = ws.Service.getDocumento(_username, _token, requestXml);
                        this._serializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, responseXml);
                        _log.Info($"CHIAMATA A LEGGI PROTOCOLLO (getDocumento), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente} AVVENUTA CORRETTAMENTE");
                        _log.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI PROTOCOLLO");
                        var response = this._serializer.Deserialize<LeggiProtocolloOutXML>(responseXml);
                        _log.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI PROTOCOLLO AVVENUTA CORRETTAMENTE");

                        if (response == null)
                        {
                            throw new Exception("LA RISPOSTA NON E' STATA VALORIZZATA");
                        }

                        if (response.Doc == null)
                        {
                            throw new Exception($"PROTOCOLLO NUMERO: {request.ProtocolloGruppo.Numero}, ANNO: {request.ProtocolloGruppo.Anno} NON TROVATO");
                        }

                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO NUMERO {request.ProtocolloGruppo.Numero}, ANNO: {request.ProtocolloGruppo.Anno}, {ex.Message}", ex);
            }
        }

        public LeggiPecOutXML GetDatiPec(LeggiPecInXML request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.Service.InnerChannel))
                    {
                        //base.AggiungiCredenzialiAContextScope();
                        var requestXml = this._serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);
                        _log.Info($"CHIAMATA A LEGGI DATI PEC (getInfoPec), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente}");
                        var responseXml = ws.Service.getInfoPec(_username, _token, requestXml);
                        _log.Info($"CHIAMATA A LEGGI DATI PEC (getInfoPec), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente} AVVENUTA CORRETTAMENTE");
                        _log.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI DATI PEC");
                        var response = this._serializer.Deserialize<LeggiPecOutXML>(responseXml);
                        _log.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI DATI PEC AVVENUTA CORRETTAMENTE");

                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA DEI DATI PEC RELATIVI AL PROTOCOLLO NUMERO {request.ProtocolloGruppo.Numero}, ANNO: {request.ProtocolloGruppo.Anno}, {ex.Message}", ex);
            }
        }
    }
}
