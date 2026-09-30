using System.Collections.Specialized;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsUuidLocalizzazione : BaseQuerystringParameter<string>
    {
        public const string QuerystringParameterName = "uuid-localizzazione";

        public QsUuidLocalizzazione(string value) :
            base(value)
        {
        }

        public QsUuidLocalizzazione(NameValueCollection qs) :
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
}