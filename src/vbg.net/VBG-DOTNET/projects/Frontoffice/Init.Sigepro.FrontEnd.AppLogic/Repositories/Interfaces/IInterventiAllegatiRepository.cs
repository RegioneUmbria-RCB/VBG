using System;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.SIGePro.Manager.DTO.AllegatiDomandaOnline;
using Init.SIGePro.Manager.DTO.Common;

namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IInterventiAllegatiRepository
	{
		IEnumerable<AllegatoInterventoDomandaOnlineDto> GetAllegatiDaIdintervento( int codiceIntervento, AmbitoRicerca ambitoRicerca);
		IEnumerable<AlberoProcDocumentiCat> GetListaCategorieAllegati();
        string GetIdDrupalDaIdIntervento(int codiceIntervento);
	}
}
