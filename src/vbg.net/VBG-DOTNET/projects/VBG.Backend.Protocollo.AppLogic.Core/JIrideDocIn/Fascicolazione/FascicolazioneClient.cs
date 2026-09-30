using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Fascicolazione.Lettura;
using System.Diagnostics;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using static VBG.Backend.Protocollo.AppLogic.Shared.Validation.ProtocolloValidation;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using FascicolazioneJIrideService;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Fascicolazione
{
    public class FascicolazioneClient
    {

        private ProtocolloLogs _protocolloLogs;
        private ProtocolloSerializer _protocolloSerializer;
        private string _url;
        private readonly ClientFascicolazioneServiceCreator _clientFascicolazioneServiceCreator;

        public FascicolazioneClient(ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer, IBindingFactory bindingFactory, string url)
        {
            this._protocolloLogs = protocolloLogs;
            this._protocolloSerializer = protocolloSerializer;
            this._url = url;
            this._clientFascicolazioneServiceCreator = new ClientFascicolazioneServiceCreator(protocolloLogs, bindingFactory, url);
        }

        public FascicoloOutXml CreaFascicolo(FascicoloInXml request, string codiceamministrazione, string codiceAoo)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {

                    this._protocolloLogs.Info("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN");
                    var requestXML = this._protocolloSerializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, request, TipiValidazione.NO_NAMESPACE);
                    this._protocolloLogs.InfoFormat("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN AVVENUTA CORRETTAMENTE, XML: {0}", requestXML);

                    this._protocolloLogs.Info("CHIAMATA A CREAFASCICOLOSTRING");
                    var response = ws.Service.CreaFascicoloString(requestXML, codiceamministrazione, codiceAoo);
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
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA CREAZIONE DEL FASCICOLO, {ex.Message}", ex);
            }
        }

        public FascicoloOutXml LeggiFascicolo(LeggiFascicoloWSRequest request, string codiceAmministrazione, string codiceAoo)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    string fascicoloOutXml;

                    if (request.Id.HasValue)
                    {
                        this._protocolloLogs.InfoFormat($"CHIAMATA A LEGGI FASCICOLO J_IRIDE, ID: {request.Id}");
                        fascicoloOutXml = ws.Service.LeggiFascicoloString(request.Id.ToString(), "", "", request.Utente, request.Ruolo, codiceAmministrazione, codiceAoo, request.Classifica);
                    }
                    else
                    {
                        this._protocolloLogs.InfoFormat($"CHIAMATA A LEGGI FASCICOLO J_IRIDE, ANNO FASCICOLO: {request.Anno}, NUMERO FASCICOLO: {request.Numero}, UTENTE: {request.Utente}, RUOLO: {request.Ruolo}, CODICE AMMINISTRAZIONE: {codiceAmministrazione}, CODICE AOO: {codiceAoo}, CLASSIFICA: {request.Classifica}");
                        fascicoloOutXml = ws.Service.LeggiFascicoloString("", request.Anno, request.Numero, request.Utente, request.Ruolo, codiceAmministrazione, codiceAoo, request.Classifica);
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
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA DEL FASCICOLO ID: {request.Id}, NUMERO: {request.Numero}, ANNO: {request.Anno}, {ex.Message}", ex);
            }
        }

        //private static bool ValidateRemoteCertificate(object sender, X509Certificate cert, X509Chain chain, SslPolicyErrors error)
        //{
        //    if (error == SslPolicyErrors.None)
        //    {
        //        return true;   // already determined to be valid
        //    }

        //    switch (cert.GetCertHashString())
        //    {
        //        // thumbprints/hashes of allowed certificates (uppercase)
        //        case "186CA85B6669E4DDC5B243C5F2AB0D662EA587589FB20A5D31036ACE5ED9E0B2":
        //        case "FFF9D538B13954A770180C2B851FBC059BC68F12":

        //            Debug.WriteLine("Trusting X509Certificate '" + cert.Subject + "'");
        //            return true;

        //        default:
        //            return false;
        //    }
        //}
    }
}
