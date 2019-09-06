using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsSoftware : BaseQuerystringParameter<string>
    {
        private static class Constants
        {
            public const int MinLength = 2;
            public const int MaxLength = 2;
            public const string Regex = "[A-Z]+";
        }


        public const string QuerystringParameterName = "software";
        
        public QsSoftware(string value):
            base(value)
        {
        }

        public QsSoftware(NameValueCollection qs):
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            return value.Between(Constants.MinLength, Constants.MaxLength) && value.Matches(Constants.Regex);
        }

        public override string ParameterName
        {
            get
            {
                return QuerystringParameterName;
            }
        }
    }
}