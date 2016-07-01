using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	/// <summary>
	/// Descrizione di riepilogo per VisuraDataGrid.
	/// </summary>
	public class ListaArchivioIstanzeDataGrid : ListaVisuraDataGridBase
	{
		public ListaArchivioIstanzeDataGrid()
			: base(new FiltriArchivioIstanzeControlProvider())
		{

		}
	}
}
