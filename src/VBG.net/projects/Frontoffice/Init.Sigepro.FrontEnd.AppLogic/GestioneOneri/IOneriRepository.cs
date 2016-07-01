// -----------------------------------------------------------------------
// <copyright file="IOneriRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

	public interface IOneriRepository
	{
		List<OnereDto> GetByIdInterventoIdEndo(int codiceIntervento, List<int> listaIdEndo);
		IEnumerable<BaseDtoOfStringString> GetModalitaPagamento();
	}
}
