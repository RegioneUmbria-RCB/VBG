using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using MovimentiTest.Helpers;
using System.Linq;
using Xunit;

namespace MovimentiTest
{
    public class ComportamentoDatiDinamiciTest : MovimentoFrontofficeTestClass
    {
        private const string IdComune = "E256";
        private const int IdMovimento = 1;
        private const string NuoveNote = "Note movimento";

        public override void OnTestInitialize()
        {
            var crea = new CreaMovimento(IdComune, IdMovimento, IdMovimento);
            this._bus.Send(crea);
        }

        [Fact]
        public void Quando_viene_modificato_il_valore_di_un_campo_dinamico_non_esistente_viene_generato_un_evento_di_tipo_ValoreDatoDinamicoAggiunto()
        {
            var idCampoDinamico = 1;
            var indiceMolteplicita = 0;
            var valore = "valore";
            var valoreDecodificato = "valore decodificato";

            var command = new ModificaValoreDatoDinamicoDelMovimento(IdComune, IdMovimento, idCampoDinamico, indiceMolteplicita, valore, valoreDecodificato);

            this._bus.Send(command);

            var eventi = this.GetEventiGeneratiNelTest().ToList();

            Assert.Single(eventi);
            Assert.IsType<ValoreDatoDinamicoAggiuntoAlMovimento>(eventi[0]);

            var evento = (ValoreDatoDinamicoAggiuntoAlMovimento)eventi[0];

            Assert.Equal(IdComune, evento.IdComune);
            Assert.Equal(IdMovimento, evento.IdMovimento);
            Assert.Equal(idCampoDinamico, evento.IdCampoDinamico);
            Assert.Equal(indiceMolteplicita, evento.IndiceMolteplicita);
            Assert.Equal(valore, evento.Valore);
            Assert.Equal(valoreDecodificato, evento.ValoreDecodificato);
        }

        [Fact]
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

            Assert.Equal(2, eventi.Count);
            Assert.IsType<ValoreDatoDinamicoDelMovimentoModificato>(eventi[1]);

            var evento = (ValoreDatoDinamicoDelMovimentoModificato)eventi[1];

            Assert.Equal(IdComune, evento.IdComune);
            Assert.Equal(IdMovimento, evento.IdMovimento);
            Assert.Equal(idCampoDinamico, evento.IdCampoDinamico);
            Assert.Equal(indiceMolteplicita, evento.IndiceMolteplicita);
            Assert.Equal(valore2, evento.Valore);
            Assert.Equal(valoreDecodificato2, evento.ValoreDecodificato);
        }

        [Fact]
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

            Assert.Equal(2, eventi.Count);

            var deleteCommand = new EliminaValoriCampo(IdComune, IdMovimento, idCampoDinamico);

            this._bus.Send(deleteCommand);

            eventi = this.GetEventiGeneratiNelTest().ToList();

            Assert.Equal(3, eventi.Count);
            Assert.IsType<ValoriCampoDinamicoEliminati>(eventi.ElementAt(2));

            var evt = (ValoriCampoDinamicoEliminati)eventi.ElementAt(2);

            Assert.Equal(IdComune, evt.IdComune);
            Assert.Equal(IdMovimento, evt.IdMovimento);
            Assert.Equal(idCampoDinamico, evt.IdCampo);
        }

        [Fact]
        public void L_eliminazione_di_valori_non_esistenti_non_genera_eventi()
        {
            var idCampoDinamico = 1;

            var deleteCommand = new EliminaValoriCampo(IdComune, IdMovimento, idCampoDinamico);

            var eventi = this.GetEventiGeneratiNelTest().ToList();

            Assert.Empty(eventi);
        }
    }
}
