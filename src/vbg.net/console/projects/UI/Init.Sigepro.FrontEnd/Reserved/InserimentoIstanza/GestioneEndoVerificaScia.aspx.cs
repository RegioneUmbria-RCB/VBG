using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.IncompatibilitaNatura;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneEndoVerificaScia : IstanzeStepPage
	{
		ILog m_logger = LogManager.GetLogger(typeof(GestioneEndoVerificaScia));

        [Inject]
        public IEndoprocedimentiIncompatibiliService _endoprocedimentiIncompatibiliService { get; set; }
        [Inject]
        public IEndoprocedimentiService _endoprocedimentiService { get; set; }

        #region parametri letti dall'xml
        public string MessaggioErrore
		{
			get { object o = this.ViewState["MessaggioErrore"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["MessaggioErrore"] = value; }
		}

		public bool ConsentiProseguimentoInCasoDiIncompatibilita
		{
			get { object o = this.ViewState["ConsentiProseguimentoInCasoDiIncompatibilita"]; return o == null ? true : (bool)o; }
			set { this.ViewState["ConsentiProseguimentoInCasoDiIncompatibilita"] = value; }
		}
		#endregion


		IEnumerable<Endoprocedimento> _endoIncompatibili;
		protected IEnumerable<Endoprocedimento> EndoIncompatibili
		{
			get
			{
				if (_endoIncompatibili == null)
				{
					_endoIncompatibili = new List<Endoprocedimento>();

					var naturaEndoCompatibile = new NaturaEndoCompatibileSpecification(ReadFacade.Domanda.Endoprocedimenti.Principale);

					_endoIncompatibili = ReadFacade.
											Domanda.
											Endoprocedimenti.
											SecondariNonPresenti.
											Where(endo => !naturaEndoCompatibile.IsSatisfiedBy(endo));
				}

				return _endoIncompatibili;
			}
		}
	

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		#region Ciclo di vita dello step
		public override bool CanEnterStep()
		{
            // devo resettare la natura dell'istanza
            var naturaBase = this._endoprocedimentiIncompatibiliService.GetNaturaBaseDaidEndoprocedimento(this.ReadFacade.Domanda.Endoprocedimenti.Principale.Codice);
            this._endoprocedimentiService.ImpostaNaturaBaseDomanda(this.IdDomanda, naturaBase);

            return EndoIncompatibili.Count() > 0;
		}

		public override bool CanExitStep()
		{
			if (EndoIncompatibili.Count() == 0) return true;

			if (!ConsentiProseguimentoInCasoDiIncompatibilita)
			{
				Errori.Add("Non è possibile proseguire con la presentazione della domanda in quanto esistono delle incompatibilità tra le nature degli endoprocedimenti attivati.");
				return false;
			}

            // Se esistono endo incompatibili assegno la nuova natura dell'istanza,
            // nel caso in cui ci fossero più endo incompatibili prendo quello con 
            // il binario dipendenze più alto
            var endoNuovaNatura = this.EndoIncompatibili.OrderByDescending(x => x.BinarioDipendenze).First();

            var naturaBase = this._endoprocedimentiIncompatibiliService.GetNaturaBaseDaidEndoprocedimento(endoNuovaNatura.Codice);

            this._endoprocedimentiService.ImpostaNaturaBaseDomanda(this.IdDomanda, naturaBase);

			return true;
		}

		#endregion



		public override void DataBind()
		{
			var endoPrincipale = ReadFacade.Domanda.Endoprocedimenti.Principale;

            var endoNuovaNatura = this.EndoIncompatibili.OrderByDescending(x => x.BinarioDipendenze).First();

            var naturaBase = this._endoprocedimentiIncompatibiliService.GetNaturaBaseDaidEndoprocedimento(endoNuovaNatura.Codice);

            ltrMessaggioErrore.Text = String.Format( MessaggioErrore,
													 endoPrincipale.Descrizione,
													 endoPrincipale.Natura.Descrizione, 
													 GeneraListaHtmlEndoIncompatibili(),
                                                     naturaBase);
		}

		private string GeneraListaHtmlEndoIncompatibili()
		{
			var sb = new StringBuilder();

			sb.Append("<ul>");

			foreach (var endo in EndoIncompatibili)
			{
				sb.AppendFormat("<li>{0}<br /><i>Natura: {1}</i></li>", 
								endo.Descrizione, 
								endo.Natura.Descrizione);
			}
			sb.Append("</ul>");

			return sb.ToString();
		}

	}
}
