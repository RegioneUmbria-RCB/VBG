using System;
using System.Collections.Generic;
using System.Text;
using System.Net.Mail;

namespace Init.SIGePro.Manager.Logic.SmtpMail
{
	public class SIGeProMailMessage
	{
		public bool InviaComeHtml;
		public string Mittente;
		public string CorpoMail;
		public string Oggetto;
		public string Destinatari;
		public string DestinatariInCopia;
		public string DestinatariInCopiaNascosta;

		public BinaryObject[] Attachments;

		private List<MailAddress> GetListaIndirizzi(string listaIndirizzi)
		{
			List<MailAddress> lista = new List<MailAddress>();

			if (string.IsNullOrEmpty(listaIndirizzi)) return lista;

			string[] elementi = listaIndirizzi.Split(';');

			for (int i = 0; i < elementi.Length; i++)
			{
				try
				{
					if (!string.IsNullOrEmpty(elementi[i]))
						lista.Add(new MailAddress(elementi[i]));
				}
				catch (Exception ex)
				{
					throw new InvalidRecipientException(elementi[i]);
				}
			}

			return lista;
		}

		internal List<MailAddress> GetDestinatari()
		{
			return GetListaIndirizzi(Destinatari);
		}

		internal List<MailAddress> GetDestinatariInCopia()
		{
			return GetListaIndirizzi(DestinatariInCopia);
		}

		internal List<MailAddress> GetDestinatariInCopiaNascosta()
		{
			return GetListaIndirizzi(DestinatariInCopiaNascosta);
		}

		internal List<BinaryObject> GetAttachments()
		{
			List<BinaryObject> tmp = new List<BinaryObject>();

			if (Attachments == null || Attachments.Length == 0) return tmp;

			for (int i = 0; i < Attachments.Length; i++)
			{
				if (String.IsNullOrEmpty(Attachments[i].FileName))
					throw new NoFileNameSpecifiedException(i);

				tmp.Add(Attachments[i]);
			}

			return tmp;
		}
	}
}
