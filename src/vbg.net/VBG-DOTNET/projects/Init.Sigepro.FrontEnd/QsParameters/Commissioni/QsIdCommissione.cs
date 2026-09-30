using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Commissioni
{
    public class QsIdCommissione : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "commissione";

        public QsIdCommissione(string value) :
            base(value)
        {
        }

        public QsIdCommissione(NameValueCollection qs) :
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            return base.Validate(value);
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