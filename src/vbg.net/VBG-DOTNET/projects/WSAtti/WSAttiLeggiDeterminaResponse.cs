using System;
using System.Collections.Generic;

namespace WSAtti
{
    public class WSAttiLeggiDeterminaResponse
    {
        public WSEsito Esito { get; internal set; }
        public int Id { get; internal set; }
        public string CodiceClassifica { get; internal set; }
        public string Classifica { get; internal set; }
        public string NumeroProposta { get; internal set; }
        public int AnnoProposta { get; internal set; }
        public string UfficioProponente { get; internal set; }
        public string StrutturaProponente { get; internal set; }
        public string Dirigente { get; internal set; }
        public string Oggetto { get; internal set; }
        public string NumeroAtto { get; internal set; }
        public DateTime? DataAtto { get; internal set; }
        public int AnnoAtto { get; internal set; }
        public IEnumerable<WSAllegatoAtto> Allegati { get; internal set; }
    }
}
