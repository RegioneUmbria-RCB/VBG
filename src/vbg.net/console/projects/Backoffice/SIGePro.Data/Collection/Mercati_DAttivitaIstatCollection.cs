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
	///       A collection that stores <see cref='Mercati_DAttivitaIstat'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/>
	[Serializable()]
	public class Mercati_DAttivitaIstatCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/>.
		///    </para>
		/// </summary>
		public Mercati_DAttivitaIstatCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> based on another <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> from which the contents are copied
		/// </param>
		public Mercati_DAttivitaIstatCollection(Mercati_DAttivitaIstatCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> containing any array of <see cref='Mercati_DAttivitaIstat'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Mercati_DAttivitaIstat'/> objects with which to intialize the collection
		/// </param>
		public Mercati_DAttivitaIstatCollection(Mercati_DAttivitaIstat[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> containing any array of <see cref='Mercati_DAttivitaIstat'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Mercati_DAttivitaIstat'/> objects with which to intialize the collection
		/// </param>
		public Mercati_DAttivitaIstatCollection(ArrayList value)
		{
			for( int i=0; i<value.Count; i++)
				this.Add(value[i] as Mercati_DAttivitaIstat);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='Mercati_DAttivitaIstat'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public Mercati_DAttivitaIstat this[int index]
		{
			get { return ((Mercati_DAttivitaIstat) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='Mercati_DAttivitaIstat'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_DAttivitaIstat'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection.AddRange'/>
		public int Add(Mercati_DAttivitaIstat value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='Mercati_DAttivitaIstat'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection.Add'/>
		public void AddRange(Mercati_DAttivitaIstat[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection.Add'/>
		public void AddRange(Mercati_DAttivitaIstatCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> contains the specified <see cref='Mercati_DAttivitaIstat'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_DAttivitaIstat'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='Mercati_DAttivitaIstat'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection.IndexOf'/>
		public bool Contains(Mercati_DAttivitaIstat value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(Mercati_DAttivitaIstat[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='Mercati_DAttivitaIstat'/> in 
		///       the <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_DAttivitaIstat'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='Mercati_DAttivitaIstat'/> of <paramref name='value'/> in the 
		/// <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection.Contains'/>
		public int IndexOf(Mercati_DAttivitaIstat value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='Mercati_DAttivitaIstat'/> into the <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='Mercati_DAttivitaIstat'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection.Add'/>
		public void Insert(int index, Mercati_DAttivitaIstat value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public Mercati_DAttivitaIstatEnumerator GetEnumerator()
		{
			return new Mercati_DAttivitaIstatEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='Mercati_DAttivitaIstat'/> from the 
		///    <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_DAttivitaIstat'/> to remove from the <see cref='Init.SIGePro.Collection.Mercati_DAttivitaIstatCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(Mercati_DAttivitaIstat value)
		{
			List.Remove(value);
		}

		public class Mercati_DAttivitaIstatEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public Mercati_DAttivitaIstatEnumerator(Mercati_DAttivitaIstatCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public Mercati_DAttivitaIstat Current
			{
				get { return ((Mercati_DAttivitaIstat) (baseEnumerator.Current)); }
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