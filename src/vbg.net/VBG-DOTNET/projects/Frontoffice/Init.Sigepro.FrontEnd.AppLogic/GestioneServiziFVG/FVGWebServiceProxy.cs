using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.FVGWebService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData;
using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using System;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG
{
    public class FVGWebServiceProxy : IFVGWebServiceProxy
    {
        private readonly string _webServiceUrl;
        private readonly string _username;
        private readonly string _password;
        private readonly ILog _log = LogManager.GetLogger(typeof(FVGWebServiceProxy));
        private readonly IBindingFactory _bindingFactory;
        public FVGWebServiceProxy(IConfigurazione<ParametriFvgSol> config, IBindingFactory bindingFactory)
        {
            if (!config.Parametri.VerticalizzazioneAttiva)
            {
                throw new InvalidOperationException("Il modulo FVG_SOL non è attivo");
            }

            this._webServiceUrl = config.Parametri.ServiceUrl;
            this._username = config.Parametri.Username;
            this._password = config.Parametri.Password;
            this._bindingFactory = bindingFactory;
        }

        private T CallService<T>(Func<FormServiceClient, T> callback)
        {
            var endpoint = new EndpointAddress(this._webServiceUrl);
            var binding = this._bindingFactory.CreateAndConfigure("oggettiServiceBinding"); // IL web service utilizza la codifica MTOM
            using (var ws = new FormServiceClient(binding, endpoint))
            {
                try
                {
                    using (var scope = new OperationContextScope(ws.InnerChannel))
                    {
                        OperationContext.Current.OutgoingMessageHeaders.Add(this.CreateHeader());
                        return callback(ws);
                    }
                }
                catch (Exception)
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
            if (avvertimenti?.Length > 0)
            {
                sb.Append("Avvertimenti:");
                sb.Append(Environment.NewLine);
                foreach (var w in avvertimenti)
                {
                    sb.Append($"{w.Codice} - {w.Descrizione} ({w.infoAvanzata})");
                    sb.Append(Environment.NewLine);
                }
            }

            if (errori?.Length > 0)
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
                    throw new Exception(this.GetErrorMessageString(avvertimenti, errori));
                }

                return new ManagedDataDeserializer().Parse(managedData.content.binaryData);
            });
        }

        public void SalvaFileXml(long codiceIstanza, string idModulo, byte[] statoXmlSerializzato)
        {
            this.CallService(ws =>
            {
                var xml = new BinaryContent { contentType = "application/xml", content = new BinaryContentContent { binaryData = statoXmlSerializzato }, fileExtension = "xml" };
                var esito = ws.salvaBinaryContent(codiceIstanza, idModulo, xml, out var avvertimenti, out var errori);
                if (!esito)
                {
                    throw new Exception(this.GetErrorMessageString(avvertimenti, errori));
                }

                return true;
            });
        }

        public byte[] CaricaFileXml(long codiceIstanza, string idModulo)
        {
            return this.CallService(ws =>
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
            this.CallService(ws =>
            {
                this._log.Debug($"Salvataggio pdf per codice istanza {codiceIstanza} e idModulo {idModulo}");
                var xml = new BinaryContent { contentType = "application/pdf", content = new BinaryContentContent { binaryData = pdfDomanda }, fileExtension = "pdf" };
                var esito = ws.salvaBinaryContent(codiceIstanza, idModulo, xml, out var avvertimenti, out var errori);
                if (!esito)
                {
                    this._log.Error($"Salvataggio pfd per codice istanza {codiceIstanza} e idModulo {idModulo} fallito: {this.GetErrorMessageString(avvertimenti, errori)}");
                    throw new Exception(this.GetErrorMessageString(avvertimenti, errori));
                }

                this._log.Debug($"Salvataggio pdf per codice istanza {codiceIstanza} e idModulo {idModulo} riuscito");
                return true;
            });
        }
    }
}