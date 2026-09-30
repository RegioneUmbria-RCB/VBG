using System;
using System.Collections;
using System.Collections.Specialized;

namespace Parser.Collections 
{
	/// <summary>
	///     <para>
	///       A collection that stores <see cref='Column'/> objects.
	///    </para>
	/// </summary>
	/// <seealso cref='ColumnCollection'/>
	[Serializable()]
	public class ColumnCollection : CollectionBase 
	{
        
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='ColumnCollection'/>.
		///    </para>
		/// </summary>
		public ColumnCollection() 
		{
		}
        
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='ColumnCollection'/> based on another <see cref='ColumnCollection'/>.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A <see cref='ColumnCollection'/> from which the contents are copied
		/// </param>
		public ColumnCollection(ColumnCollection value) 
		{
			this.AddRange(value);
		}
        
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='ColumnCollection'/> containing any array of <see cref='Column'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A array of <see cref='Column'/> objects with which to intialize the collection
		/// </param>
		public ColumnCollection(Column[] value) 
		{
			this.AddRange(value);
		}
        
		/// <summary>
		///     <para>
		///       Initializes a new instance of <see cref='ColumnCollection'/> containing any array of <see cref='Column'/> objects.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///       A ArrayList of <see cref='Column'/> objects with which to intialize the collection
		/// </param>
		public ColumnCollection(ArrayList value) 
		{
			for( int i=0; i<value.Count; i++ )
				this.Add((value[i] as Column));
		}
        
		/// <summary>
		/// <para>Represents the entry at the specified index of the <see cref='Column'/>.</para>
		/// </summary>
		/// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
		/// <value>
		///    <para> The entry at the specified index of the collection.</para>
		/// </value>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
		public Column this[int index] 
		{
			get 
			{
				return ((Column)(List[index]));
			}
			set 
			{
				List[index] = value;
			}
		}
        
		/// <summary>
		///    <para>Adds a <see cref='Column'/> with the specified value to the 
		///    <see cref='ColumnCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Column'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='ColumnCollection.AddRange'/>
		public int Add(Column value) 
		{
			return List.Add(value);
		}

		/// <summary>
		///    <para>Adds a <see cref='Column'/> with the specified value to the 
		///    <see cref='ColumnCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Column'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='ColumnCollection.AddRange'/>
		public int Add(string colName, string colValue) 
		{
			return List.Add( new Column( colName, colValue ) );
		}
        
		/// <summary>
		/// <para>Copies the elements of an array to the end of the <see cref='ColumnCollection'/>.</para>
		/// </summary>
		/// <param name='value'>
		///    An array of type <see cref='Column'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='ColumnCollection.Add'/>
		public void AddRange(Column[] value) 
		{
			for (int i = 0; (i < value.Length); i = (i + 1)) 
			{
				this.Add(value[i]);
			}
		}     

		/// <summary>
		///     <para>
		///       Adds the contents of another <see cref='ColumnCollection'/> to the end of the collection.
		///    </para>
		/// </summary>
		/// <param name='value'>
		///    A <see cref='ColumnCollection'/> containing the objects to add to the collection.
		/// </param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <seealso cref='ColumnCollection.Add'/>
		public void AddRange(ColumnCollection value) 
		{
			for (int i = 0; (i < value.Count); i = (i + 1)) 
			{
				this.Add(value[i]);
			}
		}
        
		/// <summary>
		/// <para>Gets a value indicating whether the 
		///    <see cref='ColumnCollection'/> contains the specified <see cref='Column'/>.</para>
		/// </summary>
		/// <param name='value'>The <see cref='Column'/> to locate.</param>
		/// <returns>
		/// <para><see langword='true'/> if the <see cref='Column'/> is contained in the collection; 
		///   otherwise, <see langword='false'/>.</para>
		/// </returns>
		/// <seealso cref='ColumnCollection.IndexOf'/>
		public bool Contains(Column value) 
		{
			return List.Contains(value);
		}
        
		/// <summary>
		/// <para>Copies the <see cref='ColumnCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
		///    specified index.</para>
		/// </summary>
		/// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='ColumnCollection'/> .</para></param>
		/// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
		/// <returns>
		///   <para>None.</para>
		/// </returns>
		/// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='ColumnCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
		/// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
		/// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
		/// <seealso cref='System.Array'/>
		public void CopyTo(Column[] array, int index) 
		{
			List.CopyTo(array, index);
		}
        
		/// <summary>
		///    <para>Returns the index of a <see cref='Column'/> in 
		///       the <see cref='ColumnCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Column'/> to locate.</param>
		/// <returns>
		/// <para>The index of the <see cref='Column'/> of <paramref name='value'/> in the 
		/// <see cref='ColumnCollection'/>, if found; otherwise, -1.</para>
		/// </returns>
		/// <seealso cref='ColumnCollection.Contains'/>
		public int IndexOf(Column value) 
		{
			return List.IndexOf(value);
		}
        
		/// <summary>
		/// <para>Inserts a <see cref='Column'/> into the <see cref='ColumnCollection'/> at the specified index.</para>
		/// </summary>
		/// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
		/// <param name=' value'>The <see cref='Column'/> to insert.</param>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='ColumnCollection.Add'/>
		public void Insert(int index, Column value) 
		{
			List.Insert(index, value);
		}
        
		/// <summary>
		///    <para>Returns an enumerator that can iterate through 
		///       the <see cref='ColumnCollection'/> .</para>
		/// </summary>
		/// <returns><para>None.</para></returns>
		/// <seealso cref='System.Collections.IEnumerator'/>
		public new ColumnEnumerator GetEnumerator() 
		{
			return new ColumnEnumerator(this);
		}
        
		/// <summary>
		///    <para> Removes a specific <see cref='Column'/> from the 
		///    <see cref='ColumnCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Column'/> to remove from the <see cref='ColumnCollection'/> .</param>
		/// <returns><para>None.</para></returns>
		/// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
		public void Remove(Column value) 
		{
			List.Remove(value);
		}
        
		public class ColumnEnumerator : object, IEnumerator 
		{
            
			private IEnumerator baseEnumerator;
            
			private IEnumerable temp;
            
			public ColumnEnumerator(ColumnCollection mappings) 
			{
				this.temp = ((mappings));
				this.baseEnumerator = temp.GetEnumerator();
			}
            
			public Column Current 
			{
				get 
				{
					return ((Column)(baseEnumerator.Current));
				}
			}
            
			object IEnumerator.Current 
			{
				get 
				{
					return baseEnumerator.Current;
				}
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
