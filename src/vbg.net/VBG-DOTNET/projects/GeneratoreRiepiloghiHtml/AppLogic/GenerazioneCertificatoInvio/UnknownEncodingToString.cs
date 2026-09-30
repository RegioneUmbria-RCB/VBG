using System.Text;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneCertificatoInvio
{
    public class UnknownEncodingToString
    {
        public static string GetString(byte[] buffer)
        {
            return new UnknownEncodingToString(buffer).Convert();
        }

        private byte[] _buffer;
        private readonly Encoding _encoding;

        private UnknownEncodingToString(byte[] buffer)
        {
            this._buffer = buffer;
            this._encoding = this.ResolveEncoding();
        }

        private Encoding ResolveEncoding()
        {
            Encoding rVal = CodePagesEncodingProvider.Instance.GetEncoding(1252);

            // UTF8 ?
            if (this._buffer.Length > 3 &&
                this._buffer[0] == 239 &&
                this._buffer[1] == 187 &&
                this._buffer[2] == 191)
            {
                this._buffer = this._buffer.Skip(3).ToArray();

                return Encoding.UTF8;
            }

            return rVal;
        }

        private string Convert()
        {
            return this._encoding.GetString(this._buffer);
        }
    }
}
