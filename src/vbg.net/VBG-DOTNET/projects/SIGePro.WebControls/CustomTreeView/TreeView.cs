using System;
using System.Collections;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Utils.Web.Designers;
using System.Collections.Generic;
using System.Diagnostics;

namespace Init.Utils.Web.UI
{
	/// <summary>
	/// Descrizione di riepilogo per TreeView.
	/// </summary>
	[ToolboxData("<{0}:Albero runat=server></{0}:Albero>")]
	[Designer(typeof (AlberoDesigner))]
	public class Albero : WebControl, INamingContainer
	{
		internal enum TreeNodeType
		{
			// Fields
			Collapsed = 0x20,
			Expanded = 0x10,
			LinkDown = 2,
			LinkSub = 4,
			LinkUp = 1,
			None = 0
		}


		private TreeViewNodeCollection m_nodes = new TreeViewNodeCollection(null);
		protected bool g_mustRecreateHierarchy = false;

		public delegate void ItemSelectedDelegate( SelectableTreeNode sender , EventArgs e);
		public event ItemSelectedDelegate ItemSelected;

		#region Gestione delle immagini

		[DefaultValue("TreeView"), Bindable(true), Category("Images")]
		public string ImagesRoot
		{
			get
			{
				object o = this.ViewState["ImagesRoot"];
				return o == null ? "TreeView" : (string) o;
			}
			set { this.ViewState["ImagesRoot"] = value; }
		}


		[DefaultValue("DownRight.gif"), Bindable(true), Category("Images")]
		public string ImageDownRight
		{
			get
			{
				object o = this.ViewState["ImageDownRight"];
				return o == null ? "DownRight.gif" : (string) o;
			}
			set { this.ViewState["ImageDownRight"] = value; }
		}


		[DefaultValue("DownRightCollapsed.gif"), Bindable(true), Category("Images")]
		public string ImageDownRightCollapsed
		{
			get
			{
				object o = this.ViewState["ImageDownRightCollapsed"];
				return o == null ? "DownRightCollapsed.gif" : (string) o;
			}
			set { this.ViewState["ImageDownRightCollapsed"] = value; }
		}


		[DefaultValue("DownRightExpanded.gif"), Bindable(true), Category("Images")]
		public string ImageDownRightExpanded
		{
			get
			{
				object o = this.ViewState["ImageDownRightExpanded"];
				return o == null ? "DownRightExpanded.gif" : (string) o;
			}
			set { this.ViewState["ImageDownRightExpanded"] = value; }
		}


		[DefaultValue("none.gif"), Bindable(true), Category("Images")]
		public string ImageNone
		{
			get
			{
				object o = this.ViewState["ImageNone"];
				return o == null ? "None.gif" : (string) o;
			}
			set { this.ViewState["ImageNone"] = value; }
		}


		[DefaultValue("right.gif"), Bindable(true), Category("Images")]
		public string ImageRight
		{
			get
			{
				object o = this.ViewState["ImageRight"];
				return o == null ? "Right.gif" : (string) o;
			}
			set { this.ViewState["ImageRight"] = value; }
		}


		[DefaultValue("RightCollapsed.gif"), Bindable(true), Category("Images")]
		public string ImageRightCollapsed
		{
			get
			{
				object o = this.ViewState["ImageRightCollapsed"];
				return o == null ? "RightCollapsed.gif" : (string) o;
			}
			set { this.ViewState["ImageRightCollapsed"] = value; }
		}


		[DefaultValue("RightExpanded.gif"), Bindable(true), Category("Images")]
		public string ImageRightExpanded
		{
			get
			{
				object o = this.ViewState["ImageRightExpanded"];
				return o == null ? "RightExpanded.gif" : (string) o;
			}
			set { this.ViewState["ImageRightExpanded"] = value; }
		}


		[DefaultValue("UpDown.gif"), Bindable(true), Category("Images")]
		public string ImageUpDown
		{
			get
			{
				object o = this.ViewState["ImageUpDown"];
				return o == null ? "UpDown.gif" : (string) o;
			}
			set { this.ViewState["ImageUpDown"] = value; }
		}


