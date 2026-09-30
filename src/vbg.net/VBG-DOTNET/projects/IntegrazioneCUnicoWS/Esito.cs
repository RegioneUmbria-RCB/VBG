namespace IntegrazioneCUnicoWS
{
    public class Esito
    {
        public bool Ok { get; internal set; }
        public string Codice { get; internal set; } 
        public string Messaggio { get; internal set; }
        public string XMLRichiesta { get; set; }
        public string XMLRisposta { get; set; }
    }
}