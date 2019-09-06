using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.DocArea;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using Init.SIGePro.Protocollo.ProtocolloDatagraphService;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Datagraph
{
    public class DatagraphServiceWrapperBase
    {
        string _url;
        protected string Token;
        protected ProtocolloLogs Logs;
        protected ProtocolloSerializer Serializer;

        public DatagraphServiceWrapperBase(string url, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            this._url = url;
            this.Logs = logs;
            this.Serializer = serializer;
        }

        protected DocAreaProxy CreaWebService2()
        {
            if (String.IsNullOrEmpty(_url))
                throw new Exception("IL PARAMETRO URL_PROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCAREA NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

            var ws = new DocAreaProxy { Url = _url };

            if (new Uri(_url).Scheme.ToLower() == ProtocolloConstants.HTTPS)
            {
                ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            }

            return ws;

        }

        protected DOCAREAprotoWse CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_url);
                if (String.IsNullOrEmpty(_url))
                {
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_DOCAREA NON È STATO VALORIZZATO.");
                }

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                {
                    ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
                }
                return new DOCAREAprotoWse(this._url);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE DATAGRAPH (DOCAREA), {0}", ex.Message), ex);
            }
        }
    }
}
