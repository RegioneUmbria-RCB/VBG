using System;
using System.Data;
using System.Diagnostics;
using System.Reflection;
using Init.Utils;
using PersonalLib2.Data;

namespace SIGePro.Net.Data
{
	/// <summary>
	/// Descrizione di riepilogo per TextFileReader.
	/// </summary>
	public class DataTableReader
	{
		public DataTableReader()
		{
		}

		public object PopolaClasse(IDataReader p_dr, Type classType)
		{
			object retVal = Activator.CreateInstance(classType);
			PropertyInfo[] classProperties = (PropertyInfo[]) classType.GetProperties();

			for (int i = 0; i < p_dr.FieldCount; i++)
			{
				if (p_dr[i] != DBNull.Value && ! StringChecker.IsStringEmpty(p_dr[i].ToString()))
				{
					if (classProperties[i].PropertyType.ToString() == "System.DateTime")
					{
						try
						{
							classProperties[i].SetValue(retVal, Convert.ToDateTime(p_dr[i].ToString()), null);
						}
						catch (Exception ex)
						{
							EventLog.WriteEntry("CCIAAImport - NOME = " + p_dr.GetName(i) + " - " + p_dr[i].ToString() + "-" + p_dr[0].ToString() + "-" + p_dr[1].ToString(), ex.Message);
						}
					}
					else
					{
						classProperties[i].SetValue(retVal, p_dr[i], null);
					}
				}
			}

			return retVal;

		}

		public int recAffected(string p_sql, DataBase p_db)
		{
			IDbCommand cmd = p_db.CreateCommand();
			cmd.CommandText = p_sql;
			int retVal = Convert.ToInt32(cmd.ExecuteScalar());

			return retVal;
		}
	}
}