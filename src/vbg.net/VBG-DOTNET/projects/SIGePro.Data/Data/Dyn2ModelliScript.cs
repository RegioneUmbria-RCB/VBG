
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Text;
using VBG.DatiDinamici.Interfaces;


namespace Init.SIGePro.Data
{
    [DataTable("DYN2_MODELLI_SCRIPT")]
    [Serializable]
    [DataContract]
    public partial class Dyn2ModelliScript : BaseDataClass, IDyn2ScriptModello
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        public string IdComune { get; set; } = "";

        [KeyField("FK_D2MT_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2mtId { get; set; }

        [KeyField("EVENTO", Type = DbType.String, Size = 15)]
        [DataMember]
        public string Evento { get; set; } = "";

        [DataField("SCRIPT", Type = DbType.Binary)]
        [DataMember]
        public byte[]? Script { get; set; } = null;


        [DataField("CHECKSUM", Type = DbType.String, Size = 128)]
        [DataMember]
        public string Checksum { get; set; } = "";

        public string GetTestoScript()
        {
            if (this.Script == null || this.Script.Length == 0) return String.Empty;
            return Encoding.UTF8.GetString(this.Script);
        }

        public void SetTestoScript(string script)
        {
            this.Script = Encoding.UTF8.GetBytes(script);
        }
    }
}
