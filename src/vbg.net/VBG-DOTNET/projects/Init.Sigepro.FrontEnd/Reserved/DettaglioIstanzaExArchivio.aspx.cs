using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.QsParameters;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class DettaglioIstanzaExArchivio : ReservedBasePage
    {
        [Inject]
        protected IConfigurazione<ParametriUrlAreaRiservata> _parametriUrl { get; set; }


        protected QsUuidIstanza IdIstanza
        {
            get { return new QsUuidIstanza(Request.QueryString); }
        }

        protected string ReturnTo
        {
            get
            {
                string str = Request.QueryString["ReturnTo"];

                if (String.IsNullOrEmpty(str))
                {
                    return "~/Reserved/ArchivioPratiche.aspx";
                }

                return str;
            }
        }

        protected string ReturnToArgs
        {
            get
            {
                return Request.QueryString["ReturnToArgs"];
            }
        }

        protected bool Popup
        {
            get
            {
                var qs = Request.QueryString["popup"];

                return !String.IsNullOrEmpty(qs);
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.MostraIntestazione = !Popup;
            this.Master.MostraFooter = !Popup;

            VisuraExCtrl1.ScadenzaSelezionata += new VisuraExCtrl.ScadenzaSelezionataDelegate(visuraCtrl_ScadenzaSelezionata);

            if (!IsPostBack)
            {
                VisuraExCtrl1.LivelloAccesso = LivelloAccessoVisura.AccessoAnonimo;
                VisuraExCtrl1.EffettuaVisuraIstanza(IdIstanza.Value);
            }

        }

        void visuraCtrl_ScadenzaSelezionata(object sender, string idScadenza)
        {
            Redirect("~/Reserved/GestioneMovimenti/EffettuaMovimento.aspx", qs => qs.Add("IdMovimento", idScadenza));
        }


        protected void cmdClose_Click(object sender, EventArgs e)
        {
            Redirect(ReturnTo, qs =>
            {

                if (Popup)
                {
                    qs.Add("popup", 1);
                }
            });
        }


    }
}
