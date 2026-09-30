using System;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica
{
    public class StatoPagamentoOnere : IStatoPagamentoOnere
    {
        public StatoPagamentoEnum Stato { get; }
        public DateTime? DataOraPagamento { get; }
        public string IdPosizione { get; }
        public string[] RiferimentiClient { get; }
        public string StatoPagamentoNativo { get; }

        public StatoPagamentoOnere(StatoPosizioneType posizione)
        {
            this.Stato = this.TraduciStato(posizione.stato);
            this.StatoPagamentoNativo = posizione.stato.ToString();
            this.IdPosizione = posizione.idPosizione;
            this.RiferimentiClient = posizione.riferimentoClient;

            if (this.Stato == StatoPagamentoEnum.PagamentoRiuscito)
            {
                this.DataOraPagamento = posizione.datiPagamento.dataOraPagamento;
            }
        }

        private StatoPagamentoEnum TraduciStato(StatoPagamentoType stato)
        {
            switch (stato)
            {
                case StatoPagamentoType.NOTIFICATO_DA_PSP:
                case StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO:
                case StatoPagamentoType.RENDICONTATO_DA_IC:
                    return StatoPagamentoEnum.PagamentoRiuscito;

                case StatoPagamentoType.ANNULLATO:
                case StatoPagamentoType.NON_ACQUISITO:
                    return StatoPagamentoEnum.PagamentoFallito;

                default:
                    return StatoPagamentoEnum.PagamentoInCorso;
            }
        }

    }
}
