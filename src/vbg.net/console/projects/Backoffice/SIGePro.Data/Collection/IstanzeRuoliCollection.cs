//using System;
//using System.Collections;
//using Init.SIGePro.Data;
//using PersonalLib2.Sql.Collections;

//namespace Init.SIGePro.Collection {
//    /// <summary>
//    ///     <para>
//    ///       A collection that stores <see cref='IstanzeRuoli'/> objects.
//    ///    </para>
//    /// </summary>
//    /// <seealso cref='IstanzeRuoliCollection'/>
//    [Serializable()]
//    public class IstanzeRuoliCollection : CollectionBase {
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='IstanzeRuoliCollection'/>.
//        ///    </para>
//        /// </summary>
//        public IstanzeRuoliCollection() {
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='IstanzeRuoliCollection'/> based on another <see cref='IstanzeRuoliCollection'/>.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A <see cref='IstanzeRuoliCollection'/> from which the contents are copied
//        /// </param>
//        public IstanzeRuoliCollection(IstanzeRuoliCollection value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='IstanzeRuoliCollection'/> containing any array of <see cref='IstanzeRuoli'/> objects.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A array of <see cref='IstanzeRuoli'/> objects with which to intialize the collection
//        /// </param>
//        public IstanzeRuoliCollection(IstanzeRuoli[] value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='IstanzeRuoliCollection'/> containing any array of <see cref='IstanzeRuoli'/> objects.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A array of <see cref='IstanzeRuoli'/> objects with which to intialize the collection
//        /// </param>
//        public IstanzeRuoliCollection(DataClassCollection value) 
//        {
//            this.AddRange(value);
//        }
//        /// <summary>
//        /// <para>Represents the entry at the specified index of the <see cref='IstanzeRuoli'/>.</para>
//        /// </summary>
//        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
//        /// <value>
//        ///    <para> The entry at the specified index of the collection.</para>
//        /// </value>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
//        public IstanzeRuoli this[int index] {
//            get {
//                return ((IstanzeRuoli)(List[index]));
//            }
//            set {
//                List[index] = value;
//            }
//        }
        
//        /// <summary>
//        ///    <para>Adds a <see cref='IstanzeRuoli'/> with the specified value to the 
//        ///    <see cref='IstanzeRuoliCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='IstanzeRuoli'/> to add.</param>
//        /// <returns>
//        ///    <para>The index at which the new element was inserted.</para>
//        /// </returns>
//        /// <seealso cref='IstanzeRuoliCollection.AddRange'/>
//        public int Add(IstanzeRuoli value) {
//            return List.Add(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the elements of an array to the end of the <see cref='IstanzeRuoliCollection'/>.</para>
//        /// </summary>
//        /// <param name='value'>
//        ///    An array of type <see cref='IstanzeRuoli'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='IstanzeRuoliCollection.Add'/>
//        public void AddRange(IstanzeRuoli[] value) {
//            for (int i = 0; (i < value.Length); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Adds the contents of another <see cref='IstanzeRuoliCollection'/> to the end of the collection.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///    A <see cref='IstanzeRuoliCollection'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='IstanzeRuoliCollection.Add'/>
//        public void AddRange(IstanzeRuoliCollection value) {
//            for (int i = 0; (i < value.Count); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Adds the contents of another <see cref='IstanzeRuoliCollection'/> to the end of the collection.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///    A <see cref='IstanzeRuoliCollection'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='IstanzeRuoliCollection.Add'/>
//        public void AddRange(DataClassCollection value) 
//        {
//            for (int i = 0; (i < value.Count); i = (i + 1)) 
//            {
//                this.Add(value[i] as IstanzeRuoli);
//            }
//        }
        
//        /// <summary>
//        /// <para>Gets a value indicating whether the 
//        ///    <see cref='IstanzeRuoliCollection'/> contains the specified <see cref='IstanzeRuoli'/>.</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='IstanzeRuoli'/> to locate.</param>
//        /// <returns>
//        /// <para><see langword='true'/> if the <see cref='IstanzeRuoli'/> is contained in the collection; 
//        ///   otherwise, <see langword='false'/>.</para>
//        /// </returns>
//        /// <seealso cref='IstanzeRuoliCollection.IndexOf'/>
//        public bool Contains(IstanzeRuoli value) 
//        {
//            return List.Contains(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the <see cref='IstanzeRuoliCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
//        ///    specified index.</para>
//        /// </summary>
//        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='IstanzeRuoliCollection'/> .</para></param>
//        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='IstanzeRuoliCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
//        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
//        /// <seealso cref='System.Array'/>
//        public void CopyTo(IstanzeRuoli[] array, int index) {
//            List.CopyTo(array, index);
//        }
        
//        /// <summary>
//        ///    <para>Returns the index of a <see cref='IstanzeRuoli'/> in 
//        ///       the <see cref='IstanzeRuoliCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='IstanzeRuoli'/> to locate.</param>
//        /// <returns>
//        /// <para>The index of the <see cref='IstanzeRuoli'/> of <paramref name='value'/> in the 
//        /// <see cref='IstanzeRuoliCollection'/>, if found; otherwise, -1.</para>
//        /// </returns>
//        /// <seealso cref='IstanzeRuoliCollection.Contains'/>
//        public int IndexOf(IstanzeRuoli value) {
//            return List.IndexOf(value);
//        }
        
//        /// <summary>
//        /// <para>Inserts a <see cref='IstanzeRuoli'/> into the <see cref='IstanzeRuoliCollection'/> at the specified index.</para>
//        /// </summary>
//        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
//        /// <param name=' value'>The <see cref='IstanzeRuoli'/> to insert.</param>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='IstanzeRuoliCollection.Add'/>
//        public void Insert(int index, IstanzeRuoli value) {
//            List.Insert(index, value);
//        }
        
//        /// <summary>
//        ///    <para>Returns an enumerator that can iterate through 
//        ///       the <see cref='IstanzeRuoliCollection'/> .</para>
//        /// </summary>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='System.Collections.IEnumerator'/>
//        public new IstanzeRuoliEnumerator GetEnumerator() {
//            return new IstanzeRuoliEnumerator(this);
//        }
        
//        /// <summary>
//        ///    <para> Removes a specific <see cref='IstanzeRuoli'/> from the 
//        ///    <see cref='IstanzeRuoliCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='IstanzeRuoli'/> to remove from the <see cref='IstanzeRuoliCollection'/> .</param>
//        /// <returns><para>None.</para></returns>
//        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
//        public void Remove(IstanzeRuoli value) {
//            List.Remove(value);
//        }
        
//        public class IstanzeRuoliEnumerator : object, IEnumerator {
            
//            private IEnumerator baseEnumerator;
            
//            private IEnumerable temp;
            
//            public IstanzeRuoliEnumerator(IstanzeRuoliCollection mappings) {
//                this.temp = ((mappings));
//                this.baseEnumerator = temp.GetEnumerator();
//            }
            
//            public IstanzeRuoli Current {
//                get {
//                    return ((IstanzeRuoli)(baseEnumerator.Current));
//                }
//            }
            
//            object IEnumerator.Current {
//                get {
//                    return baseEnumerator.Current;
//                }
//            }
            
//            public bool MoveNext() {
//                return baseEnumerator.MoveNext();
//            }
            
//            bool IEnumerator.MoveNext() {
//                return baseEnumerator.MoveNext();
//            }
            
//            public void Reset() {
//                baseEnumerator.Reset();
//            }
            
//            void IEnumerator.Reset() {
//                baseEnumerator.Reset();
//            }
//        }
//    }
//}
