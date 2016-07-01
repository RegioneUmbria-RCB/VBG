namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	using System;
	using System.Collections.Generic;
	using System.Web.UI.WebControls;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;

	public partial class GestioneOneri_GrigliaOneri : System.Web.UI.UserControl
	{
		public string EtichettaColonnaCausale
		{
			get 
			{ 
				object o = this.ViewState["EtichettaColonnaCausale"]; 
				return o == null ? string.Empty : (string)o; 
			}

			set 
			{ 
				this.ViewState["EtichettaColonnaCausale"] = value; 
			}
		}

		public Repeater Repeater
		{
			get { return this.rptOneri; }
		}

		public IEnumerable<OnereDaPagare> DataSource { get; set; }

		public IEnumerable<TipoPagamento> ModalitaPagamento { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
		}

		public override void DataBind()
		{
			this.rptOneri.DataSource = DataSource;
			this.rptOneri.DataBind();
		}

		protected void rptOneri_ItemDataBound(object sender, System.Web.UI.WebControls.RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				var ddl = (DropDownList)e.Item.FindControl("ddlTipoPagamento");
				var onere = (OnereDaPagare)e.Item.DataItem;

				try
				{
					ddl.SelectedValue = onere.EstremiPagamento == null ? string.Empty : onere.EstremiPagamento.ModalitaPagamento.Codice;
					ddl.DataSource = this.ModalitaPagamento;
					ddl.DataBind();
				}
				catch (ArgumentOutOfRangeException)
				{
				}
			}
		}
	}
}