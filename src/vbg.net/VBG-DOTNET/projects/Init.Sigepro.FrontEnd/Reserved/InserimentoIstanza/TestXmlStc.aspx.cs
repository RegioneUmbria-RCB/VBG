using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using Ninject;
using System;
using System.IO;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class TestXmlStc : IstanzeStepPage
    {
        [Inject]
        protected IIstanzaStcAdapter _stcAdapter { get; set; }
        [Inject]
        protected ISalvataggioDomandaStrategy SalvataggioDomandaStrategy { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            var domanda = this.SalvataggioDomandaStrategy.GetById(this.IdDomanda);

            var domandaStc = this._stcAdapter.Adatta(domanda);

            using (var fs = File.Open(@"c:\temp\classe.xml", FileMode.Create))
            {
                var xs = new XmlSerializer(domandaStc.GetType());
                xs.Serialize(fs, domandaStc);
            }

            this.Response.Write(DateTime.Now + " - Fatto");
        }
    }
}