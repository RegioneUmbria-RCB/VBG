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
	///       A collection that stores <see cref='TipiProcedureAvvio'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/>
	[Serializable()]
	public class TipiProcedureAvvioCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/>.
		///    </para>
		/// </summary>
		public TipiProcedureAvvioCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> based on another <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> from which the contents are copied
		/// </param>
		public TipiProcedureAvvioCollection(TipiProcedureAvvioCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> containing any array of <see cref='TipiProcedureAvvio'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='TipiProcedureAvvio'/> objects with which to intialize the collection
		/// </param>
		public TipiProcedureAvvioCollection(TipiProcedureAvvio[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> containing any array of <see cref='TipiProcedureAvvio'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='TipiProcedureAvvio'/> objects with which to intialize the collection
		/// </param>
		public TipiProcedureAvvioCollection(ArrayList value)
		{
			for( int i=0; i<value.Count; i++)
				this.Add(value[i] as TipiProcedureAvvio);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='TipiProcedureAvvio'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public TipiProcedureAvvio this[int index]
		{
			get { return ((TipiProcedureAvvio) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='TipiProcedureAvvio'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureAvvio'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection.AddRange'/>
		public int Add(TipiProcedureAvvio value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='TipiProcedureAvvio'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection.Add'/>
		public void AddRange(TipiProcedureAvvio[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection.Add'/>
		public void AddRange(TipiProcedureAvvioCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> contains the specified <see cref='TipiProcedureAvvio'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureAvvio'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='TipiProcedureAvvio'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection.IndexOf'/>
		public bool Contains(TipiProcedureAvvio value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(TipiProcedureAvvio[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='TipiProcedureAvvio'/> in 
		///       the <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureAvvio'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='TipiProcedureAvvio'/> of <paramref name='value'/> in the 
		/// <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection.Contains'/>
		public int IndexOf(TipiProcedureAvvio value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='TipiProcedureAvvio'/> into the <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='TipiProcedureAvvio'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection.Add'/>
		public void Insert(int index, TipiProcedureAvvio value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public TipiProcedureAvvioEnumerator GetEnumerator()
		{
			return new TipiProcedureAvvioEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='TipiProcedureAvvio'/> from the 
		///    <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='TipiProcedureAvvio'/> to remove from the <see cref='Init.SIGePro.Collection.TipiProcedureAvvioCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(TipiProcedureAvvio value)
		{
			List.Remove(value);
		}

		public class TipiProcedureAvvioEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public TipiProcedureAvvioEnumerator(TipiProcedureAvvioCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public TipiProcedureAvvio Current
			{
				get { return ((TipiProcedureAvvio) (baseEnumerator.Current)); }
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