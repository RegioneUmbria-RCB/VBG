using VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione.CreaCopie;
using System.Diagnostics;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;
using ProtocollazioneJIrideService;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public class ProtocollazioneServiceWrapper
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _codiceAmministrazione;
        string _url;
        string _codiceAoo;
        private readonly ClientProtocollazioneServiceCreator _clientProtocollazioneServiceCreator;

        public ProtocollazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string codiceAmministrazione, string codiceAoo)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._url = url;
            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
            this._clientProtocollazioneServiceCreator = new ClientProtocollazioneServiceCreator(logs, bindingFactory, url);
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

        public ProtocolloOutXml InserisciDocumento(ProtocolloInXml protocolloIn)
        {
            try
            {
                var requestXml = _serializer.Serialize(ProtocolloLogsConstants.InserisciDocumentoRequestFileName, protocolloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                _logs.Info("CHIAMATA A INSERISCI DOCUMENTO STRING DI J-IRIDE");

                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    var responseXml = ws.Service.InserisciDocumentoEAnagraficheString(requestXml, this._codiceAmministrazione, this._codiceAoo);
                    _logs.InfoFormat("RISPOSTA A INSERISCI DOCUMENTO STRING DI J-IRIDE, {0}", responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA");
                    var response = _serializer.Deserialize<ProtocolloOutXml>(responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE L'INSERIMENTO DEL DOCUMENTO {ex.Message}", ex);
            }
        }

        public ProtocolloOutXml InserisciProtocollo(ProtocolloInXml protocolloIn)
        {
            try
            {
                var requestXml = _serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, protocolloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                _logs.Info("CHIAMATA A INSERISCI PROTOCOLLO STRING DI J-IRIDE");

                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    var responseXml = ws.Service.InserisciProtocolloEAnagraficheString(requestXml, this._codiceAmministrazione, this._codiceAoo);
                    _logs.InfoFormat("RISPOSTA A INSERISCI PROTOCOLLO STRING DI J-IRIDE, {0}", responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA");
                    var response = _serializer.Deserialize<ProtocolloOutXml>(responseXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA AVVENUTA CON SUCCESSO");

                    if (!String.IsNullOrEmpty(response.Errore))
                    {
                        throw new Exception(response.Errore);
                    }

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE L'INSERIMENTO DEL PROTOCOLLO {ex.Message}", ex);
            }
        }

        public DocumentoOutXml LeggiProtocollo(short annoProtocollo, int numeroProtocollo, string operatore, string ruolo)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    this._logs.InfoFormat($"CHIAMATA A LEGGI PROTOCOLLO STRING DI J-IRIDE, ANNO PROTOCOLLO: {annoProtocollo}, NUMERO PROTOCOLLO: {numeroProtocollo}, OPERATORE: {operatore}, RUOLO: {ruolo}, CODICE AMMINISTRAZIONE: {_codiceAmministrazione}");
                    var responseXml = ws.Service.LeggiProtocolloString(annoProtocollo, numeroProtocollo, operatore, ruolo, _codiceAmministrazione, _codiceAoo, "");

                    if (this._logs.IsDebugEnabled)
                    {
                        this._logs.InfoFormat($"RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE SALVATA IN : {ProtocolloLogsConstants.LeggiProtocolloResponseFileName}");
                        this._serializer.LogAndValidate(ProtocolloLogsConstants.LeggiProtocolloResponseFileName, responseXml);
                    }

                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING DI J-IRIDE");
                    var response = this._serializer.Deserialize<DocumentoOutXml>(responseXml);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI PROTOCOLLO STRING AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA LETTURA DEL PROTOCOLLO NUMERO {numeroProtocollo}, ANNO {annoProtocollo}, {ex.Message}", ex);
            }
        }

        public DocumentoOutXml LeggiDocumento(int idProtocollo, string operatore, string ruolo)
        {
            try
            {
                using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
                {
                    this._logs.InfoFormat($"CHIAMATA A LEGGI DOCUMENTO STRING DI J-IRIDE, IDPROTOCOLLO: {idProtocollo}, OPERATORE: {operatore}, RUOLO: {ruolo}, CODICE AMMINISTRAZIONE: {_codiceAmministrazione}");
                    var responseXml = ws.Service.LeggiDocumentoString(idProtocollo, operatore, ruolo, _codiceAmministrazione, _codiceAoo);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING DI J-IRIDE");
                    var response = this._serializer.Deserialize<DocumentoOutXml>(responseXml);
                    this._logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA A LEGGI DOCUMENTO STRING AVVENUTA CON SUCCESSO");

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA CHIAMATA A LEGGI DOCUMENTO, {ex.Message}", ex);
            }
        }

        public CreaCopieOutXml CreaCopieString(string creaCopieRequestXml, string codiceAmministrazione, string codiceAOO)
        {

            using (var ws = this._clientProtocollazioneServiceCreator.CreateClient())
            {
                var creaCopieOutXml = ws.Service.CreaCopieString(creaCopieRequestXml, codiceAmministrazione, codiceAOO);
                this._logs.InfoFormat("RISPOSTA DA CREA COPIE, RESPONSE XML: {0}", creaCopieOutXml);
                var creaCopieOut = this._serializer.Deserialize<CreaCopieOutXml>(creaCopieOutXml);
                return creaCopieOut;
            }
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
                    _logs.Info("E' UNA COPIA");
                    return true;
                }
            }

            return false;
        }
    }
}
