using PersonalLib2.Data;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    public class DbConnectionInfo : IDbConnectionInfo
    {
        public string ConnectionString { get; internal set; } = "";
        public string Username { get; internal set; } = "";
        public string Password { get; internal set; } = "";
        public string ProviderName { get; internal set; } = "";

        public string IdComune { get; internal set; } = "";

        public string Alias { get; internal set; } = "";

        public IDatabase CreateDatabase(string token)
        {
            var initialProviderType = (ProviderType)Enum.Parse(typeof(ProviderType), this.ProviderName, true);

            var connectionString = this.ConnectionString;

            if (!connectionString.EndsWith(";"))
                connectionString += ";";

            connectionString += $"User Id={this.Username};Password={this.Password}";

            var rVal = new DataBase(connectionString, initialProviderType);
            rVal.ConnectionDetails.Token = token;

            return rVal;
        }
    }
}
