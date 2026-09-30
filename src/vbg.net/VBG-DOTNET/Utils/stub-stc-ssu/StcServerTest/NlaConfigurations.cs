namespace StcServerTest
{
    public class NlaConfigurations
    {
        public string Endpoint { get; set; } = "";
        public Sportello SportelloMittente { get; set; } = new Sportello();
        public Sportello SportelloDestinatario { get; set; } = new Sportello();
    }

    public class Sportello
    {
        public string IdEnte { get; set; } = "";
        public string IdNodo { get; set; } = "";
        public string IdSportello { get; set; } = "";
    }
}
