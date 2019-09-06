using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsIdDomandaOnline : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "IdPresentazione";

        public QsIdDomandaOnline(int value) :
            base(value.ToString())
        {
        }

        public QsIdDomandaOnline(string value) :
            base(value)
        {
        }

        public QsIdDomandaOnline(NameValueCollection qs) :
            base(qs)
        {
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
    public class QsIdDomandaOnline : BaseQuerystringParameter
    {
        public const string QuerystringParameterName = "IdPresentazione";

        public static QsIdDomandaOnline FromQuerystring(NameValueCollection qs)
        {
            return new QsIdDomandaOnline(qs[QuerystringParameterName]);
        }

        public static QsIdDomandaOnline FromString(string val)
        {
            return new QsIdDomandaOnline(val);
        }

        public readonly int Value;

        protected QsIdDomandaOnline(string value)
        {
            this.Value = Convert.ToInt32(value);
        }
    }
     */
}