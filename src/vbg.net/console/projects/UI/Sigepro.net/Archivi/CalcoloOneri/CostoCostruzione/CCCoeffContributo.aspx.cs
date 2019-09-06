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
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using SIGePro.Net;
using System.Collections.Generic;
using System.Diagnostics;
using Init.Utils.Web.UI;
using SIGePro.Net.Navigation;
using Init.Utils;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCCoeffContributo : BasePage
{
    private int? FkCcvcId
    {
        get 
        { 
            if(string.IsNullOrEmpty(Request.QueryString["FkCcvcId"]))
                return null;
            
            return  Convert.ToInt32(Request.QueryString["FkCcvcId"]);
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);

        if (!Page.IsPostBack)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
            BindCombo();
            ImpostaDipendenzaModificabile();
            GridSource = null;
        }

        
        DataBind();

    }

    protected DataTable GridSource
    {
        get
        {
            if (Session["gridSource"] == null)
            {
                Session["gridSource"] = SetDataSource();
            }

            return (DataTable)Session["gridSource"];
        }
        set { Session["gridSource"] = value; }
    }

    public override void DataBind()
    {
        gvLista.DataSource = GridSource;
        gvLista.DataBind();
    }

    private void BindCombo()
    {

        //aree
        CCConfigurazione ccc = new CCConfigurazioneMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Software);
        Aree ar = new Aree();
        if (ccc.FkTipiareeCodice.GetValueOrDefault(int.MinValue) != int.MinValue)
        {
            ar.CODICETIPOAREA = ccc.FkTipiareeCodice;

            ar.IDCOMUNE = AuthenticationInfo.IdComune;
            ar.SOFTWARE = Software;

            ddlFkAreeCodiceArea.DataSource = new AreeMgr(AuthenticationInfo.CreateDatabase()).GetList(ar);
            ddlFkAreeCodiceArea.DataBind();

            divFkAreeCodiceArea.Visible = true;
        }
        else 
        {
            divFkAreeCodiceArea.Visible = false;
        }

        //occ_basedestinazioni
        ddlOCCBaseDestinazioni.DataSource = new OCCBaseDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(new OCCBaseDestinazioni());
        ddlOCCBaseDestinazioni.DataBind();

        //occ_basetipointervento
        ddlOCCBaseTipoIntervento.DataSource = new OCCBaseTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(new OCCBaseTipoIntervento());
        ddlOCCBaseTipoIntervento.DataBind();
        ddlOCCBaseTipoIntervento.Items.Insert(0, "");


		// ddl Attività è visibile e bindato solamente se CC_FK_SE_CODICESETTORE è valorizzato
		pnlAttivita.Visible = !String.IsNullOrEmpty(ccc.FkSeCodicesettore);

		if (pnlAttivita.Visible)
		{
			// Imposto l'etichetta delle attività con il nome del settore impostato in configurazione
			lblAttivita.Text = new SettoriMgr(Database).GetById(ccc.FkSeCodicesettore, IdComune).ToString();

			ddlAttivita.DataSource = new CCCondizioniAttivitaMgr(Database).GetListByCodiceSettoreConfigurazione(IdComune, Software);
			ddlAttivita.DataBind();
			ddlAttivita.Items.Insert(0, "");
		}

    }

    private DataTable SetDataSource()
    {
        List<CCDestinazioni> cols = GetColumns();
        List<CCTipoIntervento> rows = GetRows();

        DataTable dt = new DataTable();
        DataColumn dc = new DataColumn();
        dc.ColumnName = "INTERVENTO";
        dc.Caption = "Tipo intervento";
        dt.Columns.Add(dc);

        foreach (CCDestinazioni col in cols)
        {
            dc = new DataColumn();
            dc.ColumnName = col.Destinazione;
            dc.Caption = col.Destinazione;
            dt.Columns.Add(dc);
        }

        foreach (CCTipoIntervento row in rows)
        {
            DataRow dr = dt.NewRow();

            dr["INTERVENTO"] = row.Intervento;

            foreach (CCDestinazioni col in cols)
            {
				string idComune = IdComune;
				string software = Software;
                int ccvcId = FkCcvcId.GetValueOrDefault(int.MinValue);
				int? areeCodicearea = null;
                int ccdeId = col.Id.GetValueOrDefault(int.MinValue);
                var cctiId = row.Id;
				int? cccaId = null;

				if (!string.IsNullOrEmpty(ddlFkAreeCodiceArea.SelectedValue))
					areeCodicearea = Convert.ToInt32(ddlFkAreeCodiceArea.SelectedValue);

				if (pnlAttivita.Visible && !String.IsNullOrEmpty(ddlAttivita.SelectedValue))
					cccaId = Convert.ToInt32(ddlAttivita.SelectedValue);

				//CCCoeffContributo cccc = new CCCoeffContributo();
				//cccc.Idcomune = AuthenticationInfo.IdComune;
				//cccc.Software = Software;
				//cccc.FkCcvcId = FkCcvcId;

				//if (!string.IsNullOrEmpty(ddlFkAreeCodiceArea.SelectedValue))
				//    cccc.FkAreeCodicearea = Convert.ToInt32(ddlFkAreeCodiceArea.SelectedValue);

				//cccc.FkCcdeId = col.Id;
				//cccc.FkCctiId = row.Id;

				//if (pnlAttivita.Visible && !String.IsNullOrEmpty(ddlAttivita.SelectedValue))
				//    cccc.FkCccaId = Convert.ToInt32(ddlAttivita.SelectedValue);

				CCCoeffContributo cccc = new CCCoeffContributoMgr(AuthenticationInfo.CreateDatabase()).GetRow(idComune, software, ccvcId, areeCodicearea, ccdeId, cctiId, cccaId);

                if (cccc != null)
                {
                    dr[col.Destinazione] = "CC_" + cccc.FkCctiId.ToString() + "_" + cccc.FkCcdeId.ToString() + "_" + cccc.Coefficiente;
                }
                else
                {
                    dr[col.Destinazione] = "CC_" + row.Id + "_" + col.Id + "_";
                }
            }

            dt.Rows.Add(dr);
        }
        return dt;
    }

    private List<CCTipoIntervento> GetRows()
    {
        //arraylist contenente i nomi delle righe
        CCTipoIntervento ccti = new CCTipoIntervento();
        List<CCTipoIntervento> retVal = new List<CCTipoIntervento>();

        if (chkShowAll.Checked)
        {
            ccti.Idcomune = IdComune;
            ccti.Software = Software;

            if (!string.IsNullOrEmpty(ddlOCCBaseTipoIntervento.SelectedValue))
                ccti.FkOccbtiId = ddlOCCBaseTipoIntervento.SelectedValue;

            retVal = new CCTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(ccti);
        }
        else 
        {
            retVal.Add(ccti);
        }

        return retVal;
    }

    private List<CCDestinazioni> GetColumns()
    {
        //arraylist contenente i nomi delle colonne
        CCDestinazioni ccd = new CCDestinazioni();
        ccd.Idcomune = AuthenticationInfo.IdComune;
        ccd.Software = Software;

        if (!string.IsNullOrEmpty(ddlOCCBaseDestinazioni.SelectedValue))
            ccd.FkOccbdeId = ddlOCCBaseDestinazioni.SelectedValue;

        return new CCDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(ccd);
    }

	protected void ComboValueChanged(object sender, EventArgs e)
	{
		GridSource = SetDataSource();
		DataBind();
	}

	//protected void ddlFkAreeCodiceArea_SelectedIndexChanged(object sender, EventArgs e)
	//{
	//    GridSource = SetDataSource();
	//    DataBind();
	//}

    protected void ddlOCCBaseDestinazioni_SelectedIndexChanged(object sender, EventArgs e)
    {
        ImpostaDipendenzaModificabile();

		ComboValueChanged(sender, e);
    }

	//protected void ddlOCCBaseTipoIntervento_SelectedIndexChanged(object sender, EventArgs e)
	//{
	//    GridSource = SetDataSource();
	//    DataBind();
	//}

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow )
        {
            for(int i=1; i<e.Row.Cells.Count;i++)
            {
                TableCell tc = e.Row.Cells[i];
                string[] arVal = tc.Text.Split( Convert.ToChar("_") );
                
                DoubleTextBox ftb = new DoubleTextBox();
                ftb.ID = arVal[1] + "_" + arVal[2];
                ftb.Text = arVal[3];
                ftb.Columns = 5;

                tc.Text = "";
                tc.Controls.Add( ftb );
            }
        }
    }

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow gvr in gvLista.Rows)
        {
            if (gvr.RowType == DataControlRowType.DataRow)
            {
                for (int i = 1; i < gvr.Cells.Count; i++)
                {
                    TableCell tc = gvr.Cells[i];

                    foreach (DoubleTextBox ftb in tc.Controls)
                    {
                        CCCoeffContributo cccc = new CCCoeffContributo();
                        cccc.Idcomune = AuthenticationInfo.IdComune;
                        cccc.Software = Software;
                        cccc.FkCcvcId = FkCcvcId;
                        cccc.Coefficiente = ftb.ValoreDouble;

                        if (!string.IsNullOrEmpty(ddlFkAreeCodiceArea.SelectedValue))
                            cccc.FkAreeCodicearea = Convert.ToInt32(ddlFkAreeCodiceArea.SelectedValue);

						if ( pnlAttivita.Visible && !String.IsNullOrEmpty(ddlAttivita.SelectedValue))
							cccc.FkCccaId = Convert.ToInt32(ddlAttivita.SelectedValue);

                        string[] arVal = ftb.ID.Split(Convert.ToChar("_"));

                        if (!string.IsNullOrEmpty(arVal[0]))
                            cccc.FkCctiId = Convert.ToInt32(arVal[0]);

                        if (!string.IsNullOrEmpty(arVal[1]))
                            cccc.FkCcdeId= Convert.ToInt32(arVal[1]);
                        
                        CCCoeffContributoMgr mgr = new CCCoeffContributoMgr(AuthenticationInfo.CreateDatabase());

                        //aggiornamento/inserimento dei nuovi coefficienti
                        if (cccc.Coefficiente.GetValueOrDefault(double.MinValue) == double.MinValue)
                            mgr.DeleteSingleRow(cccc);
                        else
                            mgr.Save(cccc);
                    }
                }
            }
        }

        ImpostaDipendenzaModificabile();
        GridSource = SetDataSource();
        DataBind();
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow gvr in gvLista.Rows)
        {
            if (gvr.RowType == DataControlRowType.DataRow)
            {
                for (int i = 1; i < gvr.Cells.Count; i++)
                {
                    TableCell tc = gvr.Cells[i];

                    foreach (DoubleTextBox ftb in tc.Controls)
                    {
                        CCCoeffContributo cccc = new CCCoeffContributo();
                        cccc.Idcomune = AuthenticationInfo.IdComune;
                        cccc.Software = Software;
                        cccc.FkCcvcId = FkCcvcId;
                        cccc.Coefficiente = ftb.ValoreDouble;

                        if (!string.IsNullOrEmpty(ddlFkAreeCodiceArea.SelectedValue))
                            cccc.FkAreeCodicearea = Convert.ToInt32(ddlFkAreeCodiceArea.SelectedValue);

                        string[] arVal = ftb.ID.Split(Convert.ToChar("_"));

                        if (!string.IsNullOrEmpty(arVal[0]))
                            cccc.FkCctiId = Convert.ToInt32(arVal[0]);

                        if (!string.IsNullOrEmpty(arVal[1]))
                            cccc.FkCcdeId = Convert.ToInt32(arVal[1]);

                        CCCoeffContributoMgr mgr = new CCCoeffContributoMgr(AuthenticationInfo.CreateDatabase());
                        mgr.DeleteSingleRow(cccc);

                    }
                }
            }
        }

        ImpostaDipendenzaModificabile();
        GridSource = SetDataSource();
        DataBind();
        
    }

    private void ImpostaDipendenzaModificabile()
    {
        CCCoeffContributo cccc = new CCCoeffContributo();
        cccc.Idcomune = AuthenticationInfo.IdComune;
        cccc.Software = Software;
        cccc.FkCcvcId = FkCcvcId;
        cccc.OthersTables.Add("CC_DESTINAZIONI");
        cccc.OthersWhereClause.Add("CC_DESTINAZIONI.IDCOMUNE = CC_COEFFCONTRIBUTO.IDCOMUNE");
        cccc.OthersWhereClause.Add("CC_DESTINAZIONI.ID = CC_COEFFCONTRIBUTO.FK_CCDE_ID");
        cccc.OthersWhereClause.Add("CC_DESTINAZIONI.FK_OCCBDE_ID = '" + ddlOCCBaseDestinazioni.SelectedValue + "'");

        List<CCCoeffContributo> al = new CCCoeffContributoMgr(AuthenticationInfo.CreateDatabase()).GetList(cccc);

        if (al.Count > 0)
        {
            chkShowAll.Enabled = false;
            chkShowAll.Checked = (al[0].FkCctiId.GetValueOrDefault(int.MinValue) != int.MinValue);
        }
        else
        {
            chkShowAll.Enabled = true;
            chkShowAll.Checked = true;
        }

        ddlFkAreeCodiceArea.Enabled = chkShowAll.Checked;
        ddlOCCBaseTipoIntervento.Enabled = chkShowAll.Checked;

        if (!chkShowAll.Checked)
        {
            ddlFkAreeCodiceArea.SelectedIndex = -1;
            ddlOCCBaseTipoIntervento.SelectedIndex = -1;
        }
    }

    protected void chkShowAll_CheckedChanged(object sender, EventArgs e)
    {
        ddlFkAreeCodiceArea.Enabled = chkShowAll.Checked;
        ddlOCCBaseTipoIntervento.Enabled = chkShowAll.Checked;

        if (!chkShowAll.Checked)
        {
            ddlFkAreeCodiceArea.SelectedIndex = -1;
            ddlOCCBaseTipoIntervento.SelectedIndex = -1;
        }

        GridSource = SetDataSource();
        DataBind();
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        Response.Redirect("CCValiditaCoefficienti.aspx?token=" + Token + "&Software=" + Software + "&Id=" + FkCcvcId);
    }
}
