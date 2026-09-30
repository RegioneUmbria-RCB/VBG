using System;
using System.Diagnostics;
using System.IO;
using PersonalLib2.Data;
using PersonalLib2.Sql;

namespace SIGePro.Net.Data
{
	/// <summary>
	/// Descrizione di riepilogo per DataImport.
	/// </summary>
	public class DataImport : DataClass
	{
		protected string m_filePath = "";
		protected bool m_isStepFirstRow = true;
		protected string m_connectionString = "";

		public string FilePath
		{
			get { return m_filePath; }
			set { m_filePath = value; }
		}

		public bool IsStepFirstRow
		{
			get { return m_isStepFirstRow; }
			set { m_isStepFirstRow = value; }
		}

		public string ConnectionString
		{
			get { return m_connectionString; }
			set { m_connectionString = value; }
		}

		public DataImport()
		{
		}

		public DataImport(string p_filePath)
		{
			m_filePath = p_filePath;
		}

		public DataImport(string p_filePath, bool p_isStepFirstRow)
		{
			m_filePath = p_filePath;
			m_isStepFirstRow = p_isStepFirstRow;
		}

		public static DataImport Instance(string p_filePath, bool p_isStepFirstRow, string p_connectionString)
		{
			return new DataImport(p_filePath, p_isStepFirstRow, p_connectionString);
		}

		public DataImport(string p_filePath, bool p_isStepFirstRow, string p_connectionString)
		{
			m_filePath = p_filePath;
			m_isStepFirstRow = p_isStepFirstRow;
			m_connectionString = p_connectionString;
		}

		public int RunTmp(Stream istream)
		{
			return GetImport(istream);
		}

		public int RunTmp(string p_connectionString, Stream istream)
		{
			m_connectionString = p_connectionString;
			return GetImport(istream);
		}

		private int GetImport(Stream istream)
		{
			TextFileReader reader = new TextFileReader(istream);
			ClassesBoxCollection classi = reader.ReadFile(m_isStepFirstRow);
			int rec = 0;
			for (int i = 0; i < classi.Count; i++)
			{
				try
				{
					DataBase db = new DataBase(m_connectionString, ProviderType.OleDb);
					rec += db.Insert(classi[i].TmpImport);
					db.Dispose();

				}
				catch (Exception ex)
				{
					EventLog.WriteEntry("CCIAAImport [" + classi[i].TmpImport.PRG + "]", ex.Message);
				}
			}
			return rec;
		}
	}
}