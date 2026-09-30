using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.SIGePro.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters.DatiDinamiciAdapterHelpers;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;

namespace MovimentiTest.Adapters
{
	public class StrutturaModelloStub : IStrutturaModello
	{
		public int IdModello
		{
			get;
			set;
		}

		public string CodiceScheda
		{
			get;
			set;
		}

		public string Descrizione
		{
			get;
			set;
		}

		public IEnumerable<ICampoDinamico> Campi
		{
			get;
			set;
		}
	}


	public class StrutturaModelloStubReader : IStrutturaModelloReader
	{
		IStrutturaModello _strutturaModello;

		public StrutturaModelloStubReader(IStrutturaModello strutturaModello)
		{
			this._strutturaModello = strutturaModello;
		}

		public IStrutturaModello Read(int idModello)
		{
			return this._strutturaModello;
		}
	}

	public class CampoDinamicoStub : ICampoDinamico
	{
		int _id;
		string _etichetta;
		string _nomeCampo;

		public CampoDinamicoStub(int id, string etichetta, string nomeCampo)
		{
			this._id = id;
			this._etichetta = etichetta;
			this._nomeCampo = nomeCampo;
		}

		public int Id
		{
			get { return this._id; }
		}

		public string Etichetta
		{
			get { return this._etichetta; }
		}

		public string NomeCampo
		{
			get { return this._nomeCampo; }
		}
	}


	[TestClass]
	public class DatiDinamiciAdapterTests
	{
		PresentazioneIstanzaDbV2 _db;
		DomandaOnlineReadInterface _readInterface;
		DatiDinamiciAdapter _adapter;
		int _idCampoDinamico;

		[TestInitialize]
		public void Initialize()
		{
			this._idCampoDinamico = 123;
			this._db = new PresentazioneIstanzaDbV2();
			this._readInterface = new DomandaOnlineReadInterface(PresentazioneIstanzaDataKey.New("E256", "SS", "1", 1), this._db, false);

			this._db.Dyn2Modelli.AddDyn2ModelliRow(0, "Nome scheda", true, 0, 0, true);
						
			
			var reader = new StrutturaModelloStubReader(CreaStrutturaModello()); 
			this._adapter = new DatiDinamiciAdapter(reader);
		}

		private StrutturaModelloStub CreaStrutturaModello()
		{
			var campo = new CampoDinamicoStub(this._idCampoDinamico, "Etichetta", "Nome campo");

			return new StrutturaModelloStub
			{
				IdModello = 0,
				CodiceScheda = "Codice scheda",
				Descrizione = "Descrizione",
				Campi = new List<ICampoDinamico>() { campo }
			};
		}

		[TestMethod]
		public void L_indice_del_valore_di_un_campo_dinamico_viene_riportato_nella_domanda_stc()
		{
			var domandaStc = new DettaglioPraticaType();

			// Creo i dati della scheda
			var indice = 2;
			var indicemolteplicita = 0;
			var valore = "valore";
			var valoreDecodificato = "valoreDecodificato";

			this._db.Dyn2Dati.AddDyn2DatiRow(this._idCampoDinamico, indice, indicemolteplicita, valore, valoreDecodificato, String.Empty);

			this._adapter.Adapt(this._readInterface, domandaStc);

			Assert.AreEqual(1, domandaStc.schede.Count());
			Assert.AreEqual(1, domandaStc.schede[0].campi.Length);
			Assert.IsNotNull(domandaStc.schede[0].campi[0].campoDinamico);
			Assert.IsNotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente);
			Assert.IsNotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore);
			Assert.AreEqual(1, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore.Length);
			Assert.AreEqual(indice, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indice);
			Assert.IsTrue(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceSpecified);
			Assert.AreEqual(indicemolteplicita, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceMolteplicita);
			Assert.IsTrue(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceMolteplicitaSpecified);
			Assert.AreEqual(valore, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].codice);
			Assert.AreEqual(valoreDecodificato, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].descrizione);
		}

		[TestMethod]
		public void Se_ho_un_valore_all_indice_2_allora_non_viene_creato_un_campo_con_valori_vuoti_all_indice_1()
		{
			var domandaStc = new DettaglioPraticaType();

			// Creo i dati della scheda
			var indice = 0;
			var indicemolteplicita = 2;
			var valore = "valore";
			var valoreDecodificato = "valoreDecodificato";

			this._db.Dyn2Dati.AddDyn2DatiRow(this._idCampoDinamico, indice, indicemolteplicita, valore, valoreDecodificato, String.Empty);

			this._adapter.Adapt(this._readInterface, domandaStc);

			Assert.AreEqual(1, domandaStc.schede.Count());
			Assert.AreEqual(1, domandaStc.schede[0].campi.Length);
			Assert.IsNotNull(domandaStc.schede[0].campi[0].campoDinamico);
			Assert.IsNotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente);
			Assert.IsNotNull(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore);
			Assert.AreEqual(1, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore.Length);
			Assert.AreEqual(indice, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indice);
			Assert.IsTrue(domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].indiceSpecified);
			Assert.AreEqual(valore, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].codice);
			Assert.AreEqual(valoreDecodificato, domandaStc.schede[0].campi[0].campoDinamico.valoreUtente.valore[0].descrizione);
		}
	}
}
