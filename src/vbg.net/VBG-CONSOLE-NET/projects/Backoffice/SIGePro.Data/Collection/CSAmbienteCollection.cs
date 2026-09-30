using System;
using System.Collections;

namespace Init.SIGePro.Collection {
    /// <summary>
    ///     <para>
    ///       A collection that stores <see cref='string'/> objects.
    ///    </para>
    /// </summary>
    /// <seealso cref='CSAmbienteCollection'/>
    [Serializable()]
    public class CSAmbienteCollection : CollectionBase {
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='CSAmbienteCollection'/>.
        ///    </para>
        /// </summary>
        public CSAmbienteCollection() {
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='CSAmbienteCollection'/> based on another <see cref='CSAmbienteCollection'/>.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A <see cref='CSAmbienteCollection'/> from which the contents are copied
        /// </param>
        public CSAmbienteCollection(CSAmbienteCollection value) {
            this.AddRange(value);
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='CSAmbienteCollection'/> containing any array of <see cref='string'/> objects.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A array of <see cref='string'/> objects with which to intialize the collection
        /// </param>
        public CSAmbienteCollection(string[] value) {
            this.AddRange(value);
        }
        
        /// <summary>
        /// <para>Represents the entry at the specified index of the <see cref='string'/>.</para>
        /// </summary>
        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
        /// <value>
        ///    <para> The entry at the specified index of the collection.</para>
        /// </value>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
        public string this[int index] {
            get {
                return ((string)(List[index]));
            }
            set {
                List[index] = value;
            }
        }
        
        /// <summary>
        ///    <para>Adds a <see cref='string'/> with the specified value to the 
        ///    <see cref='CSAmbienteCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='string'/> to add.</param>
        /// <returns>
        ///    <para>The index at which the new element was inserted.</para>
        /// </returns>
        /// <seealso cref='CSAmbienteCollection.AddRange'/>
        public int Add(string value) {
            return List.Add(value);
        }
        
        /// <summary>
        /// <para>Copies the elements of an array to the end of the <see cref='CSAmbienteCollection'/>.</para>
        /// </summary>
        /// <param name='value'>
        ///    An array of type <see cref='string'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='CSAmbienteCollection.Add'/>
        public void AddRange(string[] value) {
            for (int i = 0; (i < value.Length); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        ///     <para>
        ///       Adds the contents of another <see cref='CSAmbienteCollection'/> to the end of the collection.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///    A <see cref='CSAmbienteCollection'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='CSAmbienteCollection.Add'/>
        public void AddRange(CSAmbienteCollection value) {
            for (int i = 0; (i < value.Count); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        /// <para>Gets a value indicating whether the 
        ///    <see cref='CSAmbienteCollection'/> contains the specified <see cref='string'/>.</para>
        /// </summary>
        /// <param name='value'>The <see cref='string'/> to locate.</param>
        /// <returns>
        /// <para><see langword='true'/> if the <see cref='string'/> is contained in the collection; 
        ///   otherwise, <see langword='false'/>.</para>
        /// </returns>
        /// <seealso cref='CSAmbienteCollection.IndexOf'/>
        public bool Contains(string value) {
            return List.Contains(value);
        }
        
        /// <summary>
        /// <para>Copies the <see cref='CSAmbienteCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
        ///    specified index.</para>
        /// </summary>
        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='CSAmbienteCollection'/> .</para></param>
        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='CSAmbienteCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
        /// <seealso cref='System.Array'/>
        public void CopyTo(string[] array, int index) {
            List.CopyTo(array, index);
        }
        
        /// <summary>
        ///    <para>Returns the index of a <see cref='string'/> in 
        ///       the <see cref='CSAmbienteCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='string'/> to locate.</param>
        /// <returns>
        /// <para>The index of the <see cref='string'/> of <paramref name='value'/> in the 
        /// <see cref='CSAmbienteCollection'/>, if found; otherwise, -1.</para>
        /// </returns>
        /// <seealso cref='CSAmbienteCollection.Contains'/>
        public int IndexOf(string value) {
            return List.IndexOf(value);
        }
        
        /// <summary>
        /// <para>Inserts a <see cref='string'/> into the <see cref='CSAmbienteCollection'/> at the specified index.</para>
        /// </summary>
        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
        /// <param name=' value'>The <see cref='string'/> to insert.</param>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='CSAmbienteCollection.Add'/>
        public void Insert(int index, string value) {
            List.Insert(index, value);
        }
        
        /// <summary>
        ///    <para>Returns an enumerator that can iterate through 
        ///       the <see cref='CSAmbienteCollection'/> .</para>
        /// </summary>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='System.Collections.IEnumerator'/>
        public new stringEnumerator GetEnumerator() {
            return new stringEnumerator(this);
        }
        
        /// <summary>
        ///    <para> Removes a specific <see cref='string'/> from the 
        ///    <see cref='CSAmbienteCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='string'/> to remove from the <see cref='CSAmbienteCollection'/> .</param>
        /// <returns><para>None.</para></returns>
        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
        public void Remove(string value) {
            List.Remove(value);
        }
        
        public class stringEnumerator : object, IEnumerator {
            
            private IEnumerator baseEnumerator;
            
            private IEnumerable temp;
            
            public stringEnumerator(CSAmbienteCollection mappings) {
                this.temp = ((IEnumerable)(mappings));
                this.baseEnumerator = temp.GetEnumerator();
            }
            
            public string Current {
                get {
                    return ((string)(baseEnumerator.Current));
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
