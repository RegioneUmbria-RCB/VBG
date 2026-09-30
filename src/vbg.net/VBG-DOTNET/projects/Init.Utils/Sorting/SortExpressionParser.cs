using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;

namespace Init.Utils.Sorting
{
	public partial class SortExpressionParser<T>
	{
		public static PropertyDescriptor ParseProperty(string sortExpression)
		{
			string[] parts = sortExpression.Split(' ');

			string propName = parts[0];

			PropertyDescriptorCollection props = TypeDescriptor.GetProperties(typeof(T));
			return props.Find(propName, true);
		}

		public static ListSortDirection ParseDirection(string sortExpression)
		{
			string[] parts = sortExpression.Split(' ');

			if (parts.Length <= 1) return ListSortDirection.Ascending;

			return parts[1].ToUpper() == "ASC" ? ListSortDirection.Ascending : ListSortDirection.Descending;
		}
	}
}
