using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
using MovimentiTest.TestSegnapostoSchedeDinamiche.Utils;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
    [TestClass]
    public class SegnapostoSchedaDinamicaTest
    {
        [TestMethod]
        public void NomeTag_RestituisceCampoDinamico()
        {
            var expected = "schedaDinamica";

            var segnaposto = new SegnapostoSchedaDinamica( new StubGeneratoreHtmlSchede());

            var result = segnaposto.NomeTag;

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        public void NomeArgomento_RestituisceId()
        {
            var expected = "id";

            var segnaposto = new SegnapostoSchedaDinamica(new StubGeneratoreHtmlSchede());

            var result = segnaposto.NomeArgomento;

            Assert.AreEqual<string>(expected, result);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void Elabora_ConDomandaNull_SollevaEccezione()
        {
            var segnaposto = new SegnapostoSchedaDinamica(new StubGeneratoreHtmlSchede());

            segnaposto.Elabora(null, "", "");
        }
    }
}
