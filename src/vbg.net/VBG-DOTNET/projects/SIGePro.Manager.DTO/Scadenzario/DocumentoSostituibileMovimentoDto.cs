using System.Runtime.Serialization;

namespace Init.SIGePro.Manager.DTO.Scadenzario
{
    [DataContract]
    public class DocumentoSostituibileMovimentoDto
    {
        public enum OrigineDocumentoEnum
        {
            Intervento = 0,
            Endoprocedimento = 1
        }

        [DataMember]
        public OrigineDocumentoEnum Origine { get; set; }

        [DataMember]
        public int IdDocumento { get; set; }

        [DataMember]
        public int? CodiceOggetto { get; set; }

        [DataMember]
        public string Descrizione { get; set; }

        [DataMember]
        public string NomeFile { get; set; }

    }
}