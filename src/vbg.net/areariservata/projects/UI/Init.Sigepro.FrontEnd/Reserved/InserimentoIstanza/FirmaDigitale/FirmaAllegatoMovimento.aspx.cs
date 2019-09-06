using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.Infrastructure.Repositories;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaDigitale
{
	public partial class FirmaAllegatoMovimento : FirmaDocumentoBasePage
	{
		private static class Constants
		{
			public const string IdMovimentoParameterName = "IdMovimento";
			public const string TipoAllegatoParameterName = "Tipo";
		}

		public enum Tipo
		{
			RiepilogoScheda,
			Allegato
		}

		protected int IdMovimento
		{
			get { return Convert.ToInt32(Request.QueryString[Constants.IdMovimentoParameterName]); }
		}

		protected Tipo TipoAllegato
		{
			get
			{
				var str = Request.QueryString[Constants.TipoAllegatoParameterName];

				if(String.IsNullOrEmpty( str ))
					return Tipo.RiepilogoScheda;

				return (Tipo)Enum.Parse( typeof(Tipo), str);
			}
		}

		[Inject]
		protected FirmaDigitaleAllegatoMovimentoViewModel _viewModel { get; set; }


        public FirmaAllegatoMovimento()
        {
            // Deve sempre essere nel costruttore
            base.IgnoraVerificaAccessoIstanza = true;
        }

		protected override HiddenField GetHiddenFieldEsito()
		{
			return hidEsito;
		}

		protected override HyperLink GetHyperLinkFileDaFirmare()
		{
			return fileDaFirmare;
		}

		protected override void DocumentoFirmatoConSuccesso(int codiceOggetto, string fileName)
		{
			if(TipoAllegato == Tipo.RiepilogoScheda)
				this._viewModel.FirmaRiepilogoSchedaCompletata(IdMovimento, codiceOggetto, fileName);
			else
				this._viewModel.FirmaAllegatoCompletata( IdMovimento, codiceOggetto, fileName );

		}
	}
}