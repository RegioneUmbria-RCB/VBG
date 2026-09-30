// (c) Copyright Microsoft Corporation.
// This source is subject to the Microsoft Permissive License.
// See http://www.microsoft.com/resources/sharedsource/licensingbasics/sharedsourcelicenses.mspx.
// All other rights reserved.

using AjaxControlToolkit;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Web;
using System.Web.Script.Serialization;
using System.Web.UI;
using System.Web.UI.WebControls;

[assembly: System.Web.UI.WebResource("SIGePro.WebControls.Ajax.AutoComplete.AutoCompleteBehavior.js", "text/javascript")]
[assembly: System.Web.UI.WebResource("SIGePro.WebControls.Ajax.PopupControl.PopupControlBehavior.js", "text/javascript")]

namespace SIGePro.WebControls.Ajax
{
    /// <summary>
    /// Extender that provides suggestions to fill input in a textbox.
    /// </summary>
    [Designer("SIGePro.WebControls.Ajax.AutoCompleteDesigner")]
    //[Designer(typeof(AutoCompleteCustomDesigner))]
    [ClientScriptResource("SIGePro.WebControls.Ajax.AutoCompleteBehavior", "SIGePro.WebControls.Ajax.AutoComplete.AutoCompleteBehavior.js")]
    [RequiredScript(typeof(CommonToolkitScripts))]
    [RequiredScript(typeof(PopupExtender))]
    [RequiredScript(typeof(TimerScript))]
    [RequiredScript(typeof(AnimationExtender))]
    [TargetControlType(typeof(TextBox))]
    [ToolboxItem("System.Web.UI.Design.WebControlToolboxItem, System.Design, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a")]
    [ToolboxBitmap(typeof(AutoCompleteExtender), "AutoComplete.AutoComplete.ico")]
    public class AutoCompleteExtender : AnimationExtenderControlBase
    {
        /// <summary>
        /// Minimum length of text before the webservice provides suggestions.
        /// </summary>
        [DefaultValue(3)]
        [ExtenderControlProperty]
        [NotifyParentProperty(true)]
        [ClientPropertyName("minimumPrefixLength")]
        public virtual int MinimumPrefixLength
        {
            get { return this.GetPropertyValue("MinimumPrefixLength", 3); }
            set { this.SetPropertyValue("MinimumPrefixLength", value); }
        }

        [DefaultValue(false)]
        [ExtenderControlProperty]
        [ClientPropertyName("readOnly")]
        public bool ReadOnly
        {
            get { return this.GetPropertyValue("readOnly", false); }
            set { this.SetPropertyValue("readOnly", value); }
        }

        [DefaultValue(false)]
        [ExtenderControlProperty]
        [ClientPropertyName("ricercaSoftwareTT")]
        public bool RicercaSoftwareTT
        {
            get { return this.GetPropertyValue("ricercaSoftwareTT", false); }
            set { this.SetPropertyValue("ricercaSoftwareTT", value); }
        }

        [DefaultValue("")]
        [ExtenderControlProperty]
        [ClientPropertyName("software")]
        public string Software
        {
            get
            {
                string software = this.GetPropertyValue<string>("software", null);

                if (String.IsNullOrEmpty(software))
                {
                    software = HttpContext.Current.Items["Software"].ToString();
                    this.SetPropertyValue("software", software);
                }

                return software;
            }
            set { this.SetPropertyValue("software", value); }
        }

        [DefaultValue(null)]
        public Dictionary<string, string> InitParams
        {
            get { return this.GetPropertyValue<Dictionary<string, string>>("dictionaryPropInternal", null); }
            set { this.SetPropertyValue("dictionaryPropInternal", value); }
        }

        [ExtenderControlProperty]
        [ClientPropertyName("dictionaryProp")]
        public string DictionaryProp
        {
            get { return new JavaScriptSerializer().Serialize(this.InitParams); }
            set { this.InitParams = new JavaScriptSerializer().Deserialize<Dictionary<string, string>>(value); }
        }

        /// <summary>
        /// Time in milliseconds when the timer will kick in to get suggestions using the web service. 
        /// </summary>
        [DefaultValue(1000)]
        [ExtenderControlProperty]
        [NotifyParentProperty(true)]
        [ClientPropertyName("completionInterval")]
        public virtual int CompletionInterval
        {
            get { return this.GetPropertyValue("CompletionInterval", 1000); }
            set { this.SetPropertyValue("CompletionInterval", value); }
        }

        /// <summary>
        /// Number of suggestions to be provided.
        /// </summary>
        [DefaultValue(10)]
        [ExtenderControlProperty]
        [NotifyParentProperty(true)]
        [ClientPropertyName("completionSetCount")]
        public virtual int CompletionSetCount
        {
            get { return this.GetPropertyValue("CompletionSetCount", 10); }
            set { this.SetPropertyValue("CompletionSetCount", value); }
        }

