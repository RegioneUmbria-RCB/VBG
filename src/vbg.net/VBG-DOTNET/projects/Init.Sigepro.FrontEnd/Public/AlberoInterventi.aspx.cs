using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Ninject;
using System;
using System.Web;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class AlberoInterventi : BasePage
    {
        [Inject]
        public IInterventiRepository _alberoProcRepository { get; set; }
        //[Inject]
        //public IAtecoRepository _atecoRepository { get; set; }


        public override string Software
        {
            get
            {
                var sw = this.Request.QueryString["Software"];

                if (String.IsNullOrEmpty(sw))
                    return "SS";

                return sw;
            }
        }

        public bool StartCollapsed
        {
            get { object o = this.ViewState["StartCollapsed"]; return o == null ? true : (bool)o; }
            set { this.ViewState["StartCollapsed"] = value; }
        }

        // public Ateco VoceAteco { get; set; }




        private bool Popup
        {
            get
            {
                var qs = this.Request.QueryString["popup"];

                if (String.IsNullOrEmpty(qs))
                    return false;

                if (qs.ToUpper() == "TRUE")
                    return true;

                return false;
            }
        }

        public int IdAteco
        {
            get
            {
                var ateco = this.Request.QueryString["idAteco"];

                if (string.IsNullOrEmpty(ateco))
                    return -1;

                return Convert.ToInt32(ateco);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.DataBind();
        }

        public override void DataBind()
        {
            var url = this.GetAbsoluteBaseUrl() + "Public/MostraDettagliIntervento.aspx?Id={0}&popup=" + !this.Popup;

            if (this.Popup)
            {
                url = "mostraDettagli(this,{0})";
            }


            this.treeRenderer.UrlDettagliIntervento = url;

            if (this.IdAteco == -1)
            {
                this.treeRenderer.DataSource = this._alberoProcRepository.GetAlberoInterventi(this.IdComune, this.Software);
            }
            else
            {
                //var l = _atecoRepository.GetAlberoProc(this.IdComune, this.IdAteco, AmbitoRicerca.FrontofficePubblico);
                //this.VoceAteco = _atecoRepository.GetDettagli(this.IdComune, this.IdAteco);
                //this.StartCollapsed = false;

                //if (l.NodiFiglio.Count() == 0)
                //{
                //    this.StartCollapsed = true;
                //    l = this._alberoProcRepository.GetAlberoInterventi(this.IdComune, this.Software);
                //}

                //this.treeRenderer.DataSource = l;
            }

            this.treeRenderer.DataBind();
        }

        protected override void Render(HtmlTextWriter writer)
        {
            if (this.Popup)
                base.Render(writer);
            else
                this.pnlAlberoInterventi.RenderControl(writer);
        }

        protected string GetAbsoluteBaseUrl()
        {
            var req = HttpContext.Current.Request;
            var baseUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

            if (!String.IsNullOrEmpty(req.ApplicationPath))
                baseUrl += req.ApplicationPath;

            if (!baseUrl.EndsWith("/"))
                baseUrl += "/";

            return baseUrl;
        }
    }
}
