using System.Linq;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;

namespace MovimentiTest
{
	[TestClass]
	public class SerializzazioneEventiDeiMovimentiTest
	{
		[TestMethod]
		public void Verifica_salvataggio_e_deserializzazione_eventi()
		{
			var typesRegistry = EventTypesRegistry.RegisterEvents()
												  .FromAssembly(typeof(MovimentoCreato).Assembly)
												  .Now();

			var mock = new Mock<IGestioneMovimentiDataContext>();

			mock.Setup(x => x.GetDataStore()).Returns(new GestioneMovimentiDataStore());

			var storageMedium = new JsonEventStream(typesRegistry, mock.Object);

			var testEvent = new MovimentoCreato
			{
				IdComune = "E256",
				IdMovimentoDaEffettuare = 1,
				IdMovimentoOrigine = 2
			};

			storageMedium.Add(testEvent.IdMovimentoDaEffettuare, testEvent);

			var eventStream = storageMedium.GetEventsForAggregate(testEvent.IdMovimentoDaEffettuare);

			Assert.AreEqual<int>(1, eventStream.Count());
			Assert.IsInstanceOfType( eventStream.ElementAt(0), testEvent.GetType());

			var @event = (MovimentoCreato)eventStream.ElementAt(0);

			Assert.AreEqual<string>(testEvent.IdComune, @event.IdComune);
			Assert.AreEqual<int>(testEvent.IdMovimentoDaEffettuare, @event.IdMovimentoDaEffettuare);
			Assert.AreEqual<int>(testEvent.IdMovimentoOrigine, @event.IdMovimentoOrigine);


		}


	}
}
