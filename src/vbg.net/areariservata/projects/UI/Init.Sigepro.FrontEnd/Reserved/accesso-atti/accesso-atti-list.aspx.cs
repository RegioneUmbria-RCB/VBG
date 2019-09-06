using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Siena;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.accesso_atti
{
    public partial class accesso_atti_list : ReservedBasePage
    {
        [Inject]
        public SienaAccessoAttiService _service { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            this.gvRisultati.DataSource = this._service.GetListaPratiche(this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value);
            this.gvRisultati.DataBind();
        }

        protected void gvRisultati_SelectedIndexChanged(object sender, EventArgs e)
        {
            var uuid = gvRisultati.DataKeys[gvRisultati.SelectedIndex].Values["UUID"];
            var idAccessoAtti = Convert.ToInt32(gvRisultati.DataKeys[gvRisultati.SelectedIndex].Values["IdAccessoAtti"]);
            var codiceIstanza = Convert.ToInt32(gvRisultati.DataKeys[gvRisultati.SelectedIndex].Values["CodiceIstanza"]);
            var mostraDocumentiNonValidi = Convert.ToBoolean(gvRisultati.DataKeys[gvRisultati.SelectedIndex].Values["MostraDocumentiNonValidi"]);
            var codiceAnagrafe = this._authenticationDataResolver.DatiAutenticazione.DatiUtente.Codiceanagrafe.Value;

            // Loggo l'accesso
            this._service.LogAccessoPratica(codiceAnagrafe, idAccessoAtti, codiceIstanza);

            // Effettuo il redirect all'istanza 
            var uuidCorretto = uuid + (mostraDocumentiNonValidi ? "1" : "0");

            var url = UrlBuilder.Url("~/reserved/accesso-atti/accesso-atti-dettaglio.aspx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(new QsUuidIstanza(uuidCorretto));
            });

            Response.Redirect(url);

        }

        protected void cmdClose_Click(object sender, EventArgs e)
        {
            Response.Redirect(UrlBuilder.Url("~/reserved/default.aspx",  x => {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
            }));
        }
    }
}