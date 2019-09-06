using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System.IO;
using System.IO.Compression;
using System.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversioneVersioniDataSetDomanda
{
	public class  V1DataSetSerializer
	{
		public byte[] Serialize(PresentazioneIstanzaDataSet dataSet)
		{
			using (MemoryStream ms = new MemoryStream())
			{
				using (var gz = new GZipStream(ms, CompressionMode.Compress, false))
				{
					//gz.Write(b, 0, b.Length);
					dataSet.WriteXml(gz);
				}

				return ms.ToArray();
			}
		}

		public PresentazioneIstanzaDataSet Deserialize(byte[] dati, bool enforceConstraints)
		{
			PresentazioneIstanzaDataSet data = new PresentazioneIstanzaDataSet();
			data.EnforceConstraints = enforceConstraints;

			try
			{
				using (var ms = new MemoryStream(dati))
				{
					using (var gz = new GZipStream(ms, CompressionMode.Decompress, false))
					{
						try
						{
							data.ReadXml(gz);
						}
						catch (ConstraintException)
						{
							for (int i = 0; i < data.Tables.Count; i++)
							{
								for (int j = 0; j < data.Tables[i].Rows.Count; j++)
								{
									var row = data.Tables[i].Rows[j];

									if (!String.IsNullOrEmpty(row.RowError))
										throw new ConstraintException("Table: " + data.Tables[i].TableName + " row:" + j + " error:" + row.RowError);

								}
							}

							throw;
						}
					}
				}

				return data;
			}
			catch (System.IO.InvalidDataException)	// La domanda non è compressa
			{
				using (MemoryStream ms = new MemoryStream())
				{
					ms.Write(dati, 0, dati.Length);
					ms.Seek(0, SeekOrigin.Begin);
					data.ReadXml(ms);

					return data;
				}
			}
		}
	}
}
