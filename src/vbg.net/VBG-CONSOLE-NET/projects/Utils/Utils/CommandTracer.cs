using System;
using System.Collections.Generic;
using System.Text;
using System.Data;

namespace PersonalLib2.Utils
{
	public partial class CommandTracer
	{
		public static string Trace(IDbCommand cmd)
		{
			StringBuilder sb = new StringBuilder();
			sb.Append("[Command]").Append(Environment.NewLine);
			sb.Append(cmd.CommandText).Append(Environment.NewLine).Append(Environment.NewLine);

			if (cmd.Parameters.Count > 0)
			{
				sb.Append("[Parametri]").Append(Environment.NewLine);

				foreach (IDbDataParameter par in cmd.Parameters)
				{
					sb.Append("[").Append(par.Direction).Append("]").Append(par.ParameterName);
					sb.Append(" (").Append(par.DbType).Append("): ");

					if (par.Value == null || par.Value == DBNull.Value)
						sb.Append("null");
					else
						sb.Append(par.Value);

					sb.Append(Environment.NewLine);

				}
			}

			return sb.ToString();
		}
	}
}
