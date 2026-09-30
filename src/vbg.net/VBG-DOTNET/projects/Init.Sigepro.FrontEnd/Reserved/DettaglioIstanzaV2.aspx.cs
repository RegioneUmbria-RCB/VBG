using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class DettaglioIstanzaV2 : ReservedBasePage
    {
        [Inject]
        public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }
        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }

        protected string CodiceIstanza
        {
            get
            {
                var obj = this.Request.QueryString["Id"];

                if (String.IsNullOrEmpty(obj))
                    throw new Exception("Codice istanza non valido o non impostato");

                return obj.ToString();
            }
        }

        public DettaglioIstanzaV2()
        {
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            var domanda = this._istanzePresentateRepository.GetDettaglioPratica(this.IdComune, this.Software, this.CodiceIstanza);

            if (domanda.dettaglioErrore != null && domanda.dettaglioErrore.Length > 0)
            {
                foreach (var errore in domanda.dettaglioErrore)
                {
                    this.Errori.Add(errore.descrizione);
                }

                this.visuraCtrl.Visible = false;

                return;
            }

            this.visuraCtrl.DataSource = domanda.dettaglioPratica;
            this.visuraCtrl.DataBind();

            this.ltrIntestazioneDettaglio.Text = this._configurazione.Parametri.MessaggioIntestazioneVisura;
        }

        protected void VisuraCtrlV2_ErroreRendering(object sender, string errore)
        {
            this.Errori.Add(errore);
        }

        public void cmdClose_Click(object sender, EventArgs e)
        {
            this.Redirect("~/Reserved/IstanzePresentateV2.aspx", (x) =>
            {
                x.Add("fromLastResult", "true");
            });
        }
    }
}