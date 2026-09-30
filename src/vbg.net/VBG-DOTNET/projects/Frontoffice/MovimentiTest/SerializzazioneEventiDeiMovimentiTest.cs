using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Moq;
using System.Linq;
using Xunit;

namespace MovimentiTest
{
    public class SerializzazioneEventiDeiMovimentiTest
    {
        [Fact]
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

            Assert.Single(eventStream);
            Assert.IsType(testEvent.GetType(), eventStream.ElementAt(0));

            var @event = (MovimentoCreato)eventStream.ElementAt(0);

            Assert.Equal(testEvent.IdComune, @event.IdComune);
            Assert.Equal<int>(testEvent.IdMovimentoDaEffettuare, @event.IdMovimentoDaEffettuare);
            Assert.Equal<int>(testEvent.IdMovimentoOrigine, @event.IdMovimentoOrigine);


        }


    }
}
