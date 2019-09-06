using System;
using System.Linq;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using System.Collections.Generic;
using SIGePro.Net.Navigation;
using Init.Utils.Web.UI;
using System.Reflection;
using Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.Logic.GestioneOneri;
using log4net;

namespace Sigepro.net.Istanze.CalcoloOneri
{
    public partial class Istanze_CalcoloOneri_Rateizzazione : BasePage
    {
        #region Proprietà
        int m_TipoCausale;
        public int TipoCausale
        {
            get { return m_TipoCausale; }
            set { m_TipoCausale = value; }
        }
        string m_FlEntrataUscita;
        public string FlEntrataUscita
        {
            get { return m_FlEntrataUscita; }
            set { m_FlEntrataUscita = value; }
        }
        int m_NumeroRata;
        public int NumeroRata
        {
            get { return m_NumeroRata; }
            set { m_NumeroRata = value; }
        }
        string m_NumeroDocumento = null;
        public string NumeroDocumento
        {
            get { return m_NumeroDocumento; }
            set { m_NumeroDocumento = value; }
        }
        DateTime m_Data;
        public DateTime Data
        {
            get { return m_Data; }
            set { m_Data = value; }
        }


        private int m_CodiceRaggruppamento;
        protected int CodiceRaggruppamento
        {
            get { return m_CodiceRaggruppamento; }
        }

        private int m_CodiceOnere;
        protected int CodiceOnere
        {
            get { return m_CodiceOnere; }
            set { m_CodiceOnere = value; }
        }

        private int m_CodiceIstanza;
        protected int CodiceIstanza
        {
            get { return m_CodiceIstanza; }
        }
        #endregion

        ILog _log = LogManager.GetLogger(typeof(Istanze_CalcoloOneri_Rateizzazione));

        protected void Page_Load(object sender, EventArgs e)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            m_CodiceIstanza = string.IsNullOrEmpty(Request.QueryString["codiceistanza"]) ? int.MinValue : Convert.ToInt32(Request.QueryString["codiceistanza"]);
            m_CodiceOnere = string.IsNullOrEmpty(Request.QueryString["id"]) ? int.MinValue : Convert.ToInt32(Request.QueryString["id"]);
            m_CodiceRaggruppamento = string.IsNullOrEmpty(Request.QueryString["codiceraggruppamento"]) ? int.MinValue : Convert.ToInt32(Request.QueryString["codiceraggruppamento"]);

            if (!Page.IsPostBack)
            {
                SetEtichetta();
                BindComboTipiRateizzazioni();
                BindComboFrequenzaRate();
                BindComboScadenza();
                SetRateizzazione();

                IstanzeOneriMgr mgr = new IstanzeOneriMgr(Database);
                IstanzeOneri io = mgr.GetById(IdComune, CodiceOnere.ToString());
                cmdRateizza1.Visible = io.FlagOnereRateizzato == "0";
            }
        }

        #region Metodi usati per settare la pagina nel momento in cui viene invocata
        private void BindComboScadenza()
        {
            this.ddlComportamentoScadenza.Item.DataSource = (new OneriTipiRateizzazioneMgr(Database)).GetTipiScadenze();
            this.ddlComportamentoScadenza.Item.DataBind();
        }

        private void BindComboTipiRateizzazioni()
        {
            OneriTipiRateizzazioneMgr mgrotr = new OneriTipiRateizzazioneMgr(Database);
            OneriTipiRateizzazione otr = new OneriTipiRateizzazione();
            otr.Idcomune = IdComune;
            otr.Software = Software;

            List<OneriTipiRateizzazione> list = mgrotr.GetList(otr);
            this.ddlTipiRateizzazione.Item.DataSource = list;
            this.ddlTipiRateizzazione.Item.DataBind();

            //Leggi configurazione per l'utente dalla tabella CONFIGURAZIONEUTENTE
            if ((list != null) && (list.Count != 0))
            {
                ddlTipiRateizzazione.Value = LeggiConfUtente(list[0].Tiporateizzazione.ToString());
            }
        }

        private void BindComboFrequenzaRate()
        {
            this.ddlFrequenzaRateAmmortamentoFrancese.Item.DataSource = new OneriTipiRateizzazioneMgr(Database).GetFrequenzaRate();
            this.ddlFrequenzaRateAmmortamentoFrancese.Item.DataBind();
        }

