using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class dati_generali : System.Web.UI.UserControl
    {
        public class VisuraDatiGeneraliDataSource
        {
            public string NumeroProtocollo { get; set; }
            public DateTime? DataPratocollo { get; set; }

            public string NumeroPratica { get; set; }
            public DateTime? DataPratica { get; set; }

            public string Oggetto { get; set; }
            public string Intervento { get; set; }
            public string Stato { get; set; }

            public string ResponsabileProcedimento { get; set; }
            public string Istruttore { get; set; }
            public string Operatore { get; set; }
        }

        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }


        public bool DaArchivio
        {
            get { object o = this.ViewState["DaArchivio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["DaArchivio"] = value; }
        }

        public bool MostraStatoPratica => !this._configurazione.Parametri.DettaglioPratica.NascondiStato;
        public bool MostraRiferimenti => !this._configurazione.Parametri.DettaglioPratica.NascondiResponsabili;

        public VisuraDatiGeneraliDataSource DataSource { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.lblProtocollo.Value = this.DataSource.NumeroProtocollo;
            this.lblDataProtocollo.Value = this.DataSource.DataPratocollo.HasValue ? this.DataSource.DataPratocollo.Value.ToString("dd/MM/yyyy") : string.Empty;

            this.lblNumeroPratica.Value = this.DataSource.NumeroPratica;
            this.lblDataPresentazione.Value = this.DataSource.DataPratica.HasValue ? this.DataSource.DataPratica.Value.ToString("dd/MM/yyyy") : string.Empty;

            this.lblOggetto.Value = this.DataSource.Oggetto;
            this.lblIntervento.Value = this.DataSource.Intervento;
            this.lblStatoPratica.Value = this.DataSource.Stato;

            this.lblResponsabileProc.Value = this.DataSource.ResponsabileProcedimento;
            this.lblIstruttore.Value = this.DataSource.Istruttore;
            this.lblOperatore.Value = this.DataSource.Operatore;
        }
    }
}