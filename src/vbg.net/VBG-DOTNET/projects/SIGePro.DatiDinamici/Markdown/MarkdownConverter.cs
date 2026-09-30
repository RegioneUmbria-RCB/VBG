namespace Init.SIGePro.DatiDinamici.Markdown
{
    public static class MarkdownConverter
    {
        public static string ToHtml(string markdownText, bool implicitParagraph = false)
        {
            return new MarkdownString(markdownText).ToHtml(implicitParagraph);
        }
    }
}
