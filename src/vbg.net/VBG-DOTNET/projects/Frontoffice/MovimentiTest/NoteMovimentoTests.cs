using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using MovimentiTest.Helpers;
using System.Linq;
using Xunit;

namespace MovimentiTest
{
    /// <summary>
    /// Summary description for NoteMovimento
    /// </summary>
    public class NoteMovimentoTests : MovimentoFrontofficeTestClass
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
        public void Quando_una_nota_viene_modificata_viene_generato_un_evento_di_tipo_NoteMovimentoModificate()
        {
            var cmd = new ModificaNoteMovimento(IdComune, IdMovimento, NuoveNote);
            this._bus.Send(cmd);
            var eventi = this.GetEventiGeneratiNelTest().ToList();
            Assert.Single(eventi);
            Assert.IsType<NoteMovimentoModificate>(eventi[0]);
            Assert.Equal(IdComune, (eventi[0] as NoteMovimentoModificate).IdComune);
            Assert.Equal(IdMovimento, (eventi[0] as NoteMovimentoModificate).IdMovimento);
            Assert.Equal(NuoveNote, (eventi[0] as NoteMovimentoModificate).TestoNote);
        }
    }
}
