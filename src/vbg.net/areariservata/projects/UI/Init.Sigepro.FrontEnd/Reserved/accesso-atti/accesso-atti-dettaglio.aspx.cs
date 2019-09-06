using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.accesso_atti
{
    public partial class accesso_atti_dettaglio : ReservedBasePage
    {
        protected QsUuidIstanza IdIstanza
        {
            get { return new QsUuidIstanza(Request.QueryString); }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            var mostraDocumentiNonValidi = this.IdIstanza.Value.Substring(this.IdIstanza.Value.Length - 1, 1) == "1";
            var uuid = this.IdIstanza.Value.Substring(0, this.IdIstanza.Value.Length - 1);

            VisuraExCtrl1.MostraDocumentiNonValidi = mostraDocumentiNonValidi;
            VisuraExCtrl1.MostraScadenze = false;
            VisuraExCtrl1.MostraPraticheCollegate = false;
            VisuraExCtrl1.EffettuaVisuraIstanza(IdComune, Software, uuid);
        }

        private void TornaAllaLista()
        {
            var url = UrlBuilder.Url("~/reserved/accesso-atti/accesso-atti-list.aspx", x =>
            {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
            });

            Response.Redirect(url);
        }

        protected void cmdClose_Click(object sender, EventArgs e)
        {
            TornaAllaLista();
        }
    }
}