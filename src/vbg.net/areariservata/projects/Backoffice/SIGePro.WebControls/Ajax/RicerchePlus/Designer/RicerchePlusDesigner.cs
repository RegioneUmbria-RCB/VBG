using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel.Design;
using System.Web.UI.Design;
using System.ComponentModel;

namespace SIGePro.WebControls.Ajax.RicerchePlus.Designer
{
	public partial class RicerchePlusDesigner : ControlDesigner
	{
		#region classi utilizzate per valorizzare i controlli
		/// <summary>
		/// Proprietà dei controlli testo
		/// </summary>
		public class ParametriControlloTesto
		{
			private int m_columns;

			public int Columns
			{
				get { return m_columns; }
				set { m_columns = value; }
			}

			private int m_maxLength;

			public int MaxLength
			{
				get { return m_maxLength; }
				set { m_maxLength = value; }
			}
		}

		/// <summary>
		/// Parametri del controllo AutoComplete
		/// </summary>
		public class ParametriControlloAutoComplete
		{
			private int m_completionInterval;

			public int CompletionInterval
			{
				get { return m_completionInterval; }
				set { m_completionInterval = value; }
			}

			private int m_minimumPrefixLength;

			public int MinimumPrefixLength
			{
				get { return m_minimumPrefixLength; }
				set { m_minimumPrefixLength = value; }
			}

			private int m_completionSetCount;

			public int CompletionSetCount
			{
				get { return m_completionSetCount; }
				set { m_completionSetCount = value; }
			}

			private string m_serviceMethod;

			public string ServiceMethod
			{
				get { return m_serviceMethod; }
				set { m_serviceMethod = value; }
			}

			private string m_servicePath;

			public string ServicePath
			{
				get { return m_servicePath; }
				set { m_servicePath = value; }
			}

			private string m_dataClassType;

			public string DataClassType
			{
				get { return m_dataClassType; }
				set { m_dataClassType = value; }
			}

			private string m_targetPropertyName;

			public string TargetPropertyName
			{
				get { return m_targetPropertyName; }
				set { m_targetPropertyName = value; }
			}

			private string m_descriptionPropertyNames;

			public string DescriptionPropertyNames
			{
				get { return m_descriptionPropertyNames; }
				set { m_descriptionPropertyNames = value; }
			}

			private string m_loadingIcon;

			public string LoadingIcon
			{
				get { return m_loadingIcon; }
				set { m_loadingIcon = value; }
			}

			private string m_completionListCssClass;

			public string CompletionListCssClass
			{
				get { return m_completionListCssClass; }
				set { m_completionListCssClass = value; }
			}

			private string m_completionListItemCssClass;

			public string CompletionListItemCssClass
			{
				get { return m_completionListItemCssClass; }
				set { m_completionListItemCssClass = value; }
			}

			private string m_completionListHighlightedItemCssClass;

			public string CompletionListHighlightedItemCssClass
			{
				get { return m_completionListHighlightedItemCssClass; }
				set { m_completionListHighlightedItemCssClass = value; }
			}

			private bool m_autoSelect;

			public bool AutoSelect
			{
				get { return m_autoSelect; }
				set { m_autoSelect = value; }
			}

			private bool m_ricercaSoftwareTT;

			public bool RicercaSoftwareTT
			{
				get { return m_ricercaSoftwareTT; }
				set { m_ricercaSoftwareTT = value; }
			}

		}
		#endregion

		private DesignerActionListCollection _actionLists = null;

		// Return a custom ActionList collection
		public override DesignerActionListCollection ActionLists
		{
			get
			{
				if (_actionLists == null)
				{
					_actionLists = new DesignerActionListCollection();
					_actionLists.AddRange(base.ActionLists);

					// Add a custom DesignerActionList
					_actionLists.Add(new ActionList(this));
				}
				return _actionLists;
			}
		}

		public class ActionList : DesignerActionList
		{
			private RicerchePlusDesigner _parent;
			private DesignerActionItemCollection _items;

			// Constructor
			public ActionList(RicerchePlusDesigner parent)
				: base(parent.Component)
			{
				_parent = parent;
			}

			// Create the ActionItem collection and add one command
			public override DesignerActionItemCollection GetSortedActionItems()
			{
				if (_items == null)
				{
					_items = new DesignerActionItemCollection();
					_items.Add(new DesignerActionMethodItem(this, "ImpostaProprietaClasse", "Imposta le proprietà dell'oggetto", true));
				}
				return _items;
			}




