using Init.SIGePro.Attributes;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Utils;
using Init.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System;
using System.Reflection;

namespace Init.SIGePro.Manager.Validator
{
    public enum AmbitoValidazione
    {
        Insert,
        Update
    }

    public class ClassValidator
    {
        public static bool Validate(DataBase db, DataClass @class, AmbitoValidazione ambitoValidazione)
        {
            var validator = new ClassValidator(@class);
            return validator.RequiredFieldValidator(db, ambitoValidazione);
        }

        protected DataClass _pclass;

        public ClassValidator(DataClass p_class)
        {
            this._pclass = p_class;
        }

        public bool RequiredFieldValidator(DataBase _db, AmbitoValidazione ambitoValidazione)
        {
            var retVal = true;

            var objType = this._pclass.GetType();
            var dataTables = (DataTableAttribute[])objType.GetCustomAttributes(typeof(DataTableAttribute), true);

            if (dataTables.Length > 0)
            {
                var properties = objType.GetProperties(BindingFlags.Public | BindingFlags.Instance);
                for (var i = 0; i < properties.Length; i++)
                {

                    var seqfields = (useSequenceAttribute[])properties[i].GetCustomAttributes(typeof(useSequenceAttribute), true);
                    var fields = (isRequiredAttribute[])properties[i].GetCustomAttributes(typeof(isRequiredAttribute), true);
                    var basefields = (BaseFieldAttribute[])properties[i].GetCustomAttributes(typeof(BaseFieldAttribute), true);

                    if (basefields.Length > 0)
                    {

                        //c'è un attributo useSequence
                        if (seqfields.Length > 0)
                        {
                            var propIdComune = this.EstraiPropertyIdComune(objType);

                            // Se sto effettuando una insert e la classe richiede l'utilizzo di una sequenza stacco il valore della sequenza
                            if (ambitoValidazione == AmbitoValidazione.Insert)
                            {
                                var propVal = properties[i].GetValue(this._pclass, null);

                                if (!this.VerificaSeValoreNull(propVal, properties[i]))
                                {
                                    throw new InvalidOperationException("Errore durante l'estrazione del valore di sequenza per la proprietà " + properties[i].Name + " della classe " + objType.Name + " durante l'inserimento: la proprietà è già valorizzata");
                                }

                                var seqName = this._pclass.DataTableName + "." + properties[i].Name;
                                var idComune = propIdComune.GetValue(this._pclass, null).ToString();

                                var nextVal = this.GetNextVal(_db, idComune, seqName.ToUpper());

                                if (properties[i].PropertyType.IsGenericType && properties[i].PropertyType.GetGenericTypeDefinition() == typeof(Nullable<>))
                                {
                                    if (nextVal.GetType() != properties[i].PropertyType.GetGenericArguments()[0])
                                    {
                                        properties[i].SetValue(this._pclass, Convert.ChangeType(nextVal, properties[i].PropertyType.GetGenericArguments()[0]), null);
                                    }
                                    else
                                    {
                                        properties[i].SetValue(this._pclass, nextVal, null);
                                    }
                                }
                                else
                                {
                                    properties[i].SetValue(this._pclass, Convert.ChangeType(nextVal, properties[i].PropertyType), null);
                                }

                            }
                        }


                        //c'è un' attributo di tipo isrequired
                        if (fields.Length > 0)
                        {
                            var obj = properties[i].GetValue(this._pclass, null);

                            if (this.VerificaSeValoreNull(obj, properties[i]))
                            {
                                if (String.IsNullOrEmpty(fields[0].SOFTWARE))
                                {
                                    retVal = false;
                                }
                                else    //il campo è obbligatorio per un determinato software
                                {
                                    var SoftwarePropertyIndex = this.GetPropertyIndex(properties, "SOFTWARE");

                                    if (SoftwarePropertyIndex == -1)
                                    {
                                        retVal = false;
                                        fields[0].MSG = "Il campo " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio per un determinato software ma la classe non ha il campo software";
                                    }
                                    else
                                    {
                                        var fldSoftware = properties[SoftwarePropertyIndex].GetValue(this._pclass, null);

                                        if (this.VerificaSeValoreNull(fldSoftware, properties[SoftwarePropertyIndex]))
                                        {
                                            if (fields[0].SOFTWARE.IndexOf(fldSoftware.ToString()) > -1)
                                            {
                                                retVal = false;
                                                fields[0].MSG = "Il campo " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio per il software " + fields[0].SOFTWARE;
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        else if (basefields[0].GetType() == typeof(KeyFieldAttribute))
                        {
                            if (!(basefields[0] as KeyFieldAttribute).KeyIdentity)
                            {
                                var obj = properties[i].GetValue(this._pclass, null);
                                if (this.VerificaSeValoreNull(obj, properties[i]))
                                {
                                    retVal = false;
                                    throw (new RequiredFieldException("Il campo chiave " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio"));
                                }
                            }
                        }

                        if (retVal == false)
                        {
                            if (String.IsNullOrEmpty(fields[0].MSG))
                                throw (new RequiredFieldException("Il campo " + dataTables[0].TableName + "." + basefields[0].ColumnName + " è obbligatorio"));
                            else
                                throw (new RequiredFieldException(fields[0].MSG));
                        }
                    }
                }
            }

            return retVal;
        }

        private PropertyInfo EstraiPropertyIdComune(Type objType)
        {
            var pi = objType.GetProperties();

            for (var i = 0; i < pi.Length; i++)
            {
                if (pi[i].Name.ToLower() == "idcomune")
                    return pi[i];
            }

            return null;
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
                return (Convert.ToDateTime(propVal) == DateTime.MinValue);

            if (prop.PropertyType == typeof(string))
                return String.IsNullOrEmpty(propVal.ToString());

            return StringChecker.IsObjectEmpty(propVal);
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
        public int GetNextVal(DataBase db, string idComune, string sequenceName)
        {
            var seq = new Sequence();

            seq.Db = db;
            seq.IdComune = idComune;
            seq.SequenceName = sequenceName;
            return seq.NextVal();
        }


        protected int GetPropertyIndex(PropertyInfo[] properties, string PropertyName)
        {
            var SoftwarePropertyIndex = -1;
            var Counter = 0;

            foreach (var fldProperty in properties)
            {
                if (fldProperty.Name.ToUpper() == PropertyName)
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
