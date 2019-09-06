using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using PersonalLib2.Sql;

namespace PersonalLib2.Data
{
	public enum TipoOperazioneDb
	{
		Insert,
		Update,
		Delete
	}

	public partial class DbEventArgs
	{
		DataClass m_dataClass;
		IDbCommand m_command;
		TipoOperazioneDb m_tipoOperazione;

		public TipoOperazioneDb TipoOperazione
		{
			get { return m_tipoOperazione; }
			private set { m_tipoOperazione = value; }
		}

		public IDbCommand Command
		{
			get { return m_command; }
			private set { m_command = value; }
		}


		public DataClass DataClass
		{
			get { return m_dataClass; }
			private set { m_dataClass = value; }
		}

		internal DbEventArgs(TipoOperazioneDb tipoOperazione, DataClass dataClass, IDbCommand command)
		{
			TipoOperazione = tipoOperazione;
			DataClass = dataClass;
			Command = command;
		}

		public DbEventArgs()
		{
		}
	}
}
