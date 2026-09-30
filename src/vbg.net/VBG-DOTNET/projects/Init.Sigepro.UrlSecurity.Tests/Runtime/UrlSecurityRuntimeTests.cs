using Init.Sigepro.UrlSecurity.Configuration;
using Init.Sigepro.UrlSecurity.Runtime;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Init.Sigepro.UrlSecurity.Tests.Runtime
{
    public class UrlSecurityRuntimeTests
    {
        private static string[] blacklist = new[]
            {
                "<script",
                "</script",
                "<iframe",
                "</iframe",
                "<textarea",
                "<select",
                "<form",
                "<svg",
                "xss:",
                "expression",
                "alert(",
                "alert&#x28",
                "<input",
                "<img",
                "mouse",
                "eval(",
                "eval&#x28",
                "location",
                "window",
                "source",
                "<a "
            };

        private UrlSecurityRuntime _runtime;


        public UrlSecurityRuntimeTests()
        {
            var config = new StaticUrlSecurityConfiguration(true, blacklist, Enumerable.Empty<string>());

            var validatorsProvider = new ParametersValidatorProvider(
                new BlacklistValidator(config),
                new EntitaXmlValidator(),
                new StringheEsadecimaliValidator()
            );
            
            var urlDecoder = new HttpUtilityUrlDecoder();

            this._runtime = new UrlSecurityRuntime(config, urlDecoder, validatorsProvider);
        }

        [Fact]
        public void test_problema_1_di_42()
        {
            var uri = new Uri("http://www.myurl.com/backend/batchscadenzario/listPerOperatore.htm");
            var qs = new NameValueCollection();
            qs.Add("maxRows", "50");
            qs.Add("tab", "%22%27%3E%3CA+HREF%3D%22%2FWF_XSRF1854.html%22%3EInjected+Link%3C%2FA%3E");
            qs.Add("movimenti_da_visionare_id_tr_", "true");
            qs.Add("movimenti_da_visionare_id_p_", "1");
            qs.Add("movimenti_da_visionare_id_mr_", "50");

            Assert.Throws<RequestValidationException>(() => this._runtime.CheckUrl(uri, qs));
        }

        [Fact]
        public void Verifica_url_con_entita_xml_solleva_eccezione()
        {

        }

        [Fact]
        public void Verifica_url_con_stringhe_esadecimali_solleva_eccezione()
        {

        }
    }
}
