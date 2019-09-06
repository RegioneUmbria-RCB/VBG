using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Sit.IntegrazioneSitBari
{
	public interface IConfigurazioneSitBari
	{
		string CodEnte { get; }
		string RequestFrom { get; }
		string TipoIndirizzoRicercato { get; }
	}
}
