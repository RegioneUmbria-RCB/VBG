using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI;
using System.ComponentModel;
using System.Web.UI.WebControls;
using System.Drawing.Design;
using System.Web;

namespace SIGePro.WebControls.Ajax
{
	[ControlValueProperty("Value")]
	[DefaultProperty("Value")]
	public partial class RicerchePopup : WebControl , INamingContainer
	{
		TextBox m_txtCodice = new TextBox();
		TextBox m_txtDescrizione = new TextBox();
		RicerchePopupExtender m_extender = new RicerchePopupExtender();

		public event EventHandler ValueChanged;

		public bool AutoPostBack
		{
			get { return m_txtCodice.AutoPostBack; }
			set { m_txtCodice.AutoPostBack = value; }
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

		#region valori del controllo
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

		public KeyValuePair<string, string> Class
		{
			get { return new KeyValuePair<string, string>(m_txtCodice.Text, m_txtDescrizione.Text); }
			set { m_txtCodice.Text = value.Key; m_txtDescrizione.Text = value.Value; }
		}
		#endregion

		#region Dati del popup

		[UrlProperty]
		[Bindable(true),
		DefaultValue(""),
		Editor("System.Web.UI.Design.UrlEditor, System.Design, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a", typeof(UITypeEditor))]
		public virtual string PopupUrl
		{
			get { return m_extender.PopupUrl; }
			set { m_extender.PopupUrl = value; }
		}

		[Bindable(true)]
		public int PopupWidth
		{
			get { return m_extender.PopupWidth; }
			set { m_extender.PopupWidth = value; }
		}

		[Bindable(true)]
		public int PopupHeight
		{
			get { return m_extender.PopupHeight; }
			set { m_extender.PopupHeight = value; }
		}

		[Bindable(true)]
		public string Token
		{
			get 
			{ 
				object o = this.ViewState["Token"];

				if (o == null)
					o = HttpContext.Current.Items["Token"].ToString();
	
				return (string)o; 
			}
			set { this.ViewState["Token"] = value; }
		}


		public Dictionary<string, string> QuerystringArguments
		{
			get { return m_extender.QuerystringArguments; }
			set { m_extender.QuerystringArguments = value; }
		}
		#endregion



		public RicerchePopup()
		{
			m_txtCodice.ID = "ControlloId";
			m_txtDescrizione.ID = "ControlloDescrizione";
			m_extender.ID = "PopupExtender";

			m_txtCodice.TextChanged +=new EventHandler(CodiceChanged);

			this.Controls.Add(m_txtCodice);
			this.Controls.Add(m_txtDescrizione);
			this.Controls.Add(m_extender);
		}

		protected override void OnLoad(EventArgs e)
		{
			base.OnLoad(e);

			m_extender.TargetControlID = m_txtCodice.ID;
			m_extender.DescriptionControlID = m_txtDescrizione.ID;

			PreRender += new EventHandler(RicerchePopup_PreRender);
		}

		void RicerchePopup_PreRender(object sender, EventArgs e)
		{
			m_extender.QuerystringArguments["Token"] = Token;
		}

		protected override void Render(HtmlTextWriter writer)
		{
			writer.AddAttribute("class", "RicerchePlus");
			writer.RenderBeginTag(HtmlTextWriterTag.Div);

			m_txtCodice.RenderControl(writer);
			m_txtDescrizione.RenderControl(writer);
			m_extender.RenderControl(writer);

			writer.RenderEndTag();
		}

		void CodiceChanged(object sender, EventArgs e)
		{
			if (ValueChanged != null)
				ValueChanged(sender, e);
		}
	}
}
