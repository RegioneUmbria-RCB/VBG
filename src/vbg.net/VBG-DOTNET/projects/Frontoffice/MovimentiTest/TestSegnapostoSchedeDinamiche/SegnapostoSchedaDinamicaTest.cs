using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xunit;
using MovimentiTest.TestSegnapostoSchedeDinamiche.Utils;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
    public class SegnapostoSchedaDinamicaTest
    {
        [Fact]
        public void NomeTag_RestituisceCampoDinamico()
        {
            var expected = "schedaDinamica";
            var segnaposto = new SegnapostoSchedaDinamica(new StubGeneratoreHtmlSchede());
            var result = segnaposto.NomeTag;
            Assert.Equal(expected, result);
        }

        [Fact]
        public void NomeArgomento_RestituisceId()
        {
            var expected = "id";
            var segnaposto = new SegnapostoSchedaDinamica(new StubGeneratoreHtmlSchede());
            var result = segnaposto.NomeArgomento;
            Assert.Equal(expected, result);
        }

        [Fact]
        public void Elabora_ConDomandaNull_SollevaEccezione()
        {
            var segnaposto = new SegnapostoSchedaDinamica(new StubGeneratoreHtmlSchede());
            Assert.Throws<ArgumentNullException>(() => segnaposto.Elabora(null, "", ""));
        }
    }
}
