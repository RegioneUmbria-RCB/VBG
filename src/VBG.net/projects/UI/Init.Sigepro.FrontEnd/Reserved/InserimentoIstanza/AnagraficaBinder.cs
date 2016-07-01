using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public class AnagraficaBinder
	{
		public AnagraficaBinder()
		{
			FoKernelContainer.Inject(this);
		}



		/// <summary>
		/// Ottiene il valore SAFE di una DataColumn di una DataRow dato che i dataset tipizzati hanno qualche problema con i dbNull...
		/// </summary>
		/// <param name="dataRow">DataRow da cui prendere il dato</param>
		/// <param name="columnName">Nome della colonna che contiene il dato</param>
		/// <returns>Valore <see cref="string"/> contenuto nella colonna o String.Empty se DbNull</returns>
		public static string SafeValue(DataRow dataRow, string columnName)
		{
			return SafeValue(dataRow, columnName, String.Empty);
		}

		/// <summary>
		/// Ottiene il valore SAFE di una DataColumn di una DataRow dato che i dataset tipizzati hanno qualche problema con i dbNull...
		/// </summary>
		/// <param name="dataRow">DataRow da cui prendere il dato</param>
		/// <param name="columnName">Nome della colonna che contiene il dato</param>
		/// <param name="defaultValue">Valore di default da ritornare se DbNull</param>
		/// <returns>Valore <see cref="String"/> contenuto nella colonna o valore di default impostato se DbNull</returns>
		public static string SafeValue(DataRow dataRow, string columnName, string defaultValue)
		{
			if (dataRow[columnName] == DBNull.Value)
				return defaultValue;
			else
				return dataRow[columnName].ToString();
		}

		//public static bool EvalEnabled(DataRow dataRow, string fieldName)
		//{
		//    return String.IsNullOrEmpty(SafeValue(dataRow, fieldName));
		//}

		//public static bool EvalReadOnly(DataRow dataRow, string fieldName)
		//{
		//    return !String.IsNullOrEmpty(SafeValue(dataRow, fieldName));
		//}

	}
}
