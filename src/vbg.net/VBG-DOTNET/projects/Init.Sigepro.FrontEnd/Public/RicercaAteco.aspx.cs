using Init.Sigepro.FrontEnd.Contenuti;
using System;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class RicercaAteco : ContenutiBasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public void alberoAteco_FogliaSelezionata(object sender, int idAteco)
        {
            Response.Redirect("~/public/ricercaInterventi.aspx?IdComune=" + IdComune + "&Software=" + Software + "&IdAteco=" + idAteco);
        }
    }
}