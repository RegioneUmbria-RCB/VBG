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
using Init.SIGePro.Manager.Logic.DatiDinamici;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Manager;
using System.Xml;
using Init.Utils;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Sigepro.net.Archivi.DatiDinamici
{
	public partial class Dyn2ModelliPreview : BasePage
	{
		protected int IdModello
		{
			get { return Convert.ToInt32(Request.QueryString["IdModello"]); }
		}

		public override string Software
		{
			get
			{
				return "TT";
			}
		}


		public Dyn2ModelliPreview()
		{
			//VerificaSoftware = false;
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();

			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
		}

		public override void DataBind()
		{
			VerificaHtmlCampi();

			var dap = new Dyn2DataAccessProvider(Database);
			var loader = new ModelloDinamicoLoader(dap, IdComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Backoffice);
			var mod = new ModelloDinamicoIstanza( loader, IdModello , -1,0, true);

			ModelloDinamicoRenderer1.DataSource = mod;
			ModelloDinamicoRenderer1.DataBind();
		}
		protected void VerificaHtmlCampi()
		{
			var modelliMgr = new Dyn2ModelliDMgr(Database);
			var campiMgr = new Dyn2CampiMgr(Database);
			var testiMgr = new Dyn2ModelliDTestiMgr(Database);

			var listaCampi = modelliMgr.GetList(IdComune, Convert.ToInt32(IdModello));

			var erroriTrovati = false;

			foreach (var rigaCampi in listaCampi)
			{
				var testo = String.Empty;

				if (rigaCampi.FkD2cId.HasValue)
				{
					testo = campiMgr.GetById(IdComune, rigaCampi.FkD2cId.Value).Descrizione;
				}
				else
				{
					testo = testiMgr.GetById(IdComune, rigaCampi.FkD2mdtId.Value).Testo;
				}

				try
				{
					ValidaXmlInInput(testo);
				}
				catch (Exception ex)
				{
					this.Errori.Add(String.Format("Errore nel campo alla riga {0} e colonna {1}: {2}", rigaCampi.Posverticale, rigaCampi.Posorizzontale, ex.Message));
					erroriTrovati = true;
				}
			}
		}

		private void ValidaXmlInInput(string testo)
		{
			const string xmlFmt = "<?xml version=\"1.0\"?><contenuto>{0}</contenuto>";

			var xml = String.Format(xmlFmt, testo);

			try
			{
				var doc = new XmlDocument();
				doc.Load(StreamUtils.StringToStream(xml));
			}
			catch (XmlException ex)
			{
				throw new Exception("Il testo immesso contiene tag non chiusi o entità html non valide. (dettagli tecnici: <i>" + ex.Message + "</i>)");
			}
		}
	}
}
