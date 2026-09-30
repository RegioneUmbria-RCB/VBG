using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class ModificaPassword : ReservedBasePage
    {
        [Inject]
        public IAnagraficheService _anagrafeRepository { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            var url = UrlBuilder.Url("~/reserved/default.aspx", pb => pb.Add(nameof(this.IdComune), this.IdComune).Add(nameof(this.Software), this.Software));

            this.Response.Redirect(url);
        }

        protected void cmdConfirm_Click(object sender, EventArgs e)
        {
            try
            {
                string idComune = this.UserAuthenticationResult.Alias;
                int codiceAnagrafe = this.UserAuthenticationResult.DatiUtente.Codiceanagrafe.GetValueOrDefault(-1);
                string vecchiaPassword = this.txtVecchiaPassword.Text;
                string nuovaPassword = this.txtNuovaPassword.Text;
                string confermaPassword = this.txtConfermaNuovaPassword.Text;

                //this._anagrafeRepository.ModificaPassword(idComune, codiceAnagrafe, vecchiaPassword, nuovaPassword, confermaPassword);

                this.ClientScript.RegisterStartupScript(this.GetType(), "startup", "alert('Password modificata correttamente')", true);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }
    }
}
