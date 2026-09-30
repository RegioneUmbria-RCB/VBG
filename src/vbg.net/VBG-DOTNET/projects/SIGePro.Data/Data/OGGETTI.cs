using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.IO;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("OGGETTI")]
    [Serializable]
    [DataContract]
    public class Oggetti : BaseDataClass
    {

        #region Key Fields
        private string codiceoggetto = null;
        [useSequence]
        [KeyField("CODICEOGGETTO", Type = DbType.Int16)]
        [DataMember]
        [XmlElement(Order = 0)]
        public string CODICEOGGETTO
        {
            get { return this.codiceoggetto; }
            set { this.codiceoggetto = value; }
        }

        private string idcomune = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 1)]
        public string IDCOMUNE
        {
            get { return this.idcomune; }
            set { this.idcomune = value; }
        }

        #endregion

        private byte[] oggetto = null;
        //TODO: Commentato per ravenna
        //[isRequired]
        // [DataField("OGGETTO", Type=DbType.Binary)]
        [DataMember]
        [XmlElement(Order = 2)]
        public byte[] OGGETTO
        {
            get { return this.oggetto; }
            set { this.oggetto = value; }
        }

        private string nomefile = null;
        [isRequired]
        [DataField("NOMEFILE", Size = 128, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        [XmlElement(Order = 3)]
        public string NOMEFILE
        {
            get { return this.nomefile; }
            set { this.nomefile = value; }
        }

        public T DeserializeXML<T>()
        {
            var serial = new XmlSerializer(typeof(T));

            using (var reader = new MemoryStream(this.OGGETTO))
            {
                return (T)serial.Deserialize(reader);
            }
        }
    }
}