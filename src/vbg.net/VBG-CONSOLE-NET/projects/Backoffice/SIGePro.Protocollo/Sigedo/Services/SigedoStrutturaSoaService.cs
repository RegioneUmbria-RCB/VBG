using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.Net;
using Init.SIGePro.Protocollo.Sigedo.Proxies.Struttura;

namespace Init.SIGePro.Protocollo.Sigedo.Services
{
    public class SigedoStrutturaSoaService
    {
        public static class Constants
        {
            public const string DESCRIZIONE_UFFICIO_NON_TROVATO = "NON DEFINITO";
            public const string CANALE_TEST = "TEST";
            public const string CANALE_PRODUZIONE = "PRODUZIONE";
        }

        ProtocolloLogs _logs;
        string _endPointAddress;

        public SigedoStrutturaSoaService(string endPointAddress, ProtocolloLogs logs)
        {
            _logs = logs;
            _endPointAddress = endPointAddress;
        }

        private getStrutturasoaService CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice STRUTTURASOA di SIGEDO");
                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_STRUTTURASOA DELLA VERTICALIZZAZIONE PROTOCOLLO_SIGEDO NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                ServicePointManager.ServerCertificateValidationCallback += (sender, certificate, chain, sslPolicyErrors) => true;

                var ws = new getStrutturasoaService { Url = _endPointAddress };

                _logs.Debug("Fine creazione del webservice STRUTTURASOA");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }

        private void ValidateCanale(string canale)
        {
            try
            {
                if (String.IsNullOrEmpty(canale))
                    throw new Exception("CANALE NON VALORIZZATO");

                if (canale != Constants.CANALE_TEST && canale != Constants.CANALE_PRODUZIONE)
                    throw new Exception(String.Format("IL CANALE DEVE ESSERE VALORIZZATO A {0} o {1} MENTRE E' STATO VALORIZZATO A {2}", Constants.CANALE_TEST, Constants.CANALE_PRODUZIONE, canale));
            }
            catch (Exception ex)
            {
                throw new Exception("VALIDAZIONE DEL CANALE NON CORRETTA", ex);
            }
        }

        internal string GetDescrizioneUfficio(string codiceUfficio, string canale)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    ValidateCanale(canale);
                    string descrizione = String.Empty;
                    _logs.InfoFormat("Chiamata a getStrutturasoa, recupero della descrizione dell'unità (ufficio), ambito (canale): {0}, codiceufficio: {1}", canale, codiceUfficio);
                    var response = ws.getStrutturasoa(canale);
                    if (response != null)
                    {
                        descrizione = response.Where(x => x.codiceunita == codiceUfficio).Select(y => y.descrizioneunita).FirstOrDefault();
                        
                        if (String.IsNullOrEmpty(descrizione))
                            descrizione = Constants.DESCRIZIONE_UFFICIO_NON_TROVATO;
                        
                        _logs.InfoFormat("Chiamata a getStrutturasoa effettuata con successo, ambito (canale): {0}, codice ufficio: {1}, descrizione ufficio: {2}", canale, codiceUfficio, descrizione);
                    }

                    return descrizione;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLA DESCRIZIONE DELL'UNITA' (UFFICIO), ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
