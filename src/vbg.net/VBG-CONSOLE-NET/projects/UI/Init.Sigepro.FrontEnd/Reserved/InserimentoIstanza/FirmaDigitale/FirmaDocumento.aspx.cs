namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaDigitale
{
	using System;
	using System.Web.UI.WebControls;
	using Init.Sigepro.FrontEnd.AppLogic.ViewModels.FirmaDigitale;
	using Ninject;

	public partial class FirmaDocumento : FirmaDocumentoBasePage
	{
		protected new int IdDomanda
		{
			get { return Convert.ToInt32(Request.QueryString["IdPresentazione"]); }
		}

		[Inject]
		protected FirmaDocumentoViewModel _viewModel { get; set; }

		protected override HiddenField GetHiddenFieldEsito()
		{
			return this.hidEsito;
		}

		protected override HyperLink GetHyperLinkFileDaFirmare()
		{
			return this.fileDaFirmare;
		}

		protected override void DocumentoFirmatoConSuccesso(int codiceOggetto, string nomeFile)
		{
			this._viewModel.AggiornaStatoFirma(this.IdDomanda, codiceOggetto, nomeFile);
		}
	}
}