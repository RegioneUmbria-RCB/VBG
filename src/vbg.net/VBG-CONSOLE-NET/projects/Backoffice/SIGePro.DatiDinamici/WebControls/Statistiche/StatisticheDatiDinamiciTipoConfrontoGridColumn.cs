using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using System.ComponentModel;
using System.Web.UI;
using Init.SIGePro.DatiDinamici.Statistiche;

namespace Init.SIGePro.DatiDinamici.WebControls.Statistiche
{
	public partial class StatisticheDatiDinamiciTipoConfrontoGridColumn : DataGridColumn
	{
		public const string IdControlloTipoConfronto = "ddlTipoConfronto";

		PropertyDescriptor m_dataFieldPropDesc = null;
		PropertyDescriptor m_controlTypePropDesc = null;

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
					if (DesignMode)
					{
						cell.Controls.Add(new DropDownList());
					}
					else
					{
						ctrl = new DropDownList();
						ctrl.ID = IdControlloTipoConfronto;
						ctrl.DataBinding += new EventHandler(OnDataBindCell);
					}
					break;
			}

			if (ctrl != null)
				cell.Controls.Add(ctrl);
		}

		void OnDataBindCell(object sender, EventArgs e)
		{
			Control control = (Control)sender;
			DataGridItem namingContainer = (DataGridItem)control.NamingContainer;
			object dataItem = namingContainer.DataItem;

			if (m_dataFieldPropDesc == null)
				m_dataFieldPropDesc = TypeDescriptor.GetProperties(dataItem).Find(DataField, true);

			if (m_controlTypePropDesc == null)
				m_controlTypePropDesc = TypeDescriptor.GetProperties(dataItem).Find(ControlTypeDataField, true);

			if (control is TableCell)
				return;

			DropDownList dataControl = (DropDownList)control;
			dataControl.Items.Clear();

			if (m_controlTypePropDesc == null) return;

			string tipoControllo = m_controlTypePropDesc.GetValue(dataItem).ToString();
			string valore = m_dataFieldPropDesc.GetValue(dataItem).ToString();

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
