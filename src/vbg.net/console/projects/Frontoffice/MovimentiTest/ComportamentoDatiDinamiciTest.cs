using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using MovimentiTest.Helpers;

namespace MovimentiTest
{
	[TestClass]
	public class ComportamentoDatiDinamiciTest : MovimentoFrontofficeTestClass
	{
		const string IdComune = "E256";
		const int IdMovimento = 1;
		const string NuoveNote = "Note movimento";

		public override void OnTestInitialize()
		{
			var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);

			this._bus.Send(crea);
		}


		[TestMethod]
		public void Quando_viene_modificato_il_valore_di_un_campo_dinamico_non_esistente_viene_generato_un_evento_di_tipo_ValoreDatoDinamicoAggiunto()
		{
			var idCampoDinamico = 1;
			var indiceMolteplicita = 0;
			var valore = "valore";
			var valoreDecodificato = "valore decodificato";

			var command = new ModificaValoreDatoDinamicoDelMovimento(IdComune, IdMovimento, idCampoDinamico, indiceMolteplicita, valore, valoreDecodificato);

			this._bus.Send(command);

			var eventi = this.GetEventiGeneratiNelTest().ToList();

			Assert.AreEqual<int>(1, eventi.Count);
			Assert.IsInstanceOfType(eventi[0], typeof(ValoreDatoDinamicoAggiuntoAlMovimento));

			var evento = (ValoreDatoDinamicoAggiuntoAlMovimento)eventi[0];

			Assert.AreEqual<string>(IdComune, evento.IdComune);
			Assert.AreEqual<int>(IdMovimento, evento.IdMovimento);
			Assert.AreEqual<int>(idCampoDinamico, evento.IdCampoDinamico);
			Assert.AreEqual<int>(indiceMolteplicita, evento.IndiceMolteplicita);
			Assert.AreEqual<string>(valore, evento.Valore);
			Assert.AreEqual<string>(valoreDecodificato, evento.ValoreDecodificato);
		}

		[TestMethod]
		public void Quando_viene_modificato_il_valore_di_un_campo_dinamico_esistente_viene_generato_un_evento_di_tipo_ValoreDatoDinamicoModificato()
		{
			var idCampoDinamico = 1;
			var indiceMolteplicita = 0;
			var valore1 = "valore";
			var valoreDecodificato1 = "valore decodificato";
			var valore2 = "valore 2";
			var valoreDecodificato2 = "valore decodificato 2";

			var command1 = new ModificaValoreDatoDinamicoDelMovimento(IdComune, IdMovimento, idCampoDinamico, indiceMolteplicita, valore1, valoreDecodificato1);

			this._bus.Send(command1);

			var command2 = new ModificaValoreDatoDinamicoDelMovimento(IdComune, IdMovimento, idCampoDinamico, indiceMolteplicita, valore2, valoreDecodificato2);

			this._bus.Send(command2);

			var eventi = this.GetEventiGeneratiNelTest().ToList();

			Assert.AreEqual<int>(2, eventi.Count);
			Assert.IsInstanceOfType(eventi[1], typeof(ValoreDatoDinamicoDelMovimentoModificato));

			var evento = (ValoreDatoDinamicoDelMovimentoModificato)eventi[1];

			Assert.AreEqual<string>(IdComune, evento.IdComune);
			Assert.AreEqual<int>(IdMovimento, evento.IdMovimento);
			Assert.AreEqual<int>(idCampoDinamico, evento.IdCampoDinamico);
			Assert.AreEqual<int>(indiceMolteplicita, evento.IndiceMolteplicita);
			Assert.AreEqual<string>(valore2, evento.Valore);
			Assert.AreEqual<string>(valoreDecodificato2, evento.ValoreDecodificato);
		}

		[TestMethod]
		public void Quando_vengono_eliminati_i_valori_di_un_campo_viene_generato_un_evento_di_tipo_ValoriCampoDinamicoEliminati()
		{
			var idCampoDinamico = 1;
			var indiceMolteplicita = 0;
			var valore1 = "valore";
			var valoreDecodificato1 = "valore decodificato";
			var valore2 = "valore 2";
			var valoreDecodificato2 = "valore decodificato 2";

			var addCommand1 = new ModificaValoreDatoDinamicoDelMovimento(IdComune, IdMovimento, idCampoDinamico, indiceMolteplicita, valore1, valoreDecodificato1);

			this._bus.Send(addCommand1);

			var addCommand2 = new ModificaValoreDatoDinamicoDelMovimento(IdComune, IdMovimento, idCampoDinamico, indiceMolteplicita, valore2, valoreDecodificato2);

			this._bus.Send(addCommand2);

			var eventi = this.GetEventiGeneratiNelTest().ToList();

			Assert.AreEqual<int>(2, eventi.Count);

			var deleteCommand = new EliminaValoriCampo(IdComune, IdMovimento, idCampoDinamico);

			this._bus.Send(deleteCommand);

			eventi = this.GetEventiGeneratiNelTest().ToList();

			Assert.AreEqual<int>(3, eventi.Count);
			Assert.IsInstanceOfType(eventi.ElementAt(2), typeof(ValoriCampoDinamicoEliminati));

			var evt = (ValoriCampoDinamicoEliminati)eventi.ElementAt(2);

			Assert.AreEqual<string>(IdComune, evt.IdComune);
			Assert.AreEqual<int>(IdMovimento, evt.IdMovimento);
			Assert.AreEqual<int>(idCampoDinamico, evt.IdCampo);
		}

		[TestMethod]
		public void L_eliminazione_di_valori_non_esistenti_non_genera_eventi()
		{
			var idCampoDinamico = 1;

			var deleteCommand = new EliminaValoriCampo(IdComune, IdMovimento, idCampoDinamico);

			var eventi = this.GetEventiGeneratiNelTest().ToList();

			Assert.AreEqual<int>(0, eventi.Count);
		}
	}
}
