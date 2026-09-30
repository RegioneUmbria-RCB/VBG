using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Formule
{
    internal class FormuleAuthenticationInfo : IFormuleAuthenticationInfo
    {
        private readonly IDbConnectionInfo _dbInfo;

        public string Token { get; }
        public string Alias { get; }
        public string IdComune { get; }

        public FormuleAuthenticationInfo(string token, string alias, string idcomune, IDbConnectionInfo dbInfo)
        {
            this.Token = token;
            this.Alias = alias;
            this.IdComune = idcomune;
            this._dbInfo = dbInfo;
        }

        public IDatabase CreateDatabase()
        {
            return this._dbInfo.CreateDatabase(this.Token);
        }
    }
}