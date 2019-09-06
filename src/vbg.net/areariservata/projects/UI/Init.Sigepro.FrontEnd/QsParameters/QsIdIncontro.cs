using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{

    public class QsIdIncontro : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "incontro";

        protected QsIdIncontro(string value) :
            base(value)
        {
        }

        public QsIdIncontro(NameValueCollection qs) :
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
    public class QsIdIncontro: BaseQuerystringParameter
    {
        public const string QuerystringParameterName = "incontro";

        public static QsIdIncontro FromQuerystring(NameValueCollection qs)
        {
            return new QsIdIncontro(qs[QuerystringParameterName]);
        }

        public static QsIdIncontro FromString(string val)
        {
            return new QsIdIncontro(val);
        }

        public readonly int Value;

        protected QsIdIncontro(string value)
        {
            this.Value = Convert.ToInt32(value);
        }
    }
    */
}