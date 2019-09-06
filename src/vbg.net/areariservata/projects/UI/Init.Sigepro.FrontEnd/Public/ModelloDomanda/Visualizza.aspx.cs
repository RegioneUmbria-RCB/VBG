using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Ninject;
using System;
using System.Linq;
using System.Text;

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
			var riepilogo = _generazioneRiepilogoDomandaService.GeneraFacSimileDomanda(this.IdIntervento, this.IdEndoSelezionati);

			Response.Clear();
			Response.ContentType = riepilogo.MimeType;
			Response.ContentEncoding = Encoding.Default;
			Response.AddHeader("content-disposition", "attachment;filename=\"fac-simile-domanda.pdf\"");
			Response.BinaryWrite(riepilogo.FileContent);			
			Response.End();
		}
	}
}