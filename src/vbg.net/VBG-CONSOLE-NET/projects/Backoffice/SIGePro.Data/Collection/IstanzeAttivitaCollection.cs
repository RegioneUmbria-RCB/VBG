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
//    ///       A collection that stores <see cref='Init.SIGePro.Data.IstanzeAttivita'/> objects.
//    ///    </para>
//    /// </summary>
//    /// <seealso cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/>
//    [Serializable()]
//    public class IstanzeAttivitaCollection : CollectionBase {
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/>.
//        ///    </para>
//        /// </summary>
//        public IstanzeAttivitaCollection() {
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> based on another <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/>.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> from which the contents are copied
//        /// </param>
//        public IstanzeAttivitaCollection(IstanzeAttivitaCollection value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> containing any array of <see cref='Init.SIGePro.Data.IstanzeAttivita'/> objects.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///       A array of <see cref='Init.SIGePro.Data.IstanzeAttivita'/> objects with which to intialize the collection
//        /// </param>
//        public IstanzeAttivitaCollection(IstanzeAttivita[] value) {
//            this.AddRange(value);
//        }
        
//        /// <summary>
//        /// <para>Represents the entry at the specified index of the <see cref='Init.SIGePro.Data.IstanzeAttivita'/>.</para>
//        /// </summary>
//        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
//        /// <value>
//        ///    <para> The entry at the specified index of the collection.</para>
//        /// </value>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
//        public IstanzeAttivita this[int index] {
//            get {
//                return ((IstanzeAttivita)(List[index]));
//            }
//            set {
//                List[index] = value;
//            }
//        }

//        /// <summary>
//        /// <para>Represents the entry at the specified index of the <see cref='Init.SIGePro.Data.IstanzeAttivita'/>.</para>
//        /// </summary>
//        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
//        /// <value>
//        ///    <para> The entry at the specified index of the collection.</para>
//        /// </value>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
//        public IstanzeAttivita this[string CODICEATTIVITA] 
//        {
//            get 
//            {
//                foreach( IstanzeAttivita istAtt in List )
//                {
//                    if( istAtt.CODICEATTIVITA == CODICEATTIVITA )
//                    {
//                        return istAtt;
//                    }
//                }

//                return null;
//            }
//        }


        
//        /// <summary>
//        ///    <para>Adds a <see cref='Init.SIGePro.Data.IstanzeAttivita'/> with the specified value to the 
//        ///    <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeAttivita'/> to add.</param>
//        /// <returns>
//        ///    <para>The index at which the new element was inserted.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeAttivitaCollection.AddRange'/>
//        public int Add(IstanzeAttivita value) {
//            return List.Add(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/>.</para>
//        /// </summary>
//        /// <param name='value'>
//        ///    An array of type <see cref='Init.SIGePro.Data.IstanzeAttivita'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeAttivitaCollection.Add'/>
//        public void AddRange(IstanzeAttivita[] value) {
//            for (int i = 0; (i < value.Length); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        ///     <para>
//        ///       Adds the contents of another <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> to the end of the collection.
//        ///    </para>
//        /// </summary>
//        /// <param name='value'>
//        ///    A <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> containing the objects to add to the collection.
//        /// </param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeAttivitaCollection.Add'/>
//        public void AddRange(IstanzeAttivitaCollection value) {
//            for (int i = 0; (i < value.Count); i = (i + 1)) {
//                this.Add(value[i]);
//            }
//        }
        
//        /// <summary>
//        /// <para>Gets a value indicating whether the 
//        ///    <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> contains the specified <see cref='Init.SIGePro.Data.IstanzeAttivita'/>.</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeAttivita'/> to locate.</param>
//        /// <returns>
//        /// <para><see langword='true'/> if the <see cref='Init.SIGePro.Data.IstanzeAttivita'/> is contained in the collection; 
//        ///   otherwise, <see langword='false'/>.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeAttivitaCollection.IndexOf'/>
//        public bool Contains(IstanzeAttivita value) {
//            return List.Contains(value);
//        }
        
//        /// <summary>
//        /// <para>Copies the <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
//        ///    specified index.</para>
//        /// </summary>
//        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> .</para></param>
//        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
//        /// <returns>
//        ///   <para>None.</para>
//        /// </returns>
//        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
//        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
//        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
//        /// <seealso cref='System.Array'/>
//        public void CopyTo(IstanzeAttivita[] array, int index) {
//            List.CopyTo(array, index);
//        }
        
//        /// <summary>
//        ///    <para>Returns the index of a <see cref='Init.SIGePro.Data.IstanzeAttivita'/> in 
//        ///       the <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeAttivita'/> to locate.</param>
//        /// <returns>
//        /// <para>The index of the <see cref='Init.SIGePro.Data.IstanzeAttivita'/> of <paramref name='value'/> in the 
//        /// <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/>, if found; otherwise, -1.</para>
//        /// </returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeAttivitaCollection.Contains'/>
//        public int IndexOf(IstanzeAttivita value) {
//            return List.IndexOf(value);
//        }
        
//        /// <summary>
//        /// <para>Inserts a <see cref='Init.SIGePro.Data.IstanzeAttivita'/> into the <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> at the specified index.</para>
//        /// </summary>
//        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
//        /// <param name=' value'>The <see cref='Init.SIGePro.Data.IstanzeAttivita'/> to insert.</param>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='Init.SIGePro.Collection.IstanzeAttivitaCollection.Add'/>
//        public void Insert(int index, IstanzeAttivita value) {
//            List.Insert(index, value);
//        }
        
//        /// <summary>
//        ///    <para>Returns an enumerator that can iterate through 
//        ///       the <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> .</para>
//        /// </summary>
//        /// <returns><para>None.</para></returns>
//        /// <seealso cref='System.Collections.IEnumerator'/>
//        public new IstanzaAttivitaEnumerator GetEnumerator() {
//            return new IstanzaAttivitaEnumerator(this);
//        }
        
//        /// <summary>
//        ///    <para> Removes a specific <see cref='Init.SIGePro.Data.IstanzeAttivita'/> from the 
//        ///    <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> .</para>
//        /// </summary>
//        /// <param name='value'>The <see cref='Init.SIGePro.Data.IstanzeAttivita'/> to remove from the <see cref='Init.SIGePro.Collection.IstanzeAttivitaCollection'/> .</param>
//        /// <returns><para>None.</para></returns>
//        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
//        public void Remove(IstanzeAttivita value) {
//            List.Remove(value);
//        }
        
//        public class IstanzaAttivitaEnumerator : object, IEnumerator {
            
//            private IEnumerator baseEnumerator;
            
//            private IEnumerable temp;
            
//            public IstanzaAttivitaEnumerator(IstanzeAttivitaCollection mappings) {
//                this.temp = mappings;
//                this.baseEnumerator = temp.GetEnumerator();
//            }
            
//            public IstanzeAttivita Current {
//                get {
//                    return ((IstanzeAttivita)(baseEnumerator.Current));
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
