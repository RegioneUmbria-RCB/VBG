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
using System.Collections.Generic;
using Init.Utils.Web.UI;
using SIGePro.Net.Navigation;
using Init.Utils;

public partial class Archivi_CalcoloOneri_Urbanizzazione_OTabellaD : BasePage
{
    private int? FkOvcId
    {
        get
        {
            return string.IsNullOrEmpty(Request.QueryString["fkovcid"]) ? (int?)null : Convert.ToInt32(Request.QueryString["fkovcid"]);
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
            if (Session["OTabellaDGvSource"] == null)
            {
                Session["OTabellaDGvSource"] = SetDataSource();
            }

            return (DataTable)Session["OTabellaDGvSource"];
        }
        set { Session["OTabellaDGvSource"] = value; }
    }

    public override void DataBind()
    {
        gvLista.DataSource = GridSource;
        gvLista.DataBind();
    }

    private void BindCombo()
    {

        ODestinazioni od = new ODestinazioni();
        od.Idcomune = AuthenticationInfo.IdComune;
        od.FkOccbdeId = "I";
        od.OrderBy = "ORDINAMENTO";

        //o_destinazioni
        ddlODestinazioni.DataSource = new ODestinazioniMgr(AuthenticationInfo.CreateDatabase()).GetList(od);
        ddlODestinazioni.DataBind();

        //occ_basetipointervento
        OCCBaseTipoIntervento occbti = new OCCBaseTipoIntervento();
        occbti.OrderBy = "INTERVENTO";
        ddlOCCBaseTipoIntervento.DataSource = new OCCBaseTipoInterventoMgr(AuthenticationInfo.CreateDatabase()).GetList(occbti);
        ddlOCCBaseTipoIntervento.DataBind();
        ddlOCCBaseTipoIntervento.Items.Insert(0, "");

    }

    private DataTable SetDataSource()
    {
        List<OClassiAddetti> cols = GetColumns();
        List<OInterventi> rows = GetRows();

        DataTable dt = new DataTable();
        DataColumn dc = new DataColumn();
        dc.ColumnName = "INTERVENTO";
        dc.Caption = "Tipo intervento";
        dt.Columns.Add(dc);

        foreach (OClassiAddetti oca in cols)
        {
            dc = new DataColumn();
            dc.ColumnName = oca.Classe;
            dc.Caption = oca.Classe;
            dt.Columns.Add(dc);
        }
        
        foreach (OInterventi row in rows)
        {
            DataRow dr = dt.NewRow();

            dr["INTERVENTO"] = row.Intervento;

            foreach (OClassiAddetti oca in cols)
            {
                dr[oca.Classe] = "O_" + row.Id.ToString() + "_" + oca.Id.ToString();
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

    private List<OClassiAddetti> GetColumns()
    {
        //arraylist contenente i nomi delle colonne
        OClassiAddetti oca = new OClassiAddetti();
        oca.Idcomune = AuthenticationInfo.IdComune;
        oca.Software = Software;
        oca.OrderBy = "ORDINAMENTO";

        return new OClassiAddettiMgr(AuthenticationInfo.CreateDatabase()).GetList(oca);
    }

    protected void ddlODestinazioni_SelectedIndexChanged(object sender, EventArgs e)
    {
        ImpostaDipendenzaModificabile();
        GridSource = SetDataSource();
        DataBind();
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
                int FkOcaId = int.MinValue;

                FkOinId = Convert.ToInt32(arVal[1]);
                FkOcaId = Convert.ToInt32(arVal[2]);

                tc.Controls.Add(gvOneriUrbanizzazione(FkOinId, FkOcaId));
            }
        }
    }

    protected GridView gvOneriUrbanizzazione(int FkOinId, int FkOcaId)
    {
        GridView retVal = new GridView();
        OTabellaD otd = new OTabellaD();

        otd.Idcomune = AuthenticationInfo.IdComune;
        otd.Software = Software;
        otd.FkOvcId = FkOvcId;

        otd.FkOinId = FkOinId;
        otd.FkOcaId = FkOcaId;

        if (!string.IsNullOrEmpty(ddlODestinazioni.SelectedValue))
            otd.FkOdeId = Convert.ToInt32(ddlODestinazioni.SelectedValue);

        retVal.ShowHeader = false;
        DataTable dt = dtOneriUrbanizzazione(otd);


        retVal.BorderStyle = BorderStyle.None;
        retVal.CellPadding = 0;
        retVal.CellPadding = 0;
        retVal.GridLines = GridLines.None;
        retVal.RowDataBound += new GridViewRowEventHandler(retVal_RowDataBound);
        retVal.DataSource = dt;
        retVal.DataBind();

        return retVal;
    }

    protected DataTable dtOneriUrbanizzazione(OTabellaD cls)
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
            OTabellaD otd = (OTabellaD)cls.Clone();
            otd.Id = null;
            otd.Costo = null;
            otd.FkOtoId = tipiOneri.Id;

            otd = new OTabellaDMgr(AuthenticationInfo.CreateDatabase()).GetByClass(otd);

            dr["DESCRIZIONE"] = tipiOneri.Descrizione;

            dr["VALORE"] = cls.FkOinId.ToString() + "_" + cls.FkOcaId.ToString() + "_" + tipiOneri.Id.ToString() + "_";

            if (otd != null)
                dr["VALORE"] += otd.Costo.ToString();

            dt.Rows.Add(dr);
        }

