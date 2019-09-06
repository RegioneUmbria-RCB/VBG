using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.IO;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsReturnTo : BaseQuerystringParameter<string>
    {
        public const string QuerystringParameterName = "returnto";

        public QsReturnTo(string value) :
            base(value.ToString())
        {
        }

        public QsReturnTo(NameValueCollection qs) :
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            // Inserire la logica di validazione. Es:
            // return value.Between(37, 37);

            var urlPart = value.IndexOf('?') != -1 ? value.Split('?')[0] : value; 
            return urlPart.IndexOf('~') == 0 && File.Exists(HttpContext.Current.Server.MapPath(urlPart)) && base.Validate(value);
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