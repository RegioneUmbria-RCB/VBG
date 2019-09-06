using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.FVGWebService;
using log4net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FVGWebServiceProxy : IFVGWebServiceProxy
    {
        readonly string _webServiceUrl;
        readonly string _username;
        readonly string _password;
        ILog _log = LogManager.GetLogger(typeof(FVGWebServiceProxy));

        public FVGWebServiceProxy(IConfigurazione<ParametriFvgSol> config)
        {
            if (!config.Parametri.VerticalizzazioneAttiva)
            {
                throw new InvalidOperationException("Il modulo FVG_SOL non è attivo");
            }

            this._webServiceUrl = config.Parametri.ServiceUrl;
            this._username = config.Parametri.Username;
            this._password = config.Parametri.Password;
        }

        private T CallService<T>(Func<FormServiceClient, T> callback)
        {
            var endpoint = new EndpointAddress(this._webServiceUrl);
            var binding = new BasicHttpBinding("oggettiServiceBinding");    // IL web service utilizza la codifica MTOM

            using (var ws = new FormServiceClient(binding, endpoint))
            {
                try
                {
                    var scope = new OperationContextScope(ws.InnerChannel);

                    OperationContext.Current.OutgoingMessageHeaders.Add(CreateHeader());

                    return callback(ws);
                }
                catch (Exception ex)
                {
                    ws.Abort();
                    throw;
                }
            }
        }

        private string GetErrorMessageString(Avvertimento[] avvertimenti, Errore[] errori)
        {
            var sb = new StringBuilder("La chiamata al web service non è andata a buon fine. Esito chiamata: ");

            sb.Append(Environment.NewLine);

            if (avvertimenti != null && avvertimenti.Length > 0)
            {
                sb.Append("Avvertimenti:");
                sb.Append(Environment.NewLine);

                foreach (var w in avvertimenti)
                {
                    sb.Append($"{w.Codice} - {w.Descrizione} ({w.infoAvanzata})");
                    sb.Append(Environment.NewLine);
                }
            }

            if (errori != null && errori.Length > 0)
            {
                sb.Append("Errori:");
                sb.Append(Environment.NewLine);

                foreach (var w in errori)
                {
                    sb.Append($"{w.Codice} - {w.Descrizione} ({w.infoAvanzata})");
                    sb.Append(Environment.NewLine);
                }
            }

            return sb.ToString();
        }

        private MessageHeader CreateHeader()
        {
            return UserNameSecurityTokenHeader.FromUserNamePassword(this._username, this._password);
        }

        public XmlDocument GetManagedDataDaCodiceIstanza(long codiceIstanza)
        {
            return this.CallService(ws =>
            {
                var esito = ws.getManagedDataFromIdIstanza(codiceIstanza, out var avvertimenti, out var managedData, out var errori);

                if (!esito)
                {
                    throw new Exception(GetErrorMessageString(avvertimenti, errori));
                }

                var document = new XmlDocument();
                using (var ms = new MemoryStream(managedData.content.binaryData))
                using (var tr = new XmlTextReader(ms))
                {
                    tr.Namespaces = false;
                    document.Load(tr);
                }

                //document.LoadXml(Encoding.UTF8.GetString(managedData.content.binaryData));

                return document;
            });
        }

        public void SalvaFileXml(long codiceIstanza, string idModulo, byte[] statoXmlSerializzato)
        {
            CallService(ws =>
            {
                var xml = new BinaryContent
                {
                    contentType = "application/xml",
                    content = new BinaryContentContent
                    {
                        binaryData = statoXmlSerializzato
                    },
                    fileExtension = "xml"
                    
                };

                var esito = ws.salvaBinaryContent(codiceIstanza, idModulo, xml, out var avvertimenti, out var errori);

                if (!esito)
                {
                    throw new Exception(GetErrorMessageString(avvertimenti, errori));
                }

                return true;
            });
        }

        public byte[] CaricaFileXml(long codiceIstanza, string idModulo)
        {
            return CallService(ws =>
            {

                var esito = ws.getXMLModulo(codiceIstanza, idModulo, out var avvertimenti, out var xml, out var errori);

                if (!esito)
                {
                    //throw new Exception(GetErrorMessageString(avvertimenti, errori));
                    return null;
                }

                return xml.content.binaryData;
            });
        }

        public void SalvaFilePdf(long codiceIstanza, string idModulo, byte[] pdfDomanda)
        {
            CallService(ws =>
            {
                this._log.Info($"Salvataggio pfd per codice istanza {codiceIstanza} e idModulo {idModulo}");

                var xml = new BinaryContent
                {
                    contentType = "application/pdf",
                    content = new BinaryContentContent
                    {
                        binaryData = pdfDomanda
                    },
                    fileExtension = "pdf"
                };

                var esito = ws.salvaBinaryContent(codiceIstanza, idModulo, xml, out var avvertimenti, out var errori);

                if (!esito)
                {
                    this._log.Error($"Salvataggio pfd per codice istanza {codiceIstanza} e idModulo {idModulo} fallito: {GetErrorMessageString(avvertimenti, errori)}");

                    throw new Exception(GetErrorMessageString(avvertimenti, errori));
                }

                this._log.Info($"Salvataggio pfd per codice istanza {codiceIstanza} e idModulo {idModulo} riuscito");

                return true;
            });
        }
    }
}
