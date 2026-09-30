using log4net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.Livorno.VerificaPagamenti
{
    public class PagamentiServiceProxy
    {
        [XmlRoot("wm_dia")]
        public class WmDia
        {
            public class PagamentoType
            {
                string _cifra = "0";

                [XmlElement("esiste")]
                public string Esiste { get; set; }

                [XmlElement("cifra")]
                public string Cifra
                {
                    get { return _cifra; }
                    set
                    {
                        if (!String.IsNullOrEmpty(value))
                        {
                            this._cifra = value;
                        }
                    }
                }

                public float ImportoAsFloat
                {
                    get
                    {
                        return Convert.ToSingle(this.Cifra) / 100.0f;
                    }
                }
            }

            [XmlElement("pagamento")]
            public PagamentoType Pagamento { get; set; }

            public static WmDia FromWebServiceResponse(string response)
            {
                var serializer = new XmlSerializer(typeof(WmDia));
                return (WmDia)serializer.Deserialize(new StringReader(response));
            }
        }

        private ILog _log = LogManager.GetLogger(typeof(PagamentiServiceProxy));
        private string _serviceUrl;

        public PagamentiServiceProxy(string serviceUrl)
        {
            this._serviceUrl = serviceUrl;
        }

        public WmDia VerificavaliditaPagamento(string codicePagamentoDigitale)
        {
            var binding = new BasicHttpBinding();
            var endpoint = new EndpointAddress(this._serviceUrl);

            using (var ws = new VerificaPagamentiLivorno.ws_diaSoapClient(binding, endpoint))
            {
                try
                {
                    _log.DebugFormat("Verifica del pagamento con codice {0}", codicePagamentoDigitale);

                    var esito = ws.wm_dia(codicePagamentoDigitale);

                    _log.DebugFormat("Esito della verifica: {0}", esito);

                    return WmDia.FromWebServiceResponse(esito);
                }
                catch(Exception ex)
                {
                    _log.ErrorFormat("Errore durante la validazione del codice pagamento {0}: {1}", codicePagamentoDigitale, ex.ToString());

                    ws.Abort();

                    throw;
                }
            }
        }
    }
}
