using Markdig;
using Markdig.Renderers;
using Markdig.Renderers.Html;
using Markdig.Syntax;
using Markdig.Syntax.Inlines;

namespace GeneratoreRiepiloghiHtml.AppLogic.Markdown
{
    public class MarkdownString
    {
        private readonly string _markdown;
        private string _html = "";

        public MarkdownString(string markdown)
        {
            this._markdown = markdown?.Trim() ?? "";
        }

        public string Html => this.ToHtml();

        public string ToHtml(bool implicitParagraph = false)
        {
            if (!String.IsNullOrEmpty(this._html))
            {
                return this._html;
            }

            if (String.IsNullOrEmpty(this._markdown))
            {
                return string.Empty;
            }

            var pipeline = new MarkdownPipelineBuilder().UseAdvancedExtensions().Build();
            var document = Markdig.Markdown.Parse(this._markdown, pipeline);
            foreach (var descendant in document.Descendants())
            {
                string url;
                if (descendant is AutolinkInline autoLink) url = autoLink.Url;
                else if (descendant is LinkInline linkInline) url = linkInline.Url;
                else continue;
                if (url != null && Uri.TryCreate(url, UriKind.Absolute, out _))
                    descendant.GetAttributes().AddPropertyIfNotExist("target", "_blank");
            }

            using var writer = new StringWriter();
            var renderer = new HtmlRenderer(writer);
            renderer.ImplicitParagraph = implicitParagraph;
            pipeline.Setup(renderer);
            renderer.Render(document);

            this._html = writer.ToString();

            return this._html;
        }
    }
}
