using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici.Statistiche;

namespace Init.SIGePro.DatiDinamici.WebControls.Statistiche
{
    public partial class StatisticheDatiDinamiciTipoConfrontoGridColumn : DataGridColumn
    {
        public const string IdControlloTipoConfronto = "ddlTipoConfronto";
        private PropertyDescriptor m_dataFieldPropDesc = null;
        private PropertyDescriptor m_controlTypePropDesc = null;

        [DefaultValue("")]
        public virtual string DataField
        {
            get
            {
                object obj2 = base.ViewState["DataField"];
                if (obj2 != null)
                {
                    return (string)obj2;
                }
                return string.Empty;
            }
            set
            {
                base.ViewState["DataField"] = value;
                this.OnColumnChanged();
            }
        }

        [DefaultValue("")]
        public virtual string ControlTypeDataField
        {
            get
            {
                object obj2 = base.ViewState["ControlTypeDataField"];
                if (obj2 != null)
                {
                    return (string)obj2;
                }
                return string.Empty;
            }
            set
            {
                base.ViewState["ControlTypeDataField"] = value;
                this.OnColumnChanged();
            }
        }


        public override void InitializeCell(TableCell cell, int columnIndex, ListItemType itemType)
        {
            base.InitializeCell(cell, columnIndex, itemType);

            DropDownList ctrl = null;

            switch (itemType)
            {
                case ListItemType.Header:
                    cell.Text = this.HeaderText;
                    break;
                case ListItemType.Item:
                case ListItemType.AlternatingItem:
                case ListItemType.EditItem:
                    if (this.DesignMode)
                    {
                        cell.Controls.Add(new DropDownList());
                    }
                    else
                    {
                        ctrl = new DropDownList();
                        ctrl.ID = IdControlloTipoConfronto;
                        ctrl.DataBinding += new EventHandler(this.OnDataBindCell);
                    }
                    break;
            }

            if (ctrl != null)
                cell.Controls.Add(ctrl);
        }

        private void OnDataBindCell(object sender, EventArgs e)
        {
            Control control = (Control)sender;
            DataGridItem namingContainer = (DataGridItem)control.NamingContainer;
            object dataItem = namingContainer.DataItem;

            if (this.m_dataFieldPropDesc == null)
                this.m_dataFieldPropDesc = TypeDescriptor.GetProperties(dataItem).Find(this.DataField, true);

            if (this.m_controlTypePropDesc == null)
                this.m_controlTypePropDesc = TypeDescriptor.GetProperties(dataItem).Find(this.ControlTypeDataField, true);

            if (control is TableCell)
                return;

            DropDownList dataControl = (DropDownList)control;
            dataControl.Items.Clear();

            if (this.m_controlTypePropDesc == null) return;

            string tipoControllo = this.m_controlTypePropDesc.GetValue(dataItem).ToString();
            string valore = this.m_dataFieldPropDesc.GetValue(dataItem).ToString();

            List<KeyValuePair<TipoConfrontoFiltroEnum, string>> lista = TipoConfrontoFiltroDictionary.GetFiltriSupportati(tipoControllo);

            foreach (KeyValuePair<TipoConfrontoFiltroEnum, string> it in lista)
                dataControl.Items.Add(new ListItem(it.Value, it.Key.ToString()));

            if (String.IsNullOrEmpty(valore))
                dataControl.SelectedIndex = 0;
            else
                dataControl.SelectedValue = valore;


        }
    }
}
