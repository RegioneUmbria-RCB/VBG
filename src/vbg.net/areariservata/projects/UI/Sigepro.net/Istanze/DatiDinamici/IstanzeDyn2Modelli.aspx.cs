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
using SIGePro.Net;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net.Navigation;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using System.Text;
using System.Web.Script.Serialization;
using Init.SIGePro.Utils;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using System.Web.Services;
using System.Web.Script.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;




namespace Sigepro.net.Istanze.DatiDinamici
{
	public partial class IstanzeDyn2Modelli : BasePage
	{


		#region gestione dei Dati collegati

		protected string CodiceIstanza
		{
			get { return Request.QueryString["CodiceIstanza"]; }
		}

		Init.SIGePro.Data.Istanze m_istanza;
		Init.SIGePro.Data.Istanze Istanza
		{
			get
			{
				if (m_istanza == null)
                    m_istanza = new IstanzeMgr(Database).GetById(IdComune, Convert.ToInt32(CodiceIstanza));

				return m_istanza;
			}
		}

		public override string Software
		{
			get
			{
				return Istanza.SOFTWARE;
			}
		}

		protected string CodiceMovimento
		{
			get { return Request.QueryString["CodiceMovimento"]; }
		}

		#endregion



		#region ciclo di vita della pagina

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

				DataBind();
			}
			
