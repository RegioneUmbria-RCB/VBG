using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Xunit;
using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Events;
using Init.Sigepro.FrontEnd.GestioneMovimenti;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using MovimentiTest.Helpers;

namespace MovimentiTest
{
    public class CreazioneMovimentoTests : MovimentoFrontofficeTestClass
    {
        [Fact]
        public void Quando_si_crea_un_movimento_viene_generato_un_evento_di_tipo_MovimentoCreato()
        {
            var idComune = "E256";
            var idMovimentoOrigine = 123;
            var idMovimentoDaEffettuare = 456;
            var command = new CreaMovimento(idComune, idMovimentoDaEffettuare, idMovimentoOrigine);
            _bus.Send(command);
            var eventi = this.GetEventiGeneratiNelTest();
            Assert.Single(eventi);
            Assert.IsType<MovimentoCreato>(eventi.ElementAt(0));
            Assert.Equal(idMovimentoOrigine, (eventi.ElementAt(0) as MovimentoCreato).IdMovimentoOrigine);
            Assert.Equal(idMovimentoDaEffettuare, (eventi.ElementAt(0) as MovimentoCreato).IdMovimentoDaEffettuare);
        }
    }
}
