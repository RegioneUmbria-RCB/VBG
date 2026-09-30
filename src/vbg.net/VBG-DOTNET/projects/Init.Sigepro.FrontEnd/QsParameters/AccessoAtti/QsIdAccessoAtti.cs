using System.Collections.Specialized;

namespace Init.Sigepro.FrontEnd.QsParameters.AccessoAtti
{
    public class QsIdAccessoAtti : BaseQuerystringParameter<int>
    {
        public override string ParameterName => "id-accesso";

        public QsIdAccessoAtti(int value) :
            base(value.ToString())
        {
        }

        public QsIdAccessoAtti(NameValueCollection qs) :
            base(qs)
        {
        }
    }
}