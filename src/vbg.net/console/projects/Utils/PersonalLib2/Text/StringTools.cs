using System.Collections;
using System.Text;

namespace PersonalLib2.Text
{
	/// <summary>
	/// Descrizione di riepilogo per StringTools.
	/// </summary>
	public class StringTools
	{
		protected StringTools()
		{
		}

		public static string Join(ArrayList arrayList, string separator)
		{
			StringBuilder sb = new StringBuilder();
			foreach (string col in arrayList)
			{
				sb.Append(col);
				sb.Append(separator);
			}
			if (sb.Length > 0) sb.Remove(sb.Length - separator.Length, separator.Length);
			return sb.ToString();
		}
	}
}