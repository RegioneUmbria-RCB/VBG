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
	public class RiepiloghiSchedeDinamicheDelMovimentoTest : MovimentoFrontofficeTestClass
	{
		const string IdComune = "E256";
		const int IdMovimento = 1;

		public override void OnTestInitialize()
		{
			var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);

			this._bus.Send(crea);
		}

		[TestMethod]
		public void Quando_viene_allegato_un_riepilogo_ad_un_movimento_viene_generato_un_evento_di_tipo_RiepilogoSchedaDinamicaAggiunto()
		{
			var idSchedaDinamica = 100;
			var idAllegato = 1000;
			var nomeFile = "file1.ext";

			var cmd = new AllegaRiepilogoSchedaDinamicaAMovimento( IdComune, IdMovimento, idSchedaDinamica, idAllegato , nomeFile );

			this._bus.Send( cmd );

			var events = GetEventiGeneratiNelTest().ToList();

			Assert.AreEqual<int>( 1 , events.Count );
			Assert.IsInstanceOfType( events[0] , typeof(RiepilogoSchedaDinamicaAllegatoAlMovimento));

			var ev = (RiepilogoSchedaDinamicaAllegatoAlMovimento)events[0];

			Assert.AreEqual<string>(IdComune, ev.IdComune);
			Assert.AreEqual<int>(IdMovimento, ev.IdMovimento);
			Assert.AreEqual<int>(idSchedaDinamica, ev.IdSchedaDinamica);
			Assert.AreEqual<int>(idAllegato, ev.IdAllegato);
			Assert.AreEqual<string>(nomeFile, ev.NomeFile);
		}

		[TestMethod]
		public void Quando_viene_rimosso_un_riepilogo_da_un_movimento_viene_generato_un_evento_di_tipo_RiepilogoSchedaDinamicaRimosso()
		{
			var idSchedaDinamica = 100;
			var idAllegato = 1000;
			var nomeFile = "file1.ext";

			var cmd1 = new AllegaRiepilogoSchedaDinamicaAMovimento(IdComune, IdMovimento, idSchedaDinamica, idAllegato, nomeFile);

			this._bus.Send(cmd1);

			var cmd2 = new RimuoviRiepilogoSchedaDinamicaDalMovimento(IdComune, IdMovimento, idSchedaDinamica);

			this._bus.Send(cmd2);

			var events = GetEventiGeneratiNelTest().Skip(1).ToList();

			Assert.AreEqual<int>(1, events.Count);
			Assert.IsInstanceOfType(events[0], typeof(RiepilogoSchedaDinamicaRimossoDalMovimento));

			var ev = (RiepilogoSchedaDinamicaRimossoDalMovimento)events[0];

			Assert.AreEqual<string>(IdComune, ev.IdComune);
			Assert.AreEqual<int>(IdMovimento, ev.IdMovimento);
			Assert.AreEqual<int>(idSchedaDinamica, ev.IdSchedaDinamica);
			Assert.AreEqual<int>(idAllegato, ev.IdAllegato);
		}

		[TestMethod]
		public void Quando_viene_aggiunto_un_riepilogo_ad_una_scheda_che_ha_gia_un_riepilogo_il_vecchio_riepilogo_viene_eliminato_ed_il_nuovo_aggiunto()
		{
			var idSchedaDinamica = 100;
			var idAllegato = 1000;
			var idAllegato2 = 1001;
			var nomeFile = "file1.ext";

			var cmd1 = new AllegaRiepilogoSchedaDinamicaAMovimento(IdComune, IdMovimento, idSchedaDinamica, idAllegato, nomeFile);

			this._bus.Send(cmd1);

			var cmd2 = new AllegaRiepilogoSchedaDinamicaAMovimento(IdComune, IdMovimento, idSchedaDinamica, idAllegato2, nomeFile);

			this._bus.Send(cmd2);

			var events = GetEventiGeneratiNelTest().Skip(1).ToList();

			Assert.AreEqual<int>(2, events.Count);
			Assert.IsInstanceOfType(events[0], typeof(RiepilogoSchedaDinamicaRimossoDalMovimento));

			var ev1 = (RiepilogoSchedaDinamicaRimossoDalMovimento)events[0];

			Assert.AreEqual<string>(IdComune, ev1.IdComune);
			Assert.AreEqual<int>(IdMovimento, ev1.IdMovimento);
			Assert.AreEqual<int>(idSchedaDinamica, ev1.IdSchedaDinamica);
			Assert.AreEqual<int>(idAllegato, ev1.IdAllegato);

			var ev2 = (RiepilogoSchedaDinamicaAllegatoAlMovimento)events[1];

			Assert.AreEqual<string>(IdComune, ev2.IdComune);
			Assert.AreEqual<int>(IdMovimento, ev2.IdMovimento);
			Assert.AreEqual<int>(idSchedaDinamica, ev2.IdSchedaDinamica);
			Assert.AreEqual<int>(idAllegato2, ev2.IdAllegato);
			Assert.AreEqual<string>(nomeFile, ev2.NomeFile);
		}
	}
}
