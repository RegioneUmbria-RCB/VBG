//using System;
//using System.Collections;
//using Init.SIGePro.Data;
//// ------------------------------------------------------------------------------
//// <copyright from='1997' to='2001' company='Microsoft Corporation'>
////    Copyright (c) Microsoft Corporation. All Rights Reserved.   
////    Information Contained Herein is Proprietary and Confidential.       
//// </copyright> 
//// ------------------------------------------------------------------------------
//// 
//namespace Init.SIGePro.Collection {
//    /// <summary>
//    ///     <para>
//    ///       A collection that stores <see cref='IstanzeMappali'/> objects.
//    ///    </para>
//    /// </summary>
//    /// <seealso cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/>
//    [Serializable()]
//    public class IstanzeMappaliCollection : CollectionBase {
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/>.
//        ///    </para>
//        /// </summary>
//        public IstanzeMappaliCollection() {
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> based on another <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/>.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> from which the contents are copied
//        /// </param>
//        public IstanzeMappaliCollection(IstanzeMappaliCollection value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> containing any array of <see cref='IstanzeMappali'/> objects.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A array of <see cref='IstanzeMappali'/> objects with which to intialize the collection
//        /// </param>
//        public IstanzeMappaliCollection(IstanzeMappali[] value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        /// <para>Represents the entry at the specified index of the <see cref='IstanzeMappali'/>.</para>
//        /// </summary>
//        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
//        /// <value>
//        ///    <para> The entry at the specified index of the collection.</para>
//        /// </value>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
//        public IstanzeMappali this[int index] {
//            get {
//                return ((IstanzeMappali)(List[index]));
//            }
//            set {
//                List[index] = value;
//            }
//        }
        
//        /// <summary>
//        ///    <para>Adds a <see cref='Init.SIGePro.Data.IstanzeMappali'/> with the specified value to the 
//        ///    <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeMappali'/> to add.</param>
//        /// <returns>
//        ///    <para>The index at which the new element was inserted.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeMappaliCollection.AddRange'/>
//        public int Add(IstanzeMappali value) {
//            return List.Add(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/>.</para>
//        /// </summary>
//        /// <param name='value'>
//        ///    An array of type <see cref='Init.SIGePro.Data.IstanzeMappali'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeMappaliCollection.Add'/>
//        public void AddRange(IstanzeMappali[] value) {
//            for (int i = 0; (i < value.Length); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Adds the contents of another <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> to the end of the collection.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///    A <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeMappaliCollection.Add'/>
//        public void AddRange(IstanzeMappaliCollection value) {
//            for (int i = 0; (i < value.Count); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        /// <para>Gets a value indicating whether the 
//        ///    <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> contains the specified <see cref='Init.SIGePro.Data.IstanzeMappali'/>.</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeMappali'/> to locate.</param>
//        /// <returns>
//        /// <para><see langword='true'/> if the <see cref='Init.SIGePro.Data.IstanzeMappali'/> is contained in the collection; 
//        ///   otherwise, <see langword='false'/>.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeMappaliCollection.IndexOf'/>
//        public bool Contains(IstanzeMappali value) {
//            return List.Contains(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
//        ///    specified index.</para>
//        /// </summary>
//        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> .</para></param>
//        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
//        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
//        /// <seealso cref='System.Array'/>
//        public void CopyTo(IstanzeMappali[] array, int index) {
//            List.CopyTo(array, index);
//        }
        
//        /// <summary>
//        ///    <para>Returns the index of a <see cref='Init.SIGePro.Data.IstanzeMappali'/> in 
//        ///       the <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeMappali'/> to locate.</param>
//        /// <returns>
//        /// <para>The index of the <see cref='Init.SIGePro.Data.IstanzeMappali'/> of <paramref name='value'/> in the 
//        /// <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/>, if found; otherwise, -1.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeMappaliCollection.Contains'/>
//        public int IndexOf(IstanzeMappali value) {
//            return List.IndexOf(value);
//        }
        
//        /// <summary>
//        /// <para>Inserts a <see cref='Init.SIGePro.Data.IstanzeMappali'/> into the <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> at the specified index.</para>
//        /// </summary>
//        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
//        /// <param name=' value'>The <see cref='Init.SIGePro.Data.IstanzeMappali'/> to insert.</param>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeMappaliCollection.Add'/>
//        public void Insert(int index, IstanzeMappali value) {
//            List.Insert(index, value);
//        }
        
//        /// <summary>
//        ///    <para>Returns an enumerator that can iterate through 
//        ///       the <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> .</para>
//        /// </summary>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='System.Collections.IEnumerator'/>
//        public new IstanzaMappaleEnumerator GetEnumerator() {
//            return new IstanzaMappaleEnumerator(this);
//        }
        
//        /// <summary>
//        ///    <para> Removes a specific <see cref='Init.SIGePro.Data.IstanzeMappali'/> from the 
//        ///    <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeMappali'/> to remove from the <see cref='Init.SIGePro.Collection.IstanzeMappaliCollection'/> .</param>
//        /// <returns><para>None.</para></returns>
//        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
//        public void Remove(IstanzeMappali value) {
//            List.Remove(value);
//        }
        
//        public class IstanzaMappaleEnumerator : object, IEnumerator {
            
//            private IEnumerator baseEnumerator;
            
//            private IEnumerable temp;
            
//            public IstanzaMappaleEnumerator(IstanzeMappaliCollection mappings) {
//                this.temp = mappings;
//                this.baseEnumerator = temp.GetEnumerator();
//            }
            
//            public IstanzeMappali Current {
//                get {
//                    return ((IstanzeMappali)(baseEnumerator.Current));
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
