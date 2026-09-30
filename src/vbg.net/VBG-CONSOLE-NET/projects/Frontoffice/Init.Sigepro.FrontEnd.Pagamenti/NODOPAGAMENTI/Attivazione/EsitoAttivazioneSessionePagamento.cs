using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Attivazione
{
    public class EsitoAttivazioneSessionePagamento : IEsitoAttivazionePagamento
    {
        public bool Esito { get; }
        public string UrlSistemaPagamenti { get; }
        public string DescrizioneErrore { get; } = string.Empty;

        public IEnumerable<IEstremiPosizioneDebitoria> PosizioniAttivate { get; } = Enumerable.Empty<IEstremiPosizioneDebitoria>();

        public static EsitoAttivazioneSessionePagamento AttivazioneFallita(string messaggio) => new EsitoAttivazioneSessionePagamento(false, messaggio);

        private EsitoAttivazioneSessionePagamento(bool esito, string messaggio)
        {
            this.Esito = esito;
            this.DescrizioneErrore = messaggio;
        }

        public EsitoAttivazioneSessionePagamento(AttivaSessionePagamentoResponseType response, EsitoOperazionePosizioneDebitoriaType riferimentiPosizioneInserita)
        {
            this.Esito = response.esito;

            if (response.esito)
            {
                this.UrlSistemaPagamenti = response.payUrl;
                this.PosizioniAttivate = this.PosizioniAttivate = riferimentiPosizioneInserita.riferimentoClient.Select(riferimentoClient => new EstremiPosizioneDebitoria(riferimentoClient, riferimentiPosizioneInserita.idPosizione, riferimentiPosizioneInserita.IUV)).ToArray();
            }
            else
            {
                this.DescrizioneErrore = response.descEsito;
            }
        }
    }
}
