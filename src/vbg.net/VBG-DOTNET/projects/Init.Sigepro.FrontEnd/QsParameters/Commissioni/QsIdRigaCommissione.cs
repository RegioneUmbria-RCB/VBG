using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Commissioni
{
    public class QsIdRigaCommissione : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "riga";

        public QsIdRigaCommissione(string value) :
            base(value)
        {
        }

        public QsIdRigaCommissione(NameValueCollection qs) :
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