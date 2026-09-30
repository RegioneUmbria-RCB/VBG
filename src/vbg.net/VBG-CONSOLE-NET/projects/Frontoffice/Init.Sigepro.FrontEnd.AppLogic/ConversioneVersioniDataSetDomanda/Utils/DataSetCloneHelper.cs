using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda.Utils
{
	public class DataSetCloneHelper<DSSrc,DSDst> where DSSrc:DataSet where DSDst:DataSet,new()
	{
		public DSDst CreateFrom( DSSrc srcDataset )
		{
			var dstDataSet = new DSDst();

			foreach (var srcTable in srcDataset.Tables.Cast<DataTable>())
			{
				var dstTables = dstDataSet.Tables.Cast<DataTable>()
												 .Where(x => x.TableName == srcTable.TableName);

				if (dstTables == null || dstTables.Count() == 0)
					continue;

				var dstTable = dstTables.ElementAt(0);

				var nomiCampiInComune = new List<string>();

				foreach (var srcCol in srcTable.Columns.Cast<DataColumn>())
				{
					var dstCols = dstTables.ElementAt(0).Columns.Cast<DataColumn>().Where( x => x.ColumnName == srcCol.ColumnName );

					if( dstCols != null && dstCols.Count() > 0 )
						nomiCampiInComune.Add( srcCol.ColumnName );
				}

				for (int i = 0; i < srcTable.Rows.Count; i++)
				{
					var srcRow = srcTable.Rows[i];
					var dstRow = dstTable.NewRow();

					foreach(var nomeCampo in nomiCampiInComune)
						dstRow[nomeCampo] = srcRow[nomeCampo];

					dstTable.Rows.Add(dstRow);
				}				
			}

			dstDataSet.AcceptChanges();

			return dstDataSet;
		}
	}
}
