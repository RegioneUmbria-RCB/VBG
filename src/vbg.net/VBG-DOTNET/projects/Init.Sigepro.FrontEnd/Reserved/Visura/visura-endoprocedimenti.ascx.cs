using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Ninject;
using Ninject.Web;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_endoprocedimenti : UserControlBase
    {
        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

        public class VisuraEndoListItem
        {
            public int Id { get; set; }
            public int CodiceIstanza { get; set; }
            public IEnumerable<IstanzeAllegati> Allegati { get; set; }
            public string Endoprocedimento { get; set; }
            public bool HaAllegati => this.Allegati?.Any() ?? false;
            public int NumeroAllegati => this.Allegati?.Count() ?? 0;
        }

        public bool PermettiDownload
        {
            get { var o = this.ViewState["PermettiDownload"]; return o == null || (bool)o; }
            set { this.ViewState["PermettiDownload"] = value; }
        }

        public IEnumerable<VisuraEndoListItem> DataSource { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.dgProcedimenti.DataSource = this.DataSource;
            this.dgProcedimenti.DataBind();
        }

        protected override void OnPreRender(EventArgs e)
        {
            this.dgProcedimenti.Columns[this.dgProcedimenti.Columns.Count - 1].Visible = this.PermettiDownload;
        }

        protected void dgProcedimenti_RowDataBound(object sender, System.Web.UI.WebControls.GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var rptAllegatiEndo = (Repeater)e.Row.FindControl("rptAllegatiEndo");
                var dataItem = (e.Row.DataItem as VisuraEndoListItem);

                rptAllegatiEndo.ItemDataBound += (a, repeaterRow) =>
                {
                    if (repeaterRow.Item.ItemType == ListItemType.Header)
                    {
                        var ltrNumeroAllegati = (Literal)repeaterRow.Item.FindControl("ltrNumeroAllegati");
                        ltrNumeroAllegati.Text = $"{dataItem.NumeroAllegati} {(dataItem.NumeroAllegati > 1 ? "Allegati" : "Allegato")}";
                    }
                };

                rptAllegatiEndo.DataSource = dataItem.Allegati?.Select(x => new
                {
                    NumeroAllegati = dataItem.NumeroAllegati,
                    Url = this.ResolveClientUrl(this._urlDownloadOggettiService.GetUrlDownload(Convert.ToInt32(x.Oggetto.CODICEOGGETTO))),
                    Descrizione = x.ALLEGATOEXTRA,
                    NomeFile = x.Oggetto.NOMEFILE,
                    Md5 = x.Oggetto.Md5OrNull
                });
                rptAllegatiEndo.DataBind();

            }
        }

        private void RptAllegatiEndo_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            throw new NotImplementedException();
        }
    }
}