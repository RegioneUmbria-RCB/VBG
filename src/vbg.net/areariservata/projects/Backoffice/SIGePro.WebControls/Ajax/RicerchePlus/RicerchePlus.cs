using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI.WebControls;
using System.ComponentModel;
using SIGePro.WebControls.Ajax.RicerchePlus.Designer;
using System.Web.UI;
using System.Web.Script.Serialization;
using System.Drawing.Design;

namespace SIGePro.WebControls.Ajax
{
	[Designer(typeof(RicerchePlusDesigner))]
	[ControlValueProperty("Value")]
	[DefaultProperty("Value")]
	public partial class RicerchePlusCtrl : WebControl, INamingContainer
	{
		public event EventHandler ValueChanged;

		public bool AutoPostBack
		{
			get { return m_txtCodice.AutoPostBack; }
			set { m_txtCodice.AutoPostBack = value; }
		}


		TextBox m_txtCodice = new TextBox();
		TextBox m_txtDescrizione = new TextBox();
		AutoCompleteExtender m_autoComplete = new AutoCompleteExtender();

		public bool RicercaSoftwareTT
		{
			get { return m_autoComplete.RicercaSoftwareTT; }
			set { m_autoComplete.RicercaSoftwareTT = value; }
		}

		public bool ReadOnly
		{
			get { return m_txtCodice.ReadOnly; }
			set { m_autoComplete.ReadOnly = m_txtCodice.ReadOnly = m_txtDescrizione.ReadOnly = value; }
		}

		public string Software
		{
			get { return m_autoComplete.Software; }
			set { m_autoComplete.Software = Software; }
		}

		[Browsable(false)]
		public Dictionary<string, string> InitParams
		{
			get 
			{ 
				object o = this.ViewState["InitParams"];

				if (o == null)
				{
					o = this.ViewState["InitParams"] = new Dictionary<string, string>();
				}

				return (Dictionary<string, string>)o;
			}
			set { this.ViewState["InitParams"] = value; }
		}

		public bool AutoSelect
		{
			get { return m_autoComplete.AutoSelect; }
			set { m_autoComplete.AutoSelect = value; }
		}

		public string CompletionListCssClass
		{
			get { return m_autoComplete.CompletionListCssClass; }
			set { m_autoComplete.CompletionListCssClass = value; }
		}

		public string CompletionListItemCssClass
		{
			get { return m_autoComplete.CompletionListItemCssClass; }
			set { m_autoComplete.CompletionListItemCssClass = value; }
		}

		public string CompletionListHighlightedItemCssClass
		{
			get { return m_autoComplete.CompletionListHighlightedItemCssClass; }
			set { m_autoComplete.CompletionListHighlightedItemCssClass = value; }
		}

		#region proprieta della casella descrizione
		public int ColonneDescrizione
		{
			get { return m_txtDescrizione.Columns; }
			set { m_txtDescrizione.Columns = value; }
		}

		public int MaxLengthDescrizione
		{
			get { return m_txtDescrizione.MaxLength; }
			set { m_txtDescrizione.MaxLength = value; }
		}
		#endregion

		#region proprieta della casella codice
		public int ColonneCodice
		{
			get { return m_txtCodice.Columns; }
			set { m_txtCodice.Columns = value; }
		}

		public int MaxLengthCodice
		{
			get { return m_txtCodice.MaxLength; }
			set { m_txtCodice.MaxLength = value; }
		}
		#endregion

		#region proprieta dell'autocomplete
		public int CompletionInterval
		{
			get { return m_autoComplete.CompletionInterval; }
			set { m_autoComplete.CompletionInterval = value; }
		}

		public int MinimumPrefixLength
		{
			get { return m_autoComplete.MinimumPrefixLength; }
			set { m_autoComplete.MinimumPrefixLength = value; }
		}

		public int CompletionSetCount
		{
			get { return m_autoComplete.CompletionSetCount; }
			set { m_autoComplete.CompletionSetCount = value; }
		}

		public string ServiceMethod
		{
			get { return m_autoComplete.ServiceMethod; }
			set { m_autoComplete.ServiceMethod = value; }
		}

		public string ServiceInitializeMethod
		{
			get { return m_autoComplete.ServiceInitializeMethod; }
			set { m_autoComplete.ServiceInitializeMethod = value; }
		}

		[UrlProperty]
		[Bindable(true),
		DefaultValue(""),
		Editor("System.Web.UI.Design.UrlEditor, System.Design, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a", typeof(UITypeEditor))]
		public string ServicePath
		{
			get { return m_autoComplete.ServicePath; }
			set { m_autoComplete.ServicePath = value; }
		}

