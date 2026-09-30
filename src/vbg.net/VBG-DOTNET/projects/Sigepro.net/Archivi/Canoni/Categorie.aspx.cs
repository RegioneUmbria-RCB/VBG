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
    public partial class Categorie : BasePage
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

        private void BindDettaglio(CanoniCategorie cc)
        {
            this.IsInserting = cc.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

            this.lblCodice.Value = IsInserting ? "Nuovo" : cc.Id.ToString();
            this.txDescrizione.Value = cc.Descrizione;

            this.cmdElimina.Visible = !IsInserting;

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
            BindDettaglio(new CanoniCategorie());
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

        protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
        {
            int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

            CanoniCategorie cc = new CanoniCategorieMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, id);

            BindDettaglio(cc);

        }
        #endregion

        #region Scheda
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            CanoniCategorie cc = new CanoniCategorie();

            try
            {
                cc.Idcomune = AuthenticationInfo.IdComune;
                cc.Software = Software;
                cc.Id = null;
                if (!IsInserting)
                    cc.Id = Convert.ToInt32(lblCodice.Value);
                cc.Descrizione = this.txDescrizione.Value;

                CanoniCategorieMgr mgr = new CanoniCategorieMgr(AuthenticationInfo.CreateDatabase());

                if (IsInserting)
                    cc = mgr.Insert(cc);
                else
                    cc = mgr.Update(cc);

                BindDettaglio(cc);
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
                CanoniCategorie cc = new CanoniCategorie();

                cc.Idcomune = AuthenticationInfo.IdComune;
                cc.Id = Convert.ToInt32(lblCodice.Value);

                CanoniCategorieMgr mgr = new CanoniCategorieMgr(AuthenticationInfo.CreateDatabase());

                mgr.Delete(cc);

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
