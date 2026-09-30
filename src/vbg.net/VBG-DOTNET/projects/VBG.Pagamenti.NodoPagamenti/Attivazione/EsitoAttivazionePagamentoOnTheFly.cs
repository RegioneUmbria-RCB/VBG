using System;
using System.Collections.Generic;
using System.Linq;

namespace VBG.Pagamenti.NodoPagamenti.Attivazione
{

    public class EsitoAttivazionePagamentoOnTheFly : IEsitoAttivazionePagamento
    {
        public bool Esito { get; } = false;
        public string DescrizioneErrore { get; } = string.Empty;
        public string UrlSistemaPagamenti { get; } = String.Empty;
        public HttpMethodEnum HttpMethod { get; } = HttpMethodEnum.GET;
        public IEnumerable<HttpPostParameter> PostParameters { get; } = Enumerable.Empty<HttpPostParameter>();

        public IEnumerable<IEstremiPosizioneDebitoriaServer> PosizioniAttivate { get; } = Enumerable.Empty<IEstremiPosizioneDebitoriaServer>();

        internal EsitoAttivazionePagamentoOnTheFly(string codiceComune, AttivaPagamentoOnTheFlyResponseType response)
        {
            this.Esito = response.sessionePagamento != null ? response.sessionePagamento.esito : response.posizioneInserita.First().esito;

            if (this.Esito)
            {
                var method = response.sessionePagamento.httpMethodRequiredSpecified && response.sessionePagamento.httpMethodRequired == HttpMethodType.POST ?
                            HttpMethodEnum.POST :
                            HttpMethodEnum.GET;

                this.HttpMethod = method;
                this.UrlSistemaPagamenti = response.sessionePagamento.payUrl;

                // una posizione inserita potrebbe avere più riferimenti client
                this.PosizioniAttivate = response.posizioneInserita.SelectMany(x => x.riferimentoClient.Select(riferimentoClient => new EstremiPosizioneDebitoriaServer(codiceComune, riferimentoClient, x.idPosizione, x.IUV, x.uuid)));

                if (this.HttpMethod == HttpMethodEnum.POST)
                {
                    this.PostParameters = response.sessionePagamento.formParams?.param.Select(x => new HttpPostParameter(x.paramName, x.value)) ?? Enumerable.Empty<HttpPostParameter>();
                }
            }
            else
            {
                var errori = response.posizioneInserita.Where(x => !String.IsNullOrEmpty(x.messaggio)).Select(x => x.messaggio);

                this.DescrizioneErrore = String.Join("\n", errori.ToArray());
            }

        }

    }
}
