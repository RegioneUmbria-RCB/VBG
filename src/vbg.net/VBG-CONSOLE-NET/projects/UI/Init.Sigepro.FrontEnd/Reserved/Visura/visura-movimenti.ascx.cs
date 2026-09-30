using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_movimenti : System.Web.UI.UserControl
    {
        public class VisuraMovimentiListItem
        {
            public int Id { get; set; }
            public int CodiceIstanza { get; set; }
            public int NumeroAllegati { get; set; }
            public bool HaAllegati { get { return this.NumeroAllegati > 0; } }
            public string Descrizione { get; set; }
            public DateTime? Data { get; set; }
            public string Parere { get; set; }
            public string NumeroProtocollo { get; set; }
            public DateTime? DataProtocollo { get; set; }
            public string UuidPraticaCollegata { get; set; }
            public bool HaPraticaCollegata { get { return !String.IsNullOrEmpty(this.UuidPraticaCollegata); } }
        }

        public bool DaArchivio
        {
            get { return !this.Visible; }
            set { this.Visible = !value; }
        }

        public IEnumerable<VisuraMovimentiListItem> DataSource { get; set; }

        public string UrlAllegati
        {
            get
            {
                var pagina = this.Page as ReservedBasePage;
                var url = UrlBuilder.Url("~/Reserved/ListaAllegatiMovimento.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(pagina.IdComune));
                    qs.Add(new QsSoftware(pagina.Software));
                });

                return ResolveUrl(url);
            }
        }

        public string UrlPopupVisura
        {
            get
            {
                var pagina = this.Page as ReservedBasePage;

                var url = UrlBuilder.Url("~/Reserved/visura/sub-visura.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(pagina.IdComune));
                    qs.Add(new QsSoftware(pagina.Software));
                });

                return ResolveClientUrl(url);
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            if (this.DaArchivio)
            {
                return;
            }

            dgMovimenti.DataSource = this.DataSource;
            dgMovimenti.DataBind();
        }
    }
}