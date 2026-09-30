using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
    public partial class OIModificaRiduzioni : BasePage
    {
        public override string Software
        {
            get
            {
                var ist = new IstanzeMgr(this.Database).GetById(this.IdComune, this.ContribT.Codiceistanza.Value);
                return ist.SOFTWARE;
            }
        }

        private int IdDestinazione
        {
            get
            {
                var idDestinazione = this.Request.QueryString["IdDestinazione"];
                if (String.IsNullOrEmpty(idDestinazione))
                    throw new ArgumentException("Parametro IdDestinazione non impostato");

                return Convert.ToInt32(idDestinazione);
            }
        }


        private OICalcoloContribT m_contribT = null;
        private OICalcoloContribT ContribT
        {
            get
            {
                if (this.m_contribT == null)
                {
                    var id = Convert.ToInt32(this.Request.QueryString["IdContribT"]);
                    this.m_contribT = new OICalcoloContribTMgr(this.Database).GetById(this.IdComune, id);
                }

                return this.m_contribT;
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            this.rptTipiCausali.DataSource = new OCausaliRiduzioniTMgr(this.Database).GetList(this.IdComune, this.Software);
            this.rptTipiCausali.DataBind();
        }

        protected void rptTipiCausali_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var tipoCausale = (OCausaliRiduzioniT)e.Item.DataItem;
                var modificariduzioniCtrl = (OIModificariduzioniCtrl)e.Item.FindControl("ModificariduzioniCtrl");

                modificariduzioniCtrl.DataSource = new OICalcoloContribRRiduzMgr(this.Database).GetRiduzioniPerTipoCausale(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), this.IdDestinazione, tipoCausale.Id.GetValueOrDefault(int.MinValue));
                modificariduzioniCtrl.DataBind();
            }
        }

        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            try
            {
                this.Database.BeginTransaction();

                var contribRMgr = new OICalcoloContribRMgr(this.Database);
                var contribRRiduzMgr = new OICalcoloContribRRiduzMgr(this.Database);
                //contribRMgr.GetByContribTTipoOnereDestinazione(

                // Elimino tutte le righe di o_icalcolocontribr_riduz collegate al ContribTAttuale
                var listaContribR = contribRMgr.GetListDaContribT(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue));

                foreach (var contribR in listaContribR)
                {
                    var listaRiduzioni = contribRRiduzMgr.GetListaRiduzioniDaContribR(contribR);

                    foreach (var riduzione in listaRiduzioni)
                        contribRRiduzMgr.Delete(riduzione);
                }


                foreach (RepeaterItem rptItm in this.rptTipiCausali.Items)
                {
                    var modificariduzioniCtrl = (OIModificariduzioniCtrl)rptItm.FindControl("ModificariduzioniCtrl");
                    var valoriModificati = modificariduzioniCtrl.GetValoriModificati();

                    foreach (var campoModificato in valoriModificati)
                    {
                        var contribR = contribRMgr.GetByContribTTipoOnereDestinazione(this.IdComune, this.ContribT.Id.GetValueOrDefault(int.MinValue), campoModificato.IdTipoOnere, this.IdDestinazione);

                        var riduz = new OICalcoloContribRRiduz();
                        riduz.Idcomune = this.IdComune;
                        riduz.Codiceistanza = this.ContribT.Codiceistanza;
                        riduz.FkOiccrId = contribR.Id;
                        riduz.FkOcrrId = campoModificato.IdCausale;
                        riduz.Riduzioneperc = campoModificato.Importo;
                        riduz.Note = campoModificato.Note;

                        contribR.Note = String.Empty;
                        contribRMgr.Update(contribR);

                        contribRRiduzMgr.Insert(riduz);
                    }

                }

                this.Database.CommitTransaction();
            }
            catch (Exception ex)
            {
                this.Database.RollbackTransaction();

                this.MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
            }

            ScriptManager.RegisterStartupScript(this.cmdChiudi, this.cmdChiudi.GetType(), "success", "OnSuccess();", true);
        }
    }
}
