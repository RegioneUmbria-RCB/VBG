// -----------------------------------------------------------------------
// <copyright file="DatiUtenzaDomesticaResponseTypeToUtenzaDomesticaDenunciaTaresDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.DenunceTARES.DTOs.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TARES.ServicesProxies;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiUtenzaDomesticaResponseTypeToUtenzaDomesticaDenunciaTaresDto: IMapTo<datiUtenzaDomesticaResponseType, UtenzaDomesticaDenunciaTaresDto>
	{
		IMapTo<indirizzoComuneType, IndirizzoDenunciaTares> _indirizzoMapper;
		IMapTo<datiCatastaliNewType, DatiCatastaliDenunciaTares> _datiCatastaliMapper;
		IMapTo<occupanteImmobileType, OccupanteImmobileDenunciaTares> _occupanteImmobileMapper;

		public DatiUtenzaDomesticaResponseTypeToUtenzaDomesticaDenunciaTaresDto(IMapTo<indirizzoComuneType, IndirizzoDenunciaTares> indirizzoMapper, IMapTo<datiCatastaliNewType, DatiCatastaliDenunciaTares> datiCatastaliMapper)
		{
			this._indirizzoMapper = indirizzoMapper;
			this._datiCatastaliMapper = datiCatastaliMapper;
		}

		public UtenzaDomesticaDenunciaTaresDto Map(datiUtenzaDomesticaResponseType src)
		{
			if (src == null)
			{
				return null;
			}

			var dst = new UtenzaDomesticaDenunciaTaresDto();

			dst.Id = src.identificativoUtenza;
			dst.DataVariazioneUtenza = new DataDto(src.dataVariazioneUtenza);
			dst.DataInizioUtenza = new DataDto(src.dataInizioUtenza);
			dst.Ubicazione = this._indirizzoMapper.Map(src.ubicazione);
			dst.DatiCatastali = this._datiCatastaliMapper.Map(src.datiCatastali);

			if (src.categoriaCatastaleSpecified)
			{
				dst.CategoriaCatastale = src.categoriaCatastale.ToString();
			}

			dst.Superficie = src.superficie;

			if (src.riduzioneTariffaSpecified)
			{
				dst.RiduzioneTariffa = src.riduzioneTariffa.ToString();
			}

			if (src.numComponentiNucleoFamiliareSpecified)
			{
				dst.NumeroComponentiNumeroFamiliare = src.numComponentiNucleoFamiliare.ToString();
			}

			if (src.elencoOccupantiImmobile != null)
			{
				var occupanti = src.elencoOccupantiImmobile.Select( x => this._occupanteImmobileMapper.Map(x));

				dst.OccupantiImmobile = occupanti.ToList();
			}

			return dst;
		}
	}
}
