using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.SIGePro.Manager.DTO.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento
{
	public interface IAmbitoRicercaIntervento
	{
		AmbitoRicerca GetAmbito(); 
	}
}
