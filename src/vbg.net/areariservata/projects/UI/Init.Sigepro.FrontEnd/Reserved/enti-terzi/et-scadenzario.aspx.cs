using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Scadenzario;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.enti_terzi
{
    public partial class et_scadenzario : ReservedBasePage
    {
        [Inject]
        public IScadenzeService _scadenzeService { get; set; }
        [Inject]
        public IScrivaniaEntiTerziService _etService { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var codiceAnagrafe = new ETCodiceAnagrafe(UserAuthenticationResult.DatiUtente.Codiceanagrafe.Value);

                var listaScadenze = Enumerable.Empty<Scadenza>();

                if (this._etService.PuoEffettuareMovimenti(codiceAnagrafe))
                {
                    var amministrazione = this._etService.GetDatiAmministrazioneCollegata(codiceAnagrafe);
                    listaScadenze = this._scadenzeService.GetListaScadenzeEntiTerzi(amministrazione.PartitaIva);
                }

                dgScadenze.DataSource = listaScadenze;
                dgScadenze.DataBind();
            }
        }

        protected void dgScadenze_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "DettaglioIstanza")
            {
                string idIstanza = e.CommandArgument.ToString();

                Redirect("~/reserved/enti-terzi/et-dettaglio-pratica.aspx", qs =>
                {

                    qs.Add(new QsUuidIstanza(idIstanza));
                    qs.Add(new QsReturnTo("~/reserved/enti-terzi/et-scadenzario.aspx"));
                });
            }
        }

        protected void dgScadenze_SelectedIndexChanged(object sender, EventArgs e)
        {
            string idScadenza = dgScadenze.DataKeys[dgScadenze.SelectedIndex].Value.ToString();

            Redirect("~/reserved/gestionemovimenti/effettuamovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
        }

        protected void Unnamed_Click(object sender, EventArgs e)
        {
            Redirect("~/reserved/benvenuto.aspx",x => { });
        }
    }
}