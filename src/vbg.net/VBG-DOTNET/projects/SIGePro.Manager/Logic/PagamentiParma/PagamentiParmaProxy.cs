using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Utils;
using SIGePro.Manager.Verticalizzazioni;
using Init.SIGePro.Manager.WsParmaPagamenti;
using log4net;
using System;
using System.IO;
using System.ServiceModel;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class PagamentiParmaProxy : WebServiceProxyBase<PagamentiSoapClient>, IPagamentiParmaProxy
    {
        public static string ToXmlString<T>(T cls)
        {
            using (var ms = new MemoryStream())
            {
                var xs = new XmlSerializer(cls.GetType());
                xs.Serialize(ms, cls);

                return Encoding.Default.GetString(ms.ToArray());
            }
        }

        private readonly VerticalizzazioneCosapParma _configurazione;
        private readonly string _overrideWebServiceUrl;
        private readonly string _overrideWebServiceUser;
        private readonly string _overrideWebServicePass;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiParmaProxy));

        protected override string WebServiceUrl => String.IsNullOrEmpty(this._overrideWebServiceUrl) ?
                                            this._configurazione.WebServiceUrl :
                                            this._overrideWebServiceUrl;

        protected override PagamentiSoapClient CreateService(BasicHttpBinding binding, EndpointAddress endpoint)
        {
            return new PagamentiSoapClient(binding, endpoint);
        }

        public PagamentiParmaProxy(VerticalizzazioneCosapParma configurazione, string overrideWebServiceUrl, string overrideWebServiceUser, string overrideWebServicePass)
            : base(StaticKernelContainer.GetService<IBindingFactory>())
        {
            this._configurazione = configurazione;
            this._overrideWebServiceUrl = overrideWebServiceUrl;
            this._overrideWebServiceUser = overrideWebServiceUser;
            this._overrideWebServicePass = overrideWebServicePass;
        }


        public DtoEsitoPagameti InserisciEmissioniAvvisoPagoPA(int codiceIdentificativo, RateizzazioneEmissioniParmaBase emissioniFactory)
        {
            return this.CallService((ws) =>
            {

                //var emissioni = new Emissione[0];
                var idUtente = (int?)null;
                var emissione = emissioniFactory.GetEmissione();

                this._log.Debug($"PagamentiParmaProxy->InserisciEmissioniAvvisoPagoPA: codiceIdentificativo={codiceIdentificativo}, emissione={ToXmlString(emissione)}, " +
                           $"anno={this._configurazione.Anno}, idUtente={idUtente}, utenteinserimento={this._configurazione.UtenteInserimento}, fonte={this._configurazione.Fonte}");

                var user = String.IsNullOrEmpty(this._overrideWebServiceUser) ? this._configurazione.User : this._overrideWebServiceUser;
                var pass = String.IsNullOrEmpty(this._overrideWebServicePass) ? this._configurazione.Password : this._overrideWebServicePass;

                var esitoInserimentoEmissioni = ws.InserisciEmissioneAvvisoPagoPa(emissione, codiceIdentificativo, this._configurazione.Anno,
                                                idUtente,
                                                this._configurazione.UtenteInserimento, this._configurazione.Fonte, user,
                                                pass);

                this._log.Debug($"PagamentiParmaProxy->InserisciEmissioniAvvisoPagoPA (risultato): {ToXmlString(esitoInserimentoEmissioni)}");

                return esitoInserimentoEmissioni;
            });
        }


        public DtoOutInserimentoEmissioni InserisciEmissioniByCodiceAnno(string codiceIdentificativo, RateizzazioneEmissioniParmaBase emissioniFactory)
        {
            return this.CallService((ws) =>
            {
                var emissioni = new Emissione[0];
                var idUtente = (int?)null;
                var emissione = emissioniFactory.GetEmissioni();

                this._log.Debug($"PagamentiParmaProxy->InserisciEmissioniByCodiceAnno: codiceIdentificativo={codiceIdentificativo}, emissione={ToXmlString(emissione)}, " +
                           $"anno={this._configurazione.Anno}, idUtente={idUtente}, utenteinserimento={this._configurazione.UtenteInserimento}, fonte={this._configurazione.Fonte}");

                var esitoInserimentoEmissioni = ws.InserisciEmissioniByCodiceAnno(emissione, codiceIdentificativo, this._configurazione.Anno,
                                                idUtente,
                                                this._configurazione.UtenteInserimento, this._configurazione.Fonte, this._configurazione.User,
                                                this._configurazione.Password);

                this._log.Debug($"PagamentiParmaProxy->InserisciEmissioniByCodiceAnno (risultato): {ToXmlString(esitoInserimentoEmissioni)}");

                return esitoInserimentoEmissioni;
            });
        }



    }
}
