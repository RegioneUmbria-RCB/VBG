using System;
using System.Linq;
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
using SIGePro.Net.Navigation;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions.IstanzeAllegati;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using System.Web.Script.Services;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Sigepro.net.Istanze.DatiDinamici
{
	public partial class AnagrafeDyn2Modelli : BasePage
	{
		#region Gestione della classe Anagrafe
		protected string CodiceAnagrafe
		{
			get { return Request.QueryString["CodiceAnagrafe"]; }
		}

		Anagrafe m_anagrafe;
		Anagrafe Anagrafe
		{
			get
			{
				if (m_anagrafe == null)
					m_anagrafe = new AnagrafeMgr(Database).GetById(CodiceAnagrafe, IdComune);

				return m_anagrafe;
			}
		}

		public override string Software
		{
			get
			{
				return "TT";
			}
		}
		#endregion


		#region ciclo di vita della pagina

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
		}

		#endregion

		#region handler degli eventi del controllo di gestione modelli

		public List<ElementoListaModelli> GetListaModelli()
		{
			List<ElementoListaModelli> modelli = new AnagrafeDyn2ModelliTMgr(Database).GetModelliCollegati(IdComune, Convert.ToInt32(CodiceAnagrafe)); //new IstanzeDyn2ModelliTMgr(Database).GetModelliIstanza(IdComune, Convert.ToInt32(CodiceIstanza), CodiceMovimento);

			return modelli;
		}


		public ModelloDinamicoBase GetModelloDinamicoDaId(int idModello , int indice)
		{
			int codiceAnagrafe = Convert.ToInt32(CodiceAnagrafe);
			var dap = new Dyn2DataAccessProvider(Database);
			var loader = new ModelloDinamicoLoader(dap, IdComune, false);
			return new ModelloDinamicoAnagrafica(loader, idModello, codiceAnagrafe, indice , false);
		}

		public void AggiungiScheda(int idModello)
		{
			AnagrafeDyn2ModelliT mod = new AnagrafeDyn2ModelliT();
			mod.Idcomune = IdComune;
			mod.Codiceanagrafe = Convert.ToInt32(CodiceAnagrafe);
			mod.FkD2mtId = idModello;

			new AnagrafeDyn2ModelliTMgr(Database).Insert(mod);
		}

		public void EliminaScheda(int idModello)
		{
			AnagrafeDyn2ModelliTMgr mgr = new AnagrafeDyn2ModelliTMgr(Database);
			AnagrafeDyn2ModelliT mod = mgr.GetById(IdComune, Convert.ToInt32(CodiceAnagrafe), idModello);
			mgr.Delete(mod);
		}

		public void Close(object sender, EventArgs e)
		{
			base.CloseCurrentPage();
		}

		public List<int> GetListaIndiciScheda(int idModello)
		{
			AnagrafeDyn2ModelliTMgr mgr = new AnagrafeDyn2ModelliTMgr(Database);
			return mgr.GetListaIndiciScheda(IdComune, Convert.ToInt32(CodiceAnagrafe), idModello);
		}

		#endregion

		public string GetUrlPaginaStorico(int idModello)
		{
			string url = "~/Istanze/DatiDinamici/Storico/AnagrafeDyn2Storico.aspx?Token={0}&CodiceAnagrafe={1}&IdModello={2}";

			return ResolveClientUrl(String.Format(url, Token,
															CodiceAnagrafe,
															idModello));
		}

		public bool VerificaEsistenzaStorico(int idModello)
		{
			AnagrafeDyn2ModelliTStoricoMgr mgr = new AnagrafeDyn2ModelliTStoricoMgr(Database);
			int cnt = mgr.ContaRigheStorico(IdComune, Convert.ToInt32(CodiceAnagrafe), idModello);

			return cnt > 0;
		}

		[WebMethod()]
		[ScriptMethod(ResponseFormat = ResponseFormat.Json)]
		public static object GetListaModelliDisponibili(string token, int codice, string partial, bool cercaTT)
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if (authInfo == null)
				return new object[] { new { label = -1, value = "Token non valido" } };

			using (var db = authInfo.CreateDatabase())
			{
				return new AnagrafeDyn2ModelliTMgr(db).GetModelliNonUtilizzati(authInfo.IdComune, codice, partial)
													 .Select(x => new { label = x.Descrizione, value = x.Codice });
			}
		}
	}
}
