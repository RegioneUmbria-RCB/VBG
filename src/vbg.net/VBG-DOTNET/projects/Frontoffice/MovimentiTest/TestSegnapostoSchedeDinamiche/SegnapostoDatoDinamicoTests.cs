using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xunit;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
    public class SegnapostoDatoDinamicoTests
    {
        [Fact]
        public void NomeTag_RestituisceCampoDinamico()
        {
            var expected = "campoDinamico";
            var segnaposto = new SegnapostoDatoDinamico();
            var result = segnaposto.NomeTag;
            Assert.Equal(expected, result);
        }

        [Fact]
        public void NomeArgomento_RestituisceId()
        {
            var expected = "id";
            var segnaposto = new SegnapostoDatoDinamico();
            var result = segnaposto.NomeArgomento;
            Assert.Equal(expected, result);
        }

        [Fact]
        public void Elabora_ConDomandaNull_SollevaEccezione()
        {
            var segnaposto = new SegnapostoDatoDinamico();
            Assert.Throws<ArgumentNullException>(() => segnaposto.Elabora(null, "", ""));
        }
    }
}
