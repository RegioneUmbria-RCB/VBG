using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public interface IOneriReadInterface
	{
		IEnumerable<OnereFrontoffice> OneriIntervento { get; }
		IEnumerable<OnereFrontoffice> OneriEndoprocedimenti { get; }
		IEnumerable<OnereFrontoffice> Oneri { get; }
		AttestazioneDiPagamento AttestazioneDiPagamento { get; }
		bool DichiaraDiNonAvereOneriDaPagare { get; }
		double Totale { get; }
		double TotalePagato { get; }

        IEnumerable<OnereFrontoffice> GetOneriProntiPerPagamentoOnline();

        IEnumerable<OnereFrontoffice> GetOperazioniConPagamentoInSospeso();

        IEnumerable<OnereFrontoffice> GetOneriDaIdOperazione(string p);
    }
}
