using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.Mocks
{
    public class MockRiferimentiDomanda : IRiferimentiDomandaPerPagamenti
    {
        public string IdComune => "IDCOMUNE";

        public string Software => "SOFTWARE";

        public int IdPresentazione => 123;

        public string CodiceUnivocoDomanda => "ASDASDASD";
    }
}
