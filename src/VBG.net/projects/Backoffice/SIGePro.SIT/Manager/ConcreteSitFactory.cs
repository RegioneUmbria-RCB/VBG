using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using log4net;
using System.Reflection;
using CuttingEdge.Conditions;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Sit.Manager
{

	internal class ConcreteSitFactory
	{
		ILog _log = LogManager.GetLogger(typeof(ConcreteSitFactory));

		public enum TipiSitEnum
		{
			DEFAULT,
			NESSUNO,
			SIT_ESC,
			SIT_CORE,
			SIT_NAUTILUS,
			SIT_CTC,
			SIT_DEFAULT,
			SIT_INITMAPGUIDE,
			SIT_QUAESTIOFLORENZIA,
			SIT_7DBTL,
			SIT_MODENA,
			SIT_SILVERBROWSER,
			SIT_RAVENNA2
		}

		string _idComune;
        string _idComuneAlias;
		string _software;
		DataBase _database;
		private ISitApi _istanzaClasseSit;
		public ConcreteSitFactory(string idComune, string idComuneAlias, string software, DataBase database)
		{
			Condition.Requires(idComune, "idComune").IsNotNullOrEmpty();
            Condition.Requires(idComuneAlias, "idComuneAlias").IsNotNullOrEmpty();
			Condition.Requires(software, "software").IsNotNullOrEmpty();
			Condition.Requires(database, "database").IsNotNull();

			this._idComune = idComune;
            this._idComuneAlias = idComuneAlias;
			this._software = software;
			this._database = database;
			this._istanzaClasseSit = null;
		}

		public ISitApi GetSitAttivo()
		{
			if (this._istanzaClasseSit == null)
				this._istanzaClasseSit = Create(GetTipoSitAttivo());

			return this._istanzaClasseSit;
		}

		private ISitApi Create(TipiSitEnum tipoSit)
		{
			var assName = Assembly.GetExecutingAssembly();

			_log.DebugFormat("Caricamento via reflection del sitconnector {0}", tipoSit.ToString());

			if (tipoSit == TipiSitEnum.NESSUNO)
			{
				_log.Error("Nell'installazione corrente non sono presenti sit attivi");
				throw new Exception("La verticalizzazione SIT_ATTIVO non è attiva. Software " + this._software + "\r\n");
			}

			Type classType = assName.GetType("Init.SIGePro.Sit." + tipoSit.ToString());

			if (classType == null)
				throw new Exception("Il tipo di SIT " + tipoSit.ToString() + " non è un sit valido");

			var sitConnector = (ISitApi)Activator.CreateInstance(classType);

			sitConnector.InizializzaParametriSigepro(this._idComune, this._idComuneAlias, this._software, this._database);

			sitConnector.SetupVerticalizzazione();

			return sitConnector;
		}

		private TipiSitEnum GetTipoSitAttivo()
		{
			try
			{
				var verticalizzazioneSit = new VerticalizzazioneSitAttivo(this._idComuneAlias, this._software);

				_log.DebugFormat("GetTipoSitAttivo, verticalizzazione caricata con successo, stato attiva: {0}, tipo sit: {1}", verticalizzazioneSit.Attiva, verticalizzazioneSit.Tiposit);

				if (verticalizzazioneSit.Attiva)
					return (TipiSitEnum)Enum.Parse(typeof(TipiSitEnum), verticalizzazioneSit.Tiposit, false);

				return TipiSitEnum.NESSUNO;
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in GetTipoSitAttivo: {0}", ex.ToString());

				throw;
			}
		}
	}
}
