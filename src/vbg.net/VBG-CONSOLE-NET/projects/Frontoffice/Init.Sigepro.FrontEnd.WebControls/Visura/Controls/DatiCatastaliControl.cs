using System.Web.UI;
// using System.Web.UI.HtmlControls;
// using System.Web.UI.WebControls;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;
using Init.Sigepro.FrontEnd.WebControls.Common;

using System.Collections.Generic;
using Init.Sigepro.FrontEnd.WebControls.FormControls;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per DatiCatastaliControl.
	/// </summary>
	public class DatiCatastaliControl : BaseVisuraControl
	{
		private DropDownList _tipiCatasto = new DropDownList();
		private TextBox _foglio = new TextBox();
		private TextBox _particella = new TextBox();
		private TextBox _sub = new TextBox();


		public DatiCatastaliControl()
		{
            this.Attributes.Add("class", "row dati-catastali");

            this._tipiCatasto.BtSize = BootstrapSize.Col3;
            this._foglio.BtSize = BootstrapSize.Col3;
            this._particella.BtSize = BootstrapSize.Col3;
            this._sub.BtSize = BootstrapSize.Col3;

            this.Controls.Clear();

            this.Controls.Add(_tipiCatasto);
            this.Controls.Add(_foglio);
            this.Controls.Add(_particella);
            this.Controls.Add(_sub);
		}

		protected override void OnLoad(System.EventArgs e)
		{
            this._tipiCatasto.Label = "Tipo catasto";
            this._foglio.Label = "Foglio";
            this._particella.Label = "Particella";
            this._sub.Label = "Subalterno";

			this._tipiCatasto.DataTextField = "Value";
			this._tipiCatasto.DataValueField = "Key";

			// popolare la combo tipicatasto
			if (_tipiCatasto.Items.Count == 0)
			{
				var list = new KeyValuePair<string,string>[] { 
					new KeyValuePair<string,string>("T","Terreni"),
					new KeyValuePair<string,string>("F","Fabbricati")
				};

				_tipiCatasto.DataSource = list;
				_tipiCatasto.DataBind();

				_tipiCatasto.Items.Insert(0, new System.Web.UI.WebControls.ListItem("", ""));
			}

			base.OnLoad(e);
		}


		protected override Control GetInnerControl()
		{
            return this._tipiCatasto;
		}

		public string Foglio
		{
			get { return _foglio.Value; }
		}

		public string Particella
		{
            get { return _particella.Value; }
		}

		public string Subalterno
		{
            get { return _sub.Value; }
		}

		public string TipoCatasto
		{
            get { return _tipiCatasto.Value; }
		}
	}
}