using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;

namespace MovimentiTest.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
	
	public class LocalizzazioniTests
	{
		[TestClass]
		public class LocalizzazioniWriteInterfaceTests
		{
			private NuovaLocalizzazione CreaIndirizzo()
			{
				return new NuovaLocalizzazione(1, "indirizzo", "civico");
			}

			PresentazioneIstanzaDbV2 _db;
			ILocalizzazioniWriteInterface _writeInterface;

			[TestInitialize]
			public void Initialize()
			{
				this._db = new PresentazioneIstanzaDbV2();
				this._writeInterface = new LocalizzazioniWriteInterface(_db);

				// Dati obbligatori
				var r = this._db.ISTANZE.NewISTANZERow();
				r.CODICECOMUNE = "E256";

				this._db.ISTANZE.AddISTANZERow(r);
			}

			[TestMethod]
			public void Su_creazione_localizzazione_viene_popolato_il_suo_uuid()
			{
				this._writeInterface.AggiungiLocalizzazione(CreaIndirizzo());

				Assert.AreEqual(1, this._db.ISTANZESTRADARIO.Count);
				Assert.IsTrue(!this._db.ISTANZESTRADARIO[0].IsUuidNull());
				Assert.IsTrue(!String.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].Uuid));
			}

			[TestMethod]
			public void Su_creazione_localizzazione_se_impostato_tipo_localizzazione_viene_salvato_su_db()
			{
				var indirizzo = CreaIndirizzo();
				indirizzo.TipoLocalizzazione = "TipoLocalizzazione";

				this._writeInterface.AggiungiLocalizzazione(indirizzo);

				Assert.AreEqual(1, this._db.ISTANZESTRADARIO.Count);
				Assert.IsTrue(!String.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].TipoLocalizzazione));
				Assert.AreEqual(indirizzo.TipoLocalizzazione, this._db.ISTANZESTRADARIO[0].TipoLocalizzazione);
			}

			[TestMethod]
			public void Su_creazione_localizzazione_latitudine_e_longitudine_vengono_salvate_su_db()
			{
				var indirizzo = CreaIndirizzo();
				indirizzo.Latitudine = "Latitudine";
				indirizzo.Longitudine = "Longitudine";

				this._writeInterface.AggiungiLocalizzazione(indirizzo);

				Assert.AreEqual(1, this._db.ISTANZESTRADARIO.Count);
				Assert.IsTrue(!String.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].Latitudine));
				Assert.IsTrue(!String.IsNullOrEmpty(this._db.ISTANZESTRADARIO[0].Longitudine));
				Assert.AreEqual(indirizzo.Latitudine, this._db.ISTANZESTRADARIO[0].Latitudine);
				Assert.AreEqual(indirizzo.Longitudine, this._db.ISTANZESTRADARIO[0].Longitudine);
			}

			[TestMethod]
			public void Su_creazione_localizzazione_con_mappali_i_mappali_vengono_salvati_su_db()
			{
				var indirizzo = CreaIndirizzo();
				this._writeInterface.AggiungiLocalizzazione(indirizzo);

				var codiceIndirizzo = this._db.ISTANZESTRADARIO[0].ID;
				var datiCatastali1 = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");

				this._writeInterface.AssegnaRiferimentiCatastaliALocalizzazione(codiceIndirizzo, datiCatastali1);

				Assert.AreEqual(1, this._db.DATICATASTALI.Count);
				Assert.AreEqual(codiceIndirizzo, this._db.DATICATASTALI[0].IdLocalizzazione);
				Assert.AreEqual(datiCatastali1.TipoCatasto , this._db.DATICATASTALI[0].TipoCatasto);
				Assert.AreEqual(datiCatastali1.CodiceTipoCatasto, this._db.DATICATASTALI[0].CodiceTipoCatasto);
				Assert.AreEqual(datiCatastali1.Foglio, this._db.DATICATASTALI[0].Foglio);
				Assert.AreEqual(datiCatastali1.Particella, this._db.DATICATASTALI[0].Particella);
				Assert.AreEqual(datiCatastali1.Sub, this._db.DATICATASTALI[0].Sub);
			}

			[TestMethod]
			public void Si_possono_aggiungere_due_riferimenti_catastali_allo_stesso_indirizzo()
			{
				var indirizzo = CreaIndirizzo();
				this._writeInterface.AggiungiLocalizzazione(indirizzo);

				var codiceIndirizzo = this._db.ISTANZESTRADARIO[0].ID;

				var datiCatastali1 = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");
				var datiCatastali2 = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");

				this._writeInterface.AssegnaRiferimentiCatastaliALocalizzazione(codiceIndirizzo, datiCatastali1);
				this._writeInterface.AssegnaRiferimentiCatastaliALocalizzazione(codiceIndirizzo, datiCatastali2);

				Assert.AreEqual(codiceIndirizzo, this._db.DATICATASTALI[0].IdLocalizzazione);
				Assert.AreEqual(codiceIndirizzo, this._db.DATICATASTALI[1].IdLocalizzazione);

			}

			[TestMethod]
			public void Si_puo_creare_una_localizzazione_con_i_riferimenti_catastali_in_una_sola_chiamata()
			{
				var datiCatastali = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");

				this._writeInterface.AggiungiLocalizzazioneConRiferimentiCatastali(CreaIndirizzo(), datiCatastali);

				var idLocalizzazione = this._db.ISTANZESTRADARIO[0].ID;

				Assert.AreEqual(1, this._db.DATICATASTALI.Count);
				Assert.AreEqual(idLocalizzazione, this._db.DATICATASTALI[0].IdLocalizzazione);
				Assert.AreEqual(datiCatastali.TipoCatasto, this._db.DATICATASTALI[0].TipoCatasto);
				Assert.AreEqual(datiCatastali.CodiceTipoCatasto, this._db.DATICATASTALI[0].CodiceTipoCatasto);
				Assert.AreEqual(datiCatastali.Foglio, this._db.DATICATASTALI[0].Foglio);
				Assert.AreEqual(datiCatastali.Particella, this._db.DATICATASTALI[0].Particella);
				Assert.AreEqual(datiCatastali.Sub, this._db.DATICATASTALI[0].Sub);
			}

			[TestMethod]
			public void L_eliminazione_di_una_localizzazione_elimina_anche_i_relativi_dati_catastali()
			{
				var datiCatastali = new NuovoRiferimentoCatastale("F", "F1", "1", "2", "3");

				this._writeInterface.AggiungiLocalizzazioneConRiferimentiCatastali(CreaIndirizzo(), datiCatastali);

				var idLocalizzazione = this._db.ISTANZESTRADARIO[0].ID;

				this._writeInterface.EliminaLocalizzazione(idLocalizzazione);

				Assert.AreEqual(0, this._db.ISTANZESTRADARIO.Count);
				Assert.AreEqual(0, this._db.DATICATASTALI.Count);
			}
		}
	}
}
