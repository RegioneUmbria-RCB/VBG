using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths
{
    public class UrlBuilder
    {
        IUrlEncoder _urlEncoder;
        List<KeyValuePair<string, string>> _defaultParameters = new List<KeyValuePair<string, string>>();

        public static string Url(string url, Action<QuerystringArgumentsList> parametersBuilder = null)
        {
            return new UrlBuilder().Build(url, parametersBuilder);
        }

        public UrlBuilder()
            : this(new HttpContextUrlEncoder())
        {
        }

        public UrlBuilder(IUrlEncoder urlEncoder)
        {
            this._urlEncoder = urlEncoder;
        }

        public void AddDefaultParameter(IQuerystringParameter parameter)
        {
            this._defaultParameters.Add(new KeyValuePair<string, string>(parameter.ParameterName, parameter.ParameterStringValue));
        }

        public void AddDefaultParameter(string key, string value)
        {
            this._defaultParameters.Add(new KeyValuePair<string, string>(key, value));
        }

        public string Build(string url, Action<QuerystringArgumentsList> parametersBuilder = null)
        {
            var arguments = new QuerystringArgumentsList();

            this._defaultParameters.ForEach(x => arguments.Add(x.Key, x.Value));

            if (parametersBuilder != null)
            {
                parametersBuilder(arguments);
            }

            var qs = String.Join("&", arguments.AsEnumerable().Select(x => x.Key + "=" + this._urlEncoder.UrlEncode(x.Value)).ToArray());

            if (String.IsNullOrEmpty(qs))
            {
                return url;
            }

            var urlFmt = "{0}?{1}";

            if (url.IndexOf("?") != -1)
            {
                urlFmt = "{0}&{1}";
            }

            return String.Format(urlFmt, url, qs);
        }
    }
}
