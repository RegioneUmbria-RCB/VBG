using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
	internal class V2DataSetSerializer : V2DatasetSerializerBase
	{
		protected override string GetVersionHeader()
		{
			return VersionInformationsHelper.DatiDomandaHeader.V2Header;
		}

	}
}
