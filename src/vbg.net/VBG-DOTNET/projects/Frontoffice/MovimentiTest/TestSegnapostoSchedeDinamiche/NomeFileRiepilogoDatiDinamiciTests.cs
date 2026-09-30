using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
using Xunit;

namespace MovimentiTest.TestSegnapostoSchedeDinamiche
{
    public class NomeFileRiepilogoDatiDinamiciTests
    {
        [Fact]
        public void Se_il_nome_file_contiene_caratteri_non_validi_i_questi_vengono_sostituiti_con_un_underscore()
        {
            var nomeFile = "\\";
            var expected = "_.pdf";

            var result = new NomeFileRiepilogoModello(nomeFile, -1).ToString();

            Assert.Equal(expected, result);

            nomeFile = ":";
            expected = "_.pdf";

            result = new NomeFileRiepilogoModello(nomeFile, -1).ToString();

            Assert.Equal(expected, result);
        }
    }
}
