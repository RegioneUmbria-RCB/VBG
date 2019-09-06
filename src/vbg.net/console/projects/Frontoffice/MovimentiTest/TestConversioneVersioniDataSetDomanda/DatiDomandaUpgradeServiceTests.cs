using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Xml;

namespace MovimentiTest.TestConversioneVersioniDataSetDomanda
{
	public class DatiDomandaUpgradeServiceTests
	{
		[TestClass]
		public class Upgrade
		{
			[TestMethod]
			public void DatoUnDatasetAllaVersioneV1_AggiornaAllaVersioneV2()
			{
				var v1Dataset = new PresentazioneIstanzaDataSet();

				var v1Serializer = new V1DataSetSerializer();

				var upgrSvc = new DatiDomandaUpgradeService();


				var result = upgrSvc.PerformUpgrade( v1Serializer.Serialize(v1Dataset) );

				var v2Serializer = new V2DataSetSerializer();

				var dbV2 = v2Serializer.Deserialize(result);
			}

			[TestMethod]
			public void DatoUnDatasetAllaVersioneV2_EffettuaUpgradeAUltimaVersione()
			{
				var v2Dataset = new PresentazioneIstanzaDbV2();

				var v2Serializer = new V2DataSetSerializer();

				var upgrSvc = new DatiDomandaUpgradeService();

				var result = upgrSvc.PerformUpgrade(v2Serializer.Serialize(v2Dataset));

				Assert.AreEqual(VersionInformationsHelper.CurrentVersion, VersionInformationsHelper.GetVersion(result));
			}

			[TestMethod]
			[ExpectedException(typeof(XmlException))]
			public void DatoUnArrayDiBytesNonValido_SollevaEccezione()
			{
				var randomArray = new byte[50];

				new Random().NextBytes(randomArray);

				var upgrSvc = new DatiDomandaUpgradeService();
				var result = upgrSvc.PerformUpgrade(randomArray);
			}
		}
	}
}
