using System;
using System.Collections;
using System.IO;

namespace SIGePro.Net.Data
{
	public class AttachmentHashTable : IDictionary, ICollection, IEnumerable, ICloneable
	{
		protected Hashtable innerHash;

		#region "Constructors"

		public AttachmentHashTable()
		{
			innerHash = new Hashtable();
		}

		public AttachmentHashTable(AttachmentHashTable original)
		{
			innerHash = new Hashtable(original.innerHash);
		}

		public AttachmentHashTable(IDictionary dictionary)
		{
			innerHash = new Hashtable(dictionary);
		}

		public AttachmentHashTable(int capacity)
		{
			innerHash = new Hashtable(capacity);
		}

		public AttachmentHashTable(IDictionary dictionary, float loadFactor)
		{
			innerHash = new Hashtable(dictionary, loadFactor);
		}

		public AttachmentHashTable(IHashCodeProvider codeProvider, IComparer comparer)
		{
			innerHash = new Hashtable(codeProvider, comparer);
		}

		public AttachmentHashTable(int capacity, int loadFactor)
		{
			innerHash = new Hashtable(capacity, loadFactor);
		}

		public AttachmentHashTable(IDictionary dictionary, IHashCodeProvider codeProvider, IComparer comparer)
		{
			innerHash = new Hashtable(dictionary, codeProvider, comparer);
		}

		public AttachmentHashTable(int capacity, IHashCodeProvider codeProvider, IComparer comparer)
		{
			innerHash = new Hashtable(capacity, codeProvider, comparer);
		}

		public AttachmentHashTable(IDictionary dictionary, float loadFactor, IHashCodeProvider codeProvider, IComparer comparer)
		{
			innerHash = new Hashtable(dictionary, loadFactor, codeProvider, comparer);
		}

		public AttachmentHashTable(int capacity, float loadFactor, IHashCodeProvider codeProvider, IComparer comparer)
		{
			innerHash = new Hashtable(capacity, loadFactor, codeProvider, comparer);
		}

		#endregion

		#region Implementation of IDictionary

		public AttachmentHashTableEnumerator GetEnumerator()
		{
			return new AttachmentHashTableEnumerator(this);
		}

		IDictionaryEnumerator IDictionary.GetEnumerator()
		{
			return new AttachmentHashTableEnumerator(this);
		}

		IEnumerator IEnumerable.GetEnumerator()
		{
			return GetEnumerator();
		}

		public void Remove(string key)
		{
			innerHash.Remove(key);
		}

		void IDictionary.Remove(object key)
		{
			Remove((string) key);
		}

		public bool Contains(string key)
		{
			return innerHash.Contains(key);
		}

		bool IDictionary.Contains(object key)
		{
			return Contains((string) key);
		}

		public void Clear()
		{
			innerHash.Clear();
		}

		public void Add(string key, MemoryStream value)
		{
			innerHash.Add(key, value);
		}

		void IDictionary.Add(object key, object value)
		{
			Add((string) key, (MemoryStream) value);
		}

		public bool IsReadOnly
		{
			get { return innerHash.IsReadOnly; }
		}

		public MemoryStream this[string key]
		{
			get { return (MemoryStream) innerHash[key]; }
			set { innerHash[key] = value; }
		}

		object IDictionary.this[object key]
		{
			get { return this[(string) key]; }
			set { this[(string) key] = (MemoryStream) value; }
		}

		public ICollection Values
		{
			get { return innerHash.Values; }
		}

		public ICollection Keys
		{
			get { return innerHash.Keys; }
		}

		public bool IsFixedSize
		{
			get { return innerHash.IsFixedSize; }
		}

		#endregion

		#region Implementation of ICollection

		public void CopyTo(Array array, int index)
		{
			innerHash.CopyTo(array, index);
		}

		public bool IsSynchronized
		{
			get { return innerHash.IsSynchronized; }
		}

		public int Count
		{
			get { return innerHash.Count; }
		}

		public object SyncRoot
		{
			get { return innerHash.SyncRoot; }
		}

		#endregion

		#region Implementation of ICloneable

		public AttachmentHashTable Clone()
		{
			AttachmentHashTable clone = new AttachmentHashTable();
			clone.innerHash = (Hashtable) innerHash.Clone();
			return clone;
		}

		object ICloneable.Clone()
		{
			return Clone();
		}

		#endregion

		#region "HashTable Methods"

		public bool ContainsKey(string key)
		{
			return innerHash.ContainsKey(key);
		}

		public bool ContainsValue(MemoryStream value)
		{
			return innerHash.ContainsValue(value);
		}

		public static AttachmentHashTable Synchronized(AttachmentHashTable nonSync)
		{
			AttachmentHashTable sync = new AttachmentHashTable();
			sync.innerHash = Hashtable.Synchronized(nonSync.innerHash);
			return sync;
		}

		#endregion

		public Hashtable InnerHash
		{
			get { return innerHash; }
		}
	}

	public class AttachmentHashTableEnumerator : IDictionaryEnumerator
	{
		private IDictionaryEnumerator innerEnumerator;

		public AttachmentHashTableEnumerator(AttachmentHashTable enumerable)
		{
			innerEnumerator = enumerable.InnerHash.GetEnumerator();
		}

		#region Implementation of IDictionaryEnumerator

		public string Key
		{
			get { return (string) innerEnumerator.Key; }
		}

		object IDictionaryEnumerator.Key
		{
			get { return Key; }
		}

		public MemoryStream Value
		{
			get { return (MemoryStream) innerEnumerator.Value; }
		}

		object IDictionaryEnumerator.Value
		{
			get { return Value; }
		}

		public DictionaryEntry Entry
		{
			get { return innerEnumerator.Entry; }
		}

		#endregion

		#region Implementation of IEnumerator

		public void Reset()
		{
			innerEnumerator.Reset();
		}

		public bool MoveNext()
		{
			return innerEnumerator.MoveNext();
		}

		public object Current
		{
			get { return innerEnumerator.Current; }
		}

		#endregion
	}
}