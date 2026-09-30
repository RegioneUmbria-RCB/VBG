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
    public class BlacklistValidatorTests
    {
        [Fact]
        public void Se_parametro_contiene_parola_in_blacklist_solleva_eccezione()
        {
            var configuration = new StaticUrlSecurityConfiguration(true, new[] { "blacklisted" }, Enumerable.Empty<string>());
            var validator = new BlacklistValidator(configuration);
            var urlParams = new List<ParametroDaValidare>();
            urlParams.Add(new ParametroDaValidare("param", "parametro blacklisted", new HttpUtilityUrlDecoder()));

            Assert.Throws<RequestValidationException>(() => validator.Validate(urlParams));
        }

        [Fact]
        public void Se_parametro_non_contiene_parola_in_blacklist_non_solleva_eccezione()
        {
            var configuration = new StaticUrlSecurityConfiguration(true, new[] { "blacklisted" }, Enumerable.Empty<string>());
            var validator = new BlacklistValidator(configuration);
            var urlParams = new List<ParametroDaValidare>();
            urlParams.Add(new ParametroDaValidare("param", "parametro asd asd ", new HttpUtilityUrlDecoder()));

            validator.Validate(urlParams);
        }
    }
}
