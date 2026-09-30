using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
    public interface IOneriWriteInterface
    {
        // void AggiungiOSalvaOnereIntervento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note);
        // void AggiungiOSalvaOnereEndoprocedimento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note);

        void CreaOAggiornaDatiOnere(BaseDtoOfInt32String causale, ProvenienzaOnere provenienza, BaseDtoOfInt32String interventoOEndoOrigine, float importo, string note);

        void CancellaEstremiPagamentoOneriNonPagatiOnline();

        void ImpostaEstremiPagamentoOneriNonPagatiOnline(IdOnere id, ModalitaPagamentoOnereEnum modalitaPagamento, EstremiPagamento estremiPagamento);

        // void ImpostaEstremiPagamentoOnereIntervento(int codiceCausale, int codiceIntervento, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float ImportoPagato);
        // void ImpostaEstremiPagamentoOnereEndo(int codiceCausale, int codiceEndo, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float ImportoPagato);

        void SalvaAttestazioneDiPagamento(int codiceOggetto, string nomeFile, bool firmatoDigitalmente);

        void EliminaAttestazioneDiPagamento();

        void ImpostaDichiarazioneAssenzaOneriDaPagare();

        void RimuoviDichiarazioneAssenzaOneriDaPagare();

        // void EliminaOneriIntervento();
        // void EliminaOneriDaIdEndo(int idEndoprocedimento);
        void EliminaOneriWhereCodiceCausaleNotIn(IEnumerable<IdentificativoOnereSelezionato> listaIdDaEliminare);

        void AvviaPagamentoOneriOnline(string idNuovaOperazione, IEnumerable<OnereFrontoffice> oneriPerPagamentoOnline);

        void AnnullaPagamentiFalliti();

        void AnnullaPagamentiByUniqueId(IEnumerable<string> uniqueIds);

        void AnnullaPagamentiInCorso();

        void PagamentoRiuscito(DateTime dataOraTransazione, string numeroOperazione, string idOrdine, string idTransazione, TipoPagamento tipoPagamento);

        // void AnnullaTuttiIPagamenti();

        /// <summary>
        /// Predispone gli oneri marcati come pronti per pagamento online all'avvio delle operazioni di pagamento
        /// </summary>
        /// <param name="guidService"></param>
        /// <returns>Lista di oneri su cui iniziare il pagamento online</returns>
        IEnumerable<OnereFrontoffice> AvviaOperazioneDiPagamento(IGuidWrapperService guidService);

        void ImpostaRiferimentiNodoPagamenti(string uniqueId, string idPosizioneNodoPagamenti, string iuv);

        void AggiornaStatoPagamentoNativo(string uniqueId, string statoPagamentoNativo);

        void PagamentoRiuscitoDaNodoPagamenti(string uniqueId, string cfEnteCreditore, DateTime dataOraTransazione, TipoPagamento tipoPagamento);

        void PagamentoFallitoDaNodoPagamenti(string uniqueId);
    }
}
