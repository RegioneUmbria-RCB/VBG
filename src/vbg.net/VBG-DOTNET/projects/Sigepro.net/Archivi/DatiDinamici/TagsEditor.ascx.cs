using System;
using System.Collections.Generic;
using System.Linq;

namespace Sigepro.net.Archivi.DatiDinamici
{
    public class TagsSavedEventArgs : EventArgs
    {
        public readonly int IdRiga;
        public readonly string Tags;

        public TagsSavedEventArgs(int idRiga, string tags)
        {
            this.IdRiga = idRiga;
            this.Tags = tags;
        }
    }

    public partial class TagsEditor : System.Web.UI.UserControl
    {
        public string Tags { get { return this.ViewState["tags"]?.ToString() ?? ""; } set { this.ViewState["tags"] = this.TagsAttuali = value; } }
        public string DataListId { get { return this.ViewState[nameof(this.DataListId)]?.ToString() ?? ""; } set { this.ViewState[nameof(this.DataListId)] = this.TagsAttuali = value; } }
        private string TagsAttuali { get { return this.ViewState["TagsAttuali"]?.ToString() ?? ""; } set { this.ViewState["TagsAttuali"] = value; } }

        public int IdRiga
        {
            get
            {
                var val = this.ViewState["IdRiga"];

                if (val == null)
                    throw new ArgumentNullException(nameof(this.IdRiga));

                return Convert.ToInt32(val);
            }
            set { this.ViewState["IdRiga"] = value; }
        }

        public bool ShowModal { get { return this.ViewState["showModal"] != null && (bool)this.ViewState["showModal"]; } set { this.ViewState["showModal"] = value; } }



        public delegate void TagsSavedDelegate(object sender, TagsSavedEventArgs e);
        public event TagsSavedDelegate TagsSaved;


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.rptTagsList.DataSource = this.ParseTags();
            this.rptTagsList.DataBind();
        }

        private void RebindList()
        {
            this.rptTagsEditor.DataSource = this.ParseTags();
            this.rptTagsEditor.DataBind();
        }

        protected void btnEditTags_Click(object sender, EventArgs e)
        {
            this.ShowModal = true;

            this.txtNuovoTag.DataListId = this.DataListId;

            this.RebindList();
        }

        private List<string> ParseTags()
        {
            return this.TagsAttuali.Split(new[] { ',' }, StringSplitOptions.RemoveEmptyEntries)
                .Select(x => x.Trim())
                .ToList();
        }

        protected void bmEdit_OkClicked(object sender, EventArgs e)
        {
            this.ShowModal = false;

            this.TagsSaved.Invoke(this, new TagsSavedEventArgs(this.IdRiga, this.TagsAttuali));
        }

        protected void bmEdit_KoClicked(object sender, EventArgs e)
        {
            this.ShowModal = false;
            this.txtNuovoTag.Value = "";
            this.TagsAttuali = this.Tags;
        }

        protected void rptTags_ItemCommand(object source, System.Web.UI.WebControls.RepeaterCommandEventArgs e)
        {
            if (e.CommandName != "Delete")
            {
                return;
            }

            var tag = e.CommandArgument.ToString();
            var tags = this.ParseTags();
            tags.Remove(tag);
            this.TagsAttuali = String.Join(",", tags);

            this.RebindList();
        }

        protected void cmdAggiungi_Click(object sender, EventArgs e)
        {
            if (!String.IsNullOrEmpty(this.txtNuovoTag.Value))
            {
                var tags = this.ParseTags();
                var valore = this.txtNuovoTag.Value.ToUpper().Trim();

                if (!tags.Contains(valore))
                {
                    tags.Add(valore);
                    tags.Sort();
                    this.TagsAttuali = String.Join(",", tags);
                    this.txtNuovoTag.Value = "";
                }

                this.RebindList();
            }
        }
    }
}