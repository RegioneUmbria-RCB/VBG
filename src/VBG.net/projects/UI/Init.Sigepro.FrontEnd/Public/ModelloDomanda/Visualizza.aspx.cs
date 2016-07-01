using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneRiepilogoDomanda;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.Public.ModelloDomanda
{
	public partial class Visualizza : BasePage
	{
		[Inject]
		protected EndoprocedimentiService _endoprocedimentiService { get; set; }
		[Inject]
		protected GenerazioneRiepilogoDomandaService _generazioneRiepilogoDomandaService { get; set; }
		[Inject]
		public IInterventiRepository _interventiRepository { get; set; }

		private int IdIntervento
		{
			get { return Convert.ToInt32(Request.QueryString["intervento"]); }
		}

		protected int[] IdEndoSelezionati
		{
			get 
			{
				var obj = this.ViewState["IdEndoSelezionati"];

				if(obj == null)
					return new int[0];

				return (int[])obj;
			}

			set{this.ViewState["IdEndoSelezionati"] = value;}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		protected string GeneraUrlModello()
		{
			return ResolveClientUrl(String.Format("~/Public/ModelloDomanda/ModelloDomandaHandler.ashx?IdComune={0}&Software={1}&Intervento={2}&Endo={3}", IdComune , Software, IdIntervento, String.Join( ",",IdEndoSelezionati )));
		}

		public override void DataBind()
		{
			var endoprocedimenti = this._endoprocedimentiService.LeggiEndoprocedimentiDaCodiceIntervento(IdComune, IdIntervento);
			lnkAccedi.NavigateUrl = "~/Login.aspx?IdComune=" + IdComune + "&Software=" + Software;

			// Se esistono interventi pubblicati sul primo livello dell'albero visualizzo il bottone "Accedi ai servizi online"
			lnkAccedi.Visible = _interventiRepository.EsistonoVociAttivabiliTramiteAreaRiservata(IdComune, Software);

			if (endoprocedimenti.FamiglieEndoprocedimentiAttivabili.Count == 0 &&
				endoprocedimenti.FamiglieEndoprocedimentiFacoltativi.Count == 0)
			{
				lnkSelezionaEndo.Visible = false;

				multiView.ActiveViewIndex = 1;
				return;
			}

			geProcedimentiEventuali.DataSource = endoprocedimenti.FamiglieEndoprocedimentiFacoltativi;
			geProcedimentiEventuali.DataBind();

			geProcedimentiNecessari.DataSource = endoprocedimenti.FamiglieEndoprocedimentiAttivabili;
			geProcedimentiNecessari.DataBind();			
		}



		protected void lnkSelezionaEndo_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}

		protected void lnkGeneraModello_Click(object sender, EventArgs e)
		{
			IdEndoSelezionati = geProcedimentiNecessari
									.GetIdSelezionati()
									.Union(
										geProcedimentiEventuali
											.GetIdSelezionati()
									).ToArray();

			multiView.ActiveViewIndex = 1;
		}

		protected void lnkStampaPdf_Click(object sender, EventArgs e)
		{
			var riepilogo = _generazioneRiepilogoDomandaService.GeneraModelloDiDomanda(this.IdIntervento, this.IdEndoSelezionati, GenerazioneRiepilogoDomandaService.FormatoConversioneModello.PDF);

			Response.Clear();
			Response.ContentType = riepilogo.MimeType;
			Response.ContentEncoding = Encoding.Default;
			Response.AddHeader("content-disposition", "attachment;filename=\"modelloDomanda.pdf\"");
			Response.BinaryWrite(riepilogo.FileContent);			
			Response.End();
		}
	}
}