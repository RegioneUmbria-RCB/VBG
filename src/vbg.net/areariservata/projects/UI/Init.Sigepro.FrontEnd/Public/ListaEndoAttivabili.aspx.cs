using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;

namespace Init.Sigepro.FrontEnd.Public
{
	public partial class ListaEndoAttivabili : BasePage
	{
		[Inject]
		public IEndoprocedimentiService _endoprocedimentiRepository { get; set; }


		private int IdTipoEndo
		{
			get
			{
				return Convert.ToInt32( Request.QueryString["tipoEndo"] );
			}
		}

		private int Intervento
		{
			get
			{
				return Convert.ToInt32(Request.QueryString["intervento"]);
			}
		}

		private bool FromAreaRiservata
		{
			get
			{
				var fromAreaRiservata = Request.QueryString["fromAreaRiservata"];

				if (String.IsNullOrEmpty(fromAreaRiservata))
					return true;

				return fromAreaRiservata.ToUpper() == "TRUE";
			}
		}


		protected void Page_Load(object sender, EventArgs e)
		{
			DataBind();
		}

		public override void DataBind()
		{
			rptTipiEndo.DataSource = EstraiListaEndo();
			rptTipiEndo.DataBind();
		}

		private IEnumerable<EndoprocedimentoDto> EstraiListaEndo()
		{
			var listaEndoIntervento = _endoprocedimentiRepository.WhereInterventoIs(IdComune, Intervento);

			foreach (var famigliaEndo in listaEndoIntervento.EndoFacoltativi)
			{
				foreach (var tipoEndo in famigliaEndo.TipiEndoprocedimenti)
				{
					if (tipoEndo.Codice != this.IdTipoEndo)
						continue;

					foreach(var endo in tipoEndo.Endoprocedimenti)
						yield return endo;
				}
			}
		}

		protected string GetLinkEndo(object objEndo)
		{
			if (objEndo == null)
				return String.Empty;

			var endo = (EndoprocedimentoDto)objEndo;

			var baseUrl = base.GetBaseUrlAssoluto();
			var linkDettagliendo = baseUrl + "Public/mostraDettagliEndo.aspx?IdComune=" + IdComune + "&Id=" + endo.Codice.ToString() + "&fromAreaRiservata=" + FromAreaRiservata;

			var fmtStr = "<a href='{0}' target='_blank' class='linkDettagliendo'>{1}</a>";


			return String.Format(fmtStr, linkDettagliendo, endo.Descrizione);
		}
	}
}