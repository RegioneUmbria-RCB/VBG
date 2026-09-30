using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;

namespace SIGePro.DatiDinamici.v1.Tests.ValidazioneValoriCampi
{
	public partial class CampoObbligatorioValidatorTests
	{
		[TestClass]
		public class ValidazioneCampiNumerici
		{
			CampoDinamicoStub _campo;
			int _idCampo;

			[TestInitialize]
			public void Initialize()
			{
				this._campo = new CampoDinamicoStub();
				this._idCampo = 666;

				this._campo.SetId(this._idCampo);
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_non_valido_fallisce()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("test", "test");

				var result = new ValidNumberValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(1, result.Count());
				Assert.AreEqual<string>(etichetta, result.ElementAt(0).Messaggio);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
			}
			
			[TestMethod]
			public void Un_valore_con_separatore_migliaia_non_e_un_numero_valido()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("1.234,56", "1.234,56");

				var result = new ValidNumberValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(1, result.Count());
				Assert.AreEqual<string>(etichetta, result.ElementAt(0).Messaggio);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
			}
			
			[TestMethod]
			public void Validazione_di_un_valore_numerico_valido_ha_successo1()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("123", "123");

				var result = new ValidNumberValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_valido_ha_successo2()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("123,45", "123,45");

				var result = new ValidNumberValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_maggiore_di_valore_min_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMin(1);
				this._campo.ListaValori.AggiungiValore("2", "2");

				var result = new MinValueValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_uguale_a_valore_min_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMin(1);
				this._campo.ListaValori.AggiungiValore("1", "1");

				var result = new MinValueValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_minore_di_valore_min_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var valoreMin = 100;

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMin(valoreMin);
				this._campo.ListaValori.AggiungiValore("10", "10");

				var result = new MinValueValidator(this._campo, 0, "{0} {1}").GetErroriDiValidazione();

				Assert.AreEqual<int>(1, result.Count());
				Assert.AreEqual<int>(0, result.ElementAt(0).Indice);
				Assert.AreEqual<string>(etichetta + " " + valoreMin, result.ElementAt(0).Messaggio);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_minore_di_valore_max_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMax(10);
				this._campo.ListaValori.AggiungiValore("2", "2");

				var result = new MaxValueValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_uguale_a_valore_max_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMax(1);
				this._campo.ListaValori.AggiungiValore("1", "1");

				var result = new MaxValueValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_un_valore_numerico_maggiore_di_valore_max_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var valoreMin = 10;

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMax(valoreMin);
				this._campo.ListaValori.AggiungiValore("100", "100");

				var result = new MaxValueValidator(this._campo, 0, "{0} {1}").GetErroriDiValidazione();

				Assert.AreEqual<int>(1, result.Count());
				Assert.AreEqual<int>(0, result.ElementAt(0).Indice);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
				Assert.AreEqual<string>(etichetta + " " + valoreMin, result.ElementAt(0).Messaggio);
			}

		}

		[TestClass]
		public class ValidazioneCampiNumericiMultipli
		{
			CampoDinamicoStub _campo;
			int _idCampo;

			[TestInitialize]
			public void Initialize()
			{
				this._campo = new CampoDinamicoStub();
				this._idCampo = 666;

				this._campo.SetId(this._idCampo);
			}

			[TestMethod]
			public void Validazione_di_valori_multipli_numerici_non_validi_fallisce()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("test", "test");
				this._campo.ListaValori.AggiungiValore("test", "test");

				var result = new ValoriNumericiValidiValidator(this._campo, "{0}","{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(2, result.Count());
				Assert.AreEqual<string>(etichetta + " 1", result.ElementAt(0).Messaggio);
				Assert.AreEqual<string>(etichetta + " 2", result.ElementAt(1).Messaggio);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(1).IdCampo);
			}

			[TestMethod]
			public void Validazione_di_valori_multipli_con_virgola_come_separatore_decimali_fallisce()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("1.234,56", "1.234,56");
				this._campo.ListaValori.AggiungiValore("1.234,56", "1.234,56");

				var result = new ValoriNumericiValidiValidator(this._campo, "{0}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(2, result.Count());
				Assert.AreEqual<string>(etichetta + " 1", result.ElementAt(0).Messaggio);
				Assert.AreEqual<string>(etichetta + " 2", result.ElementAt(1).Messaggio);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(1).IdCampo);
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_validi_ha_successo1()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("123", "123");
				this._campo.ListaValori.AggiungiValore("123", "123");

				var result = new ValoriNumericiValidiValidator(this._campo, "{0}","{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_validi_ha_successo2()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.ListaValori.AggiungiValore("123,45", "123,45");
				this._campo.ListaValori.AggiungiValore("123,45", "123,45");

				var result = new ValoriNumericiValidiValidator(this._campo, "{0}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_maggiori_di_valore_min_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMin(1);
				this._campo.ListaValori.AggiungiValore("2", "2");
				this._campo.ListaValori.AggiungiValore("3", "3");

				var result = new ValoriNumericiMinValueValidator(this._campo, "{0} {1}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_uguale_a_valore_min_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMin(1);
				this._campo.ListaValori.AggiungiValore("1", "1");
				this._campo.ListaValori.AggiungiValore("1", "1");

				var result = new ValoriNumericiMinValueValidator(this._campo, "{0} {1}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_minori_di_valore_min_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var valoreMin = 100;

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMin(valoreMin);
				this._campo.ListaValori.AggiungiValore("10", "10");
				this._campo.ListaValori.AggiungiValore("11", "11");

				var result = new ValoriNumericiMinValueValidator(this._campo, "{0} {1}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(2, result.Count());
				Assert.AreEqual<int>(0, result.ElementAt(0).Indice);
				Assert.AreEqual<int>(1, result.ElementAt(1).Indice);
				Assert.AreEqual<string>(etichetta + " " + valoreMin + " 1", result.ElementAt(0).Messaggio);
				Assert.AreEqual<string>(etichetta + " " + valoreMin + " 2", result.ElementAt(1).Messaggio);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(1).IdCampo);
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_minori_di_valore_max_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMax(10);
				this._campo.ListaValori.AggiungiValore("2", "2");

				var result = new ValoriNumericiMaxValueValidator(this._campo, "{0} {1}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_uguali_a_valore_max_non_restituisce_errore()
			{
				var etichetta = "Etichetta";

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMax(1);
				this._campo.ListaValori.AggiungiValore("1", "1");

				var result = new ValoriNumericiMaxValueValidator(this._campo, "{0} {1}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, result.Count());
			}

			[TestMethod]
			public void Validazione_di_valori_numerici_maggiori_di_valore_max_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var valoreMax = 10;

				this._campo.SetEtichetta(etichetta);
				this._campo.SetValoreValidazioneMax(valoreMax);
				this._campo.ListaValori.AggiungiValore("100", "100");
				this._campo.ListaValori.AggiungiValore("100", "100");

				var result = new ValoriNumericiMaxValueValidator(this._campo, "{0} {1}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(2, result.Count());
				Assert.AreEqual<int>(0, result.ElementAt(0).Indice);
				Assert.AreEqual<int>(1, result.ElementAt(1).Indice);
				Assert.AreEqual<string>(etichetta + " " + valoreMax + " 1", result.ElementAt(0).Messaggio);
				Assert.AreEqual<string>(etichetta + " " + valoreMax + " 2", result.ElementAt(1).Messaggio);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(0).IdCampo);
				Assert.AreEqual<int>(this._idCampo, result.ElementAt(1).IdCampo);
			}

		}
	}
}
