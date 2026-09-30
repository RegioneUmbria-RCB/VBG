using Init.Sigepro.FrontEnd.GestioneMovimenti.ExternalServices;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class Scadenzario : ReservedBasePage
    {
        [Inject]
        public IScadenzeService _scadenzeService { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                var anag = UserAuthenticationResult.DatiUtente;

                string userName = anag.Codicefiscale;
                //string password = anag.Password;

                var listaScadenze = _scadenzeService.GetListaScadenzeByCodiceFiscale(Software, userName);

                dgScadenze.DataSource = listaScadenze;
                dgScadenze.DataBind();
            }
        }

        protected void dgScadenze_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "DettaglioIstanza")
            {
                string idIstanza = e.CommandArgument.ToString();

                Redirect("~/Reserved/DettaglioIstanzaEx.aspx", qs =>
                {
                    var returnTo = UrlBuilder.Url("~/Reserved/Scadenzario.aspx", mp =>
                    {
                        mp.Add(new QsAliasComune(this.IdComune));
                        mp.Add(new QsSoftware(this.Software));
                    });


                    qs.Add(new QsUuidIstanza(idIstanza));
                    qs.Add(new QsReturnTo(returnTo));
                });
            }
        }

        protected void dgScadenze_SelectedIndexChanged(object sender, EventArgs e)
        {
            string idScadenza = dgScadenze.DataKeys[dgScadenze.SelectedIndex].Value.ToString();

            Redirect("~/Reserved/GestioneMovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            var newUrl = UrlBuilder.Url("~/Reserved/Default.aspx", x =>
            {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
            });

            Response.Redirect(newUrl);
        }
    }
}
