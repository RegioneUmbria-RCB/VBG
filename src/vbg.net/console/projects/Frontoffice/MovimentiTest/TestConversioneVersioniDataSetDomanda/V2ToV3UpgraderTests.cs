using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Upgrader;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda;

namespace MovimentiTest.TestConversioneVersioniDataSetDomanda
{
	public class V2ToV3UpgraderTests
	{
		[TestClass]
		public class V2ToV3Mapping
		{
			V2DataSetSerializer _serializer;
			V2ToV3Upgrader _upgrader;
			PresentazioneIstanzaDbV2 _origine;

			[TestInitialize]
			public void Initialize()
			{
				_serializer = new V2DataSetSerializer();
				_upgrader = new V2ToV3Upgrader();
				_origine = new PresentazioneIstanzaDbV2();
			}

			private PresentazioneIstanzaDbV2.ISTANZESTRADARIORow CreaRecordLocalizzazioni(int id = 1)
			{
				var row = _origine.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

				// campi obbligatori
				row.ID = id;
				row.CODICESTRADARIO = 1;
				row.Cap = "Cap";
				row.Circoscrizione = "Circoscrizione";
				row.CIVICO = "CIVICO";
				row.CODICECOMUNE = "CODICECOMUNE";
				row.CODICESTRADARIO = 1;
				row.COLORE = "COLORE";
				row.Esponente = "Esponente";
				row.EsponenteInterno = "EsponenteInterno";
				row.Fabbricato = "Fabbricato";
				row.Interno = "Interno";
				row.Km = "Km";
				row.Latitudine = "Latitudine";
				row.Longitudine = "Longitudine";
				row.NOTE = "NOTE";
				row.Piano = "Piano";
				row.Scala = "Scala";
				row.STRADARIO = "STRADARIO";
				row.TipoLocalizzazione = "TipoLocalizzazione";
				row.Uuid = "Uuid";

				return row;
			}

			private PresentazioneIstanzaDbV2.DATICATASTALIRow CreaRecordDatiCatastali(int id = 1)
			{
				var dc = _origine.DATICATASTALI.NewDATICATASTALIRow();

				dc.Id = id;
				dc.CodiceTipoCatasto = "F" + id.ToString();
				dc.TipoCatasto = "F1" + id.ToString();
				dc.Foglio = "1" + id.ToString();
				dc.Particella = "2" + id.ToString();
				dc.Sub = "3" + id.ToString();

				return dc;
			}

			[TestMethod]
			public void Gli_stradari_privi_di_uuid_vengono_aggiornati_con_un_nuovo_uuid()
			{
				var row = CreaRecordLocalizzazioni();

				_origine.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);

				var destinazioneBin = _upgrader.Ugrade(_serializer.Serialize(_origine));
				var destinazione = _serializer.Deserialize(destinazioneBin);

				Assert.AreEqual(1, destinazione.ISTANZESTRADARIO.Count);
				Assert.IsFalse(destinazione.ISTANZESTRADARIO[0].IsUuidNull());
				Assert.IsFalse(String.IsNullOrEmpty(destinazione.ISTANZESTRADARIO[0].Uuid));
			}

			[TestMethod]
			public void Tutti_i_riferimenti_catastali_vengono_associati_ad_una_localizzazione()
			{
				var row = CreaRecordLocalizzazioni();
				var localizzazioneId = row.ID;

				_origine.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);

				var dc = CreaRecordDatiCatastali(1);

				_origine.DATICATASTALI.AddDATICATASTALIRow(dc);

				var destinazioneBin = _upgrader.Ugrade(_serializer.Serialize(_origine));
				var destinazione = _serializer.Deserialize(destinazioneBin);

				Assert.AreEqual(localizzazioneId, destinazione.DATICATASTALI[0].IdLocalizzazione);
			}

			[TestMethod]
			public void Se_esiste_piu_di_un_riferimento_catastale_e_una_sola_localizzazione_entrambi_i_riferimenti_catastali_vengono_associati_alla_localizzazione()
			{
				var localizzazioneId = 1;
				var row = CreaRecordLocalizzazioni(localizzazioneId);
				
				_origine.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);

				var dc1 = CreaRecordDatiCatastali(1);
				var dc2 = CreaRecordDatiCatastali(2);
				
				_origine.DATICATASTALI.AddDATICATASTALIRow(dc1);
				_origine.DATICATASTALI.AddDATICATASTALIRow(dc2);

				var destinazioneBin = _upgrader.Ugrade(_serializer.Serialize(_origine));
				var destinazione = _serializer.Deserialize(destinazioneBin);

				Assert.AreEqual(1, destinazione.ISTANZESTRADARIO.Count);
				Assert.AreEqual(2, destinazione.DATICATASTALI.Count);
				Assert.AreEqual(localizzazioneId, destinazione.DATICATASTALI[0].IdLocalizzazione);
				Assert.AreEqual(localizzazioneId, destinazione.DATICATASTALI[1].IdLocalizzazione);
			}
		}
	}
}
