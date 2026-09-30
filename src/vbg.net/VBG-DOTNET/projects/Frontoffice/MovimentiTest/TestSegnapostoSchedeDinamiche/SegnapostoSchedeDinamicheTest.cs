using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche.Utils;
using MovimentiTest.TestSegnapostoSchedeDinamiche.Utils;
using System;
using Xunit;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
    public class SegnapostoSchedeDinamicheTest
    {
        [Fact]
        public void NomeTag_RestituisceCampoDinamico()
        {
            var expected = "schedeDinamiche";

            var segnaposto = new SegnapostoSchedeDinamiche(new StubGeneratoreHtmlSchede(), new StubParametriGenerazioneRiepilogo(0));

            var result = segnaposto.NomeTag;

            Assert.Equal(expected, result);
        }

        [Fact]
        public void NomeArgomento_RestituisceId()
        {
            var expected = String.Empty;

            var segnaposto = new SegnapostoSchedeDinamiche(new StubGeneratoreHtmlSchede(), new StubParametriGenerazioneRiepilogo(0));

            var result = segnaposto.NomeArgomento;

            Assert.Equal(expected, result);
        }

        [Fact]
        public void Elabora_ConDomandaNull_SollevaEccezione()
        {
            var segnaposto = new SegnapostoSchedeDinamiche(new StubGeneratoreHtmlSchede(), new StubParametriGenerazioneRiepilogo(0));

            Assert.Throws<ArgumentNullException>(() => segnaposto.Elabora(null, "", ""));
        }

    }
}
