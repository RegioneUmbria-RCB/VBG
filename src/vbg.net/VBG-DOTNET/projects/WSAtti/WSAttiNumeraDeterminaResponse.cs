using System;

namespace WSAtti
{
    public class WSAttiNumeraDeterminaResponse
    {
        public WSEsito Esito { get; internal set; }
        public int? Id { get; internal set; }
        public int? Numero { get; internal set; }
        public int? Anno { get; internal set; }
        public DateTime? Data { get; internal set; }
    }
}