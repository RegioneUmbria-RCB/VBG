using System.Collections.Specialized;

namespace Init.Sigepro.FrontEnd.QsParameters
{
    public class QsUrlScrivaniaVirtuale : BaseQuerystringParameter<string>
    {
        public QsUrlScrivaniaVirtuale(string value) : base(value)
        {
        }

        public QsUrlScrivaniaVirtuale(NameValueCollection qs) : base(qs)
        {
        }

        public override string ParameterName => "url-scrivania-virtuale";
    }
}