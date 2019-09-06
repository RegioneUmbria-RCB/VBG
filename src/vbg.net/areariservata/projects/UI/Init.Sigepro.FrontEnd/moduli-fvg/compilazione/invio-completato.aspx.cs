using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Init.Sigepro.FrontEnd.QsParameters.Fvg;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.moduli_fvg.compilazione
{
    public partial class invio_completato : BasePage
    {
        [Inject]
        protected ServiziFVGService _service { get; set; }


        protected QsFvgCodiceIstanza CodiceIstanza { get => new QsFvgCodiceIstanza(Request.QueryString); }
        protected QsFvgIdModulo IdModulo { get => new QsFvgIdModulo(Request.QueryString); }


        protected void Page_Load(object sender, EventArgs e)
        {
            var endoprocedimento = this._service.GetDatiModulo(this.CodiceIstanza.Value, this.IdModulo.Value);
            this.Page.Title = endoprocedimento.Descrizione;
        }
    }
}