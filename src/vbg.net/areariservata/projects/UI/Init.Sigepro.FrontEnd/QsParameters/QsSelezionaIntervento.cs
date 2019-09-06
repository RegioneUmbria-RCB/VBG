using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsSelezionaIntervento : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "SelezionaIntervento";

        public QsSelezionaIntervento(int value) :
            this(value.ToString())
        {
        }

        public QsSelezionaIntervento(string value) :
            base(value)
        {
        }

        public QsSelezionaIntervento(NameValueCollection qs) :
            base(qs)
        {
        }

        protected override bool Validate(string value)
        {
            // Inserire la logica di validazione
            // if (!value.Between(37, 37))
            // {
            //     throw new ArgumentException("Parametro non valido");
            // }
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