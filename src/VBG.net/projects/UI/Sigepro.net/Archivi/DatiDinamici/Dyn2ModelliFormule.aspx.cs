using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Data;
using SIGePro.Net;
using Init.SIGePro.Manager;
using Sigepro.net.AdminSecurity;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using System.Collections.Generic;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Contesti;

namespace Sigepro.net.Archivi.DatiDinamici
{
	public partial class Dyn2ModelliFormule : BasePage
	{
		public string IdModello
		{
			get { return Request.QueryString["IdModello"].ToString(); }
		}

		Dyn2ModelliT m_modello;
		private Dyn2ModelliT Modello
		{
			get
			{
				if (m_modello == null)
				{
					if (string.IsNullOrEmpty(IdModello)) return null;

					Dyn2ModelliTMgr mgr = new Dyn2ModelliTMgr(Database);
					m_modello = mgr.GetById(IdComune, Convert.ToInt32(IdModello));
				}
				return m_modello;
			}
		}

		public bool VisualizzaClasse
		{
			get
			{
				var qs = Request.QueryString["VisualizzaClasse"];

				if (String.IsNullOrEmpty(qs))
					return false;

				return Convert.ToBoolean(qs);
			}
		}

		public override string Software
		{
			get
			{
				return Modello.Software;
			}
		}

