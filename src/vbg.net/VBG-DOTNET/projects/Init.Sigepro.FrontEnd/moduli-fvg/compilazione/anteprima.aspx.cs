using Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG;
using Init.Sigepro.FrontEnd.QsParameters.Fvg;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.moduli_fvg.compilazione
{
    public partial class anteprima : BasePage
    {

        [Inject]
        protected ServiziFVGService _service { get; set; }

        protected QsFvgIdModulo IdModulo { get => new QsFvgIdModulo(Request.QueryString); }

        protected void Page_Load(object sender, EventArgs e)
        {
            Response.Clear();

            var pdf = this._service.GeneraAnteprimaModulo(this.IdModulo.Value);

            this.Response.ContentType = pdf.MimeType;
            this.Response.AddHeader("content-disposition", "attachment; filename=\"" + pdf.FileName + "\"");
            this.Response.BinaryWrite(pdf.FileContent);
        }
    }
}