using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.TagSearchers
{
    public class TagChiusuraHead : TagSearcherBase
    {
        private static class Constants
        {
            public const string TagChiusuraHead = "</head>";
        }

        public TagChiusuraHead(string template) : base(template)
        {
            this.Index = template.IndexOf(Constants.TagChiusuraHead, StringComparison.OrdinalIgnoreCase);
        }
    }
}
