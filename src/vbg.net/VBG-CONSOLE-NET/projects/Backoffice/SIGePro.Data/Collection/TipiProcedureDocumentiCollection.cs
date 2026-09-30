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
	///       A collection that stores <see cref='TipiProcedureDocumenti'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/>
	[Serializable()]
	public class TipiProcedureDocumentiCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/>.
		///    </para>
		/// </summary>
		public TipiProcedureDocumentiCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> based on another <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> from which the contents are copied
		/// </param>
		public TipiProcedureDocumentiCollection(TipiProcedureDocumentiCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> containing any array of <see cref='TipiProcedureDocumenti'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='TipiProcedureDocumenti'/> objects with which to intialize the collection
		/// </param>
		public TipiProcedureDocumentiCollection(TipiProcedureDocumenti[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> containing any array of <see cref='TipiProcedureDocumenti'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='TipiProcedureDocumenti'/> objects with which to intialize the collection
		/// </param>
		public TipiProcedureDocumentiCollection(ArrayList value)
		{
			for( int i=0; i<value.Count; i++)
				this.Add(value[i] as TipiProcedureDocumenti);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='TipiProcedureDocumenti'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public TipiProcedureDocumenti this[int index]
		{
			get { return ((TipiProcedureDocumenti) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='TipiProcedureDocumenti'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureDocumenti'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection.AddRange'/>
		public int Add(TipiProcedureDocumenti value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='TipiProcedureDocumenti'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection.Add'/>
		public void AddRange(TipiProcedureDocumenti[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection.Add'/>
		public void AddRange(TipiProcedureDocumentiCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> contains the specified <see cref='TipiProcedureDocumenti'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureDocumenti'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='TipiProcedureDocumenti'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection.IndexOf'/>
		public bool Contains(TipiProcedureDocumenti value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(TipiProcedureDocumenti[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='TipiProcedureDocumenti'/> in 
		///       the <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureDocumenti'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='TipiProcedureDocumenti'/> of <paramref name='value'/> in the 
		/// <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection.Contains'/>
		public int IndexOf(TipiProcedureDocumenti value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='TipiProcedureDocumenti'/> into the <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='TipiProcedureDocumenti'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection.Add'/>
		public void Insert(int index, TipiProcedureDocumenti value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public TipiProcedureDocumentiEnumerator GetEnumerator()
		{
			return new TipiProcedureDocumentiEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='TipiProcedureDocumenti'/> from the 
		///    <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureDocumenti'/> to remove from the <see cref='Init.SIGePro.Collection.TipiProcedureDocumentiCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(TipiProcedureDocumenti value)
		{
			List.Remove(value);
		}

		public class TipiProcedureDocumentiEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public TipiProcedureDocumentiEnumerator(TipiProcedureDocumentiCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public TipiProcedureDocumenti Current
			{
				get { return ((TipiProcedureDocumenti) (baseEnumerator.Current)); }
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