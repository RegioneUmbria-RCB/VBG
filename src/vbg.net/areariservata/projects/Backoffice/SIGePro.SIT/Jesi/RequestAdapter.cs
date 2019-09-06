using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.Jesi
{
    public class RequestAdapter
    {
        public RequestAdapter()
        {

        }

        public RequestJSON Adatta(AliasEnum alias, string password, Dictionary<string, string> parametri)
        {
            return new RequestJSON
            {
                Alias = alias.ToString(),
                Pwd = password,
                Parametri = parametri
            };
        }
    }
}
