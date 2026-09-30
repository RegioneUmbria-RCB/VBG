using System;
using System.Diagnostics;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Descrizione di riepilogo per DataGridGrouper.
	/// </summary>
	public class DataGridGroupFormatter
	{
		private DataGrid _dg;
		private int[] _colToGroupBy;
		private DataGridItem _prevItem;


		public DataGridGroupFormatter(DataGrid p_dg, int[] p_colToGroup)
		{
			SetDataGrid(p_dg);
			_colToGroupBy = p_colToGroup;

			_prevItem = null;


		}


		private bool IsGoupedCol(int colId)
		{
			for (int i = 0; i < _colToGroupBy.Length; i++)
				if (colId == _colToGroupBy[i]) return true;

			return false;

		}


		private void _dg_ItemDataBound(object sender, DataGridItemEventArgs e)
		{
			DataGridItem it = e.Item;
			TableCell c;

			if (it.ItemType == ListItemType.SelectedItem ||
				it.ItemType == ListItemType.Item || it.ItemType == ListItemType.AlternatingItem ||
				it.ItemType == ListItemType.EditItem)
			{
				for (int i = 0; i < it.Cells.Count; i++)
				{
					it.Cells[i].RowSpan = 1;
				}


				if (_prevItem != null)
				{
					for (int i = 0; i < it.Cells.Count; i++)
					{
						if (IsGoupedCol(i))
						{
							c = it.Cells[i];
							c.Visible = true;

							if (c.Text.Trim() == _prevItem.Cells[i].Text.Trim() && !String.IsNullOrEmpty(c.Text.Trim()))
							{
								for (int j = (it.ItemIndex - 1); j >= 0; j--)
								{
									DataGridItem it2 = _dg.Items[j];

									if (!DataGridItem.ReferenceEquals(it, it2))
									{
										if (
											it2.Cells[i].Text.Trim() == c.Text.Trim() &&
												it2.Cells[i].Visible
											)
										{
											it2.Cells[i].RowSpan += 1;
											c.Visible = false;
											break;
										}
									}
								}
							}
						}
					}

					_prevItem = it;
				}
				else
				{
					_prevItem = it;
				}
			}
		}


		private void _dg_PreRender(object sender, EventArgs e)
		{
			DataGridItem lastItem = null;
			bool isAlternating = false;

			foreach (DataGridItem it in _dg.Items)
			{
				if (it.ItemType == ListItemType.Item || it.ItemType == ListItemType.AlternatingItem)
				{
					if (lastItem == null)
					{
						lastItem = it;
						it.CssClass = _dg.ItemStyle.CssClass;
						isAlternating = false;
						continue;
					}

					if (it.Cells[0].Visible)
					{
						lastItem = it;
						isAlternating = !isAlternating;
					}

					it.CssClass = (isAlternating) ? _dg.AlternatingItemStyle.CssClass : _dg.ItemStyle.CssClass;
				}
			}
		}

		#region Membri di IDataGridFormatter

		public void SetDataGrid(DataGrid p_grid)
		{
			_dg = p_grid;
			Debug.Assert(_dg != null);
			_dg.ItemDataBound += new DataGridItemEventHandler(_dg_ItemDataBound);
			_dg.PreRender += new EventHandler(_dg_PreRender);
		}

		#endregion
	}
}