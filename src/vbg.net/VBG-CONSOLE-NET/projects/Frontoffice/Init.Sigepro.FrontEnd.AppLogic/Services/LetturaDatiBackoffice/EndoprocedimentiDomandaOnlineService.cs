using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.LetturaDatiBackoffice
{
	public class EndoprocedimentiDomandaOnlineService
	{
		IAliasSoftwareResolver _aliasSoftwareResolver;
		IEndoprocedimentiRepository _endoprocedimentiRepository;

		public EndoprocedimentiDomandaOnlineService(IAliasSoftwareResolver aliasSoftwareResolver, IEndoprocedimentiRepository endoprocedimentiRepository)
		{
			Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();
			Condition.Requires(endoprocedimentiRepository, "endoprocedimentiRepository").IsNotNull();

			this._aliasSoftwareResolver = aliasSoftwareResolver;
			this._endoprocedimentiRepository = endoprocedimentiRepository;
		}


		public EndoprocedimentiDellaDomandaOnline LeggiEndoprocedimentiDaCodiceIntervento(int codiceIntervento)
		{
			var endoDellIntervento = this._endoprocedimentiRepository.WhereInterventoIs(this._aliasSoftwareResolver.AliasComune, codiceIntervento);

			return new EndoprocedimentiDellaDomandaOnline(endoDellIntervento.EndoIntervento, endoDellIntervento.EndoFacoltativi);
		}
	}
}
