using Init.Sigepro.FrontEnd.Pagamenti.MIP;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    [TestClass]
    public class MIPErrorTests
    {
        [TestMethod]
        public void DeserializzaDaStringa()
        {
            var xmlString = "<ErrorData><NumeroOperazione>123</NumeroOperazione><IDOperazione>BACK</IDOperazione><CodiceErrore>CodiceErrore</CodiceErrore><ErroreD>ErroreD</ErroreD></ErrorData>";
            var obj = MIPError.FromXmlString(xmlString);

            Assert.AreEqual<string>("123", obj.NumeroOperazione);
            Assert.AreEqual<string>("BACK", obj.IDOperazione);
            Assert.AreEqual<string>("CodiceErrore", obj.CodiceErrore);
            Assert.AreEqual<string>("ErroreD", obj.ErroreD);
        }
    }
}
