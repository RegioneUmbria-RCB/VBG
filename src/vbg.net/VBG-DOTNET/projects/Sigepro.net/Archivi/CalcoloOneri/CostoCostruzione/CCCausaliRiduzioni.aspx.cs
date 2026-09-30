using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Anagrafe;
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;

namespace Sigepro.net.Archivi.CalcoloOneri.CostoCostruzione
{
    public partial class CCCausaliRiduzioni : BasePage
    {

        protected void Page_Load(object sender, EventArgs e)
        {
            this.ImpostaScriptEliminazione(this.cmdElimina);
        }

        private void BindDettaglio(CcCausaliRiduzioniT cls)
        {
            this.multiView.ActiveViewIndex = 2;

            this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

            this.lblId.Item.Text = this.IsInserting ? "Nuovo" : cls.Id.ToString();

            // TODO: Inserire qui il codice per popolare i valori dei controlli
            this.txtDescrizione.Value = cls.Descrizione;

            this.dgDettagli.EditItemIndex = -1;

            this.BindDettaglio(cls.Id.GetValueOrDefault(int.MinValue));

            this.cmdElimina.Visible = !this.IsInserting;
        }

        private void BindDettaglio(int idTestata)
        {
            this.pnlDettagli.Visible = !this.IsInserting;

            if (!this.IsInserting)
            {
                var filtro = new CcCausaliRiduzioniR();
                filtro.Idcomune = this.IdComune;
                filtro.FkCccrtId = idTestata;

                this.dgDettagli.DataSource = new CcCausaliRiduzioniRMgr(this.Database).GetList(filtro);
            }
            else
            {
                this.dgDettagli.DataSource = new List<CcCausaliRiduzioniR>();
            }
            this.dgDettagli.DataBind();
        }




        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (this.multiView.ActiveViewIndex)
            {
                case (1):
                    this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
                case (2):
                    this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
                default:
                    this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
                    return;
            }
        }

        #region Scheda ricerca
        public void cmdCerca_Click(object sender, EventArgs e)
        {
            this.gvLista.DataBind();

            this.multiView.ActiveViewIndex = 1;
        }

        public void cmdNuovo_Click(object sender, EventArgs e)
        {
            this.BindDettaglio(new CcCausaliRiduzioniT());
        }

        public void cmdChiudi_Click(object sender, EventArgs e)
        {
            base.CloseCurrentPage();
        }
        #endregion


