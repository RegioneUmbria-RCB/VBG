namespace VBG.Pagamenti.NodoPagamenti
{
    public class AvvisoDiPagamento
    {
        public enum StatoAvvisoEnum
        {
            RICHIESTO,
            DISPONIBILE,
            NON_DISPONIBILE,
        }

        public StatoAvvisoEnum Stato { get; internal set; }
        public byte[] Dati { get; internal set; }
        public string Descrizione { get; internal set; }
        public string NomeFile { get; internal set; }
    }
}
