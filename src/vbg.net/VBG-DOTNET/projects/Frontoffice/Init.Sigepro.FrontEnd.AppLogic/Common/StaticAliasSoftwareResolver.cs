namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    public class StaticAliasSoftwareResolver : IAliasSoftwareResolver
    {
        private readonly string _software;

        public StaticAliasSoftwareResolver(string alias, string software)
        {
            this.AliasComune = alias;
            this._software = software;
        }

        #region IAliasSoftwareResolver Members

        public string AliasComune { get; }

        public string Software
        {
            get { return this._software; }
        }

        #endregion
    }
}
