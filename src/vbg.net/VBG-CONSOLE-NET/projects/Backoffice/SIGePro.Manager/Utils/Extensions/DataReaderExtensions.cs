// -----------------------------------------------------------------------
// <copyright file="DataReaderExtensions.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Utils.Extensions
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public static class DataReaderExtensions
	{
		public static IEnumerable<T> Select<T>(this IDataReader reader,
									   Func<IDataReader, T> projection)
		{
			while (reader.Read())
			{
				yield return projection(reader);
			}
		}

		public static List<T> SelectAll<T>(this IDbCommand cmd, Func<IDataReader, T> projection)
		{
			using (var dr = cmd.ExecuteReader())
			{
				return dr.Select(projection).ToList();
			}
		}
	}
}
