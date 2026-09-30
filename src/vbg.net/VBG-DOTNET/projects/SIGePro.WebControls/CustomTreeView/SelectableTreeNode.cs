using System;
using System.Collections;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
	/// <summary>
	/// Descrizione di riepilogo per CustomTreeNode.
	/// </summary>
	public class SelectableTreeNode : TreeViewNode
	{
		public string AltText
		{
			get
			{
				object o = this.ViewState["AltText"];
				return o == null ? String.Empty : (string) o;
			}
			set { this.ViewState["AltText"] = value; }
		}

		public string Id
		{
			get
			{
				object o = this.ViewState["Id"];
				return o == null ? String.Empty : (string) o;
			}
			set { this.ViewState["Id"] = value; }
		}

		public SelectableTreeNode():base()
		{
		}

		public SelectableTreeNode( string id , string text ) : this( id ,  text , "" )
		{
		}

		public SelectableTreeNode( string id , string text , string alt ) : this( id , text , alt , true )
		{
		}

		public SelectableTreeNode( string id , string text , string alt , bool collapsed) : base( text )
		{
			Id = id;
			Collapsed = collapsed;
			AltText = alt;
		}

		protected override void CreateNodeControl(System.Web.UI.WebControls.WebControl container)
		{
			LinkButton lb = new LinkButton();
			lb.Text = Text;
			lb.Attributes.Add( "title" , AltText );
			lb.Click +=new EventHandler(lb_Click);
			lb.CssClass = GetCssClass();

			container.Controls.Add( lb );
		}

		protected virtual string GetCssClass()
		{
			return string.Empty;
		}

		public void Select()
		{
			TreeView.SelectItem( this );
		}

		private void lb_Click(object sender, EventArgs e)
		{
			Select();
		}
	}
}
