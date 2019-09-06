using System.Runtime.Serialization;

namespace Init.Sigepro.FrontEnd.Pagamenti.ESED
{
    [DataContract(Namespace = "", Name = "CommitMsg")]
    public class ESEDCommitMessage
    {
        [DataMember]
        public string PortaleID { get; set; }

        [DataMember]
        public string NumeroOperazione { get; set; }

        [DataMember]
        public string IDOrdine { get; set; }

        [DataMember]
        public string Commit { get; set; }


        public ESEDCommitMessage()
        {

        }
    }
}