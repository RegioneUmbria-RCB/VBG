using Xunit;
using VBG.Pagamenti.Legacy;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.MIP
{
    public class RiferimentiOperazioneTests
    {
        [Fact]
        public void Popola_correttamente_i_parametri_di_inizializzazione()
        {
            var num = "numero_operaionze";
            var importo = 12345;
            var r = new RiferimentiOperazione(num, importo);

            Assert.Equal(num, r.NumeroOperazione);
            Assert.Equal(importo.ToString(), r.Importo);
            Assert.Equal("EUR", r.Valuta);
        }
    }
}
