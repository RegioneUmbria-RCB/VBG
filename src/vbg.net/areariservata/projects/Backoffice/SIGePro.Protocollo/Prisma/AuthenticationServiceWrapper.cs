using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloPrismaService;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma
{
    public class AuthenticationServiceWrapper : ServiceWrapperBase
    {
        protected ProtocolloLogs _logs;
        protected ProtocolloSerializer _serializer;
        string _endPointAddress;

        public AuthenticationServiceWrapper(string endPointAddress, ProtocolloLogs logs, ProtocolloSerializer serializer, CredentialsInfo credentials) : base(credentials)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._endPointAddress = endPointAddress;
        }

        protected DOCAREAProtoSoapClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(_endPointAddress);
                var binding = new BasicHttpBinding("prismaHttpBinding");

                if (String.IsNullOrEmpty(_endPointAddress))
                    throw new Exception("IL PARAMETRO URL_EXTENDED DELLA VERTICALIZZAZIONE PROTOCOLLO_PRISMA NON È STATO VALORIZZATO.");

                if (endPointAddress.Uri.Scheme.ToLower() == ProtocolloConstants.HTTPS)
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };

                return new DOCAREAProtoSoapClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }

        }

        public string Login()
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    using (OperationContextScope scope = new OperationContextScope(ws.InnerChannel))
                    {
                        base.AggiungiCredenzialiAContextScope();
                        _logs.InfoFormat("CHIAMATA A LOGIN DEL WEB SERVICE DOCAREA, CODICE ENTE: {0}, USERNAME: {1}, PASSWORD: {2}", base.Credentials.CodiceEnte, base.Credentials.Username, base.Credentials.Password);
                        var response = ws.login(base.Credentials.CodiceEnte, base.Credentials.Username, base.Credentials.Password);
                        if (response.lngErrNumber != 0)
                            throw new Exception(String.Format("NUMERO ERRORE: {0}, DESCRIZIONE ERRORE: {1}", response.lngErrNumber.ToString(), response.strErrString));

                        if (String.IsNullOrEmpty(response.strDST))
                            throw new Exception("IL TOKEN RESTITUITO DALL'AUTENTICAZIONE RISULTA ESSERE VUOTO");

                        _logs.InfoFormat("AUTENTICAZIONE AL WEB SERVICE AVVENUTA CORRETTAMENTE, TOKEN RESTITUITO: {0}", response.strDST);

                        return response.strDST;
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'AUTENTICAZIONE AL WEB SERVICE {0}", ex.Message), ex);
            }
        }
    }
}
