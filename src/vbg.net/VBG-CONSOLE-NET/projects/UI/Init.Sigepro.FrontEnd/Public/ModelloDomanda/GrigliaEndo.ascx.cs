using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Collections;

namespace Init.Sigepro.FrontEnd.Public.ModelloDomanda
{
	public partial class GrigliaEndo : System.Web.UI.UserControl
	{
		public IEnumerable<FamigliaEndoprocedimentoDto> DataSource;

		public string DescrizioneGriglia
		{
			get
			{ 
				var objVs = this.ViewState["DescrizioneGriglia"];
				return objVs == null || String.IsNullOrEmpty(objVs.ToString()) ? "Descrizione griglia" : objVs.ToString();
			}
			set { this.ViewState["DescrizioneGriglia"] = value; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
		}

		public override void DataBind()
		{
			if (this.DataSource != null && this.DataSource.Count() > 0)
				this.rptEndoprocedimenti.Visible = true;
			else
				this.rptEndoprocedimenti.Visible = false;

			this.rptEndoprocedimenti.DataSource = this.DataSource;
			this.rptEndoprocedimenti.DataBind();
		}


		public IEnumerable<Object> GetEndoBindingSource(object objListaEndo)
		{
			var listaEndo = (IEnumerable<EndoprocedimentoDto>)objListaEndo;

			return listaEndo.Select(x => new
			{
				Id = x.Codice,
				Descrizione = x.Descrizione,
				Amministrazione = x.Amministrazione,
				AmministrazionePresente = !String.IsNullOrEmpty(x.Amministrazione)
			});

		}

		public IEnumerable<int> GetIdSelezionati()
		{
			foreach (RepeaterItem famiglieItem in this.rptEndoprocedimenti.Items)
			{
				var rptTipiEndo = (Repeater)famiglieItem.FindControl("rptTipiEndo");

				foreach (RepeaterItem tipiEndoItem in rptTipiEndo.Items)
				{
					var rptEndo = (Repeater)tipiEndoItem.FindControl("rptEndo");

					foreach (RepeaterItem endoItem in rptEndo.Items)
					{
						var chkSelezionato = (CheckBox)endoItem.FindControl("chkSelezionato");
						var hidIdendo = (HiddenField)endoItem.FindControl("hidIdendo");

						if (chkSelezionato.Checked)
							yield return Convert.ToInt32(hidIdendo.Value);
					}
				}
			}
		}
	}
}