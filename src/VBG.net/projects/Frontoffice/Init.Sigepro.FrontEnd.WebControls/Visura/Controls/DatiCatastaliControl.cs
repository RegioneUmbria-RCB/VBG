using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;
using Init.Sigepro.FrontEnd.WebControls.Common;

using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per DatiCatastaliControl.
	/// </summary>
	public class DatiCatastaliControl : BaseVisuraControl
	{
		private DropDownList m_tipiCatasto = new DropDownList();
		private TextBox m_foglio = new TextBox();
		private TextBox m_particella = new TextBox();
		private TextBox m_sub = new TextBox();
		private HtmlTable m_innercontrol = new HtmlTable();

		private HtmlTableCell c1 = new HtmlTableCell();
		private HtmlTableCell c2 = new HtmlTableCell();
		private HtmlTableCell c3 = new HtmlTableCell();
		private HtmlTableCell c4 = new HtmlTableCell();
		private HtmlTableCell c5 = new HtmlTableCell();
		private HtmlTableCell c6 = new HtmlTableCell();
		private HtmlTableCell c7 = new HtmlTableCell();
		private HtmlTableCell c8 = new HtmlTableCell();

		private HtmlTableRow r1 = new HtmlTableRow();
		private HtmlTableRow r2 = new HtmlTableRow();
		private HtmlTableRow r3 = new HtmlTableRow();
		private HtmlTableRow r4 = new HtmlTableRow();

		private Label l1 = new Label();
		private Label l2 = new Label();
		private Label l3 = new Label();
		private Label l4 = new Label();

		public DatiCatastaliControl()
		{
			c1.Controls.Add(l1);
			c2.Controls.Add(m_tipiCatasto);
			c3.Controls.Add(l2);
			c4.Controls.Add(m_foglio);
			c5.Controls.Add(l3);
			c6.Controls.Add(m_particella);
			c7.Controls.Add(l4);
			c8.Controls.Add(m_sub);

			r1.Cells.Add(c1);
			r1.Cells.Add(c2);
			r2.Cells.Add(c3);
			r2.Cells.Add(c4);
			r3.Cells.Add(c5);
			r3.Cells.Add(c6);
			r4.Cells.Add(c7);
			r4.Cells.Add(c8);

			m_innercontrol.Rows.Add(r1);
			m_innercontrol.Rows.Add(r2);
			m_innercontrol.Rows.Add(r3);
			m_innercontrol.Rows.Add(r4);

		}

		protected override void OnLoad(System.EventArgs e)
		{
			l1.Text = "Tipo catasto:";
			l2.Text = "Foglio:";
			l3.Text = "Particella:";
			l4.Text = "Subalterno:";

			m_tipiCatasto.DataTextField = "Value";
			m_tipiCatasto.DataValueField = "Key";

			// popolare la combo tipicatasto
			if (m_tipiCatasto.Items.Count == 0)
			{
				var list = new KeyValuePair<string,string>[] { 
					new KeyValuePair<string,string>("T","Terreni"),
					new KeyValuePair<string,string>("F","Fabbricati")
				};

				m_tipiCatasto.DataSource = list;
				m_tipiCatasto.DataBind();

				m_tipiCatasto.Items.Insert(0, new ListItem("", ""));
			}

			base.OnLoad(e);
		}


		protected override Control GetInnerControl()
		{
			return m_innercontrol;
		}

		public string Foglio
		{
			get { return m_foglio.Text; }
		}

		public string Particella
		{
			get { return m_particella.Text; }
		}

		public string Subalterno
		{
			get { return m_sub.Text; }
		}

		public string TipoCatasto
		{
			get { return m_tipiCatasto.SelectedValue; }
		}
	}
}