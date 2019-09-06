// Preview of code that will be generated in the implementation file:


using System;
using System.Collections;
using System.Data;
using System.Reflection;
using Init.Utils;

/// <summary>
/// E' una classe di tipo System.Collections.Hashtable.
/// E' stato fatto l'override del metodo Add in maniera tale che l'Add di una chiave esistente non va in eccezione
/// ma aggiorna il valore della key trovata.
/// E' stato aggiunto il metodo Replace.
/// </summary>
public class MemorySpace : System.Collections.Hashtable
{
	public MemorySpace(): base()
	{
	}

	#region Proprietà della classe MemorySpace
	private int iProgressivoRecord;
	public int ProgressivoRecord
	{
		get {return iProgressivoRecord; }
		set { iProgressivoRecord = value; }
	}

    //private int iRecordCount;
    //public int RecordCount
    //{
    //    get {return iRecordCount; }
    //    set { iRecordCount = value; }
    //}
	#endregion

	#region Metodi della classe MemorySpace
	/// <summary>
	/// Restitiusce la stringa passata dopo aver sostituito i testi &xxx con i valori delle key xxx trovati nell'Hashtable
	/// </summary>
	/// <param name="Text">Testo contenente variabili di tipo &xxx dove xxx viene cercata come key nell'Hashtable</param>
	/// <returns>Ritorna il parametro text sostituito.</returns>
	public string Replace(string text)
	{
		string newText=string.Empty;
		if ( !StringChecker.IsStringEmpty(text) )
		{
			newText = text;
			string mCharacter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";
            bool bEmpty = true;

			foreach (object key in base.Keys)
			{
				string mVar = "&" + key.ToString();
				int mPos=-1;
				
				while ((newText!=System.String.Empty) && (mPos + 1 <= newText.Length - 1) && ((mPos = newText.IndexOf(mVar,mPos+1))>=0))
				{
					if ((mPos + mVar.Length) == newText.Length)
					{
						//La mVar trovata è in fondo alla stringa
						newText = newText.Remove(mPos,mVar.Length) + base[key].ToString();
					}
					else
					{ 
						//Confronto il carattere successivo a mVar, se è compreso nella lista dei caratteri indicati in mCharacter allora
						//non devo sostituire la "variabile" mVar
                        if (mCharacter.IndexOf(newText[mPos + mVar.Length].ToString()) == -1)
                        {
                            newText = newText.Replace(mVar + newText[mPos + mVar.Length].ToString(), base[key].ToString() + newText[mPos + mVar.Length].ToString());
                        }
                        else
                            bEmpty = false;
					}
				}
			}

            //Se il valore non viene sostituito:
			if ( (newText!=System.String.Empty) && (newText.Substring(0,1) == "&") && bEmpty )
				newText = System.String.Empty;
		}
		return (newText);
	}

	public override void Add(object key, object value)
	{
		if (base.ContainsKey(key.ToString().ToUpper()))
		{
			base[key.ToString().ToUpper()] = value;
		}
		else
		{
			base.Add(key.ToString().ToUpper(), value);
		}
	}

	/// <summary>
	/// Aggiunge una key per ogni column della row.
	/// </summary>
	/// <param name="row"></param>
	public void Add(DataRow row)
	{
		foreach (DataColumn dc in row.Table.Columns)
		{
			this.Add(dc.ColumnName, row[dc.ColumnName]);
		}
	}

	public override object Clone()
	{
		MemorySpace _memory = new MemorySpace();

		foreach (object elem in this.Keys)
		{
			_memory.Add(elem,this[elem]);			
		}
		return _memory;
	}
	#endregion

}// END CLASS DEFINITION MemorySpace