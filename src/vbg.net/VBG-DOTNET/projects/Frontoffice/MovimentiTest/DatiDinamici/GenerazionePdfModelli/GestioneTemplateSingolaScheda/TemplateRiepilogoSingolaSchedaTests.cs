using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda;
using Xunit;

namespace MovimentiTest.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda
{
    public class TemplateRiepilogoSingolaSchedaTests
    {
        [Fact]
        public void Il_segnaposto_per_css_viene_sostituito()
        {
            var html = "<cssScheda/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("{0}", template.Html);
        }

        [Fact]
        public void Il_segnaposto_per_css_non_viene_sostituito_se_il_tag_inizia_con_spazio()
        {
            var html = "< cssScheda/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("< cssScheda/>", template.Html);
        }

        [Fact]
        public void Il_segnaposto_per_css_viene_sostituito_indipendentemente_dal_casing()
        {
            var html = "<CSSscheda/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("{0}", template.Html);
        }

        [Fact]
        public void Il_segnaposto_per_css_viene_sostituito_indipendentemente_dagli_spazi_finali()
        {
            var html = "<CSSscheda    />";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("{0}", template.Html);
        }

        [Fact]
        public void Il_segnaposto_per_la_scheda_viene_sostituito()
        {
            var html = "<schedaDinamica/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("{1}", template.Html);
        }

        [Fact]
        public void Il_segnaposto_per_la_scheda_non_viene_sostituito_se_il_tag_inizia_con_spazio()
        {
            var html = "< schedaDinamica/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("< schedaDinamica/>", template.Html);
        }

        [Fact]
        public void Il_segnaposto_per_la_scheda_viene_sostituito_indipendentemente_dal_casing()
        {
            var html = "<schEDaDinAMica/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("{1}", template.Html);
        }

        [Fact]
        public void Il_segnaposto_per_la_scheda_viene_sostituito_indipendentemente_dagli_spazi_finali()
        {
            var html = "<schEDaDinAMica    />";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("{1}", template.Html);
        }

        [Fact]
        public void I_segnaposto_vengono_sostituiti_anche_se_contenuti_all_interno_di_altro_testo()
        {
            var html = "test <cssScheda/> test <schedaDinamica/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            Assert.Equal("test {0} test {1}", template.Html);
        }

        [Fact]
        public void ApplicaAdHtmlScheda_sostituisce_i_segnaposto_con_html_e_css_passati()
        {
            var html = "test <cssScheda/> test <schedaDinamica/>";
            var template = new TemplateRiepilogoSingolaScheda(html);
            var result = template.ApplicaAdHtmlScheda("css", "html");
            Assert.Equal("test css test html", result);
        }

        [Fact]
        public void ApplicaAdHtmlScheda_su_template_default_sostituisce_i_segnaposto_con_html_e_css_passati()
        {
            var expected = TemplateRiepilogoSingolaScheda.Constants.DefaultHtmlTemplate
                                                                    .Replace("<cssScheda/>", "css")
                                                                    .Replace("<schedaDinamica/>", "html");
            var template = TemplateRiepilogoSingolaScheda.Default;
            var result = template.ApplicaAdHtmlScheda("css", "html");
            Assert.Equal(expected, result);
        }
    }
}
