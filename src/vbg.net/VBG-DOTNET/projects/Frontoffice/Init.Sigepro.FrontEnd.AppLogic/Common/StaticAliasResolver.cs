namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    public class StaticAliasResolver : IAliasResolver
    {
        private readonly string _alias;

        public StaticAliasResolver(string alias)
        {
            _alias = alias;
        }

        public string AliasComune => this._alias;
    }
}
