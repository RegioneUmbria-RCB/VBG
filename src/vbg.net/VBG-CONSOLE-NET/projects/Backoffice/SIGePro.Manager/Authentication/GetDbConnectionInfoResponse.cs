using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.WsSigeproSecurity
{
    public partial class GetDbConnectionInfoResponse
    {
        public string BuildConnectionString()
        {
            string rVal = this.connectionString;

            if (rVal.EndsWith(";"))
                rVal += "User Id=" + this.dbUser + ";Password=" + this.dbPassword;
            else
                rVal += ";User Id=" + this.dbUser + ";Password=" + this.dbPassword;

            return rVal;
        }

        public DataBase CreateDatabase()
        {
            ProviderType providerType = (ProviderType)Enum.Parse(typeof(ProviderType), this.provider, true);

            return new DataBase(BuildConnectionString(), providerType);
        }
    }
}
