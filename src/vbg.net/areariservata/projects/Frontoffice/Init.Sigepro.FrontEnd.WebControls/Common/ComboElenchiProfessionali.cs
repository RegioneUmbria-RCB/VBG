using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Web.UI;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;
using System.Web.UI.WebControls;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Combo che contiene i valori della tabella ElenchiProfessionali.
	/// </summary>
	[ToolboxData("<{0}:ComboElenchiProfessionali runat=server></{0}:ComboElenchiProfessionali>")]
	public partial class ComboElenchiProfessionali : FilteredDropDownList
	{
		[Inject]
		public IElenchiProfessionaliRepository _elenchiProfessionaliRepository { get; set; }

		public ComboElenchiProfessionali()
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

        public string LimitaDati
        {
            get { object o = this.ViewState["LimitaDatiAlbo"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["LimitaDatiAlbo"] = value; }
        }



		protected override void CreateChildControls()
		{
			this.DataTextField = "Codice";
			this.DataValueField = "Descrizione";

			base.CreateChildControls();
		}


		public override void DataBind()
		{
			EnsureChildControls();

			this.Items.Clear();
			this.Items.Add(new ListItem("Selezionare...", String.Empty));

            var limitaDatiAlbo = new int[0];

            if (!String.IsNullOrEmpty(LimitaDati))
            {
                limitaDatiAlbo = LimitaDati.Split(',').Select(x => Convert.ToInt32(x.Trim())).ToArray();
            }

            var dati = _elenchiProfessionaliRepository.GetList(IdComune)
                        .Where(x => {
                            return limitaDatiAlbo.Length == 0 ? true : limitaDatiAlbo.Contains(x.EpId.Value);
                        })
                        .ToList();

			for (int i = 0; i < dati.Count; i++)
			{
				this.Items.Add( new ListItem( dati[i].EpDescrizione , dati[i].EpId.ToString() ) );
			}
			
			base.DataBind();
		}

        
    }
}
