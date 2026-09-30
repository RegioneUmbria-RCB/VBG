using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni;
using log4net;
using Ninject;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Reflection;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.CalcoloOneri
{
    public partial class Istanze_CalcoloOneri_Rateizzazione : BasePage
    {
        [Inject]
        public RateizzazioniService _rateizzazioniService { get; set; }

        protected int? CodiceRaggruppamento => string.IsNullOrEmpty(this.Request.QueryString["codiceraggruppamento"]) ? (int?)null : Convert.ToInt32(this.Request.QueryString["codiceraggruppamento"]);
        protected int? CodiceOnere => string.IsNullOrEmpty(this.Request.QueryString["id"]) ? (int?)null : Convert.ToInt32(this.Request.QueryString["id"]);
        protected int CodiceIstanza => Convert.ToInt32(this.Request.QueryString["codiceistanza"]);

        private readonly ILog _log = LogManager.GetLogger(typeof(Istanze_CalcoloOneri_Rateizzazione));

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!this.Page.IsPostBack)
            {
                this.SetEtichetta();
                this.BindComboTipiRateizzazioni();
                this.BindComboFrequenzaRate();
                this.BindComboScadenza();
                this.SetRateizzazione();

                IstanzeOneriMgr mgr = new IstanzeOneriMgr(this.Database, this.AuthenticationInfo);

                this.cmdRateizza1.Visible = (this.CodiceOnere.HasValue) ?
                                        !mgr.IsOnereRateizzato(this.IdComune, this.CodiceIstanza, this.CodiceOnere.Value) :
                                        !mgr.IsRaggruppamentoRateizzato(this.IdComune, this.CodiceIstanza, this.CodiceRaggruppamento.Value);

                this.cmdDerateizza1.Visible = !this.cmdRateizza1.Visible;
            }
        }

        #region Metodi usati per settare la pagina nel momento in cui viene invocata
        private void BindComboScadenza()
        {
            this.ddlComportamentoScadenza.Item.DataSource = (new OneriTipiRateizzazioneMgr(this.Database)).GetTipiScadenze();
            this.ddlComportamentoScadenza.Item.DataBind();
        }

        private void BindComboTipiRateizzazioni()
        {
            OneriTipiRateizzazioneMgr mgrotr = new OneriTipiRateizzazioneMgr(this.Database);
            OneriTipiRateizzazione otr = new OneriTipiRateizzazione();
            otr.Idcomune = this.IdComune;
            otr.Software = this.Software;

            List<OneriTipiRateizzazione> list = mgrotr.GetList(otr);
            this.ddlTipiRateizzazione.Item.DataSource = list;
            this.ddlTipiRateizzazione.Item.DataBind();

            //Leggi configurazione per l'utente dalla tabella CONFIGURAZIONEUTENTE
            if ((list != null) && (list.Count != 0))
            {
                this.ddlTipiRateizzazione.Value = this.LeggiConfUtente(list[0].Tiporateizzazione.ToString());
            }
        }

        private void BindComboFrequenzaRate()
        {
            this.ddlFrequenzaRateAmmortamentoFrancese.Item.DataSource = new OneriTipiRateizzazioneMgr(this.Database).GetFrequenzaRate();
            this.ddlFrequenzaRateAmmortamentoFrancese.Item.DataBind();
        }

        protected void SetEtichetta()
        {
            //Rateizzazione per causale
            if (!this.CodiceRaggruppamento.HasValue)
            {
                this.lblTitolo1.Visible = false;
                this.lblRaggruppamento.Visible = false;

                IstanzeOneriMgr mgrio = new IstanzeOneriMgr(this.Database, this.AuthenticationInfo);
                IstanzeOneri io = mgrio.GetById(this.IdComune, this.CodiceOnere.ToString());
                this.lblImporto.Text = io.PREZZO.ToString();

                if (io.PREZZOISTRUTTORIA.GetValueOrDefault(0.0d) == 0.0d)
                {
                    this.lblImportoIstrEtichetta.Visible = false;
                    this.lblImportoIstr.Visible = false;
                }
                else
                {
                    this.lblImportoIstr.Text = io.PREZZOISTRUTTORIA.ToString();
                }

                TipiCausaliOneriMgr mgrtco = new TipiCausaliOneriMgr(this.Database);
                TipiCausaliOneri tco = mgrtco.GetById(this.IdComune, Convert.ToInt32(io.FKIDTIPOCAUSALE));
                this.lblCausale.Text = tco.CoDescrizione;
            }
            //Rateizzazione per raggruppamento
            if (this.CodiceRaggruppamento.HasValue)
            {
                this.lblCausale.Visible = false;

                RaggruppamentoCausaliOneriMgr mgr = new RaggruppamentoCausaliOneriMgr(this.Database);
                RaggruppamentoCausaliOneri rco = mgr.GetById(this.IdComune, this.CodiceRaggruppamento.Value);

                this.lblRaggruppamento.Text = rco.RcoDescr;
                this.lblImporto.Text = mgr.GetImportoTotale(this.IdComune, this.CodiceIstanza, this.CodiceRaggruppamento.Value).ToString();

                double dImportoTotaleIstruttoria = mgr.GetImportoTotale(this.IdComune, this.CodiceIstanza, this.CodiceRaggruppamento.Value, true);
                this.lblImportoIstr.Text = dImportoTotaleIstruttoria.ToString();

                if (dImportoTotaleIstruttoria == 0.0)
                {
                    this.lblImportoIstrEtichetta.Visible = false;
                    this.lblImportoIstr.Visible = false;
                }
            }
        }

        //Questo metodo viene usato anche in seguito alla modifica del valore della combo box adibita a modifcare il tipo di rateizzazione
        protected void SetRateizzazione()
        {
            if (!string.IsNullOrEmpty(this.ddlTipiRateizzazione.Value))
            {
                this.txGiorniScadenze.Visible = true;
                this.ddlFrequenzaRateAmmortamentoFrancese.Visible = false;

                var mgrotr = new OneriTipiRateizzazioneMgr(this.Database);
                var otr = mgrotr.GetById(this.IdComune, Convert.ToInt32(this.ddlTipiRateizzazione.Value));

                var dataInizioRateizzazione = (OneriTipiRateizzazioneMgr.DataInizioRateizzazione)otr.Determdatainiziorate.GetValueOrDefault(0);

                var dataInizioRate = mgrotr.GetDataInizioRate(this.IdComune, this.CodiceIstanza, dataInizioRateizzazione, otr);

                this.txNumeroRate.Value = otr.Nrorate;
                this.txRipartizioneRate.Value = otr.Ripartizionerate;
                this.txGiorniScadenze.Value = otr.Frequenzarate;
                this.ddlComportamentoScadenza.Value = otr.Scadenzarate.ToString();
                this.txPercInteressi.Value = otr.Interessirate;
                this.txDataInizio.Value = dataInizioRate;
                this.chkInteressiLegali.Item.Checked = otr.FlagInteressiLegali == 1 ? true : false;
                this.ddlInteressiLegali.SelectedValue = otr.TipoAnatocismo.GetValueOrDefault(0).ToString();
                this.TxDataInizioIntLegali.Item.Text = this.txDataInizio.Value;

                this.ddlInteressiLegali.Visible = this.chkInteressiLegali.Item.Checked;
                this.TxDataInizioIntLegali.Visible = this.chkInteressiLegali.Item.Checked;
                this.txPercInteressi.Visible = !this.chkInteressiLegali.Item.Checked;
                this.chkInteressiLegali.Visible = true;

                if (otr.TipologiaRateizzazione == OneriTipiRateizzazioneMgr.TipoRateizzazione.AMMORTAMENTO_FRANCESE.ToString())
                {
                    this.txGiorniScadenze.Visible = false;
                    this.ddlFrequenzaRateAmmortamentoFrancese.Visible = true;
                    this.ddlFrequenzaRateAmmortamentoFrancese.Value = otr.Frequenzarate;
                    this.chkInteressiLegali.Item.Checked = false;
                    this.chkInteressiLegali.Visible = false;
                }

            }
        }
        #endregion

        #region Metodi usati per verificare la validità dei valori inseriti
        private bool VerificaValiditaCampi()
        {
            if (!this.VerificaCampoVuoto("txNumeroRate"))
                return false;
            if (!this.VerificaCampoVuoto("txRipartizioneRate"))
                return false;
            else if (!this.VerificaValiditaCampo("txRipartizioneRate"))
                return false;
            if (!this.VerificaCampoVuoto("txDataInizio"))
                return false;
            if (!this.VerificaCampoVuoto("txGiorniScadenze"))
                return false;
            else if (!this.VerificaValiditaCampo("txGiorniScadenze"))
                return false;

            return true;
        }

        private bool VerificaCampoVuoto(string sId)
        {
            WebControl ctrl = (WebControl)this.UpdatePanel1.FindControl(sId);
            if (string.IsNullOrEmpty(this.GetValue(ctrl)))
            {
                this.MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", null);
                return false;
            }
            return true;
        }

        private bool VerificaValiditaCampo(string sId)
        {
            WebControl ctrl = (WebControl)this.UpdatePanel1.FindControl(sId);
            string[] aElem = this.GetValue(ctrl).Split(';');
            foreach (string elem in aElem)
            {
                try
                {
                    Convert.ToDouble(elem);
                }
                catch (Exception)
                {
                    this.MostraErrore("Attenzione, uno dei campi non contiene un valore valido.", null);
                    return false;
                }
            }

            return true;
        }

        private string GetValue(WebControl m_control)
        {
            Type type = m_control.GetType();
            ControlValuePropertyAttribute[] attrb = (ControlValuePropertyAttribute[])type.GetCustomAttributes(typeof(ControlValuePropertyAttribute), true);
            if (attrb != null && attrb.Length > 0)
            {
                PropertyInfo pi = type.GetProperty(attrb[0].Name);
                if (pi == null) return String.Empty;
                object obj = pi.GetValue(m_control, null);
                return obj == null ? String.Empty : obj.ToString();
            }

            return String.Empty;
        }
        #endregion



        private void ScriviConfUtente(string valore)
        {
            string nomeParametro = this.GetNomeParametro();

            ConfigurazioneUtenteMgr confUtMgr = new ConfigurazioneUtenteMgr(this.Database);
            confUtMgr.SetValoreParametro(this.IdComune, this.AuthenticationInfo.CodiceResponsabile.Value, nomeParametro, valore);
        }

        private string GetNomeParametro()
        {
            string nomeParametro = "ROInt" + (new IstanzeMgr(this.Database).GetById(this.IdComune, this.CodiceIstanza).CODICEINTERVENTOPROC);
            if (!this.CodiceRaggruppamento.HasValue)
                nomeParametro += "#Caus" + new IstanzeOneriMgr(this.Database, this.AuthenticationInfo).GetById(this.IdComune, this.CodiceOnere.ToString()).FKIDTIPOCAUSALE;
            else
                nomeParametro += "#Rag" + this.CodiceRaggruppamento;

            return nomeParametro;
        }

        private string LeggiConfUtente(string valoreDefault)
        {
            string nomeParametro = this.GetNomeParametro();

            ConfigurazioneUtenteMgr confUtMgr = new ConfigurazioneUtenteMgr(this.Database);
            return confUtMgr.GetValoreParametro(this.IdComune, this.AuthenticationInfo.CodiceResponsabile.Value, nomeParametro, valoreDefault);
        }

        protected void cmdRateizza_Click(object sender, EventArgs e)
        {
            //Verifico validità campi
            if (!this.VerificaValiditaCampi())
                return;

            //Scrivi confiurazione nella tabella CONFIGURAZIONEUTENTE
            this.ScriviConfUtente(this.ddlTipiRateizzazione.Value);

            try
            {

                //Rateizzazione per causale
                if (this.CodiceOnere.HasValue)
                {
                    var importo = Convert.ToDecimal(this.lblImporto.Text);
                    decimal importoIstruttoria = 0;

                    if (!string.IsNullOrEmpty(this.lblImportoIstr.Text))
                        importoIstruttoria = Convert.ToDecimal(this.lblImportoIstr.Text);

                    IstanzeOneriMgr mgr = new IstanzeOneriMgr(this.Database, this.AuthenticationInfo);
                    IstanzeOneri io = mgr.GetById(this.IdComune, this.CodiceOnere.ToString());

                    //Verifico che la l'onere non sia stato già pagato
                    if (io.DATAPAGAMENTO.HasValue)
                        base.CloseCurrentPage();

                    var parametri = this.CreaParametriRateizzazione(this.CodiceOnere.Value);

                    if (!this._rateizzazioniService.RateizzaOnere(this.Token, parametri, importo, importoIstruttoria))
                        return;
                }

                //Rateizzazione per raggruppamento
                if (this.CodiceRaggruppamento.HasValue)
                {
                    var mgr = new RaggruppamentoCausaliOneriMgr(this.Database);

                    foreach (DataRow row in mgr.GetImporti(this.IdComune, this.CodiceIstanza, this.CodiceRaggruppamento.Value).Tables[0].Rows)
                    {
                        var idIstanzeOneri = Convert.ToInt32(row["id"]);
                        var importo = Convert.ToDecimal(row["prezzo"]);
                        decimal importoIstruttoria = 0;

                        if (row["prezzoistruttoria"] != DBNull.Value)
                            importoIstruttoria = Convert.ToDecimal(row["prezzoistruttoria"]);

                        var parametri = this.CreaParametriRateizzazione(idIstanzeOneri);

                        if (!this._rateizzazioniService.RateizzaOnere(this.Token, parametri, importo, importoIstruttoria))
                            return;
                    }
                }

                base.CloseCurrentPage();
            }
            catch (Exception ex)
            {
                this.MostraErrore("Attenzione, non è possibile effettuare la rateizzazione per il seguente motivo: " + ex.Message, ex);
            }
        }

        private ParametriRateizzazione CreaParametriRateizzazione(int idIstanzeOneri)
        {
            var tipoRateizzazione = Convert.ToInt32(this.ddlTipiRateizzazione.Value);
            var dataInizioRateizzazione = Convert.ToDateTime(this.txDataInizio.Value);
            var dataInizioInteressiLegali = (this.chkInteressiLegali.Item.Checked) ? Convert.ToDateTime(this.TxDataInizioIntLegali.Value) : (DateTime?)null;
            var speseRateizzazioni = String.IsNullOrEmpty(this.txSpeseRateizzazioni.Value) ? 0 : Convert.ToDecimal(this.txSpeseRateizzazioni.Value);
            var codiceIstanza = this.CodiceIstanza;

            return new ParametriRateizzazione(tipoRateizzazione, dataInizioRateizzazione, dataInizioInteressiLegali, speseRateizzazioni, codiceIstanza, idIstanzeOneri);
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }

        protected void txNumeroRate_ValueChanged(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(this.txNumeroRate.Value))
            {
                //Modifica il campo Ripartizione rate
                var numeroRate = Convert.ToInt32(this.txNumeroRate.Value);
                Rateizzazione calcoloRate = new Rateizzazione();
                calcoloRate.NumeroRate = numeroRate;

                var ripartizioneRate = "";
                for (int i = 0; i < numeroRate; i++)
                    ripartizioneRate += calcoloRate.CalcolaRipartizioneRata(i) + ";";

                ripartizioneRate = ripartizioneRate.Remove(ripartizioneRate.Length - 1);

                this.txRipartizioneRate.Value = ripartizioneRate;

                //Modifica il campo Frequenza rate
                if (string.IsNullOrEmpty(this.txGiorniScadenze.Value))
                {
                    string frequenzaRate = "";

                    for (int i = 0; i < numeroRate; i++)
                        frequenzaRate += "30;";

                    frequenzaRate = frequenzaRate.Remove(frequenzaRate.Length - 1);
                    this.txGiorniScadenze.Value = frequenzaRate;
                }
                else
                {
                    string[] listaFrequenzaRate = this.txGiorniScadenze.Value.Split(new Char[] { ';' });

                    if (numeroRate < listaFrequenzaRate.Length)
                    {
                        this.txGiorniScadenze.Value = "";

                        string frequenzaRate = "";

                        for (int iCount = 0; iCount < numeroRate; iCount++)
                            frequenzaRate += listaFrequenzaRate[iCount] + ";";

                        frequenzaRate = frequenzaRate.Remove(frequenzaRate.Length - 1);

                        this.txGiorniScadenze.Value = frequenzaRate;
                    }
                }

                //Modifica il campo Interessi
                this.txPercInteressi.Value = "";
            }
        }

        protected void cmdDerateizza_Click(object sender, EventArgs e)
        {
            string returnTo = this.Server.UrlEncode(this.Request.QueryString["returnTo"]);

            //Rateizzazione per causale
            if (this.CodiceOnere.HasValue)
            {
                IstanzeOneriMgr mgr = new IstanzeOneriMgr(this.Database, this.AuthenticationInfo);
                IstanzeOneri io = mgr.GetById(this.IdComune, this.CodiceOnere.ToString());

                var tipoCausale = Convert.ToInt32(io.FKIDTIPOCAUSALE);
                var url = $"~/Istanze/CalcoloOneri/Derateizzazione.aspx?Software={this.Software}&Token={this.AuthenticationInfo.Token}&CodiceIstanza={this.CodiceIstanza}&tipocausale={tipoCausale}&ReturnTo={returnTo}";
                this.Response.Redirect(url);

                return;
            }

            //Rateizzazione per raggruppamento
            if (this.CodiceRaggruppamento.HasValue)
            {
                var url = $"~/Istanze/CalcoloOneri/Derateizzazione.aspx?Software={this.Software}&Token={this.AuthenticationInfo.Token}&CodiceIstanza={this.CodiceIstanza}&codiceraggruppamento={this.CodiceRaggruppamento}&ReturnTo={returnTo}";
                this.Response.Redirect(url);

                return;
            }
        }

        protected void ddlTipiRateizzazione_ValueChanged(object sender, EventArgs e)
        {
            this.SetRateizzazione();
        }
    }
}
