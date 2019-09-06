using System;

namespace SIGePro.Net.Data
{
	/// <summary>
	/// Descrizione di riepilogo per TextFileInfoAttribute.
	/// </summary>
	public class TextFileInfoAttribute : Attribute
	{
		private int m_startAt = 0;
		private int m_size = 0;
		private bool m_nullable = true;

		public int StartAt
		{
			get { return m_startAt; }
			set { m_startAt = value; }
		}

		public int Size
		{
			get { return m_size; }
			set { m_size = value; }
		}

		public bool Nullable
		{
			get { return m_nullable; }
			set { m_nullable = value; }
		}

		public TextFileInfoAttribute(int startAt, int size)
		{
			m_startAt = startAt;
			m_size = size;

		}
	}
}