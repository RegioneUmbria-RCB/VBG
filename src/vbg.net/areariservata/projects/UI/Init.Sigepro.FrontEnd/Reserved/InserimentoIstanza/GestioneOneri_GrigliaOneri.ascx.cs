namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	using System;
    using System.Linq;
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

        public string TestoOnereNonDovuto
        {
            get { object o = this.ViewState["TestoOnereNonDovuto"]; return o == null || String.IsNullOrEmpty(o.ToString())? "Non dovuto" : (string)o; }
            set { this.ViewState["TestoOnereNonDovuto"] = value; }
        }


        public Repeater Repeater
		{
			get { return this.rptOneri; }
		}

		public IEnumerable<OnereFrontoffice> DataSource { get; set; }

		public IEnumerable<TipoPagamento> ModalitaPagamento { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
		}

		public override void DataBind()
		{
            this.rptOneri.DataSource = DataSource.Select(x => new
            {
                ModalitaPagamento = (int)x.ModalitaPagamento,
                CodiceCausale = x.Causale.Codice,
                Causale = x.Causale.Descrizione,
                CodiceEndoOInterventoOrigine = x.EndoOInterventoOrigine.Codice,
                EndoOInterventoOrigine = x.EndoOInterventoOrigine,
                Note = x.Note,
                DataPagamento = x.EstremiPagamento != null && x.EstremiPagamento.Data.HasValue ? x.EstremiPagamento.Data.Value.ToString("dd/MM/yyyy") : String.Empty,
                RiferimentoPagamento = x.EstremiPagamento == null ? String.Empty : x.EstremiPagamento.Riferimento,
                Importo = x.Importo,
                ImportoPagato = x.ImportoPagato,
                PagamentoCompletato = x.StatoPagamento == StatoPagamentoOnereEnum.PagamentoRiuscito,
                TipoPagamento = x.EstremiPagamento == null ? string.Empty : x.EstremiPagamento.TipoPagamento.Codice,
                NonPagato = x.ModalitaPagamento == ModalitaPagamentoOnereEnum.NonDovuto
            });
			this.rptOneri.DataBind();
		}

		protected void rptOneri_ItemDataBound(object sender, System.Web.UI.WebControls.RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
                var ddl = (DropDownList)e.Item.FindControl("ddlTipoPagamento");
                dynamic onere = e.Item.DataItem;

                try
                {
                    ddl.SelectedValue = onere.TipoPagamento;
                    ddl.DataSource = this.ModalitaPagamento;
                    ddl.DataBind();
                }
                catch (ArgumentOutOfRangeException)
                {
                }

                ddl = (DropDownList)e.Item.FindControl("ddlPagamento");

                try
                {
                    ddl.SelectedValue = onere.ModalitaPagamento.ToString();
                }
                catch (ArgumentOutOfRangeException)
                {
                }
			}
		}

        protected void rptOneri_ItemCreated(object sender, RepeaterItemEventArgs e)
        {
            var el = (DropDownList)e.Item.FindControl("ddlPagamento");

            if (el == null)
            {
                return;
            }

            if (el.Items.Count > 0)
            {
                return;
            }

            /*       < asp:ListItem Value = "" Text = "Selezionare..." />
   
                       < asp:ListItem Value = "2" Text = "Effettuato" />
      
                          < asp:ListItem Value = "0" Text = "Non dovuto" />*/

            el.Items.Add(new ListItem("Selezionare...", ""));
            el.Items.Add(new ListItem("Effettuato", "2"));
            el.Items.Add(new ListItem(this.TestoOnereNonDovuto, "0"));

        }
    }
}