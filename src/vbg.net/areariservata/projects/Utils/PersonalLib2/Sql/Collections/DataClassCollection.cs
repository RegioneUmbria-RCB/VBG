using System;
using System.Collections;
using System.Collections.Generic;
// ------------------------------------------------------------------------------
// <copyright from='1997' to='2001' company='Microsoft Corporation'>
//    Copyright (c) Microsoft Corporation. All Rights Reserved.   
//    Information Contained Herein is Proprietary and Confidential.       
// </copyright> 
// ------------------------------------------------------------------------------
// 

namespace PersonalLib2.Sql.Collections
{
	/// <summary>
	///     <para>
	///       A collection that stores <see cref='DataClass'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='PersonalLib2.Sql.Collections.DataClassCollection'/>
	[Serializable()]
	public class DataClassCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/>.
		///    </para>
		/// </summary>
		public DataClassCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> based on another <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> from which the contents are copied
		/// </param>
		public DataClassCollection(DataClassCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> containing any array of <see cref='PersonalLib2.Sql.DataClass'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='PersonalLib2.Sql.DataClass'/> objects with which to intialize the collection
		/// </param>
		public DataClassCollection(DataClass[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='PersonalLib2.Sql.DataClass'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public DataClass this[int index]
		{
			get { return ((DataClass) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='PersonalLib2.Sql.DataClass'/> with the specified value to the 
		///    <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='PersonalLib2.Sql.DataClass'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='PersonalLib2.Sql.Collections.DataClassCollection.AddRange'/>
		public int Add(DataClass value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='PersonalLib2.Sql.DataClass'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='PersonalLib2.Sql.Collections.DataClassCollection.Add'/>
		public void AddRange(DataClass[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='PersonalLib2.Sql.Collections.DataClassCollection.Add'/>
		public void AddRange(DataClassCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> contains the specified <see cref='PersonalLib2.Sql.DataClass'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='PersonalLib2.Sql.DataClass'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='PersonalLib2.Sql.DataClass'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='PersonalLib2.Sql.Collections.DataClassCollection.IndexOf'/>
		public bool Contains(DataClass value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(DataClass[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='PersonalLib2.Sql.DataClass'/> in 
		///       the <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='PersonalLib2.Sql.DataClass'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='PersonalLib2.Sql.DataClass'/> of <paramref name='value'/> in the 
		/// <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='PersonalLib2.Sql.Collections.DataClassCollection.Contains'/>
		public int IndexOf(DataClass value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='PersonalLib2.Sql.DataClass'/> into the <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='PersonalLib2.Sql.DataClass'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='PersonalLib2.Sql.Collections.DataClassCollection.Add'/>
		public void Insert(int index, DataClass value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public DataClassEnumerator GetEnumerator()
		{
			return new DataClassEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='PersonalLib2.Sql.DataClass'/> from the 
		///    <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='PersonalLib2.Sql.DataClass'/> to remove from the <see cref='PersonalLib2.Sql.Collections.DataClassCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(DataClass value)
		{
			List.Remove(value);
		}

		public T[] ToArray<T>()
		{
			T[] arr = new T[ this.InnerList.Count ];

			return (T[])this.InnerList.ToArray(typeof(T));
		}

		public List<T> ToList<T>()
		{
			return new List<T>(this.ToArray<T>());
		}


		public static implicit operator ArrayList(DataClassCollection coll)
		{
			return new ArrayList(coll);
		}


		public class DataClassEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public DataClassEnumerator(DataClassCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public DataClass Current
			{
				get { return ((DataClass) (baseEnumerator.Current)); }
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