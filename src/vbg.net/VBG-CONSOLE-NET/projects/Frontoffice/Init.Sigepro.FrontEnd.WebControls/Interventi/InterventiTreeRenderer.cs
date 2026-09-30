using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Web.UI;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.WebControls.Interventi
{
	public class InterventiTreeRenderer: WebControl, INamingContainer
	{
		public delegate void FogliaAlberoSelezionataDelegate(object sender, int idNodo);
		public event FogliaAlberoSelezionataDelegate FogliaAlberoSelezionata;

		Dictionary<int, LinkButton> m_linkButtons = new Dictionary<int, LinkButton>();
		public NodoAlberoInterventiDto DataSource { get; set; }

		private RappresentazioneAlberoInterventi m_rappresentazioneInterna;

		public bool StartCollapsed
		{
			get { object o = this.ViewState["Collapsed"]; return o == null ? true : (bool)o; }
			set { this.ViewState["Collapsed"] = value; }
		}

		public string UrlDettagliIntervento
		{
			get { object o = this.ViewState["UrlDettagliIntervento"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["UrlDettagliIntervento"] = value; }
		}

		public string UrlIconaHelp
		{
			get { object o = this.ViewState["UrlIconaHelp"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["UrlIconaHelp"] = value; }
		}

		public bool MostraNote
		{
			get { object o = this.ViewState["MostraNote"]; return o == null ? true : (bool)o; }
			set { this.ViewState["MostraNote"] = value; }
		}

		public bool Interactive
		{
			get { object o = this.ViewState["Interactive"]; return o == null ? true : (bool)o; }
			set { this.ViewState["Interactive"] = value; }
		}

		public bool HideFolders
		{
			get { object o = this.ViewState["HideFolders"]; return o == null ? false : (bool)o; }
			set { this.ViewState["HideFolders"] = value; }
		}





		public override void DataBind()
		{
			m_linkButtons = new Dictionary<int, LinkButton>();
			m_rappresentazioneInterna = new RappresentazioneAlberoInterventi();

			var rootNode = new NodoAlberoInterventi
			{
				Id = -1,
				Descrizione = "Root",
			};

			rootNode.NodiFiglio = PopolaNodiFiglio(DataSource.NodiFiglio);

			m_rappresentazioneInterna.NodoRoot = rootNode;

			CreaLinkButtons();
		}

		private void CreaLinkButtons()
		{
			if (m_rappresentazioneInterna == null)
				return;

			var root = m_rappresentazioneInterna.NodoRoot;

			CreaLinkButtonsNodiFiglio(root.NodiFiglio);


		}

		private void CreaLinkButtonsNodiFiglio(List<NodoAlberoInterventi> list)
		{
			list.ForEach(x =>
			{
				if (x.NodiFiglio.Count == 0)
				{

					var lb = new LinkButton
					{
						ID = "lb" + x.Id.ToString(),
						Text = x.Descrizione,
						CommandArgument = x.Id.ToString()
					};

					lb.Click += new EventHandler(lb_Click);

					this.Controls.Add(lb);

					m_linkButtons.Add(x.Id, lb);
				}
				else
				{
					CreaLinkButtonsNodiFiglio(x.NodiFiglio);
				}
			});
		}

		void lb_Click(object sender, EventArgs e)
		{
			var lb = (LinkButton)sender;
			int id = Convert.ToInt32(lb.CommandArgument);

			if (FogliaAlberoSelezionata != null)
				FogliaAlberoSelezionata(this, id);
		}

		private List<NodoAlberoInterventi> PopolaNodiFiglio(ClassTreeOfInterventoDto[] nodiFiglio)
		{
			var rVal = new List<NodoAlberoInterventi>();

			for (int i = 0; i < nodiFiglio.Length; i++)
			{
				var el = nodiFiglio[i];
				var nodo = new NodoAlberoInterventi
				{
					Id = el.Elemento.Codice,
					Descrizione = el.Elemento.Descrizione,
					NotePresenti = !String.IsNullOrEmpty(el.Elemento.Note),
					NodiFiglio = PopolaNodiFiglio(el.NodiFiglio)
				};

				rVal.Add(nodo);
			}

			return rVal;
		}

		protected override void OnInit(EventArgs e)
		{
			base.OnInit(e);
		}


		protected override void RenderContents(HtmlTextWriter writer)
		{
			writer.AddAttribute("class", "filetree");
			writer.RenderBeginTag(HtmlTextWriterTag.Ul);

			RenderSubNodes(m_rappresentazioneInterna.NodoRoot.NodiFiglio, writer);

			writer.RenderEndTag();
			
			//base.RenderContents(writer);
		}

		private void RenderSubNodes(List<NodoAlberoInterventi> nodiFiglio, HtmlTextWriter writer)
		{
			for (int i = 0; i < nodiFiglio.Count; i++)
			{
				var el = nodiFiglio[i];

				//if (StartCollapsed)
				//    writer.AddAttribute("class", "closed");
				//else
				//    writer.AddAttribute("class", "open");

				writer.RenderBeginTag(HtmlTextWriterTag.Li);

				if (el.NodiFiglio.Count > 0)
				{
					writer.AddAttribute("class", "folder");
					writer.AddAttribute("idIntervento", el.Id.ToString());
					writer.RenderBeginTag(HtmlTextWriterTag.Span);

					if (!HideFolders)
					{
                        writer.AddAttribute("class", "glyphicon glyphicon-folder-open");
						writer.RenderBeginTag(HtmlTextWriterTag.I);
						writer.RenderEndTag();
					}

					RenderNoteNodo(writer, el);

                    writer.AddAttribute("style", "margin-left:9px");
                    writer.RenderBeginTag(HtmlTextWriterTag.Span);
					writer.Write(el.Descrizione);
                    writer.RenderEndTag();


					writer.RenderEndTag();
					

					writer.RenderBeginTag(HtmlTextWriterTag.Ul);

					RenderSubNodes(el.NodiFiglio, writer);

					writer.RenderEndTag();
				}
				else
				{
					writer.AddAttribute("class", "file");
					writer.AddAttribute("idIntervento", el.Id.ToString());
					writer.RenderBeginTag(HtmlTextWriterTag.Span);

					if (!HideFolders)
					{
                        writer.AddAttribute("class", "glyphicon glyphicon-list-alt");
						writer.RenderBeginTag(HtmlTextWriterTag.I);
						writer.RenderEndTag();
					}

					RenderNoteNodo(writer, el);

                    writer.AddAttribute("style", "margin-left:9px");
                    writer.RenderBeginTag(HtmlTextWriterTag.Span);

                    if (Interactive)
                        m_linkButtons[el.Id].RenderControl(writer);
                    else
                        writer.Write(el.Descrizione);


                    writer.RenderEndTag();




					 
					writer.RenderEndTag();

					
				}
				
				writer.RenderEndTag();
			}
		}

		private string GetBaseUrl()
		{
			var req = HttpContext.Current.Request;
			var baseUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

			if (!String.IsNullOrEmpty(req.ApplicationPath))
				baseUrl += req.ApplicationPath;

			if (!baseUrl.EndsWith("/"))
				baseUrl += "/";

			return baseUrl;
		}

		private void RenderNoteNodo(HtmlTextWriter writer, NodoAlberoInterventi el)
		{
			if (!el.NotePresenti || !MostraNote)
				return;

			var href = PreparaUrlIntervento(el.Id);
			var onclick = "";

			if (String.IsNullOrEmpty(href))
				href = "#";

			if (!href.ToLower().StartsWith("http:"))
			{
				onclick = href;
				href = "#";				
			}

			writer.AddAttribute("href", href);

			if(!String.IsNullOrEmpty(onclick))
				writer.AddAttribute("onclick", onclick);

			writer.AddAttribute("class", "noteIntervento");
			writer.RenderBeginTag(HtmlTextWriterTag.A);

            writer.AddAttribute("class", "glyphicon glyphicon-question-sign");
			writer.RenderBeginTag(HtmlTextWriterTag.I);
			writer.RenderEndTag();

			writer.RenderEndTag();
		}

		private string PreparaUrlIntervento(int p)
		{
			if (String.IsNullOrEmpty(UrlDettagliIntervento))
				return String.Empty;

			var url = UrlDettagliIntervento;

			if (UrlDettagliIntervento.StartsWith("~") || UrlDettagliIntervento.ToLower().StartsWith("http"))
			{

				var uar = (UserAuthenticationResult)HttpContext.Current.Items["UserAuthenticationResult"];
				if (uar != null)
				{
					url += "&IdComune=" + HttpContext.Current.Items["IdComune"].ToString();
					url += "&Token=" + uar.Token;
					//url += "&Software=" + HttpContext.Current.Items["Software"].ToString();
				}
				else
				{
					url += "&IdComune=" + HttpContext.Current.Request.QueryString["IdComune"].ToString();
					//url += "&Software=" + HttpContext.Current.Request.QueryString["Software"].ToString();	
				}

				var fullUrl = String.Format(url, p);

				return UrlDettagliIntervento.StartsWith("~") ? Page.ResolveClientUrl(fullUrl) : fullUrl;
			}

			return String.Format(url, p);
		}

		protected override object SaveViewState()
		{
			object a = base.SaveViewState();
			object b = m_rappresentazioneInterna;

			return new Pair(a, b);
		}

		protected override void LoadViewState(object savedState)
		{
			var p = (Pair)savedState;

			base.LoadViewState(p.First);

			m_rappresentazioneInterna = (RappresentazioneAlberoInterventi)p.Second;

			CreaLinkButtons();
		}
	}
}
