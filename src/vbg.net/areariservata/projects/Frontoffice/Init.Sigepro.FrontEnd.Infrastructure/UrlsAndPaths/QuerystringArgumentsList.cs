using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public class QuerystringArgumentsList
    {
        List<KeyValuePair<string, string>> _list = new List<KeyValuePair<string, string>>();

        public void Add(IQuerystringParameter parameter)
        {
            this.Add(parameter.ParameterName, parameter.ParameterStringValue);
        }

        public void Add(string key, object value)
        {
            if (value == null)
            {
                return;
            }
            this._list.Add(new KeyValuePair<string, string>(key, value.ToString()));
        }

        public IEnumerable<KeyValuePair<string, string>> AsEnumerable()
        {
            return this._list;
        }
    }
}
