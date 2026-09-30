using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class dati_generali : Ninject.Web.UserControlBase
    {
        public class VisuraDatiGeneraliDataSource
        {
            public string ComunePratica { get; set; }

            public string NumeroProtocollo { get; set; }
            public DateTime? DataProtocollo { get; set; }

            public string NumeroPratica { get; set; }
            public DateTime? DataPratica { get; set; }

            public string Oggetto { get; set; }
            public string Intervento { get; set; }
            public string Stato { get; set; }

            public string ResponsabileProcedimento { get; set; }
            public string Istruttore { get; set; }
            public string Operatore { get; set; }
            public string PosizioneArchivio { get; set; }
        }

        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }


        public bool MostraDatiProtocollo
        {
            get { return this.ViewState["MostraDatiProtocollo"] == null ? false : Convert.ToBoolean(this.ViewState["MostraDatiProtocollo"]); }
            set { this.ViewState["MostraDatiProtocollo"] = value; }
        }


        public bool MostraStatoPratica => !this._configurazione.Parametri.DettaglioPratica.NascondiStato;

        public bool MostraRiferimenti => !this._configurazione.Parametri.DettaglioPratica.NascondiResponsabili;

        public bool MostraPosizioneArchivio => this._configurazione.Parametri.DettaglioPratica.MostraPosizioneArchivio;

        public VisuraDatiGeneraliDataSource DataSource { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.lblComune.Value = this.DataSource.ComunePratica;

            this.lblProtocollo.Value = this.DataSource.NumeroProtocollo;
            this.lblDataProtocollo.Value = this.DataSource.DataProtocollo.HasValue ? this.DataSource.DataProtocollo.Value.ToString("dd/MM/yyyy") : string.Empty;

            this.lblNumeroPratica.Value = this.DataSource.NumeroPratica;
            this.lblDataPresentazione.Value = this.DataSource.DataPratica.HasValue ? this.DataSource.DataPratica.Value.ToString("dd/MM/yyyy") : string.Empty;

            this.lblOggetto.Value = this.DataSource.Oggetto;
            this.lblIntervento.Value = this.DataSource.Intervento;
            this.lblStatoPratica.Value = this.DataSource.Stato;

            this.lblResponsabileProc.Value = this.DataSource.ResponsabileProcedimento;
            this.lblIstruttore.Value = this.DataSource.Istruttore;
            this.lblOperatore.Value = this.DataSource.Operatore;

            this.lblPosizioneArchivio.Value = this.DataSource.PosizioneArchivio;
        }
    }
}