        protected void SetEtichetta()
        {
            //Rateizzazione per causale
            if (CodiceRaggruppamento == int.MinValue)
            {
                this.lblTitolo1.Visible = false;
                this.lblRaggruppamento.Visible = false;

                IstanzeOneriMgr mgrio = new IstanzeOneriMgr(Database);
                IstanzeOneri io = mgrio.GetById(IdComune, CodiceOnere.ToString());
                this.lblImporto.Text = io.PREZZO.ToString();

                if ((io.PREZZOISTRUTTORIA == 0) || (io.PREZZOISTRUTTORIA.GetValueOrDefault(double.MinValue) == double.MinValue))
                {
                    this.lblImportoIstrEtichetta.Visible = false;
                    this.lblImportoIstr.Visible = false;
                }
                else
                    this.lblImportoIstr.Text = io.PREZZOISTRUTTORIA.ToString();

                TipiCausaliOneriMgr mgrtco = new TipiCausaliOneriMgr(Database);
                TipiCausaliOneri tco = mgrtco.GetById(IdComune, Convert.ToInt32(io.FKIDTIPOCAUSALE));
                this.lblCausale.Text = tco.CoDescrizione;
            }
            //Rateizzazione per raggruppamento
            if (CodiceOnere == int.MinValue)
            {
                this.lblCausale.Visible = false;

                RaggruppamentoCausaliOneriMgr mgr = new RaggruppamentoCausaliOneriMgr(Database);
                RaggruppamentoCausaliOneri rco = mgr.GetById(IdComune, CodiceRaggruppamento);

                this.lblRaggruppamento.Text = rco.RcoDescr;
                this.lblImporto.Text = mgr.GetImportoTotale(IdComune, CodiceIstanza, CodiceRaggruppamento).ToString();

                double dImportoTotaleIstruttoria = mgr.GetImportoTotale(IdComune, CodiceIstanza, CodiceRaggruppamento, true);
                if (dImportoTotaleIstruttoria == 0.0)
                {
                    this.lblImportoIstrEtichetta.Visible = false;
                    this.lblImportoIstr.Visible = false;
                }
                else
                    this.lblImportoIstr.Text = dImportoTotaleIstruttoria.ToString();
            }
        }

        //Questo metodo viene usato anche in seguito alla modifica del valore della combo box adibita a modifcare il tipo di rateizzazione
        protected void SetRateizzazione()
        {
            if (!string.IsNullOrEmpty(ddlTipiRateizzazione.Value))
            {
                txGiorniScadenze.Visible = true;
                ddlFrequenzaRateAmmortamentoFrancese.Visible = false;

                var mgrotr = new OneriTipiRateizzazioneMgr(Database);
                var otr = mgrotr.GetById(IdComune, Convert.ToInt32(ddlTipiRateizzazione.Value));

                var dataInizioRateizzazione = (OneriTipiRateizzazioneMgr.DataInizioRateizzazione)otr.Determdatainiziorate.GetValueOrDefault(0);

                var dataInizioRate = mgrotr.GetDataInizioRate(IdComune, CodiceIstanza, dataInizioRateizzazione, otr);

                txNumeroRate.Value = otr.Nrorate;
                txRipartizioneRate.Value = otr.Ripartizionerate;
                txGiorniScadenze.Value = otr.Frequenzarate;
                ddlComportamentoScadenza.Value = otr.Scadenzarate.ToString();
                txPercInteressi.Value = otr.Interessirate;
                txDataInizio.Value = dataInizioRate;
                chkInteressiLegali.Item.Checked = otr.FlagInteressiLegali == 1 ? true : false;
                ddlInteressiLegali.SelectedValue = (otr.TipoAnatocismo.GetValueOrDefault(int.MinValue) == int.MinValue || otr.TipoAnatocismo == 0) ? "0" : otr.TipoAnatocismo.ToString();
                TxDataInizioIntLegali.Item.Text = txDataInizio.Value;

                ddlInteressiLegali.Visible = chkInteressiLegali.Item.Checked;
                TxDataInizioIntLegali.Visible = chkInteressiLegali.Item.Checked;
                txPercInteressi.Visible = !chkInteressiLegali.Item.Checked;
                chkInteressiLegali.Visible = true;

                if (otr.TipologiaRateizzazione == OneriTipiRateizzazioneMgr.TipoRateizzazione.AMMORTAMENTO_FRANCESE.ToString())
                {
                    txGiorniScadenze.Visible = false;
                    ddlFrequenzaRateAmmortamentoFrancese.Visible = true;
                    ddlFrequenzaRateAmmortamentoFrancese.Value = otr.Frequenzarate;
                    chkInteressiLegali.Item.Checked = false;
                    chkInteressiLegali.Visible = false;
                }

            }
        }
        #endregion

