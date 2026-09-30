using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiDinamici
{
	public partial class PaginatoreSchedeDinamiche : System.Web.UI.UserControl
	{
		public event EventHandler IndiceSelezionato;
		public event EventHandler NuovaScheda;
		public event EventHandler EliminaScheda;

		IEnumerable<int> _indiciSchede;

		public IEnumerable<int> IndiciSchede
		{
			get { return _indiciSchede ?? Enumerable.Empty<int>(); }
			set 
			{
				_indiciSchede = value;
				this.IndiceNuovaScheda = value.OrderBy(x => x).Last() + 1;
			}
		}

		public int IndiceNuovaScheda
		{
			get { object o = this.ViewState["IndiceNuovaScheda"]; return o == null ? 1 : (int)o; }
			set { this.ViewState["IndiceNuovaScheda"] = value; }
		}


		public int IndiceCorrente
		{
			get { object o = this.ViewState["IndiceCorrente"]; return o == null ? 0 : (int)o; }
			set { this.ViewState["IndiceCorrente"] = value; }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
		
		}

		protected void OnIndiceSelezionato(object sender, EventArgs e)
		{
 			var lb = (LinkButton)sender;
			IndiceCorrente = Convert.ToInt32( lb.CommandArgument );

			if (this.IndiceSelezionato != null)
				this.IndiceSelezionato(this, EventArgs.Empty);
		}

		protected void OnNuovaScheda(object sender, EventArgs e)
		{
			if (this.NuovaScheda != null)
				this.NuovaScheda(this, EventArgs.Empty);
		}

		protected void OnEliminaScheda(object sender, EventArgs e)
		{
			if (this.EliminaScheda != null)
				this.EliminaScheda(this, EventArgs.Empty);
		}

		public override void DataBind()
		{
			rptMolteplicita.DataSource = GeneraDataSource();
			rptMolteplicita.DataBind();
		}

		private IEnumerable<object> GeneraDataSource()
		{
			return IndiciSchede.Select( (x,i) => new
			{
				IndiceCorrente = x == IndiceCorrente,
				Valore = x,
				Descrizione = (i + 1).ToString()
			});
		}
	}
}