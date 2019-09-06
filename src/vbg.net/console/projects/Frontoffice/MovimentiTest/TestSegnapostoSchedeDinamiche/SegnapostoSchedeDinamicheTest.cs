using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using MovimentiTest.TestSegnapostoSchedeDinamiche.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche.Utils;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
    [TestClass]
    public class SegnapostoSchedeDinamicheTest
    {
        [TestMethod]
        public void NomeTag_RestituisceCampoDinamico()
        {
            var expected = "schedeDinamiche";

            var segnaposto = new SegnapostoSchedeDinamiche(new StubGeneratoreHtmlSchede(), new StubParametriGenerazioneRiepilogo(0));

            var result = segnaposto.NomeTag;

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        public void NomeArgomento_RestituisceId()
        {
            var expected = String.Empty;

            var segnaposto = new SegnapostoSchedeDinamiche(new StubGeneratoreHtmlSchede(), new StubParametriGenerazioneRiepilogo(0));

            var result = segnaposto.NomeArgomento;

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void Elabora_ConDomandaNull_SollevaEccezione()
        {
            var segnaposto = new SegnapostoSchedeDinamiche(new StubGeneratoreHtmlSchede(), new StubParametriGenerazioneRiepilogo(0));

            segnaposto.Elabora(null, "", "");
        }

    }
}