        return dt;
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

                            OTabellaD otd = new OTabellaD();
                            otd.Idcomune = AuthenticationInfo.IdComune;
                            otd.Software = Software;
                            otd.FkOvcId = FkOvcId;
                            otd.FkOdeId = Convert.ToInt32(ddlODestinazioni.SelectedValue);
                            otd.FkOinId = Convert.ToInt32(arVal[0]);
                            otd.FkOcaId = Convert.ToInt32(arVal[1]);
                            otd.FkOtoId = Convert.ToInt32(arVal[2]);
                            otd.Costo = ftb.ValoreDouble;

                            if (otd.Costo.GetValueOrDefault(double.MinValue) == double.MinValue)
                                new OTabellaDMgr(AuthenticationInfo.CreateDatabase()).DeleteSingleRow(otd);
                            else
                                new OTabellaDMgr(AuthenticationInfo.CreateDatabase()).Save(otd);
                        }
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
        OTabellaD otd = new OTabellaD();
        otd.Idcomune = AuthenticationInfo.IdComune;
        otd.Software = Software;
        otd.FkOvcId = FkOvcId;
        otd.FkOdeId = Convert.ToInt32(ddlODestinazioni.SelectedValue);

        if (!string.IsNullOrEmpty(ddlOCCBaseTipoIntervento.SelectedValue))
        {
            string cmdText = "SELECT ID FROM O_INTERVENTI WHERE IDCOMUNE = '" + AuthenticationInfo.IdComune + "' AND SOFTWARE = '" + Software + "' AND FK_OCCBTI_ID = '" + ddlOCCBaseTipoIntervento.SelectedValue + "'";
            otd.OthersWhereClause.Add("O_TABELLAD.FK_OIN_ID IN (" + cmdText + ")");
        }

        new OTabellaDMgr(AuthenticationInfo.CreateDatabase()).Delete(otd);

        ImpostaDipendenzaModificabile();
        GridSource = SetDataSource();
        DataBind();
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        Response.Redirect("OValiditaCoefficienti.aspx?token=" + Token + "&software=" + Software + "&Id=" + FkOvcId);
    }

    protected void ddlOCCBaseTipoIntervento_SelectedIndexChanged(object sender, EventArgs e)
    {
        GridSource = SetDataSource();
        DataBind();
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

    private void ImpostaDipendenzaModificabile()
    {
        OTabellaD otd = new OTabellaD();
        otd.Idcomune = AuthenticationInfo.IdComune;
        otd.Software = Software;
        otd.FkOvcId = FkOvcId;
        try
        {
            otd.FkOdeId = Convert.ToInt32(ddlODestinazioni.SelectedValue);
        }
        catch (Exception ex)
        {
            MostraErrore("Attenzione, deve essere inserita almeno una destinazione di tipo INDUSTRIALE ARTIGIANALE", ex);
        }

        List<OTabellaD> al = new OTabellaDMgr(AuthenticationInfo.CreateDatabase()).GetList(otd);

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
}
