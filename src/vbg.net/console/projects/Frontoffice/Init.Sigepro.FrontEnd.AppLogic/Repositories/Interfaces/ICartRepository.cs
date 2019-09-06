using System;
namespace Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces
{
	public interface ICartRepository
	{
		string GetUrlSchedaCARTEndo(int codEndo);
		string GetUrlSchedaCARTIntervento(int codIntervento);
		string GetCodiceAttivitaBdrDaIdIntervento( int codiceIntervento);
	}
}
