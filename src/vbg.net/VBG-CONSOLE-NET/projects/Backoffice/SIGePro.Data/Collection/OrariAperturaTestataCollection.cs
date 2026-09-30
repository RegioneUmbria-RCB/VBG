using System;
using System.Collections;
using Init.SIGePro.Data;

namespace Init.SIGePro.Collection {
    /// <summary>
    ///     <para>
    ///       A collection that stores <see cref='OrariAperturaTestata'/> objects.
    ///    </para>
    /// </summary>
    /// <seealso cref='OrariAperturaTestataCollection'/>
    [Serializable()]
    public class OrariAperturaTestataCollection : CollectionBase {
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='OrariAperturaTestataCollection'/>.
        ///    </para>
        /// </summary>
        public OrariAperturaTestataCollection() {
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='OrariAperturaTestataCollection'/> based on another <see cref='OrariAperturaTestataCollection'/>.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A <see cref='OrariAperturaTestataCollection'/> from which the contents are copied
        /// </param>
        public OrariAperturaTestataCollection(OrariAperturaTestataCollection value) {
            this.AddRange(value);
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='OrariAperturaTestataCollection'/> containing any array of <see cref='OrariAperturaTestata'/> objects.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A array of <see cref='OrariAperturaTestata'/> objects with which to intialize the collection
        /// </param>
        public OrariAperturaTestataCollection(OrariAperturaTestata[] value) {
            this.AddRange(value);
        }
        
        /// <summary>
        /// <para>Represents the entry at the specified index of the <see cref='OrariAperturaTestata'/>.</para>
        /// </summary>
        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
        /// <value>
        ///    <para> The entry at the specified index of the collection.</para>
        /// </value>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
        public OrariAperturaTestata this[int index] {
            get {
                return ((OrariAperturaTestata)(List[index]));
            }
            set {
                List[index] = value;
            }
        }
        
        /// <summary>
        ///    <para>Adds a <see cref='OrariAperturaTestata'/> with the specified value to the 
        ///    <see cref='OrariAperturaTestataCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='OrariAperturaTestata'/> to add.</param>
        /// <returns>
        ///    <para>The index at which the new element was inserted.</para>
        /// </returns>
        /// <seealso cref='OrariAperturaTestataCollection.AddRange'/>
        public int Add(OrariAperturaTestata value) {
            return List.Add(value);
        }
        
        /// <summary>
        /// <para>Copies the elements of an array to the end of the <see cref='OrariAperturaTestataCollection'/>.</para>
        /// </summary>
        /// <param name='value'>
        ///    An array of type <see cref='OrariAperturaTestata'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='OrariAperturaTestataCollection.Add'/>
        public void AddRange(OrariAperturaTestata[] value) {
            for (int i = 0; (i < value.Length); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        ///     <para>
        ///       Adds the contents of another <see cref='OrariAperturaTestataCollection'/> to the end of the collection.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///    A <see cref='OrariAperturaTestataCollection'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='OrariAperturaTestataCollection.Add'/>
        public void AddRange(OrariAperturaTestataCollection value) {
            for (int i = 0; (i < value.Count); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        /// <para>Gets a value indicating whether the 
        ///    <see cref='OrariAperturaTestataCollection'/> contains the specified <see cref='OrariAperturaTestata'/>.</para>
        /// </summary>
        /// <param name='value'>The <see cref='OrariAperturaTestata'/> to locate.</param>
        /// <returns>
        /// <para><see langword='true'/> if the <see cref='OrariAperturaTestata'/> is contained in the collection; 
        ///   otherwise, <see langword='false'/>.</para>
        /// </returns>
        /// <seealso cref='OrariAperturaTestataCollection.IndexOf'/>
        public bool Contains(OrariAperturaTestata value) {
            return List.Contains(value);
        }
        
        /// <summary>
        /// <para>Copies the <see cref='OrariAperturaTestataCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
        ///    specified index.</para>
        /// </summary>
        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='OrariAperturaTestataCollection'/> .</para></param>
        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='OrariAperturaTestataCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
        /// <seealso cref='System.Array'/>
        public void CopyTo(OrariAperturaTestata[] array, int index) {
            List.CopyTo(array, index);
        }
        
        /// <summary>
        ///    <para>Returns the index of a <see cref='OrariAperturaTestata'/> in 
        ///       the <see cref='OrariAperturaTestataCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='OrariAperturaTestata'/> to locate.</param>
        /// <returns>
        /// <para>The index of the <see cref='OrariAperturaTestata'/> of <paramref name='value'/> in the 
        /// <see cref='OrariAperturaTestataCollection'/>, if found; otherwise, -1.</para>
        /// </returns>
        /// <seealso cref='OrariAperturaTestataCollection.Contains'/>
        public int IndexOf(OrariAperturaTestata value) {
            return List.IndexOf(value);
        }
        
        /// <summary>
        /// <para>Inserts a <see cref='OrariAperturaTestata'/> into the <see cref='OrariAperturaTestataCollection'/> at the specified index.</para>
        /// </summary>
        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
        /// <param name=' value'>The <see cref='OrariAperturaTestata'/> to insert.</param>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='OrariAperturaTestataCollection.Add'/>
        public void Insert(int index, OrariAperturaTestata value) {
            List.Insert(index, value);
        }
        
        /// <summary>
        ///    <para>Returns an enumerator that can iterate through 
        ///       the <see cref='OrariAperturaTestataCollection'/> .</para>
        /// </summary>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='System.Collections.IEnumerator'/>
        public new OrariAperturaTestataEnumerator GetEnumerator() {
            return new OrariAperturaTestataEnumerator(this);
        }
        
        /// <summary>
        ///    <para> Removes a specific <see cref='OrariAperturaTestata'/> from the 
        ///    <see cref='OrariAperturaTestataCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='OrariAperturaTestata'/> to remove from the <see cref='OrariAperturaTestataCollection'/> .</param>
        /// <returns><para>None.</para></returns>
        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
        public void Remove(OrariAperturaTestata value) {
            List.Remove(value);
        }
        
        public class OrariAperturaTestataEnumerator : object, IEnumerator {
            
            private IEnumerator baseEnumerator;
            
            private IEnumerable temp;
            
            public OrariAperturaTestataEnumerator(OrariAperturaTestataCollection mappings) {
                this.temp = ((mappings));
                this.baseEnumerator = temp.GetEnumerator();
            }
            
            public OrariAperturaTestata Current {
                get {
                    return ((OrariAperturaTestata)(baseEnumerator.Current));
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