		[DefaultValue("UpDownRight.gif"), Bindable(true), Category("Images")]
		public string ImageUpDownRight
		{
			get
			{
				object o = this.ViewState["ImageUpDownRight"];
				return o == null ? "UpDownRight.gif" : (string) o;
			}
			set { this.ViewState["ImageUpDownRight"] = value; }
		}


		[DefaultValue("UpDownRightCollapsed.gif"), Bindable(true), Category("Images")]
		public string ImageUpDownRightCollapsed
		{
			get
			{
				object o = this.ViewState["ImageUpDownRightCollapsed"];
				return o == null ? "UpDownRightCollapsed.gif" : (string) o;
			}
			set { this.ViewState["ImageUpDownRightCollapsed"] = value; }
		}


		[DefaultValue("UpDownRightExpanded.gif"), Bindable(true), Category("Images")]
		public string ImageUpDownRightExpanded
		{
			get
			{
				object o = this.ViewState["ImageUpDownRightExpanded"];
				return o == null ? "UpDownRightExpanded.gif" : (string) o;
			}
			set { this.ViewState["ImageUpDownRightExpanded"] = value; }
		}


		[DefaultValue("UpDownExpanded.gif"), Bindable(true), Category("Images")]
		public string ImageUpDownExpanded
		{
			get
			{
				object o = this.ViewState["ImageUpDownExpanded"];
				return o == null ? "UpDownExpanded.gif" : (string) o;
			}
			set { this.ViewState["ImageUpDownExpanded"] = value; }
		}


		[DefaultValue("UpRight.gif"), Bindable(true), Category("Images")]
		public string ImageUpRight
		{
			get
			{
				object o = this.ViewState["ImageUpRight"];
				return o == null ? "UpRight.gif" : (string) o;
			}
			set { this.ViewState["ImageUpRight"] = value; }
		}


		[DefaultValue("UpRightExpanded.gif"), Bindable(true), Category("Images")]
		public string ImageUpRightExpanded
		{
			get
			{
				object o = this.ViewState["ImageUpRightExpanded"];
				return o == null ? "UpRightExpanded.gif" : (string) o;
			}
			set { this.ViewState["ImageUpRightExpanded"] = value; }
		}


		[DefaultValue("UpRightCollapsed.gif"), Bindable(true), Category("Images")]
		public string ImageUpRightCollapsed
		{
			get
			{
				object o = this.ViewState["ImageUpRightCollapsed"];
				return o == null ? "UpRightCollapsed.gif" : (string) o;
			}
			set { this.ViewState["ImageUpRightCollapsed"] = value; }
		}

		#endregion

		[EditorBrowsable(EditorBrowsableState.Never)]
		public TreeViewNodeCollection Childs
		{
			get { return m_nodes; }
		}


		public Albero()
		{
			m_nodes.ItemAdded += new TreeViewNodeCollection.OnItemAddedDelegate(OnItemAddedHandler);
		}


		public override void DataBind()
		{
			g_mustRecreateHierarchy = true;
		}


		private void CreateControlHierachy()
		{
			this.Controls.Clear();

			for (int i = 0; i < Childs.Count; i++)
			{
				TreeViewNode node = Childs[i];
				node.ClearChilds();
				node.CreateHierarchy(this);
			}

			//DebugChildsRecoursive(0 , this.Childs);
		}

		private void DebugChildsRecoursive( int level , TreeViewNodeCollection childs)
		{
			foreach (TreeViewNode node in childs)
			{
				for (int i = 0; i < level; i++)
					Debug.Write("\t");

				Debug.Write( node.Text);
				Debug.Write(Environment.NewLine);

				DebugChildsRecoursive(level + 1, node.Childs);
			}


		}

		protected override void OnPreRender(EventArgs e)
		{
			if (g_mustRecreateHierarchy)
			{
				CreateControlHierachy();
			}
			
			base.OnPreRender(e);
		}


