using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.DatiDinamici.Eliminazione;
using Ninject;
using Ninject.Web;
using System;

namespace Sigepro.net.Utilita.SchedeDinamiche
{
    public partial class EliminazioneScheda : PageBase
    {
        [Inject]
        public IAuthenticationManager _authenticationManager { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.Request.QueryString["alias"]))
            {
                this.txtAlias.Value = this.Request.QueryString["alias"];
            }

            if (!String.IsNullOrEmpty(this.Request.QueryString["idScheda"]))
            {
                this.txtIdScheda.Value = this.Request.QueryString["idScheda"];
            }
        }

        protected void btnElimina_Click(object sender, EventArgs e)
        {
            var alias = this.txtAlias.Value;
            var idScheda = this.txtIdScheda.Value;

            if (String.IsNullOrEmpty(alias))
            {
                this.Master.Errori.Add("Il campo alias è obbligatorio");
            }

            if (String.IsNullOrEmpty(idScheda))
            {
                this.Master.Errori.Add("Il campo idScheda è obbligatorio");
            }

            var authInfo = this._authenticationManager.GetTokenApplicativo(alias);

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    var eliminazioneService = new EliminazioneSchedeDinamicheService(authInfo.IdComune, db);

                    eliminazioneService.Elimina(Convert.ToInt32(idScheda));
                }
                catch (Exception ex)
                {
                    this.Master.Errori.Add("Si è verificato un errore durante l'eliminazione della scheda: " + ex.Message);
                }
            }
        }
    }
}