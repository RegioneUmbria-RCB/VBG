using System.Collections.Specialized;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsReturning : BaseQuerystringParameter<string>
    {
        public const string QuerystringParameterName = "returning";

        public QsReturning(string value) :
            base(value)
        {
        }

        public QsReturning(NameValueCollection qs) :
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