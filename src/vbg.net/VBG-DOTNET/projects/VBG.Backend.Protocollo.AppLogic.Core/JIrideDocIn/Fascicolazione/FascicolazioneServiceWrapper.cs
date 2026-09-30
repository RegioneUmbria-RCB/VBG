using FascicolazioneJIrideService;
using VBG.Shared.Infrastructure.ServiceModel;
using System.Diagnostics;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Fascicolazione
{
    public class FascicolazioneServiceWrapper : IFascicolazione
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _url;
        string _codiceAmministrazione;
        string _codiceAoo;
        private readonly ClientFascicolazioneServiceCreator _clientFascicolazioneServiceCreator;

        public FascicolazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string codiceAmministrazione, string codiceAoo)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._url = url;
            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
            this._clientFascicolazioneServiceCreator = new ClientFascicolazioneServiceCreator(logs, bindingFactory, url);
        }

        public FascicoloOutXml CreaFascicolo(FascicolazioneInfo info)
        {
            try
            {
                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
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

                    _logs.Info("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN");
                    var request = _serializer.Serialize(ProtocolloLogsConstants.CreaFascicoloRequestFileName, fascicoloIn, ProtocolloValidation.TipiValidazione.NO_NAMESPACE);
                    _logs.InfoFormat("SERIALIZZAZIONE DELL'OGGETTO FASCICOLOIN AVVENUTA CORRETTAMENTE, XML: {0}", request);
                    _logs.Info("CHIAMATA A CREAFASCICOLOSTRING");
                    var response = ws.Service.CreaFascicoloString(request, this._codiceAmministrazione, this._codiceAoo);
                    _logs.InfoFormat("RISPOSTA DA CREAFASCICOLOSTRING: {0}", response);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREAFASCICOLOSTRING");
                    var fascicoloOut = _serializer.Deserialize<FascicoloOutXml>(response);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA CREAFASCICOLOSTRING AVVENUTA CON SUCCESSO");

                    if (fascicoloOut.Id == 0 || !String.IsNullOrEmpty(fascicoloOut.Errore))
                    {
                        throw new Exception(fascicoloOut.Errore);
                    }

                    _logs.InfoFormat("CREAZIONE FASCICOLO AVVENUTA CON SUCCESSO, ID FASCICOLO: {0}, NUMERO FASCICOLO: {1}, ANNO FASCICOLO: {2}", fascicoloOut.Id, fascicoloOut.Numero, fascicoloOut.Anno);

                    return fascicoloOut;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA CREAZIONE DEL FASCICOLO, {ex.Message}", ex);
            }
        }

        public EsitoOperazione FascicolaDocumento(int IDFascicolo, int IDDocumento, string AggiornaClassifica, string Utente, string Ruolo, string idProtocollo)
        {
            try
            {
                string principale = "";
                this._logs.InfoFormat("IDPROTOCOLLO = {0}", idProtocollo);
                if (!String.IsNullOrEmpty(idProtocollo))
                {
                    var arrIdProtocollo = idProtocollo.Split('-');
                    if (arrIdProtocollo.Length > 1 && arrIdProtocollo[1] == "COPIA")
                    {
                        _logs.Info("E' UNA COPIA");
                        principale = "N";
                    }
                }

                using (var ws = this._clientFascicolazioneServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A FASCICOLADOCUMENTO J_IRIDE, IDFascicolo: {0}, IDDocumento: {1}, AggiornaClassifica: {2}, Utente: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAOO: {6}, principale: {7}", IDFascicolo, IDDocumento, AggiornaClassifica, Utente, Ruolo, this._codiceAmministrazione, this._codiceAoo, principale);
                    var esito = ws.Service.FascicolaDocumento(IDFascicolo, IDDocumento, AggiornaClassifica, Utente, Ruolo, this._codiceAmministrazione, this._codiceAoo, principale);

                    _logs.InfoFormat("RISPOSTA A FASCICOLADOCUMENTO J-IRIDE, ESITO: {0}", esito.Esito);

                    if (!esito.Esito)
                    {
                        throw new Exception(esito.Errore);
                    }

                    _logs.Info("FASCICOLAZIONE DEL DOCUMENTO AVVENUTA CORRETTAMENTE");

                    return esito;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE DURANTE LA FASCICOLAZIONE DEL DOCUMENTO ID {IDDocumento} NEL FASCICOLO ID {IDFascicolo}, {ex.Message}");
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
