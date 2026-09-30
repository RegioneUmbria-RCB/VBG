using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public interface IPagamentiEntraNextSettingsReader
    {
        PaymentSettingsEntraNext GetSettings();
    }
}
