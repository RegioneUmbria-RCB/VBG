using System;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGeProExport.Data
{
	/// <summary>
	/// Descrizione di riepilogo per BaseClass.
	/// </summary>
	[Serializable]
	public class BaseDataClass : DataClass
	{
		public BaseDataClass()
		{
			UseForeign = useForeignEnum.Yes;
			InvalidateCache();
		}



		public void InvalidateCache()
		{
			OnInvalidateCache();
		}


		protected virtual void OnInvalidateCache()
		{
		}


		public string DBTable()
		{
			Type t = this.GetType();

			DataTableAttribute[] tab = (DataTableAttribute[])t.GetCustomAttributes( typeof(DataTableAttribute),true );

			if (tab.Length > 0) return tab[0].TableName;

			return String.Empty;
		}
	}
}

