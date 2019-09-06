using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Text.RegularExpressions;
using System.ComponentModel.Design;
using System.Collections;
using System.Windows.Forms.Design;
using System.Web.UI.Design;
using Mono.Cecil;

namespace SIGePro.WebControls.Ajax.RicerchePlus.Designer
{
	public partial class RicerchePlusDesignerInterface : Form
	{
		TypeDefinitionCollection m_types = null;
		RicerchePlusDesigner m_designer;

		#region Valori dei controlli
		private RicerchePlusDesigner.ParametriControlloTesto m_parametriId = new RicerchePlusDesigner.ParametriControlloTesto();

		public RicerchePlusDesigner.ParametriControlloTesto ParametriId
		{
			get { return m_parametriId; }
			set { m_parametriId = value; RefreshParameters(); }
		}

		private RicerchePlusDesigner.ParametriControlloTesto m_parametriDescrizione = new RicerchePlusDesigner.ParametriControlloTesto();

		public RicerchePlusDesigner.ParametriControlloTesto ParametriDescrizione
		{
			get { return m_parametriDescrizione; }
			set { m_parametriDescrizione = value; RefreshParameters(); }
		}

		private RicerchePlusDesigner.ParametriControlloAutoComplete m_parametriAutoComplete = new RicerchePlusDesigner.ParametriControlloAutoComplete();

		public RicerchePlusDesigner.ParametriControlloAutoComplete ParametriAutoComplete
		{
			get { return m_parametriAutoComplete; }
			set { m_parametriAutoComplete = value; RefreshParameters(); }
		}


		private void RefreshParameters()
		{
			txtIdColumns.Text = ParametriId.Columns.ToString();
			txtIdMaxLength.Text = ParametriId.MaxLength.ToString();

			txtDescColumns.Text = ParametriDescrizione.Columns.ToString();
			txtDescMaxLength.Text = ParametriDescrizione.MaxLength.ToString();

			txtAcCompletionInterval.Text = ParametriAutoComplete.CompletionInterval.ToString();
			txtAcCompletionSetCount.Text = ParametriAutoComplete.CompletionSetCount.ToString();
			txtAcMinimumPrefixLength.Text = ParametriAutoComplete.MinimumPrefixLength.ToString();
			txtAcServiceMethod.Text = ParametriAutoComplete.ServiceMethod;
			txtAcServicePath.Text = ParametriAutoComplete.ServicePath;

			txtAutLoadingIcon.Text = ParametriAutoComplete.LoadingIcon;

			txtAutListCssClass.Text = ParametriAutoComplete.CompletionListCssClass;
			txtAutItemCssClass.Text = ParametriAutoComplete.CompletionListItemCssClass;
			txtAutSelectedItemCssClass.Text = ParametriAutoComplete.CompletionListHighlightedItemCssClass;

			chkAutoSelect.Checked = ParametriAutoComplete.AutoSelect;

			chkRicercaSoftwareTT.Checked = ParametriAutoComplete.RicercaSoftwareTT;
		}

		private void SyncParameters()
		{
			ParametriId.Columns = Convert.ToInt32(txtIdColumns.Text);
			ParametriId.MaxLength = Convert.ToInt32(txtIdMaxLength.Text);

			ParametriDescrizione.Columns = Convert.ToInt32(txtDescColumns.Text);
			ParametriDescrizione.MaxLength = Convert.ToInt32(txtDescMaxLength.Text);

			ParametriAutoComplete.CompletionInterval = Convert.ToInt32(txtAcCompletionInterval.Text);
			ParametriAutoComplete.CompletionSetCount = Convert.ToInt32(txtAcCompletionSetCount.Text);
			ParametriAutoComplete.MinimumPrefixLength = Convert.ToInt32(txtAcMinimumPrefixLength.Text);

			ParametriAutoComplete.ServiceMethod = txtAcServiceMethod.Text;
			ParametriAutoComplete.ServicePath = txtAcServicePath.Text;

			ParametriAutoComplete.DataClassType = cmbClasse.Text;
			ParametriAutoComplete.TargetPropertyName = cmbProprietaId.Text;
			ParametriAutoComplete.DescriptionPropertyNames = JoinSelectedItems();
			ParametriAutoComplete.LoadingIcon = txtAutLoadingIcon.Text;

			ParametriAutoComplete.CompletionListCssClass = txtAutListCssClass.Text;
			ParametriAutoComplete.CompletionListItemCssClass = txtAutItemCssClass.Text;
			ParametriAutoComplete.CompletionListHighlightedItemCssClass = txtAutSelectedItemCssClass.Text;

			ParametriAutoComplete.AutoSelect = chkAutoSelect.Checked;

			ParametriAutoComplete.RicercaSoftwareTT = chkRicercaSoftwareTT.Checked;
		}

		#endregion

