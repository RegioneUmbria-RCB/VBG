namespace VBG.Pagamenti.NodoPagamenti.Attivazione
{
    public class HttpPostParameter
    {
        public HttpPostParameter(string key, string value)
        {
            this.Key = key;
            this.Value = value;
        }

        public string Key { get; }
        public string Value { get; }
    }
}
