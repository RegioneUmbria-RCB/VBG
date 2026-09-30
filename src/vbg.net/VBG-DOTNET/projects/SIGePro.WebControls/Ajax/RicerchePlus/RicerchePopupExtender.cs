using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI;
using AjaxControlToolkit;
using System.ComponentModel;
using System.Web.UI.Design;
using System.Drawing.Design;
using System.Web.UI.WebControls;
using System.Web.Script.Serialization;

[assembly: System.Web.UI.WebResource("SIGePro.WebControls.Ajax.RicerchePlus.RicerchePopupBehavior.js", "text/javascript")]

namespace SIGePro.WebControls.Ajax
{
	[ClientScriptResource("SIGePro.WebControls.Ajax.RicerchePopupBehavior", "SIGePro.WebControls.Ajax.RicerchePlus.RicerchePopupBehavior.js")]
	[TargetControlType(typeof(WebControl))]
	[RequiredScript(typeof(CommonToolkitScripts))]
	public partial class RicerchePopupExtender : ExtenderControlBase
	{
		[UrlProperty]
		[ExtenderControlProperty]
		[Bindable(true), 
		DefaultValue(""), 
		Editor("System.Web.UI.Design.UrlEditor, System.Design, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a", typeof(UITypeEditor))]
		[ClientPropertyName("popupUrl")]
		public virtual string PopupUrl
		{
			get { return GetPropertyValue("popupUrl", String.Empty); }
			set { SetPropertyValue("popupUrl", value); }
		}

		[ExtenderControlProperty]
		[IDReferenceProperty(typeof(TextBox))]
		[ClientPropertyName("descriptionControlID")]
		public string DescriptionControlID
		{
			get
			{
				return GetPropertyValue("descriptionControlID", "");
			}
			set
			{
				SetPropertyValue("descriptionControlID", value);
			}
		}


		[DefaultValue(false)]
		[ExtenderControlProperty]
		[ClientPropertyName("readOnly")]
		public bool ReadOnly
		{
			get { return GetPropertyValue("readOnly", false); }
			set { SetPropertyValue("readOnly", value); }
		}

		[DefaultValue(400)]
		[ExtenderControlProperty]
		[ClientPropertyName("popupWidth")]
		public int PopupWidth
		{
			get { return GetPropertyValue("popupWidth", 400); }
			set { SetPropertyValue("popupWidth", value); }
		}

		[DefaultValue(400)]
		[ExtenderControlProperty]
		[ClientPropertyName("popupHeight")]
		public int PopupHeight
		{
			get { return GetPropertyValue("popupHeight", 400); }
			set { SetPropertyValue("popupHeight", value); }
		}

		[DefaultValue(null)]
		public Dictionary<string, string> QuerystringArguments
		{
			get 
			{
				Dictionary<string, string> qsa = GetPropertyValue<Dictionary<string, string>>("QuerystringArguments", null);

				if (qsa == null)
				{
					qsa = new Dictionary<string, string>();
					SetPropertyValue("QuerystringArguments", qsa); 
				}

				return qsa;
			}
			set { SetPropertyValue("querystringParams", value); }
		}

		[ExtenderControlProperty]
		[ClientPropertyName("querystringParameters")]
		public string QuerystringParameters
		{
			get { return new JavaScriptSerializer().Serialize(QuerystringArguments); }
			set { QuerystringArguments = new JavaScriptSerializer().Deserialize<Dictionary<string, string>>(value); }
		}
	}
}
