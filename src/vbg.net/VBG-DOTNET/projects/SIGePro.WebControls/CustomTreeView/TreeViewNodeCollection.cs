using System;
using System.Collections;
using System.Collections.Generic;

namespace Init.Utils.Web.UI
{
	/// <summary>
	///     <para>
	///       A collection that stores <see cref='TreeViewNode'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='TreeViewNodeCollection'/>
	[Serializable()]
	public class TreeViewNodeCollection {
		List<TreeViewNode> m_list = new List<TreeViewNode>();
		public delegate void OnItemAddedDelegate(object sender, TreeViewNode newItem);
		public event OnItemAddedDelegate ItemAdded;

		TreeViewNode m_root = null;

		public TreeViewNodeCollection(TreeViewNode root)
		{
			m_root = root;
		}

		public int Count
		{
			get { return m_list.Count; }
		}

		public void Add(TreeViewNode node)
		{
			m_list.Add(node);
			node.Parent = m_root;

			if (ItemAdded != null)
				ItemAdded(this, node);
		}

		public void Clear()
		{
			m_list.Clear();
		}

		public void Sort( Comparison<TreeViewNode> comparison)
		{
			m_list.Sort(comparison);
		}

		public List<TreeViewNode>.Enumerator GetEnumerator()
		{
			return m_list.GetEnumerator();
		}

		public TreeViewNode this[int index]
		{
			get { return ((TreeViewNode)(m_list[index])); }
			set { m_list[index] = value; }
		}
	}
}