using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneOneri;
using System;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using static Init.SIGePro.Manager.IstanzeMgr;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
    public partial class CCICalcoliTotDettaglio : PaginaTotaleOneriBase
    {
        public override string Software => this.Istanza.Software;

        private int CodiceIstanza
        {
            get
            {
                var codiceIstanza = this.Request.QueryString["CodiceIstanza"];

                if (String.IsNullOrEmpty(codiceIstanza))
                    throw new ArgumentException("Codice istanza non passato");

                return int.Parse(codiceIstanza);
            }
        }

        private DatiMinimiIstanza m_istanza = null;
        private DatiMinimiIstanza Istanza
        {
            get
            {
                if (this.m_istanza == null)
                    this.m_istanza = new IstanzeMgr(this.AuthenticationInfo.CreateDatabase()).GetDatiMinimi(this.IdComune, this.CodiceIstanza);

                return this.m_istanza;
            }
        }

        private int IdCalcoloTot
        {
            get
            {
                if (String.IsNullOrEmpty(this.Request.QueryString["IdCalcoloTot"]))
                {
                    throw new ArgumentException("IdCalcoloTot non passato");
                }

                return Convert.ToInt32(this.Request.QueryString["IdCalcoloTot"]);
            }
        }

        #region visibilità delle righe della tabella del contributo
        public bool MostraRigaContributoProgetto
        {
            get { var o = this.ViewState["MostraRigaContributoProgetto"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraRigaContributoProgetto"] = value; }
        }

        public bool MostraRigaContributoAttuale
        {
            get { var o = this.ViewState["MostraRigaContributoAttuale"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraRigaContributoAttuale"] = value; }
        }

        public bool MostraTabellaContributo
        {
            get { return this.MostraRigaContributoProgetto || this.MostraRigaContributoAttuale; }
        }
        #endregion



        protected void Page_Load(object sender, EventArgs e)
        {
            this.ImpostaScriptEliminazione(this.cmdEliminaCalcolo);

            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            var cls = new CCICalcoloTotMgr(this.Database).GetById(this.IdComune, this.IdCalcoloTot);

            new CCICalcoloTotMgr(this.Database).IntegraForeignKey(cls);

            this.lblId.Text = cls.Id.ToString();
            this.lblData.Text = cls.Data.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy");
            this.lblListino.Text = new CCValiditaCoefficientiMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, cls.FkCcvcId.Value).Descrizione;
            this.lblTipoCalcolo.Text = new CCBaseTipoCalcoloMgr(this.Database).GetById(cls.FkBcctcId).Tipocalcolo;
            this.lblTipoIntervento.Text = new OCCBaseTipoInterventoMgr(this.Database).GetById(cls.FkOccbtiId).Intervento;
            this.lblDestinazione.Text = new OCCBaseDestinazioniMgr(this.Database).GetById(cls.FkOccbdeId).Destinazione;

            this.lblTipoInterventoDettaglio.Text = cls.FkInterventoDettaglioId.HasValue
                                                        ? new CCTipoInterventoMgr(this.Database).GetById(cls.Idcomune, cls.FkInterventoDettaglioId.Value).Intervento
                                                        : "Non definito";
            this.lblDestinazioneDettaglio.Text = cls.FkDestinazioneDettaglioId.HasValue
                                                        ? new CCDestinazioniMgr(this.Database).GetById(cls.Idcomune, cls.FkDestinazioneDettaglioId.Value).Destinazione
                                                        : "Non definita";

            this.txtEditDescrizione.Text = cls.Descrizione;

            this.MostraRigaContributoAttuale =
            this.MostraRigaContributoProgetto = false;

            var totaleContributo = 0.0m;
            var contrAttuale = 0.0m;
            var contrProgetto = 0.0m;

            if (cls.StatoAttuale != null)
            {
                this.MostraRigaContributoAttuale = true;
                this.txtCostoEdificioAttuale.ValoreDecimal = cls.StatoAttuale.CostocEdificio;
                this.txtCoefficenteAttuale.ValoreDecimal = cls.StatoAttuale.Coefficiente;

                if (!cls.StatoAttuale.Riduzioneperc.HasValue)
                {
                    cls.StatoAttuale.Riduzioneperc = 0.0m;
                    new CCICalcoloTContributoMgr(this.Database).Update(cls.StatoAttuale);
                }

                var quotaNoRiduz = cls.StatoAttuale.GetQuotaSenzaRiduzioni();
                var quotaConRiduz = cls.StatoAttuale.GetQuotaConRiduzioni();

                this.lblQuotaAttuale.Text = quotaNoRiduz.ToString("N2");
                this.txtQuotaContributoAttuale.ValoreDecimal = quotaConRiduz;

                contrAttuale = quotaConRiduz;

                this.cmdDettagliCostoEdificioAttuale.Visible = cls.StatoAttuale.FkCcicId.HasValue;

                if (cls.StatoAttuale.FkCcicId.HasValue)
                    this.cmdDettagliCostoEdificioAttuale.CommandArgument = cls.StatoAttuale.FkCcicId.ToString();

                this.cmdDettagliContributoAttuale.CommandArgument = cls.Id.ToString() + "$A";

                var haRiduzioni = new CcICalcoloTContributoRiduzMgr(this.Database).GetListByIdTestataContributoUseForeign(this.IdComune, cls.StatoAttuale.Id.Value).Count > 0;

                this.txtVariazioneAttuale.ReadOnly = haRiduzioni;
                this.txtVariazioneAttuale.ValoreDecimal = cls.StatoAttuale.Riduzioneperc;

                this.hlpVariazioneAttuale.Text = this.GeneraTestoRiduzioni(cls.StatoAttuale);
            }

            if (cls.StatoDiProgetto != null)
            {
                this.MostraRigaContributoProgetto = true;
                this.txtCostoEdificioProgetto.ValoreDecimal = cls.StatoDiProgetto.CostocEdificio;
                this.txtCoefficenteProgetto.ValoreDecimal = cls.StatoDiProgetto.Coefficiente;

                if (!cls.StatoDiProgetto.Riduzioneperc.HasValue)
                {
                    cls.StatoDiProgetto.Riduzioneperc = 0.0m;
                    new CCICalcoloTContributoMgr(this.Database).Update(cls.StatoDiProgetto);
                }

                var quotaNoRiduz = cls.StatoDiProgetto.GetQuotaSenzaRiduzioni();
                var quotaConRiduz = cls.StatoDiProgetto.GetQuotaConRiduzioni();

                this.lblQuotaProgetto.Text = quotaNoRiduz.ToString("N2");
                this.txtQuotaContributoProgetto.ValoreDecimal = quotaConRiduz;

                contrProgetto = quotaConRiduz;

                this.cmdDettagliCostoEdificioProgetto.Visible = cls.StatoDiProgetto.FkCcicId.HasValue;

                if (cls.StatoDiProgetto.FkCcicId.HasValue)
                    this.cmdDettagliCostoEdificioProgetto.CommandArgument = cls.StatoDiProgetto.FkCcicId.ToString();

                this.cmdDettagliContributoProgetto.CommandArgument = cls.Id.ToString() + "$P";

                this.txtVariazioneProgetto.ValoreDecimal = cls.StatoDiProgetto.Riduzioneperc;

                var haRiduzioni = new CcICalcoloTContributoRiduzMgr(this.Database).GetListByIdTestataContributoUseForeign(this.IdComune, cls.StatoDiProgetto.Id.Value).Count > 0;
                this.txtVariazioneProgetto.ReadOnly = haRiduzioni;

                this.hlpVariazioneProgetto.Text = this.GeneraTestoRiduzioni(cls.StatoDiProgetto);
            }

            totaleContributo = contrProgetto - contrAttuale;

            this.txtQuotaContributoTotale.ValoreDecimal = totaleContributo;
        }

        private string GeneraTestoRiduzioni(CCICalcoloTContributo cCICalcoloTContributo)
        {
            var riduzioni = new CcICalcoloTContributoRiduzMgr(this.Database).GetListByIdTestataContributoUseForeign(this.IdComune, cCICalcoloTContributo.Id.Value);

            if (riduzioni.Count == 0) return cCICalcoloTContributo.Noteriduzione;   // TODO: Verificare che non sia stato immesso un valore manualmente in tal caso ritornare le note del calcolo

            var sb = new StringBuilder();

            sb.Append("<table width='100%'>");

            var totale = 0.0m;

            foreach (var r in riduzioni)
            {
                sb.Append("<tr style='color:#000'><td>");
                sb.Append(r.CausaleRiduzione.Descrizione);
                sb.Append("</td><td style='text-align:right;'>");
                sb.Append(r.Riduzioneperc.GetValueOrDefault(0.0m).ToString("N2") + "%");
                sb.Append("</td></tr>");

                if (!String.IsNullOrEmpty(r.Note))
                {
                    sb.Append("<tr><td>&nbsp;&nbsp;<i>");
                    sb.Append(r.Note);
                    sb.Append("</i></td><td>&nbsp;</td></tr>");
                }

                totale += r.Riduzioneperc.GetValueOrDefault(0.0m);

            }

            sb.Append("<tr style='color:#000'><td>&nbsp;</td><td style='text-align:right;'>-------------</td></tr>");

            sb.Append("<tr style='color:#000'><td>Totale</td><td style='text-align:right;'>");
            sb.Append(totale.ToString("N2"));
            sb.Append("%");
            sb.Append("</td></tr>");

            sb.Append("</table>");

            return sb.ToString();

        }

        protected void cmdAggiornaDescrizione_Click(object sender, EventArgs e)
        {
            var mgr = new CCICalcoloTotMgr(this.Database);
            var cls = mgr.GetById(this.IdComune, this.IdCalcoloTot);

            cls.Descrizione = this.txtEditDescrizione.Text;

            try
            {
                mgr.Update(cls);
            }
            catch (Exception ex)
            {
                this.MostraErrore("Errore durante l'aggiornamento: " + ex.Message, ex);
            }
        }


        protected void cmdSalvaContributo_Click(object sender, EventArgs e)
        {
            var id = this.IdCalcoloTot;
            var mgrTot = new CCICalcoloTotMgr(this.Database);
            var mgrICalc = new CCICalcoloTContributoMgr(this.Database);

            var cTot = mgrTot.GetById(this.AuthenticationInfo.IdComune, id);
            mgrTot.IntegraForeignKey(cTot);

            if (cTot.StatoDiProgetto != null)
            {
                cTot.StatoDiProgetto.Coefficiente = this.txtCoefficenteProgetto.ValoreDecimal.GetValueOrDefault(0.0m);
                cTot.StatoDiProgetto.CostocEdificio = this.txtCostoEdificioProgetto.ValoreDecimal.GetValueOrDefault(0.0m);
                cTot.StatoDiProgetto.Riduzioneperc = this.txtVariazioneProgetto.ValoreDecimal.GetValueOrDefault(0.0m);
                cTot.StatoDiProgetto = mgrICalc.Update(cTot.StatoDiProgetto);
            }

            if (cTot.StatoAttuale != null)
            {
                cTot.StatoAttuale.Coefficiente = this.txtCoefficenteAttuale.ValoreDecimal.GetValueOrDefault(0.0m);
                cTot.StatoAttuale.CostocEdificio = this.txtCostoEdificioAttuale.ValoreDecimal.GetValueOrDefault(0.0m);
                cTot.StatoAttuale.Riduzioneperc = this.txtVariazioneAttuale.ValoreDecimal.GetValueOrDefault(0.0m);
                cTot.StatoAttuale = mgrICalc.Update(cTot.StatoAttuale);
            }

            // cTot = mgrTot.GetById(this.AuthenticationInfo.IdComune, id);

            this.DataBind();
        }
        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            var url = $"~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={this.AuthenticationInfo.Token}&CodiceIstanza={this.CodiceIstanza}";

            this.Response.Redirect(url);
        }

        protected void ApriDettagli(object sender, EventArgs e)
        {
            var lbSender = (ImageButton)sender;

            var idCalcolo = int.Parse(lbSender.CommandArgument);

            var cfg = new CCConfigurazioneMgr(this.Database).GetById(this.IdComune, this.Istanza.Software);

            var fmtUrl = "";

            // Utilizza l'immissione dei dati nel modello?
            if (cfg.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
                fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella1.aspx?Token={0}&IdCalcolo={1}";
            else
                fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliDettaglio.aspx?Token={0}&IdCalcolo={1}";

            this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, idCalcolo), true);
        }

        protected void ApriDettagliContributo(object sender, EventArgs e)
        {
            var lbSender = (ImageButton)sender;

            var parts = lbSender.CommandArgument.Split('$');

            var idCalcoloTot = int.Parse(parts[0]);
            var tipoDettaglio = parts[1];

            var fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoloCoeffPercentuale.aspx?Token={0}&IdCalcoloTot={1}&TipoDettaglio={2}";

            this.Response.Redirect(String.Format(fmtUrl, this.AuthenticationInfo.Token, idCalcoloTot, tipoDettaglio), true);
        }

        protected void cmdRiportaValore_Click(object sender, EventArgs e)
        {
            var istanzeOneriMgr = new IstanzeOneriMgr(this.Database, this.AuthenticationInfo);
            var codiceCausale = new CCConfigurazioneMgr(this.Database).GetById(this.IdComune, this.Software).FkCoId;
            var oneriEsistenti = this.GetOneriFromIstanzaCausale(this.CodiceIstanza, codiceCausale.Value);
            var importoOnere = this.txtQuotaContributoTotale.ValoreDecimal;

            //Verifico se è stato trovato un onere nell'istanza con la stessa causale
            if (oneriEsistenti.Count == 1)
            {
                //E' stato trovato un onere 
                var onere = oneriEsistenti[0];

                var importo = ((decimal)onere.PREZZO.GetValueOrDefault(0.0d)) + importoOnere;
                var idComune = onere.IDCOMUNE;
                var idOnere = Convert.ToInt32(onere.ID);

                try
                {
                    istanzeOneriMgr.UpdateImporto(idComune, idOnere, importo.Value);
                }
                catch (Exception ex)
                {
                    this.MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
                }
            }
            else
            {
                //Non è stato trovato nessun onere o più di uno
                try
                {
                    var oneriService = new OneriService(this.AuthenticationInfo);

                    oneriService.Inserisci(this.CodiceIstanza, codiceCausale.Value, importoOnere.Value);
                }
                catch (Exception ex)
                {
                    this.MostraErrore(AmbitoErroreEnum.Inserimento, ex);
                }
            }

            this.MostraConfermaCopiaOneri();
            //ImpostaScriptCopia(cmdCopiaOneri, CodiceIstanza, codiceCausale.Value);
        }

        protected void cmdEliminaCalcolo_Click(object sender, EventArgs e)
        {
            var mgr = new CCICalcoloTotMgr(this.Database);
            var cls = mgr.GetById(this.AuthenticationInfo.IdComune, this.IdCalcoloTot);

            try
            {
                mgr.Delete(cls);
            }
            catch (Exception ex)
            {
                this.MostraErrore(ex);

                return;
            }

            this.cmdChiudiDettaglio_Click(sender, e);
        }

        protected void EditContributoProgetto(object sender, ImageClickEventArgs e)
        {
            var calcoloTot = new CCICalcoloTotMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, this.IdCalcoloTot);
            new CCICalcoloTotMgr(this.Database).IntegraForeignKey(calcoloTot);

            this.ModificaRiduzioniCtrl.TContributoId = calcoloTot.StatoDiProgetto.Id.Value;
            this.ModificaRiduzioniCtrl.DataBind();
        }

        protected void EditContributoAttuale(object sender, ImageClickEventArgs e)
        {
            var calcoloTot = new CCICalcoloTotMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, this.IdCalcoloTot);
            new CCICalcoloTotMgr(this.Database).IntegraForeignKey(calcoloTot);

            this.ModificaRiduzioniCtrl.TContributoId = calcoloTot.StatoAttuale.Id.Value;
            this.ModificaRiduzioniCtrl.DataBind();
        }

        protected void OnModificaRiduzioniRiuscita(object sender, EventArgs e)
        {
            this.DataBind();
        }

        protected void OnErroreSalvataggioRiduzioni(object sender, Exception e)
        {
            this.MostraErrore(e);
        }

        protected override void OnPreRender(EventArgs e)
        {
            var mgrConfigurazione = new CCConfigurazioneMgr(this.Database);
            var configurazione = mgrConfigurazione.GetById(this.AuthenticationInfo.IdComune, this.Istanza.Software);
            var idCausaleOnere = configurazione?.FkCoId;

            this.cmdCopiaOneri.Visible = idCausaleOnere.HasValue;

            if (idCausaleOnere.HasValue)
            {
                this.ImpostaScriptCopia(this.cmdCopiaOneri, this.CodiceIstanza, idCausaleOnere.Value);
            }

            base.OnPreRender(e);
        }
    }
}