using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Ninject;
using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class TestXmlStc : IstanzeStepPage
    {
        [Inject]
        protected IIstanzaStcAdapter _stcAdapter { get; set; }
        [Inject]
        protected ISalvataggioDomandaStrategy SalvataggioDomandaStrategy { get;set; }


        protected void Page_Load(object sender, EventArgs e)
        {
            var domanda = SalvataggioDomandaStrategy.GetById(IdDomanda);

            var domandaStc = _stcAdapter.Adatta(domanda);

            using (var fs = File.Open(@"c:\temp\classe.xml", FileMode.Create))
            {
                var xs = new XmlSerializer(domandaStc.GetType());
                xs.Serialize(fs, domandaStc);
            }

            Response.Write(DateTime.Now + " - Fatto");
        }
    }
}