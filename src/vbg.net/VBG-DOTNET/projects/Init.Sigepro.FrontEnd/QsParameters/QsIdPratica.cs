using System.Collections.Specialized;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsIdPratica : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "idpratica";

        public QsIdPratica(string value) :
            base(value)
        {
        }

        public QsIdPratica(NameValueCollection qs) :
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