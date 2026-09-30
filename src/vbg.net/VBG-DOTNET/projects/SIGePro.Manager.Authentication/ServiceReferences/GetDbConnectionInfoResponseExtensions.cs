using Init.SIGePro.Manager.Authentication.WsSigeproSecurity;
using PersonalLib2.Data;
using System;
using System.Text;

namespace Init.SIGePro.Manager.Authentication.ServiceReferences
{
    public static class GetDbConnectionInfoResponseExtensions
    {
        private static string BuildConnectionString(this GetDbConnectionInfoResponse r)
        {
            var rVal = new StringBuilder(r.connectionString);

            if (!r.connectionString.EndsWith(";"))
            {
                rVal.Append(";");
            }

            rVal.Append("User Id=").Append(r.dbUser).Append(";Password=").Append(r.dbPassword);

            return rVal.ToString();
        }

        public static DataBase CreateDatabase(this GetDbConnectionInfoResponse r)
        {
            ProviderType providerType = (ProviderType)Enum.Parse(typeof(ProviderType), r.provider, true);

            return new DataBase(r.BuildConnectionString(), providerType);
        }
    }
}
