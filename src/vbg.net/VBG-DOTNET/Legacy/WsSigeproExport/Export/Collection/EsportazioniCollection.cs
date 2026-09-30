using System;
using System.Collections;
using Export.Data;

namespace Export.Collection {
    /// <summary>
    ///     <para>
    ///       A collection that stores <see cref='CEsportazione'/> objects.
    ///    </para>
    /// </summary>
    /// <seealso cref='EsportazioniCollection'/>
    [Serializable()]
    public class EsportazioniCollection : CollectionBase {
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='EsportazioniCollection'/>.
        ///    </para>
        /// </summary>
        public EsportazioniCollection() {
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='EsportazioniCollection'/> based on another <see cref='EsportazioniCollection'/>.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A <see cref='EsportazioniCollection'/> from which the contents are copied
        /// </param>
        public EsportazioniCollection(EsportazioniCollection value) {
            this.AddRange(value);
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='EsportazioniCollection'/> containing any array of <see cref='CEsportazione'/> objects.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A array of <see cref='CEsportazione'/> objects with which to intialize the collection
        /// </param>
        public EsportazioniCollection(CEsportazione[] value) {
            this.AddRange(value);
        }
        
        /// <summary>
        /// <para>Represents the entry at the specified index of the <see cref='CEsportazione'/>.</para>
        /// </summary>
        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
        /// <value>
        ///    <para> The entry at the specified index of the collection.</para>
        /// </value>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
        public CEsportazione this[int index] {
            get {
                return ((CEsportazione)(List[index]));
            }
            set {
                List[index] = value;
            }
        }
        
        /// <summary>
        ///    <para>Adds a <see cref='CEsportazione'/> with the specified value to the 
        ///    <see cref='EsportazioniCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='CEsportazione'/> to add.</param>
        /// <returns>
        ///    <para>The index at which the new element was inserted.</para>
        /// </returns>
        /// <seealso cref='EsportazioniCollection.AddRange'/>
        public int Add(CEsportazione value) {
            return List.Add(value);
        }
        
        /// <summary>
        /// <para>Copies the elements of an array to the end of the <see cref='EsportazioniCollection'/>.</para>
        /// </summary>
        /// <param name='value'>
        ///    An array of type <see cref='CEsportazione'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='EsportazioniCollection.Add'/>
        public void AddRange(CEsportazione[] value) {
            for (int i = 0; (i < value.Length); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        ///     <para>
        ///       Adds the contents of another <see cref='EsportazioniCollection'/> to the end of the collection.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///    A <see cref='EsportazioniCollection'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='EsportazioniCollection.Add'/>
        public void AddRange(EsportazioniCollection value) {
            for (int i = 0; (i < value.Count); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        /// <para>Gets a value indicating whether the 
        ///    <see cref='EsportazioniCollection'/> contains the specified <see cref='CEsportazione'/>.</para>
        /// </summary>
        /// <param name='value'>The <see cref='CEsportazione'/> to locate.</param>
        /// <returns>
        /// <para><see langword='true'/> if the <see cref='CEsportazione'/> is contained in the collection; 
        ///   otherwise, <see langword='false'/>.</para>
        /// </returns>
        /// <seealso cref='EsportazioniCollection.IndexOf'/>
        public bool Contains(CEsportazione value) {
            return List.Contains(value);
        }
        
        /// <summary>
        /// <para>Copies the <see cref='EsportazioniCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
        ///    specified index.</para>
        /// </summary>
        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='EsportazioniCollection'/> .</para></param>
        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='EsportazioniCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
        /// <seealso cref='System.Array'/>
        public void CopyTo(CEsportazione[] array, int index) {
            List.CopyTo(array, index);
        }
        
        /// <summary>
        ///    <para>Returns the index of a <see cref='CEsportazione'/> in 
        ///       the <see cref='EsportazioniCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='CEsportazione'/> to locate.</param>
        /// <returns>
        /// <para>The index of the <see cref='CEsportazione'/> of <paramref name='value'/> in the 
        /// <see cref='EsportazioniCollection'/>, if found; otherwise, -1.</para>
        /// </returns>
        /// <seealso cref='EsportazioniCollection.Contains'/>
        public int IndexOf(CEsportazione value) {
            return List.IndexOf(value);
        }
        
        /// <summary>
        /// <para>Inserts a <see cref='CEsportazione'/> into the <see cref='EsportazioniCollection'/> at the specified index.</para>
        /// </summary>
        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
        /// <param name=' value'>The <see cref='CEsportazione'/> to insert.</param>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='EsportazioniCollection.Add'/>
        public void Insert(int index, CEsportazione value) {
            List.Insert(index, value);
        }
        
        /// <summary>
        ///    <para>Returns an enumerator that can iterate through 
        ///       the <see cref='EsportazioniCollection'/> .</para>
        /// </summary>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='System.Collections.IEnumerator'/>
        public new EsportazioneEnumerator GetEnumerator() {
            return new EsportazioneEnumerator(this);
        }
        
        /// <summary>
        ///    <para> Removes a specific <see cref='CEsportazione'/> from the 
        ///    <see cref='EsportazioniCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='CEsportazione'/> to remove from the <see cref='EsportazioniCollection'/> .</param>
        /// <returns><para>None.</para></returns>
        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
        public void Remove(CEsportazione value) {
            List.Remove(value);
        }
        
        public class EsportazioneEnumerator : object, IEnumerator {
            
            private IEnumerator baseEnumerator;
            
            private IEnumerable temp;
            
            public EsportazioneEnumerator(EsportazioniCollection mappings) {
                this.temp = ((IEnumerable)(mappings));
                this.baseEnumerator = temp.GetEnumerator();
            }
            
            public CEsportazione Current {
                get {
                    return ((CEsportazione)(baseEnumerator.Current));
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
