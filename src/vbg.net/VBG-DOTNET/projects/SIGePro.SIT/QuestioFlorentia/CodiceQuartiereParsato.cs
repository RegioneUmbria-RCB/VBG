using log4net;

namespace SIGePro.SIT.QuestioFlorentia
{
    public class CodiceQuartiereParsato
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(CodiceQuartiereParsato));
        public readonly string CodComune;
        public readonly string CodiceQuartiere;

        public CodiceQuartiereParsato(string stringaCodQuartiere)
        {
            this._log.DebugFormat("stringa quartiere: {0}", stringaCodQuartiere);

            this.CodComune = stringaCodQuartiere.Substring(0, 4);
            this.CodiceQuartiere = stringaCodQuartiere.Substring(4);

            this._log.DebugFormat("Codice comune: {0}, codice quartiere: {1}", this.CodComune, this.CodiceQuartiere);
        }
    }
}
