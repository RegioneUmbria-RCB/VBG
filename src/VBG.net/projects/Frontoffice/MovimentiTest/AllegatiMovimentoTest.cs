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
	public class AllegatiMovimentoTest : MovimentoFrontofficeTestClass
	{
		const string IdComune = "E256";
		const int IdMovimento = 1;

		public override void OnTestInitialize()
		{
			var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);

			this._bus.Send(crea);
		}

		[TestMethod]
		public void Quando_viene_aggiunto_un_allegato_viene_generato_un_evento_di_tipo_AllegatoAggiuntoAlMovimento()
		{
			var idAllegato = 1;
			var nomeFile = "nomefile.ext";
			var descrizione = "descrizione";

			var cmd = new AggiungiAllegatoAlMovimento(IdComune, IdMovimento, idAllegato, nomeFile, descrizione);

			this._bus.Send(cmd);

			var eventi = GetEventiGeneratiNelTest().ToList();

			Assert.AreEqual<int>(1, eventi.Count);
			Assert.IsInstanceOfType(eventi[0], typeof(AllegatoAggiuntoAlMovimento));

			var evt = (AllegatoAggiuntoAlMovimento)eventi[0];

			Assert.AreEqual<string>(IdComune, evt.IdComune);
			Assert.AreEqual<int>(IdMovimento, evt.IdMovimento);
			Assert.AreEqual<int>(idAllegato, evt.IdAllegato);
			Assert.AreEqual<string>(nomeFile, evt.NomeFile);
			Assert.AreEqual<string>(descrizione, evt.Descrizione);
			
		}

		[TestMethod]
		public void Quando_viene_rimosso_un_allegato_viene_generato_un_evento_di_tipo_AllegatoRimossoDalMovimento()
		{
			var idAllegato = 1000;
			var nomeFile = "file1.ext";
			var descrizione = "descrizione";

			var cmd1 = new AggiungiAllegatoAlMovimento(IdComune, IdMovimento, idAllegato, nomeFile, descrizione);

			this._bus.Send(cmd1);

			var cmd2 = new RimuoviAllegatoDalMovimento(IdComune, IdMovimento, idAllegato);

			this._bus.Send(cmd2);

			var events = GetEventiGeneratiNelTest().Skip(1).ToList();

			Assert.AreEqual<int>(1, events.Count);
			Assert.IsInstanceOfType(events[0], typeof(AllegatoRimossoDalMovimento));

			var ev = (AllegatoRimossoDalMovimento)events[0];

			Assert.AreEqual<string>(IdComune, ev.IdComune);
			Assert.AreEqual<int>(IdMovimento, ev.IdMovimento);
			Assert.AreEqual<int>(idAllegato, ev.IdAllegato);
		}
	}
}
