using Init.Sigepro.UrlSecurity.Runtime;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Init.Sigepro.UrlSecurity.Tests.Runtime
{
    public class EntitaXmlValidatorTests
    {
        [Fact]
        public void Se_contiene_escape_esadecimali_solleva_eccezione()
        {
            var validator = new EntitaXmlValidator();
            var urlParams = new List<ParametroDaValidare>();
            urlParams.Add(new ParametroDaValidare("param", @"&#x6d;&#x6f;&#x75;&#x73;&#x65", new HttpUtilityUrlDecoder()));

            Assert.Throws<RequestValidationException>(() => validator.Validate(urlParams));
        }

        [Fact]
        public void Se_non_contiene_escape_esadecimali_non_solleva_eccezione()
        {
            var validator = new EntitaXmlValidator();
            var urlParams = new List<ParametroDaValidare>();
            urlParams.Add(new ParametroDaValidare("param", @"stringa valida", new HttpUtilityUrlDecoder()));

            validator.Validate(urlParams);
        }
    }
}
