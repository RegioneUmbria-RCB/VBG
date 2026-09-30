using System.Collections.Specialized;

namespace Init.Sigepro.FrontEnd.QsParameters
{

    //TODO: Cambiare ogni riferimento a IdPresentazione con IdDomandaOnLine
    public class QsIdDomandaOnline : BaseQuerystringParameter<int>
    {
        public const string QuerystringParameterName = "IdPresentazione";

        public QsIdDomandaOnline(int value) :
            base(value.ToString())
        {
        }

        public QsIdDomandaOnline(string value) :
            base(value)
        {
        }

        public QsIdDomandaOnline(NameValueCollection qs) :
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