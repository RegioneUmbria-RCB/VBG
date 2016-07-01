using System;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Collections.Generic;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface IInterventiAllegatiRepository
	{
		IEnumerable<AllegatoInterventoDomandaOnlineDto> GetAllegatiDaIdintervento( int codiceIntervento, AmbitoRicerca ambitoRicerca);
		IEnumerable<AlberoProcDocumentiCat> GetListaCategorieAllegati();
	}
}
