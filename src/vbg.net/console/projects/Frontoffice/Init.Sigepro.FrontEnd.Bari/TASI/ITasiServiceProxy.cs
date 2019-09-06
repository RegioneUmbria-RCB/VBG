using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.TASI.DTOs;

namespace Init.Sigepro.FrontEnd.Bari.TASI
{
	internal interface ITasiServiceProxy
	{
		DatiContribuenteTasiDto GetDatiContribuenteByCodiceFiscale(string codiceFiscaleCaf,string indirizzoPecCaf, string codiceFiscaleUtenza);
		DatiContribuenteTasiDto GetDatiContribuenteByPartitaIva(string codiceFiscaleCaf, string indirizzoPecCaf, int partitaIvaUtente);
	}
}
