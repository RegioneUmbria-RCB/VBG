namespace WSAtti
{
    public class WSAttiConfigurazione
    {
        public string BindingName => "defaultHttpBinding";
        public string TipoConnettore { get; set; }
        public string Utente { get; set; }
        public string Ruolo { get; set; }
        public string CodiceAmministrazione { get; set; }
        public string Url { get; set; }
        public string UrlFirmatari { get; set; }
    }
}
