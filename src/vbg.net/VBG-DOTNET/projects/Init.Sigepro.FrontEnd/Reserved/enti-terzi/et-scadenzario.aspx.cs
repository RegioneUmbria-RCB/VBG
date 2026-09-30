using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.SIGePro.Manager.DTO.Scadenzario;
using Ninject;
using System;
using System.Linq;
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

                var listaScadenze = Enumerable.Empty<ElementoListaScadenzeDto>();

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

                var returnTo = UrlBuilder.Url("~/reserved/enti-terzi/et-scadenzario.aspx", qs => {
                    qs.Add(new QsAliasComune(this.IdComune));
                    qs.Add(new QsSoftware(this.Software));
                });

                var redir = UrlBuilder.Url("~/reserved/enti-terzi/et-dettaglio-pratica.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(this.IdComune));
                    qs.Add(new QsSoftware(this.Software));
                    qs.Add(new QsUuidIstanza(idIstanza));
                    qs.Add(new QsReturnTo(returnTo));
                });

                Response.Redirect(redir);
            }
        }

        protected void dgScadenze_SelectedIndexChanged(object sender, EventArgs e)
        {
            var idScadenza = dgScadenze.DataKeys[dgScadenze.SelectedIndex].Value.ToString();

            var returnTo = UrlBuilder.Url("~/reserved/enti-terzi/et-scadenzario.aspx", qs => {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
            });

            var redir = UrlBuilder.Url("~/reserved/gestionemovimenti/effettuamovimento.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
                qs.Add("IdMovimento", idScadenza);
                qs.Add(new QsReturnTo(returnTo));
            });

            Response.Redirect(redir);
        }

        protected void Unnamed_Click(object sender, EventArgs e)
        {
            var redir = UrlBuilder.Url("~/reserved/benvenuto.aspx", qs =>
            {
                qs.Add(new QsAliasComune(this.IdComune));
                qs.Add(new QsSoftware(this.Software));
            });

            Response.Redirect(redir);
        }
    }
}