using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.LeggiProtocolloPrismaService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper : ExtendedServiceWrapper
    {

        public LeggiProtocolloServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credential) : base(url, logs, serializer, credential)
        {

        }

        public LeggiProtocolloOutXML Leggi(LeggiProtocolloInXML request)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);
                        base.Logs.Info($"CHIAMATA A LEGGI PROTOCOLLO (getDocumento), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente}");
                        var responseXml = ws.getDocumento(base.Credentials.Username, base.Credentials.Token, requestXml);
                        base.Logs.Info($"CHIAMATA A LEGGI PROTOCOLLO (getDocumento), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente} AVVENUTA CORRETTAMENTE");
                        base.Logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI PROTOCOLLO");
                        var response = base.Serializer.Deserialize<LeggiProtocolloOutXML>(responseXml);
                        base.Logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI PROTOCOLLO AVVENUTA CORRETTAMENTE");

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
                using (var ws = base.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, request);
                        base.Logs.Info($"CHIAMATA A LEGGI DATI PEC (getInfoPec), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente}");
                        var responseXml = ws.getInfoPec(base.Credentials.Username, base.Credentials.Token, requestXml);
                        base.Logs.Info($"CHIAMATA A LEGGI DATI PEC (getInfoPec), NUMERO: {request.ProtocolloGruppo.Numero} ANNO: {request.ProtocolloGruppo.Anno}, TIPO REGISTRO: {request.ProtocolloGruppo.TipoRegistro}, UTENTE: {request.Utente} AVVENUTA CORRETTAMENTE");
                        base.Logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI DATI PEC");
                        var response = base.Serializer.Deserialize<LeggiPecOutXML>(responseXml);
                        base.Logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGI DATI PEC AVVENUTA CORRETTAMENTE");

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
