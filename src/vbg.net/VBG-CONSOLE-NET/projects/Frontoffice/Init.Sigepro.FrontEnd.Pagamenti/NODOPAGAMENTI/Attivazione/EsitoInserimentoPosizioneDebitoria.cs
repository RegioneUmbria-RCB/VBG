using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Attivazione
{
    public class EsitoInserimentoPosizioneDebitoria : IEsitoAttivazionePagamento
    {
        private readonly OperazionePosizioniDebitorieResponseType _esitoInserimentoPosizioneDebitoria;

        public EsitoInserimentoPosizioneDebitoria(OperazionePosizioniDebitorieResponseType esitoInserimentoPosizioneDebitoria)
        {
            this._esitoInserimentoPosizioneDebitoria = esitoInserimentoPosizioneDebitoria ?? throw new ArgumentNullException(nameof(esitoInserimentoPosizioneDebitoria));
        }

        public bool Esito => this._esitoInserimentoPosizioneDebitoria.esito == EsitoType.OK;

        public string DescrizioneErrore => this._esitoInserimentoPosizioneDebitoria?.messaggio ?? "Dettagli dell'errore non disponibili";

        public string UrlSistemaPagamenti => throw new InvalidOperationException("L'attivazione di una posizione debitoria non prevede un url di pagamento");

        public IEnumerable<IEstremiPosizioneDebitoria> PosizioniAttivate => this.GetEstremiPosizioniDebitorie();

        private IEnumerable<EstremiPosizioneDebitoria> GetEstremiPosizioniDebitorie()
        {
            if (!this.Esito)
            {
                return Enumerable.Empty<EstremiPosizioneDebitoria>();
            }

            var estremiPosizioni = this._esitoInserimentoPosizioneDebitoria.posizioniInserite.SelectMany(x => x.riferimentoClient.Select(riferimentoClient => new EstremiPosizioneDebitoria(riferimentoClient, x.idPosizione, x.IUV)));

            return estremiPosizioni;
        }
    }
}