		#region Gestione del viewstate

		protected override object SaveViewState()
		{
			object[] vsData = new object[2];
			vsData[0] = base.SaveViewState();
			vsData[1] = SaveChilds();

			return vsData;
		}


		private object SaveChilds()
		{
			ArrayList childvs = new ArrayList();

			for (int i = 0; i < Childs.Count; i++)
			{
				TreeViewNode node = Childs[i];

				System.Web.UI.Pair val = new System.Web.UI.Pair();

				val.First = node.GetType().AssemblyQualifiedName;
				val.Second = node.SaveViewState();

				childvs.Add( val );
			}

			return childvs;
		}


		protected override void LoadViewState(object savedState)
		{
			object[] vsData = (object[]) savedState;
			base.LoadViewState(vsData[0]);

			LoadChilds(vsData[1]);

			CreateControlHierachy();
		}

		private void LoadChilds(object o)
		{
			ArrayList childvs = (ArrayList) o;

			for (int i = 0; i < childvs.Count; i++)
			{
				System.Web.UI.Pair val = (System.Web.UI.Pair)childvs[i];

				TreeViewNode child = TreeViewNode.TreeNodeAllocator.CreateFromQualifiedTypeName( val.First.ToString() );
				
				child.LoadViewState( val.Second );

				Childs.Add( child );
			}
		}

		#endregion

		private void OnItemAddedHandler(object sender, TreeViewNode newItem)
		{
			newItem.TreeView = this;
			newItem.Childs.ItemAdded += new TreeViewNodeCollection.OnItemAddedDelegate(OnItemAddedHandler);
			OnItemAdded(newItem);
		}

		protected virtual void OnItemAdded(TreeViewNode newItem)
		{
		}

		internal void SelectItem( SelectableTreeNode sender )
		{
			if ( ItemSelected != null )
				ItemSelected( sender , EventArgs.Empty );
		}
	}

	#region Classe TreeViewItem

	public class TreeViewNode
	{
		#region Allocator della classe
		public class TreeNodeAllocator
		{
			public static TreeViewNode CreateFromQualifiedTypeName( string qualifiedName )
			{
				Type type = Type.GetType( qualifiedName , true, true);
				try
				{
					return (TreeViewNode) Activator.CreateInstance(type);
				}
				catch (Exception e)
				{
					throw new ArgumentException(String.Format("The type '{0}' cannot be recreated from ViewState", type.ToString()), e);
				}
			}
		}
		#endregion

		private TreeViewNodeCollection m_childs = null;
		private Albero m_treeView;

		private Panel m_childsPanel = new Panel();

		internal TreeViewNode Parent;

		#region Properties

		public string CssClass
		{
			get
			{
				object o = ViewState["CssClass"];
				return o == null ? "" : o.ToString();
			}
			set { ViewState["CssClass"] = value; }
		}


		public Albero TreeView
		{
			set { m_treeView = value; }

			get
			{
				if (m_treeView != null) return m_treeView;

				if (Parent != null) return Parent.TreeView;

				return null;
			}
		}

		public string Text
		{
			get
			{
				object o = m_controlState["Internal_Text"];
				return (o == null) ? String.Empty : o.ToString();
			}
			set { m_controlState["Internal_Text"] = value; }
		}


		public bool Collapsed
		{
			get
			{
				object o = m_controlState["Internal_Collapsed"];
				if (o == null) return false;
				
				if ( Parent == null )
					return false;

				return (bool) o;
			}
			set 
			{ 
				m_controlState["Internal_Collapsed"] = value;

				if (value) return;

				TreeViewNode p = this.Parent;

				while (p != null)
				{
					if (p.Collapsed) p.Collapsed = false;

					p = p.Parent;
				}
			}
		}


		public bool HasChilds
		{
			get { return m_childs.Count > 0; }
		}


		public bool IsRoot
		{
			get { return Parent == null; }
		}


		public TreeViewNodeCollection Childs
		{
			get { return m_childs; }
		}

