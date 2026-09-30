using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
{
    public partial class AnnullaPagamenti : IstanzeStepPage
    {
        [Inject]
        protected PagamentiMIPService PagamentiService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                PagamentiService.AnnullaTuttiIPagamenti(IdDomanda);
            }
        }
    }
}