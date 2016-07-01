using System;
using System.Web.UI;
using System.Web.UI.WebControls;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per StatoIstanzaControl.
	/// </summary>
	public class StatoIstanzaControl : BaseVisuraControl
	{
		[Inject]
		public IStatiIstanzaRepository _statiIstanzaRepository { get; set; }


		private DropDownList m_innerControl = new DropDownList();

		public StatoIstanzaControl()
		{
			FoKernelContainer.Inject(this);

			m_innerControl.DataValueField = "CODICESTATO";
			m_innerControl.DataTextField = "STATO";

			this.Init += new EventHandler(StatoIstanzaControl_Init);
		}

		protected override Control GetInnerControl()
		{
			return m_innerControl;
		}

		protected override void OnLoad(EventArgs e)
		{
			if (m_innerControl.Items.Count == 0)
				DataBind();
		}


		public override void DataBind()
		{
			m_innerControl.DataSource = _statiIstanzaRepository.GetList(IdComune, Software);
			m_innerControl.DataBind();

			m_innerControl.Items.Insert(0, new ListItem("", ""));
		}

		private void StatoIstanzaControl_Init(object sender, EventArgs e)
		{
		}

		public string Value
		{
			get { return m_innerControl.SelectedValue; }
		}
	}
}