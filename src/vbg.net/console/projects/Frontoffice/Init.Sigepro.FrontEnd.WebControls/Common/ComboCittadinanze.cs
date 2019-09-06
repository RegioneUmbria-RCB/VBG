using System;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Combo contenente i valior della tabella cittadinanze.
	/// Utilizza la chache del server per limitare il traffico verso il DB
	/// </summary>
	[ToolboxData("<{0}:ComboCittadinanza runat=server></{0}:ComboCittadinanza>")]
	public class ComboCittadinanza : FilteredDropDownList
	{
		[Inject]
		public ICittadinanzeService _cittadinanzeService { get; set; }

		public ComboCittadinanza()
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
					this.SelectedIndex = -1;
				}
			}
		}


		protected override void CreateChildControls()
		{
			this.DataTextField = "CITTADINANZA";
			this.DataValueField = "CODICE";

			base.CreateChildControls();
		}


		public override void DataBind()
		{
			EnsureChildControls();


			var cittadinanze = _cittadinanzeService.GetListaCittadinanze().ToList();

			var cittadinanzaItaliana = cittadinanze.FirstOrDefault(x => x.Descrizione.ToUpperInvariant() == "ITALIA");

			if (cittadinanzaItaliana != null)
			{
				cittadinanze.Remove(cittadinanzaItaliana);
				cittadinanze.Insert(0, cittadinanzaItaliana);
			}

			this.Items.Clear();
			this.Items.Add(new ListItem("Selezionare...", String.Empty));

			for (int i = 0; i < cittadinanze.Count; i++ )
				this.Items.Add(new ListItem(cittadinanze[i].Descrizione, cittadinanze[i].Codice.ToString()));

			base.DataBind();
		}
	}
}