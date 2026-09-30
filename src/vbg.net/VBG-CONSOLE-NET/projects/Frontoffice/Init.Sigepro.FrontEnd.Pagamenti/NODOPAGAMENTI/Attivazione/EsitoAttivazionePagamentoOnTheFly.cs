using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Attivazione
{
    public class EsitoAttivazionePagamentoOnTheFly : IEsitoAttivazionePagamento
    {
        public bool Esito { get; } = false;
        public string DescrizioneErrore { get; } = string.Empty;
        public string UrlSistemaPagamenti { get; } = String.Empty;

        public IEnumerable<IEstremiPosizioneDebitoria> PosizioniAttivate { get; } = Enumerable.Empty<IEstremiPosizioneDebitoria>();

        internal EsitoAttivazionePagamentoOnTheFly(AttivaPagamentoOnTheFlyResponseType response)
        {
            this.Esito = response.sessionePagamento != null ? response.sessionePagamento.esito : response.posizioneInserita.First().esito;

            if (this.Esito)
            {
                this.UrlSistemaPagamenti = response.sessionePagamento.payUrl;

                // una posizione inserita potrebbe avere più riferimenti client
                this.PosizioniAttivate = response.posizioneInserita.SelectMany(x => x.riferimentoClient.Select(riferimentoClient => new EstremiPosizioneDebitoria(riferimentoClient, x.idPosizione, x.IUV)));
            }
            else
            {
                var errori = response.posizioneInserita.Where(x => !String.IsNullOrEmpty(x.messaggio)).Select(x => x.messaggio);

                this.DescrizioneErrore = String.Join("\n", errori.ToArray());
            }
        }

    }
}
