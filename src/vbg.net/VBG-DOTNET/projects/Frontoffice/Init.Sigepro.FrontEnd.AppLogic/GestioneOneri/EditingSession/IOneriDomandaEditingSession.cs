using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri.EditingSession
{

    public interface IOneriDomandaEditingSession
    {
        string CodiceComune { get; }
        ModelloPagamentoEnum ModelloPagamento { get; }
        PresentazioneIstanzaDataKey DataKey { get; }
        IOneriReadInterface ReadInterface { get; }

        void LegacySetValoreDatoExtra(string chiave, string valore);

        void LegacyAvviaPagamentoOneriOnline(string numeroOperazione, IEnumerable<OnereFrontoffice> oneri);

        void AggiornaStatoPagamentoNativo(string uniqueId, string statoPagamentoNativo);
        void AnnullaPagamenti();
        void AnnullaPagamentiByUniqueId(IEnumerable<string> uniqueIds);
        IEnumerable<OnereFrontoffice> AvviaOperazioneDiPagamento(IGuidWrapperService guidWrapperService, ModelloPagamentoEnum modelloPagamento);
        void ImpostaRiferimentiNodoPagamenti(string uniqueId, string idPosizioneDebitoria, string IUV);
        bool IsPagamentoAvviato { get; }
        bool RichiedePagamentoOnLine { get; }
        string DescrizioneIntervento { get; }
        [Obsolete]
        IDomandaOnlineReadInterface LegacyFullReadInterface { get; }

        void PagamentoFallitoDaNodoPagamenti(string uniqueId);
        void PagamentoRiuscitoDaNodoPagamenti(string uniqueId, string codiceFiscaleEnteCreditore, DateTime dataOraPagamento, TipoPagamento tipoPagamento);
        void TerminaSessioneModifica();
        IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoFallito();
        IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoAvviato();
        [Obsolete]
        void LegacyPagamentoRiuscito(DateTime dataOraTransazione, string rifPraticaEsterna, string iuv, string idTransazione, TipoPagamento tipoPagamento);
        [Obsolete]
        T LegacyGetDatoExtra<T>(string chiave) where T : class;
    }
}
