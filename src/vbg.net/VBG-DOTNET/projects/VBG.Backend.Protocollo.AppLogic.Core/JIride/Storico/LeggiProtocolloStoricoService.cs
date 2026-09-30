using VBG.Backend.Protocollo.AppLogic.Core.JIride.Client;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Storico
{
    public class LeggiProtocolloStoricoService
    {
        private readonly ParametriStorici _parametri;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public LeggiProtocolloStoricoService(ParametriStorici parametri, IBindingFactory bindingFactory)
        {
            this._parametri = parametri;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(parametri.Logger, bindingFactory, parametri.Url);
        }

        public DocumentoOutXml LeggiProtocollo()
        {
            return this._parametri.IdProtocollo.HasValue ? this.LeggiProtocolloDaId() : this.LeggiProtocolloDaRiferimenti();
        }

        private DocumentoOutXml LeggiProtocolloDaRiferimenti()
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                try
                {
                    this._parametri.Logger.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO STRING DI J-IRIDE, ANNO PROTOCOLLO: {0}, NUMERO PROTOCOLLO: {1}, OPERATORE: {2}, RUOLO: {3}, CODICE AMMINISTRAZIONE: {4}", this._parametri.AnnoProtocollo, this._parametri.NumeroProtocollo, this._parametri.Operatore, this._parametri.Ruolo, this._parametri.CodiceAmministrazione);
                    var responseXml = ws.LeggiProtocolloString(this._parametri.AnnoProtocollo, this._parametri.NumeroProtocollo, this._parametri.Operatore, this._parametri.Ruolo, this._parametri.CodiceAmministrazione, this._parametri.CodiceAOO, "");

                    if (this._parametri.Logger.IsDebugEnabled)
                    {
                        this._parametri.Logger.InfoFormat("RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE SALVATA IN : {0}", ProtocolloLogsConstants.LeggiProtocolloResponseFileName);
                        this._parametri.Serializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, responseXml);
                    }

                    this._parametri.Logger.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE");
                    var response = this._parametri.Serializer.Deserialize<DocumentoOutXml>(responseXml);
                    this._parametri.Logger.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING AVVENUTA CON SUCCESSO");

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE DURANTE LA LETTURA DEL PROTOCOLLO NUMERO {this._parametri.NumeroProtocollo}, ANNO {this._parametri.AnnoProtocollo}, {ex.Message}", ex);
                }
            });
        }

        private DocumentoOutXml LeggiProtocolloDaId()
        {
            return this._protocolloClientServiceCreator.Call(ws =>
            {
                try
                {
                    this._parametri.Logger.InfoFormat("CHIAMATA A LEGGI DOCUMENTO STRING DI J-IRIDE, IDPROTOCOLLO: {0}, OPERATORE: {1}, RUOLO: {2}, CODICE AMMINISTRAZIONE: {3}", this._parametri.IdProtocollo, this._parametri.Operatore, this._parametri.Ruolo, this._parametri.CodiceAmministrazione);
                    var responseXml = ws.LeggiDocumentoString(this._parametri.IdProtocollo.Value, this._parametri.Operatore, this._parametri.Ruolo, this._parametri.CodiceAmministrazione, this._parametri.CodiceAOO);
                    this._parametri.Logger.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING DI J-IRIDE");
                    var response = this._parametri.Serializer.Deserialize<DocumentoOutXml>(responseXml);
                    this._parametri.Logger.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING AVVENUTA CON SUCCESSO");

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
