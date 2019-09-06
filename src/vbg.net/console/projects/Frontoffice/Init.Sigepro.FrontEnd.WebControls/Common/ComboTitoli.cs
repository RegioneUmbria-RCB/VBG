using System;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Combo che contiene i dati della tabella Titoli
	/// </summary>
	[ToolboxData("<{0}:ComboTitoli runat=server></{0}:ComboTitoli>")]
	public class ComboTitoli : FilteredDropDownList
	{
		[Inject]
		public ITitoliRepository _titoliRepository { get; set; }


		public ComboTitoli()
		{
			FoKernelContainer.Inject(this);
		}

		protected override void CreateChildControls()
		{
			this.DataTextField = "TITOLO";
			this.DataValueField = "CODICETITOLO";

			base.CreateChildControls();
		}


		public override void DataBind()
		{
			EnsureChildControls();


			var titoli = _titoliRepository.GetList(IdComune);

			this.Items.Clear();
			this.Items.Add(new ListItem("Selezionare...", String.Empty));

			foreach (var t in titoli)
			{
				this.Items.Add(new ListItem(t.TITOLO , t.CODICETITOLO));
			}

			base.DataBind();
		}

	}
}