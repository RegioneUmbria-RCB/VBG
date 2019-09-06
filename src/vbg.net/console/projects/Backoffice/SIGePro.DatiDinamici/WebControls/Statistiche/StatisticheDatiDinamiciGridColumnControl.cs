using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;
using System.Web.UI;
using System.ComponentModel;
using System.Reflection;

namespace Init.SIGePro.DatiDinamici.WebControls.Statistiche
{
	public partial class StatisticheDatiDinamiciGridColumnControl : WebControl
	{

		public delegate void ControlPropertiesRequiredDelegate(object sender, Dictionary<string, string> propList);
		public event ControlPropertiesRequiredDelegate ControlPropertiesRequired;

		public string ControlType;
		public string ControlValue;

		public bool ControlIsValid
		{
			get { object o = this.ViewState["ControlIsValid"]; return o == null ? false : (bool)o; }
			set { this.ViewState["ControlIsValid"] = value; }
		}


		public void Reload()
		{
			Control newControl;

			if (String.IsNullOrEmpty(ControlType))
				newControl = CreateDummyControl();
			else
				newControl = InstantiateControl(ControlType, ControlValue);

			if (newControl != null)
			{
				this.Controls.Clear();
				this.Controls.Add(newControl);
			}
		}

		private Control InstantiateControl(string controlType, string controlValue)
		{
			Type t = Type.GetType(ControlType);

			//Control ctrl = (Control)Activator.CreateInstance(t);
			Control ctrl = null;
			var q = from c in t.GetConstructors( BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance)
												 where c.GetParameters().Length == 0
												 select c;
			ConstructorInfo parameterlessCtor = q.FirstOrDefault();
			if (parameterlessCtor == null)
				return null;

			ctrl= (Control)parameterlessCtor.Invoke(null);

			ctrl.ID = "StatisticheDatiDinamiciGridColumnControl_control";

			// imposto le proprietà del controllo
			SetControlProperties(ctrl);


			// Imposto il valore
			if (controlValue != null)
			{
				ControlValuePropertyAttribute[] attr = (ControlValuePropertyAttribute[])ctrl.GetType().GetCustomAttributes(typeof(ControlValuePropertyAttribute), true);

				if (attr.Length == 1)
				{
					PropertyDescriptor pd = TypeDescriptor.GetProperties(ctrl).Find(attr[0].Name, true);
					pd.SetValue(ctrl, Convert.ChangeType(controlValue, pd.PropertyType));
				}
			}

			ControlIsValid = true;

			return ctrl;
		}

		private void SetControlProperties(Control ctrl)
		{
			Dictionary<string, string> properties = new Dictionary<string, string>();

			if (ControlPropertiesRequired != null)
				ControlPropertiesRequired(this, properties);

			// Loop sulle proprietà per valorizzarle
			PropertyDescriptorCollection propCollection = TypeDescriptor.GetProperties(ctrl);

			foreach (string key in properties.Keys)
			{
				try
				{
					propCollection[key].SetValue(ctrl, Convert.ChangeType(properties[key], propCollection[key].PropertyType));
				}
				catch (Exception /*ex*/)
				{

				}
			}
		}


		private Control CreateDummyControl()
		{
			Label lbl = new Label();
			lbl.Text = "Tipo non impostato";

			ControlIsValid = false;

			return lbl;
		}

		protected override object SaveViewState()
		{
			Pair pair = new Pair();
			pair.First = ControlType;
			pair.Second = base.SaveViewState();

			return pair;
		}

		protected override void LoadViewState(object savedState)
		{
			Pair pair = (Pair)savedState;

			if (pair.First != null)
				ControlType = pair.First.ToString();

			Reload();

			base.LoadViewState(pair.Second);
		}

		public string Value
		{
			get
			{
				if (!ControlIsValid) return String.Empty;

				Control ctrl = this.Controls[0];

				ControlValuePropertyAttribute[] attr = (ControlValuePropertyAttribute[])ctrl.GetType().GetCustomAttributes(typeof(ControlValuePropertyAttribute), true);

				if (attr.Length == 1)
				{
					PropertyDescriptor pd = TypeDescriptor.GetProperties(ctrl).Find(attr[0].Name, true);
					object obj = pd.GetValue(ctrl);

					return obj == null ? String.Empty : obj.ToString();
				}

				return String.Empty;
			}
		}
	}
}
