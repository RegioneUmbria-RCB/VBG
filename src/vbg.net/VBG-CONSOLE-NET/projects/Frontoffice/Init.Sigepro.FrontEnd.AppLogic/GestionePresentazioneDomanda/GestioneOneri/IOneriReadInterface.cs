using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public interface IOneriReadInterface
	{
		/// <summary>
		/// Restituisce la lista di oneri le cui causali sono legate all'intervento
		/// </summary>
		/// <returns></returns>
		IEnumerable<OnereFrontoffice> OneriIntervento { get; }

		/// <summary>
		/// Restituisce la lista di oneri le cui causali sono legate ad un endoprocedimento
		/// </summary>
		/// <returns></returns>		
		IEnumerable<OnereFrontoffice> OneriEndoprocedimenti { get; }

		/// <summary>
		/// Restituisce la lista di oneri della pratica
		/// </summary>
		/// <returns></returns>
		IEnumerable<OnereFrontoffice> Oneri { get; }

		/// <summary>
		/// Restituisce l'attestazione di pagamento caricata dall'utente per gli oneri contrassegnati come "pagato"
		/// </summary>
		/// <returns></returns>
		AttestazioneDiPagamento AttestazioneDiPagamento { get; }

		/// <summary>
		/// Restituisce true se l'utente ha selezionato la spunta "Dichiaro di non avere oneri da pagare"
		/// </summary>
		/// <returns></returns>
		bool DichiaraDiNonAvereOneriDaPagare { get; }

		/// <summary>
		/// REstituisce l'importo totale degli oneri da pagare
		/// </summary>
		decimal Totale { get; }

		/// <summary>
		/// REstituisce l'importo totale degli oneri pagati
		/// </summary>
		decimal TotalePagato { get; }

		/// <summary>
		/// Restituisce le righe di oneri domanda pronte per essere pagate on line
		/// </summary>
		/// <returns></returns>
        IEnumerable<OnereFrontoffice> GetOneriOnlineProntiPerPagamento();

		/// <summary>
		/// Restituisce le righe di oneri domanda i cui pagamenti sono stati avviato on line
		/// </summary>
		/// <returns></returns>
		IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoAvviato();

		/// <summary>
		/// Restituisce la lista di tutti gli oneri da pagare online, indipendentemente dallo stato di pagamento
		/// </summary>
		/// <returns></returns>
		IEnumerable<OnereFrontoffice> GetOneriConPagamentoOnline();

		[Obsolete]
		IEnumerable<OnereFrontoffice> GetOneriDaIdOperazione(string p);

		/// <summary>
		/// Restituisce le righe di oneri domanda le cui operazioni di pagamento sono fallite
		/// </summary>
		/// <returns></returns>
		IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoFallito();

		/// <summary>
		/// Restituisce le righe di oneri domanda le cui operazioni di pagamento sono riuscite
		/// </summary>
		/// <returns></returns>
		IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoRiuscito();

	}
}
