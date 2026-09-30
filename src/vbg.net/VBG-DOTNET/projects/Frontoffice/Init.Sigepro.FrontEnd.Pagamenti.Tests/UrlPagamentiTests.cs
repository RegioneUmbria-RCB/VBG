using Init.Sigepro.FrontEnd.Pagamenti.Tests.Mocks;
using Xunit;
using VBG.Pagamenti.Legacy;
using VBG.Pagamenti.NodoPagamenti.Shared;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests
{
    public class UrlPagamentiTests
    {
        private const int StepId = 0;

        [Fact]
        public void Genera_un_url_accodando_le_informazioni_della_domanda()
        {
            var riferimentiDomanda = new RiferimentiDomanda(new MockRiferimentiDomanda(), 0);
            var url = new UrlPagamenti("~/test.url?par=val", riferimentiDomanda, new MockUrlEncoder(), new MockResolveUrl());

            var parsedUrl = url.ToString();

            Assert.NotEqual(-1, parsedUrl.IndexOf($"&idComune={riferimentiDomanda.IdComune}"));
            Assert.NotEqual(-1, parsedUrl.IndexOf($"&software={riferimentiDomanda.Software}"));
            Assert.NotEqual(-1, parsedUrl.IndexOf($"&idPresentazione={riferimentiDomanda.IdDomanda}"));
            Assert.NotEqual(-1, parsedUrl.IndexOf($"&stepId={StepId}"));
        }

        [Fact]
        public void Genera_un_url_sostituendo_ai_segnaposto_le_informazioni_della_domanda()
        {
            var riferimentiDomanda = new RiferimentiDomanda(new MockRiferimentiDomanda(), 0);
            var url = new UrlPagamenti("/{idComune}/{software}/{idPresentazione}/{stepId}", riferimentiDomanda, new MockUrlEncoder(), new MockResolveUrl());
            string expected = $"/{riferimentiDomanda.IdComune}/{riferimentiDomanda.Software}/{riferimentiDomanda.IdDomanda}/{StepId}";
            var parsedUrl = url.ToString();

            Assert.Equal(expected, parsedUrl.Substring(0, expected.Length));
        }
    }
}
