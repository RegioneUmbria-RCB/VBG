using Init.SIGePro.Sit.Forli;
using Init.SIGePro.Verticalizzazioni;
using log4net;
using PersonalLib2.Data;
using System;
using System.Reflection;

namespace Init.SIGePro.Sit.Manager
{

    internal class ConcreteSitFactory
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ConcreteSitFactory));

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
            SIT_RAVENNA2,
            SIT_LDP,
            SIT_PISTOIA,
            SIT_JESI,
            SIT_ITCITY,
            SIT_FORLI
        }

        private readonly string _idComune;
        private readonly string _idComuneAlias;
        private readonly string _software;
        private readonly DataBase _database;
        private ISitApi _istanzaClasseSit;
        public ConcreteSitFactory(string idComune, string idComuneAlias, string software, DataBase database)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new ArgumentException($"'{nameof(idComune)}' cannot be null or empty.", nameof(idComune));
            }

            if (string.IsNullOrEmpty(idComuneAlias))
            {
                throw new ArgumentException($"'{nameof(idComuneAlias)}' cannot be null or empty.", nameof(idComuneAlias));
            }

            if (string.IsNullOrEmpty(software))
            {
                throw new ArgumentException($"'{nameof(software)}' cannot be null or empty.", nameof(software));
            }

            this._idComune = idComune;
            this._idComuneAlias = idComuneAlias;
            this._software = software;
            this._database = database ?? throw new ArgumentNullException(nameof(database));
            this._istanzaClasseSit = null;
        }

        public ISitApi GetSitAttivo()
        {
            if (this._istanzaClasseSit == null)
                this._istanzaClasseSit = this.Create(this.GetTipoSitAttivo());

            return this._istanzaClasseSit;
        }

        private ISitApi Create(TipiSitEnum tipoSit)
        {
            var assName = Assembly.GetExecutingAssembly();

            this._log.DebugFormat("Caricamento via reflection del sitconnector {0}", tipoSit.ToString());

            if (tipoSit == TipiSitEnum.NESSUNO)
            {
                this._log.Error("Nell'installazione corrente non sono presenti sit attivi");
                throw new Exception("La verticalizzazione SIT_ATTIVO non è attiva. Software " + this._software + "\r\n");
            }

            ISitApi sitConnector;

            if (tipoSit == TipiSitEnum.SIT_FORLI)
            {
                sitConnector = new SitForli();
            }
            else
            {
                Type classType = assName.GetType("Init.SIGePro.Sit." + tipoSit.ToString());

                if (classType == null)
                    throw new Exception("Il tipo di SIT " + tipoSit.ToString() + " non è un sit valido");

                sitConnector = (ISitApi)Activator.CreateInstance(classType);
            }

            sitConnector.InizializzaParametriSigepro(this._idComune, this._idComuneAlias, this._software, this._database);

            sitConnector.SetupVerticalizzazione();

            return sitConnector;
        }

        private TipiSitEnum GetTipoSitAttivo()
        {
            try
            {
                var verticalizzazioneSit = new VerticalizzazioneSitAttivo(this._idComuneAlias, this._software);

                this._log.DebugFormat("GetTipoSitAttivo, verticalizzazione caricata con successo, stato attiva: {0}, tipo sit: {1}", verticalizzazioneSit.Attiva, verticalizzazioneSit.Tiposit);

                if (verticalizzazioneSit.Attiva)
                    return (TipiSitEnum)Enum.Parse(typeof(TipiSitEnum), verticalizzazioneSit.Tiposit, false);

                return TipiSitEnum.NESSUNO;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in GetTipoSitAttivo: {0}", ex.ToString());

                throw;
            }
        }
    }
}
