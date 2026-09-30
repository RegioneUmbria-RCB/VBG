using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
    internal class V5DataSetSerializer : V2DatasetSerializerBase
    {
        protected override string GetVersionHeader()
        {
            return VersionInformationsHelper.DatiDomandaHeader.V5Header;
        }
    }
}
