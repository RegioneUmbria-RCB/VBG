using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public interface IOneriReadInterface
	{
		IEnumerable<OnereDaPagare> OneriIntervento { get; }
		IEnumerable<OnereDaPagare> OneriEndoprocedimenti { get; }
		IEnumerable<OnereDaPagare> Oneri { get; }
		AttestazioneDiPagamento AttestazioneDiPagamento { get; }
		bool DichiaraDiNonAvereOneriDaPagare { get; }
		double Totale { get; }
		double TotalePagato { get; }
	}
}
