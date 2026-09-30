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
using Init.SIGePro.Data;
using SIGePro.Net;
using SIGePro.Net.Navigation;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions;
using System.Collections.Generic;
using SIGePro.WebControls.Ajax;
using PersonalLib2.Exceptions;
using Init.SIGePro.Manager.Logic.Ricerche;

public partial class Archivi_DatiDinamici_Dyn2AlberoProcModelli : BasePage
{

    protected string ScCodice
    {
        get
        {
            return Request.QueryString["scCodice"];
        }
    }

	AlberoProc m_alberoProc;
	protected AlberoProc AlberoProc
	{
		get
		{
			if (m_alberoProc == null)
			{
				AlberoProcMgr mgr = new AlberoProcMgr(Database);
				m_alberoProc = mgr.GetByScCodice(IdComune, Software, ScCodice);
			}

			return m_alberoProc;
		}
	}



    protected void Page_Load(object sender, EventArgs e)
    {
        //ImpostaScriptEliminazione(cmdElimina);
        if (!Page.IsPostBack)
        {
            DataBind();
            this.lblDescrizione1.Text = new AlberoProcMgr(Database).GetById(AlberoProc.Sc_id.GetValueOrDefault(int.MinValue), IdComune).SC_DESCRIZIONE;
            this.cmdChiudiLista.Attributes.Add("onClick", "self.close();");
        }
    }

    public override void DataBind()
    {
        this.gvLista.DataSource = AlberoProcDyn2ModelliTMgr.Find(Token, AlberoProc.Sc_id.GetValueOrDefault(int.MinValue), "");
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
        AlberoProcDyn2ModelliTMgr mgr = new AlberoProcDyn2ModelliTMgr(Database);
        AlberoProcDyn2ModelliT cls = null;

        try
        {
            cls = new AlberoProcDyn2ModelliT();
            cls.Idcomune = IdComune;
			cls.FkScId = AlberoProc.Sc_id;
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
            MostraErrore("Attenzione, si sta tendando di inserire un modello già presente per il procedimento corrente.", de);
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
        AlberoProcDyn2ModelliTMgr mgr = new AlberoProcDyn2ModelliTMgr(Database);

        int fk_scid = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][0]);
        int fkd2mtid = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][1]);

        AlberoProcDyn2ModelliT cls = mgr.GetById(IdComune, fk_scid, fkd2mtid);

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
