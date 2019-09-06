using Init.Sigepro.FrontEnd.AppLogic.GestioneModulisticaFrontoffice;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.WsModulisticaFrontoffice;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class Ricercamodulistica : PublicBasePage
    {
        [Inject]
        public ModulisticaFrontofficeService _modulisticaService { get; set; }

        [Inject]
        public IOggettiService _oggettiService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if(!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            rptCategorie.DataSource = this._modulisticaService.GetModulistica(this.Software);
            rptCategorie.DataBind();
        }

        protected void rptCategorie_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var rptModulistica = (Repeater)e.Item.FindControl("rptModulistica");
                var categoria = (CategoriaModulisticaDto)e.Item.DataItem;

                rptModulistica.DataSource = categoria.Modulistica;
                rptModulistica.DataBind();
            }
        }

        protected void rptModulistica_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            if (e.CommandName == "Download")
            {
                var idAllegato = Convert.ToInt32(e.CommandArgument);

                var oggetto = this._oggettiService.GetById(idAllegato);

                this.Page.Response.AddHeader("content-disposition", "attachment; filename=\"" + oggetto.FileName.Replace("\"", "_") + "\"");
                this.Page.Response.ContentType = oggetto.MimeType;
                this.Page.Response.BinaryWrite(oggetto.FileContent);

                Response.End();
            }
        }
    }
}