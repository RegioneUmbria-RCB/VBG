
using PersonalLib2.Sql.Attributes;
using VBG.DatiDinamici.Interfaces;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    public partial class AnagrafeDyn2Dati : IValoreCampo
    {
        [DataMember]
        [ForeignKey("Idcomune,FkD2cId", "Idcomune,Id")]
        public Dyn2Campi CampoDinamico { get; set; }
    }
}