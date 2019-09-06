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
    public class UrlPagamentiMIPTests
    {
        [TestMethod]
        public void Genera_un_url_con_le_informazioni_della_domanda()
        { 
            var riferimentiDomanda = new RiferimentiDomanda("IDCOMUNE", "SOFTWARE", 123, 456);
            var url = new UrlPagamenti("~/test.url?par=val", riferimentiDomanda, new MockUrlEncoder(), new MockResolveUrl());

            var parsedUrl = url.ToString();

            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf("&idComune=IDCOMUNE"));
            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf("&software=SOFTWARE"));
            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf("&idPresentazione=123"));
            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf("&stepId=456"));
            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf("&token=TOKEN"));
        }
    }
}
