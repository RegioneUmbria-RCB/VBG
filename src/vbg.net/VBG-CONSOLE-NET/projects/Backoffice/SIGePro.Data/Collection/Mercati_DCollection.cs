using System;
using System.Collections;
using Init.SIGePro.Data;
// ------------------------------------------------------------------------------
// <copyright from='1997' to='2001' company='Microsoft Corporation'>
//    Copyright (c) Microsoft Corporation. All Rights Reserved.   
//    Information Contained Herein is Proprietary and Confidential.       
// </copyright> 
// ------------------------------------------------------------------------------
// 

namespace Init.SIGePro.Collection
{
	/// <summary>
	///     <para>
	///       A collection that stores <see cref='Mercati_D'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='Init.SIGePro.Collection.Mercati_DCollection'/>
	[Serializable()]
	public class Mercati_DCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DCollection'/>.
		///    </para>
		/// </summary>
		public Mercati_DCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> based on another <see cref='Init.SIGePro.Collection.Mercati_DCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> from which the contents are copied
		/// </param>
		public Mercati_DCollection(Mercati_DCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> containing any array of <see cref='Mercati_D'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Mercati_D'/> objects with which to intialize the collection
		/// </param>
		public Mercati_DCollection(Mercati_D[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> containing any array of <see cref='Mercati_D'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Mercati_D'/> objects with which to intialize the collection
		/// </param>
		public Mercati_DCollection(ArrayList value)
		{
			for( int i=0; i<value.Count; i++)
				this.Add(value[i] as Mercati_D);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='Mercati_D'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public Mercati_D this[int index]
		{
			get { return ((Mercati_D) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='Mercati_D'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_D'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DCollection.AddRange'/>
		public int Add(Mercati_D value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.Mercati_DCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='Mercati_D'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DCollection.Add'/>
		public void AddRange(Mercati_D[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DCollection.Add'/>
		public void AddRange(Mercati_DCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> contains the specified <see cref='Mercati_D'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_D'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='Mercati_D'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DCollection.IndexOf'/>
		public bool Contains(Mercati_D value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(Mercati_D[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='Mercati_D'/> in 
		///       the <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_D'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='Mercati_D'/> of <paramref name='value'/> in the 
		/// <see cref='Init.SIGePro.Collection.Mercati_DCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DCollection.Contains'/>
		public int IndexOf(Mercati_D value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='Mercati_D'/> into the <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='Mercati_D'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DCollection.Add'/>
		public void Insert(int index, Mercati_D value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public Mercati_DEnumerator GetEnumerator()
		{
			return new Mercati_DEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='Mercati_D'/> from the 
		///    <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_D'/> to remove from the <see cref='Init.SIGePro.Collection.Mercati_DCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(Mercati_D value)
		{
			List.Remove(value);
		}

		public class Mercati_DEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public Mercati_DEnumerator(Mercati_DCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public Mercati_D Current
			{
				get { return ((Mercati_D) (baseEnumerator.Current)); }
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