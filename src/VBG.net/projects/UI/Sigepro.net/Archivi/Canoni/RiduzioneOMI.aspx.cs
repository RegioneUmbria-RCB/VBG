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

namespace Sigepro.net.Archivi.Canoni
{
    public partial class RiduzioneOMI : BasePage
    {
        protected double minimo = 0;

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
                default:
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
            }
        }

        private void BindDettaglio(CanoniRiduzioniOMI cr)
        {
            this.IsInserting = cr.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

            this.lblCodice.Value = (IsInserting) ? "Nuovo" : cr.Id.ToString();

            this.txDescrizione.Value = cr.Descrizione;
            this.txMqA.Item.ValoreDouble = cr.MqA.GetValueOrDefault(double.MinValue);

            this.cmdElimina.Visible = !IsInserting;
        }
       
        #region Lista
        protected void BindGrid()
        {
            gvLista.DataBind();
        }

        protected void cmdNuovo_Click(object sender, EventArgs e)
        {
            BindDettaglio(new CanoniRiduzioniOMI());
            this.multiView.ActiveViewIndex = 1;
        }

        protected void cmdCloseList_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }

        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);
            CanoniRiduzioniOMI cr = new CanoniRiduzioniOMIMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);
            BindDettaglio(cr);

            this.multiView.ActiveViewIndex = 1;
        }
        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                e.Row.Cells[1].Text = minimo.ToString();
                minimo = Convert.ToDouble(e.Row.Cells[2].Text);
            }
        }
        #endregion

        #region Scheda
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            CanoniRiduzioniOMI cr = new CanoniRiduzioniOMI();

            try
            {
                cr.Idcomune = IdComune;
                cr.Id = (IsInserting) ? (int?)null : Convert.ToInt32(this.lblCodice.Value);
                cr.MqA = this.txMqA.Item.ValoreDouble;
                cr.Descrizione = this.txDescrizione.Value;
                cr.Software = Software;

                CanoniRiduzioniOMIMgr mgr = new CanoniRiduzioniOMIMgr(AuthenticationInfo.CreateDatabase());

                if (IsInserting)
                    cr = mgr.Insert(cr);
                else
                    cr = mgr.Update(cr);

                BindDettaglio(cr);
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (MoreThanOneRecordException mtore)
            {
                MostraErrore("Attenzione, la riduzione OMI non è stata salvata: " + mtore.Message, mtore);
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
                CanoniRiduzioniOMI cr = new CanoniRiduzioniOMI();

                cr.Idcomune = AuthenticationInfo.IdComune;
                cr.Id = Convert.ToInt32(this.lblCodice.Value);

                CanoniRiduzioniOMIMgr mgr = new CanoniRiduzioniOMIMgr(AuthenticationInfo.CreateDatabase());

                mgr.Delete(cr);

                BindGrid();

                multiView.ActiveViewIndex = 0;
            }
            catch (Exception ex)
            {
                MostraErrore("Errore durante l'eliminazione: " + ex.Message, ex);
            }
        }

        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            BindGrid();

            multiView.ActiveViewIndex = 0;
        }
        #endregion


    }
}
