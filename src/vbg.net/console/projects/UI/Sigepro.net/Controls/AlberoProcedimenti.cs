using System;
using System.Collections;
using System.Data;
using System.Data.OleDb;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using PersonalLib2.Data;

namespace SIGePro.Net.Controls
{
	/// <summary>
	/// Descrizione di riepilogo per AlberoProcedimenti.
	/// </summary>
	[ToolboxData("<{0}:AlberoProcedimenti runat=server></{0}:AlberoProcedimenti>")]
	public class AlberoProcedimenti : Init.Utils.Web.UI.Albero
	{
		public delegate void ProcedimentoSelezionatoDelegate(object sender, AlberoProc procedimento);

		public event ProcedimentoSelezionatoDelegate ProcedimentoSelezionato;

		#region membri privati

		private DataBase m_database;

		#endregion

		#region Properties

		protected string IdComune
		{
			get
			{
				object o = this.ViewState["IdComune"];
				return o == null ? String.Empty : (string) o;
			}
			set { this.ViewState["IdComune"] = value; }
		}

		public string Software
		{
			get
			{
				object o = this.ViewState["Software"];
				return o == null ? String.Empty : (string) o;
			}
			set { this.ViewState["Software"] = value; }
		}

		public bool AllowTitles
		{
			get
			{
				object o = this.ViewState["ShowTitles"];
				return o == null ? false : (bool) o;
			}
			set { this.ViewState["ShowTitles"] = value; }
		}
		
        public bool AllowParentSelection
		{
			get
			{
				object o = this.ViewState["AllowParentSelection"];
				return o == null ? false : (bool) o;
			}
			set { this.ViewState["AllowParentSelection"] = value; }
		}


		public string ImageFolderOpen
		{
			get
			{
				object o = this.ViewState["ImageFolderOpen"];
				return o == null ? "folderOpen.gif" : (string) o;
			}
			set { this.ViewState["ImageFolderOpen"] = value; }
		}

		public string ImageFolderClosed
		{
			get
			{
				object o = this.ViewState["ImageFolderClosed"];
				return o == null ? "folderClosed.gif" : (string) o;
			}
			set { this.ViewState["ImageFolderClosed"] = value; }
		}

		public string ImageDocument
		{
			get
			{
				object o = this.ViewState["ImageDocument"];
				return o == null ? "documentIcon.gif" : (string) o;
			}
			set { this.ViewState["ImageDocument"] = value; }
		}

		public AuthenticationInfo AuthenticationInfo
		{
			set
			{
				m_database = value.CreateDatabase();
				IdComune = value.IdComune;
			}
		}

		public string Title
		{
			get
			{
				object o = this.ViewState["Title"];
				return o == null ? "ALBERO PROCEDIMENTI" : (string) o;
			}
			set { this.ViewState["Title"] = value; }
		}

		#endregion 

		public AlberoProcedimenti()
		{
			CssClass = "NodoProcedimenti";

			this.ItemSelected += new ItemSelectedDelegate(NodeClicked);
		}

		public override void DataBind()
		{
			this.Childs.Clear();

			Hashtable htProcedimenti = ReadProcedimenti();
			SortProcedimenti(htProcedimenti);

			base.DataBind();
		}

		#region metodi protetti

		private void SortProcedimenti(Hashtable procedimenti)
		{
			TreeViewNode rootNode = new TreeViewNode(Title);
			rootNode.CssClass = "RootProcedimenti";
			foreach (string key in procedimenti.Keys)
			{
				AlberoProcedimentiItem item = (AlberoProcedimentiItem) procedimenti[key];

				if (key.Length == 2) // nodo radice
				{
					rootNode.Childs.Add(item);
				}
				else
				{
					string parentId = key.Substring(0, key.Length - 2);
					AlberoProcedimentiItem parentItem = (AlberoProcedimentiItem) procedimenti[parentId];

					if(parentItem != null)
						parentItem.Childs.Add(item);
				}
			}

			//foreach( AlberoProcedimentiItem item in rootNode.Childs )
				SortRecoursive(rootNode);
			

			Childs.Add(rootNode);
		}

