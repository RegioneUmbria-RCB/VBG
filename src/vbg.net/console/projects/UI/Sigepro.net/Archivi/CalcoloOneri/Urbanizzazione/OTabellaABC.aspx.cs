using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web.Security;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System.Collections.Generic;
using Init.Utils.Web.UI;
using SIGePro.Net.Navigation;
using System.Web.UI;
using Init.Utils;

public partial class Archivi_CalcoloOneri_Urbanizzazione_OTabellaABC : BasePage
{

    private int? FkOvcId
    {
        get 
        { 
            if(string.IsNullOrEmpty(Request.QueryString["fkovcid"]))
                return null;

            return Convert.ToInt32(Request.QueryString["fkovcid"]); 
        }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        ImpostaScriptEliminazione(cmdElimina);
        
        if (!Page.IsPostBack)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
            ImpostaEtichette();
            BindCombo();
            ImpostaDipendenzaModificabile();
            GridSource = null;
        }

        DataBind();
    }
        
    #region DataBind delle combo
    
    private void BindCombo()
    {
        BindDestinazioni();

        BindInterventi();

        SetCombo();
    }

    private void BindDestinazioni()
    {
        //occ_basedestinazioni
        OCCBaseDestinazioni occbd = new OCCBaseDestinazioni();
        occbd.OrderBy = "DESTINAZIONE";
        ddlOCCBaseDestinazioni.DataSource = new OCCBaseDestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(occbd);
        ddlOCCBaseDestinazioni.DataBind();
    }
    private void BindInterventi()
    {
        OCCBaseTipoIntervento occbti = new OCCBaseTipoIntervento();
        occbti.OrderBy = "INTERVENTO";
        ddlOCCBaseTipoIntervento.DataSource = new OCCBaseTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(occbti);
        ddlOCCBaseTipoIntervento.DataBind();
        ddlOCCBaseTipoIntervento.Items.Insert(0, "");
    }
    private void BindIndiciTerritoriali()
    {
        OIndiciTerritoriali oit = new OIndiciTerritoriali();
        oit.Idcomune = AuthenticationInfo.IdComune;
        oit.Software = Software;
        oit.OrderBy = "O_INDICITERRITORIALI.DESCRIZIONE";

        List<OIndiciTerritoriali> lOit = new OIndiciTerritorialiMgr(AuthenticationInfo.CreateDatabase()).GetList(oit);
        ddlFkOitId.DataSource = lOit;
        ddlFkOitId.DataBind();
        ddlFkOitId.Items.Insert(0, "");
        divIndiciTerritoriali.Visible = (lOit.Count > 0);
    }
    private void BindAreaZTO()
    {
        OConfigurazione oc = new OConfigurazioneMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Software);
        if (oc.FkTipiareeCodiceZto != int.MinValue)
        {
            Aree ar = new Aree();
            ar.CODICETIPOAREA = oc.FkTipiareeCodiceZto;
            ar.IDCOMUNE = AuthenticationInfo.IdComune;
            ar.SOFTWARE = Software;

            ddlFkAreeCodiceareaZTO.DataSource = new AreeMgr(AuthenticationInfo.CreateDatabase()).GetList(ar);
            ddlFkAreeCodiceareaZTO.DataBind();
            ddlFkAreeCodiceareaZTO.Items.Insert(0, "");

            divAreeCodiceAreaZTO.Visible = true;
        }
        else
        {
            divAreeCodiceAreaZTO.Visible = false;
        }
    }
    private void BindAreaPRG()
    {
        OConfigurazione oc = new OConfigurazioneMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Software);
        if (oc.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue) != int.MinValue)
        {
            //non ci sono record salvati quindi mostro tutti i valori possibili
            Aree ar = new Aree();
            ar.CODICETIPOAREA = oc.FkTipiareeCodicePrg;

            ar.IDCOMUNE = AuthenticationInfo.IdComune;
            ar.SOFTWARE = Software;

            ddlFkAreeCodiceareaPRG.DataSource = new AreeMgr(AuthenticationInfo.CreateDatabase()).GetList(ar);
            ddlFkAreeCodiceareaPRG.DataBind();
            ddlFkAreeCodiceareaPRG.Items.Insert(0, "");

            divAreeCodiceAreaPRG.Visible = true;
        }
        else
        {
            divAreeCodiceAreaPRG.Visible = false;
        }
    }

    private void SetCombo()
    {
        BindIndiciTerritoriali();
        BindAreaZTO();
        BindAreaPRG();

        //lista dei dati precedentemente salvati per mostrare o meno il valore vuoto nelle combo:
        //  - ddlFkAreeCodiceareaZTO
        //  - ddlFkAreeCodiceareaPRG
        //  - ddlFkOitId
        OTabellaAbc otabc = new OTabellaAbc();
        otabc.Idcomune = AuthenticationInfo.IdComune;
        otabc.Software = Software;
        otabc.FkOvcId = FkOvcId;
        otabc.OthersTables.Add("O_DESTINAZIONI");
        otabc.OthersWhereClause.Add("O_DESTINAZIONI.IDCOMUNE = O_TABELLAABC.IDCOMUNE");
        otabc.OthersWhereClause.Add("O_DESTINAZIONI.ID = O_TABELLAABC.FK_ODE_ID");
        otabc.OthersWhereClause.Add("O_DESTINAZIONI.FK_OCCBDE_ID = '" + ddlOCCBaseDestinazioni.SelectedValue + "'");
        List<OTabellaAbc> lOtabc = new OTabellaAbcMgr(AuthenticationInfo.CreateDatabase()).GetList(otabc);

        #region Aree PRG
        if (divAreeCodiceAreaPRG.Visible && ddlFkAreeCodiceareaPRG.Items.Count > 0 )
        {
            if (lOtabc.Count == 0)
            { 
                if( ddlFkAreeCodiceareaPRG.Items[0].Value != "" )
                    ddlFkAreeCodiceareaPRG.Items.Insert(0, "");
            }
            else
            {
                if (lOtabc[0].FkAreeCodiceareaPrg.GetValueOrDefault(int.MinValue) == int.MinValue)
                {
                    ddlFkAreeCodiceareaPRG.Items.Clear();
                    ddlFkAreeCodiceareaPRG.Items.Insert(0, "");
                }
                else
                {
                    if (ddlFkAreeCodiceareaPRG.Items[0].Value == "" )
                        ddlFkAreeCodiceareaPRG.Items.RemoveAt(0);
                }
            }
        }
        #endregion

        #region Aree ZTO
        if (divAreeCodiceAreaZTO.Visible && ddlFkAreeCodiceareaZTO.Items.Count > 0)
        {
            if (lOtabc.Count == 0)
            {
                if (ddlFkAreeCodiceareaZTO.Items[0].Value != "")
                    ddlFkAreeCodiceareaZTO.Items.Insert(0, "");
            }
            else
            {
                if (lOtabc[0].FkAreeCodiceareaZto.GetValueOrDefault(int.MinValue) == int.MinValue)
                {
                    ddlFkAreeCodiceareaZTO.Items.Clear();
                    ddlFkAreeCodiceareaZTO.Items.Insert(0, "");
                }
                else
                {
                    if (ddlFkAreeCodiceareaZTO.Items[0].Value == "")
                        ddlFkAreeCodiceareaZTO.Items.RemoveAt(0);
                }
            }
        }
        #endregion

        #region Indici territoriali
        if (divIndiciTerritoriali.Visible && ddlFkOitId.Items.Count > 0)
        {
            if (lOtabc.Count == 0)
            {
                if (ddlFkOitId.Items[0].Value != "")
                    ddlFkOitId.Items.Insert(0, "");
            }
            else
            {
                if (lOtabc[0].FkOitId.GetValueOrDefault(int.MinValue) == int.MinValue)
                {
                    ddlFkOitId.Items.Clear();
                    ddlFkOitId.Items.Insert(0, "");
                }
                else
                {
                    if (ddlFkOitId.Items[0].Value == "")
                        ddlFkOitId.Items.RemoveAt(0);
                }
            }
        }

        #endregion
    }

    #endregion

    #region Datasource della griglia

    protected DataTable GridSource
    {
        get
        {
            if (Session["OTabellaABCGvSource"] == null)
            {
                Session["OTabellaABCGvSource"] = SetDataSource();
            }

            return (DataTable)Session["OTabellaABCGvSource"];
        }
        set { Session["OTabellaABCGvSource"] = value; }
    }

    public override void DataBind()
    {
        gvLista.DataSource = GridSource;
        gvLista.DataBind();
    }

    private DataTable SetDataSource()
    {
        List<ODestinazioni> cols = GetColumns();
        List<OInterventi> rows = GetRows();

        DataTable dt = new DataTable();
        DataColumn dc = new DataColumn();
        dc.ColumnName = "INTERVENTO";
        dc.Caption = "Tipo intervento";
        dt.Columns.Add(dc);

        foreach (ODestinazioni col in cols)
        {
            dc = new DataColumn();
            dc.ColumnName = col.Destinazione;
            dc.Caption = col.Destinazione;
            dt.Columns.Add(dc);
        }

        foreach (OInterventi row in rows)
        {
            DataRow dr = dt.NewRow();

            dr["INTERVENTO"] = row.Intervento;

            foreach (ODestinazioni col in cols)
            {
                dr[col.Destinazione] = "O_" + row.Id.ToString() + "_" + col.Id.ToString();
            }

            dt.Rows.Add(dr);
        }
        return dt;
    }

    private List<OInterventi> GetRows()
    {
        //arraylist contenente i nomi delle righe
        OInterventi oi = new OInterventi();
        List<OInterventi> retVal = new List<OInterventi>();

        if (chkShowAll.Checked)
        {
            oi.Idcomune = AuthenticationInfo.IdComune;
            oi.Software = Software;
            oi.OrderBy = "ORDINAMENTO";
            if (!string.IsNullOrEmpty(ddlOCCBaseTipoIntervento.SelectedValue))
                oi.FkOccbtiId = ddlOCCBaseTipoIntervento.SelectedValue;

            retVal = new OInterventiMgr(AuthenticationInfo.CreateDatabase()).GetList(oi);
        }
        else
        {
            retVal.Add(oi);
        }

        return retVal;
    }

    private List<ODestinazioni> GetColumns()
    {
        //arraylist contenente i nomi delle colonne
        ODestinazioni od = new ODestinazioni();
        od.Idcomune = AuthenticationInfo.IdComune;
        od.Software = Software;
        od.OrderBy = "ORDINAMENTO";
        if (!string.IsNullOrEmpty(ddlOCCBaseDestinazioni.SelectedValue))
            od.FkOccbdeId = ddlOCCBaseDestinazioni.SelectedValue;

        return new ODestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(od);
    }

    protected GridView gvOneriUrbanizzazione(int FkOinId, int FkOdeId)
    {
        GridView retVal = new GridView();
        OTabellaAbc otabc = new OTabellaAbc();

        otabc.Idcomune = AuthenticationInfo.IdComune;
        otabc.Software = Software;
        otabc.FkOvcId = FkOvcId;

        otabc.FkOinId = FkOinId;
        otabc.FkOdeId = FkOdeId;

        if (!string.IsNullOrEmpty(ddlFkAreeCodiceareaZTO.SelectedValue))
            otabc.FkAreeCodiceareaZto = Convert.ToInt32(ddlFkAreeCodiceareaZTO.SelectedValue);

        if (!string.IsNullOrEmpty(ddlFkAreeCodiceareaPRG.SelectedValue))
            otabc.FkAreeCodiceareaPrg = Convert.ToInt32(ddlFkAreeCodiceareaPRG.SelectedValue);



        if (string.IsNullOrEmpty(ddlFkOitId.SelectedValue))
        {
            otabc.OthersWhereClause.Add("FK_OIT_ID IS NULL");
        }
        else
        {
            otabc.FkOitId = Convert.ToInt32(ddlFkOitId.SelectedValue);
        }


        retVal.ShowHeader = false;
        DataTable dt = dtOneriUrbanizzazione(otabc);


        retVal.BorderStyle = BorderStyle.None;
        retVal.CellPadding = 0;
        retVal.CellPadding = 0;
        retVal.GridLines = GridLines.None;
        retVal.RowDataBound += new GridViewRowEventHandler(retVal_RowDataBound);
        retVal.DataSource = dt;
        retVal.DataBind();

        return retVal;
    }

    void retVal_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            string[] arVal = e.Row.Cells[1].Text.Split(Convert.ToChar("_"));

            DoubleTextBox ftb = new DoubleTextBox();
            ftb.ID = arVal[0] + "_" + arVal[1] + "_" + arVal[2];
            ftb.ValoreDouble = string.IsNullOrEmpty(arVal[3]) ? double.MinValue : Convert.ToSingle(arVal[3]);
            ftb.Columns = 6;

            e.Row.Cells[1].Text = "";
            e.Row.Cells[1].Controls.Add(ftb);
        }
    }

    protected DataTable dtOneriUrbanizzazione(OTabellaAbc cls)
    {

        DataTable dt = new DataTable();

        OTipiOneri oto = new OTipiOneri();
        oto.Idcomune = AuthenticationInfo.IdComune;
        oto.Software = Software;

        List<OTipiOneri> lOto = new OTipiOneriMgr(AuthenticationInfo.CreateDatabase()).GetList(oto);

        DataColumn dcDescrizione = new DataColumn();
        dcDescrizione.ColumnName = "DESCRIZIONE";
        dt.Columns.Add(dcDescrizione);

        DataColumn dcValore = new DataColumn();
        dcValore.ColumnName = "VALORE";
        dt.Columns.Add(dcValore);

        foreach (OTipiOneri tipiOneri in lOto)
        {
            DataRow dr = dt.NewRow();
            OTabellaAbc otabc = (OTabellaAbc)cls.Clone();
            otabc.Id = null;
            otabc.Costo = null;
            otabc.FkOtoId = tipiOneri.Id;

            otabc = new OTabellaAbcMgr(AuthenticationInfo.CreateDatabase()).GetByClass(otabc);

            dr["DESCRIZIONE"] = tipiOneri.Descrizione;

            dr["VALORE"] = cls.FkOinId.ToString() + "_" + cls.FkOdeId.ToString() + "_" + tipiOneri.Id.ToString() + "_";

            if (otabc != null)
                dr["VALORE"] += otabc.Costo.ToString();

            dt.Rows.Add(dr);
        }

        return dt;
    }

    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.Header)
        {
            e.Row.Cells[0].Text = "Tipi intervento";
        }
        else if (e.Row.RowType == DataControlRowType.DataRow)
        {
            for (int i = 1; i < e.Row.Cells.Count; i++)
            {
                TableCell tc = e.Row.Cells[i];
                string[] arVal = tc.Text.Split(Convert.ToChar("_"));
                tc.Text = "";

                //per ogni cella inserisco una griglia che mostri gli oneri da poter impostare
                int FkOinId = int.MinValue;
                int FkOdeId = int.MinValue;

                FkOinId = Convert.ToInt32(arVal[1]);
                FkOdeId = Convert.ToInt32(arVal[2]);

                tc.Controls.Add(gvOneriUrbanizzazione(FkOinId, FkOdeId));
            }
        }
    }

    #endregion

    #region Selected Index Changed

    protected void ddlOCCBaseDestinazioni_SelectedIndexChanged(object sender, EventArgs e)
    {
        SetCombo();
        ImpostaDipendenzaModificabile();
        GridSource = SetDataSource();
        DataBind();
    }

    protected void ddlOCCBaseTipoIntervento_SelectedIndexChanged(object sender, EventArgs e)
    {
        SetCombo();
        GridSource = SetDataSource();
        DataBind();
    }

    protected void ddlFkOitId_SelectedIndexChanged(object sender, EventArgs e)
    {
        GridSource = SetDataSource();
        DataBind();
    }

    protected void ddlFkAreeCodiceareaZTO_SelectedIndexChanged(object sender, EventArgs e)
    {
        GridSource = SetDataSource();
        DataBind();
    }

    protected void ddlFkAreeCodiceareaPRG_SelectedIndexChanged(object sender, EventArgs e)
    {
        GridSource = SetDataSource();
        DataBind();
    }

    #endregion

    #region Eventi

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        foreach (GridViewRow gvr in gvLista.Rows)
        {
            if (gvr.RowType == DataControlRowType.DataRow)
            {
                for (int i = 1; i < gvr.Cells.Count; i++)
                {
                    TableCell tc = gvr.Cells[i];

                    //in ogni cella trovo una GridView ....
                    GridView gv = (tc.Controls[0] as GridView);

                    //... in ogni riga della gridview è presente una cella con i dati degli oneri
                    foreach (GridViewRow row in gv.Rows)
                    {
                        if (row.RowType == DataControlRowType.DataRow)
                        {
                            DoubleTextBox ftb = (row.Cells[1].Controls[0] as DoubleTextBox);
                            string[] arVal = ftb.ID.Split(Convert.ToChar("_"));

                            OTabellaAbc otabc = new OTabellaAbc();
                            otabc.FkAreeCodiceareaPrg = null;
                            if(!string.IsNullOrEmpty(ddlFkAreeCodiceareaPRG.SelectedValue))
                                otabc.FkAreeCodiceareaPrg = Convert.ToInt32(ddlFkAreeCodiceareaPRG.SelectedValue);

                            otabc.FkAreeCodiceareaZto = null;
                            if (!string.IsNullOrEmpty(ddlFkAreeCodiceareaZTO.SelectedValue))
                                otabc.FkAreeCodiceareaZto = Convert.ToInt32(ddlFkAreeCodiceareaZTO.SelectedValue);

                            otabc.FkOitId = null;
                            if(!string.IsNullOrEmpty(ddlFkOitId.SelectedValue))
                                otabc.FkOitId = Convert.ToInt32(ddlFkOitId.SelectedValue);

                            otabc.FkOdeId = Convert.ToInt32(arVal[1]);
                            otabc.FkOinId = Convert.ToInt32( arVal[0] );
                            
                            otabc.FkOtoId = Convert.ToInt32( arVal[2] );
                            otabc.FkOvcId = FkOvcId;
                            otabc.Idcomune = AuthenticationInfo.IdComune;
                            otabc.Software = Software;
                            otabc.Costo = ftb.ValoreDouble;

                            if (otabc.Costo.GetValueOrDefault(double.MinValue) != double.MinValue)
                                new OTabellaAbcMgr(AuthenticationInfo.CreateDatabase()).Save(otabc);
                            else
                                new OTabellaAbcMgr(AuthenticationInfo.CreateDatabase()).DeleteSingleRow(otabc);

                        }
                    }
                }
            }
        }

        SetCombo();
        ImpostaDipendenzaModificabile();
        GridSource = SetDataSource();
        DataBind();
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        OTabellaAbc otabc = new OTabellaAbc();
        otabc.Idcomune = AuthenticationInfo.IdComune;
        otabc.Software = Software;
        
        //listino
        otabc.FkOvcId = FkOvcId;

        //area PRG
        if (string.IsNullOrEmpty(ddlFkAreeCodiceareaPRG.SelectedValue))
            otabc.OthersWhereClause.Add("O_TABELLAABC.FK_AREE_CODICEAREA_PRG IS NULL");
        else
            otabc.FkAreeCodiceareaPrg = Convert.ToInt32(ddlFkAreeCodiceareaPRG.SelectedValue);

        //area ZTO
        if (string.IsNullOrEmpty(ddlFkAreeCodiceareaZTO.SelectedValue))
            otabc.OthersWhereClause.Add("O_TABELLAABC.FK_AREE_CODICEAREA_ZTO IS NULL");
        else
            otabc.FkAreeCodiceareaZto = Convert.ToInt32(ddlFkAreeCodiceareaZTO.SelectedValue);

        //destinazione di base
        string cmdText = "SELECT ID FROM O_DESTINAZIONI WHERE IDCOMUNE = '" + AuthenticationInfo.IdComune + "' AND SOFTWARE = '" + Software + "' AND FK_OCCBDE_ID = '" + ddlOCCBaseDestinazioni.SelectedValue + "'";
        otabc.OthersWhereClause.Add("O_TABELLAABC.FK_ODE_ID IN (" + cmdText + ")");

        //intervento di base
        if (!string.IsNullOrEmpty(ddlOCCBaseTipoIntervento.SelectedValue))
        {
            cmdText = "SELECT ID FROM O_INTERVENTI WHERE IDCOMUNE = '" + AuthenticationInfo.IdComune + "' AND SOFTWARE = '" + Software + "' AND FK_OCCBTI_ID = '" + ddlOCCBaseTipoIntervento.SelectedValue + "'";
            otabc.OthersWhereClause.Add("O_TABELLAABC.FK_OIN_ID IN (" + cmdText + ")");
        }
        //indici territoriali
        if (string.IsNullOrEmpty(ddlFkOitId.SelectedValue))
            otabc.OthersWhereClause.Add("O_TABELLAABC.FK_OIT_ID IS NULL");
        else
            otabc.FkOitId = Convert.ToInt32(ddlFkOitId.SelectedValue);

        new OTabellaAbcMgr(AuthenticationInfo.CreateDatabase()).Delete(otabc);

        SetCombo();
        ImpostaDipendenzaModificabile();
        GridSource = SetDataSource();
        DataBind();
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        Response.Redirect("OValiditaCoefficienti.aspx?Token=" + Token + "&Software=" + Software + "&Id=" + FkOvcId.ToString());
    }

    protected void chkShowAll_CheckedChanged(object sender, EventArgs e)
    {
        ddlOCCBaseTipoIntervento.Enabled = chkShowAll.Checked;

        if (!chkShowAll.Checked)
        {
            ddlOCCBaseTipoIntervento.SelectedIndex = -1;
        }

        GridSource = SetDataSource();
        DataBind();
    }

    #endregion

    private void ImpostaDipendenzaModificabile()
    {
        OTabellaAbc otabc = new OTabellaAbc();
        otabc.Idcomune = AuthenticationInfo.IdComune;
        otabc.Software = Software;
        otabc.FkOvcId = FkOvcId;
        otabc.OthersTables.Add("O_DESTINAZIONI");
        otabc.OthersWhereClause.Add("O_DESTINAZIONI.IDCOMUNE = O_TABELLAABC.IDCOMUNE");
        otabc.OthersWhereClause.Add("O_DESTINAZIONI.ID = O_TABELLAABC.FK_ODE_ID");
        otabc.OthersWhereClause.Add("O_DESTINAZIONI.FK_OCCBDE_ID = '" + ddlOCCBaseDestinazioni.SelectedValue + "'");

        List<OTabellaAbc> al = new OTabellaAbcMgr(AuthenticationInfo.CreateDatabase()).GetList(otabc);

        if (al.Count > 0)
        {
            chkShowAll.Enabled = false;
            chkShowAll.Checked = (al[0].FkOinId.GetValueOrDefault(int.MinValue) != int.MinValue);
        }
        else
        {
            chkShowAll.Enabled = true;
            chkShowAll.Checked = true;
        }

        ddlOCCBaseTipoIntervento.Enabled = chkShowAll.Checked;

        if (!chkShowAll.Checked)
        {
            ddlOCCBaseTipoIntervento.SelectedIndex = -1;
        }
    }  

    protected void ImpostaEtichette()
    { 
        OConfigurazione oc = new OConfigurazioneMgr( AuthenticationInfo.CreateDatabase() ).GetById( AuthenticationInfo.IdComune, Software );

        if (oc.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue) != int.MinValue)
        {
            TipiAree prg = new TipiAreeMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, oc.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue));
            lblFkAreeCodiceareaPRG.Text = prg.Tipoarea;
        }

        if (oc.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue) != int.MinValue)
        {
            TipiAree zto = new TipiAreeMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, oc.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue));
            lblFkAreeCodiceareaZTO.Text = zto.Tipoarea;
        }
    }
}
