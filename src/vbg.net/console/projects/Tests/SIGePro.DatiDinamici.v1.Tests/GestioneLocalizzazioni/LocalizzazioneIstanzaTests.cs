using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace SIGePro.DatiDinamici.v1.Tests.GestioneLocalizzazioni
{
	public class LocalizzazioneIstanzaTests
	{
		[TestClass]
		public class FormattazioneIndirizzi
		{
			LocalizzazioneIstanza _localizzazione;

			[TestInitialize]
			public void Initialize()
			{
				_localizzazione = new LocalizzazioneIstanza
				{
					Uuid = "uuid",
					Indirizzo = "Via Manzoni",
					Civico = "civico",
					Esponente = "esponente",
					Scala = "scala",
					Piano = "piano",
					Interno = "interno",
					EsponenteInterno = "espInt",
					Km = "km",
					Note = "note",
					TipoLocalizzazione = "tipo",
					Coordinate = new LocalizzazioneIstanza.Coordinata
					{
						Latitudine = "latitudine",
						Longitudine = "longitudine"
					},
					Mappali = new LocalizzazioneIstanza.RiferimentiCatastali
					{
						TipoCatasto = "tipoCatasto",
						Foglio = "foglio",
						Particella = "particellza",
						Sub = "sub"
					}
				};
			}

			[TestMethod]
			public void Sostituzione_indirizzo()
			{
				var espressione = "{indirizzo}";

				var result = _localizzazione.ToString(espressione);

				Assert.AreEqual(result, _localizzazione.Indirizzo);

				result = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(result, _localizzazione.Indirizzo);
			}

			[TestMethod]
			public void Sostituzione_civico()
			{
				var espressione = "{civico}";
				var confronto = _localizzazione.Civico;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_esponente()
			{
				var espressione = "{esponente}";
				var confronto = _localizzazione.Esponente;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_scala()
			{
				var espressione = "{scala}";
				var confronto = _localizzazione.Scala;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_piano()
			{
				var espressione = "{piano}";
				var confronto = _localizzazione.Piano;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}


			[TestMethod]
			public void Sostituzione_interno()
			{
				var espressione = "{interno}";
				var confronto = _localizzazione.Interno;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}


			[TestMethod]
			public void Sostituzione_esponente_interno()
			{
				var espressione = "{esponenteinterno}";
				var confronto = _localizzazione.EsponenteInterno;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}


			[TestMethod]
			public void Sostituzione_km()
			{
				var espressione = "{km}";
				var confronto = _localizzazione.Km;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_note()
			{
				var espressione = "{note}";
				var confronto = _localizzazione.Note;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}


			[TestMethod]
			public void Sostituzione_tipo_localizzazione()
			{
				var espressione = "{tipo}";
				var confronto = _localizzazione.TipoLocalizzazione;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}


			[TestMethod]
			public void Sostituzione_coordinate()
			{
				var espressione = "{coordinate}";
				var confronto = _localizzazione.Coordinate.ToString();

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_coordinate_vuote()
			{
				var espressione = "{coordinate}";
				var confronto = String.Empty;

				_localizzazione.Coordinate = null;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_mappali()
			{
				var espressione = "{mappali}";
				var confronto = _localizzazione.Mappali.ToString();

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_mappali_vuoti()
			{
				var espressione = "{mappali}";
				var confronto =String.Empty;

				_localizzazione.Mappali = null;

				var result1 = _localizzazione.ToString(espressione);
				var result2 = _localizzazione.ToString(espressione.ToUpper());

				Assert.AreEqual(confronto, result1);
				Assert.AreEqual(confronto, result2);
			}

			[TestMethod]
			public void Sostituzione_spazi_centrali_consecutivi()
			{
				_localizzazione = new LocalizzazioneIstanza();

				var espressione = "A {indirizzo} B";
				var confronto = "A B";

				var result = _localizzazione.ToString(espressione);

				Assert.AreEqual(confronto, result);
			}

			[TestMethod]
			public void Sostituzione_spazi_centrali_consecutivi2()
			{
				_localizzazione = new LocalizzazioneIstanza();

				var espressione = "A {indirizzo} {civico} {esponente} B {scala} {piano}";
				var confronto = "A B";

				var result = _localizzazione.ToString(espressione);

				Assert.AreEqual(confronto, result);
			}

			[TestMethod]
			public void Sostituzione_spazi_iniziali_consecutivi()
			{
				_localizzazione = new LocalizzazioneIstanza();

				var espressione = "{indirizzo} A";
				var confronto = "A";

				var result = _localizzazione.ToString(espressione);

				Assert.AreEqual(confronto, result);
			}

			[TestMethod]
			public void Sostituzione_spazi_finali_consecutivi()
			{
				_localizzazione = new LocalizzazioneIstanza();

				var espressione = "A {indirizzo}";
				var confronto = "A";

				var result = _localizzazione.ToString(espressione);

				Assert.AreEqual(confronto, result);
			}

			[TestMethod]
			public void Se_l_espressione_di_formattazione_non_è_specificata_restituisce_via_e_civico()
			{
				var espressione = String.Empty;
				var confronto = _localizzazione.Indirizzo + " " + _localizzazione.Civico;

				var result = _localizzazione.ToString(espressione);

				Assert.AreEqual(confronto, result);
			}
		}

	}
}
