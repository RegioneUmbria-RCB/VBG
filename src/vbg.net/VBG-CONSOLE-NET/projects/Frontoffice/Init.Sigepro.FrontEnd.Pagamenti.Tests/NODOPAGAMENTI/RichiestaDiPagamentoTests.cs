using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.Pagamenti.Tests.Mocks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti.Tests.NODOPAGAMENTI
{
    [TestClass]
    public class RichiestaDiPagamentoTests
    {
        // RichiestaDiPagamento(RiferimentiDomanda riferimentiDomanda, RiferimentiOperazioneNodoPagamenti riferimentiOperazione, RiferimentiUtenteNodoPagamenti riferimentiUtente)
        RiferimentiUtenteNodoPagamenti utenteFarlocco = new RiferimentiUtenteNodoPagamenti("nome", "cognome", "cfpi", "comune", "via", "provincia", "cap", "email");
        CausaliDaPagare causaliFarlocche = new CausaliDaPagare(Enumerable.Empty<OnereNodoPagamentiDTO>());
        RiferimentiDomanda riferimentiDomandaFarlocchi = new RiferimentiDomanda(new MockRiferimentiDomanda(), 0);

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void Costruttore_riferimentiDomanda_non_puo_essere_null()
        {
            new RichiestaDiPagamento(null, utenteFarlocco, causaliFarlocche, true);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void Costruttore_riferimentiOperazione_non_puo_essere_null()
        {
            new RichiestaDiPagamento(riferimentiDomandaFarlocchi, utenteFarlocco, null, true);
        }

        [TestMethod]
        [ExpectedException(typeof(ArgumentNullException))]
        public void Costruttore_riferimentiUtente_non_puo_essere_null()
        {
            new RichiestaDiPagamento(riferimentiDomandaFarlocchi, null, causaliFarlocche, true);
        }
    }
}
