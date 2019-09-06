using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsAliasComune : BaseQuerystringParameter<string>
    {
        private static class Constants
        {
            public const int MinLength = 2;
            public const int MaxLength = 10;
            public const string Regex = "[A-Z]+";
        }

        public const string QuerystringParameterName = "idcomune";

        public QsAliasComune(string value) :
            base(value)
        {
        }

        public QsAliasComune(NameValueCollection qs) :
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            return value.Between(Constants.MinLength, Constants.MaxLength) && value.Matches(Constants.Regex);
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


    /*
    public class QsAliasComune : BaseQuerystringParameter
    {



        public const string QuerystringParameterName = "idcomune";

        public static QsAliasComune FromQuerystring(NameValueCollection qs)
        {
            return new QsAliasComune(qs[QuerystringParameterName]);
        }

        public static QsAliasComune FromString(string value)
        {
            return new QsAliasComune(value);
        }

        public readonly string Value;

        protected QsAliasComune(string value)
        {
            if (!value.Between(Constants.MinLength, Constants.MaxLength) || !value.Matches(Constants.Regex))
            {
                throw new Exception("Alias comune non valido");
            }

            this.Value = value;
        }
    }
    */
}