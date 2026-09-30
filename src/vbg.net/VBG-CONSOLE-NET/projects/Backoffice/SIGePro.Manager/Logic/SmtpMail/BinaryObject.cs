using System;
using System.Collections.Generic;
using System.Text;
using System.Net.Mail;
using Init.Utils;

namespace Init.SIGePro.Manager.Logic.SmtpMail
{
	public class BinaryObject
	{
		public string FileName{get;set;}
		public byte[] FileContent{get;set;}

		internal Attachment ToAttachment()
		{
			return new Attachment(StreamUtils.FileToStream(FileContent), FileName);
		}
	}
}
