using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.WebControls.Common;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per BaseVisuraControl.
	/// </summary>
	public abstract class BaseVisuraControl : SearchWebControl, IVisuraFilterControl, INamingContainer
	{
		private Label m_titleLabel = new Label();


		public override string ID
		{
			get { return base.ID; }
			set
			{
				EnsureChildControls();

				base.ID = value;
				m_titleLabel.ID = value + "_titleLabel";
				GetInnerControl().ID = value + "_innerControl";
				m_titleLabel.AssociatedControlID = GetInnerControl().ID;
			}
		}


		public virtual string CodiceComune
		{
			get
			{
				object o = this.ViewState["CodiceComune"];
				return o == null ? "" : o.ToString();
			}
			set { this.ViewState["CodiceComune"] = value; }
		}

		public string Title
		{
			get { return m_titleLabel.Text; }
			set { m_titleLabel.Text = value; }
		}

		public BaseVisuraControl()
		{
			this.Controls.Add(m_titleLabel);
			this.Controls.Add(GetInnerControl());
		}

		public override void RenderBeginTag(HtmlTextWriter writer)
		{
			writer.RenderBeginTag(HtmlTextWriterTag.Div);
		}

		protected abstract Control GetInnerControl();
	}
}