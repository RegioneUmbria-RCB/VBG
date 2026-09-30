using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Pagamenti
{
    public class QsUidOnere : BaseQuerystringParameter<string>
    {
        public override string ParameterName => "onere";

        protected QsUidOnere(string value) :
            base(value)
        {
        }

        public QsUidOnere(NameValueCollection qs) :
            base(qs)
        {
        }
    }
}