		#endregion

		#region Costruttori

		public TreeViewNode()
		{
			m_childs = new TreeViewNodeCollection(this);
		}

		public TreeViewNode(string text) : this()
		{
			Text = text;
		}

		#endregion

		#region Creazione della gerarchia dei controlli

		protected virtual void CreateNodeControl(WebControl container)
		{
			Label label = new Label();
			label.Text = this.Text;
			container.Controls.Add(label);
		}

		internal void ClearChilds()
		{
			m_childsPanel.Controls.Clear();

			foreach (TreeViewNode n in this.Childs)
				n.ClearChilds();
		}

		public void CreateHierarchy(WebControl container)
		{
			Panel panel = new Panel();

			panel.CssClass = (CssClass == String.Empty) ? TreeView.CssClass : CssClass;

			RenderNodePrefix(panel, true);

			CreateNodeControl(panel);

			container.Controls.Add(panel);
			panel.Controls.Add(m_childsPanel);

			for (int i = 0; i < m_childs.Count; i++)
				m_childs[i].CreateHierarchy(m_childsPanel);

			m_childsPanel.Visible = !Collapsed;

		}

		private void RenderNodePrefix(WebControl container, bool firstSubChild)
		{
			if (Parent == null)
				return;

			if (!Parent.IsRoot)
			{
				Parent.RenderNodePrefix(container, false);
			}

			Control prefix = null;
			string imageUrl = GetNodeImage(firstSubChild);

			if (HasChilds)
			{
				ImageButton prefixButton = new ImageButton();
				prefixButton.ImageAlign = ImageAlign.Top;
				prefixButton.Click += new ImageClickEventHandler(OnImageClick);
				prefixButton.ImageUrl = imageUrl;

				prefix = prefixButton;
			}
			else
			{
				Image image = new Image();
				image.ImageUrl = imageUrl;
				image.ImageAlign = ImageAlign.Top;

				prefix = image;
			}
			container.Controls.Add(prefix);
		}

		private Albero.TreeNodeType GetNodeType(bool firstSubChild)
		{
			bool isLastChild = true;
			bool isFirstChild = true;

			if (Parent.Childs.Count > 0)
			{
				isLastChild = (Parent.Childs[Parent.Childs.Count - 1] == this);
				isFirstChild = (Parent.Childs[0] == this);
			}

			Albero.TreeNodeType type1 = Albero.TreeNodeType.None;

			if (firstSubChild)
			{
				type1 |= Albero.TreeNodeType.LinkSub;
			}
			if (((!Parent.IsRoot || !firstSubChild) || !isFirstChild) && (firstSubChild || !isLastChild))
			{
				type1 |= Albero.TreeNodeType.LinkUp;
			}
			if (!isLastChild)
			{
				type1 |= Albero.TreeNodeType.LinkDown;
			}
			if (!firstSubChild || !HasChilds)
			{
				return type1;
			}
			if (Collapsed)
			{
				return (type1 | Albero.TreeNodeType.Collapsed);
			}
			return (type1 | Albero.TreeNodeType.Expanded);

		}

