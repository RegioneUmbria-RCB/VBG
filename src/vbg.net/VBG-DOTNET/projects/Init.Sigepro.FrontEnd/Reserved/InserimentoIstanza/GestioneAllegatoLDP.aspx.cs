using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PraticheEdilizieSiena;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneAllegatoLDP : IstanzeStepPage
    {
        [Inject]
        protected IPraticheEdilizieSienaService _ldpService { get; set; }

        public string NomeAllegato
        {
            get { object o = this.ViewState["NomeAllegato"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["NomeAllegato"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override bool CanEnterStep()
        {
            this._ldpService.AllegaPdfADomanda(IdDomanda, this.NomeAllegato);

            return false;
        }
    }
}