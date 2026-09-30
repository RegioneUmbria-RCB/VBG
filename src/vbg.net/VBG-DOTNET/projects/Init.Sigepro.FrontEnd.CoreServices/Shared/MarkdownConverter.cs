using Init.Sigepro.FrontEnd.CoreServices.Markdown;
using VBG.DatiDinamici.Agid.Services;

namespace Init.Sigepro.FrontEnd.CoreServices.Shared
{
    public class MarkdownConverter : IMarkdownConverter
    {
        public string ToHtml(string markdownText, bool implicitParagraph = false)
        {
            return new MarkdownString(markdownText).ToHtml(implicitParagraph);
        }
    }
}
