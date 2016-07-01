using System;
using System.Collections;
using System.Data;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;

using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System.Collections.Generic;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Descrizione di riepilogo per RicercaAlberoProc.
	/// </summary>
	public class RicercaAlberoProc : SearchWebControl /*, INamingContainer*/
	{
		public class RigaRicerca: WebControl
		{
			public RigaRicerca():base(System.Web.UI.HtmlTextWriterTag.Div)
			{

			}
		}

		private DropDownList m_level0 = new DropDownList();
		private DropDownList m_level1 = new DropDownList();
		private DropDownList m_level2 = new DropDownList();
		private DropDownList m_level3 = new DropDownList();
		private DropDownList m_level4 = new DropDownList();
		private DropDownList m_level5 = new DropDownList();

		private Label m_label0 = new Label();
		private Label m_label1 = new Label();
		private Label m_label2 = new Label();
		private Label m_label3 = new Label();
		private Label m_label4 = new Label();
		private Label m_label5 = new Label();

		RigaRicerca m_ctrl0 = new RigaRicerca();
		RigaRicerca m_ctrl1 = new RigaRicerca();
		RigaRicerca m_ctrl2 = new RigaRicerca();
		RigaRicerca m_ctrl3 = new RigaRicerca();
		RigaRicerca m_ctrl4 = new RigaRicerca();
		RigaRicerca m_ctrl5 = new RigaRicerca();

		Dictionary<DropDownList, List<RigaRicerca>> _controlsChain = new Dictionary<DropDownList, List<RigaRicerca>>();


		public string Value
		{
			get
			{
				object o = this.ViewState["Value"];
				return o == null ? "" : o.ToString();
			}
			set
			{
				EnsureChildControls();
				this.ViewState["Value"] = value;
			}
		}

		public override string ID
		{
			get { return base.ID; }
			set
			{
				EnsureChildControls();
				base.ID = value;

				m_level0.ID = value + "_level0";
				m_level1.ID = value + "_level1";
				m_level2.ID = value + "_level2";
				m_level3.ID = value + "_level3";
				m_level4.ID = value + "_level4";
				m_level5.ID = value + "_level5";

				m_ctrl0.ID = value + "_row0";
				m_ctrl1.ID = value + "_row1";
				m_ctrl2.ID = value + "_row2";
				m_ctrl3.ID = value + "_row3";
				m_ctrl4.ID = value + "_row4";
				m_ctrl5.ID = value + "_row5";

				m_label0.AssociatedControlID = m_level0.ID;
				m_label1.AssociatedControlID = m_level1.ID;
				m_label2.AssociatedControlID = m_level2.ID;
				m_label3.AssociatedControlID = m_level3.ID;
				m_label4.AssociatedControlID = m_level4.ID;
				m_label5.AssociatedControlID = m_level5.ID;
			}
		}

		[Inject]
		public IInterventiRepository _alberoProcRepository { get;set; }

		public RicercaAlberoProc()
		{
			FoKernelContainer.Inject(this);


			this.Init += new EventHandler(RicercaAlberoProc_Init);
			this.Load += new EventHandler(RicercaAlberoProc_Load);

			m_level0.AutoPostBack =
			m_level1.AutoPostBack =
			m_level2.AutoPostBack =
			m_level3.AutoPostBack =
			m_level4.AutoPostBack =
			m_level5.AutoPostBack = true;

			m_label0.Text = "Tipo intervento";
			m_label1.Text = "";
			m_label2.Text = "";
			m_label3.Text = "";
			m_label4.Text = "";
			m_label5.Text = "";

			m_ctrl0.Controls.Add(m_label0);
			m_ctrl0.Controls.Add(m_level0);
			m_ctrl1.Controls.Add(m_label1);
			m_ctrl1.Controls.Add(m_level1);
			m_ctrl2.Controls.Add(m_label2);
			m_ctrl2.Controls.Add(m_level2);
			m_ctrl3.Controls.Add(m_label3);
			m_ctrl3.Controls.Add(m_level3);
			m_ctrl4.Controls.Add(m_label4);
			m_ctrl4.Controls.Add(m_level4);
			m_ctrl5.Controls.Add(m_label5);
			m_ctrl5.Controls.Add(m_level5);

			this.Controls.Add(m_ctrl0);
			this.Controls.Add(m_ctrl1);
			this.Controls.Add(m_ctrl2);
			this.Controls.Add(m_ctrl3);
			this.Controls.Add(m_ctrl4);
			this.Controls.Add(m_ctrl5);

			_controlsChain.Add(m_level0, new List<RigaRicerca>());
			_controlsChain.Add(m_level1, new List<RigaRicerca>());
			_controlsChain.Add(m_level2, new List<RigaRicerca>());
			_controlsChain.Add(m_level3, new List<RigaRicerca>());
			_controlsChain.Add(m_level4, new List<RigaRicerca>());
			_controlsChain.Add(m_level5, new List<RigaRicerca>());

			_controlsChain[m_level0].AddRange(new RigaRicerca[] { m_ctrl1, m_ctrl2, m_ctrl3, m_ctrl4, m_ctrl5 });
			_controlsChain[m_level1].AddRange(new RigaRicerca[] { m_ctrl2, m_ctrl3, m_ctrl4, m_ctrl5 });
			_controlsChain[m_level2].AddRange(new RigaRicerca[] { m_ctrl3, m_ctrl4, m_ctrl5 });
			_controlsChain[m_level3].AddRange(new RigaRicerca[] { m_ctrl4, m_ctrl5 });
			_controlsChain[m_level4].AddRange(new RigaRicerca[] { m_ctrl5 });
			_controlsChain[m_level5].AddRange(new RigaRicerca[] { });

		}

		private void RicercaAlberoProc_Init(object sender, EventArgs e)
		{

			m_level0.SelectedIndexChanged += new EventHandler(ItemSelectedIndexChanged);
			m_level1.SelectedIndexChanged += new EventHandler(ItemSelectedIndexChanged);
			m_level2.SelectedIndexChanged += new EventHandler(ItemSelectedIndexChanged);
			m_level3.SelectedIndexChanged += new EventHandler(ItemSelectedIndexChanged);
			m_level4.SelectedIndexChanged += new EventHandler(ItemSelectedIndexChanged);
			m_level5.SelectedIndexChanged += new EventHandler(ItemSelectedIndexChanged);
		}

		private void ItemSelectedIndexChanged(object sender, EventArgs e)
		{
			var ddl = (DropDownList)sender;
			var controlsChainItem = _controlsChain[ddl];
			var nextControl = controlsChainItem.Count > 0 ? controlsChainItem[0] : (RigaRicerca)null;
			

			if ( !String.IsNullOrEmpty(ddl.SelectedValue))
			{
				string valore = ddl.SelectedValue;

				if (!BindDropDown(nextControl, FindChilds(Convert.ToInt32(valore))))
					this.Value = valore;
				else
					this.Value = "";

			}
			else
			{
				HideDropDown(nextControl);
			}

			for (int i = 1; i < controlsChainItem.Count; i++)
			{
				HideDropDown(controlsChainItem[i]);
			}
		}


		private void RicercaAlberoProc_Load(object sender, EventArgs e)
		{
			if (m_level0.Items.Count == 0)
			{
				BindDropDown(m_ctrl0, FindChilds(-1));

				_controlsChain[m_level0].ForEach( x =>  HideDropDown( x ) );
			}
		}


		private bool BindDropDown(RigaRicerca riga, InterventoDto[] rows)
		{
			if (riga == null)
				return true;

			DropDownList dropDown = riga.Controls[1] as DropDownList;

			if (rows != null && rows.Length > 0)
			{
				DataTable tmpDt = new DataTable();
				DataColumn codice = new DataColumn("SC_ID",typeof(int));
				DataColumn descrizione = new DataColumn("SC_DESCRIZIONE",typeof(string));

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

		private InterventoDto[] FindChilds(int valore)
		{
			return _alberoProcRepository.GetSottonodi(IdComune, Software, valore, new AmbitoRicercaAreaRiservata(false), LivelloAutenticazioneEnum.Identificato);
		}
	}
}