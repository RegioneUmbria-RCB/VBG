using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
	public class DatiDomandaUpgradeService
	{
		UpgradePathsHelper _upgradePaths = new UpgradePathsHelper();

		public byte[] PerformUpgrade(byte[] data)
		{
			var datasetVersion = VersionInformationsHelper.GetVersion(data);

			while (_upgradePaths.UpgradePathExists(datasetVersion))
			{
				data = _upgradePaths.Upgrade(datasetVersion, data);
				datasetVersion = VersionInformationsHelper.GetVersion(data);
			}

			return data;
		}

	}
}
