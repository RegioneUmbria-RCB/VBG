using static GeneratoreRiepiloghiHtml.AppLogic.TemplateLoader;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede
{
    public static class SingolaSchedaHtmlWrapper
    {
        private static readonly TemplateInstance Wrapper = TemplateLoader.Load("singola-scheda.html");

        public static string Wrap(string htmlSchede)
        {
            return Wrapper.Wrap(htmlSchede);
        }
    }
}
