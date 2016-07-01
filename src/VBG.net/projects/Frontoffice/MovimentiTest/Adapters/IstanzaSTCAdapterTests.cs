using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace MovimentiTest.Adapters
{

	public class IstanzaSTCAdapterTests
	{
		[TestClass]
		public class LocalizzazioneTests
		{
			LocalizzazioneAdapter _adapter;
			PresentazioneIstanzaDbV2 _db;
			DomandaOnlineReadInterface _readInterface;

			[TestInitialize]
			public void Initialize()
			{
				this._adapter = new LocalizzazioneAdapter();
				this._db = new PresentazioneIstanzaDbV2();
				this._readInterface = new DomandaOnlineReadInterface(PresentazioneIstanzaDataKey.New("E256", "SS", "1", 1), this._db, false);
			}

			[TestMethod]
			public void Il_campo_uuid_di_LocalizzazioneNelComuneType_viene_popolato()
			{
				var domandaStc = new DettaglioPraticaType();
				var uuid = "123456";

				var row = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

				row.ID = 1;
				row.CODICESTRADARIO = 1;
				row.Uuid = uuid;

				this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);

				this._adapter.Adapt(this._readInterface, domandaStc);

				Assert.AreEqual(1, domandaStc.localizzazione.Count());
				Assert.IsTrue(!String.IsNullOrEmpty(domandaStc.localizzazione[0].uuid));
				Assert.AreEqual(domandaStc.localizzazione[0].uuid, uuid);
			}

			[TestMethod]
			public void I_campi_latitudine_e_longitudine_vengono_popolati()
			{
				var domandaStc = new DettaglioPraticaType();
				var uuid = "123456";

				var row = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

				row.ID = 1;
				row.CODICESTRADARIO = 1;
				row.Uuid = uuid;
				row.Longitudine = "longitudine";
				row.Latitudine = "latitudine";

				this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);

				this._adapter.Adapt(this._readInterface, domandaStc);

				Assert.AreEqual(1, domandaStc.localizzazione.Count());
				Assert.IsNotNull(domandaStc.localizzazione[0].coordinate);
				Assert.IsTrue(!String.IsNullOrEmpty(domandaStc.localizzazione[0].coordinate.longitudine));
				Assert.IsTrue(!String.IsNullOrEmpty(domandaStc.localizzazione[0].coordinate.latitudine));
				Assert.AreEqual(domandaStc.localizzazione[0].coordinate.longitudine, row.Longitudine);
				Assert.AreEqual(domandaStc.localizzazione[0].coordinate.latitudine, row.Latitudine);
			}

			[TestMethod]
			public void Il_campo_tipoLocalizzazione_viene_popolato()
			{
				var domandaStc = new DettaglioPraticaType();
				var uuid = "123456";

				var row = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

				row.ID = 1;
				row.CODICESTRADARIO = 1;
				row.Uuid = uuid;
				row.TipoLocalizzazione = "tipoLocalizzazione";

				this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);

				this._adapter.Adapt(this._readInterface, domandaStc);

				Assert.AreEqual(1, domandaStc.localizzazione.Count());
				Assert.IsNotNull(domandaStc.localizzazione[0].tipo);
				Assert.AreEqual(row.TipoLocalizzazione, domandaStc.localizzazione[0].tipo.descrizione);
			}

			[TestMethod]
			public void Popolamento_dei_campi_di_localizzazioneType()
			{
				var domandaStc = new DettaglioPraticaType();

				var loc = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

				loc.Cap = "Cap";
				loc.Circoscrizione = "Circoscrizione";
				loc.CIVICO = "CIVICO";
				loc.CODICECOMUNE = "CODICECOMUNE";
				loc.CODICESTRADARIO = 2;
				loc.COLORE = "COLORE";
				loc.Esponente = "Esponente";
				loc.EsponenteInterno = "EsponenteInterno";
				loc.Fabbricato = "Fabbricato";
				loc.ID = 1;
				loc.Interno = "Interno";
				loc.Km = "Km";
				loc.Latitudine = "Latitudine";
				loc.Longitudine = "Longitudine";
				loc.NOTE = "NOTE";
				loc.Piano = "Piano";
				loc.Scala = "Scala";
				loc.STRADARIO = "STRADARIO";
				loc.TipoLocalizzazione = "TipoLocalizzazione";
				loc.Uuid = "Uuid";

				this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc);

				this._adapter.Adapt(this._readInterface, domandaStc);

				Assert.AreEqual(1, domandaStc.localizzazione.Count());

				Assert.AreEqual(loc.STRADARIO, domandaStc.localizzazione[0].denominazione);
				Assert.AreEqual(loc.CIVICO, domandaStc.localizzazione[0].civico);
				Assert.AreEqual(loc.CODICESTRADARIO.ToString(), domandaStc.localizzazione[0].id);
				Assert.AreEqual(loc.COLORE, domandaStc.localizzazione[0].colore);
				Assert.AreEqual(loc.Esponente, domandaStc.localizzazione[0].esponente);
				Assert.AreEqual(loc.EsponenteInterno, domandaStc.localizzazione[0].esponenteInterno);
				Assert.AreEqual(loc.Fabbricato, domandaStc.localizzazione[0].fabbricato);
				Assert.AreEqual(loc.Interno, domandaStc.localizzazione[0].interno);
				Assert.AreEqual(loc.Km, domandaStc.localizzazione[0].km);
				Assert.AreEqual(loc.Latitudine, domandaStc.localizzazione[0].coordinate.latitudine);
				Assert.AreEqual(loc.Longitudine, domandaStc.localizzazione[0].coordinate.longitudine);
				Assert.AreEqual(loc.Piano, domandaStc.localizzazione[0].piano);
				Assert.AreEqual(loc.Scala, domandaStc.localizzazione[0].scala);
				Assert.AreEqual(loc.TipoLocalizzazione, domandaStc.localizzazione[0].tipo.descrizione);
				Assert.AreEqual(loc.Uuid, domandaStc.localizzazione[0].uuid);

				//Assert.AreEqual(loc.ID, domandaStc.localizzazione[0].id);
				//Assert.AreEqual(loc.Cap 			  , domandaStc.localizzazione[0].  );
				//Assert.AreEqual(loc.Circoscrizione 	  , domandaStc.localizzazione[0].  );
				//Assert.AreEqual(loc.CODICECOMUNE 	  , domandaStc.localizzazione[0].  );
				//Assert.AreEqual(loc.NOTE 			  , domandaStc.localizzazione[0].  );
				//Assert.AreEqual(loc.STRADARIO 		  , domandaStc.localizzazione[0].stra  );
			}

			[TestMethod]
			public void Popolamento_dei_campi_di_riferimento_catastale()
			{
				var domandaStc = new DettaglioPraticaType();

				var loc = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();
				loc.ID = 0;
				loc.CODICESTRADARIO = 1;

				var dc1 = this._db.DATICATASTALI.NewDATICATASTALIRow();
				dc1.IdLocalizzazione = loc.ID;
				dc1.CodiceTipoCatasto = "F";
				dc1.TipoCatasto = "F1";
				dc1.Foglio = "1";
				dc1.Particella = "2";
				dc1.Sub = "3";

				var dc2 = this._db.DATICATASTALI.NewDATICATASTALIRow();
				dc2.IdLocalizzazione = loc.ID;
				dc2.CodiceTipoCatasto = "T";
				dc2.TipoCatasto = "F12";
				dc2.Foglio = "12";
				dc2.Particella = "22";

				this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc);
				this._db.DATICATASTALI.AddDATICATASTALIRow(dc1);
				this._db.DATICATASTALI.AddDATICATASTALIRow(dc2);

				this._adapter.Adapt(this._readInterface, domandaStc);

				Assert.AreEqual(1, domandaStc.localizzazione.Length);
				Assert.AreEqual(2, domandaStc.localizzazione[0].riferimentoCatastale.Length);

				Assert.AreEqual(RiferimentoCatastaleTypeTipoCatasto.EdilizioUrbano, domandaStc.localizzazione[0].riferimentoCatastale[0].tipoCatasto);
				Assert.AreEqual(dc1.Foglio, domandaStc.localizzazione[0].riferimentoCatastale[0].foglio);
				Assert.AreEqual(dc1.Particella, domandaStc.localizzazione[0].riferimentoCatastale[0].particella);
				Assert.AreEqual(dc1.Sub, domandaStc.localizzazione[0].riferimentoCatastale[0].sub);

				Assert.AreEqual(RiferimentoCatastaleTypeTipoCatasto.Terreni, domandaStc.localizzazione[0].riferimentoCatastale[1].tipoCatasto);
				Assert.AreEqual(dc2.Foglio, domandaStc.localizzazione[0].riferimentoCatastale[1].foglio);
				Assert.AreEqual(dc2.Particella, domandaStc.localizzazione[0].riferimentoCatastale[1].particella);
				Assert.AreEqual(dc2.Sub, domandaStc.localizzazione[0].riferimentoCatastale[1].sub);
			}

			[TestMethod]
			public void Viene_generata_una_localizzazioneType_per_ogni_localizzazione_della_domanda()
			{
				var domandaStc = new DettaglioPraticaType();

				var loc1 = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

				loc1.ID = 1;
				loc1.CODICESTRADARIO = 1;

				var loc2 = this._db.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

				loc2.ID = 2;
				loc2.CODICESTRADARIO = 2;

				this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc1);
				this._db.ISTANZESTRADARIO.AddISTANZESTRADARIORow(loc2);

				this._adapter.Adapt(this._readInterface, domandaStc);

				Assert.AreEqual(2, domandaStc.localizzazione.Count());
				/*
				Assert.AreEqual(loc1.CODICESTRADARIO, domandaStc.localizzazione[0]..Count());





				var mappale1 = this._db.DATICATASTALI.NewDATICATASTALIRow();

				mappale1.TipoCatasto = "F1";
				mappale1.Foglio = "1.1";
				mappale1.Particella = "1.2";
				mappale1.Sub = "1.3";

				this._db.DATICATASTALI.AddDATICATASTALIRow(mappale1);

				var mappale2 = this._db.DATICATASTALI.NewDATICATASTALIRow();

				mappale2.TipoCatasto = "F2";
				mappale2.Foglio = "2.1";
				mappale2.Particella = "2.2";
				mappale2.Sub = "2.3";

				this._db.DATICATASTALI.AddDATICATASTALIRow(mappale2);

				

				
				Assert.IsNotNull( domandaStc.localizzazione[0].riferimentoCatastale );
				Assert.AreEqual( 1, domandaStc.localizzazione[0].riferimentoCatastale.Length );
				Assert.AreEqual(mappale1.TipoCatasto,	domandaStc.localizzazione[0].riferimentoCatastale[0].tipoCatasto);
				Assert.AreEqual(mappale1.Foglio,		domandaStc.localizzazione[0].riferimentoCatastale[0].foglio);
				Assert.AreEqual(mappale1.Particella,	domandaStc.localizzazione[0].riferimentoCatastale[0].particella);
				Assert.AreEqual(mappale1.Sub,			domandaStc.localizzazione[0].riferimentoCatastale[0].sub);

				Assert.IsNotNull( domandaStc.localizzazione[1].riferimentoCatastale );
				Assert.AreEqual( 1, domandaStc.localizzazione[1].riferimentoCatastale.Length );
				Assert.AreEqual( mappale2.TipoCatasto,	domandaStc.localizzazione[1].riferimentoCatastale[1].tipoCatasto);
				Assert.AreEqual( mappale2.Foglio,		domandaStc.localizzazione[1].riferimentoCatastale[1].foglio);
				Assert.AreEqual( mappale2.Particella,	domandaStc.localizzazione[1].riferimentoCatastale[1].particella);
				Assert.AreEqual( mappale2.Sub,			domandaStc.localizzazione[1].riferimentoCatastale[1].sub);
				*/
			}
		}



		[TestClass]
		public class OneriTests
		{
			OneriAdapter _adapter;
			PresentazioneIstanzaDbV2 _db;
			DomandaOnlineReadInterface _readInterface;

			[TestInitialize]
			public void Initialize()
			{
				this._adapter = new OneriAdapter();
				this._db = new PresentazioneIstanzaDbV2();
				this._readInterface = new DomandaOnlineReadInterface(PresentazioneIstanzaDataKey.New("E256", "SS", "1", 1), this._db, false);
			}

			[TestMethod]
			public void Popolamento_degli_importi_pagati()
			{
				var domandaStc = new DettaglioPraticaType();

				var expectedValue = 66.6d;

				var row = this._db.OneriDomanda.NewOneriDomandaRow();

				row.Causale = "Causale";
				row.CodiceCausale = 1;
				row.CodiceInterventoOEndoOrigine = 0;
				row.CodiceTipoPagamento = "CodTipoPagamento";
				row.DataPagmento = "11/11/2014";
				row.DescrizioneTipoPagamento = "Tipo Pagamento";
				row.Importo = Convert.ToSingle(100.0d);
				row.ImportoPagato = Convert.ToSingle(expectedValue);
				row.InterventoOEndoOrigine = "InterventoOEndoOrigine";
				row.Note = "note";
				row.NumeroPagamento = "123";
				row.TipoOnere = "TipoOnere";

				this._db.OneriDomanda.AddOneriDomandaRow(row);

				this._adapter.Adapt(this._readInterface, domandaStc);

				Assert.AreEqual<int>(1, domandaStc.oneri.Length);
				Assert.AreEqual<double>(expectedValue, domandaStc.oneri[0].importo);
				Assert.AreEqual<int>(1, domandaStc.oneri[0].scadenze.Length);
				Assert.AreEqual<double>(expectedValue, domandaStc.oneri[0].scadenze[0].importoRata);
				Assert.AreEqual<int>(1, domandaStc.oneri[0].scadenze[0].pagamenti.Length);
				Assert.AreEqual<double>(expectedValue, domandaStc.oneri[0].scadenze[0].pagamenti[0].importo);

			}
		}
	}
}
