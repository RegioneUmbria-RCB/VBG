using System.Collections.Generic;
using System.Linq;

namespace VBG.Pagamenti.NodoPagamenti.Attivazione
{
    public class EsitoAttivazioneSessionePagamento : IEsitoAttivazionePagamento
    {
        public bool Esito { get; }
        public string UrlSistemaPagamenti { get; }
        public string DescrizioneErrore { get; } = string.Empty;

        public IEnumerable<IEstremiPosizioneDebitoriaServer> PosizioniAttivate { get; } = Enumerable.Empty<IEstremiPosizioneDebitoriaServer>();

        public HttpMethodEnum HttpMethod => HttpMethodEnum.GET;

        public IEnumerable<HttpPostParameter> PostParameters => Enumerable.Empty<HttpPostParameter>();

        public static EsitoAttivazioneSessionePagamento AttivazioneFallita(string messaggio) => new EsitoAttivazioneSessionePagamento(false, messaggio);

        private EsitoAttivazioneSessionePagamento(bool esito, string messaggio)
        {
            this.Esito = esito;
            this.DescrizioneErrore = messaggio;
        }

        public EsitoAttivazioneSessionePagamento(string codiceComune, AttivaSessionePagamentoResponseType response, EsitoOperazionePosizioneDebitoriaType riferimentiPosizioneInserita)
        {
            this.Esito = response.esito;

            if (response.esito)
            {
                this.UrlSistemaPagamenti = response.payUrl;
                this.PosizioniAttivate = riferimentiPosizioneInserita.riferimentoClient.Select(riferimentoClient => new EstremiPosizioneDebitoriaServer(codiceComune, riferimentoClient, riferimentiPosizioneInserita.idPosizione, riferimentiPosizioneInserita.IUV, riferimentiPosizioneInserita.uuid)).ToArray();
            }
            else
            {
                this.DescrizioneErrore = response.descEsito;
            }
        }
    }
}
