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
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using SIGePro.WebControls.Ajax;
using System.Collections.Generic;
using SIGePro.Net.Navigation;
using Init.SIGePro.Exceptions;
using PersonalLib2.Exceptions;
using Init.SIGePro.Manager.Logic.Ricerche;

namespace Sigepro.net.Archivi.DatiDinamici
{
    public partial class Dyn2InventarioProcModelli : BasePage
    {

        protected int? codiceInventario
        {
            get
            {
                return string.IsNullOrEmpty(Request.QueryString["codiceinventario"]) ? (int?)null : Convert.ToInt32(Request.QueryString["codiceinventario"]);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!Page.IsPostBack)
            {
                //BindCombo();
                DataBind();
				this.lblDescrizione1.Text = new InventarioProcedimentiMgr(Database).GetById(IdComune , codiceInventario.GetValueOrDefault(int.MinValue)).Procedimento;
            }
        }

        public override void DataBind()
        {
            this.gvLista.DataSource = InventarioProcDyn2ModelliTMgr.Find(Token, codiceInventario.GetValueOrDefault(int.MinValue), "");
            this.gvLista.DataBind();
        }

        protected void multiView_ActiveViewChanged(object sender, EventArgs e)
        {
            switch (multiView.ActiveViewIndex)
            {
                case (0):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                    return;
                case (1):
                    Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                    return;
            }
        }

        #region Scheda lista
        protected void cmdNuovo_Click(object sender, EventArgs e)
        {
            this.rplModelloDinamico.Class = null;
            multiView.ActiveViewIndex = 1;

        }
        #endregion

        #region Scheda dettaglio
        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            InventarioProcDyn2ModelliTMgr mgr = new InventarioProcDyn2ModelliTMgr(Database);
            InventarioProcDyn2ModelliT cls = null;

            try
            {
                cls = new InventarioProcDyn2ModelliT();
                cls.Idcomune = IdComune;
                cls.Codiceinventario = codiceInventario;
                cls.FkD2mtId = string.IsNullOrEmpty(this.rplModelloDinamico.Value) ? (int?)null : Convert.ToInt32(this.rplModelloDinamico.Value);

                cls = mgr.Insert(cls);

                multiView.ActiveViewIndex = 0;

                DataBind();
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (DatabaseException de)
            {
                MostraErrore("Attenzione, si sta tendando di inserire un modello già presente per l'endoprocedimento corrente.", de);
            }
            catch (Exception ex)
            {
                MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
            }
        }

        protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
        {
            multiView.ActiveViewIndex = 0;

            DataBind();
        }
        #endregion

        protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            InventarioProcDyn2ModelliTMgr mgr = new InventarioProcDyn2ModelliTMgr(Database);

            int codiceinventario = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][0]);
            int fkd2mtid = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][1]);

            InventarioProcDyn2ModelliT cls = mgr.GetById(IdComune, codiceinventario, fkd2mtid);

            try
            {
                mgr.Delete(cls);

                DataBind();

            }
            catch (Exception ex)
            {
                MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
            }
        }

        protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                ImpostaScriptEliminazione(e.Row.Cells[1].Controls[1] as ImageButton);
            }
        }

    }
}
