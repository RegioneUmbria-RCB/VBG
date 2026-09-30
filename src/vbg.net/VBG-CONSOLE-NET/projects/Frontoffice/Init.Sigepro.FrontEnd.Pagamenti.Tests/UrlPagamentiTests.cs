using Init.Sigepro.FrontEnd.Pagamenti.Tests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests
{
    [TestClass]
    public class UrlPagamentiTests
    {


        const int StepId = 0;

        [TestMethod]
        public void Genera_un_url_accodando_le_informazioni_della_domanda()
        {
            var riferimentiDomanda = new RiferimentiDomanda(new MockRiferimentiDomanda(), 0);
            var url = new UrlPagamenti("~/test.url?par=val", riferimentiDomanda, new MockUrlEncoder(), new MockResolveUrl());

            var parsedUrl = url.ToString();

            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf($"&idComune={riferimentiDomanda.IdComune}"));
            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf($"&software={riferimentiDomanda.Software}"));
            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf($"&idPresentazione={riferimentiDomanda.IdDomanda}"));
            Assert.AreNotEqual<int>(-1, parsedUrl.IndexOf($"&stepId={StepId}"));
        }

        [TestMethod]
        public void Genera_un_url_sostituendo_ai_segnaposto_le_informazioni_della_domanda()
        {
            var riferimentiDomanda = new RiferimentiDomanda(new MockRiferimentiDomanda(), 0);
            var url = new UrlPagamenti("/{idComune}/{software}/{idPresentazione}/{stepId}", riferimentiDomanda, new MockUrlEncoder(), new MockResolveUrl());
            string expected = $"/{riferimentiDomanda.IdComune}/{riferimentiDomanda.Software}/{riferimentiDomanda.IdDomanda}/{StepId}";
            var parsedUrl = url.ToString();

            Assert.AreEqual<string>(expected, parsedUrl.Substring(0, expected.Length));
        }
    }
}
