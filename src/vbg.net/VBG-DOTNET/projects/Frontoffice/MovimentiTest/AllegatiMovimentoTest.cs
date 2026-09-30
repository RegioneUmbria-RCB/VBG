using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using Xunit;
using MovimentiTest.Helpers;
using System.Linq;

namespace MovimentiTest
{
    public class AllegatiMovimentoTest : MovimentoFrontofficeTestClass
    {
        const string IdComune = "E256";
        const int IdMovimento = 1;

        public override void OnTestInitialize()
        {
            var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);
            _bus.Send(crea);
        }

        [Fact]
        public void Quando_viene_aggiunto_un_allegato_viene_generato_un_evento_di_tipo_AllegatoAggiuntoAlMovimento()
        {
            var idAllegato = 1;
            var nomeFile = "nomefile.ext";
            var descrizione = "descrizione";

            var cmd = new AggiungiAllegatoAlMovimento(IdComune, IdMovimento, idAllegato, nomeFile, descrizione);
            _bus.Send(cmd);

            var eventi = GetEventiGeneratiNelTest().ToList();

            Assert.Single(eventi);
            Assert.IsType<AllegatoAggiuntoAlMovimento>(eventi[0]);

            var evt = (AllegatoAggiuntoAlMovimento)eventi[0];
            Assert.Equal(IdComune, evt.IdComune);
            Assert.Equal(IdMovimento, evt.IdMovimento);
            Assert.Equal(idAllegato, evt.IdAllegato);
            Assert.Equal(nomeFile, evt.NomeFile);
            Assert.Equal(descrizione, evt.Descrizione);
        }

        [Fact]
        public void Quando_viene_rimosso_un_allegato_viene_generato_un_evento_di_tipo_AllegatoRimossoDalMovimento()
        {
            var idAllegato = 1000;
            var nomeFile = "file1.ext";
            var descrizione = "descrizione";

            var cmd1 = new AggiungiAllegatoAlMovimento(IdComune, IdMovimento, idAllegato, nomeFile, descrizione);
            _bus.Send(cmd1);

            var cmd2 = new RimuoviAllegatoDalMovimento(IdComune, IdMovimento, idAllegato);
            _bus.Send(cmd2);

            var events = GetEventiGeneratiNelTest().Skip(1).ToList();

            Assert.Single(events);
            Assert.IsType<AllegatoRimossoDalMovimento>(events[0]);

            var ev = (AllegatoRimossoDalMovimento)events[0];
            Assert.Equal(IdComune, ev.IdComune);
            Assert.Equal(IdMovimento, ev.IdMovimento);
            Assert.Equal(idAllegato, ev.IdAllegato);
        }
    }
}
