using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class MostraDettagliEndoB : Ninject.Web.PageBase
    {
        [Inject]
        public IEndoprocedimentiService _endoprocedimentiRepository { get; set; }

        public virtual string IdComune
        {
            get
            {
                return Request.QueryString["IdComune"];
            }
        }

        public int Id
        {
            get { return Convert.ToInt32(Request.QueryString["Id"]); }
        }

        public bool Print
        {
            get
            {
                var qs = Request.QueryString["print"];

                if (String.IsNullOrEmpty(qs))
                    return false;

                if (qs.ToUpper() == "TRUE")
                    return true;

                return false;
            }
        }

        public bool MostraBottoneStampa
        {
            get
            {
                var qs = Request.QueryString["MostraBottoneStampa"];

                if (String.IsNullOrEmpty(qs))
                    return false;

                if (qs.ToUpper() == "TRUE")
                    return true;

                return false;
            }
        }

        private bool Popup
        {
            get
            {
                var qs = Request.QueryString["popup"];

                if (String.IsNullOrEmpty(qs))
                    return false;

                if (qs.ToUpper() == "TRUE")
                    return true;

                return false;
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

        public EndoprocedimentoDto DataSource { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
                DataBind();
        }

        public override void DataBind()
        {
            DataSource = _endoprocedimentiRepository.GetById(IdComune, Id, FromAreaRiservata ? AmbitoRicerca.AreaRiservata : AmbitoRicerca.FrontofficePubblico);
            Page.Title = DataSource.Descrizione;
            base.DataBind();

            pnlSchedaCart.Visible = false;
        }

        public string GetUrlStampaPagina()
        {
            var req = HttpContext.Current.Request;
            var downloadUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

            if (!String.IsNullOrEmpty(req.ApplicationPath))
                downloadUrl += req.ApplicationPath;

            if (!downloadUrl.EndsWith("/"))
                downloadUrl += "/";

            downloadUrl += "Public/MostraDettagliEndo.aspx?idComune=" + IdComune + "&Id=" + Id + "&Print=true";

            return downloadUrl;
        }

        public string GetUrlDownloadPagina()
        {
            var downloadUrl = GetUrlStampaPagina();

            return GetBaseUrl() + "Public/DownloadPage.ashx" + "?IdComune=" + IdComune + "&url=" + Server.UrlEncode(downloadUrl);
        }

        public string GetLinkNormativa(object objNormativa)
        {
            var normativa = (NormativaDto)objNormativa;
            var link = normativa.Link;

            if (String.IsNullOrEmpty(link))
                return String.Empty;

            if (link.ToUpper().IndexOf("HTTP") != 0)
            {
                link = "http://" + link;
            }

            return GetLinkEsterno(link);
        }


        public string GetLinkEsterno(object objLink)
        {
            if (objLink == null)
                return String.Empty;

            var strLink = objLink.ToString();

            if (String.IsNullOrEmpty(strLink))
                return String.Empty;

            var baseUrl = GetBaseUrl();

            baseUrl += "images/link_icon.png";

            var fmtString = "<a href='{0}' target='_blank'><img src='{1}' alt='Link esterno'/></a>";

            return string.Format(fmtString, strLink, baseUrl);
        }

        protected string GetBaseUrl()
        {
            var req = HttpContext.Current.Request;
            var baseUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

            if (!String.IsNullOrEmpty(req.ApplicationPath))
                baseUrl += req.ApplicationPath;

            if (!baseUrl.EndsWith("/"))
                baseUrl += "/";

            return baseUrl;
        }

        public string GetLinkModello(object objIdOggetto, object objFormatiDownload)
        {
            if (objIdOggetto == null)
                return String.Empty;

            var baseUrl = GetBaseUrl();
            var formatoDownload = objFormatiDownload == null ? String.Empty : objFormatiDownload.ToString();

            if (String.IsNullOrEmpty(formatoDownload))
                formatoDownload = "pdf";

            var formatiSupportati = formatoDownload.Split(',');

            var sb = new StringBuilder();

            for (int i = 0; i < formatiSupportati.Length; i++)
            {
                var formato = formatiSupportati[i].ToUpperInvariant();
                var iconName = String.Format("{0}16x16.gif", (formato == "PDFC" ? "PDF" : formato));

                if (String.IsNullOrEmpty(formato.ToUpperInvariant()))
                {
                    continue;
                }

                var urlOggetto = String.Format("{0}MostraOggettoMultiFormato.ashx", baseUrl);
                var urlIcona = String.Format("{0}Images/{1}", baseUrl, iconName);
                var url = UrlBuilder.Url(urlOggetto, qs =>
                {
                    qs.Add("idComune", IdComune);
                    qs.Add("id", objIdOggetto);
                    qs.Add("fmt", formato == "PDFC" ? "PDF" : formato);

                    if (formato == "PDFC")
                    {
                        qs.Add("noconv", "1");
                    }
                });

                sb.Append($"<a href='{url}'><img src='{urlIcona}' /></a>");
            }

            return sb.ToString();
        }

        protected override void Render(HtmlTextWriter writer)
        {
            if (Popup || Print)
                base.Render(writer);
            else
                pnlDatiEndo.RenderControl(writer);
        }
    }
}