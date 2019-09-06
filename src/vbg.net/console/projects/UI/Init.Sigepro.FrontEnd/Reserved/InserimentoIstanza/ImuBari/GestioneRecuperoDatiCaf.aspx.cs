using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Imu;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ImuBari
{
    public partial class GestioneRecuperoDatiCaf : IstanzeStepPage
    {
        #region Dati letti dal workflow xml
        public int CodiceTipoSoggettoAnagrafica
        {
            get { object o = this.ViewState["CodiceTipoSoggettoanagrafica"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceTipoSoggettoanagrafica"] = value; }
        }

        public int CodiceTipoSoggettoAnagraficaCollegata
        {
            get { object o = this.ViewState["CodiceTipoSoggettoAnagraficaCollegata"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceTipoSoggettoAnagraficaCollegata"] = value; }
        }


        #endregion

        [Inject]
        protected ImuBariService _imuService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
        }

        public override bool CanEnterStep()
        {
            _imuService.InserisciOperatoreeCafNeiSoggettiDellaDomanda(IdDomanda, UserAuthenticationResult.DatiUtente.Codicefiscale, CodiceTipoSoggettoAnagrafica, CodiceTipoSoggettoAnagraficaCollegata);

            return false;
        }
    }
}