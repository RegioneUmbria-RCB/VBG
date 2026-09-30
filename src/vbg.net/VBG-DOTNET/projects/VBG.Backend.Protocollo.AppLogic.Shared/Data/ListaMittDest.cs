namespace VBG.Backend.Protocollo.AppLogic.Shared.Data
{
    public class ListaMittDest
    {
        public List<ProtocolloAnagrafe> Anagrafe { get; set; }
        public List<ProtocolloAmministrazioni> Amministrazione { get; set; }

        public List<String> PecPresenti()
        {
            var retVal = new List<String>();
            if (this.Anagrafe?.Any(x => !String.IsNullOrEmpty(x.PecProtocollazione)) == true)
            {
                retVal.AddRange(this.Anagrafe.Where(x => !String.IsNullOrEmpty(x.PecProtocollazione)).Select(x => x.PecProtocollazione));
            }
            if (this.Amministrazione?.Any(x => !String.IsNullOrEmpty(x.PEC)) == true)
            {
                retVal.AddRange(this.Amministrazione.Where(x => !String.IsNullOrEmpty(x.PEC)).Select(x => x.PEC));
            }

            return retVal;
        }
    }
}
