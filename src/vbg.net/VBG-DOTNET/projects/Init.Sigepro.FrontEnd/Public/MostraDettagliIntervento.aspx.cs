using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.DTO.Interventi;
using Init.SIGePro.Manager.DTO.Normative;
using Ninject;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.Public
{
    public partial class MostraDettagliIntervento : BasePage
    {
        [Inject]
        public IInterventiRepository _alberoProcRepository { get; set; }
        [Inject]
        public IEndoprocedimentiService _endoprocedimentiService { get; set; }
        //[Inject]
        //public ICartRepository _cartRepository { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }



        public bool MostraDefinizioni
        {
            get { object o = this.ViewState["MostraDefinizioni"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraDefinizioni"] = value; }
        }


        public int Id
        {
            get { return Convert.ToInt32(this.Request.QueryString["Id"]); }
        }

        public bool Print
        {
            get
            {
                var qs = this.Request.QueryString["print"];

                if (String.IsNullOrEmpty(qs))
                    return false;

                if (qs.ToUpper() == "TRUE")
                    return true;

                return false;
            }
        }

        public bool NoPrintButton
        {
            get
            {
                var qs = this.Request.QueryString["noprint"];

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
                var qs = this.Request.QueryString["popup"];

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
                var fromAreaRiservata = this.Request.QueryString["fromAreaRiservata"];

                if (String.IsNullOrEmpty(fromAreaRiservata))
                    return true;

                return fromAreaRiservata.ToUpper() == "TRUE";
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.treeRenderer.HideFolders = this.Print;

            if (!this.IsPostBack)
                this.DataBind();
        }


        public class AllegatoInterventoBindingItem
        {
            private readonly IUrlDownloadOggettiService _urlDownloadOggettiService;
            private static readonly string[] FormatiCheSupportanoPrecompilazione = { ".XSL", ".RTF" };
            private readonly DocumentoInterventoDto _documento;

            public AllegatoInterventoBindingItem(Page page, DocumentoInterventoDto doc, IUrlDownloadOggettiService urlDownloadOggettiService)
            {
                this._documento = doc;
                this._urlDownloadOggettiService = urlDownloadOggettiService;

                this.LinkDoc = String.Empty;
                this.LinkOdt = String.Empty;
                this.LinkPdf = String.Empty;
                this.LinkRtf = String.Empty;
                this.LinkGenerico = String.Empty;

                if (doc.CodiceOggetto.HasValue)
                {
                    this.LinkDoc = page.ResolveUrl(this.UrlDownload(FormatoConversioneEnum.DOC));
                    this.LinkOdt = page.ResolveUrl(this.UrlDownload(FormatoConversioneEnum.ODT));
                    this.LinkPdf = page.ResolveUrl(this.UrlDownload(FormatoConversioneEnum.PDF));
                    this.LinkRtf = page.ResolveUrl(this.UrlDownload(FormatoConversioneEnum.RTF));
                    this.LinkGenerico = page.ResolveUrl(this.UrlDownloadNoPrecompilazione());
                }
            }

            private string UrlDownloadNoPrecompilazione()
            {
                var estensioneFile = Path.GetExtension(this._documento.NomeFile).ToUpperInvariant();

                if (FormatiCheSupportanoPrecompilazione.Contains(estensioneFile))
                    return String.Empty;

                return this._urlDownloadOggettiService.GetUrlDownload(this._documento.CodiceOggetto.Value);
            }

            private string UrlDownload(FormatoConversioneEnum formato)
            {
                var estensioneFile = Path.GetExtension(this._documento.NomeFile).ToUpperInvariant();

                if (!FormatiCheSupportanoPrecompilazione.Contains(estensioneFile))
                    return String.Empty;

                if (this._documento.TipoDownload.ToUpperInvariant().IndexOf(formato.ToString()) == -1)
                    return String.Empty;

                return this._urlDownloadOggettiService.GetUrlDownloadConvertito(this._documento.CodiceOggetto.Value, formato);
            }

            public string Obbligatorio => this._documento.Obbligatorio ? "* " : "";
            public string Descrizione => this._documento.Descrizione;
            public string LinkPdf { get; private set; }
            public string LinkRtf { get; private set; }
            public string LinkDoc { get; private set; }
            public string LinkOdt { get; private set; }
            public string LinkGenerico { get; private set; }

            public bool SupportaDownloadPdf { get { return !String.IsNullOrEmpty(this.LinkPdf); } }
            public bool SupportaDownloadRtf { get { return !String.IsNullOrEmpty(this.LinkRtf); } }
            public bool SupportaDownloadDoc { get { return !String.IsNullOrEmpty(this.LinkDoc); } }
            public bool SupportaDownloadOdt { get { return !String.IsNullOrEmpty(this.LinkOdt); } }
            public bool SupportaDownloadGenerico { get { return !String.IsNullOrEmpty(this.LinkGenerico); } }


        }






        public override void DataBind()
        {
            IAmbitoRicercaIntervento ambitoRicerca = this.FromAreaRiservata ? (IAmbitoRicercaIntervento)new AmbitoRicercaAreaRiservata(false) : (IAmbitoRicercaIntervento)new AmbitoRicercaFrontofficePubblico();

            var alberoIntervento = this._alberoProcRepository.GetAlberaturaNodoDaId(this.IdComune, this.Id);
            var dettagliIntervento = this._alberoProcRepository.GetDettagliIntervento(this.IdComune, this.Id, ambitoRicerca, true);

            var falsoRoot = new NodoAlberoInterventiDto();

            falsoRoot.NodiFiglio = new List<ClassTree<InterventoDto>> {
                alberoIntervento
            };

            this.treeRenderer.DataSource = falsoRoot;
            this.treeRenderer.DataBind();

            var figlio = falsoRoot.NodiFiglio[0];

            while (figlio.NodiFiglio.Count() > 0)
            {
                figlio = figlio.NodiFiglio[0];
            }

            this.Page.Title = figlio.Elemento.Descrizione;

            if (!String.IsNullOrEmpty(dettagliIntervento.Note))
            {
                this.ltrNote.Text = dettagliIntervento.Note.Replace("\n", "<br />"); ;
            }
            else
            {
                this.MostraDefinizioni = false;
            }

            if (dettagliIntervento.FasiAttuative.Count > 0)
            {
                this.rptFasiAttuative.DataSource = dettagliIntervento.FasiAttuative;
                this.rptFasiAttuative.DataBind();
            }

            if (dettagliIntervento.Documenti.Count > 0)
            {
                var dataSourceModulistica = dettagliIntervento.Documenti
                                                              .Where(d => !d.DomandaFo)
                                                              .Select(d => new AllegatoInterventoBindingItem(this.Page, d, this._urlDownloadOggettiService));

                this.rptModulistica.DataSource = dataSourceModulistica;
                this.rptModulistica.DataBind();
            }

            if (dettagliIntervento.Normative.Count > 0)
            {
                this.rptNormativa.DataSource = dettagliIntervento.Normative;
                this.rptNormativa.DataBind();
            }

            this.BindEndoprocedimenti();

            if (dettagliIntervento.Oneri.Count > 0)
            {
                this.rptOneri.DataSource = dettagliIntervento.Oneri;
                this.rptOneri.DataBind();
            }


            this.pnlSchedaCart.Visible = false;

            //var urlCart = this._cartRepository.GetUrlSchedaCARTIntervento(this.Id);

            //if (!String.IsNullOrEmpty(urlCart))
            //{
            //    this.pnlSchedaCart.Visible = true;
            //    this.BindSchedaCart(urlCart);
            //}
        }

        private void BindSchedaCart(string urlCart)
        {
            // used to build entire input
            StringBuilder sb = new StringBuilder();

            // used on each read operation
            byte[] buf = new byte[8192];

            // prepare the web page we will be asking for
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(urlCart);

            // execute the request
            HttpWebResponse response = (HttpWebResponse)
                request.GetResponse();

            var respEncoding = Encoding.GetEncoding(response.CharacterSet);

            // we will read data via the response stream
            var resStream = response.GetResponseStream();

            string tempString = null;
            int count = 0;

            do
            {
                // fill the buffer with data
                count = resStream.Read(buf, 0, buf.Length);

                // make sure we read some data
                if (count != 0)
                {
                    // translate from bytes to ASCII text
                    tempString = respEncoding.GetString(buf, 0, count);

                    // continue building the string
                    sb.Append(tempString);
                }
            }
            while (count > 0); // any more data to read?

            string content = sb.ToString();

            var startIdx = content.IndexOf("<body>");

            if (startIdx > 0)
            {
                startIdx += 6;

                var endIdx = content.IndexOf("</body>");

                if (endIdx > 0)
                    content = content.Substring(startIdx, endIdx - startIdx);
            }

            content = "<div id='datiCart'>" + content + "</div>";

            this.ltrSchedaCart.Text = content;
        }


        private void BindEndoprocedimenti()
        {
            var listaEndoIntervento = this._endoprocedimentiService.GetListaEndoDaIdIntervento(null, this.Id);

            if (listaEndoIntervento.Principali.Count > 0 ||
                listaEndoIntervento.Richiesti.Count > 0)
            {
                var items = new List<FamigliaEndoprocedimentoDto>(listaEndoIntervento.Principali);

                items.AddRange(listaEndoIntervento.Richiesti);

                var dataSource = new AlberelloEndoControl.AlberelloEndoControlDataSource(this.IdComune, this.FromAreaRiservata, this.Popup, this.Print, items);

                this.rptProcedimentiNecessari.DataSource = dataSource;
                this.rptProcedimentiNecessari.DataBind();
            }


            if (listaEndoIntervento.Ricorrenti.Count > 0)
            {
                var dataSource = new AlberelloEndoControl.AlberelloEndoControlDataSource(this.IdComune, this.FromAreaRiservata, this.Popup, this.Print, listaEndoIntervento.Ricorrenti);

                this.rptProcedimentiRicorrenti.DataSource = dataSource;
                this.rptProcedimentiRicorrenti.DataBind();
            }


            if (listaEndoIntervento.Altri.Count() > 0)
            {
                var dataSource = new AlberelloEndoControl.AlberelloEndoControlDataSource(this.IdComune, this.FromAreaRiservata, this.Popup, this.Print, listaEndoIntervento.Altri);

                this.rptProcedimentiEventuali.DataSource = dataSource;
                this.rptProcedimentiEventuali.DataBind();
            }
        }



        public string GetUrlStampaPagina()
        {
            var req = HttpContext.Current.Request;
            var downloadUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

            if (!String.IsNullOrEmpty(req.ApplicationPath))
                downloadUrl += req.ApplicationPath;

            if (!downloadUrl.EndsWith("/"))
                downloadUrl += "/";

            downloadUrl += "Public/MostraDettagliIntervento.aspx?idComune=" + this.IdComune + "&Id=" + this.Id + "&Print=true";

            return downloadUrl;
        }

        public string GetUrlDownloadPagina()
        {
            var downloadUrl = this.GetUrlStampaPagina();

            return base.GetBaseUrlAssoluto() + "Public/DownloadPage.ashx" + "?IdComune=" + this.IdComune + "&url=" + this.Server.UrlEncode(downloadUrl);
        }



        public string GetLinkModello(object objIdOggetto)
        {
            var idOggetto = (int?)objIdOggetto;

            if (!idOggetto.HasValue)
                return "&nbsp;";

            var baseUrl = base.GetBaseUrlAssoluto();

            string urlLink = this.Page.ResolveUrl(this._urlDownloadOggettiService.GetUrlDownloadConvertito(Convert.ToInt32(objIdOggetto), FormatoConversioneEnum.PDF));

            string urlImmagine = this.Page.ResolveUrl("~/Images/download16x16.png");

            return $"<a href='{urlLink}' target='_blank'><img src='{urlImmagine}' /></a>";
        }

        public string GetLinkNormativa(object objNormativa)
        {
            var normativa = (NormativaDto)objNormativa;
            var link = normativa.Link;

            if (String.IsNullOrEmpty(link))
                return String.Empty;

            if (!link.ToUpper().StartsWith("HTTP"))
                link = "http://" + link;

            return "<a href='" + link + "' target='_blank'><img src='" + base.GetBaseUrlAssoluto() + "images/link_icon.png' /></a>";
        }




        protected override void Render(HtmlTextWriter writer)
        {
            if (this.Popup || this.Print)
                base.Render(writer);
            else
                this.pnlDatiIntervento.RenderControl(writer);
        }
    }
}