        #region Metodi usati per verificare la validità dei valori inseriti
        private bool VerificaValiditaCampi()
        {
            if (!VerificaCampoVuoto("txNumeroRate"))
                return false;
            if (!VerificaCampoVuoto("txRipartizioneRate"))
                return false;
            else if (!VerificaValiditaCampo("txRipartizioneRate"))
                return false;
            if (!VerificaCampoVuoto("txDataInizio"))
                return false;
            if (!VerificaCampoVuoto("txGiorniScadenze"))
                return false;
            else if (!VerificaValiditaCampo("txGiorniScadenze"))
                return false;

            return true;
        }

        private bool VerificaCampoVuoto(string sId)
        {
            WebControl ctrl = (WebControl)this.UpdatePanel1.FindControl(sId);
            if (string.IsNullOrEmpty(GetValue(ctrl)))
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", null);
                return false;
            }
            return true;
        }

        private bool VerificaValiditaCampo(string sId)
        {
            WebControl ctrl = (WebControl)this.UpdatePanel1.FindControl(sId);
            string[] aElem = GetValue(ctrl).Split(';');
            foreach (string elem in aElem)
            {
                try
                {
                    Convert.ToDouble(elem);
                }
                catch (Exception)
                {
                    MostraErrore("Attenzione, uno dei campi non contiene un valore valido.", null);
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
            string nomeParametro = string.Empty;
            if (CodiceRaggruppamento == int.MinValue)
                nomeParametro = "ROInt" + (new IstanzeMgr(Database).GetById(IdComune, CodiceIstanza).CODICEINTERVENTOPROC) + "#Caus" + new IstanzeOneriMgr(Database).GetById(IdComune, CodiceOnere.ToString()).FKIDTIPOCAUSALE;
            else
                nomeParametro = "ROInt" + (new IstanzeMgr(Database).GetById(IdComune, CodiceIstanza).CODICEINTERVENTOPROC) + "#Rag" + CodiceRaggruppamento;

            ConfigurazioneUtenteMgr confUtMgr = new ConfigurazioneUtenteMgr(Database);
            confUtMgr.SetValoreParametro(IdComune, AuthenticationInfo.CodiceResponsabile.GetValueOrDefault(int.MinValue), nomeParametro, valore);
        }

        private string LeggiConfUtente(string valoreDefault)
        {
            string nomeParametro = string.Empty;
            if (CodiceRaggruppamento == int.MinValue)
                nomeParametro = "ROInt" + (new IstanzeMgr(Database).GetById(IdComune, CodiceIstanza).CODICEINTERVENTOPROC) + "#Caus" + new IstanzeOneriMgr(Database).GetById(IdComune, CodiceOnere.ToString()).FKIDTIPOCAUSALE;
            else
                nomeParametro = "ROInt" + (new IstanzeMgr(Database).GetById(IdComune, CodiceIstanza).CODICEINTERVENTOPROC) + "#Rag" + CodiceRaggruppamento;

            ConfigurazioneUtenteMgr confUtMgr = new ConfigurazioneUtenteMgr(Database);
            return confUtMgr.GetValoreParametro(IdComune, AuthenticationInfo.CodiceResponsabile.GetValueOrDefault(int.MinValue), nomeParametro, valoreDefault);
        }

        protected void cmdRateizza_Click(object sender, EventArgs e)
        {
            //Verifico validità campi
            if (!VerificaValiditaCampi())
                return;

            //Scrivi confiurazione nella tabella CONFIGURAZIONEUTENTE
            ScriviConfUtente(ddlTipiRateizzazione.Value);

            double dImporto = 0;
            double dImportoIstruttoria = 0;
            //Rateizzazione per causale
            if (CodiceRaggruppamento == int.MinValue)
            {
                dImporto = Convert.ToDouble(this.lblImporto.Text);
                if (!string.IsNullOrEmpty(this.lblImportoIstr.Text))
                    dImportoIstruttoria = Convert.ToDouble(this.lblImportoIstr.Text);

                IstanzeOneriMgr mgr = new IstanzeOneriMgr(Database);
                IstanzeOneri io = mgr.GetById(IdComune, CodiceOnere.ToString());

                //Verifico che la l'onere non sia stato già pagato
                if (io.DATAPAGAMENTO.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue)
                    base.CloseCurrentPage();

                TipoCausale = Convert.ToInt32(io.FKIDTIPOCAUSALE);
                FlEntrataUscita = io.FLENTRATAUSCITA;
                Data = io.DATA.GetValueOrDefault(DateTime.MinValue);
                NumeroDocumento = io.NR_DOCUMENTO;

                if (!CalcoloRate(dImporto, dImportoIstruttoria))
                    return;
            }
            //Rateizzazione per raggruppamento
            if (CodiceOnere == int.MinValue)
            {
                RaggruppamentoCausaliOneriMgr mgr = new RaggruppamentoCausaliOneriMgr(Database);
                foreach (DataRow row in mgr.GetImporti(IdComune, CodiceIstanza, CodiceRaggruppamento).Tables[0].Rows)
                {
                    TipoCausale = Convert.ToInt32(row["fkidtipocausale"]);
                    FlEntrataUscita = Convert.ToString(row["flentratauscita"]);
                    Data = Convert.ToDateTime(row["data"]);
                    CodiceOnere = Convert.ToInt32(row["id"]);
                    dImporto = Convert.ToDouble(row["prezzo"]);
                    if (row["nr_documento"] != DBNull.Value)
                        NumeroDocumento = row["nr_documento"].ToString();
                    if (row["prezzoistruttoria"] != DBNull.Value)
                        dImportoIstruttoria = Convert.ToDouble(row["prezzoistruttoria"]);
                    if (!CalcoloRate(dImporto, dImportoIstruttoria))
                        return;
                }
            }

            base.CloseCurrentPage();
        }

        private bool CalcoloRate(double dImporto, double dImportoIstruttoria)
        {
            try
            {
                var tipoRateizzazione = Convert.ToInt32(ddlTipiRateizzazione.Value);
                var data = Convert.ToDateTime(this.txDataInizio.Value);
                var dataInizio = (chkInteressiLegali.Item.Checked) ? Convert.ToDateTime(this.TxDataInizioIntLegali.Value) : (DateTime?)null;
                var importo = Convert.ToDecimal(dImporto);
                var importoIstruttoria = Convert.ToDecimal(dImportoIstruttoria);
                var speseRateizzazioni = String.IsNullOrEmpty(txSpeseRateizzazioni.Value) ? 0 : Convert.ToDecimal(txSpeseRateizzazioni.Value);

                var svc = new RateizzazioniService(Token);

                var rate = svc.Rateizza(tipoRateizzazione, data, dataInizio, importo, speseRateizzazioni);
                var rateIstruttoria = svc.Rateizza(tipoRateizzazione, data, dataInizio, importoIstruttoria, speseRateizzazioni);

                List<IstanzeOneri> listRate = new List<IstanzeOneri>();

                int iCount = 0;
                //int iNumRata = 0;
                foreach (var elem in rate)
                {
                    var istOneri = new IstanzeOneri
                    {
                        //Calcolo l'importo della singola rata
                        PREZZO = Convert.ToDouble(elem.Prezzo),
                        //Calcolo l'importo istruttoria della singola rata
                        PREZZOISTRUTTORIA = Convert.ToDouble(rateIstruttoria.ElementAt(iCount).Prezzo),
                        //Calcolo l'importo della scadenza della singola rata
                        DATASCADENZA = elem.DataScadenza,
                        //Setto il codice istanze
                        CODICEISTANZA = CodiceIstanza.ToString(),
                        //Setto il tipo causale
                        FKIDTIPOCAUSALE = TipoCausale.ToString(),
                        //Setto l'IdComune
                        IDCOMUNE = IdComune,
                        //Setto il numero documento
                        NR_DOCUMENTO = NumeroDocumento == null ? null : NumeroDocumento,
                        FLENTRATAUSCITA = FlEntrataUscita,
                        DATA = Data,
                        FlagOnereRateizzato = "1",
                        ImportoInteressi = elem.Interesse
                    };

                    iCount++;

                    listRate.Add(istOneri);
                }

                UpdateIstanzeOneri(listRate);

                return true;
            }
            catch (Exception ex)
            {
                MostraErrore("Attenzione, non è possibile effettuare la rateizzazione per il seguente motivo: " + ex.Message, ex);

                return false;
            }

            /*
            try
            {
                RateizzazioniService rateizzazioniService = new RateizzazioniService();
                rateizzazioniService.Url = ParametriConfigurazione.Get.JavaServiceUrl;

                RateizzazioniRequest rateizzazioniRequest = new RateizzazioniRequest();
                rateizzazioniRequest.codiceOneriTipiRateizzazione = Convert.ToInt32(ddlTipiRateizzazione.Value);
                rateizzazioniRequest.data = Convert.ToDateTime(this.txDataInizio.Value);
                if (chkInteressiLegali.Item.Checked)
                    rateizzazioniRequest.dataInizio = Convert.ToDateTime(this.TxDataInizioIntLegali.Value);
                rateizzazioniRequest.importo = Convert.ToDecimal(dImporto);
                rateizzazioniRequest.token = Token;
                ImportoRateizzatoXML[] listImporto = rateizzazioniService.Rateizzazioni(rateizzazioniRequest);
                rateizzazioniRequest.importo = Convert.ToDecimal(dImportoIstruttoria);
                ImportoRateizzatoXML[] listImportoIstr = rateizzazioniService.Rateizzazioni(rateizzazioniRequest);

                List<IstanzeOneri> listRate = new List<IstanzeOneri>();

                int iCount = 0;
                //int iNumRata = 0;
                foreach (ImportoRateizzatoXML elem in listImporto)
                {
                    IstanzeOneri istOneri = new IstanzeOneri();

                    //Calcolo l'importo della singola rata
                    istOneri.PREZZO = Convert.ToDouble(elem.importoRateizzato);
                    //Calcolo l'importo istruttoria della singola rata
                    istOneri.PREZZOISTRUTTORIA = Convert.ToDouble(listImportoIstr[iCount].importoRateizzato);
                    //Calcolo l'importo della scadenza della singola rata
                    istOneri.DATASCADENZA = elem.scadenza;
                    //Setto il codice istanze
                    istOneri.CODICEISTANZA = CodiceIstanza.ToString();
                    //Setto il tipo causale
                    istOneri.FKIDTIPOCAUSALE = TipoCausale.ToString();
                    //Setto l'IdComune
                    istOneri.IDCOMUNE = IdComune;
                    //Setto il numero documento
                    istOneri.NR_DOCUMENTO = NumeroDocumento == null ? null : NumeroDocumento;
                    istOneri.FLENTRATAUSCITA = FlEntrataUscita;
                    istOneri.DATA = Data;
                    iCount++;

                    listRate.Add(istOneri);
                }

                UpdateIstanzeOneri(listRate);
            }
            catch (Exception ex)
            {
                MostraErrore("Attenzione, non è possibile effettuare la rateizzazione per il seguente motivo: " + ex.Message, ex);
                return false;
            }

            return true;*/
        }


        private void UpdateIstanzeOneri(IEnumerable<IstanzeOneri> listRate)
        {
            try
            {
                //Database.BeginTransaction();
                //Verifico se l'onere era collegato ad un record nella tabella ISTANZECALCOLOCANONI_O (ISTANZEONERI_CANONI  non viene gestita)
                int fk_idtestataIstCal_Canoni = new IstanzeCalcoloCanoniOMgr(Database).VerificaIstanzeCalcoloCanoniCollegatiFromOnere(IdComune, CodiceOnere);

                IstanzeOneriMgr mgrio = new IstanzeOneriMgr(Database);
                //Cancello l'onere che viene rateizzato ed eventuali record collegati
                IstanzeOneri ioDel = mgrio.GetById(IdComune, CodiceOnere.ToString());
                mgrio.Delete(ioDel);

                //Verifico se l'onere era collegato ad un record nella tabella ISTANZECALCOLOCANONI_O (ISTANZEONERI_CANONI non viene gestita)
                var svc = new OneriService(Token, IdComune);
                listRate = svc.Inserisci(listRate);

                if (fk_idtestataIstCal_Canoni != int.MinValue)
                {
                    //Inserisco le rate in ISTANZEONERI ed ISTANZECALCOLOCANONI_O
                    new IstanzeCalcoloCanoniOMgr(Database).InserisciOneri(IdComune, fk_idtestataIstCal_Canoni, listRate);
                }

                //Numerazione delle rate per data scadenza crescente
                //Lista delle rate da rinumerare
                DataSet ds = null;
                ds = mgrio.GetListaOneri(IdComune, CodiceIstanza, TipoCausale);

                //Lista delle rate pagate
                IstanzeOneri ioNumeroRata = new IstanzeOneri();
                ioNumeroRata.IDCOMUNE = IdComune;
                ioNumeroRata.CODICEISTANZA = CodiceIstanza.ToString();
                ioNumeroRata.FKIDTIPOCAUSALE = TipoCausale.ToString();
                ioNumeroRata.OthersWhereClause.Add("DATAPAGAMENTO is not null");
                List<IstanzeOneri> list = mgrio.GetList(ioNumeroRata);
                int iNumeroRata = 1;
                bool bNumeroRata = true;
                foreach (DataRow dr in ds.Tables[0].Rows)
                {
                    for (; ; )
                    {
                        foreach (IstanzeOneri elem in list)
                        {
                            if (elem.NUMERORATA == iNumeroRata.ToString())
                            {
                                bNumeroRata = false;
                                break;
                            }
                        }

                        if (bNumeroRata)
                            break;
                        else
                        {
                            iNumeroRata++;
                            bNumeroRata = true;
                        }
                    }

                    IstanzeOneri ioUpd = mgrio.GetById(IdComune, Convert.ToString(dr["id"]));
                    ioUpd.NUMERORATA = iNumeroRata.ToString();
                    iNumeroRata++;
                    mgrio.Update(ioUpd);
                }

                //Database.CommitTransaction();

            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Si è verificato un errore durante la rateizzazione: {0}", ex.ToString());

                throw;
            }
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
                Rateizzazione calcoloRate = new Rateizzazione();
                calcoloRate.NumeroRate = Convert.ToInt32(this.txNumeroRate.Value);

                string sRipartizioneRate = "";
                for (int iCount = 0; iCount < Convert.ToInt32(txNumeroRate.Value); iCount++)
                    sRipartizioneRate += calcoloRate.CalcolaRipartizioneRata(iCount) + ";";

                sRipartizioneRate = sRipartizioneRate.Remove(sRipartizioneRate.Length - 1);

                this.txRipartizioneRate.Value = sRipartizioneRate;

                //Modifica il campo Frequenza rate
                if (string.IsNullOrEmpty(this.txGiorniScadenze.Value))
                {
                    string sFrequenzaRate = "";
                    for (int iCount = 0; iCount < Convert.ToInt32(txNumeroRate.Value); iCount++)
                        sFrequenzaRate += "30;";

                    sFrequenzaRate = sFrequenzaRate.Remove(sFrequenzaRate.Length - 1);
                    this.txGiorniScadenze.Value = sFrequenzaRate;
                }
                else
                {
                    string[] aFrequenzaRate = txGiorniScadenze.Value.Split(new Char[] { ';' });
                    if (Convert.ToInt32(txNumeroRate.Value) < aFrequenzaRate.Length)
                    {
                        txGiorniScadenze.Value = "";
                        string sFrequenzaRate = "";
                        for (int iCount = 0; iCount < Convert.ToInt32(txNumeroRate.Value); iCount++)
                            sFrequenzaRate += aFrequenzaRate[iCount] + ";";

                        sFrequenzaRate = sFrequenzaRate.Remove(sFrequenzaRate.Length - 1);
                        this.txGiorniScadenze.Value = sFrequenzaRate;
                    }
                }

                //Modifica il campo Interessi
                this.txPercInteressi.Value = "";
            }
        }

        protected void cmdDerateizza_Click(object sender, EventArgs e)
        {
            string returnTo = Server.UrlEncode(Request.QueryString["returnTo"]);
            string url;
            //Rateizzazione per causale
            if (CodiceRaggruppamento == int.MinValue)
            {
                IstanzeOneriMgr mgr = new IstanzeOneriMgr(Database);
                IstanzeOneri io = mgr.GetById(IdComune, CodiceOnere.ToString());
                TipoCausale = Convert.ToInt32(io.FKIDTIPOCAUSALE);
                url = "~/Istanze/CalcoloOneri/Derateizzazione.aspx?Software={0}&Token={1}&CodiceIstanza={2}&tipocausale={3}&ReturnTo={4}";
                Response.Redirect(String.Format(url, Software, AuthenticationInfo.Token, CodiceIstanza, TipoCausale, returnTo));
            }
            //Rateizzazione per raggruppamento
            if (CodiceOnere == int.MinValue)
            {
                url = "~/Istanze/CalcoloOneri/Derateizzazione.aspx?Software={0}&Token={1}&CodiceIstanza={2}&codiceraggruppamento={3}&ReturnTo={4}";
                Response.Redirect(String.Format(url, Software, AuthenticationInfo.Token, CodiceIstanza, CodiceRaggruppamento, returnTo));
            }
        }

        protected void ddlTipiRateizzazione_ValueChanged(object sender, EventArgs e)
        {
            SetRateizzazione();
        }
    }
}
