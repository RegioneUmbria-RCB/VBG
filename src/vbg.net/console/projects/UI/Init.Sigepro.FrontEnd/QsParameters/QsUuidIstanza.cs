using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsUuidIstanza : BaseQuerystringParameter<string>
    {
        public const string QuerystringParameterName = "uuid-pratica";

        public QsUuidIstanza(string value) :
            base(value.ToString())
        {
        }

        public QsUuidIstanza(NameValueCollection qs) :
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            // Inserire la logica di validazione. Es:
            //return value.MaxLength(12) oppure value.Between(37, 37);

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