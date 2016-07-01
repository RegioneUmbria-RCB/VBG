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

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class ErroreAccessoPagina : ReservedBasePage
	{
		public enum TipoErroreEnum
		{
			ErroreNonDefinito= 0,
			PermessiNonDisponibili = 1,
			IstanzaGiaPresentata = 2,
			AccessoNegato = 3
		}

		public static void Mostra(string idComune, string token, string software, TipoErroreEnum tipoErrore)
		{
			string fmtStr = "~/Reserved/ErroreAccessoPagina.aspx?Token={0}&Software={1}&IdComune={2}&Codice={3}";
			HttpContext.Current.Response.Redirect(String.Format(fmtStr,token, software, idComune, (int)tipoErrore));
		}

		TipoErroreEnum TipoErrore
		{
			get 
			{ 
				var tipoErrore = Request.QueryString["Codice"];

				if (String.IsNullOrEmpty(tipoErrore))
					return TipoErroreEnum.ErroreNonDefinito;

				return (TipoErroreEnum)Convert.ToInt32(tipoErrore);
			
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			string msg = "Si è verificato un errore durante l'apertura dell'istanza";

			switch (TipoErrore)
			{
				case TipoErroreEnum.PermessiNonDisponibili:
					msg = "Non si dispone dei permessi necessari per accedere ai dati dell'istanza";
					break;

				case TipoErroreEnum.IstanzaGiaPresentata:
					msg = "La domanda è già stata presentata";
					break;

				case TipoErroreEnum.AccessoNegato:
					msg = "Non si dispone delle autorizzazioni necessarie per accedere a questa funzionalità";
					this.Master.MostraMenu = false;
					this.Title = "Accesso negato";
					break;

				default:
					msg = "Errore non definito, contattare il servizio di assistenza";
					break;



			}

			ltrErrore.Text = msg;
		}
	}
}
