using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public class QuerystringArgumentsList : IQuerystringArgumentsList
    {
        List<KeyValuePair<string, string>> _list = new List<KeyValuePair<string, string>>();

        public IQuerystringArgumentsList Add(IQuerystringParameter parameter)
        {
            this.Add(parameter.ParameterName, parameter.ParameterStringValue);

            return this;
        }

        public IQuerystringArgumentsList Add(string key, object value)
        {
            if (value != null)
            {
                this._list.Add(new KeyValuePair<string, string>(key, value.ToString()));
            }

            return this;
        }

        public IEnumerable<KeyValuePair<string, string>> AsEnumerable()
        {
            return this._list;
        }
    }
}
