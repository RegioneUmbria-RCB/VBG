using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Utils;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.Data;

namespace MovimentiTest.TestConversioneVersioniDataSetDomanda
{
	
	public class DataSetCloneHelperTests
	{
        [TestClass]
        public class CreateFrom
        {
            [TestMethod]
            public void conDatatablePopolata_creaNuovoDatasetConIValoriDellaTabellaOrigine()
            {
                // Attenzione il test verifica più condizioni contemporaneamente. Questo è formalmente sbagliato 
                // ma al momento è l'unico modo per verificare che la copia venga effettuata correttamente

                var cloneHelper = new DataSetCloneHelper<PresentazioneIstanzaDataSet, PresentazioneIstanzaDbV2>();

                var src = new PresentazioneIstanzaDataSet();

				src.ISTANZE.AddISTANZERow(new DateTime(2013, 1, 1), "oggetto", "note", 1, "nome_attivita", "codice", "codcomune", "flg_tecnico", true, "descrintervento", "indirizzoDomicilio");

                var dst = cloneHelper.CreateFrom(src);

				Assert.AreEqual<int>(1, dst.ISTANZE.Count);

				foreach (var col in src.ISTANZE.Columns.Cast<DataColumn>())
                {
					Assert.AreEqual(dst.ISTANZE[0][col.ColumnName], src.ISTANZE[0][col.ColumnName]);
                }

            }

        }

		
	}
}
