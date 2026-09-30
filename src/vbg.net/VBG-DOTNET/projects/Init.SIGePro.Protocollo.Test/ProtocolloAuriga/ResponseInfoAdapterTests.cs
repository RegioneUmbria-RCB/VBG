using System;
using Init.SIGePro.Protocollo.Auriga;
using Init.SIGePro.Protocollo.AurigaProxyService;
using Xunit;

namespace Init.SIGePro.Protocollo.Test.ProtocolloAuriga
{
    public class ResponseInfoAdapterTestabile : ProxyResponseInfoAdapter
    {
        public ResponseInfoAdapterTestabile(AurigaProxyResponseType response): base(response)
        {

        }

        public string RemoveXMLDeclaration(string xml)
        {
            return base.RemoveXMLDeclaration(xml);
        }
    }

    public class ResponseInfoAdapterTests
    {


        [Fact]
        public void RemoveXMLDeclaration_rimuove_la_dichiarazione_xml()
        {
            var responseInfoAdapter = new ResponseInfoAdapterTestabile(null);

            var xml = @"<?xml version=""1.0"" encoding=""ISO-8859-1""?><Output_UD></Output_UD>";
            var xmlExpected = @"<Output_UD></Output_UD>";

            var result = responseInfoAdapter.RemoveXMLDeclaration(xml);

            Assert.Equal(xmlExpected, result);

        }

        [Fact]
        public void RemoveXMLDeclaration_non_rimuove_la_dichiarazione_xml_se_non_esiste()
        {
            var responseInfoAdapter = new ResponseInfoAdapterTestabile(null);

            var xml = @"<Output_UD></Output_UD>";
            var xmlExpected = @"<Output_UD></Output_UD>";

            var result = responseInfoAdapter.RemoveXMLDeclaration(xml);

            Assert.Equal(xmlExpected, result);

        }
    }
}
