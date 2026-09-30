using System;
using System.Collections;
using Export.Data;

namespace Export.Collection {
    /// <summary>
    ///     <para>
    ///       A collection that stores <see cref='Parametro'/> objects.
    ///    </para>
    /// </summary>
    /// <seealso cref='ParametriCollection'/>
    [Serializable()]
    public class ParametriCollection : CollectionBase {
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='ParametriCollection'/>.
        ///    </para>
        /// </summary>
        public ParametriCollection() {
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='ParametriCollection'/> based on another <see cref='ParametriCollection'/>.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A <see cref='ParametriCollection'/> from which the contents are copied
        /// </param>
        public ParametriCollection(ParametriCollection value) {
            this.AddRange(value);
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='ParametriCollection'/> containing any array of <see cref='Parametro'/> objects.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A array of <see cref='Parametro'/> objects with which to intialize the collection
        /// </param>
        public ParametriCollection(Parametro[] value) {
            this.AddRange(value);
        }
        
        /// <summary>
        /// <para>Represents the entry at the specified index of the <see cref='Parametro'/>.</para>
        /// </summary>
        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
        /// <value>
        ///    <para> The entry at the specified index of the collection.</para>
        /// </value>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
        public Parametro this[int index] {
            get {
                return ((Parametro)(List[index]));
            }
            set {
                List[index] = value;
            }
        }
        
        /// <summary>
        ///    <para>Adds a <see cref='Parametro'/> with the specified value to the 
        ///    <see cref='ParametriCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='Parametro'/> to add.</param>
        /// <returns>
        ///    <para>The index at which the new element was inserted.</para>
        /// </returns>
        /// <seealso cref='ParametriCollection.AddRange'/>
        public int Add(Parametro value) {
            return List.Add(value);
        }
        
        /// <summary>
        /// <para>Copies the elements of an array to the end of the <see cref='ParametriCollection'/>.</para>
        /// </summary>
        /// <param name='value'>
        ///    An array of type <see cref='Parametro'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='ParametriCollection.Add'/>
        public void AddRange(Parametro[] value) {
            for (int i = 0; (i < value.Length); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        ///     <para>
        ///       Adds the contents of another <see cref='ParametriCollection'/> to the end of the collection.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///    A <see cref='ParametriCollection'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='ParametriCollection.Add'/>
        public void AddRange(ParametriCollection value) {
            for (int i = 0; (i < value.Count); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        /// <para>Gets a value indicating whether the 
        ///    <see cref='ParametriCollection'/> contains the specified <see cref='Parametro'/>.</para>
        /// </summary>
        /// <param name='value'>The <see cref='Parametro'/> to locate.</param>
        /// <returns>
        /// <para><see langword='true'/> if the <see cref='Parametro'/> is contained in the collection; 
        ///   otherwise, <see langword='false'/>.</para>
        /// </returns>
        /// <seealso cref='ParametriCollection.IndexOf'/>
        public bool Contains(Parametro value) {
            return List.Contains(value);
        }
        
        /// <summary>
        /// <para>Copies the <see cref='ParametriCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
        ///    specified index.</para>
        /// </summary>
        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='ParametriCollection'/> .</para></param>
        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='ParametriCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
        /// <seealso cref='System.Array'/>
        public void CopyTo(Parametro[] array, int index) {
            List.CopyTo(array, index);
        }
        
        /// <summary>
        ///    <para>Returns the index of a <see cref='Parametro'/> in 
        ///       the <see cref='ParametriCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='Parametro'/> to locate.</param>
        /// <returns>
        /// <para>The index of the <see cref='Parametro'/> of <paramref name='value'/> in the 
        /// <see cref='ParametriCollection'/>, if found; otherwise, -1.</para>
        /// </returns>
        /// <seealso cref='ParametriCollection.Contains'/>
        public int IndexOf(Parametro value) {
            return List.IndexOf(value);
        }
        
        /// <summary>
        /// <para>Inserts a <see cref='Parametro'/> into the <see cref='ParametriCollection'/> at the specified index.</para>
        /// </summary>
        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
        /// <param name=' value'>The <see cref='Parametro'/> to insert.</param>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='ParametriCollection.Add'/>
        public void Insert(int index, Parametro value) {
            List.Insert(index, value);
        }
        
        /// <summary>
        ///    <para>Returns an enumerator that can iterate through 
        ///       the <see cref='ParametriCollection'/> .</para>
        /// </summary>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='System.Collections.IEnumerator'/>
        public new ParametriEnumerator GetEnumerator() {
            return new ParametriEnumerator(this);
        }
        
        /// <summary>
        ///    <para> Removes a specific <see cref='Parametro'/> from the 
        ///    <see cref='ParametriCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='Parametro'/> to remove from the <see cref='ParametriCollection'/> .</param>
        /// <returns><para>None.</para></returns>
        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
        public void Remove(Parametro value) {
            List.Remove(value);
        }
        
        public class ParametriEnumerator : object, IEnumerator {
            
            private IEnumerator baseEnumerator;
            
            private IEnumerable temp;
            
            public ParametriEnumerator(ParametriCollection mappings) {
                this.temp = ((IEnumerable)(mappings));
                this.baseEnumerator = temp.GetEnumerator();
            }
            
            public Parametro Current {
                get {
                    return ((Parametro)(baseEnumerator.Current));
                }
            }
            
            object IEnumerator.Current {
                get {
                    return baseEnumerator.Current;
                }
            }
            
            public bool MoveNext() {
                return baseEnumerator.MoveNext();
            }
            
            bool IEnumerator.MoveNext() {
                return baseEnumerator.MoveNext();
            }
            
            public void Reset() {
                baseEnumerator.Reset();
            }
            
            void IEnumerator.Reset() {
                baseEnumerator.Reset();
            }
        }
    }
}