			private void ImpostaProprietaClasse()
			{
				// Get a reference to the parent designer's associated control
				RicerchePlusCtrl ctl = (RicerchePlusCtrl)_parent.Component;

				ParametriControlloTesto parametriId = new ParametriControlloTesto();
				parametriId.Columns = ctl.ColonneCodice;
				parametriId.MaxLength = ctl.MaxLengthCodice;

				ParametriControlloTesto parametriDescrizione = new ParametriControlloTesto();
				parametriDescrizione.Columns = ctl.ColonneDescrizione;
				parametriDescrizione.MaxLength = ctl.MaxLengthDescrizione;

				ParametriControlloAutoComplete parametriAutoCpl = new ParametriControlloAutoComplete();
				parametriAutoCpl.CompletionInterval = ctl.CompletionInterval;
				parametriAutoCpl.CompletionSetCount = ctl.CompletionSetCount;
				parametriAutoCpl.DataClassType = ctl.DataClassType;
				parametriAutoCpl.DescriptionPropertyNames = ctl.DescriptionPropertyNames;
				parametriAutoCpl.MinimumPrefixLength = ctl.MinimumPrefixLength;
				parametriAutoCpl.ServiceMethod = ctl.ServiceMethod;
				parametriAutoCpl.ServicePath = ctl.ServicePath;
				parametriAutoCpl.TargetPropertyName = ctl.TargetPropertyName;
				parametriAutoCpl.LoadingIcon = ctl.LoadingIcon;
				parametriAutoCpl.CompletionListCssClass = ctl.CompletionListCssClass;
				parametriAutoCpl.CompletionListHighlightedItemCssClass = ctl.CompletionListHighlightedItemCssClass;
				parametriAutoCpl.CompletionListItemCssClass = ctl.CompletionListItemCssClass;
				parametriAutoCpl.AutoSelect = ctl.AutoSelect;
				parametriAutoCpl.RicercaSoftwareTT = ctl.RicercaSoftwareTT;

				using (RicerchePlusDesignerInterface frm = new RicerchePlusDesignerInterface(_parent))
				{
					frm.ParametriId = parametriId;
					frm.ParametriDescrizione = parametriDescrizione;
					frm.ParametriAutoComplete = parametriAutoCpl;

					if (frm.ShowDialog() == System.Windows.Forms.DialogResult.OK)
					{
						PropertyDescriptorCollection pdc = TypeDescriptor.GetProperties(ctl);

						pdc["ColonneCodice"].SetValue( ctl , frm.ParametriId.Columns );
						pdc["MaxLengthCodice"].SetValue( ctl , frm.ParametriId.MaxLength );

						pdc["ColonneDescrizione"].SetValue( ctl , frm.ParametriDescrizione.Columns );
						pdc["MaxLengthDescrizione"].SetValue( ctl , frm.ParametriDescrizione.MaxLength );

						pdc["CompletionInterval"].SetValue( ctl , frm.ParametriAutoComplete.CompletionInterval );
						pdc["CompletionSetCount"].SetValue( ctl , frm.ParametriAutoComplete.CompletionSetCount );
						pdc["DataClassType"].SetValue( ctl , frm.ParametriAutoComplete.DataClassType );
						pdc["DescriptionPropertyNames"].SetValue( ctl , frm.ParametriAutoComplete.DescriptionPropertyNames );
						pdc["MinimumPrefixLength"].SetValue( ctl , frm.ParametriAutoComplete.MinimumPrefixLength );
						pdc["ServiceMethod"].SetValue( ctl , frm.ParametriAutoComplete.ServiceMethod );
						pdc["ServicePath"].SetValue( ctl , frm.ParametriAutoComplete.ServicePath );
						pdc["TargetPropertyName"].SetValue( ctl , frm.ParametriAutoComplete.TargetPropertyName );
						pdc["LoadingIcon"].SetValue( ctl , frm.ParametriAutoComplete.LoadingIcon );
						pdc["CompletionListCssClass"].SetValue(ctl, frm.ParametriAutoComplete.CompletionListCssClass);
						pdc["CompletionListHighlightedItemCssClass"].SetValue(ctl, frm.ParametriAutoComplete.CompletionListHighlightedItemCssClass);
						pdc["CompletionListItemCssClass"].SetValue(ctl, frm.ParametriAutoComplete.CompletionListItemCssClass);
						pdc["AutoSelect"].SetValue(ctl, frm.ParametriAutoComplete.AutoSelect);
						pdc["RicercaSoftwareTT"].SetValue(ctl, frm.ParametriAutoComplete.RicercaSoftwareTT);
					}
				}
			}


		}
	}
}
