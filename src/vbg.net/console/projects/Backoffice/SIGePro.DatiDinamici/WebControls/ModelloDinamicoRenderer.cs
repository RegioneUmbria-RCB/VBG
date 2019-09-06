using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using System.Web;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;
using Init.Utils;
using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;
using Init.SIGePro.DatiDinamici.Exceptions;
using Init.SIGePro.DatiDinamici.ValidazioneValoriCampi;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	[ToolboxData("<{0}:ModelloDinamicoRenderer runat=server />")]
	public partial class ModelloDinamicoRenderer : WebControl, INamingContainer
	{
		public static class Constants
		{
			/// <summary>
			/// Chiave di sessione in cui viene conservato il modello utilizzato
			/// </summary>
			public const string SessionKey = "ModelloDinamicoRenderer::SessionData";
			public const string MascheraInSolaLetturaSessionKey = "ModelloDinamicoRenderer::MascheraSolaLettura";
			public const string CampiNonVisibiliSessionKey = "ModelloDinamicoRenderer::CampiNonVisibili";
		}


		

		///// <summary>
		///// Evento generato quando vengono modificati uno o più valori del modello
		///// </summary>
		//public event EventHandler ValoriModificati;

		#region gestione degli errori
		/// <summary>
		/// Lista contenente eventuali errori di rendering
		/// </summary>
		List<string> m_renderingErrors = new List<string>();

		protected List<string> ErroriRendering
		{
			get { return m_renderingErrors; }
		}



		/// <summary>
		/// Lista contenente eventuali errori di validazione
		/// </summary>
		protected IEnumerable<ErroreValidazione> ErroriValidazione
		{
			get;set;
		}
		/*
		public List<string> ListaErrori
		{
			get
			{
				List<string> errori = new List<string>(ErroriRendering.Count + ErroriValidazione.Count);
				errori.AddRange(ErroriRendering);
				errori.AddRange(ErroriValidazione);

				return errori;
			}
		}
		*/
		///// <summary>
		///// Visualizza la lista di tutti gli errori di validazione o di rendering
		///// </summary>
		//private void VisualizzaErrori()
		//{
		//    m_errorPanel.Controls.Clear();

		//    List<string> errori = new List<string>( ErroriRendering.Count + ErroriValidazione.Count );
		//    errori.AddRange(ErroriRendering);
		//    errori.AddRange(ErroriValidazione);

		//    BulletedList ul = new BulletedList();

		//    m_errorPanel.Controls.Add(ul);

		//    ul.BulletStyle = BulletStyle.Circle;
		//    ul.DataSource = errori;
		//    ul.DataBind();
		//}


		#endregion

		/// <summary>
		/// True se almeno uno dei campi del modello contiene modeifiche
		/// </summary>
		public bool ContieneModifiche
		{
			get { return DataSource != null && DataSource.CampiModificati.Count > 0; }
		}


		/// <summary>
		/// Token da utilizzare per la creazione di database
		/// </summary>
		public string Token
		{
			get { object o = this.ViewState["Token"]; return o == null ? HttpContext.Current.Request.QueryString["Token"] : (string)o; }
			set { this.ViewState["Token"] = value; }
		}


		/// <summary>
		/// Imposta o legge se la scheda visualizzata è in sola lettura
		/// </summary>
		public bool ReadOnly
		{
			get { object o = this.ViewState["ReadOnly"]; return o == null ? false : (bool)o; }
			set 
			{ 
				this.ViewState["ReadOnly"] = value;
				ImpostaMascheraSolaLettura(value ? (IMascheraSolaLettura)new MascheraSolaLetturaCompleta() : (IMascheraSolaLettura)new MascheraSolaLetturaVuota());
			}
		}

		///// <summary>
		///// Imposta o legge se la scheda deve effettuare il rendering in modalità compatibile con OpenOffice
		///// </summary>
		//public bool CompatibilitaOpenOffice
		//{
		//	get { object o = this.ViewState["CompatibilitaOpenOffice"]; return o == null ? false : (bool)o; }
		//	set { this.ViewState["CompatibilitaOpenOffice"] = value; }
		//}

		public bool Preview
		{
			get { object o = this.ViewState["Preview"]; return o == null ? false : (bool)o; }
			set { this.ViewState["Preview"] = value; }
		}



		// CONTROLLI FIGLIO
		Panel _renderingPanel = new Panel();	// tabella utilizzata per contenere la vista del modello
		//Panel m_errorPanel = new Panel();		// pannello che contiene la lista di errori del modello

        private string GetSessionKeyMascheraLettura()
        {
            if (DataSource == null)
            {
                throw new Exception("Maschera lettura impostata prima della datasource: ModelloDinamicoRenderer");                
            }

            return Constants.MascheraInSolaLetturaSessionKey + "_" + DataSource.IdModello;
        }
        
		protected IMascheraSolaLettura MascheraSolaLettura
		{
			get 
			{
				return GetSessionVal<IMascheraSolaLettura>(Constants.MascheraInSolaLetturaSessionKey, new MascheraSolaLetturaVuota());
			}
			set
			{
				SetSessionVal(Constants.MascheraInSolaLetturaSessionKey, value);
			}
		}

		public ICampiNonVisibili CampiNascosti
		{
			get
			{
				return GetSessionVal<ICampiNonVisibili>(Constants.CampiNonVisibiliSessionKey, CampiNonVisibili.TuttiICampiVisibili);
			}
			set
			{
				SetSessionVal(Constants.CampiNonVisibiliSessionKey, value);
			}
		}


		#region gestione dei valori salvati in sessione
		public static ModelloDinamicoBase DataSourceAttuale
		{
			get { return GetSessionVal<ModelloDinamicoBase>(Constants.SessionKey); }
		}

		public ModelloDinamicoBase DataSource
		{
			get { return GetSessionVal<ModelloDinamicoBase>(Constants.SessionKey); }
			set { SetSessionVal(Constants.SessionKey, value); }
		}

		/// <summary>
		/// Wrapper, salva in sessione il modello visualizzato
		/// </summary>
		/// <param name="value"></param>
		static void SetSessionVal<T>(string sessionKey, T value)
		{
			if (HttpContext.Current != null)
				HttpContext.Current.Session[sessionKey] = value;
		}

		public delegate ModelloDinamicoBase RicaricaModelloDinamicoDelegate(object sender, EventArgs e);
		public event RicaricaModelloDinamicoDelegate RicaricaModelloDinamico;

		/// <summary>
		/// Wrapper, legge dalla sessione il modello visualizzato
		/// </summary>
		/// <returns></returns>
		private static T GetSessionVal<T>(string sessionKey, T defaultValue = default(T))
		{
			T currValue = default(T);

			if (HttpContext.Current != null)
				currValue = (T)HttpContext.Current.Session[sessionKey];

			return currValue == null ? defaultValue : currValue;
		}
		#endregion


		///// <summary>
		///// Lista contenente gli id lato server dei controlli contenuti nella vista del modello
		///// </summary>
		//public List<string> IdControlliDinamici
		//{
		//    get { object o = this.ViewState["IdControlliDinamici"]; return o == null ? new List<string>() : (List<string>)o; }
		//    set { this.ViewState["IdControlliDinamici"] = value; }
		//}

		/// <summary>
		/// Costruttore :P
		/// </summary>
		public ModelloDinamicoRenderer()
			: base(HtmlTextWriterTag.Div)
		{
			this.ErroriValidazione = new List<ErroreValidazione>();
			//m_errorPanel.ID = "_errPanel";
			//m_errorPanel.CssClass = "ErroriDatiDinamici";
			//m_errorPanel.Controls.Clear();

//			m_table.ID = "_table";

			//this.Controls.Add(m_errorPanel);
			this.Controls.Add(_renderingPanel);


			this.Load += new EventHandler(ModelloDinamicoRenderer_Load);
		}

		/// <summary>
		/// Handler dell'evento load
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		void ModelloDinamicoRenderer_Load(object sender, EventArgs e)
		{
			//if (this.Page.IsPostBack)
			//	ResyncDataSource();
		}


		/// <summary>
		/// Risincronizza i valori della vista con quelli dei campi del modello
		/// </summary>
		private void ResyncDataSource()
		{
			if (DataSource == null) return;

			foreach (IDatiDinamiciControl ctrl in GetControlliDinamiciRicorsivo(this))
			{
				CampoDinamicoBase campo = DataSource.TrovaCampoDaId(ctrl.IdCampoCollegato);

				if (campo != null /*&& campo.TipoCampo != TipoControlloEnum.Testo && campo.TipoCampo != TipoControlloEnum.Titolo*/)
				{
					campo.ListaValori[ctrl.Indice].Valore = ctrl.Valore;
					ctrl.Valore = campo.ListaValori[ctrl.Indice].Valore;
				}
			}
		}

		private IEnumerable<IDatiDinamiciControl> GetControlliDinamiciRicorsivo(Control control)
		{
			foreach (Control c in control.Controls)
			{
				if (c is IDatiDinamiciControl)
					yield return c as IDatiDinamiciControl;

				if (c.Controls.Count > 0)
				{
					foreach (IDatiDinamiciControl ctrl in GetControlliDinamiciRicorsivo( c ))
					{
						yield return ctrl;
					}
				}
			}

		}

		public void ImpostaMascheraSolaLettura(IMascheraSolaLettura mascheraSolaLettura)
		{
			this.MascheraSolaLettura = mascheraSolaLettura;
		}

		/// <summary>
		/// Effettua il binding dei dati. Al termine dell'operazione nella proprietà RenderingErrors saranno disponibiligli eventuali errori di rendering
		/// </summary>
		public override void DataBind()
		{
			if (DataSource == null) return;

			m_renderingErrors = new List<string>();

			if (Preview)
				PopolaValoriPreview();

			Action<int> callbackAggiuntaRighe = (idGruppo) => { IncrementaMolteplicitaRighe(idGruppo); };
			Action<int,int> callbackEliminazioneRighe = (idGruppo, indice) => { EliminaValoriBlocco(idGruppo, indice); };

			var renderer = new ModelloDinamicoTableRenderer(this.DataSource, this.MascheraSolaLettura, callbackAggiuntaRighe, callbackEliminazioneRighe);

			renderer.SetCampiNonVisibili(this.CampiNascosti);


			renderer.ErroreCreazioneControllo += delegate(string msg) { m_renderingErrors.Add(msg); };
			renderer.RenderTo( _renderingPanel );

			base.DataBind();
			
			if (ErroriRendering.Count > 0)
			{
				StringBuilder sb = new StringBuilder("alert(\"Si sono verificati errori durante il rendering dei controlli:\\n");

				foreach (string s in ErroriRendering)
				{
					var err = s.Replace("\"", "'");

					sb.Append("- ").Append(err).Append("\\n");
				}

				sb.Append("\");");

				if (this.Page != null)
				{
					this.Page.ClientScript.RegisterStartupScript(this.GetType(), "ErroriRendering", sb.ToString(), true);
				}
			}
		}

		private void PopolaValoriPreview()
		{
			foreach (var riga in  DataSource.Righe)
			{
				for (int colIdx = 0; colIdx < riga.NumeroColonne; colIdx++)
				{
					CampoDinamicoBase cdb = riga[colIdx];

					if (cdb is CampoDinamicoTestuale || cdb == null)
						continue;

					if (riga.TipoRiga != TipoRigaEnum.Singola)
					{
						if (cdb.ListaValori.Count == 0)
						{
							cdb.ListaValori.IncrementaMolteplicita();
						}

                        cdb.ListaValori[0].Valore = "[" + cdb.Id.ToString() + " - " + cdb.NomeCampo + "]";
					}
					else
					{
						while (cdb.ListaValori.Count < 3)
						{
							cdb.ListaValori.IncrementaMolteplicita();
						}

						for (int i = 0; i < 3; i++)
						{
							cdb.ListaValori[i].Valore = "[" + cdb.Id.ToString() + " - " + cdb.NomeCampo + "(" + i + ")]";
						}
					}

				}

			}
		}


		/// <summary>
		/// Override del prerender, solleva l'evento valori modificati nel caso in cui alcuni dei 
		/// campi siano stati modificati dall'utente
		/// </summary>
		/// <param name="e"></param>
		protected override void OnPreRender(EventArgs e)
		{
			ErroriValidazione = new List<ErroreValidazione>();

			if (DataSource != null)
			{
				try
				{
					DataSource.ValidaModello();
				}
				catch (ValidazioneModelloDinamicoException ex)
				{
					ErroriValidazione = ex.ListaErrori;
				}
			}

			//VisualizzaErrori();

			// Gestione del design mode (crea una tabella vuota con scritto [ModelloDinamicoRender])
			if (this.DesignMode)
			{
				Label globalLabel = new Label();
				globalLabel.Text = "[ModelloDinamicoRender]";

				_renderingPanel = new Panel();

				for (int i = 0; i < 5; i++)
				{
                    var designModeLiteral = new Literal
                    {
                        Text = "[Modello dinamico rendering target]"
                    };
					_renderingPanel.Controls.Add(designModeLiteral);
				}
			}

			base.OnPreRender(e);
		}


		public override void RenderBeginTag(HtmlTextWriter writer)
		{
			writer.AddAttribute("class", "DatiDinamici d2-corpo-modello");
			base.RenderBeginTag(writer);
		}

		public bool SalvaModello()
		{
			if (DataSource == null) return true;

			try
			{
				DataSource.ValidaModello();
			}
			catch (ValidazioneModelloDinamicoException ex)
			{
				ErroriValidazione = ex.ListaErrori;

				return false;
			}

			DataSource.Salva();

			return true;
		}

		internal void IncrementaMolteplicitaRiga(int indiceRiga)
		{
			DataSourceAttuale.Righe.ElementAt(indiceRiga).IncrementaMolteplicita();

			DataBind();
		}

		internal void IncrementaMolteplicitaRighe(int idGruppo)
		{
			DataSourceAttuale.Gruppi.GetRaggruppamentoById(idGruppo).IncrementaMolteplicita();			

			DataBind();
		}

		internal void EliminaValoriBlocco(int idGruppo, int indiceValori)
		{
			DataSourceAttuale.Gruppi.GetRaggruppamentoById(idGruppo).EliminaValoriAllIndice(indiceValori);

			DataBind();
		}

		#region gestione del viewstate
		protected override object SaveViewState()
		{
			KeyValuePair<string, object> vs = new KeyValuePair<string, object>("pippo", base.SaveViewState());
			return vs;
		}

		protected override void LoadViewState(object savedState)
		{
			KeyValuePair<string, object> vs = (KeyValuePair<string, object>)savedState;
			base.LoadViewState(vs.Value);

			DataBind();
		}
		#endregion
	
	}
}
