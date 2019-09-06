using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using System.Diagnostics;

namespace Init.SIGePro.DatiDinamici.Statistiche
{
	public static class TipoConfrontoFiltroDictionary
	{
		public static Dictionary<TipoConfrontoFiltroEnum, string> Get
		{
			get
			{
				Dictionary<TipoConfrontoFiltroEnum, string> ret = new Dictionary<TipoConfrontoFiltroEnum, string>();

				ret[TipoConfrontoFiltroEnum.Equal] = "è uguale a";
				ret[TipoConfrontoFiltroEnum.NotEqual] = "è diverso da";
				ret[TipoConfrontoFiltroEnum.LessThan] = "è minore di";
				ret[TipoConfrontoFiltroEnum.LessThanOrEqual] = "è minore o uguale a";
				ret[TipoConfrontoFiltroEnum.GreaterThan] = "è maggiore di";
				ret[TipoConfrontoFiltroEnum.GreaterThanOrEqual] = "è maggiore o uguale a";
				ret[TipoConfrontoFiltroEnum.Null] = "è vuoto";
				ret[TipoConfrontoFiltroEnum.NotNull] = "non è vuoto";
				ret[TipoConfrontoFiltroEnum.Like] = "è simile a";

				return ret;
			}
		}

		public static List<KeyValuePair<TipoConfrontoFiltroEnum, string>> GetFiltriSupportati(string fullyQualifiedTypeName)
		{
			Type t = Type.GetType(fullyQualifiedTypeName);

			List<KeyValuePair<TipoConfrontoFiltroEnum, string>> ret = new List<KeyValuePair<TipoConfrontoFiltroEnum, string>>();

			if (t == null)
			{
				Debug.WriteLine("Impossibile istanziare il tipo " + fullyQualifiedTypeName);
				return ret;
			}

			MethodInfo mi = t.GetMethod("GetTipiConfrontoSupportati", BindingFlags.Static | BindingFlags.Public);

			if (mi == null)
			{
				Debug.WriteLine("il tipo " + fullyQualifiedTypeName + " non contiene il metodo GetTipiConfrontoSupportati");
				return ret;
			}

			TipoConfrontoFiltroEnum[] filtri = (TipoConfrontoFiltroEnum[])mi.Invoke(null, null);

			for (int i = 0; i < filtri.Length; i++)
			{
				TipoConfrontoFiltroEnum key = filtri[i];

				ret.Add(new KeyValuePair<TipoConfrontoFiltroEnum, string>(key, Get[key]));
			}

			return ret;
		}
	}

}
