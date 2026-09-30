using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using MovimentiTest.Helpers;
using System.Linq;
using Xunit;

namespace MovimentiTest
{
    public class RiepiloghiSchedeDinamicheDelMovimentoTest : MovimentoFrontofficeTestClass
    {
        private const string IdComune = "E256";
        private const int IdMovimento = 1;

        public override void OnTestInitialize()
        {
            var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);

            this._bus.Send(crea);
        }

        [Fact]
        public void Quando_viene_allegato_un_riepilogo_ad_un_movimento_viene_generato_un_evento_di_tipo_RiepilogoSchedaDinamicaAggiunto()
        {
            var idSchedaDinamica = 100;
            var idAllegato = 1000;
            var nomeFile = "file1.ext";

            var cmd = new AllegaRiepilogoSchedaDinamicaAMovimento(IdComune, IdMovimento, idSchedaDinamica, idAllegato, nomeFile);

            this._bus.Send(cmd);

            var events = this.GetEventiGeneratiNelTest().ToList();

            Assert.Single(events);
            Assert.IsType<RiepilogoSchedaDinamicaAllegatoAlMovimento>(events[0]);

            var ev = (RiepilogoSchedaDinamicaAllegatoAlMovimento)events[0];

            Assert.Equal(IdComune, ev.IdComune);
            Assert.Equal<int>(IdMovimento, ev.IdMovimento);
            Assert.Equal<int>(idSchedaDinamica, ev.IdSchedaDinamica);
            Assert.Equal<int>(idAllegato, ev.IdAllegato);
            Assert.Equal(nomeFile, ev.NomeFile);
        }

        [Fact]
        public void Quando_viene_rimosso_un_riepilogo_da_un_movimento_viene_generato_un_evento_di_tipo_RiepilogoSchedaDinamicaRimosso()
        {
            var idSchedaDinamica = 100;
            var idAllegato = 1000;
            var nomeFile = "file1.ext";

            var cmd1 = new AllegaRiepilogoSchedaDinamicaAMovimento(IdComune, IdMovimento, idSchedaDinamica, idAllegato, nomeFile);

            this._bus.Send(cmd1);

            var cmd2 = new RimuoviRiepilogoSchedaDinamicaDalMovimento(IdComune, IdMovimento, idSchedaDinamica);

            this._bus.Send(cmd2);

            var events = this.GetEventiGeneratiNelTest().Skip(1).ToList();

            Assert.Single(events);
            Assert.IsType<RiepilogoSchedaDinamicaRimossoDalMovimento>(events[0]);

            var ev = (RiepilogoSchedaDinamicaRimossoDalMovimento)events[0];

            Assert.Equal(IdComune, ev.IdComune);
            Assert.Equal<int>(IdMovimento, ev.IdMovimento);
            Assert.Equal<int>(idSchedaDinamica, ev.IdSchedaDinamica);
            Assert.Equal<int>(idAllegato, ev.IdAllegato);
        }

        [Fact]
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

            var events = this.GetEventiGeneratiNelTest().Skip(1).ToList();

            Assert.Equal<int>(2, events.Count);
            Assert.IsType<RiepilogoSchedaDinamicaRimossoDalMovimento>(events[0]);

            var ev1 = (RiepilogoSchedaDinamicaRimossoDalMovimento)events[0];

            Assert.Equal(IdComune, ev1.IdComune);
            Assert.Equal<int>(IdMovimento, ev1.IdMovimento);
            Assert.Equal<int>(idSchedaDinamica, ev1.IdSchedaDinamica);
            Assert.Equal<int>(idAllegato, ev1.IdAllegato);

            var ev2 = (RiepilogoSchedaDinamicaAllegatoAlMovimento)events[1];

            Assert.Equal(IdComune, ev2.IdComune);
            Assert.Equal<int>(IdMovimento, ev2.IdMovimento);
            Assert.Equal<int>(idSchedaDinamica, ev2.IdSchedaDinamica);
            Assert.Equal<int>(idAllegato2, ev2.IdAllegato);
            Assert.Equal(nomeFile, ev2.NomeFile);
        }
    }
}
