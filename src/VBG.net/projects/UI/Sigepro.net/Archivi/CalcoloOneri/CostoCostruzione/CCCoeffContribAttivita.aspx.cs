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
using System.Collections.Generic;
using SIGePro.Net.Navigation;
using Init.Utils.Web.UI;
using Init.Utils;

public partial class Archivi_CalcoloOneri_CostoCostruzione_CCCoeffContribAttivita : BasePage
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
            GridSource = null;
        }

        DataBind();
    }

    protected DataTable GridSource
    {
        get
        {
            if (Session["gridSourceCCCA"] == null)
            {
                Session["gridSourceCCCA"] = SetDataSource();
            }

            return (DataTable)Session["gridSourceCCCA"];
        }
        set { Session["gridSourceCCCA"] = value; }
    }

    public override void DataBind()
    {
        gvLista.DataSource = GridSource;
        gvLista.DataBind();
    }

    private void BindCombo()
    {

        //occ_basedestinazioni
        ddlOCCBaseDestinazioni.DataSource = new OCCBaseDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(new OCCBaseDestinazioni());
        ddlOCCBaseDestinazioni.DataBind();

        //settori
        Settori sett = new Settori();
        sett.IDCOMUNE = AuthenticationInfo.IdComune;
        sett.SOFTWARE = Software;
        sett.OthersTables.Add("CC_CONFIGURAZIONE_SETTORI");
        sett.OthersWhereClause.Add("CC_CONFIGURAZIONE_SETTORI.IDCOMUNE = SETTORI.IDCOMUNE");
        sett.OthersWhereClause.Add("CC_CONFIGURAZIONE_SETTORI.FK_SE_CODICESETTORE = SETTORI.CODICESETTORE");
        ddlConfigurazioneSettori.DataSource = new SettoriMgr(AuthenticationInfo.CreateDatabase()).GetList(sett);
        ddlConfigurazioneSettori.DataBind();

    }

    private DataTable SetDataSource()
    {
        List<CCDestinazioni> cols = GetColumns();
        List<CCCondizioniAttivita> rows = GetRows();

        DataTable dt = new DataTable();
        DataColumn dc = new DataColumn();
        dc.ColumnName = "DETTAGLIOINFO";
        dc.Caption = "Dettaglio informazione";
        dt.Columns.Add(dc);

        foreach (CCDestinazioni col in cols)
        {
            dc = new DataColumn();
            dc.ColumnName = col.Destinazione;
            dc.Caption = col.Destinazione;
            dt.Columns.Add(dc);
        }

        foreach (CCCondizioniAttivita row in rows)
        {
            DataRow dr = dt.NewRow();

            dr["DETTAGLIOINFO"] = new AttivitaMgr(AuthenticationInfo.CreateDatabase()).GetById(row.FkAtCodiceistat, AuthenticationInfo.IdComune).ISTAT;

            foreach (CCDestinazioni col in cols)
            {
                CCCoeffContribAttivita cccca = new CCCoeffContribAttivita();
                cccca.Idcomune = AuthenticationInfo.IdComune;
                cccca.Software = Software;
                cccca.FkCcvcId = FkCcvcId;
                cccca.FkCcdeId = col.Id;
                cccca.FkCccaId = row.Id;

                cccca = new CCCoeffContribAttivitaMgr(AuthenticationInfo.CreateDatabase()).GetByClass(cccca);

                if (cccca != null)
                {
                    dr[col.Destinazione] = "CC_" + cccca.FkCccaId.ToString() + "_" + cccca.FkCcdeId.ToString() + "_" + cccca.Coefficiente.ToString();
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

    private List<CCCondizioniAttivita> GetRows()
    {
        string codiceSettore = (string.IsNullOrEmpty(ddlConfigurazioneSettori.SelectedValue)) ? null : ddlConfigurazioneSettori.SelectedValue;
        return CCCondizioniAttivitaMgr.Find(AuthenticationInfo.Token, codiceSettore);
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

    protected void ddlOCCBaseDestinazioni_SelectedIndexChanged(object sender, EventArgs e)
    {
        GridSource = SetDataSource();
        DataBind();
    }

    protected void ddlConfigurazioneSettori_SelectedIndexChanged(object sender, EventArgs e)
    {
        GridSource = SetDataSource();
        DataBind();
    }

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.Header)
        {
            if (ddlConfigurazioneSettori.SelectedItem != null)
                e.Row.Cells[0].Text = ddlConfigurazioneSettori.SelectedItem.Text;
        }
        else if (e.Row.RowType == DataControlRowType.DataRow)
        {
            for (int i = 1; i < e.Row.Cells.Count; i++)
            {
                TableCell tc = e.Row.Cells[i];
                string[] arVal = tc.Text.Split(Convert.ToChar("_"));

                DoubleTextBox ftb = new DoubleTextBox();
                ftb.ID = arVal[1] + "_" + arVal[2];
                ftb.Text = arVal[3];
                ftb.Columns = 5;

                tc.Text = "";
                tc.Controls.Add(ftb);
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
                        CCCoeffContribAttivita cccca = new CCCoeffContribAttivita();
                        cccca.Idcomune = AuthenticationInfo.IdComune;
                        cccca.Software = Software;
                        cccca.FkCcvcId = FkCcvcId;
                        cccca.Coefficiente = ftb.ValoreDouble;

                        string[] arVal = ftb.ID.Split(Convert.ToChar("_"));

                        cccca.FkCccaId = Convert.ToInt32(arVal[0]);
                        cccca.FkCcdeId = Convert.ToInt32(arVal[1]);

                        //aggiornamento/inserimento dei nuovi coefficienti
                        CCCoeffContribAttivitaMgr mgr = new CCCoeffContribAttivitaMgr(AuthenticationInfo.CreateDatabase());

                        if (cccca.Coefficiente.GetValueOrDefault(double.MinValue) == double.MinValue)
							mgr.DeleteSingleRow(cccca.Idcomune, cccca.Software, cccca.FkCcvcId.Value, cccca.FkCcdeId.Value, cccca.FkCccaId);
                        else
                            mgr.Save(cccca);
                    }
                }
            }
        }

        GridSource = SetDataSource();
        DataBind();
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
		try
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
							CCCoeffContribAttivita cccca = new CCCoeffContribAttivita();
							cccca.Idcomune = AuthenticationInfo.IdComune;
							cccca.Software = Software;
							cccca.FkCcvcId = FkCcvcId;
							string[] arVal = ftb.ID.Split(Convert.ToChar("_"));
							cccca.FkCccaId = Convert.ToInt32(arVal[0]);
							cccca.FkCcdeId = Convert.ToInt32(arVal[1]);

							CCCoeffContribAttivitaMgr mgr = new CCCoeffContribAttivitaMgr(AuthenticationInfo.CreateDatabase());

							mgr.DeleteSingleRow(cccca.Idcomune, cccca.Software, cccca.FkCcvcId.Value, cccca.FkCcdeId.Value, cccca.FkCccaId);
						}
					}
				}
			}

			GridSource = SetDataSource();
			DataBind();
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
		}


    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        Response.Redirect("CCValiditaCoefficienti.aspx?token=" + Token + "&Software=" + Software + "&Id=" + FkCcvcId);
    }
}
