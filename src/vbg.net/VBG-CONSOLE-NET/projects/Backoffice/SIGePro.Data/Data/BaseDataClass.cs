using System;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System.Text.RegularExpressions;
using System.ComponentModel;

namespace Init.SIGePro.Data
{
	/// <summary>
	/// Descrizione di riepilogo per BaseClass.
	/// </summary>
	[Serializable]
	public class BaseDataClass : DataClass
	{
		public BaseDataClass()
		{
			InvalidateCache();
		}



		public void InvalidateCache()
		{
			OnInvalidateCache();
		}


		protected virtual void OnInvalidateCache()
		{
		}

        public DateTime? VerificaDataLocale(DateTime? data)
        {
            if (data.GetValueOrDefault(DateTime.MinValue)== DateTime.MinValue) return null;
            if (data.Value.Kind == DateTimeKind.Unspecified)
            {
                return data.Value;
            }
            else
            {
                return data.Value.ToLocalTime();
            }
        }

		public string GetDBTableName()
		{
			Type t = this.GetType();

			DataTableAttribute[] tab = (DataTableAttribute[])t.GetCustomAttributes( typeof(DataTableAttribute),true );

			if (tab.Length > 0) return tab[0].TableName;

			return String.Empty;
		}

		public string ToString(string formatString)
		{
			Match m = Regex.Match(formatString, @"\{[\w_]+\}");

			while (m.Success)
			{
				PropertyDescriptorCollection propColl = TypeDescriptor.GetProperties(this);

				foreach (Capture c in m.Captures)
				{
					string matchValue = c.Value;
					string propName = matchValue.Substring(1, matchValue.Length - 2);

					object propVal = String.Empty;

					PropertyDescriptor pd = propColl[propName];

					if (pd != null)
						propVal = pd.GetValue(this);

					if (propVal == null) propVal = String.Empty;

					formatString = formatString.Replace(matchValue, propVal.ToString());

					m = m.NextMatch();
				}
			}

			return formatString;

		}
	}
}
