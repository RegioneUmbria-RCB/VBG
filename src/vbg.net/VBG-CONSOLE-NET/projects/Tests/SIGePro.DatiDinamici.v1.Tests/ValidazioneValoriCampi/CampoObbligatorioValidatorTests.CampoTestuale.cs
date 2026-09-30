using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;
using Init.SIGePro.DatiDinamici.Contesti;

namespace SIGePro.DatiDinamici.v1.Tests.ValidazioneValoriCampi
{
	public partial class CampoObbligatorioValidatorTests
	{
		[TestClass]
		public class ValidazioneCampiTestuali
		{
			ModelloDinamicoStub _modello;
			CampoDinamicoStub _campo;
			int _idCampo;

			[TestInitialize]
			public void Initialize()
			{
				this._modello = new ModelloDinamicoStub();
				this._campo = new CampoDinamicoStub(this._modello);
				this._idCampo = 666;

				this._campo.SetId(this._idCampo);
			}

			[TestMethod]
			public void Valore_singolo_se_il_campo_e_obbligatorio_restituisce_un_messaggio_di_errore()
			{
				var etichetta = "Etichetta";

				_campo.SetObbligatorio(true);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore(String.Empty,String.Empty);

				var validator = new CampoObbligatorioValidator(_campo, 0, "{0}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(1, errors.Count());
				Assert.AreEqual<string>(etichetta, errors.ElementAt(0).Messaggio);
				Assert.AreEqual<int>(0, errors.ElementAt(0).Indice);
				Assert.AreEqual<int>(this._idCampo, errors.ElementAt(0).IdCampo);
				
			}

			[TestMethod]
			public void Valore_singolo_se_il_campo_e_obbligatorio_ma_con_ignora_obbligatorieta_su_attivita_non_restituisce_un_messaggio_di_errore()
			{
				var etichetta = "Etichetta";

				_modello.ImpostaContesto(ContestoModelloEnum.Attivita);

				_campo.SetObbligatorio(true);
				_campo.SetIgnoraObbligatorietaSuAttivita(true);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore(String.Empty, String.Empty);

				var validator = new CampoObbligatorioValidator(_campo, 0, "{0}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(0, errors.Count());
			}

			[TestMethod]
			public void Valore_singolo_se_il_campo_non_e_obbligatorio_non_restituisce_errori()
			{
				_campo.SetObbligatorio(false);
				_campo.Valore = String.Empty;
				_campo.ValoreDecodificato = String.Empty;

				var validator = new CampoObbligatorioValidator(_campo, 0, String.Empty);

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual(0, errors.Count());
			}

			[TestMethod]
			public void Valore_multiplo_se_il_campo_e_obbligatorio_restituisce_un_messaggio_di_errore_per_ogni_valore_mancante()
			{
				var etichetta = "Etichetta";

				_campo.SetObbligatorio(true);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore(String.Empty, String.Empty);
				_campo.ListaValori.AggiungiValore(String.Empty, String.Empty);

				var validator = new CampiMultipliObbligatoriValidator(_campo, "{0}", "{0}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(2, errors.Count());
				Assert.AreEqual<string>(etichetta + " 1", errors.ElementAt(0).Messaggio);
				Assert.AreEqual<string>(etichetta + " 2", errors.ElementAt(1).Messaggio);
				Assert.AreEqual<int>(0, errors.ElementAt(0).Indice);
				Assert.AreEqual<int>(1, errors.ElementAt(1).Indice);
				Assert.AreEqual<int>(this._idCampo, errors.ElementAt(0).IdCampo);
				Assert.AreEqual<int>(this._idCampo, errors.ElementAt(1).IdCampo);
			}

			[TestMethod]
			public void Valore_multiplo_se_il_campo_e_obbligatorio_e_contiene_un_solo_valore_restituisce_errore_semplice()
			{
				var etichetta = "Etichetta";

				_campo.SetObbligatorio(true);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore(String.Empty, String.Empty);

				var validator = new CampiMultipliObbligatoriValidator(_campo, "{0}", "{0}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(1, errors.Count());
				Assert.AreEqual<string>(etichetta, errors.ElementAt(0).Messaggio);
				Assert.AreEqual<int>(0, errors.ElementAt(0).Indice);
				Assert.AreEqual<int>(this._idCampo, errors.ElementAt(0).IdCampo);
				
			}

			[TestMethod]
			public void Valore_multiplo_se_il_campo_non_e_obbligatorio_non_restituisce_errori()
			{
				var etichetta = "Etichetta";

				_campo.SetObbligatorio(false);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore(String.Empty, String.Empty);
				_campo.ListaValori.AggiungiValore(String.Empty, String.Empty);

				var validator = new CampiMultipliObbligatoriValidator(_campo, "{0}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(0, errors.Count());
			}

			[TestMethod]
			public void Valore_singolo_non_match_con_regex_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var regex = "[a-zA-Z]+";

				_campo.SetRegex(regex);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore("123","123");

				var validator = new RegexCampoValidator(_campo, 0, "{0} {1}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(1, errors.Count());
				Assert.AreEqual<string>(etichetta + " " + regex, errors.ElementAt(0).Messaggio);
				Assert.AreEqual<int>(0, errors.ElementAt(0).Indice);
				Assert.AreEqual<int>(this._idCampo, errors.ElementAt(0).IdCampo);
				
			}

			[TestMethod]
			public void Valore_singolo_match_con_regex_non_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var regex = "[a-zA-Z]+";

				_campo.SetRegex(regex);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore("asd", "asd");

				var validator = new RegexCampoValidator(_campo, 0, "{0} {1}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(0, errors.Count());
			}

			[TestMethod]
			public void Valore_multiplo_non_match_con_regex_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var regex = "[a-zA-Z]+";

				_campo.SetRegex(regex);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore("123", "123");
				_campo.ListaValori.AggiungiValore("123", "123");

				var validator = new RegexCampiValidator(_campo, "{0} {1}", "{0}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(2, errors.Count());
				Assert.AreEqual<string>(etichetta + " " + regex + " 1", errors.ElementAt(0).Messaggio);
				Assert.AreEqual<string>(etichetta + " " + regex + " 2", errors.ElementAt(1).Messaggio);
				Assert.AreEqual<int>(0, errors.ElementAt(0).Indice);
				Assert.AreEqual<int>(1, errors.ElementAt(1).Indice);
				Assert.AreEqual<int>(this._idCampo, errors.ElementAt(0).IdCampo);
				Assert.AreEqual<int>(this._idCampo, errors.ElementAt(1).IdCampo);
			}

			[TestMethod]
			public void Valori_multipli_match_con_regex_non_restituisce_errore()
			{
				var etichetta = "Etichetta";
				var regex = "[a-zA-Z]+";

				_campo.SetRegex(regex);
				_campo.SetEtichetta(etichetta);
				_campo.ListaValori.AggiungiValore("asd", "asd");
				_campo.ListaValori.AggiungiValore("asd", "asd");

				var validator = new RegexCampiValidator(_campo, "{0} {1}", "{0}");

				var errors = validator.GetErroriDiValidazione();

				Assert.AreEqual<int>(0, errors.Count());
			}
		}
	}
}
