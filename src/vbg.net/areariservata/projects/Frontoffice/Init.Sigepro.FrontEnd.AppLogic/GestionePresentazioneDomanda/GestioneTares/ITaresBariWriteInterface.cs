using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Bari.TARES.DTOs;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneTares
{
	public interface ITaresBariWriteInterface
	{
		void ImpostaUtenza(DatiContribuenteDto datiutenza);
	}
}
