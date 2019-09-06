using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing.Design;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public class DropDownListConAttributiInViewstate : System.Web.UI.WebControls.DropDownList
    {
        protected override object SaveViewState()
        {
            // create object array for Item count + 1
            object[] allStates = new object[this.Items.Count + 1];

            // the +1 is to hold the base info
            object baseState = base.SaveViewState();
            allStates[0] = baseState;

            Int32 i = 1;
            // now loop through and save each Style attribute for the List
            foreach (ListItem li in this.Items)
            {
                Int32 j = 0;
                string[][] attributes = new string[li.Attributes.Count][];
                foreach (string attribute in li.Attributes.Keys)
                {
                    attributes[j++] = new string[] { attribute, li.Attributes[attribute] };
                }
                allStates[i++] = attributes;
            }
            return allStates;
        }

        protected override void LoadViewState(object savedState)
        {
            if (savedState != null)
            {
                object[] myState = (object[])savedState;

                // restore base first
                if (myState[0] != null)
                    base.LoadViewState(myState[0]);

                Int32 i = 1;
                foreach (ListItem li in this.Items)
                {
                    // loop through and restore each style attribute
                    foreach (string[] attribute in (string[][])myState[i++])
                    {
                        li.Attributes[attribute[0]] = attribute[1];
                    }
                }
            }
        }
    }


    [ToolboxData("<{0}:DropDownList runat='server' />")]
    [DefaultEvent("SelectedIndexChanged"), Designer("System.Web.UI.Design.WebControls.ListControlDesigner, System.Design, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a"), ControlValueProperty("SelectedValue"), DataBindingHandler("System.Web.UI.Design.WebControls.ListControlDataBindingHandler, System.Design, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a"), ParseChildren(true, "Items")]
    public class DropDownList : BootstrapFormControl<DropDownListConAttributiInViewstate>
    {
        [DefaultValue(null), Editor("System.Web.UI.Design.WebControls.ListItemsCollectionEditor,System.Design, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a", typeof(UITypeEditor)), MergableProperty(false), PersistenceMode(PersistenceMode.InnerDefaultProperty)]
        public virtual ListItemCollection Items
        {
            get
            {
                return this._innerControl.Items;
            }
        }

        public string DataTextField
        {
            get { return this._innerControl.DataTextField; }
            set { this._innerControl.DataTextField = value; }
        }

        public bool AutoPostback
        {
            get { return this._innerControl.AutoPostBack; }
            set { this._innerControl.AutoPostBack = value; }
        }

        public string DataValueField
        {
            get { return this._innerControl.DataValueField; }
            set { this._innerControl.DataValueField = value; }
        }

        public string SelectedValue
        {
            get { return this._innerControl.SelectedValue; }
            set { this._innerControl.SelectedValue = value; }
        }

        public ListItem SelectedItem
        {
            get { return this._innerControl.SelectedItem; }
        }

        public int SelectedIndex
        {
            get { return this._innerControl.SelectedIndex; }
            set { this._innerControl.SelectedIndex = value; }
        }

        public object DataSource
        {
            get { return this._innerControl.DataSource; }
            set { this._innerControl.DataSource = value; }
        }

        public bool ReadOnly
        {
            get { object o = this.ViewState["ReadOnly"]; return o == null ? false : (bool)o; }
            set { this.ViewState["ReadOnly"] = value; }
        }



        protected override DropDownListConAttributiInViewstate CreateInnerControl()
        {
            return new DropDownListConAttributiInViewstate();
        }

        public override string Value
        {
            get { return this.SelectedValue; }
            set { this.SelectedValue = value; }
        }

        public void AddItem(string text, string value)
        {
            this.Items.Add(new ListItem(text, value));
        }

        internal void InsertItem(int position, string text, string value)
        {
            this.Items.Insert(position, new ListItem(text, value));
        }

        protected override void Render(HtmlTextWriter writer)
        {
            if (this.ReadOnly)
            {
                this._innerControl.Attributes.Add("readonly", "readonly");
            }

            base.Render(writer);

        }


    }
}
