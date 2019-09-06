using System;
using System.Reflection;
using Init.SIGeProExport.Attributes;
using Init.SIGeProExport.Exceptions;
using Init.SIGeProExport.Utils;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGeProExport.Validator
{
	/// <summary>
	/// Descrizione di riepilogo per ClassValidator.
	/// </summary>
	public class ClassValidator
	{
		protected DataClass  _pclass;

		public ClassValidator( DataClass p_class )
		{
			_pclass = p_class;
		}

		
		public bool RequiredFieldValidator( DataBase _db )
		{
			bool retVal = true;

			Type objType = _pclass.GetType();
			DataTableAttribute[] dataTables = (DataTableAttribute[])objType.GetCustomAttributes(typeof(DataTableAttribute), true);

			if (dataTables.Length > 0)
			{
				PropertyInfo[] properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);
				for (int i=0; i < properties.Length; i++)
				{

					useSequenceAttribute[] seqfields = ( useSequenceAttribute[] ) properties[i].GetCustomAttributes( typeof(useSequenceAttribute),true );
					isRequiredAttribute[] fields = ( isRequiredAttribute[] ) properties[i].GetCustomAttributes( typeof(isRequiredAttribute),true );
					BaseFieldAttribute[] basefields = ( BaseFieldAttribute[] ) properties[i].GetCustomAttributes( typeof(BaseFieldAttribute),true );
					
					if ( basefields.Length > 0 )
					{

						//c'è un attributo useSequence
						if ( seqfields.Length > 0 )
						{
							PropertyInfo propIdComune = objType.GetProperty("IDCOMUNE");
							
							string seqName = _pclass.DataTableName + "." + properties[i].Name;
							string idComune = propIdComune.GetValue( _pclass, null ).ToString();

							properties[i].SetValue( _pclass, GetNextVal( _db, idComune, seqName  ).ToString() , null );
						}


						//c'è un' attributo di tipo isrequired
						if ( fields.Length > 0  )
						{
							object obj = properties[i].GetValue( _pclass,null );

							if (  StringChecker.IsObjectEmpty( obj ) )
							{
								if ( StringChecker.IsStringEmpty( fields[0].SOFTWARE ) )
								{
									retVal = false;
								}
								else	//il campo è obbligatorio per un determinato software
								{
									int SoftwarePropertyIndex = GetPropertyIndex( properties, "SOFTWARE" );

									if ( SoftwarePropertyIndex == -1 )
									{
										retVal = false;
										fields[0].MSG = "Il campo " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio per un determinato software ma la classe non ha il campo software";
									}
									else
									{
										object fldSoftware = properties[SoftwarePropertyIndex].GetValue( _pclass, null );

										if ( ! StringChecker.IsObjectEmpty( fldSoftware ) )
										{
											if ( fields[0].SOFTWARE.IndexOf( fldSoftware.ToString()  ) > -1 )
											{
												retVal = false;
												fields[0].MSG = "Il campo " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio per il software " + fields[0].SOFTWARE;
											}
										}
									}
								}
							
							}
						}
						else if ( basefields[0].GetType() == typeof(KeyFieldAttribute) )
						{
							if ( ! (basefields[0] as KeyFieldAttribute ).KeyIdentity )
							{
								object obj = properties[i].GetValue( _pclass,null );
								if (  StringChecker.IsObjectEmpty( obj ) )
								{
									retVal = false;
									throw( new RequiredFieldException("Il campo chiave " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio")  );
								}
							}
						}

						if ( retVal == false ) 
						{
							if ( StringChecker.IsStringEmpty( fields[0].MSG ) )
								throw( new RequiredFieldException("Il campo " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio")  );
							else
								throw( new RequiredFieldException( fields[0].MSG ) );
						}
					}
				}
			}
	
			return retVal;
		}
		public bool RequiredFieldValidator()
		{
			return RequiredFieldValidator( null );
		}



		/// <summary>
		/// La funzione ritorna il prossimo valore di una sequenza nella tabella SEQUENCETABLE
		/// </summary>
		/// <param name="db">PersonalLib2.Data.DataBase con la connessione attiva</param>
		/// <param name="idComune">Idcomune</param>
		/// <param name="sequenceName">Nome della sequenza da leggere</param>
		/// <returns></returns>
		protected int GetNextVal( DataBase db, string idComune, string sequenceName )
		{
			return GetNextVal( db, "SEQUENCETABLE", "CURRVAL", idComune, sequenceName );
		}
		/// <summary>
		/// La funzione ritorna il prossimo valore di una sequenza
		/// </summary>
		/// <param name="db">PersonalLib2.Data.DataBase con la connessione attiva</param>
		/// <param name="tableName">Tabella che contiene la sequenza da leggere</param>
		/// <param name="fieldName">Nome del campo con il valore attuale della sequenza</param>
		/// <param name="idComune">Idcomune</param>
		/// <param name="sequenceName">Nome della sequenza da leggere</param>
		/// <returns></returns>
		public int GetNextVal( DataBase db, string tableName, string fieldName, string idComune, string sequenceName )
		{
			string whereCondition = String.Empty;

			whereCondition = " WHERE IDCOMUNE = '" + idComune + "' AND SEQUENCENAME = '" + sequenceName + "'";

			Sequence seq = new Sequence();

			seq.Db = db;
			seq.TableName = tableName;
			seq.FieldName = fieldName;
			seq.WhereCondition = whereCondition;

			return seq.NextVal();
		}

		
		protected int GetPropertyIndex( PropertyInfo[] properties, string PropertyName )
		{
			int SoftwarePropertyIndex = -1;
			int Counter = 0;

			foreach( PropertyInfo fldProperty in properties )
			{
				if ( fldProperty.Name.ToUpper() == PropertyName )
				{
					SoftwarePropertyIndex = Counter;
					break;
				}

				Counter = Counter + 1;
			}

			return SoftwarePropertyIndex;
		}
	}
}
