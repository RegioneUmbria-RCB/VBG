using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.SIGePro.Manager.DTO.Interventi;
using Ninject;
using System;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;

using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
    /// <summary>
    /// Descrizione di riepilogo per RicercaAlberoProc.
    /// </summary>
    public class RicercaAlberoProc : SearchWebControl /*, INamingContainer*/
    {
        public class RigaRicerca : WebControl
        {
            public RigaRicerca() : base(System.Web.UI.HtmlTextWriterTag.Div)
            {

            }
        }

        private readonly DropDownList m_level0 = new DropDownList();
        private readonly DropDownList m_level1 = new DropDownList();
        private readonly DropDownList m_level2 = new DropDownList();
        private readonly DropDownList m_level3 = new DropDownList();
        private readonly DropDownList m_level4 = new DropDownList();
        private readonly DropDownList m_level5 = new DropDownList();

        private readonly Label m_label0 = new Label();
        private readonly Label m_label1 = new Label();
        private readonly Label m_label2 = new Label();
        private readonly Label m_label3 = new Label();
        private readonly Label m_label4 = new Label();
        private readonly Label m_label5 = new Label();

        private readonly RigaRicerca m_ctrl0 = new RigaRicerca();
        private readonly RigaRicerca m_ctrl1 = new RigaRicerca();
        private readonly RigaRicerca m_ctrl2 = new RigaRicerca();
        private readonly RigaRicerca m_ctrl3 = new RigaRicerca();
        private readonly RigaRicerca m_ctrl4 = new RigaRicerca();
        private readonly RigaRicerca m_ctrl5 = new RigaRicerca();

        private readonly Dictionary<DropDownList, List<RigaRicerca>> _controlsChain = new Dictionary<DropDownList, List<RigaRicerca>>();


        public string Value
        {
            get
            {
                var o = this.ViewState["Value"];
                return o == null ? "" : o.ToString();
            }
            set
            {
                this.EnsureChildControls();
                this.ViewState["Value"] = value;
            }
        }

        public override string ID
        {
            get { return base.ID; }
            set
            {
                this.EnsureChildControls();
                base.ID = value;

                this.m_level0.ID = value + "_level0";
                this.m_level1.ID = value + "_level1";
                this.m_level2.ID = value + "_level2";
                this.m_level3.ID = value + "_level3";
                this.m_level4.ID = value + "_level4";
                this.m_level5.ID = value + "_level5";

                this.m_level0.Style.Add("max-width", "600px");
                this.m_level1.Style.Add("max-width", "600px");
                this.m_level2.Style.Add("max-width", "600px");
                this.m_level3.Style.Add("max-width", "600px");
                this.m_level4.Style.Add("max-width", "600px");
                this.m_level5.Style.Add("max-width", "600px");

                this.m_ctrl0.ID = value + "_row0";
                this.m_ctrl1.ID = value + "_row1";
                this.m_ctrl2.ID = value + "_row2";
                this.m_ctrl3.ID = value + "_row3";
                this.m_ctrl4.ID = value + "_row4";
                this.m_ctrl5.ID = value + "_row5";

                this.m_label0.AssociatedControlID = this.m_level0.ID;
                this.m_label1.AssociatedControlID = this.m_level1.ID;
                this.m_label2.AssociatedControlID = this.m_level2.ID;
                this.m_label3.AssociatedControlID = this.m_level3.ID;
                this.m_label4.AssociatedControlID = this.m_level4.ID;
                this.m_label5.AssociatedControlID = this.m_level5.ID;
            }
        }

        [Inject]
        public IInterventiRepository _alberoProcRepository { get; set; }

        public RicercaAlberoProc()
        {
            FoKernelContainer.Inject(this);


            this.Init += new EventHandler(this.RicercaAlberoProc_Init);
            this.Load += new EventHandler(this.RicercaAlberoProc_Load);

            this.m_level0.AutoPostBack =
            this.m_level1.AutoPostBack =
            this.m_level2.AutoPostBack =
            this.m_level3.AutoPostBack =
            this.m_level4.AutoPostBack =
            this.m_level5.AutoPostBack = true;

            this.m_label0.Text = "Tipo intervento";
            this.m_label1.Text = "";
            this.m_label2.Text = "";
            this.m_label3.Text = "";
            this.m_label4.Text = "";
            this.m_label5.Text = "";

            this.m_ctrl0.Controls.Add(this.m_label0);
            this.m_ctrl0.Controls.Add(this.m_level0);
            this.m_ctrl1.Controls.Add(this.m_label1);
            this.m_ctrl1.Controls.Add(this.m_level1);
            this.m_ctrl2.Controls.Add(this.m_label2);
            this.m_ctrl2.Controls.Add(this.m_level2);
            this.m_ctrl3.Controls.Add(this.m_label3);
            this.m_ctrl3.Controls.Add(this.m_level3);
            this.m_ctrl4.Controls.Add(this.m_label4);
            this.m_ctrl4.Controls.Add(this.m_level4);
            this.m_ctrl5.Controls.Add(this.m_label5);
            this.m_ctrl5.Controls.Add(this.m_level5);

            this.Controls.Add(this.m_ctrl0);
            this.Controls.Add(this.m_ctrl1);
            this.Controls.Add(this.m_ctrl2);
            this.Controls.Add(this.m_ctrl3);
            this.Controls.Add(this.m_ctrl4);
            this.Controls.Add(this.m_ctrl5);

            this._controlsChain.Add(this.m_level0, new List<RigaRicerca>());
            this._controlsChain.Add(this.m_level1, new List<RigaRicerca>());
            this._controlsChain.Add(this.m_level2, new List<RigaRicerca>());
            this._controlsChain.Add(this.m_level3, new List<RigaRicerca>());
            this._controlsChain.Add(this.m_level4, new List<RigaRicerca>());
            this._controlsChain.Add(this.m_level5, new List<RigaRicerca>());

            this._controlsChain[this.m_level0].AddRange(new RigaRicerca[] { this.m_ctrl1, this.m_ctrl2, this.m_ctrl3, this.m_ctrl4, this.m_ctrl5 });
            this._controlsChain[this.m_level1].AddRange(new RigaRicerca[] { this.m_ctrl2, this.m_ctrl3, this.m_ctrl4, this.m_ctrl5 });
            this._controlsChain[this.m_level2].AddRange(new RigaRicerca[] { this.m_ctrl3, this.m_ctrl4, this.m_ctrl5 });
            this._controlsChain[this.m_level3].AddRange(new RigaRicerca[] { this.m_ctrl4, this.m_ctrl5 });
            this._controlsChain[this.m_level4].AddRange(new RigaRicerca[] { this.m_ctrl5 });
            this._controlsChain[this.m_level5].AddRange(new RigaRicerca[] { });

        }

        private void RicercaAlberoProc_Init(object sender, EventArgs e)
        {

            this.m_level0.SelectedIndexChanged += new EventHandler(this.ItemSelectedIndexChanged);
            this.m_level1.SelectedIndexChanged += new EventHandler(this.ItemSelectedIndexChanged);
            this.m_level2.SelectedIndexChanged += new EventHandler(this.ItemSelectedIndexChanged);
            this.m_level3.SelectedIndexChanged += new EventHandler(this.ItemSelectedIndexChanged);
            this.m_level4.SelectedIndexChanged += new EventHandler(this.ItemSelectedIndexChanged);
            this.m_level5.SelectedIndexChanged += new EventHandler(this.ItemSelectedIndexChanged);
        }

        private void ItemSelectedIndexChanged(object sender, EventArgs e)
        {
            var ddl = (DropDownList)sender;
            var controlsChainItem = this._controlsChain[ddl];
            var nextControl = controlsChainItem.Count > 0 ? controlsChainItem[0] : (RigaRicerca)null;


            if (!String.IsNullOrEmpty(ddl.SelectedValue))
            {
                var valore = ddl.SelectedValue;

                if (!this.BindDropDown(nextControl, this.FindChilds(Convert.ToInt32(valore))))
                    this.Value = valore;
                else
                    this.Value = "";

            }
            else
            {
                this.HideDropDown(nextControl);
            }

            for (var i = 1; i < controlsChainItem.Count; i++)
            {
                this.HideDropDown(controlsChainItem[i]);
            }
        }


        private void RicercaAlberoProc_Load(object sender, EventArgs e)
        {
            if (this.m_level0.Items.Count == 0)
            {
                this.BindDropDown(this.m_ctrl0, this.FindChilds(-1));

                this._controlsChain[this.m_level0].ForEach(x => this.HideDropDown(x));
            }
        }


        private bool BindDropDown(RigaRicerca riga, IEnumerable<InterventoDto> rows)
        {
            if (riga == null)
                return true;

            DropDownList dropDown = riga.Controls[1] as DropDownList;

            if (rows != null && rows.Any())
            {
                DataTable tmpDt = new DataTable();
                DataColumn codice = new DataColumn("SC_ID", typeof(int));
                DataColumn descrizione = new DataColumn("SC_DESCRIZIONE", typeof(string));

                tmpDt.Columns.Add(codice);
                tmpDt.Columns.Add(descrizione);

                foreach (var el in rows)
                {
                    DataRow newRow = tmpDt.NewRow();

                    newRow["SC_ID"] = el.Codice;
                    newRow["SC_DESCRIZIONE"] = el.Descrizione;

                    tmpDt.Rows.Add(newRow);
                }

                dropDown.DataSource = tmpDt;
                dropDown.DataTextField = "SC_DESCRIZIONE";
                dropDown.DataValueField = "SC_ID";
                dropDown.DataBind();

                dropDown.Items.Insert(0, new ListItem("Selezionare...", ""));
                riga.Visible = true;


                return true;
            }

            return false;
        }

        private void HideDropDown(RigaRicerca riga)
        {
            DropDownList dropDown = riga.Controls[1] as DropDownList;

            dropDown.SelectedValue = null;
            dropDown.DataSource = null;
            dropDown.DataBind();

            riga.Visible = false;
        }

        private IEnumerable<InterventoDto> FindChilds(int valore)
        {
            return this._alberoProcRepository.GetSottonodi(this.IdComune, this.Software, valore, new AmbitoRicercaAreaRiservata(false), String.Empty);
        }
    }
}