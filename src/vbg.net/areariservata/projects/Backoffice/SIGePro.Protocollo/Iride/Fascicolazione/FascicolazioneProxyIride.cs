using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Iride.Fascicolazione
{
    public class FascicolazioneProxyIride : FascicolazioneProxy
    {

        public FascicolazioneProxyIride(string url, string proxyAddress) : base(url, proxyAddress)
        {

        }

        [System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://tempuri.org/LeggiFascicolo", RequestNamespace = "http://tempuri.org/", ResponseNamespace = "http://tempuri.org/", Use = System.Web.Services.Description.SoapBindingUse.Literal, ParameterStyle = System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
        [return: System.Xml.Serialization.XmlElementAttribute("FascicoloOut", IsNullable = true)]
        public FascicoloOut LeggiFascicolo(string IDFascicolo, string Anno, string Numero, string Utente, string Ruolo, string CodiceAmministrazione, string CodiceAOO)
        {
            object[] results = this.Invoke("LeggiFascicolo", new object[] {
                    IDFascicolo,
                    Anno,
                    Numero,
                    Utente,
                    Ruolo,
                    CodiceAmministrazione,
                    CodiceAOO});
            return ((FascicoloOut)(results[0]));
        }
    }
}
