namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati
{
	using System;
	using System.ComponentModel;
	using System.Web.UI;
	using System.Web.UI.WebControls;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.IoC;
	using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments;
	using Ninject;
    using Init.Sigepro.FrontEnd.Infrastructure.IOC;

    public partial class GrigliaAllegati : System.Web.UI.UserControl
	{
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		protected static class Constants
		{
			public const string FirmaCommandName = "Firma";
			public const string CompilaCommandName = "Compila";
            public const string AllegaMultipliCommand = "AllegaMultipli";
		}

        public delegate IValidPostedFileSpecification GetValidationSpecificationDelegate(object sender, int idAllegato);
        public event GetValidationSpecificationDelegate GetValidationSpecification;

        public delegate void ErroreDelegate(object sender, string errorMessage);
        public event ErroreDelegate Errore;

		public event OnAllegaDocumentoDelegate AllegaDocumento;

		public event OnRimuoviDocumentoDelegate	RimuoviDocumento;

		public event OnCompilaDocumentoDelegate	CompilaDocumento;

		public event OnFirmaDocumentoDelegate FirmaDocumento;

        public event OnAllegaDocumentiMultipliDelegate AllegaDocumentiMultipli;


		public bool SoloFirma
		{
			get { object o = this.ViewState["SoloFirma"]; return o == null ? false : (bool)o; }
			set { this.ViewState["SoloFirma"] = value; }
		}

        public bool PermettiAllegatiMultipili
        {
            get { object o = this.ViewState["PermettiAllegatiMultipili"]; return o == null ? false : (bool)o; }
            set { this.ViewState["PermettiAllegatiMultipili"] = value; }
        }



		[Bindable(true)]
		[DefaultValue("")]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
		[Themeable(false)]
		public virtual object DataSource 
		{
			get { return this.gvAllegati.DataSource; }
			set { this.gvAllegati.DataSource = value; }
		}

		public GrigliaAllegati()
		{
			FoKernelContainer.Inject(this);
		}

		protected void Page_Load(object sender, EventArgs e)
		{
            
		}

		public override void DataBind()
		{
			if (SoloFirma)
			{
				this.gvAllegati.Columns[0].Visible = false;
				this.gvAllegati.Columns[2].Visible = false;
			}
			this.gvAllegati.DataBind();
		}

		protected void OnRowCommand(object sender, GridViewCommandEventArgs e)
		{
			if (e.CommandName == Constants.FirmaCommandName)
			{
				var codiceOggetto = Convert.ToInt32(e.CommandArgument);

				if (this.FirmaDocumento != null)
				{
					this.FirmaDocumento(this, new FirmaDocumentoEventArgs(codiceOggetto));
				}
			}

			if (e.CommandName == Constants.CompilaCommandName)
			{
				var idAllegato = Convert.ToInt32(e.CommandArgument);

				if (this.CompilaDocumento != null)
				{
					this.CompilaDocumento(this, new CompilaDocumentoEventArgs(idAllegato));
				}
			}

            if (e.CommandName == Constants.AllegaMultipliCommand)
            {
                var idAllegato = Convert.ToInt32(e.CommandArgument);

                if (this.AllegaDocumentiMultipli != null)
                {
                    this.AllegaDocumentiMultipli(this, new AllegaDocumentiMultipliEventArgs(idAllegato));
                }
            }
		}

		protected void OnRowUpdating(object sender, GridViewUpdateEventArgs e)
		{
            try
            {
                var idAllegato = Convert.ToInt32(this.gvAllegati.DataKeys[e.RowIndex]["Id"]);
                var editPostedFile = (FileUpload)this.gvAllegati.Rows[e.RowIndex].FindControl("EditPostedFile");

                if (this.AllegaDocumento != null)
                {
                    var specifiction = (IValidPostedFileSpecification)this._validPostedFileSpecification;

                    if (this.GetValidationSpecification != null)
                    {
                        specifiction = this.GetValidationSpecification(this, idAllegato);
                    }

					var file = new BinaryFile(editPostedFile, specifiction);
                    this.AllegaDocumento(this, new AllegaDocumentoEventArgs(idAllegato, file));
                }
            }
            catch (Exception ex)
            {
                if (this.Errore != null)
                {
                    this.Errore(this, ex.Message);
                }
            }
		}

		protected void OnRowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			var key = Convert.ToInt32(gvAllegati.DataKeys[e.RowIndex]["Id"]);

			if (this.RimuoviDocumento != null)
			{
				this.RimuoviDocumento(this, new RimuoviDocumentoEventArgs(key));
			}
		}

        protected void RowCancelingEdit1(object sender, GridViewCancelEditEventArgs e)
        {

        }

        protected void RowEditing(object sender, GridViewEditEventArgs e)
        {

        }
    }
}