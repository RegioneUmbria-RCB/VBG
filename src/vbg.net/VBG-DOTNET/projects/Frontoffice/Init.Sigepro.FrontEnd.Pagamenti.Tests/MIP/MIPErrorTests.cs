using Xunit;
using VBG.Pagamenti.Legacy.MIP;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    public class MIPErrorTests
    {
        [Fact]
        public void DeserializzaDaStringa()
        {
            var xmlString = "<ErrorData><NumeroOperazione>123</NumeroOperazione><IDOperazione>BACK</IDOperazione><CodiceErrore>CodiceErrore</CodiceErrore><ErroreD>ErroreD</ErroreD></ErrorData>";
            var obj = MIPError.FromXmlString(xmlString);

            Assert.Equal("123", obj.NumeroOperazione);
            Assert.Equal("BACK", obj.IDOperazione);
            Assert.Equal("CodiceErrore", obj.CodiceErrore);
            Assert.Equal("ErroreD", obj.ErroreD);
        }
    }
}
