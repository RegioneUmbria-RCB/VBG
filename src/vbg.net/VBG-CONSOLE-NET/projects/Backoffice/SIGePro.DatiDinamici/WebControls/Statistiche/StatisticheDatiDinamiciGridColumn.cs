using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using System.ComponentModel;
using System.Web.UI;

namespace Init.SIGePro.DatiDinamici.WebControls.Statistiche
{
	public partial class StatisticheDatiDinamiciGridColumn : DataGridColumn
	{
		public const string ID_CONTROLLO_VALORE = "StatisticheDatiDinamiciGridColumn_Ctrl";

		public event StatisticheDatiDinamiciGridColumnControl.ControlPropertiesRequiredDelegate ControlPropertiesRequired;

		PropertyDescriptor m_dataFieldPropDesc = null;
		PropertyDescriptor m_controlTypePropDesc = null;

		// Properties
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

			WebControl ctrl = null;

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
						Label lbl = new Label();
						lbl.Text = "Databound item";
						ctrl = lbl;
					}
					else
					{
						ctrl = new StatisticheDatiDinamiciGridColumnControl();
						ctrl.ID = ID_CONTROLLO_VALORE;
						ctrl.DataBinding += new EventHandler(OnDataBindCell);
						(ctrl as StatisticheDatiDinamiciGridColumnControl).ControlPropertiesRequired += new StatisticheDatiDinamiciGridColumnControl.ControlPropertiesRequiredDelegate(StatisticheDatiDinamiciGridColumn_ControlPropertiesRequired);
					}
					break;
			}

			if (ctrl != null)
				cell.Controls.Add(ctrl);
		}

		void StatisticheDatiDinamiciGridColumn_ControlPropertiesRequired(object sender, Dictionary<string, string> propList)
		{
			if (this.ControlPropertiesRequired != null)
				this.ControlPropertiesRequired(sender, propList);
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

			StatisticheDatiDinamiciGridColumnControl dataControl = (StatisticheDatiDinamiciGridColumnControl)control;

			if (m_controlTypePropDesc != null)
			{
				dataControl.ControlType = m_controlTypePropDesc.GetValue(dataItem).ToString();
				dataControl.ControlValue = m_dataFieldPropDesc.GetValue(dataItem).ToString();
			}

			dataControl.Reload();
		}

	}
}
