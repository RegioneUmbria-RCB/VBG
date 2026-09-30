namespace VBG.Pagamenti.NodoPagamenti
{
    public class RicevutaTelematica
    {
        public enum StatoRicevutaEnum
        {
            RICHIESTO,
            DISPONIBILE,
            NON_DISPONIBILE,
        }

        public StatoRicevutaEnum Stato { get; internal set; }
        public byte[] Dati { get; internal set; }
        public string Descrizione { get; internal set; }
    }
}
