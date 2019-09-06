using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public interface IOneriWriteInterface
	{
        void AggiungiOSalvaOnereIntervento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note);
        void AggiungiOSalvaOnereEndoprocedimento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note);

        void InizializzaOnere(BaseDtoOfInt32String causale, ProvenienzaOnere provenienza, BaseDtoOfInt32String interventoOEndoOrigine, float importo, string note);

		void CancellaEstremiPagamento();

        void ImpostaEstremiPagamento(IdOnere id, ModalitaPagamentoOnereEnum modalitaPagamento, EstremiPagamento estremiPagamento);

		void ImpostaEstremiPagamentoOnereIntervento(int codiceCausale, int codiceIntervento, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float ImportoPagato);
		void ImpostaEstremiPagamentoOnereEndo(int codiceCausale, int codiceEndo, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float ImportoPagato);

		void SalvaAttestazioneDiPagamento(int codiceOggetto, string nomeFile, bool firmatoDigitalmente);
		void EliminaAttestazioneDiPagamento();
		void ImpostaDichiarazioneAssenzaOneriDaPagare();
		void RimuoviDichiarazioneAssenzaOneriDaPagare();
		void EliminaOneriIntervento();
		void EliminaOneriDaIdEndo(int idEndoprocedimento);
		void EliminaOneriWhereCodiceCausaleNotIn(IEnumerable<IdentificativoOnereSelezionato> listaIdDaEliminare);

        void AvviaPagamentoOneriOnline(string idNuovaOperazione, IEnumerable<OnereFrontoffice> oneriPerPagamentoOnline);

        void AnnullaPagamento(string numeroOperazione);

        void PagamentoRiuscito(DateTime dataOraTransazione, string numeroOperazione, string idOrdine, string idTransazione, TipoPagamento tipoPagamento);

        void AnnullaTuttiIPagamenti();
    }
}
