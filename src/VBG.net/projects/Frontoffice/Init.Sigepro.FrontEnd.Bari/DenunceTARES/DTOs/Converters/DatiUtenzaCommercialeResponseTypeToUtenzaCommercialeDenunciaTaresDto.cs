// -----------------------------------------------------------------------
// <copyright file="DatiUtenzaCommercialeResponseTypeToUtenzaCommercialeDenunciaTaresDto.cs" company="">
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
	public class DatiUtenzaCommercialeResponseTypeToUtenzaCommercialeDenunciaTaresDto: IMapTo<datiUtenzaCommercialeResponseType,UtenzaCommercialeDenunciaTaresDto>
	{
		IMapTo<indirizzoComuneType, IndirizzoDenunciaTares> _indirizzoMapper;
		IMapTo<datiCatastaliNewType, DatiCatastaliDenunciaTares> _datiCatastaliMapper;

		public DatiUtenzaCommercialeResponseTypeToUtenzaCommercialeDenunciaTaresDto(IMapTo<indirizzoComuneType, IndirizzoDenunciaTares> indirizzoMapper, IMapTo<datiCatastaliNewType, DatiCatastaliDenunciaTares> datiCatastaliMapper)
		{
			this._indirizzoMapper = indirizzoMapper;
			this._datiCatastaliMapper = datiCatastaliMapper;
		}


		public UtenzaCommercialeDenunciaTaresDto Map(datiUtenzaCommercialeResponseType src)
		{
			if (src == null)
			{
				return null;
			}

			var dst = new UtenzaCommercialeDenunciaTaresDto();

			dst.Id = src.identificativoUtenza;
			dst.DataVariazioneUtenza = new DataDto(src.dataVariazioneUtenza);
			dst.DataInizioUtenza = new DataDto(src.dataInizioUtenza);
			dst.Ubicazione = this._indirizzoMapper.Map(src.ubicazione);
			dst.DatiCatastali = this._datiCatastaliMapper.Map(src.datiCatastali);
			dst.AreaScoperta = src.areaScoperta;
			dst.SuperficieTassabile = src.supTassabile;

			if (src.supTotaleSpecified)
			{
				dst.SuperficieTotale = src.supTotale;
			}

			if (src.supIntassabileSpecified)
			{
				dst.SuperficieIntassabile = src.supIntassabile;
			}

			if (src.supRifiutiSpecialiSpecified)
			{
				dst.SuperficieRifiutiSpeciali = src.supRifiutiSpeciali;
			}

			dst.CodiceAttivita = src.codAttivita;


            if (src.categoriaUtenzaSpecified)
            {
                dst.CategoriaUtenza = src.categoriaUtenza;
            }

			if (src.riduzioneSuperficieSpecified)
			{
				dst.RiduzioneSuperficie = src.riduzioneSuperficie;
			}

			if (src.riduzioneTariffaSpecified)
			{
				dst.RiduzioneTariffa = src.riduzioneTariffa;
			}

			if (src.riduzioneRaccoltaDifferenziataSpecified)
			{
				dst.RiduzioneRaccoltaDifferenziata = src.riduzioneRaccoltaDifferenziata;
			}



			return dst;
		}
	}
}
