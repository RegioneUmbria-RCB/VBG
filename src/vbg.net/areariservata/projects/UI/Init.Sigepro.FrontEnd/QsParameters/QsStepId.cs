using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsStepId : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "stepid";

        public QsStepId(int value) :
            this(value.ToString())
        {
        }

        public QsStepId(string value) :
            base(value)
        {
        }

        public QsStepId(NameValueCollection qs) :
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            return value.Between(1, 2);
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