		private string GetNodeImage(bool firstSubChild)
		{
			string text1 = TreeView.ImagesRoot;

			if (!text1.EndsWith("/"))
			{
				text1 = text1 + "/";
			}

			Albero.TreeNodeType node_type = GetNodeType(firstSubChild);

			switch (node_type)
			{
				case (Albero.TreeNodeType.LinkDown | Albero.TreeNodeType.LinkUp):
					return text1 + TreeView.ImageUpDown;

				case Albero.TreeNodeType.LinkSub:
					return text1 + TreeView.ImageRight;

				case (Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkUp):
					return text1 + TreeView.ImageUpRight;

				case (Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkDown):
					return text1 + TreeView.ImageDownRight;

				case (Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkDown | Albero.TreeNodeType.LinkUp):
					return text1 + TreeView.ImageUpDownRight;

				case (Albero.TreeNodeType.Expanded | Albero.TreeNodeType.LinkSub):
					return text1 + TreeView.ImageRightExpanded;

				case (Albero.TreeNodeType.Expanded | Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkUp):
					return text1 + TreeView.ImageUpRightExpanded;

				case (Albero.TreeNodeType.Expanded | Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkDown):
					return text1 + TreeView.ImageDownRightExpanded;

				case (Albero.TreeNodeType.Expanded | Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkDown | Albero.TreeNodeType.LinkUp):
					return text1 + TreeView.ImageUpDownRightExpanded;

				case (Albero.TreeNodeType.Collapsed | Albero.TreeNodeType.LinkSub):
					return text1 + TreeView.ImageRightCollapsed;

				case (Albero.TreeNodeType.Collapsed | Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkUp):
					return text1 + TreeView.ImageUpRightCollapsed;

				case (Albero.TreeNodeType.Collapsed | Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkDown):
					return text1 + TreeView.ImageDownRightCollapsed;

				case (Albero.TreeNodeType.Collapsed | Albero.TreeNodeType.LinkSub | Albero.TreeNodeType.LinkDown | Albero.TreeNodeType.LinkUp):
					return text1 + TreeView.ImageUpDownRightCollapsed;
			}
			return (text1 + TreeView.ImageNone);
		}

		#endregion

		public void SwapCollapsed()
		{
			Collapsed = !Collapsed;
			m_childsPanel.Visible = !Collapsed;
		}


		#region gestione del Viewstate

		private Hashtable m_controlState = new Hashtable();

		protected Hashtable ViewState
		{
			get { return m_controlState; }
		}

		internal object SaveViewState()
		{
			object[] viewState = new object[2];

			viewState[0] = SaveControlStatePairs();
			viewState[1] = SaveChilds();

			return viewState;
		}

		private System.Web.UI.Pair[] SaveControlStatePairs()
		{
			System.Web.UI.Pair[] csItems = new System.Web.UI.Pair[m_controlState.Count];

			int idx = 0;

			foreach (string s in m_controlState.Keys)
			{
				csItems[idx] = new System.Web.UI.Pair(s, m_controlState[s]);
				idx++;
			}
			return csItems;
		}

		
		private void LoadControlStatePairs(object savedPairs)
		{
			m_controlState.Clear();

			if (savedPairs == null) return;

			System.Web.UI.Pair[] csItems = (System.Web.UI.Pair[]) savedPairs;

			for (int i = 0; i < csItems.Length; i++)
			{
				object key = csItems[i].First;
				object value = csItems[i].Second;

				m_controlState.Add(key, value);
			}
		}

		
		private object SaveChilds()
		{
			ArrayList childvs = new ArrayList();

			for (int i = 0; i < m_childs.Count; i++)
			{
				TreeViewNode node = m_childs[i];

				System.Web.UI.Pair val = new System.Web.UI.Pair();
				val.First = node.GetType().AssemblyQualifiedName;
				val.Second = node.SaveViewState();

				childvs.Add( val );
			}

			return childvs;
		}


		private void LoadChilds(object savedState)
		{
			ArrayList childvs = (ArrayList) savedState;

			for (int i = 0; i < childvs.Count; i++)
			{
				System.Web.UI.Pair val = (System.Web.UI.Pair)childvs[i];

				TreeViewNode child = TreeNodeAllocator.CreateFromQualifiedTypeName( val.First.ToString() );

				child.LoadViewState( val.Second );

				m_childs.Add(child);
			}
		}


		internal void LoadViewState(object savedState)
		{
			object[] viewState = (object[]) savedState;

			LoadControlStatePairs(viewState[0]);
			LoadChilds(viewState[1]);
		}

		#endregion

		#region gestione degli eventi interni

		private void OnImageClick(object sender, ImageClickEventArgs e)
		{
			SwapCollapsed();
			((ImageButton) sender).ImageUrl = GetNodeImage(true);
			

			OnExpandCollapse();
		}

		protected virtual void OnExpandCollapse()
		{
		}


 
		#endregion
	}

	#endregion
}