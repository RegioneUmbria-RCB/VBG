using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli
{
	public interface ICampoDinamico
	{
		int Id { get; }
		string Etichetta { get; }
		string NomeCampo { get; }
	}

	public interface IStrutturaModello
	{
		int IdModello { get; }
		string CodiceScheda { get; }
		string Descrizione { get; }
		IEnumerable<ICampoDinamico> Campi { get; }
	}

	public interface IStrutturaModelloReader
	{
		IStrutturaModello Read(int idModello);
	}
}
