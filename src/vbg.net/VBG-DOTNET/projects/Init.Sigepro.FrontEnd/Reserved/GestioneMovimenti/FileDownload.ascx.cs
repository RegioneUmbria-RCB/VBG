using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class FileDownload : Ninject.Web.UserControlBase
    {
        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        public object DataSource { get; set; }

        public string IdComune
        {
            get
            {
                return this.Page.Request.QueryString["idcomune"];
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public string UrlDownload(object codiceOggetto)
        {
            if (codiceOggetto == null)
            {
                return String.Empty;
            }

            return this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(codiceOggetto));
        }
    }
}