		private void SortRecoursive(TreeViewNode node)
		{
			node.Childs.Sort(delegate(TreeViewNode a, TreeViewNode b) { return (a as AlberoProcedimentiItem).Ordine - (b as AlberoProcedimentiItem).Ordine; });

			foreach (AlberoProcedimentiItem item in node.Childs)
				SortRecoursive(item);
		}

		private Hashtable ReadProcedimenti()
		{
			Hashtable ht = new Hashtable();

			AlberoProc alberoProc	= new AlberoProc();
			alberoProc.Idcomune		= IdComune;
			alberoProc.SOFTWARE		= Software;
			alberoProc.OrderBy		= "length(sc_codice) ASC,sc_codice asc";

			ArrayList al = m_database.GetClassList( alberoProc);

			foreach(AlberoProc ap in al)
			{
				string key				= ap.SC_CODICE;
				string idProcedimento	= ap.Sc_id.ToString();
				string testo			= ap.SC_DESCRIZIONE;
				bool nodoPadre			= Convert.ToInt16( ap.SC_PADRE ) == 1;
				string note				= ap.SC_NOTE;
				int ordine				= Convert.ToInt32(ap.SC_ORDINE);

				if (!AllowTitles)
					note = "";

				if (AllowParentSelection)
					nodoPadre = false;

				ht.Add(key, new AlberoProcedimentiItem(idProcedimento, testo, note, ordine , nodoPadre));
			}

			return ht;
		}

		protected void ItemClicked(AlberoProcedimentiItem item)
		{
		}

		private void NodeClicked(SelectableTreeNode sender, EventArgs e)
		{
			if (ProcedimentoSelezionato != null)
			{
				AlberoProc procedimento = new AlberoProcMgr(m_database).GetById(Convert.ToInt32(sender.Id), IdComune);

				ProcedimentoSelezionato(sender, procedimento);
			}
		}

		#endregion

		#region Sottoclasse AlberoProcedimentiItem

		public class AlberoProcedimentiItem : SelectableTreeNode
		{
			private Image m_nodeImage = new Image();


			public bool Padre
			{
				get
				{
					object o = this.ViewState["Padre"];
					return o == null ? true : (bool) o;
				}
				set { this.ViewState["Padre"] = value; }
			}

			public int Ordine
			{
				get { object o = this.ViewState["Ordine"]; return o == null ? -1 : (int)o; }
				set { this.ViewState["Ordine"] = value; }
			}


			public AlberoProcedimentiItem() : base()
			{
			}

			public AlberoProcedimentiItem(string id, string text, string alt, int ordine , bool padre) : base(id, text)
			{
				Padre = padre;
				Ordine = ordine;

				if (alt != String.Empty)
				{
					this.AltText = text + ":\n" + alt;
				}
			}

			protected override void CreateNodeControl(WebControl container)
			{
				UpdateNodeImage();

				container.Controls.Add(m_nodeImage);

				if (!Padre)
				{
					base.CreateNodeControl(container);
				}
				else
				{
					Label lbl = new Label();
					lbl.Text = this.Text;

					container.Controls.Add(lbl);
				}
			}

			protected override void OnExpandCollapse()
			{
				UpdateNodeImage();
			}

			protected void UpdateNodeImage()
			{
				m_nodeImage.ImageAlign = ImageAlign.Top;

				AlberoProcedimenti albero = (AlberoProcedimenti) this.TreeView;
				string root = albero.ImagesRoot;

				root.Replace('\\', '/');

				if (!root.EndsWith("/"))
					root += "/";

				if (!HasChilds)
				{
					m_nodeImage.ImageUrl = root + albero.ImageDocument;
				}
				else
				{
					if (Collapsed)
						m_nodeImage.ImageUrl = root + albero.ImageFolderClosed;
					else
						m_nodeImage.ImageUrl = root + albero.ImageFolderOpen;
				}
			}
		}

		#endregion
	}
}