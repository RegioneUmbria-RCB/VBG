using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;


namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneEndoV2GrigliaEndo : System.Web.UI.UserControl
	{
		public bool ModificaProcedimentiProposti
		{
			get { object o = this.ViewState["ModificaProcedimentiProposti"]; return o == null ? true : (bool)o; }
			set { this.ViewState["ModificaProcedimentiProposti"] = value; }
		}



		public bool MostraFamiglia
		{
			get { object o = this.ViewState["MostraFamiglia"]; return o == null ? true : (bool)o; }
			set { this.ViewState["MostraFamiglia"] = value; }
		}

		public bool MostraTipoEndo
		{
			get { object o = this.ViewState["MostraTipoEndo"]; return o == null ? true : (bool)o; }
			set { this.ViewState["MostraTipoEndo"] = value; }
		}

		public IEnumerable<int> ListaIdSelezionati { get; set; }

		public  FamigliaEndoprocedimentoDto[] DataSource{get;set;}

		public GestioneEndoV2GrigliaEndo()
		{
			ListaIdSelezionati = new List<int>();
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}

		public override void DataBind()
		{
			foreach (var famiglia in DataSource)
			{
				foreach (var tipo in famiglia.TipiEndoprocedimenti)
				{
					tipo.Endoprocedimenti = tipo.Endoprocedimenti.OrderBy(x => x.Ordine).ThenBy(x => x.Descrizione).ToArray(); 
				}
			}

			rptFamiglieEndo.DataSource = DataSource;
			rptFamiglieEndo.DataBind();
		}

		public IEnumerable<int> GetCodiciFoglieSelezionate()
		{
			var rVal = new List<int>();

			for (int i = 0; i < rptFamiglieEndo.Items.Count; i++)
			{
				var famiglieEndoItem = rptFamiglieEndo.Items[i];

				var rptTipiEndo = (Repeater)famiglieEndoItem.FindControl("rptTipiEndo");

				for (int j = 0; j < rptTipiEndo.Items.Count; j++)
				{
					var tipiEndoItem = rptTipiEndo.Items[j];

					var rptEndo = (Repeater)tipiEndoItem.FindControl("rptEndo");

					for (int k = 0; k < rptEndo.Items.Count; k++)
					{
						var endoItem = rptEndo.Items[k];

						var hidEndo = (HiddenField)endoItem.FindControl("hidEndo");
						var chkEndo = (CheckBox)endoItem.FindControl("chkEndo");

						if (!chkEndo.Checked)
							continue;

						yield return Convert.ToInt32(hidEndo.Value);
					}
				}
			}
		}

		public bool EndoSelezionato(object objCodiceEndo) 
		{
			return ListaIdSelezionati.Contains(Convert.ToInt32(objCodiceEndo));
		}
	}
}