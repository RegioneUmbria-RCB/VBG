using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.LDPGISSservice;
using Init.SIGePro.Manager.Utils;
using System;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP
{
    public class LdpProxyServiceWrapper : ILdpAnnullamentoServiceWrapper
    {
        private readonly string _serviceUrlAnnullamento;
        private readonly BasicSoapAuthenticationCredentials _credentials;
        private readonly IBindingFactory _bindingFactory;

        public LdpProxyServiceWrapper(string serviceUrlAnnullamento, string serviceUserName, string serviceUserPassword)
        {
            this._serviceUrlAnnullamento = serviceUrlAnnullamento;
            this._credentials = new BasicSoapAuthenticationCredentials(serviceUserName, serviceUserPassword);
            this._bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();
        }

        private PresentazioneAreeUsoPubblicoSoapClient CreaWebServiceAnnullamento()
        {
            try
            {
                if (String.IsNullOrEmpty(this._serviceUrlAnnullamento))
                    throw new Exception("IL PARAMETRO URL_SERVIZIO_DOMANDE DELLA VERTICALIZZAZIONE SIT_LDP NON È STATO VALORIZZATO.");

                var endPointAddress = new EndpointAddress(this._serviceUrlAnnullamento);
                var binding = this._bindingFactory.CreateAndConfigure("defaultHttpBinding");

                if (string.Equals(endPointAddress.Uri.Scheme, "https", StringComparison.OrdinalIgnoreCase))
                {
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
                }

                return new PresentazioneAreeUsoPubblicoSoapClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public void EliminaOccupazione(string identificativoTemporaneo)
        {
            using (var ws = this.CreaWebServiceAnnullamento())
            {

                try
                {
                    using (var scope = new OperationContextScope(ws.InnerChannel))
                    {
                        this._credentials.AggiungiCredenzialiAContextScope();

                        var request = new ComplexTypePraticaIdentificativiDelete
                        {
                            identificativo_temporaneo = identificativoTemporaneo
                        };

                        ws.deleteOccupazione(request);
                    }
                }
                catch (Exception ex)
                {
                    ws.Abort();

                    throw;
                }
            }
        }

        public bool EsisteUnOccupazionePrenotata(string identificativoTemporaneo)
        {
            using (var ws = this.CreaWebServiceAnnullamento())
            {
                try
                {
                    using (var scope = new OperationContextScope(ws.InnerChannel))
                    {
                        this._credentials.AggiungiCredenzialiAContextScope();

                        var request = new ComplexTypeStringa
                        {
                            testo = identificativoTemporaneo
                        };

                        ws.getDatiOccupazioneSuoloByIdentificativoTemporaneo(request);

                        return true;
                    }
                }
                catch (Exception ex)
                {
                    if (string.Equals(ex.Message, "nessuna pratica con l'identificativo passato", StringComparison.OrdinalIgnoreCase))
                    {
                        return false;
                    }
                    ws.Abort();
                    throw;
                }
            }
        }
    }
}
