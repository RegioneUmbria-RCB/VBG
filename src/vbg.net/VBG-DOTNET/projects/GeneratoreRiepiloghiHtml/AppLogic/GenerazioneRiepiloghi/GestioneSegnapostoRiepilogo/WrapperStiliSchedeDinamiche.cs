using static GeneratoreRiepiloghiHtml.AppLogic.TemplateLoader;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo
{
    public static class WrapperStiliSchedeDinamiche
    {
        private static readonly TemplateInstance Wrapper = TemplateLoader.Load("template-schede-dinamiche-css.html");
        //private static readonly TemplateInstance Wrapper = TemplateLoader.Load("template-css-vuoto.html");

        public static string Wrap(string part)
        {
            return Wrapper.Wrap(part);
        }
    }
}
