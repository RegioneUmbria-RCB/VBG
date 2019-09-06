using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd.Contenuti
{
	public partial class Step2 : ContenutiBasePage
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

		public Step2()
		{
			//
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.StepId = 2;
			this.Master.MostraHelp = true;


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
			Response.Redirect("~/Contenuti/Step3.aspx?alias=" + AliasComune + "&Software=" + Software + "&Id=" + idIntervento);
		}
	}
}
