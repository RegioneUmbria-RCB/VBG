using Init.SIGeProExport.Data;
using Init.SIGeProExport.Manager;
using System;
//using System.Data.OracleClient;

namespace WebSigeproExport
{
    /// <summary>
    /// Descrizione di riepilogo per SelectEnte.
    /// </summary>
    public partial class SelectEnte : BasePage
    {


        protected void Page_Load(object sender, System.EventArgs e)
        {
            if (!this.IsPostBack)
            {
                //Carico la combo box con i valori letti dalla tabella ESPORTAZIONI
                this.LoadDDExp();

                //Svuoto eventuali session
                this.ClearSessions();
            }
        }

        private void ClearSessions()
        {
            this.Esp = null;
            this.Trac = null;
            this.TracDett = null;
            this.Parametro = null;
        }

        /// <summary>
        /// Questo metodo è usato per caricare la combo box (Esportazioni) con i valori letti dalla tabella ESPORTAZIONI
        /// </summary>
        private void LoadDDExp()
        {
            ESPORTAZIONI pExp = new ESPORTAZIONI();
            pExp.OrderBy = "DESCRIZIONE";
            EsportazioniMgr pExpMgr = new EsportazioniMgr(this.DbDestinazione);
            var pLstExp = pExpMgr.GetList(pExp);
            if (pLstExp != null && pLstExp.Count >= 0)
            {
                this.DDLstExp.DataSource = pLstExp;
                this.DDLstExp.DataTextField = "desc_estesa";
                this.DDLstExp.DataValueField = "chiave_primaria";
                this.DDLstExp.DataBind();
            }
        }

        protected void BtnCerca_Click(object sender, System.EventArgs e)
        {
            string idcomune = this.DDLstExp.SelectedItem.Value.Split(Convert.ToChar("-"))[0];
            string idesportazione = this.DDLstExp.SelectedItem.Value.Split(Convert.ToChar("-"))[1];
            this.Esp = new EsportazioniMgr(this.DbDestinazione).GetById(idcomune, idesportazione);

            this.Response.Redirect("Exp.aspx?idcomune=" + idcomune + "&idesportazione=" + idesportazione);
        }

        protected void BtnChiudi_Click(object sender, System.EventArgs e)
        {
            this.Response.Redirect("Main.aspx");
        }

        protected void BtnNuovo_Click(object sender, EventArgs e)
        {
            this.Response.Redirect("Exp.aspx?idcomune=&idesportazione=");
        }
    }
}
