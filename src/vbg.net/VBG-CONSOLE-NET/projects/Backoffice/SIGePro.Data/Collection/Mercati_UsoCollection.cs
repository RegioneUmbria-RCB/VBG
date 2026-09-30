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
	///       A collection that stores <see cref='Mercati_Uso'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='Init.SIGePro.Collection.Mercati_UsoCollection'/>
	[Serializable()]
	public class Mercati_UsoCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/>.
		///    </para>
		/// </summary>
		public Mercati_UsoCollection ()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> based on another <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> from which the contents are copied
		/// </param>
		public Mercati_UsoCollection (Mercati_UsoCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> containing any array of <see cref='Mercati_Uso'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Mercati_Uso'/> objects with which to intialize the collection
		/// </param>
		public Mercati_UsoCollection (Mercati_Uso[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> containing any array of <see cref='Mercati_Uso'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Mercati_Uso'/> objects with which to intialize the collection
		/// </param>
		public Mercati_UsoCollection (ArrayList value)
		{
			for( int i=0; i<value.Count; i++)
				this.Add(value[i] as Mercati_Uso);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='Mercati_Uso'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public Mercati_Uso this[int index]
		{
			get { return ((Mercati_Uso) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='Mercati_Uso'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_Uso'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_UsoCollection.AddRange'/>
		public int Add(Mercati_Uso value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='Mercati_Uso'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_UsoCollection.Add'/>
		public void AddRange(Mercati_Uso[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_UsoCollection.Add'/>
		public void AddRange(Mercati_UsoCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> contains the specified <see cref='Mercati_Uso'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_Uso'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='Mercati_Uso'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_UsoCollection.IndexOf'/>
		public bool Contains(Mercati_Uso value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(Mercati_Uso[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='Mercati_Uso'/> in 
		///       the <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_Uso'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='Mercati_Uso'/> of <paramref name='value'/> in the 
		/// <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_UsoCollection.Contains'/>
		public int IndexOf(Mercati_Uso value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='Mercati_Uso'/> into the <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='Mercati_Uso'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='Init.SIGePro.Collection.Mercati_UsoCollection.Add'/>
		public void Insert(int index, Mercati_Uso value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public Mercati_UsoEnumerator GetEnumerator()
		{
			return new Mercati_UsoEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='Mercati_Uso'/> from the 
		///    <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Mercati_Uso'/> to remove from the <see cref='Init.SIGePro.Collection.Mercati_UsoCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(Mercati_Uso value)
		{
			List.Remove(value);
		}

		public class Mercati_UsoEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public Mercati_UsoEnumerator(Mercati_UsoCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public Mercati_Uso Current
			{
				get { return ((Mercati_Uso) (baseEnumerator.Current)); }
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