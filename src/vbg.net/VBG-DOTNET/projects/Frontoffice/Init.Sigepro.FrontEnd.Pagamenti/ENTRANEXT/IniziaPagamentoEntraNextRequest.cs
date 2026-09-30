using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class IniziaPagamentoEntraNextRequest
    {
        public readonly RiferimentiDomandaEntranext RiferimentiDomanda;
        public readonly RiferimentiUtenteEntraNext RiferimentiUtente;
        public readonly RiferimentiOperazioneEntraNext RiferimentiOperazione;

        public IniziaPagamentoEntraNextRequest(RiferimentiDomandaEntranext riferimentiDomanda, RiferimentiUtenteEntraNext riferimentiUtente, RiferimentiOperazioneEntraNext riferimentiOperazione)
        {
            this.RiferimentiDomanda = riferimentiDomanda;
            this.RiferimentiUtente = riferimentiUtente;
            this.RiferimentiOperazione = riferimentiOperazione;
        }
    }
}
