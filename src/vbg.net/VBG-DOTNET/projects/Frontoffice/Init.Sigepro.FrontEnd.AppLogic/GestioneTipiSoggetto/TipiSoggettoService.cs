// -----------------------------------------------------------------------
// <copyright file="TipiSoggettoService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto
{
    using Init.Sigepro.FrontEnd.AppLogic.Common;
    using Init.SIGePro.Manager.DTO.TipiSoggetto;
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;

    public interface ITipiSoggettoService
    {
        Task<TipoSoggettoDto> GetDtoByIdAsync(int codiceTipoSoggetto);
        TipoSoggetto GetById(int codiceTipoSoggetto);
        TipoSoggetto GetById(int codiceTipoSoggetto, int codiceIntervento);
        IEnumerable<TipoSoggetto> GetTipiSoggettoObbligatori(int? codiceIntervento);
        IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaGiurudica(int? codiceIntervento);
        IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaFisica(int? codiceIntervento);
        TipiSoggettoInterventoDto GetSoggettiPerIntervento(int codiceIntervento);
    }

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class TipiSoggettoService : ITipiSoggettoService
    {
        private readonly TipiSoggettoServiceCreator _serviceCreator;
        private readonly ISoftwareResolver _softwareResolver;

        public TipiSoggettoService(TipiSoggettoServiceCreator serviceCreator, ISoftwareResolver softwareResolver)
        {
            if (serviceCreator == null)
                throw new ArgumentNullException(nameof(serviceCreator));

            if (softwareResolver == null)
                throw new ArgumentNullException(nameof(softwareResolver));
            //Condition.Requires(serviceCreator, "serviceCreator").IsNotNull();
            //Condition.Requires(aliasSoftwareResolver, "aliasSoftwareResolver").IsNotNull();

            this._serviceCreator = serviceCreator;
            this._softwareResolver = softwareResolver;
        }

        public async Task<TipoSoggettoDto> GetDtoByIdAsync(int codiceTipoSoggetto)
        {
            return await this._serviceCreator.CallAsync(async (ws) => await ws.Service.GetByIdAsync(ws.Token, codiceTipoSoggetto));
        }

        public TipoSoggetto GetById(int id)
        {
            var ts = this._serviceCreator.Call(ws => ws.Service.GetById(ws.Token, id));

            if (ts == null)
            {
                return null;
            }

            return new TipoSoggetto(ts);
        }

        public TipoSoggetto GetById(int id, int codiceIntervento)
        {
            var ts = this._serviceCreator.Call(ws => ws.Service.GetByIdECodiceIntervento(ws.Token, id, codiceIntervento));

            if (ts == null)
            {
                return null;
            }

            return new TipoSoggetto(ts);
        }

        public TipiSoggettoInterventoDto GetSoggettiPerIntervento(int codiceIntervento)
        {
            return this.GetList(codiceIntervento);
        }

        public IEnumerable<TipoSoggetto> GetTipiSoggettoObbligatori(int? codiceIntervento)
        {
            var ts = this._serviceCreator.Call(ws => ws.Service.GetObbligatori(ws.Token, this._softwareResolver.Software, codiceIntervento));

            return ts.Select(x => new TipoSoggetto(x));
        }

        public IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaFisica(int? codiceIntervento)
        {
            return this.GetList(codiceIntervento).PersoneFisiche.Select(x => new TipoSoggetto(x));
        }

        public IEnumerable<TipoSoggetto> GetTipiSoggettoPersonaGiurudica(int? codiceIntervento)
        {
            return this.GetList(codiceIntervento).PersoneGiuridiche.Select(x => new TipoSoggetto(x));
        }

        private TipiSoggettoInterventoDto GetList(int? codiceIntervento)
        {
            var software = this._softwareResolver.Software;

            return this._serviceCreator.Call(ws => ws.Service.GetTipiSoggettoDaIdIntervento(ws.Token, software, codiceIntervento));
        }
    }
}
