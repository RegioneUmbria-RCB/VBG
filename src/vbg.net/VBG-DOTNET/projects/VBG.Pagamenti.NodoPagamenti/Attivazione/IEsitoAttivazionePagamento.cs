using System.Collections.Generic;

namespace VBG.Pagamenti.NodoPagamenti.Attivazione
{
    public interface IEsitoAttivazionePagamento
    {
        bool Esito { get; }
        string DescrizioneErrore { get; }
        string UrlSistemaPagamenti { get; }
        IEnumerable<IEstremiPosizioneDebitoriaServer> PosizioniAttivate { get; }
        HttpMethodEnum HttpMethod { get; }
        IEnumerable<HttpPostParameter> PostParameters { get; }
    }
}
