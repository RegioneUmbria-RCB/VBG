using System;
using System.Collections.Generic;
using System.Linq;

namespace VBG.Pagamenti.NodoPagamenti.Attivazione
{
    public class EsitoInserimentoPosizioneDebitoria : IEsitoAttivazionePagamento
    {
        private readonly string _codiceComune;
        private readonly OperazionePosizioniDebitorieResponseType _esitoInserimentoPosizioneDebitoria;

        public EsitoInserimentoPosizioneDebitoria(string codiceComune, OperazionePosizioniDebitorieResponseType esitoInserimentoPosizioneDebitoria)
        {
            this._codiceComune = codiceComune;
            this._esitoInserimentoPosizioneDebitoria = esitoInserimentoPosizioneDebitoria ?? throw new ArgumentNullException(nameof(esitoInserimentoPosizioneDebitoria));
        }

        public bool Esito => this._esitoInserimentoPosizioneDebitoria.esito == EsitoType.OK;

        public string DescrizioneErrore => this._esitoInserimentoPosizioneDebitoria?.messaggio ?? "Dettagli dell'errore non disponibili";

        public string UrlSistemaPagamenti => throw new InvalidOperationException("L'attivazione di una posizione debitoria non prevede un url di pagamento");

        public IEnumerable<IEstremiPosizioneDebitoriaServer> PosizioniAttivate => this.GetEstremiPosizioniDebitorie();

        public HttpMethodEnum HttpMethod => HttpMethodEnum.GET;

        public IEnumerable<HttpPostParameter> PostParameters => Enumerable.Empty<HttpPostParameter>();

        private IEnumerable<IEstremiPosizioneDebitoriaServer> GetEstremiPosizioniDebitorie()
        {
            if (!this.Esito)
            {
                return Enumerable.Empty<IEstremiPosizioneDebitoriaServer>();
            }

            var estremiPosizioni = this._esitoInserimentoPosizioneDebitoria.posizioniInserite.SelectMany(x => x.riferimentoClient.Select(y => new EstremiPosizioneDebitoriaServer(this._codiceComune, y, x.idPosizione, x.IUV, x.uuid)));

            return estremiPosizioni;
        }
    }
}
