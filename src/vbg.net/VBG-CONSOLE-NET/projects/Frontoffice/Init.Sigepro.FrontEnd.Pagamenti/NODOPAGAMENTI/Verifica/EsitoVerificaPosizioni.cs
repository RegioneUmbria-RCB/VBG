using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica
{

    public class EsitoVerificaPosizioni : IEsitoVerificaPosizioni
    {
        public StatoPagamentoEnum StatoGlobale { get; }
        public IEnumerable<IStatoPagamentoOnere> StatoOneri { get; }
        public string CodiceFiscaleEnteCreditore { get; }

        public EsitoVerificaPosizioni(string codiceFiscaleEnteCreditore, IEnumerable<IStatoPagamentoOnere> statoOneri)
        {
            this.CodiceFiscaleEnteCreditore = codiceFiscaleEnteCreditore;
            this.StatoOneri = statoOneri;
            this.StatoGlobale = DecodificaStato();
        }

        private StatoPagamentoEnum DecodificaStato()
        {
            var numeroOneri = this.StatoOneri.Count();

            if (this.StatoOneri.Where(x => x.Stato == StatoPagamentoEnum.PagamentoInCorso).Any())
            {
                return StatoPagamentoEnum.PagamentoInCorso;
            }

            if (this.StatoOneri.Where(x => x.Stato == StatoPagamentoEnum.PagamentoRiuscito).Count() == numeroOneri)
            {
                return StatoPagamentoEnum.PagamentoRiuscito;
            }

            if (this.StatoOneri.Where(x => x.Stato == StatoPagamentoEnum.PagamentoFallito).Count() == numeroOneri)
            {
                return StatoPagamentoEnum.PagamentoFallito;
            }

            return StatoPagamentoEnum.PagamentoRiuscitoParzialmente;
        }
    }
}
