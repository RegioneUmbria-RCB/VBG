using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager.DTO.DatiDinamici;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public class WsListaModelliDinamiciDomanda
	{
		public List<SchedaDinamicaInterventoDto> SchedeIntervento { get; set; }
		public List<SchedaDinamicaEndoprocedimentoDto> SchedeEndoprocedimenti { get; set; }
	}
}
