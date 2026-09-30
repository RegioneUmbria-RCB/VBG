namespace WSAtti
{
    public class WSEsito
    {
        public bool OK { get; internal set; }
        public string Messaggio { get; internal set; }

        public static WSEsito FromOK()
        {
            return new WSEsito
            {
                OK = true,
                Messaggio = ""
            };
        }

        public static WSEsito FromKO(string messaggio)
        {
            return new WSEsito
            {
                OK = false,
                Messaggio = messaggio
            };
        }
    }
}
