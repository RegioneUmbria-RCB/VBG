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
//    ///       A collection that stores <see cref='Init.SIGePro.Data.Movimenti'/> objects.
//    ///    </para>
//    /// </summary>
//    /// <seealso cref='Init.SIGePro.Collection.MovimentiCollection'/>
//    [Serializable()]
//    public class MovimentiCollection : CollectionBase {
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.MovimentiCollection'/>.
//        ///    </para>
//        /// </summary>
//        public MovimentiCollection() {
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.MovimentiCollection'/> based on another <see cref='Init.SIGePro.Collection.MovimentiCollection'/>.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A <see cref='Init.SIGePro.Collection.MovimentiCollection'/> from which the contents are copied
//        /// </param>
//        public MovimentiCollection(MovimentiCollection value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.MovimentiCollection'/> containing any array of <see cref='Init.SIGePro.Data.Movimenti'/> objects.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A array of <see cref='Init.SIGePro.Data.Movimenti'/> objects with which to intialize the collection
//        /// </param>
//        public MovimentiCollection(Movimenti[] value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        /// <para>Represents the entry at the specified index of the <see cref='Init.SIGePro.Data.Movimenti'/>.</para>
//        /// </summary>
//        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
//        /// <value>
//        ///    <para> The entry at the specified index of the collection.</para>
//        /// </value>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
//        public Movimenti this[int index] {
//            get {
//                return ((Movimenti)(List[index]));
//            }
//            set {
//                List[index] = value;
//            }
//        }
        
//        /// <summary>
//        ///    <para>Adds a <see cref='Init.SIGePro.Data.Movimenti'/> with the specified value to the 
//        ///    <see cref='Init.SIGePro.Collection.MovimentiCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.Movimenti'/> to add.</param>
//        /// <returns>
//        ///    <para>The index at which the new element was inserted.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.MovimentiCollection.AddRange'/>
//        public int Add(Movimenti value) {
//            return List.Add(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.MovimentiCollection'/>.</para>
//        /// </summary>
//        /// <param name='value'>
//        ///    An array of type <see cref='Init.SIGePro.Data.Movimenti'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.MovimentiCollection.Add'/>
//        public void AddRange(Movimenti[] value) {
//            for (int i = 0; (i < value.Length); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Adds the contents of another <see cref='Init.SIGePro.Collection.MovimentiCollection'/> to the end of the collection.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///    A <see cref='Init.SIGePro.Collection.MovimentiCollection'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.MovimentiCollection.Add'/>
//        public void AddRange(MovimentiCollection value) {
//            for (int i = 0; (i < value.Count); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        /// <para>Gets a value indicating whether the 
//        ///    <see cref='Init.SIGePro.Collection.MovimentiCollection'/> contains the specified <see cref='Init.SIGePro.Data.Movimenti'/>.</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.Movimenti'/> to locate.</param>
//        /// <returns>
//        /// <para><see langword='true'/> if the <see cref='Init.SIGePro.Data.Movimenti'/> is contained in the collection; 
//        ///   otherwise, <see langword='false'/>.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.MovimentiCollection.IndexOf'/>
//        public bool Contains(Movimenti value) {
//            return List.Contains(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the <see cref='Init.SIGePro.Collection.MovimentiCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
//        ///    specified index.</para>
//        /// </summary>
//        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.MovimentiCollection'/> .</para></param>
//        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.MovimentiCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
//        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
//        /// <seealso cref='System.Array'/>
//        public void CopyTo(Movimenti[] array, int index) {
//            List.CopyTo(array, index);
//        }
        
//        /// <summary>
//        ///    <para>Returns the index of a <see cref='Init.SIGePro.Data.Movimenti'/> in 
//        ///       the <see cref='Init.SIGePro.Collection.MovimentiCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.Movimenti'/> to locate.</param>
//        /// <returns>
//        /// <para>The index of the <see cref='Init.SIGePro.Data.Movimenti'/> of <paramref name='value'/> in the 
//        /// <see cref='Init.SIGePro.Collection.MovimentiCollection'/>, if found; otherwise, -1.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.MovimentiCollection.Contains'/>
//        public int IndexOf(Movimenti value) {
//            return List.IndexOf(value);
//        }
        
//        /// <summary>
//        /// <para>Inserts a <see cref='Init.SIGePro.Data.Movimenti'/> into the <see cref='Init.SIGePro.Collection.MovimentiCollection'/> at the specified index.</para>
//        /// </summary>
//        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
//        /// <param name=' value'>The <see cref='Init.SIGePro.Data.Movimenti'/> to insert.</param>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='Init.SIGePro.Collection.MovimentiCollection.Add'/>
//        public void Insert(int index, Movimenti value) {
//            List.Insert(index, value);
//        }
        
//        /// <summary>
//        ///    <para>Returns an enumerator that can iterate through 
//        ///       the <see cref='Init.SIGePro.Collection.MovimentiCollection'/> .</para>
//        /// </summary>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='System.Collections.IEnumerator'/>
//        public new MovimentoEnumerator GetEnumerator() {
//            return new MovimentoEnumerator(this);
//        }
        
//        /// <summary>
//        ///    <para> Removes a specific <see cref='Init.SIGePro.Data.Movimenti'/> from the 
//        ///    <see cref='Init.SIGePro.Collection.MovimentiCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.Movimenti'/> to remove from the <see cref='Init.SIGePro.Collection.MovimentiCollection'/> .</param>
//        /// <returns><para>None.</para></returns>
//        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
//        public void Remove(Movimenti value) {
//            List.Remove(value);
//        }
        
//        public class MovimentoEnumerator : object, IEnumerator {
            
//            private IEnumerator baseEnumerator;
            
//            private IEnumerable temp;
            
//            public MovimentoEnumerator(MovimentiCollection mappings) {
//                this.temp = mappings;
//                this.baseEnumerator = temp.GetEnumerator();
//            }
            
//            public Movimenti Current {
//                get {
//                    return ((Movimenti)(baseEnumerator.Current));
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
