using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing.Design;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SIGePro.WebControls.Ajax
{
    [ControlValueProperty("Value")]
    [DefaultProperty("Value")]
    public partial class RicerchePlusCtrl : WebControl, INamingContainer
    {
        public event EventHandler ValueChanged;

        public bool AutoPostBack
        {
            get { return this.m_txtCodice.AutoPostBack; }
            set { this.m_txtCodice.AutoPostBack = value; }
        }

        private readonly TextBox m_txtCodice = new TextBox();
        private readonly TextBox m_txtDescrizione = new TextBox();
        private readonly AutoCompleteExtender m_autoComplete = new AutoCompleteExtender();

        public bool RicercaSoftwareTT
        {
            get { return this.m_autoComplete.RicercaSoftwareTT; }
            set { this.m_autoComplete.RicercaSoftwareTT = value; }
        }

        public bool ReadOnly
        {
            get { return this.m_txtCodice.ReadOnly; }
            set { this.m_autoComplete.ReadOnly = this.m_txtCodice.ReadOnly = this.m_txtDescrizione.ReadOnly = value; }
        }

        public string Software
        {
            get { return this.m_autoComplete.Software; }
            set { this.m_autoComplete.Software = this.Software; }
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
            get { return this.m_autoComplete.AutoSelect; }
            set { this.m_autoComplete.AutoSelect = value; }
        }

        public string CompletionListCssClass
        {
            get { return this.m_autoComplete.CompletionListCssClass; }
            set { this.m_autoComplete.CompletionListCssClass = value; }
        }

        public string CompletionListItemCssClass
        {
            get { return this.m_autoComplete.CompletionListItemCssClass; }
            set { this.m_autoComplete.CompletionListItemCssClass = value; }
        }

        public string CompletionListHighlightedItemCssClass
        {
            get { return this.m_autoComplete.CompletionListHighlightedItemCssClass; }
            set { this.m_autoComplete.CompletionListHighlightedItemCssClass = value; }
        }

        #region proprieta della casella descrizione
        public int ColonneDescrizione
        {
            get { return this.m_txtDescrizione.Columns; }
            set { this.m_txtDescrizione.Columns = value; }
        }

        public int MaxLengthDescrizione
        {
            get { return this.m_txtDescrizione.MaxLength; }
            set { this.m_txtDescrizione.MaxLength = value; }
        }
        #endregion

        #region proprieta della casella codice
        public int ColonneCodice
        {
            get { return this.m_txtCodice.Columns; }
            set { this.m_txtCodice.Columns = value; }
        }

        public int MaxLengthCodice
        {
            get { return this.m_txtCodice.MaxLength; }
            set { this.m_txtCodice.MaxLength = value; }
        }
        #endregion

        #region proprieta dell'autocomplete
        public int CompletionInterval
        {
            get { return this.m_autoComplete.CompletionInterval; }
            set { this.m_autoComplete.CompletionInterval = value; }
        }

        public int MinimumPrefixLength
        {
            get { return this.m_autoComplete.MinimumPrefixLength; }
            set { this.m_autoComplete.MinimumPrefixLength = value; }
        }

        public int CompletionSetCount
        {
            get { return this.m_autoComplete.CompletionSetCount; }
            set { this.m_autoComplete.CompletionSetCount = value; }
        }

        public string ServiceMethod
        {
            get { return this.m_autoComplete.ServiceMethod; }
            set { this.m_autoComplete.ServiceMethod = value; }
        }

        public string ServiceInitializeMethod
        {
            get { return this.m_autoComplete.ServiceInitializeMethod; }
            set { this.m_autoComplete.ServiceInitializeMethod = value; }
        }

        [UrlProperty]
        [Bindable(true),
        DefaultValue(""),
        Editor("System.Web.UI.Design.UrlEditor, System.Design, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a", typeof(UITypeEditor))]
        public string ServicePath
        {
            get { return this.m_autoComplete.ServicePath; }
            set { this.m_autoComplete.ServicePath = value; }
        }

        public string DataClassType
        {
            get { return this.m_autoComplete.DataClassType; }
            set { this.m_autoComplete.DataClassType = value; }
        }

        public string TargetPropertyName
        {
            get { return this.m_autoComplete.TargetPropertyName; }
            set { this.m_autoComplete.TargetPropertyName = value; }
        }

        public string DescriptionPropertyNames
        {
            get { return this.m_autoComplete.DescriptionPropertyNames; }
            set { this.m_autoComplete.DescriptionPropertyNames = value; }
        }


        public string LoadingIcon
        {
            get { return this.m_autoComplete.ImageLoadingIcon; }
            set { this.m_autoComplete.ImageLoadingIcon = value; }
        }

        public string BehaviorID
        {
            get { return this.m_autoComplete.BehaviorID; }
            set { this.m_autoComplete.BehaviorID = value; }
        }

        #endregion

        [Browsable(true),
        DefaultValue(""),
        DesignerSerializationVisibility(DesignerSerializationVisibility.Visible)]
        public string Value
        {
            get { return this.m_txtCodice.Text; }
            set { this.m_txtCodice.Text = value; }
        }


        [Browsable(true),
        DefaultValue(""),
        DesignerSerializationVisibility(DesignerSerializationVisibility.Visible)]
        public string Text
        {
            get { return this.m_txtDescrizione.Text; }
            set { this.m_txtDescrizione.Text = value; }
        }

        [Browsable(false)]
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

                this.m_txtCodice.Text = codice;
                this.m_txtDescrizione.Text = descrizione;
            }
        }


        public RicerchePlusCtrl()
        {
            this.m_txtCodice.ID = "ControlloId";
            this.m_txtDescrizione.ID = "ControlloDescrizione";
            this.m_autoComplete.ID = "AutoCompeteCtrl";

            this.m_autoComplete.FirstRowSelected = true;
            this.m_autoComplete.EnableCaching = false;
            this.m_autoComplete.AutoSelect = true;

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

            this.m_txtCodice.TextChanged += new EventHandler(this.CodiceChanged);

            this.Controls.Add(this.m_txtCodice);
            this.Controls.Add(this.m_txtDescrizione);
            this.Controls.Add(this.m_autoComplete);

        }

        private void CodiceChanged(object sender, EventArgs e)
        {
            if (ValueChanged != null)
                ValueChanged(sender, e);
        }


        protected override void OnLoad(EventArgs e)
        {
            base.OnLoad(e);

            this.m_autoComplete.TargetControlID = this.m_txtCodice.ID;
            this.m_autoComplete.DescriptionControlID = this.m_txtDescrizione.ID;
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            this.m_autoComplete.InitParams = this.InitParams;
        }

        protected override void Render(HtmlTextWriter writer)
        {
            writer.AddAttribute("class", "RicerchePlus");
            writer.RenderBeginTag(HtmlTextWriterTag.Div);

            this.m_txtCodice.RenderControl(writer);
            this.m_txtDescrizione.RenderControl(writer);
            this.m_autoComplete.RenderControl(writer);

            writer.RenderEndTag();
        }

        public static new string[] CreateResultList(List<KeyValuePair<string, string>> foundItems)
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
            return this.m_txtCodice.ClientID;
        }
    }
}
