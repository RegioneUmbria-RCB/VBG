using System;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Combo che contiene i valori della tabella TIPISOGGETTO.
	/// </summary>
	[ToolboxData("<{0}:ComboTipiSoggetto runat=server></{0}:ComboTipiSoggetto>")]
	public class ComboTipiSoggetto : FilteredDropDownList
	{
		[Inject]
		public ITipiSoggettoService _tipiSoggettoRepository { get; set; }


		public ComboTipiSoggetto()
		{
			FoKernelContainer.Inject(this);
		}

		public override string SelectedValue
		{
			get { return base.SelectedValue; }
			set
			{
				try
				{
					base.SelectedValue = value;
				}
				catch (ArgumentOutOfRangeException)
				{
					SelectedIndex = -1;
				}
			}
		}


		public string TipoSoggetto
		{
			get
			{
				object o = this.ViewState["TipoSoggetto"];
				return (o == null) ? "F" : (string) o;
			}
			set { this.ViewState["TipoSoggetto"] = value; }
		}

		public int? CodiceIntervento
		{
			get { object o = this.ViewState["CodiceIntervento"]; return o == null ? (int?)null : (int?)o; }
			set { this.ViewState["CodiceIntervento"] = value; }
		}



		protected override void CreateChildControls()
		{
			this.DataTextField = "TIPOSOGGETTO";
			this.DataValueField = "CODICETIPOSOGGETTO";

			base.CreateChildControls();
		}


		public override void DataBind()
		{
			EnsureChildControls();

			var l = ( (this.TipoSoggetto == "G") ? _tipiSoggettoRepository.GetTipiSoggettoPersonaGiurudica(this.CodiceIntervento) :
													_tipiSoggettoRepository.GetTipiSoggettoPersonaFisica(this.CodiceIntervento))
					.Select(x => new ListItem(x.Descrizione, x.Codice.ToString()));

			this.Items.Clear();
			this.Items.Add(new ListItem("Selezionare...", String.Empty));
			this.Items.AddRange(l.ToArray());

			base.DataBind();
		}

	}
}