using System.Collections.Generic;

namespace VBG.Backend.SIT.AppLogic.Jesi
{
    public class RequestAdapter
    {
        public RequestAdapter()
        {
        }

        public RequestJSON<T> Adatta<T>(AliasEnum alias, T parametri)
        {
            return new RequestJSON<T>
            {
                Alias = alias.ToString(),
                Parametri = new List<T> { parametri }
            };
        }
    }
}
