// -----------------------------------------------------------------------
// <copyright file="DatiContribuenteImuResponseTypeToDatiContribuenteImuDto.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU.DTOs.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using Init.Sigepro.FrontEnd.Bari.TASI;
	using Init.Sigepro.FrontEnd.Bari.IMU.wsdl;
	using System.Globalization;
	using Init.Sigepro.FrontEnd.Bari.Core;

	public class DatiContribuenteImuResponseTypeToDatiContribuenteImuDto : IMapTo<datiContribuenteImuResponseType, DatiContribuenteImuDto>
	{
		IMapTo<datiIndirizzoResponseType, IndirizzoImuDto> _indirizzoMapper;
		IMapTo<datiImmobileResponseType, ImmobileImuDto> _immobiliMapper;
		IResolveComuneDaCodiceIstat _comuneResolver;

		public DatiContribuenteImuResponseTypeToDatiContribuenteImuDto(IMapTo<datiIndirizzoResponseType, IndirizzoImuDto> indirizzoMapper, IMapTo<datiImmobileResponseType, ImmobileImuDto> immobiliMapper, IResolveComuneDaCodiceIstat comuneResolver)
		{
			this._indirizzoMapper = indirizzoMapper;
			this._immobiliMapper = immobiliMapper;
			this._comuneResolver = comuneResolver;
		}

		public DatiContribuenteImuDto Map(datiContribuenteImuResponseType source)
		{
			if (source == null) return null;

			var rVal = new DatiContribuenteImuDto
			{
				IdContribuente = source.idContribuente.ToString(),
				TipoPersona = source.Item.GetType() == typeof(personaFisicaType) ? DatiContribuenteImuDto.TipoPersonaEnum.Fisica : DatiContribuenteImuDto.TipoPersonaEnum.Giuridica
			};

			if (rVal.TipoPersona == DatiContribuenteImuDto.TipoPersonaEnum.Fisica)
			{
				var pf = source.Item as personaFisicaType;

				rVal.CodiceFiscale = pf.codiceFiscale;
				rVal.Cognome = pf.cognome;
				rVal.Nome = pf.nome;

				if (!String.IsNullOrEmpty(pf.comuneNascita))
				{
					rVal.CodiceIstatComuneNascita = pf.comuneNascita;
					rVal.ComuneNascita = this._comuneResolver.GetComuneDaCodiceIstat(pf.comuneNascita);
					rVal.ProvinciaNascita = pf.provinciaNascita;
				}
				else
				{
					rVal.ComuneNascita = pf.comuneEsteroNascita;
				}

				rVal.DataNascita = String.IsNullOrEmpty(pf.dataNascita) ? (DateTime?)null : DateTime.ParseExact(pf.dataNascita, "dd/MM/yyyy", CultureInfo.InvariantCulture);

				rVal.Sesso = pf.sesso == sessoType.M ? DatiContribuenteImuDto.SessoEnum.Maschio : DatiContribuenteImuDto.SessoEnum.Femmina;
			}

			if (rVal.TipoPersona == DatiContribuenteImuDto.TipoPersonaEnum.Giuridica)
			{
				var pg = source.Item as societaResponseType;

				rVal.CodiceFiscale = pg.partitaIva;
				rVal.Cognome = pg.denominazione;
			}

			rVal.Residenza = this._indirizzoMapper.Map(source.datiResidenza);

			if (source.immobili != null)
			{

				var elencoImmobili = source.immobili.Select(x => this._immobiliMapper.Map(x)).ToArray();

				rVal.AggiungiListaImmobili(elencoImmobili);
			}

			return rVal;
		}
	}
}