		public string DataClassType
		{
			get { return m_autoComplete.DataClassType; }
			set { m_autoComplete.DataClassType = value; }
		}

		public string TargetPropertyName
		{
			get { return m_autoComplete.TargetPropertyName; }
			set { m_autoComplete.TargetPropertyName = value; }
		}

		public string DescriptionPropertyNames
		{
			get { return m_autoComplete.DescriptionPropertyNames; }
			set { m_autoComplete.DescriptionPropertyNames = value; }
		}


		public string LoadingIcon
		{
			get { return m_autoComplete.ImageLoadingIcon; }
			set { m_autoComplete.ImageLoadingIcon = value; }
		}

		public string BehaviorID
		{
			get { return m_autoComplete.BehaviorID; }
			set { m_autoComplete.BehaviorID = value; }
		}

		#endregion

		[Browsable(true),
		DefaultValue(""),
		DesignerSerializationVisibility(DesignerSerializationVisibility.Visible)]
		public string Value
		{
			get { return m_txtCodice.Text; }
			set { m_txtCodice.Text = value; }
		}


		[Browsable(true),
		DefaultValue(""),
		DesignerSerializationVisibility(DesignerSerializationVisibility.Visible)]
		public string Text
		{
			get { return m_txtDescrizione.Text; }
			set { m_txtDescrizione.Text = value; }
		}

		[Browsable(false )]
		public Object Class
		{
			set 
			{
				string codice = String.Empty;
				string descrizione = String.Empty;

				if (value != null)
				{
					codice = TypeDescriptor.GetProperties(value)[this.TargetPropertyName].GetValue(value).ToString();
					descrizione = value.ToString();
				}

				m_txtCodice.Text = codice;
				m_txtDescrizione.Text = descrizione;
			}
		}


		public RicerchePlusCtrl()
		{
			m_txtCodice.ID = "ControlloId";
			m_txtDescrizione.ID = "ControlloDescrizione";
			m_autoComplete.ID = "AutoCompeteCtrl";

			m_autoComplete.FirstRowSelected = true;
			m_autoComplete.EnableCaching = false;
			m_autoComplete.AutoSelect = true;

			//m_txtDescrizione.ReadOnly = true;

			this.CompletionListCssClass = "RicerchePlusLista";
			this.CompletionListItemCssClass = "RicerchePlusElementoLista";
			this.CompletionListHighlightedItemCssClass = "RicerchePlusElementoSelezionatoLista";

			this.CompletionInterval = 300;
			this.CompletionSetCount = 1;

			this.ServiceMethod = "GetCompletionList";
			this.ServicePath = "~/WebServices/WsSiGePro/RicerchePlus.asmx";

			this.LoadingIcon = "~/Images/ajaxload.gif";

			/*if (m_autoComplete.InitParams == null)
				m_autoComplete.InitParams = new Dictionary<string, string>();
			*/

			m_txtCodice.TextChanged += new EventHandler(CodiceChanged);

			this.Controls.Add(m_txtCodice);
			this.Controls.Add(m_txtDescrizione);
			this.Controls.Add(m_autoComplete);

		}

		void CodiceChanged(object sender, EventArgs e)
		{
			if (ValueChanged != null)
				ValueChanged(sender, e);
		}

		
		protected override void OnLoad(EventArgs e)
		{
			base.OnLoad(e);

			m_autoComplete.TargetControlID = m_txtCodice.ID;
			m_autoComplete.DescriptionControlID = m_txtDescrizione.ID;
		}

		protected override void OnPreRender(EventArgs e)
		{
			base.OnPreRender(e);

			m_autoComplete.InitParams = this.InitParams;
		}

		protected override void Render(HtmlTextWriter writer)
		{
			writer.AddAttribute("class", "RicerchePlus");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
			
			m_txtCodice.RenderControl(writer);
			m_txtDescrizione.RenderControl(writer);
			m_autoComplete.RenderControl(writer);

			writer.RenderEndTag();
		}

		public static new string[] CreateResultList(List<KeyValuePair<string,string>> foundItems)
		{
			List<string> list = new List<string>(foundItems.Count);

			for (int i = 0; i < foundItems.Count; i++)
				list.Add(AutoCompleteExtender.CreateAutoCompleteItem(foundItems[i].Value, foundItems[i].Key));

			return list.ToArray();
		}

		public static new string[] CreateErrorResult(Exception ex)
		{
			List<string> list = new List<string>(1);

			list.Add(ex.Message);

			return list.ToArray();
		}


		internal string ClientIdTxtCodice()
		{
			return m_txtCodice.ClientID;
		}
	}
}
