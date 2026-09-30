using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Oneri;
using log4net;
using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Logic.ServiziConsole.GestioneOneri.GestioneConti
{
    public class ContiService : ServiziLocaliRestClient, IContiService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(ContiService));
        public class ContoJson
        {
            public int Id { get; set; }
            public int Iva { get; set; }
            public int AnnoAccertamento { get; set; }
            public string MappaturaNodoPag { get; set; }
            public string Conto { get; set; }
        }

        private readonly DataBase _db;
        private readonly ConsoleService _consoleService;

        public ContiService(DataBase db, ConsoleService consoleService)
        {
            this._db = db;
            this._consoleService = consoleService;
        }

        public ContoDto GetContoDaIdCausaleOnere(string codiceComune, string softwareSuCuiCercareCausale, int idCausaleOnere)
        {
            SDEProxyDto parametri = this._consoleService.GetParametriSdeProxy();
            TipiCausaliOneri causale = new TipiCausaliOneriMgr(this._db).GetById(parametri.IdComuneBase, idCausaleOnere);

            if (causale == null)
            {
                throw new ArgumentException($"Causale onere {idCausaleOnere} non trovata", nameof(idCausaleOnere));
            }

            UrlServiziConsole urlServizi = this._consoleService.GetUrlServizi();
            string url = urlServizi.ContiDaCodiceCausaleOnere(codiceComune, softwareSuCuiCercareCausale, causale.CoId.Value, causale.CoDescrizione);

            this._log.DebugFormat("Lettura della causale dall'url {0}", url);

            try
            {
                ContoJson conto = this.QueryItem<ContoJson>(url);

                return conto == null ? null : new ContoDto
                {
                    Id = conto.Id,
                    CodiceMappaturaNodoPagamenti = conto.MappaturaNodoPag,
                    Iva = conto.Iva,
                    Conto = conto.Conto
                };
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la lettura del conto da causale onere {0} con url {1}: {2}", idCausaleOnere, url, ex.ToString());

                throw;
            }
        }
    }
}
