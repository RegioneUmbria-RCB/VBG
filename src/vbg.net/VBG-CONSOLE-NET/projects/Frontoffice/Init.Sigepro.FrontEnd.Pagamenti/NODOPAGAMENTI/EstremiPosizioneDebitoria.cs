namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public class EstremiPosizioneDebitoria : IEstremiPosizioneDebitoria
    {
        public string IdPosizioneDebitoria { get; }

        public string UniqueId { get; }

        public string IUV { get; }

        public EstremiPosizioneDebitoria(string uniqueId, string idPosizioneDebitoria, string iuv)
        {
            this.UniqueId = uniqueId;
            this.IdPosizioneDebitoria = idPosizioneDebitoria;
            this.IUV = iuv;
        }

        private EstremiPosizioneDebitoria()
        {

        }

        public RiferimentoPosizioneDebitoriaType ToRiferimentoPosizioneDebitoriaType()
        {
            return new RiferimentoPosizioneDebitoriaType
            {
                IUV = this.IUV,
                idPosizione = this.IdPosizioneDebitoria,
                riferimentoClient = new[] { this.UniqueId }
            };
        }
    }
}