		private string JoinSelectedItems()
		{
			StringBuilder sb = new StringBuilder();

			bool first = true;

			foreach (string s in lbProprietaDescrizione.SelectedItems)
			{
				if (!first)
					sb.Append(",");

				sb.Append(s);

				if (first)
					first = false;
			}

			return sb.ToString();
		}

		private List<string> ListaProprietaDescrizione
		{
			get { return new List<string>(ParametriAutoComplete.DescriptionPropertyNames.Split(',')); }
		}

		public RicerchePlusDesignerInterface(RicerchePlusDesigner designer)
		{
			InitializeComponent();

			m_designer = designer;
		}

		private void ValidateNumericInput(object sender, CancelEventArgs e)
		{
			string value = (sender as TextBox).Text;

			if (!Regex.IsMatch(value, "[0-9]*"))
				e.Cancel = true;
		}

		private void RicerchePlusDesignerInterface_Load(object sender, EventArgs e)
		{
			if (m_designer.Component.Site != null)
			{
				//ITypeDiscoveryService service = (ITypeDiscoveryService)m_designer.Component.Site.GetService(typeof(ITypeDiscoveryService));

				try
				{
					Cursor = Cursors.WaitCursor;

					string assemblyLocation = @"Y:\VS2005\Init\Sigepro\Libraries\DllSigepro\SIGePro.Data\bin\Debug\SIGePro.Data.dll";
					AssemblyDefinition asmDef = AssemblyFactory.GetAssembly(assemblyLocation);
					m_types = asmDef.MainModule.Types;

					//ICollection is2 = service.GetTypes(typeof(object), false);
					/*
					m_types = new List<Type>(is2.Count);

					foreach (Type t in is2)
					{
						if (!t.IsInterface && !t.IsEnum)
						{
							m_types.Add(t);
						}
					}*/

					UpdateTypeList();
				}
				catch (Exception ex)
				{
					MessageBox.Show(ex.ToString());
				}
				finally
				{
					Cursor = Cursors.Default;
				}
			}
		}

		private void UpdateTypeList()
		{
			int selIdx = -1;

			cmbClasse.Items.Clear();

			for (int i = 0; i < m_types.Count; i++)
			{
				cmbClasse.Items.Add(m_types[i]);
				if (m_types[i].ToString() == ParametriAutoComplete.DataClassType)
					selIdx = i;
			}
/*
			m_types.ForEach(delegate(Type t)
				{
					int idx = cmbClasse.Items.Add(t);
					if (t.ToString() == ParametriAutoComplete.DataClassType)
						selIdx = idx;
				});
*/
			if (selIdx != -1)
				cmbClasse.SelectedIndex = selIdx;
		}

		private void cmbClasse_SelectedIndexChanged(object sender, EventArgs e)
		{
			if (cmbClasse.SelectedItem == null) return;

			cmbProprietaId.Items.Clear();
			lbProprietaDescrizione.Items.Clear();

			TypeDefinition t = (TypeDefinition)cmbClasse.SelectedItem;

			PropertyDefinitionCollection pdc = t.Properties;
			
			int selPid = -1;

			foreach (PropertyDefinition pd in pdc)
			{
				if ( pd.SetMethod == null ) continue;

				int pidIdx = cmbProprietaId.Items.Add(pd.Name);

				if (pd.Name == ParametriAutoComplete.TargetPropertyName)
					selPid = pidIdx;

				int pdeIdx = lbProprietaDescrizione.Items.Add(pd.Name);

				if (ListaProprietaDescrizione.Contains(pd.Name))
					lbProprietaDescrizione.SetSelected(pdeIdx, true);
			}

			cmbProprietaId.SelectedIndex = (selPid == -1) ? 0 : selPid;
		}


		#region gestori dei click sui bottoni
		private void cmdOk_Click(object sender, EventArgs e)
		{
			SyncParameters();

			this.DialogResult = DialogResult.OK;

			this.Close();
		}

		private void cmdCancel_Click(object sender, EventArgs e)
		{
			this.DialogResult = DialogResult.Cancel;

			this.Close();
		}
		#endregion

		private void cmdSelectLoadingImage_Click(object sender, EventArgs e)
		{
			m_designer.Component.Site.GetService(typeof(IWindowsFormsEditorService));

			string caption = "Selezionare un'immagine da utilizzare come icona per il caricamento";
			string filter = "*.gif";
			txtAutLoadingIcon.Text = UrlBuilder.BuildUrl(m_designer.Component, null, txtAutLoadingIcon.Text, caption, filter);
		}

		private void groupBox5_Enter(object sender, EventArgs e)
		{

		}

		private void cmdFiltraClassi_Click(object sender, EventArgs e)
		{
			try
			{
				using (FiltroClassiCtrl fcc = new FiltroClassiCtrl())
				{
					fcc.TipiDisponibili = m_types;

					if (fcc.ShowDialog() == DialogResult.OK)
						cmbClasse.SelectedItem = fcc.SelectedItem;
				}
			}
			catch (Exception ex)
			{
				MessageBox.Show(ex.Message);
			}
		}




	}
}