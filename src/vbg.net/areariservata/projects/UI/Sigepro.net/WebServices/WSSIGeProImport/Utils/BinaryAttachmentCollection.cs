using System;
using System.Collections;

namespace SIGePro.Net.WebServices.WSSIGeProImport.Utils
{
	/// <summary>
	///     <para>
	///       A collection that stores <see cref='BinaryAttachment'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='BinaryAttachmentCollection'/>
	[Serializable()]
	public class BinaryAttachmentCollection : CollectionBase
	{
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='BinaryAttachmentCollection'/>.
		///    </para>
		/// </summary>
		public BinaryAttachmentCollection()
		{
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='BinaryAttachmentCollection'/> based on another <see cref='BinaryAttachmentCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='BinaryAttachmentCollection'/> from which the contents are copied
		/// </param>
		public BinaryAttachmentCollection(BinaryAttachmentCollection value)
		{
			this.AddRange(value);
		}


		public BinaryAttachmentCollection(IList l)
		{
			for (int i = 0; i < l.Count; i++)
			{
				this.Add((BinaryAttachment) l[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='BinaryAttachmentCollection'/> containing any array of <see cref='BinaryAttachment'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='BinaryAttachment'/> objects with which to intialize the collection
		/// </param>
		public BinaryAttachmentCollection(BinaryAttachment[] value)
		{
			this.AddRange(value);
		}

		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='BinaryAttachment'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public BinaryAttachment this[int index]
		{
			get { return ((BinaryAttachment) (List[index])); }
			set { List[index] = value; }
		}


		public BinaryAttachment FindByFileName(string fileName)
		{
			for (int i = 0; i < this.Count; i++)
			{
				if (this[i].FileName.ToUpper() == fileName.ToUpper())
					return this[i];
			}

			return null;
		}

		/// <summary>
		///    <para>Adds a <see cref='BinaryAttachment'/> with the specified value to the 
		///    <see cref='BinaryAttachmentCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='BinaryAttachment'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='BinaryAttachmentCollection.AddRange'/>
		public int Add(BinaryAttachment value)
		{
			return List.Add(value);
		}

		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='BinaryAttachmentCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='BinaryAttachment'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='BinaryAttachmentCollection.Add'/>
		public void AddRange(BinaryAttachment[] value)
		{
			for (int i = 0; (i < value.Length); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='BinaryAttachmentCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='BinaryAttachmentCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='BinaryAttachmentCollection.Add'/>
		public void AddRange(BinaryAttachmentCollection value)
		{
			for (int i = 0; (i < value.Count); i = (i + 1))
			{
				this.Add(value[i]);
			}
		}

		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='BinaryAttachmentCollection'/> contains the specified <see cref='BinaryAttachment'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='BinaryAttachment'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='BinaryAttachment'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='BinaryAttachmentCollection.IndexOf'/>
		public bool Contains(BinaryAttachment value)
		{
			return List.Contains(value);
		}

		/// <summary>
		/// <para>Copies the <see cref='BinaryAttachmentCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='BinaryAttachmentCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='BinaryAttachmentCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(BinaryAttachment[] array, int index)
		{
			List.CopyTo(array, index);
		}

		/// <summary>
		///    <para>Returns the index of a <see cref='BinaryAttachment'/> in 
		///       the <see cref='BinaryAttachmentCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='BinaryAttachment'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='BinaryAttachment'/> of <paramref name='value'/> in the 
		/// <see cref='BinaryAttachmentCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='BinaryAttachmentCollection.Contains'/>
		public int IndexOf(BinaryAttachment value)
		{
			return List.IndexOf(value);
		}

		/// <summary>
		/// <para>Inserts a <see cref='BinaryAttachment'/> into the <see cref='BinaryAttachmentCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='BinaryAttachment'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='BinaryAttachmentCollection.Add'/>
		public void Insert(int index, BinaryAttachment value)
		{
			List.Insert(index, value);
		}

		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='BinaryAttachmentCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		public new BinaryAttachmentEnumerator GetEnumerator()
		{
			return new BinaryAttachmentEnumerator(this);
		}

		/// <summary>
		///    <para> Removes a specific <see cref='BinaryAttachment'/> from the 
		///    <see cref='BinaryAttachmentCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='BinaryAttachment'/> to remove from the <see cref='BinaryAttachmentCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(BinaryAttachment value)
		{
			List.Remove(value);
		}

		public class BinaryAttachmentEnumerator : object, IEnumerator
		{
			private IEnumerator baseEnumerator;

			private IEnumerable temp;

			public BinaryAttachmentEnumerator(BinaryAttachmentCollection mappings)
			{
				this.temp = ((IEnumerable) (mappings));
				this.baseEnumerator = temp.GetEnumerator();
			}

			public BinaryAttachment Current
			{
				get { return ((BinaryAttachment) (baseEnumerator.Current)); }
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