        /// <summary>
        /// ID of element that will serve as the completion list.
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlProperty]
        [ClientPropertyName("completionListElementID")]
        [IDReferenceProperty(typeof(WebControl))]
        [Obsolete("Instead of passing in CompletionListElementID, use the default flyout and style that using the CssClass properties.")]
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Naming", "CA1706:ShortAcronymsShouldBeUppercase", MessageId = "Member")]
        public virtual string CompletionListElementID
        {
            get { return this.GetPropertyValue("CompletionListElementID", String.Empty); }
            set { this.SetPropertyValue("CompletionListElementID", value); }
        }

        /// <summary>
        /// The web service method to be called.
        /// </summary>
        [DefaultValue("")]
        [RequiredProperty]
        [ExtenderControlProperty]
        [ClientPropertyName("serviceMethod")]
        public virtual string ServiceMethod
        {
            get { return this.GetPropertyValue("ServiceMethod", String.Empty); }
            set { this.SetPropertyValue("ServiceMethod", value); }
        }

        [DefaultValue("")]
        [ExtenderControlProperty]
        [ClientPropertyName("serviceInitializeMethod")]
        public virtual string ServiceInitializeMethod
        {
            get { return this.GetPropertyValue("ServiceInitializeMethod", String.Empty); }
            set { this.SetPropertyValue("ServiceInitializeMethod", value); }
        }



        /// <summary>
        /// The path to the web service that the extender will pull the 
        /// word\sentence completions from. If this is not provided, the 
        /// service method should be a page method. 
        /// </summary>
        [UrlProperty]
        [ExtenderControlProperty]
        [TypeConverter(typeof(ServicePathConverter))]
        [ClientPropertyName("servicePath")]
        public virtual string ServicePath
        {
            get { return this.GetPropertyValue("ServicePath", String.Empty); }
            set { this.SetPropertyValue("ServicePath", value); }
        }

        /// <summary>
        /// User/page specific context provided to an optional overload of the
        /// web method described by ServiceMethod/ServicePath.  If the context
        /// key is used, it should have the same signature with an additional
        /// parameter named contextKey of type string.
        /// </summary>
        [ExtenderControlProperty]
        [ClientPropertyName("token")]
        [DefaultValue(null)]
        public string Token
        {
            get
            {
                string token = this.GetPropertyValue<string>("Token", null);

                if (String.IsNullOrEmpty(token))
                {
                    token = HttpContext.Current.Items["Token"].ToString();
                    this.SetPropertyValue("Token", token);
                }

                return token;
            }
            set
            {
                this.SetPropertyValue("Token", value);
            }
        }

