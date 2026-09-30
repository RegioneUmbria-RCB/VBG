namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.TagSearchers
{
    public class TagSearcherBase
    {
        protected int Index = -1;
        protected readonly string Template;

        protected TagSearcherBase(string template)
        {
            this.Template = template;
        }

        public string InserisciHtml(string html)
        {
            if (this.Index < 0)
            {
                return this.Template;
            }

            return this.Template.Insert(this.Index, html);
        }
    }
}
