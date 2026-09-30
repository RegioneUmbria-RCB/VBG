using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_movimenti : Ninject.Web.UserControlBase
    {
        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }


        public class VisuraMovimentiListItem
        {
            public int Id { get; set; }
            public int CodiceIstanza { get; set; }
            public int NumeroAllegati => this.Allegati?.Count() ?? 0;
            public bool HaAllegati => this.NumeroAllegati > 0;
            public string Descrizione { get; set; }
            public DateTime? Data { get; set; }
            public string Parere { get; set; }
            public string NumeroProtocollo { get; set; }
            public DateTime? DataProtocollo { get; set; }
            public string UuidPraticaCollegata { get; set; }
            public bool HaPraticaCollegata { get { return !String.IsNullOrEmpty(this.UuidPraticaCollegata); } }
            public IEnumerable<MovimentiAllegati> Allegati { get; set; } = Enumerable.Empty<MovimentiAllegati>();
        }


        public IEnumerable<VisuraMovimentiListItem> DataSource { get; set; }

        public bool MostraPraticheCollegate
        {
            get { object o = this.ViewState["MostraPraticheCollegate"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraPraticheCollegate"] = value; }
        }


        public bool PermettiDownload
        {
            get { var o = this.ViewState["PermettiDownload"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PermettiDownload"] = value; }
        }

        public string UrlPopupVisura
        {
            get
            {
                var pagina = this.Page as ReservedBasePage;

                var url = UrlBuilder.Url("~/Reserved/sub-visura.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(pagina.IdComune));
                    qs.Add(new QsSoftware(pagina.Software));
                });

                return this.ResolveClientUrl(url);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.dgMovimenti.DataSource = this.DataSource;
            this.dgMovimenti.DataBind();


            this.dgMovimenti.Columns[this.dgMovimenti.Columns.Count - 1].Visible = this.MostraPraticheCollegate;
        }

        protected override void OnPreRender(EventArgs e)
        {
            this.dgMovimenti.Columns[this.dgMovimenti.Columns.Count - 2].Visible = this.PermettiDownload;
        }

        protected void dgMovimenti_RowDataBound(object sender, System.Web.UI.WebControls.GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var dataItem = (VisuraMovimentiListItem)e.Row.DataItem;
                var rptAllegatiMovimento = (Repeater)e.Row.FindControl("rptAllegatiMovimento");

                e.Row.Attributes.Add("id", $"movimento{dataItem.Id}");
                e.Row.Attributes.Add("class", $"elemento-lista-movimenti");

                if ((dataItem.Allegati?.Count() ?? 0) > 0)
                {
                    rptAllegatiMovimento.ItemDataBound += (a, repeaterRow) =>
                    {
                        if (repeaterRow.Item.ItemType == ListItemType.Header)
                        {
                            var ltrNumeroAllegati = (Literal)repeaterRow.Item.FindControl("ltrNumeroAllegati");
                            ltrNumeroAllegati.Text = $"{dataItem.NumeroAllegati} {(dataItem.NumeroAllegati > 1 ? "Allegati" : "Allegato")}";
                        }
                    };

                    rptAllegatiMovimento.DataSource = dataItem.Allegati.Select(y => new
                    {
                        Descrizione = y.DESCRIZIONE,
                        NomeFile = y.Oggetto.NOMEFILE,
                        UrlDownload = this.ResolveClientUrl(this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(y.CODICEOGGETTO))),
                        Md5 = y.Oggetto.Md5
                    });
                    rptAllegatiMovimento.DataBind();
                }
            }
        }
    }
}