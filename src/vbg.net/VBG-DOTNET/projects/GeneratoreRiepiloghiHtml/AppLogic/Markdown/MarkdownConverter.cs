using VBG.DatiDinamici.Agid.Services;

namespace GeneratoreRiepiloghiHtml.AppLogic.Markdown
{

    public class MarkdownConverter : IMarkdownConverter
    {
        public string ToHtml(string markdownText, bool implicitParagraph = false)
        {
            return new MarkdownString(markdownText).ToHtml(implicitParagraph);
        }
    }
}
