using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Xunit;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;

namespace MovimentiTest.TestConversioneVersioniDataSetDomanda
{
    public class V1ToV2UpgraderTests
    {
        /// <summary>
        /// Classe helper utilizzata per i tests. Utile per non dover confrontare 
        /// le rappresentazioni binarie del dataset
        /// </summary>
        public class V1ToV2UpgraderTestHelper : V1ToV2Upgrader
        {
            public PresentazioneIstanzaDbV2 V2Database;

            protected override PresentazioneIstanzaDbV2 MapV1ToV2(PresentazioneIstanzaDataSet v1)
            {
                V2Database = base.MapV1ToV2(v1);
                return V2Database;
            }
        }

        public class MapV1ToV2
        {
            [Fact]
            public void ConTabellaProcureContenenteAllegato_MuoveAllegatoSuTabellaAllegati()
            {
                PresentazioneIstanzaDataSet  v1Dataset = new PresentazioneIstanzaDataSet();
                v1Dataset.Procure.AddProcureRow("1", "2", "123", "nomeFile.txt");
                var testClass = new V1ToV2UpgraderTestHelper();
                testClass.Ugrade(new V1DataSetSerializer().Serialize(v1Dataset));
                var result = testClass.V2Database;
                Assert.Equal(1, result.Procure.Count);
                Assert.Equal(1, result.Allegati.Count);
                Assert.Equal(v1Dataset.Procure[0].CodiceAnagrafe, result.Procure[0].CodiceAnagrafe);
                Assert.Equal(v1Dataset.Procure[0].CodiceProcuratore, result.Procure[0].CodiceProcuratore);
                Assert.Equal(v1Dataset.Procure[0].CodiceOggettoProcura, result.Allegati[0].CodiceOggetto.ToString());
                Assert.Equal(v1Dataset.Procure[0].NomeFile, result.Allegati[0].NomeFile);
                Assert.Equal(result.Procure[0].IdAllegato, result.Allegati[0].Id);
            }

            [Fact]
            public void ConTabellaProcureContenenteAllegatoConEstensioneP7M_ImpostaFlagFirmatoDigitalmenteATrue()
            {
                PresentazioneIstanzaDataSet v1Dataset = new PresentazioneIstanzaDataSet();
                v1Dataset.Procure.AddProcureRow("1", "2", "123", "nomeFile.p7m");
                var testClass = new V1ToV2UpgraderTestHelper();
                testClass.Ugrade(new V1DataSetSerializer().Serialize(v1Dataset));
                var result = testClass.V2Database;
                Assert.True(result.Allegati[0].FirmatoDigitalmente);
            }

            [Fact]
            public void ConTabellaOggettiContenenteAllegato_MuoveAllegatoSuTabellaAllegati()
            {
                PresentazioneIstanzaDataSet v1Dataset = new PresentazioneIstanzaDataSet();
                v1Dataset.OGGETTI.AddOGGETTIRow(1, "descrizione", "nomefile", 1234, 1, 2, "tipoDocumento", 3, false, 0, 1, "codCategoria", "categoria", false, "tipoDownload", "md5Hash", "linkInformazioni", "nomeFileModello", "note");
                var testClass = new V1ToV2UpgraderTestHelper();
                testClass.Ugrade(new V1DataSetSerializer().Serialize(v1Dataset));
                var result = testClass.V2Database;
                Assert.Equal(1, result.OGGETTI.Count);
                Assert.Equal(1, result.Allegati.Count);
                Assert.Equal(v1Dataset.OGGETTI[0].CODICEOGGETTO, result.Allegati[0].CodiceOggetto);
                Assert.Equal(v1Dataset.OGGETTI[0].MD5Hash, result.Allegati[0].Md5);
                Assert.Equal(v1Dataset.OGGETTI[0].NOMEFILE, result.Allegati[0].NomeFile);
                Assert.Equal(v1Dataset.OGGETTI[0].Note, result.Allegati[0].Note);
                Assert.Equal(result.OGGETTI[0].IdAllegato, result.Allegati[0].Id);
            }

            [Fact]
            public void ConTabellaDelegaATrasmettereContenenteAllegato_MuoveAllegatoSuTabellaAllegati()
            {
                PresentazioneIstanzaDataSet v1Dataset = new PresentazioneIstanzaDataSet();
                v1Dataset.DelegaATrasmettere.AddDelegaATrasmettereRow("nomeFile.txt", 123);
                var testClass = new V1ToV2UpgraderTestHelper();
                testClass.Ugrade(new V1DataSetSerializer().Serialize(v1Dataset));
                var result = testClass.V2Database;
                Assert.Equal(1, result.DelegaATrasmettere.Count);
                Assert.Equal(1, result.Allegati.Count);
                Assert.Equal(v1Dataset.DelegaATrasmettere[0].CodiceOggetto, result.Allegati[0].CodiceOggetto);
                Assert.Equal(v1Dataset.DelegaATrasmettere[0].NomeFile, result.Allegati[0].NomeFile);
                Assert.Equal(result.DelegaATrasmettere[0].IdAllegato, result.Allegati[0].Id);
            }

            [Fact]
            public void ConTabellaAttestazioneDiPagamentoContenenteAllegato_MuoveAllegatoSuTabellaAllegati()
            {
                PresentazioneIstanzaDataSet v1Dataset = new PresentazioneIstanzaDataSet();
                v1Dataset.OneriAttestazionePagamento.AddOneriAttestazionePagamentoRow(100,"nomeFile.txt", 123, false);
                var testClass = new V1ToV2UpgraderTestHelper();
                testClass.Ugrade(new V1DataSetSerializer().Serialize(v1Dataset));
                var result = testClass.V2Database;
                Assert.Equal(1, result.OneriAttestazionePagamento.Count);
                Assert.Equal(1, result.Allegati.Count);
                Assert.Equal(v1Dataset.OneriAttestazionePagamento[0].CodiceOggetto, result.Allegati[0].CodiceOggetto);
                Assert.Equal(v1Dataset.OneriAttestazionePagamento[0].NomeFile, result.Allegati[0].NomeFile);
                Assert.Equal(result.OneriAttestazionePagamento[0].IdAllegato, result.Allegati[0].Id);
            }

            [Fact]
            public void ConTabellaRiepiloghiDatiDinamiciConAllegato_MuoveAllegatoSuTabellaAllegati()
            {
                PresentazioneIstanzaDataSet v1Dataset = new PresentazioneIstanzaDataSet();
                v1Dataset.RiepilogoDatiDinamici.AddRiepilogoDatiDinamiciRow(1, 2, "descrizione", 123, "nomeFile", "md5hash");
                var testClass = new V1ToV2UpgraderTestHelper();
                testClass.Ugrade(new V1DataSetSerializer().Serialize(v1Dataset));
                var result = testClass.V2Database;
                Assert.Equal(1, result.RiepilogoDatiDinamici.Count);
                Assert.Equal(1, result.Allegati.Count);
                Assert.Equal(v1Dataset.RiepilogoDatiDinamici[0].CodiceOggetto, result.Allegati[0].CodiceOggetto);
                Assert.Equal(v1Dataset.RiepilogoDatiDinamici[0].NomeFile, result.Allegati[0].NomeFile);
                Assert.Equal(v1Dataset.RiepilogoDatiDinamici[0].MD5Hash, result.Allegati[0].Md5);
                Assert.Equal(result.RiepilogoDatiDinamici[0].IdAllegato, result.Allegati[0].Id);
            }
        }
    }
}
