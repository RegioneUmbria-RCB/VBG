using System;
using System.Collections;
using Init.SIGePro.Data;
// ------------------------------------------------------------------------------
// <copyright from='1997' to='2001' company='Microsoft Corporation'>
//    Copyright (c) Microsoft Corporation. All Rights Reserved.   
//    Information Contained Herein is Proprietary and Confidential.       
// </copyright> 
// ------------------------------------------------------------------------------
// 
namespace Init.SIGePro.Collection {
	/// <summary>
    ///     <para>
    ///       A collection that stores <see cref='Init.SIGePro.Data.DynDati'/> objects.
    ///    </para>
    /// </summary>
    /// <seealso cref='Init.SIGePro.Collection.DynDatiCollection'/>
    [Serializable()]
    public class DynDatiCollection : CollectionBase {
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.DynDatiCollection'/>.
        ///    </para>
        /// </summary>
        public DynDatiCollection() {
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.DynDatiCollection'/> based on another <see cref='Init.SIGePro.Collection.DynDatiCollection'/>.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A <see cref='Init.SIGePro.Collection.DynDatiCollection'/> from which the contents are copied
        /// </param>
        public DynDatiCollection(DynDatiCollection value) {
            this.AddRange(value);
        }
        
        /// <summary>
        ///     <para>
        ///       Initializes a new instance of <see cref='Init.SIGePro.Collection.DynDatiCollection'/> containing any array of <see cref='Init.SIGePro.Data.DynDati'/> objects.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///       A array of <see cref='Init.SIGePro.Data.DynDati'/> objects with which to intialize the collection
        /// </param>
        public DynDatiCollection(DynDati[] value) {
            this.AddRange(value);
        }
        
        /// <summary>
        /// <para>Represents the entry at the specified index of the <see cref='Init.SIGePro.Data.DynDati'/>.</para>
        /// </summary>
        /// <param name='index'><para>The zero-based index of the entry to locate in the collection.</para></param>
        /// <value>
        ///    <para> The entry at the specified index of the collection.</para>
        /// </value>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is outside the valid range of indexes for the collection.</exception>
        public DynDati this[int index] {
            get {
                return ((DynDati)(List[index]));
            }
            set {
                List[index] = value;
            }
        }
        
        /// <summary>
        ///    <para>Adds a <see cref='Init.SIGePro.Data.DynDati'/> with the specified value to the 
        ///    <see cref='Init.SIGePro.Collection.DynDatiCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='Init.SIGePro.Data.DynDati'/> to add.</param>
        /// <returns>
        ///    <para>The index at which the new element was inserted.</para>
        /// </returns>
        /// <seealso cref='Init.SIGePro.Collection.DynDatiCollection.AddRange'/>
        public int Add(DynDati value) {
            return List.Add(value);
        }

		/// <summary>
		///    <para>Adds a <see cref='Init.SIGePro.Data.DynDati'/> with the specified value to the 
		///    <see cref='Init.SIGePro.Collection.DynDatiCollection'/> .</para>
		/// </summary>
		/// <param name='value'>The <see cref='Init.SIGePro.Data.DynDati'/> to add.</param>
		/// <returns>
		///    <para>The index at which the new element was inserted.</para>
		/// </returns>
		/// <seealso cref='Init.SIGePro.Collection.DynDatiCollection.AddRange'/>
		public int Add( string IDCOMUNE, string CODICERECORDCORRELATO, string CODICECAMPO, string FKIDTESTATA, string VALORE ) 
		{
			DynDati pClass = new DynDati();
			pClass.CODICECAMPO = CODICECAMPO;
			pClass.CODICERECORDCORRELATO = CODICERECORDCORRELATO;
			pClass.FKIDTESTATA = FKIDTESTATA;
			pClass.IDCOMUNE = IDCOMUNE;
			pClass.VALORE = VALORE;

			return List.Add(pClass);
		}
        
        /// <summary>
        /// <para>Copies the elements of an array to the end of the <see cref='Init.SIGePro.Collection.DynDatiCollection'/>.</para>
        /// </summary>
        /// <param name='value'>
        ///    An array of type <see cref='Init.SIGePro.Data.DynDati'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='Init.SIGePro.Collection.DynDatiCollection.Add'/>
        public void AddRange(DynDati[] value) {
            for (int i = 0; (i < value.Length); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        ///     <para>
        ///       Adds the contents of another <see cref='Init.SIGePro.Collection.DynDatiCollection'/> to the end of the collection.
        ///    </para>
        /// </summary>
        /// <param name='value'>
        ///    A <see cref='Init.SIGePro.Collection.DynDatiCollection'/> containing the objects to add to the collection.
        /// </param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <seealso cref='Init.SIGePro.Collection.DynDatiCollection.Add'/>
        public void AddRange(DynDatiCollection value) {
            for (int i = 0; (i < value.Count); i = (i + 1)) {
                this.Add(value[i]);
            }
        }
        
        /// <summary>
        /// <para>Gets a value indicating whether the 
        ///    <see cref='Init.SIGePro.Collection.DynDatiCollection'/> contains the specified <see cref='Init.SIGePro.Data.DynDati'/>.</para>
        /// </summary>
        /// <param name='value'>The <see cref='Init.SIGePro.Data.DynDati'/> to locate.</param>
        /// <returns>
        /// <para><see langword='true'/> if the <see cref='Init.SIGePro.Data.DynDati'/> is contained in the collection; 
        ///   otherwise, <see langword='false'/>.</para>
        /// </returns>
        /// <seealso cref='Init.SIGePro.Collection.DynDatiCollection.IndexOf'/>
        public bool Contains(DynDati value) {
            return List.Contains(value);
        }
        
        /// <summary>
        /// <para>Copies the <see cref='Init.SIGePro.Collection.DynDatiCollection'/> values to a one-dimensional <see cref='System.Array'/> instance at the 
        ///    specified index.</para>
        /// </summary>
        /// <param name='array'><para>The one-dimensional <see cref='System.Array'/> that is the destination of the values copied from <see cref='Init.SIGePro.Collection.DynDatiCollection'/> .</para></param>
        /// <param name='index'>The index in <paramref name='array'/> where copying begins.</param>
        /// <returns>
        ///   <para>None.</para>
        /// </returns>
        /// <exception cref='System.ArgumentException'><para><paramref name='array'/> is multidimensional.</para> <para>-or-</para> <para>The number of elements in the <see cref='Init.SIGePro.Collection.DynDatiCollection'/> is greater than the available space between <paramref name='index'/> and the end of <paramref name='array'/>.</para></exception>
        /// <exception cref='System.ArgumentNullException'><paramref name='array'/> is <see langword='null'/>. </exception>
        /// <exception cref='System.ArgumentOutOfRangeException'><paramref name='index'/> is less than <paramref name='array'/>'s lowbound. </exception>
        /// <seealso cref='System.Array'/>
        public void CopyTo(DynDati[] array, int index) {
            List.CopyTo(array, index);
        }
        
        /// <summary>
        ///    <para>Returns the index of a <see cref='Init.SIGePro.Data.DynDati'/> in 
        ///       the <see cref='Init.SIGePro.Collection.DynDatiCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='Init.SIGePro.Data.DynDati'/> to locate.</param>
        /// <returns>
        /// <para>The index of the <see cref='Init.SIGePro.Data.DynDati'/> of <paramref name='value'/> in the 
        /// <see cref='Init.SIGePro.Collection.DynDatiCollection'/>, if found; otherwise, -1.</para>
        /// </returns>
        /// <seealso cref='Init.SIGePro.Collection.DynDatiCollection.Contains'/>
        public int IndexOf(DynDati value) {
            return List.IndexOf(value);
        }
        
        /// <summary>
        /// <para>Inserts a <see cref='Init.SIGePro.Data.DynDati'/> into the <see cref='Init.SIGePro.Collection.DynDatiCollection'/> at the specified index.</para>
        /// </summary>
        /// <param name='index'>The zero-based index where <paramref name='value'/> should be inserted.</param>
        /// <param name=' value'>The <see cref='Init.SIGePro.Data.DynDati'/> to insert.</param>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='Init.SIGePro.Collection.DynDatiCollection.Add'/>
        public void Insert(int index, DynDati value) {
            List.Insert(index, value);
        }
        
        /// <summary>
        ///    <para>Returns an enumerator that can iterate through 
        ///       the <see cref='Init.SIGePro.Collection.DynDatiCollection'/> .</para>
        /// </summary>
        /// <returns><para>None.</para></returns>
        /// <seealso cref='System.Collections.IEnumerator'/>
        public new DynDatoEnumerator GetEnumerator() {
            return new DynDatoEnumerator(this);
        }
        
        /// <summary>
        ///    <para> Removes a specific <see cref='Init.SIGePro.Data.DynDati'/> from the 
        ///    <see cref='Init.SIGePro.Collection.DynDatiCollection'/> .</para>
        /// </summary>
        /// <param name='value'>The <see cref='Init.SIGePro.Data.DynDati'/> to remove from the <see cref='Init.SIGePro.Collection.DynDatiCollection'/> .</param>
        /// <returns><para>None.</para></returns>
        /// <exception cref='System.ArgumentException'><paramref name='value'/> is not found in the Collection. </exception>
        public void Remove(DynDati value) {
            List.Remove(value);
        }
        
        public class DynDatoEnumerator : object, IEnumerator {
            
            private IEnumerator baseEnumerator;
            
            private IEnumerable temp;
            
            public DynDatoEnumerator(DynDatiCollection mappings) {
                this.temp = mappings;
                this.baseEnumerator = temp.GetEnumerator();
            }
            
            public DynDati Current {
                get {
                    return ((DynDati)(baseEnumerator.Current));
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
