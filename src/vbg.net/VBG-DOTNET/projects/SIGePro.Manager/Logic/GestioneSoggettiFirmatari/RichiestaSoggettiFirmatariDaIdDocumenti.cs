using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneSoggettiFirmatari
{
    [DataContract]
    public class RichiestaSoggettiFirmatariDaIdDocumenti
    {
        [DataMember(Order = 0)]
        public int[] IdDocumentiIntervento { get; set; }

        [DataMember(Order = 1)]
        public int[] IdDocumentiEndo { get; set; }
    }
}
