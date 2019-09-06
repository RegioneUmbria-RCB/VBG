using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
	internal enum DatasetVersionEnum
	{
		V1,
		V2,
		V3,
		V4,
        V5
	}



	internal static class VersionInformationsHelper
	{
		internal static class DatiDomandaHeader
		{
			public const string V2Header = "FoDataSetV2 ";
			public const string V3Header = "FoDataSetV3 ";
			public const string V4Header = "FoDataSetV4 ";
            public const string V5Header = "FoDataSetV5 ";
		}

		public static DatasetVersionEnum CurrentVersion { get { return DatasetVersionEnum.V5; } }
		private static int HeaderLength { get { return 12; } }

		public static DatasetVersionEnum GetVersion(byte[] data)
		{
			if (data.Length < HeaderLength)		// Potrebbe essere una versione V1 anche se è strano che la lunghezza sia minore dell'intestazione xml
				return DatasetVersionEnum.V1;

			var header = new byte[HeaderLength];

			Array.Copy( data, header, HeaderLength );

			if (Encoding.Default.GetString(header) == DatiDomandaHeader.V2Header)
				return DatasetVersionEnum.V2;

			if (Encoding.Default.GetString(header) == DatiDomandaHeader.V3Header)
				return DatasetVersionEnum.V3;

			if (Encoding.Default.GetString(header) == DatiDomandaHeader.V4Header)
				return DatasetVersionEnum.V4;

            if (Encoding.Default.GetString(header) == DatiDomandaHeader.V5Header)
                return DatasetVersionEnum.V5;

			return DatasetVersionEnum.V1; 
		}

	}

	internal class UpgradePathsHelper
	{
		Dictionary<DatasetVersionEnum, IDatiDomandaUpgrader> _upgradePaths = new Dictionary<DatasetVersionEnum, IDatiDomandaUpgrader>();

		public UpgradePathsHelper()
		{
			_upgradePaths.Add(DatasetVersionEnum.V1, new V1ToV2Upgrader());
			_upgradePaths.Add(DatasetVersionEnum.V2, new V2ToV3Upgrader());
			_upgradePaths.Add(DatasetVersionEnum.V3, new V3ToV4Upgrader());
            _upgradePaths.Add(DatasetVersionEnum.V4, new V4ToV5Upgrader());
		}

		public bool UpgradePathExists(DatasetVersionEnum version)
		{
			return _upgradePaths.ContainsKey(version);
		}

		public byte[] Upgrade(DatasetVersionEnum originalVersion, byte[] originalData)
		{
			IDatiDomandaUpgrader upgrader = null;

			if (!_upgradePaths.TryGetValue(originalVersion, out upgrader))
				upgrader = new NullUpgrader();

			return upgrader.Ugrade(originalData);
		}
	}
}
