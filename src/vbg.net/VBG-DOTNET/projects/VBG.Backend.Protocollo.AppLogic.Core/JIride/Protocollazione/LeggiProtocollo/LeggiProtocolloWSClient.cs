using System.Xml.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Client;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione.LeggiProtocollo
{
    public class LeggiProtocolloWSClient
    {
        private readonly ProtocolloLogs _protocolloLogs;
        private readonly ProtocolloSerializer _protocolloSerializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public LeggiProtocolloWSClient(ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer, string url, IBindingFactory bindingFactory)
        {
            this._protocolloLogs = protocolloLogs;
            this._protocolloSerializer = protocolloSerializer;

            if (String.IsNullOrEmpty(url))
            {
                throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_JIRIDE NON È STATO VALORIZZATO.");
            }

            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(this._protocolloLogs, bindingFactory, url);
        }

        public DocumentoOutXml LeggiDocumentoPlus(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo, string codiceAmministrazione, string codiceAoo)
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                try
                {
                    var filtroDocXml = new XDocument(
                        new XElement("FiltroDocumentoIn",
                            new XElement("AnnoProtocollo", annoProtocollo),
                            new XElement("NumeroProtocollo", numeroProtocollo),
                            new XElement("Utente", operatore),
                            new XElement("Ruolo", ruolo),
                            new XElement("DownloadAllegati", "N")
                        )
                    );

                    var filtroDoc = filtroDocXml.ToString(SaveOptions.DisableFormatting);

                    var responseXml = ws.LeggiDocumentoPlus(filtroDoc, codiceAmministrazione, codiceAoo);

                    if (this._protocolloLogs.IsDebugEnabled)
                    {
                        this._protocolloLogs.InfoFormat($"RISPOSTA A LEGGI DOCUMENTO PLUS DI J-IRIDE SALVATA IN : {ProtocolloLogsConstants.LeggiDocumentoPlusResponseFileName}");
                        this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.LeggiDocumentoPlusResponseFileName, responseXml);
                    }

                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO PLUS DI J-IRIDE");
                    var response = this._protocolloSerializer.Deserialize<DocumentoOutXml>(responseXml);
                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO PLUS AVVENUTA CON SUCCESSO");

                    return response;
                }
                catch (Exception ex)
                {
                    this._protocolloLogs.Error(ex);
                    throw new Exception($"ERRORE DURANTE LEGGI DOCUMENTO PLUS DEL PROTOCOLLO NUMERO {numeroProtocollo}, ANNO {annoProtocollo}, {ex.Message}", ex);
                }
            });
        }

        public DocumentoOutXml LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo, string codiceAmministrazione, string codiceAoo)
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                try
                {
                    var responseXml = ws.LeggiProtocolloString(annoProtocollo, numeroProtocollo, operatore, ruolo, codiceAmministrazione, codiceAoo, "");

                    if (this._protocolloLogs.IsDebugEnabled)
                    {
                        this._protocolloLogs.InfoFormat($"RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE SALVATA IN : {ProtocolloLogsConstants.LeggiProtocolloResponseFileName}");
                        this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, responseXml);
                    }

                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE");
                    var response = this._protocolloSerializer.Deserialize<DocumentoOutXml>(responseXml);
                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING AVVENUTA CON SUCCESSO");

                    return response;
                }
                catch (Exception ex)
                {
                    this._protocolloLogs.Error(ex);
                    throw new Exception($"ERRORE DURANTE LEGGI PROTOCOLLO STRING DEL PROTOCOLLO NUMERO {numeroProtocollo}, ANNO {annoProtocollo}, {ex.Message}", ex);
                }
            });
        }

        public DocumentoOutXml LeggiDocumento(int idProtocollo, string operatore, string ruolo, string codiceAmministrazione, string codiceAoo)
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                try
                {
                    this._protocolloLogs.InfoFormat($"CHIAMATA A LEGGI DOCUMENTO STRING DI J-IRIDE, IDPROTOCOLLO: {idProtocollo}, OPERATORE: {operatore}, RUOLO: {ruolo}, CODICE AMMINISTRAZIONE: {codiceAmministrazione}");
                    var responseXml = ws.LeggiDocumentoString(idProtocollo, operatore, ruolo, codiceAmministrazione, codiceAoo);
                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING DI J-IRIDE");
                    var response = this._protocolloSerializer.Deserialize<DocumentoOutXml>(responseXml);
                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING AVVENUTA CON SUCCESSO");

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE DURANTE LA CHIAMATA A LEGGI DOCUMENTO, {ex.Message}", ex);
                }
            });
        }
    }
}
