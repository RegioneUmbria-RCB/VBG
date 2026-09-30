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
	///       A collection that stores <see cref='TipiMovimento_Dis'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/>
	[Serializable()]
	public class TipiMovimento_DisCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/>.
		///    </para>
		/// </summary>
		public TipiMovimento_DisCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> based on another <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> from which the contents are copied
		/// </param>
		public TipiMovimento_DisCollection(TipiMovimento_DisCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> containing any array of <see cref='TipiMovimento_Dis'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='TipiMovimento_Dis'/> objects with which to intialize the collection
		/// </param>
		public TipiMovimento_DisCollection(TipiMovimento_Dis[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> containing any array of <see cref='TipiMovimento_Dis'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='TipiMovimento_Dis'/> objects with which to intialize the collection
		/// </param>
		public TipiMovimento_DisCollection(ArrayList value)
		{
			for( int i=0; i<value.Count; i++)
				this.Add(value[i] as TipiMovimento_Dis);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='TipiMovimento_Dis'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public TipiMovimento_Dis this[int index]
		{
			get { return ((TipiMovimento_Dis) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='TipiMovimento_Dis'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiMovimento_Dis'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiMovimento_DisCollection.AddRange'/>
		public int Add(TipiMovimento_Dis value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='TipiMovimento_Dis'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiMovimento_DisCollection.Add'/>
		public void AddRange(TipiMovimento_Dis[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiMovimento_DisCollection.Add'/>
		public void AddRange(TipiMovimento_DisCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> contains the specified <see cref='TipiMovimento_Dis'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiMovimento_Dis'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='TipiMovimento_Dis'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiMovimento_DisCollection.IndexOf'/>
		public bool Contains(TipiMovimento_Dis value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(TipiMovimento_Dis[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='TipiMovimento_Dis'/> in 
		///       the <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiMovimento_Dis'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='TipiMovimento_Dis'/> of <paramref name='value'/> in the 
		/// <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiMovimento_DisCollection.Contains'/>
		public int IndexOf(TipiMovimento_Dis value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='TipiMovimento_Dis'/> into the <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='TipiMovimento_Dis'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiMovimento_DisCollection.Add'/>
		public void Insert(int index, TipiMovimento_Dis value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public TipiMovimento_DisEnumerator GetEnumerator()
		{
			return new TipiMovimento_DisEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='TipiMovimento_Dis'/> from the 
		///    <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiMovimento_Dis'/> to remove from the <see cref='Init.SIGePro.Collection.TipiMovimento_DisCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(TipiMovimento_Dis value)
		{
			List.Remove(value);
		}

		public class TipiMovimento_DisEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public TipiMovimento_DisEnumerator(TipiMovimento_DisCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public TipiMovimento_Dis Current
			{
				get { return ((TipiMovimento_Dis) (baseEnumerator.Current)); }
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