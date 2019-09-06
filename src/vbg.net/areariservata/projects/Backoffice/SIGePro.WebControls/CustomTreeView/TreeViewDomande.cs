using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.Utils.Web.UI;

namespace SIGePro.WebControls.CustomTreeView
{
	public partial class TreeViewDomande : Albero
	{
		public int SelectedId
		{
			get { object o = this.ViewState["SelectedId"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["SelectedId"] = value; }
		}


		List<DomandeFrontAlbero> m_dataSource = null;

		public List<DomandeFrontAlbero> DataSource
		{
			get { return m_dataSource; }
			set	{ m_dataSource = value;	}
		}

		public TreeViewDomande()
		{
			this.ImagesRoot = "~/Images/TreeView";
			this.ItemSelected += new ItemSelectedDelegate(TreeViewDomande_ItemSelected);
		}

		void TreeViewDomande_ItemSelected(SelectableTreeNode sender, EventArgs e)
		{
			this.SelectedId = Convert.ToInt32(sender.Id);
			sender.Collapsed = false;

			g_mustRecreateHierarchy = true;
		}

		public override void DataBind()
		{

			this.Childs.Clear();

			this.Childs.Add(new SelectableTreeNode("-1", "<b>Aree tematiche disponibili:</b>"));

			foreach (DomandeFrontAlbero dfa in DataSource)
				this.Childs[0].Childs.Add(CreaNodoRicorsivo(dfa));

			ExpandSelected();

			base.DataBind();
		}

		private void ExpandSelected()
		{
			ExpandSelectedRecoursive(this.Childs);
		}

		private void ExpandSelectedRecoursive(TreeViewNodeCollection childs)
		{
			foreach (TreeViewNode tvn in childs)
			{
				SelectableTreeNode stn = tvn as SelectableTreeNode;

				if (stn != null && stn.Id == this.SelectedId.ToString())
				{
					stn.Collapsed = false;
					return;
				}

				ExpandSelectedRecoursive(tvn.Childs);
			}
		}

		private TreeViewNode CreaNodoRicorsivo(DomandeFrontAlbero dfa)
		{
			TreeViewNodeDomande nodo = new TreeViewNodeDomande(dfa);

			foreach (DomandeFrontAlbero child in dfa.SottoAree)
				nodo.Childs.Add(CreaNodoRicorsivo(child));
			
			return nodo;
		}

	}

	public partial class TreeViewNodeDomande : SelectableTreeNode
	{
		public bool Disabilitato
		{
			get { object o = this.ViewState["Disabilitato"]; return o == null ? false : (bool)o; }
			set { this.ViewState["Disabilitato"] = value; }
		}

		public TreeViewNodeDomande(): base()
		{

		}

		public TreeViewNodeDomande(DomandeFrontAlbero dfa)
			: base(dfa.Id.ToString(), dfa.Descrizione + (dfa.Disattiva == 1 ? " (disabilitato)" : ""), dfa.Note)
		{
			Disabilitato = dfa.Disattiva == 1;
		}

		protected override string GetCssClass()
		{
			if ((this.TreeView as TreeViewDomande).SelectedId.ToString() == this.Id)
				return "AreaSelezionata";

			if (this.Disabilitato)
				return "AreaDisabilitata";
			
			return "";
		}
	}
}
