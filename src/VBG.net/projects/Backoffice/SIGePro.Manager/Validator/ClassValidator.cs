using System;
using System.Reflection;
using Init.SIGePro.Attributes;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Utils;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Validator
{

	public enum AmbitoValidazione
	{
		Insert,
		Update
	}

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

		
		public bool RequiredFieldValidator( DataBase _db , AmbitoValidazione ambitoValidazione)
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
							PropertyInfo propIdComune = EstraiPropertyIdComune( objType );

							// Se sto effettuando una insert e la classe richiede l'utilizzo di una sequenza stacco il valore della sequenza
							if (ambitoValidazione == AmbitoValidazione.Insert)
							{
								object propVal = properties[i].GetValue(_pclass, null);

								if ( !VerificaSeValoreNull( propVal , properties[i] ) )
								{
									throw new InvalidOperationException( "Errore durante l'estrazione del valore di sequenza per la proprietà " + properties[i].Name + " della classe " + objType.Name + " durante l'inserimento: la proprietà è già valorizzata" );
								}

								string seqName = _pclass.DataTableName + "." + properties[i].Name;
								string idComune = propIdComune.GetValue(_pclass, null).ToString();

								int nextVal = GetNextVal(_db, idComune, seqName.ToUpper() );

                                if (properties[i].PropertyType.IsGenericType && properties[i].PropertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
                                {
                                    if (nextVal.GetType() != properties[i].PropertyType.GetGenericArguments()[0])
                                    {
                                        properties[i].SetValue(_pclass, Convert.ChangeType(nextVal, properties[i].PropertyType.GetGenericArguments()[0]), null);
                                    }
                                    else
                                    {
                                        properties[i].SetValue(_pclass, nextVal, null);
                                    }
                                }
                                else
                                {
                                    properties[i].SetValue(_pclass, Convert.ChangeType(nextVal, properties[i].PropertyType), null);
                                }

							}
						}


						//c'è un' attributo di tipo isrequired
						if ( fields.Length > 0  )
						{
							object obj = properties[i].GetValue( _pclass,null );

                            if (VerificaSeValoreNull(obj, properties[i]))
							{
								if (String.IsNullOrEmpty(fields[0].SOFTWARE))
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

                                        if (VerificaSeValoreNull(fldSoftware, properties[SoftwarePropertyIndex]))
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
								if (  VerificaSeValoreNull( obj , properties[i] ) )
								{
									retVal = false;
									throw( new RequiredFieldException("Il campo chiave " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio")  );
								}
							}
						}

						if ( retVal == false ) 
						{
                            if ( String.IsNullOrEmpty( fields[0].MSG ) )
								throw( new RequiredFieldException("Il campo " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio")  );
							else
								throw( new RequiredFieldException( fields[0].MSG ) );
						}
					}
				}
			}
	
			return retVal;
		}

		private PropertyInfo EstraiPropertyIdComune(Type objType)
		{
			PropertyInfo[] pi = objType.GetProperties();

			for (int i = 0; i < pi.Length; i++)
			{
				if (pi[i].Name.ToLower() == "idcomune")
					return pi[i];
			}

			return null;
		}
		public bool RequiredFieldValidator(AmbitoValidazione ambitoValidazione)
		{
			return RequiredFieldValidator( null , ambitoValidazione);
		}


		private bool VerificaSeValoreNull(object propVal, PropertyInfo prop)
		{
			if (propVal == null) return true;

            if (prop.PropertyType == typeof(int) || prop.PropertyType == typeof(Nullable<int>))
                return (Convert.ToInt32(propVal) == int.MinValue);

			if (prop.PropertyType == typeof(float) || prop.PropertyType == typeof(Nullable<float>))
                return (Convert.ToSingle(propVal) == float.MinValue);

			if (prop.PropertyType == typeof(double) || prop.PropertyType == typeof(Nullable<double>))
				return (Convert.ToDouble(propVal) < -1E37);

			if (prop.PropertyType == typeof(DateTime) || prop.PropertyType == typeof(Nullable<DateTime>))
                return (Convert.ToDateTime(propVal) == DateTime.MinValue );

            if (prop.PropertyType == typeof(string))
                return String.IsNullOrEmpty(propVal.ToString());

			return StringChecker.IsObjectEmpty( propVal );
		}

		/// <summary>
		/// La funzione ritorna il prossimo valore di una sequenza della tabella SEQUENCETABLE
		/// </summary>
		/// <param name="db">PersonalLib2.Data.DataBase con la connessione attiva</param>
		/// <param name="tableName">Tabella che contiene la sequenza da leggere</param>
		/// <param name="fieldName">Nome del campo con il valore attuale della sequenza</param>
		/// <param name="idComune">Idcomune</param>
		/// <param name="sequenceName">Nome della sequenza da leggere</param>
		/// <returns></returns>
		public int GetNextVal( DataBase db, string idComune, string sequenceName )
		{
            Sequence seq = new Sequence();

			seq.Db = db;
            seq.IdComune = idComune;
            seq.SequenceName = sequenceName;
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
