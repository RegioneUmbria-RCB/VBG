using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Mono.Cecil;

namespace SIGePro.WebControls.Ajax.RicerchePlus.Designer
{
	public partial class FiltroClassiCtrl : Form
	{
		public TypeDefinitionCollection TipiDisponibili = null;

		public TypeDefinition SelectedItem = null;

		public FiltroClassiCtrl()
		{
			InitializeComponent();
		}

		private void txtFilter_TextChanged(object sender, EventArgs e)
		{
			cmbClassi.Items.Clear();

			foreach (TypeDefinition t in TipiDisponibili)
				if (t.ToString().ToUpper().IndexOf(txtFilter.Text.ToUpper()) > -1)
					cmbClassi.Items.Add(t);

			if (cmbClassi.Items.Count > 0)
				cmbClassi.SelectedIndex = 0;
		}

		private void cmdOk_Click(object sender, EventArgs e)
		{
			SelectedItem = (TypeDefinition)cmbClassi.SelectedItem;

			this.DialogResult = DialogResult.OK;
			this.Close();
		}

		private void cmdClose_Click(object sender, EventArgs e)
		{
			SelectedItem = null;

			this.DialogResult = DialogResult.Cancel;
			this.Close();
		}

		private void FiltroClassiCtrl_Load(object sender, EventArgs e)
		{
			cmbClassi.Items.Clear();

			foreach (TypeDefinition t in TipiDisponibili)
				cmbClassi.Items.Add(t);

			if (cmbClassi.Items.Count > 0)
				cmbClassi.SelectedIndex = 0;
		}
	}
}