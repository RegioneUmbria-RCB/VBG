// -----------------------------------------------------------------------
// <copyright file="DatiImmobileResponseTypeToImmobileImuDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU.DTOs.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.IMU.wsdl;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using System.Globalization;


	public class DatiImmobileResponseTypeToImmobileImuDto : IMapTo<datiImmobileResponseType, ImmobileImuDto>
	{
		IMapTo<datiIndirizzoImmobileResponse, IndirizzoImuDto> _ubicazioneMapper;
		IMapTo<datiCatastaliType, DatiCatastaliImuDto> _datiCatastaliMapper;

		public DatiImmobileResponseTypeToImmobileImuDto(IMapTo<datiIndirizzoImmobileResponse, IndirizzoImuDto> ubicazioneMapper, IMapTo<datiCatastaliType, DatiCatastaliImuDto> datiCatastaliMapper)
		{
			this._ubicazioneMapper = ubicazioneMapper;
			this._datiCatastaliMapper = datiCatastaliMapper;
		}

		public ImmobileImuDto Map(datiImmobileResponseType i)
		{
			if (i == null) return null;

			var ubicazione = this._ubicazioneMapper.Map(i.ubicazione);
			var riferimentiCatastali = this._datiCatastaliMapper.Map(i.datiCatastali);
			var percentualePossesso = i.percentualePossesso.ToString("0.0", CultureInfo.InvariantCulture);
			var tipoImmobile = TipoImmobileEnum.Sconosciuto;
			var categoriaCatastale = i.categoriaCatastale;
			var idImmobile = i.idImmobile;
            // var dataInizioPossesso = i.dataInizioPossesso;

            return new ImmobileImuDto(ubicazione, riferimentiCatastali, /*dataInizioPossesso,*/ percentualePossesso, idImmobile, tipoImmobile, categoriaCatastale);
		}
	}
}
