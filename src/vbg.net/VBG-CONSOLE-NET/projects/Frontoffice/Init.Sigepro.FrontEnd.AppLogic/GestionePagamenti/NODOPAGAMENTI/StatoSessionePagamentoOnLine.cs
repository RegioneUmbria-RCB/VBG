using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI
{
    public class StatoSessionePagamentoOnLine
    {
        /// <summary>
        /// Lista delle operazioni di pagamento lette
        /// </summary>
        public IEnumerable<OnereConPagamentoInSospeso> Pagamenti;

        /// <summary>
        /// True se esiste almeno un'operazione di pagamento
        /// </summary>
        public bool EsistonoPagamentiInSospeso => Pagamenti.Any();

        /// <summary>
        /// True se esiste almeno un pagamento in stato ATTIVATO_IN_PSP
        /// </summary>
        public bool EsistonoPagamentiAttivatiNonCompletati => Pagamenti.Any(x => x.StatoNativo == StatoPagamentoType.ATTIVATO_IN_PSP.ToString());

        /// <summary>
        /// Stato globale delle operazioni di pagamento selezionate.
        /// Gli stati possibili sono:
        /// - PagamentoIniziato: Almeno un pagamento in stato PagamentoIniziato->si attende l'esito dal nodo di pagamenti
        /// - PagamentoRiuscito: tutti i pagamenti sono in stato PagamentoRiuscito
        /// - PagamentoFallito: tutti i pagamenti sono in stato PagamentoFallito
        /// - PagamentoParziale: oh, shit! alcuni oneri sono pagati e altri no. Spero di non vederlo mai da vivo
        /// </summary>
        public StatoPagamentoOnereEnum StatoGlobale => DecodificaStato();

        public StatoSessionePagamentoOnLine(IEnumerable<OnereConPagamentoInSospeso> pagamentiInSospeso)
        {
            this.Pagamenti = pagamentiInSospeso ?? throw new ArgumentNullException(nameof(pagamentiInSospeso));
        }

        private StatoPagamentoOnereEnum DecodificaStato()
        {
            var numeroOneri = this.Pagamenti.Count();

            if (this.Pagamenti.Where(x => x.Stato == StatoPagamentoOnereEnum.PagamentoIniziato).Any())
            {
                return StatoPagamentoOnereEnum.PagamentoIniziato;
            }

            if (this.Pagamenti.Where(x => x.Stato == StatoPagamentoOnereEnum.PagamentoRiuscito).Count() == numeroOneri)
            {
                return StatoPagamentoOnereEnum.PagamentoRiuscito;
            }

            if (this.Pagamenti.Where(x => x.Stato == StatoPagamentoOnereEnum.PagamentoFallito).Count() == numeroOneri)
            {
                return StatoPagamentoOnereEnum.PagamentoFallito;
            }

            // TODO:
            // Ci sono alcuni oneri il cui pagamento è riuscito e alcuni in cui il pagamento è fallito... cosa faccio?
            return StatoPagamentoOnereEnum.PagamentoParziale;
        }
    }
}
