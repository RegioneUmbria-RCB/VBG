using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI.WebControls;
using Init.Utils.Web.UI;
using System.Web.UI;
using System.ComponentModel;
using System.Reflection;

namespace Init.Utils.Web.UI
{
	[DefaultProperty("Value")]
	[ControlValueProperty("Value")]
	public abstract class LabeledControlBase : WebControl, INamingContainer
	{
		/// <summary>
		/// Evento generato quando il valore del controllo contenuto viene modificato
		/// </summary>
		public event EventHandler ValueChanged;

		Div m_div = new Div();
		Label m_label = new Label();
		WebControl m_control = null;
		HelpIcon m_imgDescrizione = new HelpIcon();

		public override string CssClass
		{
			get
			{
				return m_div.CssClass;
			}
			set
			{
				m_div.CssClass = value;
			}
		}

		[Browsable(true),
		DefaultValue("Descrizione"),
		DesignerSerializationVisibility(DesignerSerializationVisibility.Visible)]
		public string Descrizione
		{
			get { return m_label.Text == String.Empty ? "Descrizione" : m_label.Text; }
			set { m_label.Text = value; }
		}


		protected T GetInnerControl<T>() where T : WebControl
		{
			return (T)m_control;
		}

		[Browsable(true),
		DefaultValue(""),
		DesignerSerializationVisibility(DesignerSerializationVisibility.Visible)]
		public string Value
		{
			get
			{
				PropertyInfo pi = GetValueProperty();

				if (pi == null) return String.Empty;

				object obj = pi.GetValue(m_control, null);

				return obj == null ? String.Empty : obj.ToString();
			}

			set
			{
				PropertyInfo pi = GetValueProperty();

				if (pi != null)
					pi.SetValue(m_control, value, null);
			}
		}

		private PropertyInfo GetValueProperty()
		{
			Type type = m_control.GetType();
			ControlValuePropertyAttribute[] attrb = (ControlValuePropertyAttribute[])type.GetCustomAttributes(typeof(ControlValuePropertyAttribute), true);
			if (attrb != null && attrb.Length > 0)
			{
				return type.GetProperty(attrb[0].Name);
			}

			return null;
		}

		private EventInfo GetDefaultEvent()
		{
			Type type = m_control.GetType();
			DefaultEventAttribute[] attrb = (DefaultEventAttribute[])type.GetCustomAttributes(typeof(DefaultEventAttribute), true);
			if (attrb != null && attrb.Length > 0)
			{
				return type.GetEvent(attrb[0].Name);
			}

			return null;
		}
		/*
		public override string ID
		{
			get
			{
				return base.ID;
			}
			set
			{
				base.ID = value;

				m_div.ID = this.ID + "_div";
				m_label.ID = this.ID + "_labelCtrl";
				m_control.ID = this.ID + typeof(T).Name;
			}
		}
		*/
		[IDReferenceProperty(typeof(HelpDiv))]
		public string HelpControl
		{
			get { return m_imgDescrizione.HelpControl; }
			set { m_imgDescrizione.HelpControl = value; }
		}

		public override bool Visible
		{
			get
			{
				return base.Visible;
			}
			set
			{
				base.Visible = value;
				m_imgDescrizione.Visible = false;
			}
		}


		public LabeledControlBase()
		{
			m_control = CreateInnerControl();

			m_div.ID = "_div";
			m_label.ID = "_labelCtrl";
			m_control.ID = "_" + m_control.GetType().Name;

			m_imgDescrizione.ID = "_HelpIcon";

			EnsureChildControls();

			this.Init += new EventHandler(LabeledControl_Init);
		}

		protected abstract WebControl CreateInnerControl();

		protected override void CreateChildControls()
		{
			base.CreateChildControls();

			m_div.Controls.Add(m_label);
			m_div.Controls.Add(m_control);
			m_div.Controls.Add(m_imgDescrizione);

			this.Controls.Add(m_div);
		}

		void LabeledControl_Init(object sender, EventArgs e)
		{
			EventInfo ev = GetDefaultEvent();

			if (ev != null)
				ev.AddEventHandler(m_control, new EventHandler(ItemValueChanged));
		}


		void ItemValueChanged(object sender, EventArgs e)
		{
			if (this.ValueChanged != null)
				this.ValueChanged(this, e);
		}

		protected override void OnPreRender(EventArgs e)
		{
			EnsureChildControls();
			base.OnPreRender(e);
		}

		/// <summary>
		/// Effettua il rendering del controllo
		/// </summary>
		/// <param name="writer"></param>
		public override void RenderControl(System.Web.UI.HtmlTextWriter writer)
		{
			if (!this.Visible) return;

			m_label.AssociatedControlID = m_control.ID;
			//m_div.RenderControl(writer);
			base.RenderControl(writer);
		}

		public string InnerClientId
		{
			get { return m_control.ClientID; }
		}
	}
}
