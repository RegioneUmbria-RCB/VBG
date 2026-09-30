using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Pal.Protocollazione
{
    public class Ente
    {
        public string modelKey { get; set; }
        public int id { get; set; }
        public string descrizione { get; set; }
        public string codiceComune { get; set; }
    }

    public class Protocollatore
    {
        public string modelKey { get; set; }
        public int id { get; set; }
        public string username { get; set; }
    }

    public class RaccordoAllegati
    {
        public int id { get; set; }
        public string nomeFile { get; set; }
    }

    public class RootObject
    {
        public string abbreviateTipo { get; set; }
        public int annoProtocollo { get; set; }
        public string codiceRegistro { get; set; }
        public string dataProtocollo { get; set; }
        public string destinatario { get; set; }
        public Ente ente { get; set; }
        public int id { get; set; }
        public string mittente { get; set; }
        public int numeroProtocollo { get; set; }
        public string oggetto { get; set; }
        public Protocollatore protocollatore { get; set; }
        public List<RaccordoAllegati> raccordoAllegati { get; set; }
    }
}
