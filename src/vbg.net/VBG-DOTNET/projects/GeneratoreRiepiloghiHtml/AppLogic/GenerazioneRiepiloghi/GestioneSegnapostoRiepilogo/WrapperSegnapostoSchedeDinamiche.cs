using static GeneratoreRiepiloghiHtml.AppLogic.TemplateLoader;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo
{
    public static class WrapperSegnapostoSchedeDinamiche
    {
        private static readonly TemplateInstance Wrapper = TemplateLoader.Load("template-schede-dinamiche.html");
        //private static readonly TemplateInstance Wrapper = TemplateLoader.Load("template-css-vuoto.html");

        public static string Wrap(string htmlSchede)
        {
            return Wrapper.Wrap(htmlSchede);
        }
    }
}
