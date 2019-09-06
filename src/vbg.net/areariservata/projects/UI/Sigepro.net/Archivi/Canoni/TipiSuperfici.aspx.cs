using System;
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
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions;
using System.Collections.Generic;
using Init.Utils.Web.UI;

namespace Sigepro.net.Archivi.Canoni
{
    public partial class TipiSuperfici : BasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            ImpostaScriptEliminazione(cmdElimina);

        }

        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (multiView.ActiveViewIndex)
            {
                case (1):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
                case (2):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
                default:
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
                    return;
            }
        }

        private void BindDettaglio(CanoniTipiSuperfici cts)
        {
            this.IsInserting = cts.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

            this.lblCodice.Value = IsInserting ? "Nuovo" : cts.Id.ToString();
            this.txTipoSuperficie.Value = cts.TipoSuperficie;
            this.chkPertinenza.Item.Checked = (cts.Pertinenza == 1);
            this.ddlTipoCalcolo.SelectedValue = cts.Tipocalcolo;

            this.cmdElimina.Visible = !IsInserting;

            if (IsInserting)
            {
                this.chkFlagConteggiaMq.Item.Checked = true;
            }
            else
            {
                this.chkFlagConteggiaMq.Item.Checked = (cts.FlagConteggiaMq == 1);
                this.chkFlagConteggiaMq.Enabled = this.chkPertinenza.Enabled = !(new CanoniTipiSuperficiMgr(Database).isUsed(IdComune, this.lblCodice.Value));            
            }

            multiView.ActiveViewIndex = 2;
        }

        #region Cerca
        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            gvLista.DataBind();
            multiView.ActiveViewIndex = 1;
        }

        protected void cmdNuovo_Click(object sender, EventArgs e)
        {
            BindDettaglio(new CanoniTipiSuperfici());
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }
        #endregion

        #region Lista
        protected void cmdCloseList_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;
        }
        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                CanoniTipiSuperfici cts = (CanoniTipiSuperfici)e.Row.DataItem;
                e.Row.Cells[1].Text = (cts.Pertinenza < 1) ? "No" : "Si";
            }
        }
        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

            CanoniTipiSuperfici cts = new CanoniTipiSuperficiMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

            BindDettaglio(cts);
        }
        #endregion

        #region Scheda
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            CanoniTipiSuperfici cts = new CanoniTipiSuperfici();

            try
            {
                cts.Idcomune = AuthenticationInfo.IdComune;
                cts.Software = Software;
                cts.Id = null;
                if (!IsInserting)
                    cts.Id = Convert.ToInt32(lblCodice.Value);

                cts.TipoSuperficie = this.txTipoSuperficie.Value;
                cts.Tipocalcolo = ddlTipoCalcolo.SelectedValue;
                cts.Pertinenza = (this.chkPertinenza.Item.Checked) ? 1 : 0;
                cts.FlagConteggiaMq = (this.chkFlagConteggiaMq.Item.Checked) ? 1 : 0;
                CanoniTipiSuperficiMgr mgr = new CanoniTipiSuperficiMgr(AuthenticationInfo.CreateDatabase());

                if (IsInserting)
                    cts = mgr.Insert(cts);
                else
                    cts = mgr.Update(cts);

                BindDettaglio(cts);
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (Exception ex)
            {
                MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
            }
        }

        protected void cmdElimina_Click(object sender, EventArgs e)
        {
            try
            {
                CanoniTipiSuperfici cts = new CanoniTipiSuperfici();

                cts.Idcomune = AuthenticationInfo.IdComune;
                cts.Id = Convert.ToInt32(lblCodice.Value);

                CanoniTipiSuperficiMgr mgr = new CanoniTipiSuperficiMgr(AuthenticationInfo.CreateDatabase());

                mgr.Delete(cts);

                multiView.ActiveViewIndex = 0;
            }
            catch (Exception ex)
            {
                MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
            }
        }

        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;
        }
        #endregion
    }
}
