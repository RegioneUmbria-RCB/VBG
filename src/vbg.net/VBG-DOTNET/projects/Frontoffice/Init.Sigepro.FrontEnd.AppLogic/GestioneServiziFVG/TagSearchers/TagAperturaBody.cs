using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.TagSearchers
{
    public class TagAperturaBody : TagSearcherBase
    {
        private static class Constants
        {
            public const string TagAperturaBody = "<body";
            public const string CarattereChiusuraBody = ">";
        }

        public TagAperturaBody(string template) : base(template)
        {
            var startIdx = template.IndexOf(Constants.TagAperturaBody, StringComparison.OrdinalIgnoreCase);
            this.Index = template.IndexOf(Constants.CarattereChiusuraBody, startIdx, StringComparison.OrdinalIgnoreCase);

            if (this.Index >= 0)
            {
                this.Index++;
            }
        }
    }
}