        /// <summary>
        /// Css Class that will be used to style the completion list flyout.
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlProperty]
        [ClientPropertyName("completionListCssClass")]
        public string CompletionListCssClass
        {
            get { return this.GetPropertyValue("CompletionListCssClass", ""); }
            set { this.SetPropertyValue("CompletionListCssClass", value); }
        }

        /// <summary>
        /// Css Class that will be used to style an item in the autocomplete list.
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlProperty]
        [ClientPropertyName("completionListItemCssClass")]
        public string CompletionListItemCssClass
        {
            get { return this.GetPropertyValue("CompletionListItemCssClass", ""); }
            set { this.SetPropertyValue("CompletionListItemCssClass", value); }
        }

        /// <summary>
        /// Css Class that will be used to style a highlighted item in the autocomplete list.
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlProperty]
        [ClientPropertyName("highlightedItemCssClass")]
        public string CompletionListHighlightedItemCssClass
        {
            get { return this.GetPropertyValue("CompletionListHighlightedItemCssClass", ""); }
            set { this.SetPropertyValue("CompletionListHighlightedItemCssClass", value); }
        }

        /// <summary>
        /// Flag to denote whether client side caching is enabled.
        /// </summary>
        [DefaultValue(true)]
        [ExtenderControlProperty]
        [ClientPropertyName("enableCaching")]
        public virtual bool EnableCaching
        {
            get { return this.GetPropertyValue("EnableCaching", true); }
            set { this.SetPropertyValue("EnableCaching", value); }
        }
        /// <summary>
        /// Gets or sets the character(s) used to separate words for autocomplete.
        /// </summary>
        [ExtenderControlProperty]
        [ClientPropertyName("delimiterCharacters")]
        public virtual string DelimiterCharacters
        {
            get { return this.GetPropertyValue("DelimiterCharacters", String.Empty); }
            set { this.SetPropertyValue("DelimiterCharacters", value); }
        }

        /// <summary>
        /// Determines if the First Row of the Search Results be selected by default
        /// </summary>
        [DefaultValue(false)]
        [ExtenderControlProperty]
        [ClientPropertyName("firstRowSelected")]
        public virtual bool FirstRowSelected
        {
            get { return this.GetPropertyValue("FirstRowSelected", false); }
            set { this.SetPropertyValue("FirstRowSelected", value); }
        }

        /// <summary>
        /// OnShow animation
        /// </summary>
        [ExtenderControlProperty]
        [ClientPropertyName("onShow")]
        [Browsable(false)]
        [DefaultValue(null)]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        public Animation OnShow
        {
            get { return this.GetAnimation(ref this._onShow, "OnShow"); }
            set { this.SetAnimation(ref this._onShow, "OnShow", value); }
        }
        private Animation _onShow;

        /// <summary>
        /// OnHide animation
        /// </summary>
        [ExtenderControlProperty]
        [ClientPropertyName("onHide")]
        [Browsable(false)]
        [DefaultValue(null)]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        public Animation OnHide
        {
            get { return this.GetAnimation(ref this._onHide, "OnHide"); }
            set { this.SetAnimation(ref this._onHide, "OnHide", value); }
        }
        private Animation _onHide;

        /// <summary>
        /// Handler to attach to the client-side populating event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("populating")]
        public string OnClientPopulating
        {
            get { return this.GetPropertyValue("OnClientPopulating", string.Empty); }
            set { this.SetPropertyValue("OnClientPopulating", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side populated event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("populated")]
        public string OnClientPopulated
        {
            get { return this.GetPropertyValue("OnClientPopulated", string.Empty); }
            set { this.SetPropertyValue("OnClientPopulated", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side showing event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("showing")]
        public string OnClientShowing
        {
            get { return this.GetPropertyValue("OnClientShowing", string.Empty); }
            set { this.SetPropertyValue("OnClientShowing", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side shown event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("shown")]
        public string OnClientShown
        {
            get { return this.GetPropertyValue("OnClientShown", string.Empty); }
            set { this.SetPropertyValue("OnClientShown", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side hiding event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("hiding")]
        public string OnClientHiding
        {
            get { return this.GetPropertyValue("OnClientHiding", string.Empty); }
            set { this.SetPropertyValue("OnClientHiding", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side hidden event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("hidden")]
        public string OnClientHidden
        {
            get { return this.GetPropertyValue("OnClientHidden", string.Empty); }
            set { this.SetPropertyValue("OnClientHidden", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side itemSelected event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("itemSelected")]
        public string OnClientItemSelected
        {
            get { return this.GetPropertyValue("OnClientItemSelected", string.Empty); }
            set { this.SetPropertyValue("OnClientItemSelected", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side itemOver event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("itemOver")]
        public string OnClientItemOver
        {
            get { return this.GetPropertyValue("OnClientItemOver", string.Empty); }
            set { this.SetPropertyValue("OnClientItemOver", value); }
        }

        /// <summary>
        /// Handler to attach to the client-side itemOut event
        /// </summary>
        [DefaultValue("")]
        [ExtenderControlEvent]
        [ClientPropertyName("itemOut")]
        public string OnClientItemOut
        {
            get { return this.GetPropertyValue("OnClientItemOut", string.Empty); }
            set { this.SetPropertyValue("OnClientItemOut", value); }
        }

        /// <summary>
        /// Convert server IDs into ClientIDs for animations
        /// </summary>
        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            this.ResolveControlIDs(this._onShow);
            this.ResolveControlIDs(this._onHide);

            if (!String.IsNullOrEmpty(this.ImageLoadingIcon))
                this.ImageLoadingIcon = this.ResolveClientUrl(this.ImageLoadingIcon);
        }

        /// <summary>
        /// Create a serialized JSON object representing a text/value pair that can
        /// be returned by the webservice.
        /// </summary>
        /// <param name="text">Text</param>
        /// <param name="value">Value</param>
        /// <returns>serialized JSON object representing the text/value pair</returns>
        public static string CreateAutoCompleteItem(string text, string value)
        {
            return new JavaScriptSerializer().Serialize(new Pair(text, value));
        }

        [ExtenderControlProperty]
        [IDReferenceProperty(typeof(TextBox))]
        public string DescriptionControlID
        {
            get
            {
                return this.GetPropertyValue("DescriptionControlID", "");
            }
            set
            {
                this.SetPropertyValue("DescriptionControlID", value);
            }
        }

        [ExtenderControlProperty]
        [ClientPropertyName("dataClassType")]
        public string DataClassType
        {
            get { return this.GetPropertyValue("DataClassType", String.Empty); }
            set { this.SetPropertyValue("DataClassType", value); }
        }

        [ExtenderControlProperty]
        [ClientPropertyName("targetPropertyName")]
        public string TargetPropertyName
        {
            get { return this.GetPropertyValue("TargetPropertyName", ""); }
            set { this.SetPropertyValue("TargetPropertyName", value); }
        }

        [ExtenderControlProperty]
        [ClientPropertyName("descriptionPropertyNames")]
        public string DescriptionPropertyNames
        {
            get { return this.GetPropertyValue("DescriptionPropertyNames", ""); }
            set { this.SetPropertyValue("DescriptionPropertyNames", value); }
        }


        [ExtenderControlProperty]
        [ClientPropertyName("imageLoadingIcon")]
        public string ImageLoadingIcon
        {
            get { return this.GetPropertyValue("imageLoadingIcon", ""); }
            set { this.SetPropertyValue("imageLoadingIcon", value); }
        }

        [ExtenderControlProperty]
        [ClientPropertyName("autoSelect")]
        public bool AutoSelect
        {
            get { return this.GetPropertyValue("autoSelect", false); }
            set { this.SetPropertyValue("autoSelect", value); }
        }
    }
}