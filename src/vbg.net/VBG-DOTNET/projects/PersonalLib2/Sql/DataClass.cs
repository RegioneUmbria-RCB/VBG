using PersonalLib2.Sql.Attributes;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Runtime.Serialization;
using System.Text.Json.Serialization;
using System.Xml.Serialization;

namespace PersonalLib2.Sql
{
    public enum useForeignEnum { No, Yes, Recoursive };
    /// <summary>
    /// Descrizione di riepilogo per GenericData.
    /// </summary>
    [Serializable]
    [DataContract]
    public class DataClass
    {
        private string _dataTableName = null;

        #region Properties
        [SoapIgnore]
        [JsonIgnore]
        [XmlIgnore]
        [IgnoreDataMember]
        public useForeignEnum UseForeign { get; set; } = useForeignEnum.No;

        [SoapIgnore]
        [XmlIgnore]
        [JsonIgnore]
        [IgnoreDataMember]
        public List<string> OthersTables { get; set; } = new List<string>();

        [SoapIgnore]
        [XmlIgnore]
        [JsonIgnore]
        [IgnoreDataMember]
        public List<string> OthersJoinClause { get; set; } = new List<string>();

        [SoapIgnore]
        [XmlIgnore]
        [JsonIgnore]
        [IgnoreDataMember]
        public List<string> OthersWhereClause { get; set; } = new List<string>();

        [SoapIgnore]
        [XmlIgnore]
        [JsonIgnore]
        [IgnoreDataMember]
        public List<string> OthersSelectColumns { get; set; } = new List<string>();

        [SoapIgnore]
        [XmlIgnore]
        [JsonIgnore]
        [IgnoreDataMember]
        public string SelectColumns { get; set; } = "";

        [SoapIgnore]
        [XmlIgnore]
        [JsonIgnore]
        [IgnoreDataMember]
        public string OrderBy { get; set; } = "";

        /// <summary>
        /// Ritorna il nome a cui mappa la classe
        /// </summary>
        [SoapIgnore]
        [XmlIgnore]
        [JsonIgnore]
        [IgnoreDataMember]
        public string DataTableName
        {
            get
            {
                if (this._dataTableName == null)
                {

                    var dataTables = (DataTableAttribute[])this.GetType().GetCustomAttributes(typeof(DataTableAttribute), true);

                    // Nessuna DataTable
                    if (dataTables == null || dataTables.Length != 1)
                        this._dataTableName = String.Empty;
                    else
                        this._dataTableName = dataTables[0].TableName;
                }

                return this._dataTableName;
            }
        }

        #endregion

        #region Clone function

        /// <summary>
        /// Crea un oggetto dello stesso tipo di quello passato ed imposta 
        /// le proprietà del nuovo oggetto = a quelle di quello passato.
        /// </summary>
        /// <returns>-
        /// Un oggetto dello stesso tipo di quello passato.
        /// Con le proprietà impostate al valore dell'oggetto passato.</returns>
        public object Clone()
        {
            var objType = this.GetType();
            var newClass = Activator.CreateInstance(objType);

            foreach (var property in objType.GetProperties().Where(x => x.GetSetMethod() != null))
            {
                var propValue = property.GetValue(this, null);
                var propType = property.PropertyType;

                if (propType == typeof(ArrayList))
                {
                    // TODO: sarebbe meglio invocare il metodo clone senza specificare
                    //		 Personal.Collections.ArrayList sia sul confronto precedente
                    //		 che nell'assegnazione successiva.
                    var myarO = (ArrayList)propValue;
                    var myar = new ArrayList();

                    myar.AddRange(myarO.ToArray());

                    property.SetValue(newClass, myar, null);

                    continue;
                }

                if (IsGenericList(property) && propType.GetGenericArguments()[0].IsSubclassOf(typeof(DataClass)))
                {
                    //è una lista di dataclass
                    var srcList = (IList)propValue;
                    var newList = (IList)Activator.CreateInstance(propType);

                    for (var i = 0; i < srcList.Count; i++)
                    {
                        if (srcList[i] is DataClass @class)
                        {
                            newList.Add(@class.Clone());
                        }
                    }
                    property.SetValue(newClass, newList, null);

                    continue;
                }

                property.SetValue(newClass, propValue, null);
            }

            return newClass;
        }

        private static bool IsGenericList(PropertyInfo property)
        {
            return property.PropertyType.IsGenericType && (property.PropertyType.GetGenericTypeDefinition() == typeof(List<>));
        }

        #endregion
    }
}