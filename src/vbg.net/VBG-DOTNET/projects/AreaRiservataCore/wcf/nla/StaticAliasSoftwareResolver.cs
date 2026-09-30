using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.WebServices.Nla
{
    internal class StaticAliasSoftwareResolver : IAliasSoftwareResolver
    {
        private readonly string _idcomune;
        private readonly string _software;

        public StaticAliasSoftwareResolver(string idcomune, string software)
        {
            this._idcomune = idcomune;
            this._software = software;
        }

        public string AliasComune => this._idcomune;

        public string Software => this._software;
    }
}