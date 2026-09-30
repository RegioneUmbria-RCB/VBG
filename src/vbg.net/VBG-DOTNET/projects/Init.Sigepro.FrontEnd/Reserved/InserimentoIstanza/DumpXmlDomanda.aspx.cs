using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DumpXmlDomanda : IstanzeStepPage
    {
        [Inject]
        protected ISalvataggioDomandaStrategy SalvataggioDomandaStrategy { get; set; }
        [Inject]
        protected IIstanzaSigeproAdapterService istanzaSigeproAdapterService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Response.Clear();
            this.Response.ContentType = "text/plain;charset=UTF-8";

            if (!String.IsNullOrEmpty(this.Request.QueryString["vbg"]))
            {
                var pratica = this.SalvataggioDomandaStrategy.GetById(this.IdDomanda);
                var xml = this.istanzaSigeproAdapterService.ToIstanzaBackoffice(pratica.ReadInterface).ToXmlModelloRiepilogo();
                this.Response.Write(xml);
            }
            else
            {
                var domanda = this.SalvataggioDomandaStrategy.GetAsXml(this.IdDomanda);
                this.Response.BinaryWrite(domanda);
            }

            this.Response.End();
        }
    }
}