			if( !String.IsNullOrEmpty(CodiceMovimento) )
				ddCtrl.ExtraQueryStringFunction = () => "CodiceMovimento=" + CodiceMovimento;
		}

		public override void DataBind()
		{
			var istanzeMgr = new IstanzeMgr(Database);
			var istanza = istanzeMgr.GetById(IdComune, Convert.ToInt32(CodiceIstanza));

			// Imposto le etichette
			lblCodiceIstanza.Text = istanza.NUMEROISTANZA;
			lblRichiedente.Text = new AnagrafeMgr(Database).GetById(IdComune, Convert.ToInt32(istanza.CODICERICHIEDENTE)).ToString();

			// Verifica dei permessi dell'istanza
			//var perm = istanzeMgr.PermessiIstanza(IdComune, AuthenticationInfo.CodiceResponsabile.Value, Convert.ToInt32(CodiceIstanza));

			// if (!perm.AccessoConsentito)
			// {
			// 	Logger.LogEvent(AuthenticationInfo, "DatiDinamici", "L'utente " + AuthenticationInfo.CodiceResponsabile + " non dispone dei permessi necessari per accedere alle schede dell'istanza " + CodiceIstanza, "");
            // 
			// 	throw new ApplicationException("L'utente corrente non dispone dei permessi necessari per accedere all'istanza");
			// }
            // 
			// if (perm.SolaLettura)
			// 	ddCtrl.SolaLettura = true;

			// Popolamento dei dati del movimento
			datiMovimento.Visible = false;

			if(!String.IsNullOrEmpty(CodiceMovimento))
			{
				datiMovimento.Visible = true;
				
				var movimento = new MovimentiMgr(Database).GetById(IdComune, Convert.ToInt32(CodiceMovimento));
				var tipoMovimento = new TipiMovimentoMgr(Database).GetById(movimento.TIPOMOVIMENTO, IdComune);

				this.Title = String.Format("Schede del movimento \"{0}\"", tipoMovimento.Movimento);				

				lblCodiceMovimento.Text = CodiceMovimento;
			}
		}

		public void lnkTornaAIstanza_Click(object sender, EventArgs e)
		{
			Close(sender, e);
		}

		#endregion

		#region handler degli eventi del controllo di gestione modelli

		public List<int> GetListaIndiciScheda(int idModello)
		{
			IstanzeDyn2ModelliTMgr mgr = new IstanzeDyn2ModelliTMgr(Database);
			return mgr.GetListaIndiciScheda(IdComune, Convert.ToInt32(CodiceIstanza), idModello);
		}

		public List<ElementoListaModelli> GetListaModelli()
		{
			List<IstanzeDyn2ModelliTMgr.ElementoListaModelliIstanza> modelli = new IstanzeDyn2ModelliTMgr(Database).GetModelliIstanza(IdComune, Convert.ToInt32(CodiceIstanza), CodiceMovimento);

			List<ElementoListaModelli> ret = new List<ElementoListaModelli>(modelli.Count);

			modelli.ForEach(delegate(IstanzeDyn2ModelliTMgr.ElementoListaModelliIstanza m)
			{
				ret.Add(m);
			});

			return ret;
		}

		//public List<Dyn2ModelliT> GetListaModelliDisponibili()
		//{
		//    return new IstanzeDyn2ModelliTMgr(Database).GetModelliNonUtilizzati(IdComune, Convert.ToInt32(CodiceIstanza),false);
		//}

		public ModelloDinamicoBase GetModelloDinamicoDaId(int idModello, int indice)
		{
			int codiceIstanza = Convert.ToInt32(Istanza.CODICEISTANZA);
			
			var dap = new IstanzeDyn2DataAccessProvider(Database, codiceIstanza, IdComune);
			var loader = new ModelloDinamicoLoader(dap, IdComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Backoffice);
			return new ModelloDinamicoIstanza(loader, idModello, codiceIstanza, indice, false);
		}

		public void AggiungiScheda(int idModello)
		{

			if (!String.IsNullOrEmpty(CodiceMovimento))
			{
				MovimentiDyn2ModelliT mov = new MovimentiDyn2ModelliT();
				mov.Idcomune = IdComune;
				mov.FkD2mtId = idModello;
				mov.Codicemovimento = Convert.ToInt32(CodiceMovimento);
				mov.Codiceistanza = Convert.ToInt32(CodiceIstanza);

				new MovimentiDyn2ModelliTMgr(Database).Insert(mov);

				// Se l'istanza contiene già il modello questo non va aggiunto
				var filtro = new IstanzeDyn2ModelliT
				{
					Idcomune = IdComune,
					Codiceistanza = Convert.ToInt32(CodiceIstanza),
					FkD2mtId = idModello
				};
				if (new IstanzeDyn2ModelliTMgr(Database).GetList(filtro).Count > 0)
					return;
			}


			var mod = new IstanzeDyn2ModelliT
			{
				Idcomune = IdComune,
				Codiceistanza = Convert.ToInt32(CodiceIstanza),
				FkD2mtId = idModello
			};

			new IstanzeDyn2ModelliTMgr(Database).Insert(mod);

		}

		public void EliminaScheda(int idModello)
		{
			// Se sto eliminando la scheda dalla gestione movimenti la scheda deve essere rimossa solo dalla 
			// tabella MovimentiDyn2ModelliT
			if (!String.IsNullOrEmpty(CodiceMovimento))
			{
				try
				{
					var mgr = new MovimentiDyn2ModelliTMgr(Database);
					var movimento = mgr.GetById(IdComune, idModello, Convert.ToInt32(CodiceMovimento));
					mgr.Delete(movimento);
				}
				catch (Exception ex)
				{
					Errori.Add("Errore durante la rimozione della scheda dal movimento: " + ex.Message);
				}
				return;
			}

			try
			{
				var istMgr = new IstanzeDyn2ModelliTMgr(Database);
				IstanzeDyn2ModelliT mod = istMgr.GetById(IdComune, Convert.ToInt32(CodiceIstanza), idModello);
				istMgr.Delete(mod);
			}
			catch (Exception ex)
			{
				Errori.Add("Errore durante la rimozione della scheda dall'istanza: " + ex.Message);
			}
		}

		public void Close(object sender, EventArgs e)
		{
			base.CloseCurrentPage();
		}

		public string GetUrlPaginaStorico(int idModello)
		{
			string url = "~/Istanze/DatiDinamici/Storico/IstanzeDyn2Storico.aspx?Token={0}&CodiceIstanza={1}&IdModello={2}";

			return ResolveClientUrl(String.Format(url, Token,
															CodiceIstanza,
															idModello));
		}

		public bool VerificaEsistenzaStorico(int idModello)
		{
			IstanzeDyn2ModelliTStoricoMgr mgr = new IstanzeDyn2ModelliTStoricoMgr(Database);
			int cnt = mgr.ContaRigheStorico(IdComune, Convert.ToInt32(CodiceIstanza), idModello);

			return cnt > 0;
		}

		#endregion


		[WebMethod()]
		[ScriptMethod(ResponseFormat = ResponseFormat.Json)]
		public static object GetListaModelliDisponibili(string token, int codice, string partial, bool cercaTT, string codiceMovimento = "")
		{
			var authInfo = AuthenticationManager.CheckToken(token);

			if( authInfo == null )
				return new object[]{ new{label=-1,value="Token non valido"} };

			using (var db = authInfo.CreateDatabase())
			{
				var idComune = authInfo.IdComune;

				if (!String.IsNullOrEmpty(codiceMovimento))
				{
					return new MovimentiDyn2ModelliTMgr(db).GetModelliNonUtilizzati(idComune, Convert.ToInt32(codiceMovimento), partial, cercaTT)
														   .Select(x => new { label=x.Descrizione,value=x.Codice });
				}

				return new IstanzeDyn2ModelliTMgr(db).GetModelliNonUtilizzati(idComune, codice, partial, cercaTT)
													 .Select(x => new { label=x.Descrizione,value=x.Codice });
			}
		}
	}
}
