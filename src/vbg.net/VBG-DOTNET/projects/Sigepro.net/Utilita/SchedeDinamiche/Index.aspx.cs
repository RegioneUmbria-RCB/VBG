using System;

namespace Sigepro.net.Utilita.SchedeDinamiche
{
    public partial class Index : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void btnVaiAElimina_Click(object sender, EventArgs e)
        {
            this.Response.Redirect("~/Utilita/SchedeDinamiche/EliminazioneScheda.aspx?alias=" + this.txtEliminaAlias.Value + "&idScheda=" + this.txtEliminaIdScheda.Value);
        }

        protected void btnVaiAEsporta_Click(object sender, EventArgs e)
        {
            this.Response.Redirect("~/Utilita/SchedeDinamiche/ExportSchedeDinamiche.ashx?alias=" + this.txtEsportaAlias.Value + "&idScheda=" + this.txtEsportaIdScheda.Value);
        }

        protected void btnVaiAImporta_Click(object sender, EventArgs e)
        {
            this.Response.Redirect("~/Utilita/SchedeDinamiche/ImportSchedeDinamiche.aspx?alias=" + this.txtImportAlias.Value + "&software=" + this.txtImportSoftware.Value);

        }

        protected void btnEsportaIstanza_Click(object sender, EventArgs e)
        {
            this.Response.Redirect("~/Utilita/SchedeDinamiche/ExportIstanze.ashx?alias=" + this.txtEsportaIstanzaAlias.Value + "&codiceIstanza=" + this.txtEsportaCodiceIstanza.Value);
        }
    }
}