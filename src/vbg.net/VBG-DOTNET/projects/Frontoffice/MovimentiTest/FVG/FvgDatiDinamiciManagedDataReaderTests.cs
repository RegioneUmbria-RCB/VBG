using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.ManagedData;
using Xunit;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogicTests.FVG
{
    public class FvgDatiDinamiciManagedDataReaderTests
    {
        #region Xml di test
        string TEST_XML = @"
<managedData xmlns=""http://www.insiel.it/gestioneDocumentale/FEG/common/managedData"">
	<datoAcquisitoPerModulo>
		<idProcedimento>143542</idProcedimento>
		<datiModulo>
			<datoStrutturato>
				<fvgDataSet xmlns="""" xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"">
					<campoDinamico>
						<id>2</id>
						<nome>00.1.1.NUMERO DELLA DOMANDA</nome>
						<indiceMolteplicita>0</indiceMolteplicita>
						<valore>801</valore>
						<valoreDecodificato>801</valoreDecodificato>
					</campoDinamico>
					<campoDinamico>
						<id>3</id>
						<nome>00.1.2.TITOLARECOGNOME</nome>
						<indiceMolteplicita>0</indiceMolteplicita>
						<valore>CARANO</valore>
						<valoreDecodificato>CARANO</valoreDecodificato>
					</campoDinamico>
					<campoDinamico>
						<id>86</id>
						<nome>DATIDICHIARANTE-DATANASCITA</nome>
						<indiceMolteplicita>0</indiceMolteplicita>
						<valore>19660118</valore>
						<valoreDecodificato>18/01/1966</valoreDecodificato>
					</campoDinamico>
					<campoDinamico>
						<id>86</id>
						<nome>NON-DEVE-COMPARIRE</nome>
						<indiceMolteplicita>0</indiceMolteplicita>
						<valore/>
						<valoreDecodificato></valoreDecodificato>
						</campoDinamico>
					</fvgDataSet>
				</datoStrutturato>
			</datiModulo>
		</datoAcquisitoPerModulo>

		<datoAcquisitoPerModulo>
			<idProcedimento>143542</idProcedimento>
			<datiModulo>
				<datoStrutturato>
					<fvgDataSet xmlns="""" xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"">
						<campoDinamico>
							<id>2</id>
							<nome>00.1.1.NUMERO DELLA DOMANDA</nome>
							<indiceMolteplicita>0</indiceMolteplicita>
							<valore>IL VALORE NON COMPARE</valore>
							<valoreDecodificato>IL VALORE NON COMPARE</valoreDecodificato>
						</campoDinamico>
						<campoDinamico>
							<id>3</id>
							<nome>00.1.2.TITOLARECOGNOME</nome>
							<indiceMolteplicita>1</indiceMolteplicita>
							<valore>TEST_MOLTEPLICITA</valore>
							<valoreDecodificato>TEST_MOLTEPLICITA</valoreDecodificato>
						</campoDinamico>
						<campoDinamico>
							<id>86</id>
							<nome>DATIDICHIARANTE-DATANASCITA</nome>
							<indiceMolteplicita>0</indiceMolteplicita>
							<valore>IL VALORE NON COMPARE</valore>
							<valoreDecodificato>IL VALORE NON COMPARE</valoreDecodificato>
						</campoDinamico>
					</fvgDataSet>
				</datoStrutturato>
			</datiModulo>
		</datoAcquisitoPerModulo>
	</managedData>";

        #endregion

        [Fact]
        public void Testa_il_caricamento_da_un_managed_data()
        {
            var mock = new Mock<IFVGWebServiceProxy>();
            mock.Setup(x => x.GetManagedDataDaCodiceIstanza(123)).Returns(() =>
            {
                var document = new XmlDocument();

                document.LoadXml(TEST_XML);

                return document;
            });

            var reader = new FvgDatiDinamiciManagedDataReader(new[] {
                "00.1.1.NUMERO DELLA DOMANDA",
                "00.1.2.TITOLARECOGNOME",
                "DATIDICHIARANTE-DATANASCITA",
                "NON-DEVE-COMPARIRE"
            }, mock.Object);

            var valori = reader.GetValoriDatiDinamici(123);

            Assert.Equal(4, valori.Count());
            Assert.Equal("801", valori.Where(x => x.NomeCampo == "00.1.1.NUMERO DELLA DOMANDA").First().Valore);
            Assert.Equal("18/01/1966", valori.Where(x => x.NomeCampo == "DATIDICHIARANTE-DATANASCITA").First().ValoreDecodificato);
            Assert.Equal("TEST_MOLTEPLICITA", valori.Where(x => x.NomeCampo == "00.1.2.TITOLARECOGNOME" && x.IndiceMolteplicita == 1).First().ValoreDecodificato);
        }
    }
}