        #region Scheda lista
        public void cmdChiudiLista_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = 0;
        }

        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(this.gvLista.DataKeys[this.gvLista.SelectedIndex].Value);

            CcCausaliRiduzioniT cls = new CcCausaliRiduzioniTMgr(this.Database).GetById(this.IdComune, id);

            this.BindDettaglio(cls);
        }
        #endregion


        #region Scheda dettaglio
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            var mgr = new CcCausaliRiduzioniTMgr(this.Database);

            CcCausaliRiduzioniT cls = null;

            if (this.IsInserting)
            {
                cls = new CcCausaliRiduzioniT();
                cls.Idcomune = this.IdComune;
                cls.Software = this.Software;
            }
            else
            {
                int id = Convert.ToInt32(this.lblId.Item.Text);

                cls = mgr.GetById(this.IdComune, id);
            }

            try
            {
                cls.Descrizione = this.txtDescrizione.Value;


                if (this.IsInserting)
                    cls = mgr.Insert(cls);
                else
                    cls = mgr.Update(cls);

                this.BindDettaglio(cls);
            }
            catch (RequiredFieldException rfe)
            {
                this.MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (Exception ex)
            {
                this.MostraErrore(this.IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
            }
        }

        protected void cmdElimina_Click(object sender, EventArgs e)
        {
            var mgr = new CcCausaliRiduzioniTMgr(this.Database);

            int id = Convert.ToInt32(this.lblId.Item.Text);

            CcCausaliRiduzioniT cls = mgr.GetById(this.IdComune, id);

            try
            {
                this.Database.BeginTransaction();

                mgr.Delete(cls);

                this.Database.CommitTransaction();

                this.multiView.ActiveViewIndex = 0;
            }
            catch (Exception ex)
            {
                this.Database.RollbackTransaction();

                this.MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
            }
        }

        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            this.multiView.ActiveViewIndex = 0;
        }
        #endregion

        protected void gvDettagli_RowCommand(object sender, GridViewCommandEventArgs e)
        {

        }

        protected void dgDettagli_ItemCommand(object source, DataGridCommandEventArgs e)
        {
            var row = (DataGridItem)((WebControl)e.CommandSource).NamingContainer;

            var mgr = new CcCausaliRiduzioniRMgr(this.Database);

            int idTestata = Convert.ToInt32(this.lblId.Value);

            if (e.CommandName == "Elimina")
            {
                try
                {
                    int id = Convert.ToInt32(this.dgDettagli.DataKeys[row.ItemIndex]);
                    mgr.Delete(mgr.GetById(this.IdComune, id));
                }
                catch (Exception ex)
                {
                    this.MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
                }
            }
            else if (e.CommandName == "Inserisci")
            {
                var txtDescrizione = (TextBox)row.FindControl("txtNewDescrizione");
                var ddlTipologia = (DropDownList)row.FindControl("ddlNewTipologia");
                var txtImporto = (FloatTextBox)row.FindControl("ftxtNewImporto");

                var cls = new CcCausaliRiduzioniR();
                cls.Idcomune = this.IdComune;
                cls.FkCccrtId = idTestata;
                cls.Descrizione = txtDescrizione.Text;
                cls.Riduzioneperc = Convert.ToInt32(ddlTipologia.SelectedValue) * txtImporto.ValoreFloat;

                try
                {
                    mgr.Insert(cls);
                }
                catch (Exception ex)
                {
                    this.MostraErrore(AmbitoErroreEnum.Inserimento, ex);
                }
            }

            this.dgDettagli_CancelCommand(this, e);
        }

        protected void dgDettagli_DeleteCommand(object source, DataGridCommandEventArgs e)
        {
            int idCausaleR = Convert.ToInt32(this.dgDettagli.DataKeys[e.Item.ItemIndex]);

            var mgr = new CcCausaliRiduzioniRMgr(this.Database);
            CcCausaliRiduzioniR riduzioneR = mgr.GetById(this.IdComune, idCausaleR);

            try
            {
                mgr.Delete(riduzioneR);

                this.dgDettagli_CancelCommand(this, e);
            }
            catch (Exception ex)
            {
                this.MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
            }
        }

        protected void dgDettagli_UpdateCommand(object source, DataGridCommandEventArgs e)
        {
            int id = Convert.ToInt32(this.dgDettagli.DataKeys[e.Item.ItemIndex]);

            var mgr = new CcCausaliRiduzioniRMgr(this.Database);

            var txtDescrizione = (TextBox)e.Item.FindControl("txtDescrizione");
            var ddlTipologia = (DropDownList)e.Item.FindControl("ddlTipologia");
            var txtImporto = (FloatTextBox)e.Item.FindControl("ftxtImporto");

            CcCausaliRiduzioniR cls = mgr.GetById(this.IdComune, id);
            cls.Descrizione = txtDescrizione.Text;
            cls.Riduzioneperc = Convert.ToInt32(ddlTipologia.SelectedValue) * txtImporto.ValoreFloat;

            try
            {
                mgr.Update(cls);

                this.dgDettagli_CancelCommand(this, e);
            }
            catch (Exception ex)
            {
                this.MostraErrore(AmbitoErroreEnum.Inserimento, ex);
            }
        }

        protected void dgDettagli_EditCommand(object source, DataGridCommandEventArgs e)
        {
            int idTestata = Convert.ToInt32(this.lblId.Value);
            this.dgDettagli.EditItemIndex = e.Item.ItemIndex;

            this.dgDettagli.ShowFooter = false;

            this.BindDettaglio(idTestata);
        }

        protected void dgDettagli_CancelCommand(object source, DataGridCommandEventArgs e)
        {
            int idTestata = Convert.ToInt32(this.lblId.Value);
            this.dgDettagli.EditItemIndex = -1;

            this.dgDettagli.ShowFooter = true;

            this.BindDettaglio(idTestata);
        }

        protected void dgDettagli_ItemDataBound(object sender, DataGridItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.EditItem)
            {
                this.ImpostaScriptEliminazione((e.Item.FindControl("imgElimina") as ImageButton));
            }
        }

    }
}
