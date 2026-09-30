using System;
using System.Data;
using System.Data.OleDb;

namespace Rgls.Cig.Utility
{
	public class Statement
	{
		protected OleDbCommand p_oCommand = null;

		protected DBConnection p_oDbConnection;

		public Statement(DBConnection dbconn)
		{
			this.p_oDbConnection = dbconn;
			this.p_oCommand = new OleDbCommand();
			if (dbconn != null)
			{
				this.p_oCommand.Connection = dbconn.Connection;
			}
			this.p_oCommand.CommandType = CommandType.Text;
		}

		public virtual OleDbDataReader executeQuery(string sql)
		{
			this.p_oCommand.CommandText = sql;
			this.p_oCommand.Transaction = this.p_oDbConnection.Transaction;
			return this.p_oCommand.ExecuteReader(CommandBehavior.Default);
		}

		public virtual int executeUpdate(string sql)
		{
			this.p_oCommand.CommandText = sql;
			this.p_oCommand.Transaction = this.p_oDbConnection.Transaction;
			return this.p_oCommand.ExecuteNonQuery();
		}

		public string createParam(string sNome, object oParam)
		{
			if (oParam != null && oParam.GetType().ToString() == "System.String" && (string)oParam == "")
			{
				this.p_oCommand.Parameters.AddWithValue(sNome, null);
			}
			else
			{
				this.p_oCommand.Parameters.AddWithValue(sNome, oParam);
			}
			return sNome;
		}

		public string createTimeParam(string sNome, DateTime oParam)
		{
			if (oParam != DateTime.MinValue)
			{
				this.p_oCommand.Parameters.AddWithValue(sNome, oParam);
			}
			else
			{
				this.p_oCommand.Parameters.AddWithValue(sNome, null);
			}
			return sNome;
		}

		public void clearParams()
		{
			this.p_oCommand.Parameters.Clear();
		}

		public static object ColObject(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			object result;
			if (dr.IsDBNull(ordinal))
			{
				if (dr.GetDataTypeName(ordinal) == "DBTYPE_VARCHAR")
				{
					result = "";
				}
				else
				{
					result = null;
				}
			}
			else
			{
				result = dr.GetValue(ordinal);
			}
			return result;
		}

		public static int ReadInt(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			int result;
			if (dr.IsDBNull(ordinal))
			{
				result = 0;
			}
			else
			{
				result = dr.GetInt32(ordinal);
			}
			return result;
		}

		public static short ReadShort(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			short result;
			if (dr.IsDBNull(ordinal))
			{
				result = 0;
			}
			else
			{
				result = dr.GetInt16(ordinal);
			}
			return result;
		}

		public static float ReadFloat(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			float result;
			if (dr.IsDBNull(ordinal))
			{
				result = 0f;
			}
			else
			{
				result = dr.GetFloat(ordinal);
			}
			return result;
		}

		public static byte ReadByte(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			byte result;
			if (dr.IsDBNull(ordinal))
			{
				result = 0;
			}
			else
			{
				result = dr.GetByte(ordinal);
			}
			return result;
		}

		public static decimal ReadCurrency(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			decimal result;
			if (dr.IsDBNull(ordinal))
			{
				result = 0m;
			}
			else
			{
				result = dr.GetDecimal(ordinal);
			}
			return result;
		}

		public static long ReadLong(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			long result;
			if (dr.IsDBNull(ordinal))
			{
				result = 0L;
			}
			else
			{
				result = (long)dr.GetInt32(ordinal);
			}
			return result;
		}

		public static string ReadString(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			string result;
			if (dr.IsDBNull(ordinal))
			{
				result = "";
			}
			else
			{
				result = dr.GetString(ordinal);
			}
			return result;
		}

		public static bool ReadBoolean(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			return !dr.IsDBNull(ordinal) && dr.GetBoolean(ordinal);
		}

		public static DateTime ReadDateTime(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			DateTime result;
			if (dr.IsDBNull(ordinal))
			{
				result = new DateTime(1753, 1, 1);
			}
			else
			{
				result = dr.GetDateTime(ordinal);
			}
			return result;
		}

		public static DateTime ColTimeObject(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			DateTime result;
			if (dr.IsDBNull(ordinal))
			{
				result = DateTime.MinValue;
			}
			else
			{
				result = (DateTime)dr.GetValue(ordinal);
			}
			return result;
		}

		public static int ColIntObject(OleDbDataReader dr, string name)
		{
			int ordinal = dr.GetOrdinal(name);
			int result;
			if (dr.IsDBNull(ordinal))
			{
				result = 0;
			}
			else
			{
				result = (int)dr.GetValue(ordinal);
			}
			return result;
		}
	}
}
