using Init.Sigepro.UrlSecurity.Runtime;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xunit;

namespace Init.Sigepro.UrlSecurity.Tests.Runtime
{
    public class StringheEsadecimaliValidatorTests
    {
        [Fact]
        public void Se_contiene_escape_esadecimali_solleva_eccezione()
        {
            var validator = new StringheEsadecimaliValidator();
            var urlParams = new List<ParametroDaValidare>();
            urlParams.Add(new ParametroDaValidare("param", @"\x6a\x61\x76\x61\x73\x63\x72\x69\x70\x74\x3a\x61\x6c\x65\x72\x74\x28429\x29", new HttpUtilityUrlDecoder()));

            Assert.Throws<RequestValidationException>(() => validator.Validate(urlParams));
        }

        [Fact]
        public void Se_non_contiene_escape_esadecimali_non_solleva_eccezione()
        {
            var validator = new StringheEsadecimaliValidator();
            var urlParams = new List<ParametroDaValidare>();
            urlParams.Add(new ParametroDaValidare("param", @"stringa valida", new HttpUtilityUrlDecoder()));

            validator.Validate(urlParams);
        }
    }
}
