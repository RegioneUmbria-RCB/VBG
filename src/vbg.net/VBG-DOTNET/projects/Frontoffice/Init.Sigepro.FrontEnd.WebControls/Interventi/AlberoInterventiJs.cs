using Init.Sigepro.FrontEnd.WebControls.FormControls;
using System;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Interventi
{

    public class AlberoInterventiJs : WebControl, INamingContainer
    {
        public delegate void OnFogliaSelezionata(object sender, int idIntervento);
        public event OnFogliaSelezionata FogliaSelezionata;

        public string IdComune
        {
            get { return ((IIdComunePage)this.Page).IdComune; }
        }

        public string Software
        {
            get
            {
                var sw = HttpContext.Current.Request.QueryString["Software"];

                if (String.IsNullOrEmpty(sw))
                    return "SS";

                return sw;
            }
        }

        public int IdAteco
        {
            get { var o = this.ViewState["IdAteco"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["IdAteco"] = value; }
        }

        public string CodiceComune
        {
            get { return this.ViewState["CodiceComune"] as string ?? ""; }
            set { this.ViewState["CodiceComune"] = value; }
        }

        public bool AreaRiservata
        {
            get { var o = this.ViewState["AreaRiservata"]; return o == null ? false : (bool)o; }
            set { this.ViewState["AreaRiservata"] = value; }
        }


        public string UrlImgJsLoader
        {
            get { var o = this.ViewState["UrlImgJsLoader"]; return o == null ? "~/Images/ajax-loader.gif" : (string)o; }
            set { this.ViewState["UrlImgJsLoader"] = value; }
        }

        public bool EvidenziaVociAttivabiliDaAreaRiservata
        {
            get { var o = this.ViewState["EvidenziaVociAttivabiliDaAreaRiservata"]; return o == null ? false : (bool)o; }
            set { this.ViewState["EvidenziaVociAttivabiliDaAreaRiservata"] = value; }
        }


        public string UrlInterventiService
        {
            get { var o = this.ViewState["UrlInterventiService"]; return o == null ? "~/Public/WebServices/InterventiJsService.asmx" : (string)o; }
            set { this.ViewState["UrlInterventiService"] = value; }
        }

        public string UrlDettagliIntervento
        {
            get { var o = this.ViewState["UrlDettagliIntervento"]; return o == null ? "~/Public/MostraDettagliIntervento.aspx" : (string)o; }
            set { this.ViewState["UrlDettagliIntervento"] = value; }
        }

        public string UrlDettagliEndo
        {
            get { var o = this.ViewState["UrlDettagliEndo"]; return o == null ? "~/Public/MostraDettagliEndo.aspx" : (string)o; }
            set { this.ViewState["UrlDettagliEndo"] = value; }
        }

        public string UrlInfoImage
        {
            get { var o = this.ViewState["UrlInfoImage"]; return o == null ? "~/Images/help_interventi.gif" : (string)o; }
            set { this.ViewState["UrlInfoImage"] = value; }
        }

        public string BlankInfoImage
        {
            get { var o = this.ViewState["BlankInfoImage"]; return o == null ? "~/Images/blank.gif" : (string)o; }
            set { this.ViewState["BlankInfoImage"] = value; }
        }

        public string UrlContenutiBoxRicerca
        {
            get { var o = this.ViewState["UrlContenutiBoxRicerca"]; return o == null ? "~/Public/ContenutiBoxRicercaAteco.htm" : (string)o; }
            set { this.ViewState["UrlContenutiBoxRicerca"] = value; }
        }

        public string Note
        {
            get { var o = this.ViewState["Note"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["Note"] = value; }
        }

        public bool UtenteTester
        {
            get { var o = this.ViewState["UtenteTester"]; return o == null ? false : (bool)o; }
            set { this.ViewState["UtenteTester"] = value; }
        }

        public string CookiePrefix
        {
            get { var o = this.ViewState["CookiePrefix"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CookiePrefix"] = value; }
        }

        public string AutoCompleteCustomRenderer
        {
            get { var o = this.ViewState["AutoCompleteCustomRenderer"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["AutoCompleteCustomRenderer"] = value; }
        }

        public bool MostraInformazioniIntervento
        {
            get { var o = this.ViewState["MostraInformazioniIntervento"]; return o == null || (bool)o; }
            set { this.ViewState["MostraInformazioniIntervento"] = value; }
        }







        public AlberoInterventiJs()
        {
            this.Load += new EventHandler(this.AlberoInterventiJs_Load);
        }

        private void AlberoInterventiJs_Load(object sender, EventArgs e)
        {
            if (this.Page.IsPostBack && HttpContext.Current.Request["__EVENTTARGET"] == this.UniqueID)
            {
                var eventArg = HttpContext.Current.Request["__EVENTARGUMENT"];
                var idx = eventArg.IndexOf("idSelezionato");
                if (idx >= 0)
                {
                    var id = Convert.ToInt32(eventArg.Substring(idx + 14));

                    if (FogliaSelezionata != null)
                        FogliaSelezionata(this, id);
                }
            }
        }

        protected override void RenderContents(HtmlTextWriter writer)
        {
            writer.AddAttribute("class", "treeView");
            writer.RenderBeginTag(HtmlTextWriterTag.Div);

            if (!String.IsNullOrEmpty(this.Note.Trim()))
            {
                writer.AddAttribute("class", "noteAlbero");
                writer.RenderBeginTag(HtmlTextWriterTag.Span);
                writer.Write(this.Note);
                //<span class="noteAlbero" style="margin-left:32px">Le voci contrassegnate con * sono attivabili tramite i servizi online</span>
                writer.RenderEndTag();
            }

            writer.AddAttribute("class", "filetree");
            writer.AddAttribute("id", "rootNode");
            writer.RenderBeginTag(HtmlTextWriterTag.Ul);
            writer.RenderEndTag();

            writer.RenderEndTag();

            writer.AddAttribute("id", "descrizioneIntervento");
            writer.RenderBeginTag(HtmlTextWriterTag.Div);
            writer.RenderEndTag();

            writer.AddAttribute("id", "descrizioneEndo");
            writer.RenderBeginTag(HtmlTextWriterTag.Div);
            writer.RenderEndTag();

            // Div per la ricerca testuale
            writer.AddAttribute("id", "divRicerca");
            writer.RenderBeginTag(HtmlTextWriterTag.Div);
            writer.RenderEndTag();

            var divRicerca = new BootstrapModal();
            divRicerca.ID = "divRicerca";
            divRicerca.Title = "Ricerca testuale";
            divRicerca.ShowFooter = true;
            divRicerca.ModalBody = new ModalBody();
            divRicerca.ShowOkButton = false;

            this.Controls.Add(divRicerca);

            divRicerca.RenderControl(writer);

            // Elaboro la stringa di postback
            var postbackReference = this.Page.ClientScript.GetPostBackEventReference(this, "SEGNAPOSTO_POSTBACK");
            postbackReference = postbackReference.Replace("'SEGNAPOSTO_POSTBACK'", "'idSelezionato@' + id");

            var startupScript = $@"
                var options = {{
					idComune : '{this.IdComune}',
					idAteco: '{this.IdAteco}',
					software: '{this.Software}',
                    codiceComune: '{this.CodiceComune}',
					rootNode : $('#rootNode'),
					divDescrizioneIntervento: $('#descrizioneIntervento'),
					divDescrizioneEndo: $('#descrizioneEndo'),
					urlImgJsLoader: '{this.ResolveClientUrl(this.UrlImgJsLoader)}',
					urlInterventiService: '{this.ResolveClientUrl(this.UrlInterventiService)}',
					urlContenutiBoxRicerca: '{this.ResolveClientUrl(this.UrlContenutiBoxRicerca)}',
					urlDettagliIntervento: '{this.ResolveClientUrl(this.UrlDettagliIntervento)}?IdComune={this.IdComune}&Software={this.Software}&fromAreaRiservata={this.AreaRiservata}',
					urlDettagliEndo: '{this.ResolveClientUrl(this.UrlDettagliEndo)}?IdComune={this.IdComune}&Software={this.Software}&fromAreaRiservata={this.AreaRiservata}',
					infoImageString: '<a href=\'#\'><img src=\'{this.ResolveClientUrl(this.UrlInfoImage)}\' /></a>',
					blankInfoString: '<img src=\'{this.ResolveClientUrl(this.BlankInfoImage)}\' class=\'blankInfo\' />',
					folderClosedImage: '{this.ResolveClientUrl("~/images/folder-closed.gif")}',
					folderOpenImage: '{this.ResolveClientUrl("~/images/folder.gif")}',
					fileImage: '{this.ResolveClientUrl("~/images/file.gif")}',
					mostraVociAttivabiliDaAreaRiservata: {(this.EvidenziaVociAttivabiliDaAreaRiservata ? "true" : "false")},
					areaRiservata: {(this.AreaRiservata ? "'true'" : "'false'")},
					utenteTester: {(this.UtenteTester ? "true" : "false")},
					divRicerca: $('#{divRicerca.ClientID}'),
					lnkRicerca: $('#lnkRicerca'),
					cookiePrefix: '{this.CookiePrefix}',
                    useBootstrap: true,
                    mostraInformazioniIntervento: {(this.MostraInformazioniIntervento ? "true" : "false")}, 
					fogliaSelezionata: function(id){{
						{postbackReference};
					}}";

            if (!String.IsNullOrEmpty(this.AutoCompleteCustomRenderer))
                startupScript += ",\r\nautoCompleteCustomRenderer: " + this.AutoCompleteCustomRenderer + ",";


            startupScript += "};\r\nwindow.alberoInterventi.initialize( options );";

            startupScript = $"\r\n$(function(){{ {startupScript} }})";



            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "startupScript", startupScript, true);

        }

    }
}
