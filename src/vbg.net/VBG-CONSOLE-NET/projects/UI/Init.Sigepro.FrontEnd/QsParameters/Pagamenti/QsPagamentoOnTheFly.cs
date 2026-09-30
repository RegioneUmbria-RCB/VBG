using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Web;

namespace Init.Sigepro.FrontEnd.QsParameters.Pagamenti
{
    public class QsPagamentoOnTheFly : BaseQuerystringParameter<string>
    {
        public static class Constants
        {
            public const string ValoreTrue = "1";
            public const string ValoreFalse = "0";
        }

        public static QsPagamentoOnTheFly UsaPagamentoOTF => new QsPagamentoOnTheFly(Constants.ValoreTrue);

        public override string ParameterName => "PagamentoOTF";

        protected QsPagamentoOnTheFly(string value) :
            base(value)
        {
        }

        public QsPagamentoOnTheFly(NameValueCollection qs) :
            base(qs)
        {
        }

        public bool UtilizzaPagamentoOTF => this.ParameterStringValue == Constants.ValoreTrue;
    }
}