		public Dyn2ModelliFormule()
		{
			//VerificaSoftware = false;
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
//				if (!AdminSecurityManager.IsCurrentUserAdmin)
//					ShowLogonScreen();

				if (VisualizzaClasse)
				{
					MostraClasseGenerata();
					return;
				}

				ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Caricamento), TipoScriptEnum.Caricamento.ToString()));
				ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Modifica), TipoScriptEnum.Modifica.ToString()));
				ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Salvataggio), TipoScriptEnum.Salvataggio.ToString()));
				ddlEvento.Item.Items.Add(new ListItem(NomeTipoContestoScript.Get(TipoScriptEnum.Funzioni), TipoScriptEnum.Funzioni.ToString()));

				ddlEvento.Value = TipoScriptEnum.Caricamento.ToString();

				DataBind();

				this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
			}
		}


		private void ShowLogonScreen()
		{
			multiView.ActiveViewIndex = 0;
		}

		public override void DataBind()
		{
			if (Modello == null)
				throw new ArgumentException("Il codice modello " + IdModello + " non è valido");

			Dyn2ModelliScriptMgr mgr = new Dyn2ModelliScriptMgr(Database);
            Dyn2ModelliScript script = mgr.GetById(IdComune, Modello.Id.GetValueOrDefault(int.MinValue), ddlEvento.Value);

			txtScript.Text = (script == null) ? String.Empty : script.GetTestoScript();
		}

		private void MostraClasseGenerata()
		{
			var script = Session["CODICE_SCRIPT"] == null ? String.Empty : Session["CODICE_SCRIPT"].ToString();

			Response.Clear();
			Response.AddHeader("content-disposition", "attachment;filename=CodiceScript.txt;");
			Response.ContentType = "text/plain";
			Response.Write(script);
			Response.End();
		}

		protected void cmdVisualizzaClasse_Click(object sender, EventArgs e)
		{
			var contesto = new ContestoModelloDinamico(Token, ContestoTranslator.ContestoBaseToContestoEnum( Modello.FkD2bcId ), null);

			var script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, txtScript.Text);

			Session["CODICE_SCRIPT"] = script.GetCodiceScript();

			var jsScript = "window.open('" + GetUrlAssolutoPagina() + "&VisualizzaClasse=true')";

			this.Page.ClientScript.RegisterStartupScript(this.GetType(), "mostraScript", jsScript, true);
		}


		protected void cmdCompila_Click(object sender, EventArgs e)
		{
			VerificaCompilazioneFormule();
		}

		private bool VerificaCompilazioneFormule()
		{
			var contesto = new ContestoModelloDinamico(Token, ContestoTranslator.ContestoBaseToContestoEnum(Modello.FkD2bcId), null);

			ScriptCampoDinamico script = null;

			if (!String.IsNullOrEmpty(txtScript.Text.Trim()))
			{
				var funzioniCondivise = ddlEvento.Item.SelectedValue == TipoScriptEnum.Funzioni.ToString();

				if (funzioniCondivise)
				{
					script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, string.Empty, txtScript.Text);
				}
				else
				{
					Dyn2ModelliScriptMgr mgr = new Dyn2ModelliScriptMgr(Database);
					var fc = mgr.GetById(IdComune, Modello.Id.GetValueOrDefault(int.MinValue), TipoScriptEnum.Funzioni.ToString() );

					var scriptCondivisi = (fc != null) ? fc.GetTestoScript() : String.Empty;

					script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, txtScript.Text, scriptCondivisi);
				}

				try
				{
					script.Compila();
				}
				catch (Exception ex)
				{
					MostraErrore(ex.Message, ex);
					txtScript.Focus();
					return false;
				}
			}


			return true;
		}
		/*
		private bool VerificaCompilazioneFormule()
		{
			var contesto = new ContestoModelloDinamico(Token, ContestoTranslator.ContestoBaseToContestoEnum( Modello.FkD2bcId ), null);

			ScriptCampoDinamico script = null;			
			
			if (!String.IsNullOrEmpty(txtScript.Text.Trim()))
			{
				var funzioniCondivise = ddlEvento.Item.SelectedValue == TipoScriptEnum.FunzioniCondivise.ToString();

				if (funzioniCondivise)
				{
					script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, string.Empty, txtScript.Text);
				}
				else
				{
					script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, txtScript.Text);
				}

				try
				{
					script.Compila();
				}
				catch (Exception ex)
				{
					MostraErrore("Errore durante la compilazione della formula del modello: " + ex.ToString(), ex);
					txtScript.Focus();
					return false;
				}
			}
			

			return true;
		}
		*/
		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			if (!VerificaCompilazioneFormule()) return;

			bool bInsert = false;

			Dyn2ModelliScriptMgr mgr = new Dyn2ModelliScriptMgr(Database);
            Dyn2ModelliScript script = mgr.GetById(IdComune, Modello.Id.GetValueOrDefault(int.MinValue), ddlEvento.Value);

			if (script == null)
			{
				script = new Dyn2ModelliScript();
				script.Idcomune = IdComune;
				script.FkD2mtId = Modello.Id;
				script.Evento = ddlEvento.Value;

				bInsert = true;
			}

			script.SetTestoScript( txtScript.Text );

			try
			{
				if (bInsert)
					mgr.Insert(script);
				else
					mgr.Update(script);

				string js = "alert('Formula salvata correttamente.');";
				Page.ClientScript.RegisterStartupScript(this.GetType(), "loadScript", js, true);

				DataBind();
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
			}

		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";
			Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, Modello.Software, IdModello));
		}

		protected void AdminAuthenticationOk(object sender, EventArgs e)
		{
			string fmtUrl = "~/Archivi/DatiDinamici/Dyn2ModelliFormule.aspx?Token={0}&IdModello={1}";
			Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, IdModello));
		}

		protected void AdminAuthenticationKo(object sender, EventArgs e)
		{
			string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Modelli.aspx?Token={0}&Software={1}&IdModello={2}";
			Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, Modello.Software, IdModello));
		}

		protected void ddlEvento_ValueChanged(object sender, EventArgs e)
		{
			DataBind();
		}

		protected string GetUrlAssolutoPagina()
		{
			var url = ResolveClientUrl("~/Archivi/DatiDinamici/Dyn2ModelliFormule.aspx") + "?" + Request.QueryString;

			return url;
		}

		protected override void MostraErrore(Exception ex)
		{
			
		}

		protected override void MostraErrore(string messaggio, Exception ex)
		{
			var errMsg = messaggio;

			if (String.IsNullOrEmpty(errMsg))
				errMsg = ex.ToString();

			errMsg = errMsg.Replace("\r", "");
			errMsg = errMsg.Replace("\n", "\\n");
			errMsg = errMsg.Replace("'", "\\'");

			Page.ClientScript.RegisterStartupScript(this.GetType(), "errorMessage", "alert(\"" + errMsg + "\");", true);

		}
	}
}
