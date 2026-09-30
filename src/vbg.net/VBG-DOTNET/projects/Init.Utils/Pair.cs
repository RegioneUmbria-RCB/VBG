namespace Init.Utils
{
	/// <summary>
	/// Rappresenta una coppia di valori
	/// </summary>
	public class Pair<T>
	{
		private T m_first;
		private T m_second;

		public T First
		{
			get { return m_first; }
			set { m_first = value; }
		}

		public T Second
		{
			get { return m_second; }
			set { m_second = value; }
		}

		public Pair()
		{
		}

		public Pair(T first, T second)
		{
			m_first = first;
			m_second = second;
		}
	}
}