using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.FileEncoding
{
	public class UnknownEncodingToString
	{
		public static string Convert(byte[] buffer)
		{
			return new UnknownEncodingToString(buffer).Convert();
		}

		byte[] _buffer;
		Encoding _encoding;

		private UnknownEncodingToString(byte[] buffer)
		{
			_buffer = buffer;
			_encoding = ResolveEncoding();
		}

		private Encoding ResolveEncoding()
		{
			Encoding rVal = Encoding.GetEncoding(1252);	// Ansi
			var tmpBuffer = this._buffer;

			// UTF8 ?
			if (this._buffer[0] == 239 &&
				this._buffer[1] == 187 &&
				this._buffer[2] == 191)
			{
				tmpBuffer = new Byte[this._buffer.Length - 3];

				Array.Copy(this._buffer, 3, tmpBuffer, 0, this._buffer.Length - 3);

				this._buffer = tmpBuffer;

				return Encoding.UTF8;
			}

			return rVal;
		}

		private string Convert()
		{
			return _encoding.GetString(_buffer);
		}
	}
}
