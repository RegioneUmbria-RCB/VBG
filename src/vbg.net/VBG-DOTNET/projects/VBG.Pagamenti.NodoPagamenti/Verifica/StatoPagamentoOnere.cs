using System;
using System.Linq;

namespace VBG.Pagamenti.NodoPagamenti.Verifica
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
            this.Stato = TraduciStato(posizione.stato);
            this.StatoPagamentoNativo = posizione.stato.ToString();
            this.IdPosizione = posizione.idPosizione;
            this.RiferimentiClient = posizione.riferimentoClient;

            if (this.Stato == StatoPagamentoEnum.PagamentoRiuscito)
            {
                this.DataOraPagamento = posizione.datiPagamento?.dataOraPagamento ?? this.CalcolaDataFallback(posizione);
            }
        }

        private DateTime CalcolaDataFallback(StatoPosizioneType posizione)
        {
            if ((posizione.cronologiaStatiPosizione?.Length ?? 0) == 0)
            {
                return DateTime.Now;
            }

            return posizione.cronologiaStatiPosizione.OrderByDescending(x => x.dataStato).First()?.dataStato ?? DateTime.Now;
        }

        public static StatoPagamentoEnum TraduciStato(string stato)
        {
            if (Enum.TryParse<StatoPagamentoType>(stato, out var statoEnum))
            {
                return TraduciStato(statoEnum);
            }

            return StatoPagamentoEnum.StatoSconosciuto;
        }

        public static StatoPagamentoEnum TraduciStato(StatoPagamentoType stato)
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
