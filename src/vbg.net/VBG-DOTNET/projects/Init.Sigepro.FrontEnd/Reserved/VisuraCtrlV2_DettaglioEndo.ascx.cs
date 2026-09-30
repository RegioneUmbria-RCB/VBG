using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Ninject;
using System;
using System.IO;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class VisuraCtrlV2_DettaglioEndo : Ninject.Web.UserControlBase
    {
        public string IdComune { get { return this.Request.QueryString["IdComune"]; } }
        public string Software { get { return this.Request.QueryString["Software"]; } }

        private ProcedimentoType _dataSource;
        public ProcedimentoType DataSource
        {
            get { return this._dataSource; }
            set { this._dataSource = value; }
        }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
        }

        protected void rptAllegati_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var hlDownloadAllegato = (HyperLink)e.Item.FindControl("hlDownloadAllegato");

                var doc = (DocumentiType)e.Item.DataItem;

                var documentoFirmato = (Path.GetExtension(doc.allegati.allegato).ToUpper() == ".P7M");

                if (!String.IsNullOrEmpty(doc.allegati?.id))
                {
                    hlDownloadAllegato.NavigateUrl = this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(doc.allegati.id));
                }
            }
        }
    }
}