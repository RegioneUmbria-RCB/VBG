using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.CoreServices.GestioneUrl
{
    public class UrlBuilder : IUrlBuilder
    {
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;

        public UrlBuilder(IAliasSoftwareResolver aliasSoftwareResolver)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
        }

        public string Build(params string?[] parts)
        {
            var baseList = new List<string>
            {
                this._aliasSoftwareResolver.AliasComune,
                this._aliasSoftwareResolver.Software,
            };

            baseList.AddRange(parts.Where(x => x != null).Select(x => x!));

            return $"{String.Join("/", baseList.ToArray())}";
        }

        public string Build(params object[] parts)
        {
            return this.Build(parts.Select(x => x?.ToString()).ToArray());
        }
    }
}
