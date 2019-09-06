using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.FascicolazioneJIrideService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Validation;

namespace Init.SIGePro.Protocollo.JIride.Fascicolazione
{
    public class FascicolazioneServiceWrapper : IFascicolazione
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _url;
        string _codiceAmministrazione;
        string _codiceAoo;

        public FascicolazioneServiceWrapper(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string codiceAmministrazione, string codiceAoo)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._url = url;
            this._codiceAmministrazione = codiceAmministrazione;
            this._codiceAoo = codiceAoo;
        }

        public FascicoloOutXml CreaFascicolo(FascicolazioneInfo info)
        {
            try
            {
                using (var ws = CreaWebService())
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
                    var response = ws.CreaFascicoloString(request, this._codiceAmministrazione, this._codiceAoo);
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

                using (var ws = CreaWebService())
                {
                    _logs.InfoFormat("CHIAMATA A FASCICOLADOCUMENTO J_IRIDE, IDFascicolo: {0}, IDDocumento: {1}, AggiornaClassifica: {2}, Utente: {3}, Ruolo: {4}, CodiceAmministrazione: {5}, CodiceAOO: {6}, principale: {7}", IDFascicolo, IDDocumento, AggiornaClassifica, Utente, Ruolo, this._codiceAmministrazione, this._codiceAoo, principale);
                    var esito = ws.FascicolaDocumento(IDFascicolo, IDDocumento, AggiornaClassifica, Utente, Ruolo, this._codiceAmministrazione, this._codiceAoo, principale);

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

        public FascicoloOutXml LeggiFascicolo(string numero, string anno, string classifica, int id, string utente, string ruolo)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    string fascicoloOutXml = "";

                    _logs.InfoFormat("ID FASCICOLO: {0}", id.ToString());
                    if (id != 0)
                    {
                        _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE, ID: {0}", id.ToString());
                        fascicoloOutXml = ws.LeggiFascicoloString(id.ToString(), "", "", utente, ruolo, this._codiceAmministrazione, this._codiceAoo, classifica);
                    }
                    else
                    {
                        _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE, ANNO FASCICOLO: {0}, NUMERO FASCICOLO: {1}, UTENTE: {2}, RUOLO: {3}, CODICE AMMINISTRAZIONE: {4}, CODICE AOO: {5}, CLASSIFICA: {6}", anno, numero, utente, ruolo, this._codiceAmministrazione, this._codiceAoo, classifica);
                        fascicoloOutXml = ws.LeggiFascicoloString("", anno, numero, utente, ruolo, this._codiceAmministrazione, this._codiceAoo, classifica);
                    }

                    //_logs.InfoFormat("RISPOSTA DA LEGGIFASCICOLOSTRING: {0}", fascicoloOutXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGIFASCICOLOSTRING");
                    var fascicoloOut = _serializer.Deserialize<FascicoloOutXml>(fascicoloOutXml);
                    _logs.Info("DESERIALIZZAZIONE DELLA RISPOSTA DA LEGGIFASCICOLOSTRING AVVENUTA CON SUCCESSO");

                    _serializer.Serialize(ProtocolloLogsConstants.LeggiFascicoloResponseFileName, fascicoloOut);

                    if (fascicoloOut.Id == 0 || !String.IsNullOrEmpty(fascicoloOut.Errore))
                    {
                        throw new Exception(fascicoloOut.Errore);
                    }

                    _logs.InfoFormat("CHIAMATA A LEGGIFASCICOLOSTRING AVVENUTA CORRETTAMENTE, ID FASCICOLO: {0}", fascicoloOut.Id);

                    return fascicoloOut;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO DURANTE LA LETTURA DEL FASCICOLO ID: {id}, NUMERO: {numero}, ANNO: {anno}, {ex.Message}", ex);
            }
        }

        private DocWSFascicoliSoapClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di fascicolazione J-IRIDE");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL_FASC DELLA VERTICALIZZAZIONE PROTOCOLLO_JIRIDE NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                var ws = new DocWSFascicoliSoapClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del web service di fascicolazione J-IRIDE");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }
    }
}
