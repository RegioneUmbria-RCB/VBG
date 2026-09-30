using System;
using System.Collections;
using PersonalLib2.Data;

namespace Init.SIGePro.Collection
{
	[Serializable()]
	public class DataBaseCollection : CollectionBase
	{
		public DataBaseCollection()
		{
		}

		public DataBaseCollection(DataBaseCollection value)
		{
			this.AddRange(value);
		}

		public DataBaseCollection(DataBase[] value)
		{
			this.AddRange(value);
		}

		public DataBase this[int index]
		{
			get { return ((DataBase) (List[index])); }
			set { List[index] = value; }
		}

		public int Add(DataBase value)
		{
			return List.Add(value);
		}

		public void AddRange(DataBase[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}


		public void AddRange(DataBaseCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}


		public bool Contains(DataBase value)
		{
			return List.Contains(value);
		}


		public void CopyTo(DataBase[] array, int index)
		{
			List.CopyTo(array, index);
		}


		public int IndexOf(DataBase value)
		{
			return List.IndexOf(value);
		}


		public void Insert(int index, DataBase value)
		{
			List.Insert(index, value);
		}


		new public DataBaseEnumerator GetEnumerator()
		{
			return new DataBaseEnumerator(this);
		}


		public void Remove(DataBase value)
		{
			List.Remove(value);
		}

		public class DataBaseEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public DataBaseEnumerator(DataBaseCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public DataBase Current
			{
				get { return ((DataBase) (baseEnumerator.Current)); }
			}

			object IEnumerator.Current
			{
				get { return baseEnumerator.Current; }
			}

			public bool MoveNext()
			{
				return baseEnumerator.MoveNext();
			}

			bool IEnumerator.MoveNext()
			{
				return baseEnumerator.MoveNext();
			}

			public void Reset()
			{
				baseEnumerator.Reset();
			}

			void IEnumerator.Reset()
			{
				baseEnumerator.Reset();
			}
		}
	}
}
