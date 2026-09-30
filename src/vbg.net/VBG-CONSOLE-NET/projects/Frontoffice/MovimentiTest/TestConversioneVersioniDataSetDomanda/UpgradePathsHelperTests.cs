using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Data;

namespace MovimentiTest.TestConversioneVersioniDataSetDomanda
{
	public class UpgradePathsHelperTests
	{
		[TestClass]
		public class UpgradePathExists
		{
			[TestMethod]
			public void VerificaEsistenzaUpgradeDaVersioneV1_RestituisceTrue()
			{
				var helper = new UpgradePathsHelper();

				var result = helper.UpgradePathExists(DatasetVersionEnum.V1);

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void VerificaEsistenzaUpgradeDaVersioneV2_RestituisceTrue()
			{
				var helper = new UpgradePathsHelper();

				var result = helper.UpgradePathExists(DatasetVersionEnum.V2);

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void VerificaEsistenzaUpgradeDaVersioneV3_RestituisceTrue()
			{
				var helper = new UpgradePathsHelper();

				var result = helper.UpgradePathExists(DatasetVersionEnum.V3);

				Assert.IsTrue(result);
			}

			[TestMethod]
			public void VerificaEsistenzaUpgradeDaVersioneV4_RestituisceTrue()
			{
				var helper = new UpgradePathsHelper();

				var result = helper.UpgradePathExists(DatasetVersionEnum.V4);

				Assert.IsTrue(result);
			}

            [TestMethod]
            public void VerificaEsistenzaUpgradeDaVersioneV5_RestituisceFalse()
            {
                var helper = new UpgradePathsHelper();

                var result = helper.UpgradePathExists(DatasetVersionEnum.V5);

                Assert.IsFalse(result);
            }
		}

		[TestClass]
		public class Upgrade
		{
			[TestMethod]
			public void DatoUnDatasetNelFormatoV1_EffettuaUpgradeAV2()
			{
				var v1Dataset = new PresentazioneIstanzaDataSet();

				var v1Serializer = new V1DataSetSerializer();

				var dsSerializzato = v1Serializer.Serialize(v1Dataset);

				var helper = new UpgradePathsHelper();

				var result = helper.Upgrade(DatasetVersionEnum.V1, dsSerializzato);

				var v2Serializer = new V2DataSetSerializer();

				var dbV2 = v2Serializer.Deserialize(result);
			}

			[TestMethod]
			public void DatoUnDatasetNelFormatoV2_NonEffettuaUpgrade()
			{
				var v2Dataset = new PresentazioneIstanzaDbV2();

				var v2Serializer = new V2DataSetSerializer();

				var dsSerializzato = v2Serializer.Serialize(v2Dataset);

				var helper = new UpgradePathsHelper();

				var result = helper.Upgrade(DatasetVersionEnum.V2, dsSerializzato);

				var dbV2 = v2Serializer.Deserialize(result);
			}

		}
	}
}
