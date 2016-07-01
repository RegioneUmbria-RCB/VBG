using System;
using System.Collections.Generic;
using System.Configuration;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using log4net;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class VisuraExCtrl : System.Web.UI.UserControl
	{
		[Inject]
		public IDettaglioPraticaRepository _visuraRepository { get; set; }
		[Inject]
		public IScadenzeService _scadenzeService { get; set; }
		[Inject]
		public IConfigurazione<ParametriVisura> _configurazione { get; set; }

		public delegate void ScadenzaSelezionataDelegate(object sender, string idScadenza);
		public event ScadenzaSelezionataDelegate ScadenzaSelezionata;

		ILog _log = LogManager.GetLogger(typeof(VisuraExCtrl));


		public bool DaArchivio
		{
			get { object o = this.ViewState["DaArchivio"]; return o == null ? false : (bool)o; }
			set { this.ViewState["DaArchivio"] = value; }
		}


		protected string Token
		{
			get { return Request.QueryString["Token"]; }
		}

		protected string IdComune
		{
			get { return Request.QueryString["IdComune"]; }
		}

		public bool MostraDatiCatastaliEstesi
		{
			get
			{
				var obj = ConfigurationManager.AppSettings["MostraDatiCatastaliEstesi"];

				if (String.IsNullOrEmpty(obj))
					return false;

				try
				{
					return Convert.ToBoolean(obj);
				}
				catch (Exception)
				{
					return false;
				}
			}
		}

		public class DatiRichiedenti
		{
			public string Nominativo{get;set;}
			public string InQualitaDi{get;set;}
			public string NominativoCollegato{get;set;}
			public string Procuratore{get;set;}

			public DatiRichiedenti( Anagrafe soggetto , TipiSoggetto tipoSoggetto , Anagrafe anagrafeCollegata , Anagrafe procuratore)
			{
				Nominativo = soggetto.NOMINATIVO + " " + soggetto.NOME;

				if (tipoSoggetto != null)
					InQualitaDi = tipoSoggetto.TIPOSOGGETTO;

				if (anagrafeCollegata != null)
					NominativoCollegato = anagrafeCollegata.NOMINATIVO + " " + anagrafeCollegata.NOME;

				if (procuratore != null)
					Procuratore = procuratore.NOMINATIVO + " " + procuratore.NOME;
			}
		}

		public Istanze DataSource{get;set;}


		public VisuraExCtrl()
		{
			FoKernelContainer.Inject(this);
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			dgProcedimenti.Columns[0].Visible = !DaArchivio;
		}

		public void EffettuaVisuraIstanza(string idComune, string software, int codiceIstanza)
		{
			dgLocalizzazioni.Columns[3].Visible = MostraDatiCatastaliEstesi;
			dgLocalizzazioni.Columns[4].Visible = MostraDatiCatastaliEstesi;
			dgLocalizzazioni.Columns[5].Visible = MostraDatiCatastaliEstesi;
			dgLocalizzazioni.Columns[6].Visible = MostraDatiCatastaliEstesi;



			Istanze istanza = _visuraRepository.GetById(idComune,codiceIstanza, false);
			
			this.DataSource = istanza;
			this.DataBind();

			try
			{
				if (!IsPostBack)
					ltrIntestazioneDettaglio.Text = _configurazione.Parametri.MessaggioIntestazioneVisura;
			}
			catch (Exception ex1)
			{
				_log.Error(ex1.ToString());
			}

			var listaScadenze = _scadenzeService.GetListaScadenzeByNumeroIstanza( software , istanza.NUMEROISTANZA);

			dgScadenze.DataSource = listaScadenze;
			dgScadenze.DataBind();
		}

		public override void DataBind()
		{
			//try
			//{
				if (DataSource == null)
					return;

				// Response.Write("1<br />");

				lblDataPresentazione.Text = FormattaData(DataSource.DATA);
				lblDataProtocollo.Text = FormattaData(DataSource.DATAPROTOCOLLO);
				lblIntervento.Text = DataSource.Intervento.SC_DESCRIZIONE;
				lblNumeroPratica.Text = DataSource.NUMEROISTANZA;
				lblOggetto.Text = DataSource.LAVORI;//.Replace("\n","<br />");
				lblProtocollo.Text = DataSource.NUMEROPROTOCOLLO;

				// Response.Write("2<br />");

				if (DataSource.Istruttore != null)
					lblIstruttore.Text = DataSource.Istruttore.RESPONSABILE;

				// Response.Write("3<br />");

				if (DataSource.Operatore != null)
					lblOperatore.Text = DataSource.Operatore.RESPONSABILE;

				// Response.Write("4<br />");

				if (DataSource.ResponsabileProc != null)
					lblResponsabileProc.Text = DataSource.ResponsabileProc.RESPONSABILE;

				// Response.Write("5<br />");

				lblStatoPratica.Text = DataSource.Stato.Stato;

				// Response.Write("6<br />");

				// Soggetti dell'istanza
				List<DatiRichiedenti> richiedenti = new List<DatiRichiedenti>();

				richiedenti.Add(new DatiRichiedenti(DataSource.Richiedente, DataSource.TipoSoggetto, DataSource.AziendaRichiedente, null));

				// Response.Write("7<br />");

				if (DataSource.Professionista != null)
				{
					TipiSoggetto ts = new TipiSoggetto();
					ts.TIPOSOGGETTO = "Tecnico";
					richiedenti.Add(new DatiRichiedenti(DataSource.Professionista, ts, null, null));
				}

				// Response.Write("8<br />");

				if (DataSource.Richiedenti != null)
				{
					for (int i = 0; i < DataSource.Richiedenti.Length; i++)
					{
						richiedenti.Add(new DatiRichiedenti(DataSource.Richiedenti[i].Richiedente, DataSource.Richiedenti[i].TipoSoggetto, DataSource.Richiedenti[i].AnagrafeCollegata, DataSource.Richiedenti[i].Procuratore));
					}
				}

				// Response.Write("9<br />");

				dgSoggetti.DataSource = richiedenti;
				dgSoggetti.DataBind();

				// Response.Write("10<br />");

				// Localizzazioni
				dgLocalizzazioni.DataSource = DataSource.Stradario;
				dgLocalizzazioni.DataBind();

				// Response.Write("11<br />");

				// Mappali
				dgDatiCatastali.DataSource = DataSource.Mappali;
				dgDatiCatastali.DataBind();

				// Response.Write("12<br />");

				// Procedimenti
				dgProcedimenti.DataSource = DataSource.EndoProcedimenti;
				dgProcedimenti.DataBind();

				// Response.Write("13<br />");

				// movimenti
				dgMovimenti.DataSource = ElaboraMovimenti(DataSource.Movimenti);
				dgMovimenti.DataBind();

				// Response.Write("14<br />");

				// Autorizzazioni
				dgAutorizzazioni.DataSource = DataSource.Autorizzazioni;
				dgAutorizzazioni.DataBind();

				// Response.Write("15<br />");

				// Oneri
				dgOneri.DataSource = DataSource.Oneri;
				dgOneri.DataBind();

				// Response.Write("16<br />");

				// Allegati
				gvAllegati.DataSource = ElaboraDocumenti(DataSource.DocumentiIstanza);
				gvAllegati.DataBind();
			//}
			//catch (Exception ex)
			//{
			//	Response.Write(ex.ToString());
			//}
		}

		protected  bool VerificaEsistenzaAllegatiProcedimento(object objProcedimento)
		{
			var endo = (IstanzeProcedimenti)objProcedimento;

			if (endo.IstanzeAllegati != null && endo.IstanzeAllegati.Length > 0)
			{
				foreach (var allegatoEndo in endo.IstanzeAllegati)
				{
					if (!String.IsNullOrEmpty(allegatoEndo.CODICEOGGETTO))
						return true;
				}
			}

			return false;
		}

		protected bool VerificaEsistenzaAllegatiMovimento(object objMovimentiAllegatiList)
		{
			var listaAllegati = (MovimentiAllegati[])objMovimentiAllegatiList;

			if (listaAllegati == null || listaAllegati.Length == 0)
				return false;

			for (int i = 0; i < listaAllegati.Length; i++)
			{
				var allegato = listaAllegati[i];

				if (allegato.FlagPubblica.GetValueOrDefault(0) == 0)
					continue;

				if (String.IsNullOrEmpty(allegato.CODICEOGGETTO))
					continue;

				return true;
			}

			return false;
		}


		private IEnumerable<DocumentiIstanza> ElaboraDocumenti(IEnumerable<DocumentiIstanza> documentiIstanza)
		{
			foreach(var documento in documentiIstanza)
			{
				if (!String.IsNullOrEmpty(documento.CODICEOGGETTO))
					yield return documento;
			}
		}

		private IEnumerable<Movimenti> ElaboraMovimenti(IEnumerable<Movimenti> movimenti)
		{
			foreach (var movimento in movimenti)
			{
				if (movimento.PUBBLICA != "1")
					continue;

				if (movimento.PUBBLICAPARERE != "1")
					movimento.PARERE = String.Empty;

				yield return movimento;
			}

		}

		private string FormattaData(DateTime? data)
		{
			return data.HasValue ? data.Value.ToString("dd/MM/yyyy") : "";
		}

		public string EsitoMovimento(object val)
		{
			if (val == null || String.IsNullOrEmpty(val.ToString() ) ) return String.Empty;

			if (val.ToString() == "0") return "Negativo";

			return "Positivo";
		}

		public void dgScadenze_SelectedIndexChanged(object sender, EventArgs e)
		{
			string scadenza = dgScadenze.DataKeys[dgScadenze.SelectedIndex].Value.ToString();

			if (ScadenzaSelezionata != null)
				ScadenzaSelezionata(this, scadenza);
		}

		protected void gvAllegati_RowDataBound(object sender, GridViewRowEventArgs e)
		{
			string downloadAllegatoFmtString = "~/MostraOggetto.ashx?IdComune={0}&CodiceOggetto={1}";

			if (e.Row.RowType == DataControlRowType.DataRow)
			{
				var hlDownloadAllegato = (HyperLink)e.Row.FindControl("hlDownloadAllegato");

				DocumentiIstanza doc = (DocumentiIstanza)e.Row.DataItem;

				hlDownloadAllegato.NavigateUrl = String.Format(downloadAllegatoFmtString,
																Request.QueryString["IdComune"],
																doc.CODICEOGGETTO
																);
			}
		}

		protected string ProcessaDescrizione(object descr)
		{
			if (descr == null) return "";
			return descr.ToString().Replace("\n", "<br />");

		}
	}
}