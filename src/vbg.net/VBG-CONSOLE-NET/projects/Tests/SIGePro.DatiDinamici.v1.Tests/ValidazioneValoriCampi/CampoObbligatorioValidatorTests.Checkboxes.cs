using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;
using Init.SIGePro.DatiDinamici.WebControls;

namespace SIGePro.DatiDinamici.v1.Tests.ValidazioneValoriCampi
{
	public partial class CampoObbligatorioValidatorTests
	{
		[TestClass]
		public class CheckboxesValidationTests
		{
			CampoDinamicoStub _campo;
			string _etichetta;
			string _valoreTrue = "1";
			string _valoreFalse = "0";

			[TestInitialize]
			public void Initialize()
			{
				this._etichetta = "Etichetta";
				this._campo = new CampoDinamicoStub();
				this._campo.SetTipoCampo(TipoControlloEnum.Checkbox);
				this._campo.SetEtichetta(this._etichetta);
				this._campo.AddProprietaControllo("ValoreTrue", this._valoreTrue);
				this._campo.AddProprietaControllo("ValoreFalse", this._valoreFalse);
			}

			[TestMethod]
			public void Valori_singoli_checkbox_obbligatoria_non_selezionata_restituisce_errore()
			{
				this._campo.SetObbligatorio(true);
				this._campo.ListaValori.AggiungiValore(this._valoreFalse, this._valoreFalse);

				var risultato = new CheckboxValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(1, risultato.Count());
				Assert.AreEqual<string>(this._etichetta, risultato.ElementAt(0).Messaggio);
				Assert.AreEqual<int>(0, risultato.ElementAt(0).Indice);
			}

			[TestMethod]
			public void Valori_singoli_checkbox_non_obbligatoria_non_selezionata_non_restituisce_errore()
			{
				this._campo.SetObbligatorio(false);
				this._campo.ListaValori.AggiungiValore(this._valoreFalse, this._valoreFalse);

				var risultato = new CheckboxValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, risultato.Count());
			}

			[TestMethod]
			public void Valori_singoli_checkbox_obbligatoria_selezionata_non_restituisce_errore()
			{
				this._campo.SetObbligatorio(true);
				this._campo.ListaValori.AggiungiValore(this._valoreTrue, this._valoreTrue);

				var risultato = new CheckboxValidator(this._campo, 0, "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, risultato.Count());
			}

			[TestMethod]
			public void Valori_multipli_checkbox_obbligatorie_non_selezionate_restituisce_errore()
			{
				this._campo.SetObbligatorio(true);
				this._campo.ListaValori.AggiungiValore(this._valoreFalse, this._valoreFalse);
				this._campo.ListaValori.AggiungiValore(this._valoreFalse, this._valoreFalse);

				var risultato = new CheckboxesObbligatorieValidator(this._campo, "{0}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(2, risultato.Count());
				Assert.AreEqual<string>(this._etichetta + " 1", risultato.ElementAt(0).Messaggio);
				Assert.AreEqual<string>(this._etichetta + " 2", risultato.ElementAt(1).Messaggio);
				Assert.AreEqual<int>(0, risultato.ElementAt(0).Indice);
				Assert.AreEqual<int>(1, risultato.ElementAt(1).Indice);				
			}

			[TestMethod]
			public void Valori_multipli_checkbox_obbligatorie_selezionate_non_restituisce_errore()
			{
				this._campo.SetObbligatorio(true);
				this._campo.ListaValori.AggiungiValore(this._valoreTrue, this._valoreTrue);
				this._campo.ListaValori.AggiungiValore(this._valoreTrue, this._valoreTrue);

				var risultato = new CheckboxesObbligatorieValidator(this._campo, "{0}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, risultato.Count());
			}

			[TestMethod]
			public void Valori_multipli_checkbox_non_obbligatorie_selezionate_non_restituisce_errore()
			{
				this._campo.SetObbligatorio(false);
				this._campo.ListaValori.AggiungiValore(this._valoreFalse, this._valoreFalse);
				this._campo.ListaValori.AggiungiValore(this._valoreFalse, this._valoreFalse);

				var risultato = new CheckboxesObbligatorieValidator(this._campo, "{0}", "{0}").GetErroriDiValidazione();

				Assert.AreEqual<int>(0, risultato.Count());
			}
		}
	}
}
