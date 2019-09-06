using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsNumeroPratica : BaseQuerystringParameter<string>
    {
        private static class Constants
        {
            public const int MinLength = 1;
            public const int MaxLength = 60;
            public const string Regex = "[A-Z/]+";
        }

        public const string QuerystringParameterName = "numeroistanza";

        public QsNumeroPratica(string value) :
            base(value)
        {
        }

        public QsNumeroPratica(NameValueCollection qs) :
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            return value.Between(Constants.MinLength, Constants.MaxLength);
        }

        // Restituisce il nome del parametro in querystring
        public override string ParameterName
        {
            get
            {
                return QuerystringParameterName;
            }
        }
    }


}