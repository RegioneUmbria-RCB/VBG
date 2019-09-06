using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Web.UI.WebControls;

using Init.Sigepro.FrontEnd.WebControls.Common;



namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	/// <summary>
	/// Descrizione di riepilogo per VisuraDataGrid.
	/// </summary>
	public class ListaVisuraDataGrid : ListaVisuraDataGridBase
	{
		public ListaVisuraDataGrid():base( new FiltriVisuraControlProvider())
		{

		}

        public void RebindFromCache()
        {
            Rebind();
        }
    }
}