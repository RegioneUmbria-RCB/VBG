using Init.Sigepro.FrontEnd.AppLogic.GestioneFilesExcel;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneFilesExcel : IstanzeStepPage
    {
        [Inject]
        protected IDatiDinamiciExcelService ExcelService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
        }

        public override void OnInitializeStep()
        {
            ExcelService.EstraiDatiDinamiciDaFilesExcel(IdDomanda);
        }

        public override bool CanEnterStep()
        {
            return false;
        }
    }
}