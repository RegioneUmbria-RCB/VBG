using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri.Sincronizzazione;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public interface IOneriWriteInterface
	{
		void AggiungiOSalvaOnereIntervento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, float importo, float importoPagato, string note);
		void AggiungiOSalvaOnereEndoprocedimento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, float importo, float importoPagato, string note);

		void CancellaEstremiPagamento();
		void ImpostaEstremiPagamentoOnereIntervento(int codiceCausale, int codiceIntervento, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float ImportoPagato);
		void ImpostaEstremiPagamentoOnereEndo(int codiceCausale, int codiceEndo, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float ImportoPagato);

		void SalvaAttestazioneDiPagamento(int codiceOggetto, string nomeFile, bool firmatoDigitalmente);
		void EliminaAttestazioneDiPagamento();
		void ImpostaDichiarazioneAssenzaOneriDaPagare();
		void RimuoviDichiarazioneAssenzaOneriDaPagare();
		void EliminaOneriIntervento();
		void EliminaOneriDaIdEndo(int idEndoprocedimento);
		void EliminaOneriWhereCodiceCausaleNotIn(IEnumerable<IdentificativoOnereSelezionato> listaIdDaEliminare);
	}
}
