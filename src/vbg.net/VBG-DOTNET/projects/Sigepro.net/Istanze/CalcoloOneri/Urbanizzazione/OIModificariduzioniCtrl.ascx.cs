using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Web.UI;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
    public partial class OIModificariduzioniCtrl : Ninject.Web.UserControlBase
    {
        private IAuthenticationInfoResolver _authenticationInfoResolver;
        private Lazy<DataBase> _dataBase;

        private string IdComune => this._authenticationInfoResolver.Resolve().IdComune;

        private DataBase Database => this._dataBase.Value;

        public DataSet DataSource
        {
            get { return (DataSet)this.Session[this.ClientID + "_dataSource"]; }
            set { this.Session[this.ClientID + "_dataSource"] = value; }
        }

        protected override void OnInit(EventArgs e)
        {
            this._authenticationInfoResolver = StaticKernelContainer.GetService<IAuthenticationInfoResolver>();
            this._dataBase = new Lazy<DataBase>(() => this._authenticationInfoResolver.Resolve().CreateDatabase());

            // Serve a ripristinare lo stato della datagrid che viene creata dinamicamente
            this.DataBind();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            //if (!this.IsPostBack)
            //{
            //    
            //}
        }

        public override void DataBind()
        {
            this.dgCausali.DataSource = this.DataSource;
            this.dgCausali.DataBind();
        }

        protected void dgCausali_ItemDataBound(object sender, DataGridItemEventArgs e)
        {
            var row = (DataRowView)e.Item.DataItem;
            var dataSet = this.DataSource.Tables[0];

            e.Item.Cells[0].Visible = false;

            if (e.Item.ItemType == ListItemType.Header)
            {
                for (var i = 0; i < dataSet.Columns.Count; i++)
                {
                    var columnName = dataSet.Columns[i].ColumnName;
                    var currCell = e.Item.Cells[i];

                    if (columnName.IndexOf("_note") != -1)
                    {
                        currCell.Visible = false;
                    }
                    else if (columnName == "descrizioneCausale")
                    {
                        currCell.Text = "Causale";
                    }
                    else if (columnName == "percentuale")
                    {
                        currCell.Text = "Incremento/Riduzione";
                    }
                    else if (columnName.IndexOf("_selezionato") != -1)
                    {
                        currCell.ColumnSpan = 2;
                        var idTipoOnere = Convert.ToInt32(columnName.Replace("_selezionato", ""));
                        currCell.Text = "Applica a " + new OTipiOneriMgr(this.Database).GetById(this.IdComune, idTipoOnere).Descrizione;
                        currCell.HorizontalAlign = HorizontalAlign.Center;
                    }
                    else if (columnName.IndexOf("_percentuale") != -1)
                    {
                        currCell.Visible = false;
                    }

                    if (currCell.Visible)
                        currCell.Text = "<b>" + currCell.Text + "</b>";
                }
            }
            else if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var idxControllo = 0;

                for (var i = 0; i < dataSet.Columns.Count; i++)
                {
                    var columnName = dataSet.Columns[i].ColumnName;
                    var currCell = e.Item.Cells[i];
                    if (columnName.IndexOf("_note") != -1)
                    {
                        currCell.Visible = false;
                    }
                    else if (columnName.IndexOf("_selezionato") != -1)
                    {
                        var cb = new CheckBox();
                        cb.ID = idxControllo + "_CheckBox";
                        cb.Checked = Convert.ToBoolean(row[columnName]);

                        var hf = new HiddenField();
                        hf.ID = idxControllo + "_HiddenField";
                        hf.Value = row["percentuale"].ToString();

                        currCell.CssClass = "percentuale_check";
                        currCell.Controls.Clear();
                        currCell.Controls.Add(cb);
                        currCell.Controls.Add(hf);
                    }
                    else if (columnName == "percentuale")
                    {
                        var percentuale = Convert.ToDouble(row[columnName]);
                        currCell.Text = percentuale.ToString("N2") + "%";
                        currCell.HorizontalAlign = HorizontalAlign.Right;
                    }
                    else if (columnName.IndexOf("_percentuale") != -1)
                    {
                        var idTipoOnere = columnName.Replace("_percentuale", "");

                        // Updatepanel con textbox per l'immissione della percentuale
                        var dtb = new DecimalTextBox();
                        dtb.ID = idxControllo + "_DoubleTextBox";
                        dtb.ValoreDecimal = Convert.ToDecimal(row[columnName]);
                        dtb.Columns = 9;

                        var imgBtn = new ImageButton();
                        imgBtn.ID = idxControllo + "_BtnNote";
                        imgBtn.ImageUrl = "~/Images/edit.gif";
                        imgBtn.CssClass = "bottone-note";

                        var pnl = new Panel();
                        pnl.Controls.Add(dtb);
                        pnl.Controls.Add(imgBtn);

                        var ctrlNote = new OIModificaRiduzioniNote();
                        ctrlNote.ID = idxControllo + "_CtrlNote";
                        ctrlNote.Titolo = row["descrizioneCausale"].ToString();
                        ctrlNote.Text = row[idTipoOnere + "_note"].ToString();
                        ctrlNote.AssociatedControlId = idxControllo + "_CheckBox";
                        ctrlNote.ActivationControlId = imgBtn.ID;

                        var visExt = new OIModificariduzioniVisualizzazioneExtender();
                        visExt.CheckBoxId = idxControllo + "_CheckBox";
                        visExt.ImageButtonId = imgBtn.ID;
                        visExt.DoubleTextBoxId = dtb.ID;
                        visExt.Riduzione = Convert.ToDecimal(row["percentuale"]);


                        currCell.Controls.Clear();
                        currCell.Controls.Add(pnl);
                        currCell.Controls.Add(ctrlNote);
                        currCell.Controls.Add(visExt);

                        idxControllo++;
                    }
                }
            }
        }


        public class OIModificariduzioniCtrlReturnValue
        {
            private string m_note;

            public string Note
            {
                get { return this.m_note; }
                set { this.m_note = value; }
            }

            public decimal Importo { get; set; }

            public int IdTipoOnere { get; set; }

            public int IdCausale { get; set; }

            internal OIModificariduzioniCtrlReturnValue(int idCausale, int idTipoOnere, decimal importo, string note)
            {
                this.IdCausale = idCausale;
                this.IdTipoOnere = idTipoOnere;
                this.Importo = importo;
                this.m_note = note;
            }
        }

        public List<OIModificariduzioniCtrlReturnValue> GetValoriModificati()
        {
            var rVal = new List<OIModificariduzioniCtrlReturnValue>();

            var maxIdxControllo = 0;
            var listaTipiOnere = new List<int>();

            foreach (DataColumn col in this.DataSource.Tables[0].Columns)
            {
                if (col.ColumnName.IndexOf("_percentuale") != -1)
                {
                    maxIdxControllo++;
                    var idTipoOnere = Convert.ToInt32(col.ColumnName.Replace("_percentuale", ""));
                    listaTipiOnere.Add(idTipoOnere);
                }
            }

            foreach (DataGridItem item in this.dgCausali.Items)
            {
                for (var i = 0; i < maxIdxControllo; i++)
                {
                    var cb = (CheckBox)item.FindControl(i + "_CheckBox");
                    var dtb = (DecimalTextBox)item.FindControl(i + "_DoubleTextBox");
                    var txtNote = (OIModificaRiduzioniNote)item.FindControl(i + "_CtrlNote");


                    if (!cb.Checked) continue;

                    var idTipoOnere = listaTipiOnere[i];
                    var idCausale = Convert.ToInt32(this.dgCausali.DataKeys[item.ItemIndex]);
                    var importo = dtb.ValoreDecimal.GetValueOrDefault(0.0m);
                    var note = txtNote.Text;

                    rVal.Add(new OIModificariduzioniCtrlReturnValue(idCausale, idTipoOnere, importo, note));
                }
            }

            return rVal;
        }
    }
}