using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.SIGePro.Manager.DTO.Comuni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni
{

    internal class ComuniAssociatiService : IComuniAssociatiService
    {
        private readonly ISoftwareResolver _softwareResolver;
        private readonly ComuniServiceCreator _serviceCreator;

        public ComuniAssociatiService(ComuniServiceCreator serviceCreator, ISoftwareResolver softwareResolver)
        {
            this._serviceCreator = serviceCreator ?? throw new ArgumentNullException(nameof(serviceCreator));
            this._softwareResolver = softwareResolver ?? throw new ArgumentNullException(nameof(softwareResolver));
        }

        public IEnumerable<DatiComuneCompatto> GetComuniAssociati(string[]? codiciComuneDaEscludere = null)
        {
            var comuniAssociati = this.GetComuniAssociati(this._softwareResolver.Software);

            if (codiciComuneDaEscludere == null || codiciComuneDaEscludere.Length == 0)
            {
                return comuniAssociati;
            }

            return comuniAssociati.Where(x => !codiciComuneDaEscludere.Contains(x.CodiceComune));
        }

        public IEnumerable<DatiComuneCompatto> GetComuniAssociati(string software)
        {
            return this._serviceCreator.Call(ws => ws.Service.GetComuniAssociati(ws.Token, software));
        }

        public async Task<IEnumerable<DatiComuneCompatto>> GetComuniAssociatiAsync(string software)
        {
            return await this._serviceCreator.CallAsync(async (ws) => await ws.Service.GetComuniAssociatiAsync(ws.Token, software));
        }

        public async Task<IEnumerable<DatiComuneCompatto>> GetComuniAssociatiAsync(string[]? codiciComuneDaEscludere = null)
        {
            var comuniAssociati = await this.GetComuniAssociatiAsync(this._softwareResolver.Software);

            if (codiciComuneDaEscludere == null || codiciComuneDaEscludere.Length == 0)
            {
                return comuniAssociati;
            }

            return comuniAssociati.Where(x => !codiciComuneDaEscludere.Contains(x.CodiceComune));
        }

        public async Task<DatiComuneCompatto?> GetByCodiceComuneAsync(string codiceComune)
        {
            if (String.IsNullOrEmpty(codiceComune))
            {
                return null;
            }

            var comuni = await this.GetComuniAssociatiAsync(this._softwareResolver.Software);

            return comuni.FirstOrDefault(x => x.CodiceComune == codiceComune);
        }
    }
}
