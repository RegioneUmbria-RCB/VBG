using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using Ninject;
using System;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class TestStcAdapter : IstanzeStepPage
    {
        [Inject]
        protected IIstanzaStcAdapter _stcAdapter { get; set; }

        [Inject]
        protected ISalvataggioDomandaStrategy LogicaCaricamento { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.Adapt();
        }

        private void Adapt()
        {
            var domanda = this.LogicaCaricamento.GetById(this.IdDomanda);

            var dettaglioPratica = this._stcAdapter.Adatta(domanda);

            using (var fs = new System.IO.MemoryStream())
            {
                var xs = new XmlSerializer(dettaglioPratica.GetType());
                xs.Serialize(fs, dettaglioPratica);

                this.Response.Clear();
                this.Response.AddHeader("content-disposition", "attachment; filename=istanza.xml");
                this.Response.BinaryWrite(fs.ToArray());
                this.Response.End();
            }
        }
    }
}