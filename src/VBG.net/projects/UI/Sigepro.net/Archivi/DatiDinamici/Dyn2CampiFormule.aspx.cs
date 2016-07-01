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
using SIGePro.Net;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using Sigepro.net.AdminSecurity;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.DatiDinamici.Scripts;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.Contesti;

public partial class Archivi_DatiDinamici_Dyn2CampiFormule : BasePage
{
	public string IdCampo
	{
		get { return Request.QueryString["IdCampo"].ToString(); }
	}

	Dyn2Campi m_campo;
	private Dyn2Campi Campo
	{
		get
		{
			if (m_campo == null)
			{
				if (string.IsNullOrEmpty(IdCampo)) return null;

				Dyn2CampiMgr mgr = new Dyn2CampiMgr(Database);
				m_campo = mgr.GetById(IdComune, Convert.ToInt32(IdCampo));
			}
			return m_campo;
		}
	}

	public override string Software
	{
		get
		{
			return Campo.Software;
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
		if (Campo == null)
			throw new ArgumentException("Il codice campo " + IdCampo + " non è valido");

		lblCampoCorrente.Text = Campo.Nomecampo;

		Dyn2CampiScriptMgr mgr = new Dyn2CampiScriptMgr(Database);

        Dyn2CampiScript script = mgr.GetById(IdComune, Campo.Id.GetValueOrDefault(int.MinValue), ddlEvento.Value);

		txtScript.Text = script == null ? String.Empty : script.GetTestoScript();
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
		var contesto = new ContestoModelloDinamico(Token, ContestoTranslator.ContestoBaseToContestoEnum( Campo.FkD2bcId ), null);

		var script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, txtScript.Text);

		Session["CODICE_SCRIPT"] = script.GetCodiceScript();

		var jsScript = "window.open('" + GetUrlAssolutoPagina() + "&VisualizzaClasse=true')";

		this.Page.ClientScript.RegisterStartupScript(this.GetType(), "mostraScript", jsScript, true);
	}

	protected string GetUrlAssolutoPagina()
	{
		var url = ResolveClientUrl("~/Archivi/DatiDinamici/Dyn2CampiFormule.aspx") + "?" + Request.QueryString;

		return url;
	}

	protected void cmdCompila_Click(object sender, EventArgs e)
	{
		VerificaCompilazioneFormule();
	}

	private bool VerificaCompilazioneFormule()
	{
		ContestoModelloDinamico contesto = new ContestoModelloDinamico(Token, ContestoTranslator.ContestoBaseToContestoEnum( Campo.FkD2bcId ), null);

		ScriptCampoDinamico script = null;

		if (!String.IsNullOrEmpty(txtScript.Text.Trim()))
		{
			script = ScriptCampoDinamico.CreaScriptDesignTime(contesto, txtScript.Text);

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

	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		if (!VerificaCompilazioneFormule()) return;

		bool bInsert = false;

		Dyn2CampiScriptMgr mgr = new Dyn2CampiScriptMgr(Database);

		Dyn2CampiScript script = mgr.GetById( IdComune , Campo.Id.GetValueOrDefault(int.MinValue) , ddlEvento.Value );
		
		if (script == null)
		{
			script = new Dyn2CampiScript();
			script.Idcomune = IdComune;
			script.FkD2cId = Campo.Id;
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
		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2Campi.aspx?Token={0}&Software={1}&IdCampo={2}&Popup={3}";
		Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, Campo.Software , IdCampo , IsInPopup));
	}

	protected void AdminAuthenticationOk(object sender, EventArgs e)
	{
		string fmtUrl = "~/Archivi/DatiDinamici/Dyn2CampiFormule.aspx?Token={0}&IdCampo={1}&Popup={2}";
		Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, IdCampo, IsInPopup));
	}

	protected void AdminAuthenticationKo(object sender, EventArgs e)
	{
		cmdChiudi_Click(sender, EventArgs.Empty);
	}

	protected void ddlEvento_ValueChanged(object sender, EventArgs e)
	{
		DataBind();
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
