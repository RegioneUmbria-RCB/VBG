namespace GeneratoreRiepiloghiHtml.AppLogic
{
    public static class TemplateLoader
    {
        private static class Constants
        {
            public const string PlaceHolder = "{{schedaDinamica}}";
        }

        public class TemplateInstance
        {
            public string Pre { get; set; } = "";
            public string Post { get; set; } = "";

            public string Wrap(string htmlContent)
            {
                return $"{this.Pre}{htmlContent}{this.Post}";
            }
        }

        public static TemplateInstance Load(string fileName)
        {
            var file = Path.Combine(AppContext.BaseDirectory, "TemplatesHtml", fileName);
            var fileContent = File.ReadAllText(file);
            var placeHolderIndex = fileContent.IndexOf(Constants.PlaceHolder, StringComparison.OrdinalIgnoreCase);
            if (placeHolderIndex < 0)
            {
                throw new Exception($"Il file {file} non contiene il segnaposto {Constants.PlaceHolder}");
            }

            var pre = fileContent.Substring(0, placeHolderIndex);
            var post = fileContent.Substring(placeHolderIndex + Constants.PlaceHolder.Length);

            return new TemplateInstance
            {
                Pre = pre,
                Post = post
            };
        }
    }
}
