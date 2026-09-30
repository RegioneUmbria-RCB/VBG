using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli.GestioneTemplateSingolaScheda.Configurazione
{
    public class ParametriRiepilogoSingolaScheda : IParametriConfigurazione
    {
        public readonly TemplateRiepilogoSingolaScheda TemplateRiepilogoSchedaMovimento;


        public ParametriRiepilogoSingolaScheda(BinaryFile? templateHtml)
        {
            if (templateHtml == null)
            {
                this.TemplateRiepilogoSchedaMovimento = TemplateRiepilogoSingolaScheda.Default;
            }
            else
            {
                var html = Encoding.UTF8.GetString(templateHtml.FileContent);
                this.TemplateRiepilogoSchedaMovimento = new TemplateRiepilogoSingolaScheda(html);
            }
        }
    }

}
