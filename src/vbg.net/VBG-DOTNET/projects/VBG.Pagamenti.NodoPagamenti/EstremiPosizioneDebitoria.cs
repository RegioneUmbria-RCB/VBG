namespace VBG.Pagamenti.NodoPagamenti
{
    public class EstremiPosizioneDebitoriaClient : IEstremiPosizioneDebitoriaClient
    {
        public string IdPosizioneDebitoria { get; }
        public string RiferimentoClient { get; }
        public string IUV { get; }
        public string CodiceComune { get; }

        public EstremiPosizioneDebitoriaClient(string codiceComune, string uniqueId, string idPosizioneDebitoria, string iuv)
        {
            this.RiferimentoClient = uniqueId;
            this.IdPosizioneDebitoria = idPosizioneDebitoria;
            this.IUV = iuv;
            this.CodiceComune = codiceComune;
        }

        private EstremiPosizioneDebitoriaClient()
        {

        }

        public RiferimentoPosizioneDebitoriaType ToRiferimentoPosizioneDebitoriaType()
        {
            return new RiferimentoPosizioneDebitoriaType
            {
                IUV = this.IUV,
                idPosizione = this.IdPosizioneDebitoria,
                riferimentoClient = new[] { this.RiferimentoClient }
            };
        }
    }


    public class EstremiPosizioneDebitoriaServer : IEstremiPosizioneDebitoriaServer
    {
        public string IdPosizioneDebitoria { get; }
        public string RiferimentoClient { get; }
        public string IUV { get; }
        public string UuidNodoPagamenti { get; }
        public string CodiceComune { get; }

        public EstremiPosizioneDebitoriaServer(string codiceComune, string uniqueId, string idPosizioneDebitoria, string iuv, string uuid)
        {
            this.RiferimentoClient = uniqueId;
            this.IdPosizioneDebitoria = idPosizioneDebitoria;
            this.IUV = iuv;
            this.UuidNodoPagamenti = uuid;
            this.CodiceComune = codiceComune;
        }

        private EstremiPosizioneDebitoriaServer()
        {

        }

    }
}
