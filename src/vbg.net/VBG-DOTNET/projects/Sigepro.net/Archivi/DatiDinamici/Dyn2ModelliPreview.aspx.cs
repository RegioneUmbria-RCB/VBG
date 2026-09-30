using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Istanze;
using Init.SIGePro.Manager.Logic.DatiDinamici.ModelliFactory;
using Init.Utils;
using SIGePro.Net;
using System;
using System.Xml;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;

namespace Sigepro.net.Archivi.DatiDinamici
{
    public partial class Dyn2ModelliPreview : BasePage
    {
        protected int IdModello
        {
            get { return Convert.ToInt32(this.Request.QueryString["IdModello"]); }
        }

        public override string Software
        {
            get
            {
                return "TT";
            }
        }


        public Dyn2ModelliPreview()
        {
            //VerificaSoftware = false;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();

            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
        }

        public override void DataBind()
        {
            this.VerificaHtmlCampi();

            var dap = new IstanzeDyn2DataAccessFactory(this.Database, this.IdComune, -1);
            var loader = new ModelloDinamicoLoader(dap, this.IdComune, ContestoScriptEnum.Backoffice);
            var mod = new BackendModelliFactory().CreaModelloIstanza(loader, this.IdModello, 0, true);

            this.ModelloDinamicoRenderer1.DataSource = mod;
            this.ModelloDinamicoRenderer1.DataBind();
        }
        protected void VerificaHtmlCampi()
        {
            var modelliMgr = new Dyn2ModelliDMgr(this.Database);
            var campiMgr = new Dyn2CampiMgr(this.Database);
            var testiMgr = new Dyn2ModelliDTestiMgr(this.Database);

            var listaCampi = modelliMgr.GetListByIdModello(this.IdComune, Convert.ToInt32(this.IdModello));

            foreach (var rigaCampi in listaCampi)
            {
                var testo = String.Empty;

                if (rigaCampi.FkD2cId.HasValue)
                {
                    testo = campiMgr.GetById(this.IdComune, rigaCampi.FkD2cId.Value).Descrizione;
                }
                else
                {
                    testo = testiMgr.GetById(this.IdComune, rigaCampi.FkD2mdtId.Value).Testo;
                }

                try
                {
                    this.ValidaXmlInInput(testo);
                }
                catch (Exception ex)
                {
                    this.Errori.Add(String.Format("Errore nel campo alla riga {0} e colonna {1}: {2}", rigaCampi.Posverticale, rigaCampi.Posorizzontale, ex.Message));
                }
            }
        }

        private void ValidaXmlInInput(string testo)
        {
            const string xmlFmt = "<?xml version=\"1.0\"?><contenuto>{0}</contenuto>";

            var xml = String.Format(xmlFmt, testo);

            try
            {
                var doc = new XmlDocument();
                doc.Load(StreamUtils.StringToStream(xml));
            }
            catch (XmlException ex)
            {
                throw new Exception("Il testo immesso contiene tag non chiusi o entità html non valide. (dettagli tecnici: <i>" + ex.Message + "</i>)");
            }
        }
    }
}
