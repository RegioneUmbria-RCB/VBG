using VBG.Backend.Protocollo.AppLogic.Core.JIride.Client;
using VBG.Backend.Protocollo.AppLogic.Core.JIride.Fascicolazione.Lettura;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;
using static VBG.Backend.Protocollo.AppLogic.Shared.Validation.ProtocolloValidation;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Fascicolazione
{
    public class FascicolazioneClient
    {
        private readonly ProtocolloLogs _protocolloLogs;
        private readonly ProtocolloSerializer _protocolloSerializer;
        private readonly DocWSFascicoliClientServiceCreator _docWSFascicoliClientServiceCreator;

        public FascicolazioneClient(ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer, string url, IBindingFactory bindingFactory)
        {
            this._protocolloLogs = protocolloLogs;
            this._protocolloSerializer = protocolloSerializer;

            this._docWSFascicoliClientServiceCreator = new DocWSFascicoliClientServiceCreator(protocolloLogs, bindingFactory, url);
        }

        public FascicoloOutXml CreaFascicolo(FascicoloInXml request, string codiceamministrazione, string codiceAoo)
        {
            return this._docWSFascicoliClientServiceCreator.Call(ws =>
            {
                try
                {
                    this._protocolloLogs.Info("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN");
                    var requestXML = this._protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, request, TipiValidazione.NO_NAMESPACE);
                    this._protocolloLogs.InfoFormat("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN AVVENUTA CORRETTAMENTE, XML: {0}", requestXML);

                    this._protocolloLogs.Info("CHIAMATA A CREAFASCICOLOSTRING");
                    var response = ws.CreaFascicoloString(requestXML, codiceamministrazione, codiceAoo);
                    this._protocolloLogs.InfoFormat("RISPOSTA DA CREAFASCICOLOSTRING: {0}", response);

                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREAFASCICOLOSTRING");
                    var fascicoloOut = this._protocolloSerializer.Deserialize<FascicoloOutXml>(response);
                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREAFASCICOLOSTRING AVVENUTA CON SUCCESSO");

                    if (fascicoloOut.Id == 0 || !String.IsNullOrEmpty(fascicoloOut.Errore))
                    {
                        throw new Exception(fascicoloOut.Errore);
                    }

                    this._protocolloLogs.InfoFormat($"CREAZIONE FASCICOLO AVVENUTA CON SUCCESSO, ID FASCICOLO: {fascicoloOut.Id}, NUMERO FASCICOLO: {fascicoloOut.Numero}, ANNO FASCICOLO: {fascicoloOut.Anno}");

                    return fascicoloOut;
                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE DURANTE LA CREAZIONE DEL FASCICOLO, {ex.Message}", ex);
                }
            });
        }

        public FascicoloOutXml LeggiFascicolo(LeggiFascicoloWSRequest request, string codiceAmministrazione, string codiceAoo)
        {
            return this._docWSFascicoliClientServiceCreator.Call(ws =>
            {
                try
                {
                    string fascicoloOutXml;

                    if (request.Id.HasValue)
                    {
                        this._protocolloLogs.InfoFormat($"CHIAMATA A LEGGI FASCICOLO J_IRIDE, ID: {request.Id}");
                        fascicoloOutXml = ws.LeggiFascicoloString(request.Id.ToString(), "", "", request.Utente, request.Ruolo, codiceAmministrazione, codiceAoo, request.Classifica);
                    }
                    else
                    {
                        this._protocolloLogs.InfoFormat($"CHIAMATA A LEGGI FASCICOLO J_IRIDE, ANNO FASCICOLO: {request.Anno}, NUMERO FASCICOLO: {request.Numero}, UTENTE: {request.Utente}, RUOLO: {request.Ruolo}, CODICE AMMINISTRAZIONE: {codiceAmministrazione}, CODICE AOO: {codiceAoo}, CLASSIFICA: {request.Classifica}");
                        fascicoloOutXml = ws.LeggiFascicoloString("", request.Anno, request.Numero, request.Utente, request.Ruolo, codiceAmministrazione, codiceAoo, request.Classifica);
                    }

                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGIFASCICOLOSTRING");
                    var fascicoloOut = this._protocolloSerializer.Deserialize<FascicoloOutXml>(fascicoloOutXml);
                    this._protocolloLogs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGIFASCICOLOSTRING AVVENUTA CON SUCCESSO");

                    this._protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.LeggiFascicoloResponseFileName, fascicoloOut);

                    if (fascicoloOut.Id == 0 || !String.IsNullOrEmpty(fascicoloOut.Errore))
                    {
                        throw new Exception(fascicoloOut.Errore);
                    }

                    this._protocolloLogs.InfoFormat("CHIAMATA A LEGGIFASCICOLOSTRING AVVENUTA CORRETTAMENTE, ID FASCICOLO: {0}", fascicoloOut.Id);

                    return fascicoloOut;
                }
                catch (Exception ex)
                {
                    throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA DEL FASCICOLO ID: {request.Id}, NUMERO: {request.Numero}, ANNO: {request.Anno}, {ex.Message}", ex);
                }
            });
        }
    }
}
