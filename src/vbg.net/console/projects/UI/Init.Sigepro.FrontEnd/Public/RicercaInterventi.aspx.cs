using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Contenuti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class RicercaInterventi : ContenutiBasePage
	{
		const string MESSAGGIO_COLLEGAMENTO_ATECO_NON_TROVATO_DEFAULT = "Non sono stati individuati interventi riconducibili all'attività ATECO selezionata. Verrà mostrata la lista completa degli interventi";

		[Inject]
		public IAtecoRepository _atecoRepository { get; set; }


//		[RegExValidate("^[0-9]{1,10}$")]
		protected int IdAteco
		{
			get {
				var obj = Request.QueryString["idateco"];
				if (String.IsNullOrEmpty(obj))
					return -1;
				return int.Parse(obj);
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			this.alberoInterventi.IdAteco = IdAteco;


			if (IdAteco > 0)
			{
				if(!IsPostBack)
				{
					if (!_atecoRepository.EsistonoInterventiCollegati(IdComune, Software, IdAteco, new AmbitoRicercaFrontofficePubblico()))
					{
						alberoInterventi.Note = MESSAGGIO_COLLEGAMENTO_ATECO_NON_TROVATO_DEFAULT + "<br /><br />" + alberoInterventi.Note;

					}
				}
			}
		}

		protected void InterventoSelezionato(object sender, int idIntervento)
		{
			Response.Redirect("~/Public/RicercaInterventiDettaglio.aspx?IdComune=" + IdComune + "&Software=" + Software + "&Id=" + idIntervento);
		}
	}
}