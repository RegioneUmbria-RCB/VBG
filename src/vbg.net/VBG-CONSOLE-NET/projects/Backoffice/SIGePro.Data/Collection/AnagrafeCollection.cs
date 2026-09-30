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
	///       A collection that stores <see cref='Anagrafe'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='Init.SIGePro.Collection.AnagrafeCollection'/>
	[Serializable()]
	public class AnagrafeCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.AnagrafeCollection'/>.
		///    </para>
		/// </summary>
		public AnagrafeCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> based on another <see cref='Init.SIGePro.Collection.AnagrafeCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> from which the contents are copied
		/// </param>
		public AnagrafeCollection(AnagrafeCollection value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> containing any array of <see cref='Anagrafe'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Anagrafe'/> objects with which to intialize the collection
		/// </param>
		public AnagrafeCollection(Anagrafe[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> containing any array of <see cref='Anagrafe'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Anagrafe'/> objects with which to intialize the collection
		/// </param>
		public AnagrafeCollection(ArrayList value)
		{
			for( int i=0; i<value.Count; i++)
				this.Add(value[i] as Anagrafe);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='Anagrafe'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public Anagrafe this[int index]
		{
			get { return ((Anagrafe) (List[index])); }
			set { List[index] = value; }
		}

		/// <summary>
		///    <para>Adds a <see cref='Anagrafe'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Anagrafe'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.AnagrafeCollection.AddRange'/>
		public int Add(Anagrafe value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.AnagrafeCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='Anagrafe'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.AnagrafeCollection.Add'/>
		public void AddRange(Anagrafe[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.AnagrafeCollection.Add'/>
		public void AddRange(AnagrafeCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> contains the specified <see cref='Anagrafe'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='Anagrafe'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='Anagrafe'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.AnagrafeCollection.IndexOf'/>
		public bool Contains(Anagrafe value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(Anagrafe[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='Anagrafe'/> in 
		///       the <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Anagrafe'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='Anagrafe'/> of <paramref name='value'/> in the 
		/// <see cref='Init.SIGePro.Collection.AnagrafeCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.AnagrafeCollection.Contains'/>
		public int IndexOf(Anagrafe value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='Anagrafe'/> into the <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='Anagrafe'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='Init.SIGePro.Collection.AnagrafeCollection.Add'/>
		public void Insert(int index, Anagrafe value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		new public AnagrafeEnumerator GetEnumerator()
		{
			return new AnagrafeEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='Anagrafe'/> from the 
		///    <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Anagrafe'/> to remove from the <see cref='Init.SIGePro.Collection.AnagrafeCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(Anagrafe value)
		{
			List.Remove(value);
		}

		public class AnagrafeEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public AnagrafeEnumerator(AnagrafeCollection mappings)
			{
				this.temp = mappings;
				this.baseEnumerator = temp.GetEnumerator();
			}

			public Anagrafe Current
			{
				get { return ((Anagrafe) (baseEnumerator.Current)); }
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