using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    public class FascicolazioneServiceWrapper : ExtendedServiceWrapper
    {
        public FascicolazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentials) : base(url, logs, serializer, credentials)
        {

        }

        public LeggiFascicoliOutXML GetFascicoli(LeggiFascicoliInXML request)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.ListaClassificheRequest, request);
                        base.Logs.Info($"CHIAMATA A GET FASCICOLI {requestXml}");
                        var responseXml = ws.getFascicoli(base.Credentials.Username, base.Credentials.Token, requestXml);

                        base.Logs.Info("DESERIALIZZAZIONE DEI DATI DI RISPOSTA DA GET FASCICOLI");
                        var response = base.Serializer.Deserialize<LeggiFascicoliOutXML>(responseXml);
                        base.Logs.Info("CHIAMATA A GET FASCICOLI AVVENUTA CORRETTAMENTE");

                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE IL RECUPERO DEI FASCICOLI {request.NumeroFascicolo}/{request.AnnoFascicolo}, {ex.Message}", ex);
            }
        }

        public FascicoloOutXml GetDettaglioFascicolo(LeggiFascicoliInXML request)
        {
            var responseFascicoli = this.GetFascicoli(request);

            if (responseFascicoli.Fascicolo == null)
            {
                return null;
            }

            var fascicoli = responseFascicoli.Fascicolo.Where(x => x.NumeroFascicolo == request.NumeroFascicolo).ToList();

            if (fascicoli.Count() > 0)
            {
                return fascicoli[0];
            }

            return null;
        }

        public CreaFascicoloOutXML CreaFascicolo(CreaFascicoloInXML request)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, request);
                        base.Logs.Info($"CHIAMATA A CREA FASCICOLO {requestXml}");
                        var responseXml = ws.creaFascicolo(base.Credentials.Username, base.Credentials.Token, requestXml);
                        base.Logs.Info("DESERIALIZZAZIONE DEI DATI DI RISPOSTA DA CREA FASCICOLO");
                        var response = base.Serializer.Deserialize<CreaFascicoloOutXML>(responseXml);

                        if (response.Result == CreaFascicoloOutXML.ResultEnum.KO)
                        {
                            throw new Exception($"{response.ErrorNumber} - {response.Message}");
                        }
                        base.Logs.Info($"CHIAMATA A CREA FASCICOLO AVVENUTA CORRETTAMENTE, CREATO FASCICOLO NUMERO: {response.NumeroFascicolo}, ANNO: {response.AnnoFascicolo}, ID: {response.Id}");

                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA CREAZIONE DEL FASCICOLO, {ex.Message}");
            }
        }

        public FascicolazioneOutXML FascicolaProtocollo(FascicolazioneInXML request)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.FascicolazioneRequestFileName, request);
                        base.Logs.Info($"CHIAMATA A FASCICOLA PROTOCOLLO {requestXml}");
                        var responseXml = ws.inserisciDocumentoInFascicolo(base.Credentials.Username, base.Credentials.Token, requestXml);
                        base.Logs.Info("DESERIALIZZAZIONE DEI DATI DI RISPOSTA DA FASCICOLA PROTOCOLLO");
                        var response = base.Serializer.Deserialize<FascicolazioneOutXML>(responseXml);

                        if (response.Result == FascicolazioneOutXML.ResultEnum.KO)
                        {
                            throw new Exception($"{response.ErrorNumber} - {response.Message}");
                        }
                        base.Logs.Info($"CHIAMATA A FASCICOLA PROTOCOLLO AVVENUTA CORRETTAMENTE");

                        return response;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA FASCICOLAZIONE DEL PROTOCOLLO NUMERO {request.ProtocolloGruppo.Numero}, ANNO {request.ProtocolloGruppo.Anno}, {ex.Message}");
            }
        }

        public void CambiaFascicolo(CambiaFascicoloInXML request)
        {
            try
            {
                using (var ws = base.CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        var requestXml = base.Serializer.Serialize(ProtocolloLogsConstants.CambiaFascicoloRequest, request);
                        base.Logs.Info($"CHIAMATA A MOD PROTOCOLLO, RELATIVO A CAMBIA FASCICOLO DEL PROTOCOLLO NUMERO: {request.ProtocolloGruppo.Numero}, ANNO: {request.ProtocolloGruppo.Anno}, UTENTE: {request.Utente}");
                        var responseXml = ws.modProtocollo(base.Credentials.Username, base.Credentials.Token, requestXml);
                        var response = base.Serializer.Deserialize<CambiaFascicoloOutXML>(responseXml);

                        if (response.Result == CambiaFascicoloOutXML.ResultEnum.KO)
                        {
                            throw new Exception($"ERRORE NUMERO: {response.ErrorNumber}, DESCRIZIONE: {response.Message}");
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA MODIFICA DEL FASCICOLO DEL PROTOCOLLO NUMERO: {request.ProtocolloGruppo.Numero}, ANNO: {request.ProtocolloGruppo.Anno}, {ex.Message}", ex);
            }
        }
    }
}
