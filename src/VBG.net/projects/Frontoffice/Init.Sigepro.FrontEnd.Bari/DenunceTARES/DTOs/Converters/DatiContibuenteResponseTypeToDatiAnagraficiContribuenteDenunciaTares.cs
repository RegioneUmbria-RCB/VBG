// -----------------------------------------------------------------------
// <copyright file="DatiContibuenteResponseTypeToDatiAnagraficiContribuenteDenunciaTares.cs" company="">
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
using Init.Sigepro.FrontEnd.Bari.Core;
using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;
using System.Globalization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiContribuenteResponseTypeToDatiAnagraficiContribuenteDenunciaTares: IMapTo<datiContribuenteResponseType,DatiAnagraficiContribuenteDenunciaTares>
	{
		IResolveComuneDaCodiceIstat _comuneResolver;
		IMapTo<datiRappresentanteType,RappresentanteDenunciaTares> _datiRappresentanteMapper;
		IMapTo<indirizzoType,IndirizzoDenunciaTares> _indirizzoMapper;
		IMapTo<datiUtenzaCommercialeResponseType, UtenzaCommercialeDenunciaTaresDto> _utenzeCommercialiMapper;
		IMapTo<datiUtenzaDomesticaResponseType, UtenzaDomesticaDenunciaTaresDto> _utenzeDomesticheMapper;

		public DatiContribuenteResponseTypeToDatiAnagraficiContribuenteDenunciaTares(
			IResolveComuneDaCodiceIstat comuneResolver, 
			IMapTo<datiRappresentanteType, RappresentanteDenunciaTares> datiRappresentanteMapper, 
			IMapTo<indirizzoType, IndirizzoDenunciaTares> indirizzoMapper, 
			IMapTo<datiUtenzaCommercialeResponseType, UtenzaCommercialeDenunciaTaresDto> utenzeCommercialiMapper, 
			IMapTo<datiUtenzaDomesticaResponseType, UtenzaDomesticaDenunciaTaresDto> utenzeDomesticheMapper)
		{
			this._comuneResolver = comuneResolver;
			this._datiRappresentanteMapper = datiRappresentanteMapper;
			this._indirizzoMapper = indirizzoMapper;
			this._utenzeCommercialiMapper = utenzeCommercialiMapper;
			this._utenzeDomesticheMapper = utenzeDomesticheMapper;
		}

		public DatiAnagraficiContribuenteDenunciaTares Map(datiContribuenteResponseType source)
		{
			if (source == null) return null;

			var datiAnagrafici = source.datiAnagrafici;

			var dest = new DatiAnagraficiContribuenteDenunciaTares
			{
				IdContribuente = source.idContribuente,
				TipoPersona = datiAnagrafici.Item.GetType() == typeof(personaFisicaType) ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica
			};

			dest.PartitaIva = source.datiAnagrafici.partitaIVA;
			dest.Pec = source.datiAnagrafici.indirizzoPEC;
			dest.Telefono = source.datiAnagrafici.telefono;
			dest.Fax = source.datiAnagrafici.fax;

			if (source.datiAnagrafici.numeroREASpecified) {
				dest.NumeroREA = source.datiAnagrafici.numeroREA;
				dest.ProvinciaREA = source.datiAnagrafici.provinciaREA;
			}			

			if (dest.TipoPersona == TipoPersonaEnum.Fisica)
			{
				var pf = datiAnagrafici.Item as personaFisicaType;

				dest.CodiceFiscale = pf.codiceFiscale;
				dest.Cognome = pf.cognome;
				dest.Nome = pf.nome;

                if (!String.IsNullOrEmpty(pf.comuneNascita))
                {
                    dest.CodiceIstatComuneNascita = pf.comuneNascita;
                    dest.ComuneNascita = this._comuneResolver.GetComuneDaCodiceIstat(pf.comuneNascita);
                    dest.ProvinciaNascita = pf.provinciaNascita;
                }
                else
                {
                    dest.ComuneNascita = pf.comuneEsteroNascita;
                }

				dest.DataNascita = String.IsNullOrEmpty(pf.dataNascita) ? (DateTime?)null : DateTime.ParseExact(pf.dataNascita, "dd/MM/yyyy", CultureInfo.InvariantCulture);
				
				dest.Sesso = pf.sesso == sessoType.M ? SessoEnum.Maschio : SessoEnum.Femmina;
			}

			if (dest.TipoPersona == TipoPersonaEnum.Giuridica)
			{
				var pg = datiAnagrafici.Item as personaGiuridicaType;

				dest.CodiceFiscale = pg.codiceFiscale;
				dest.Cognome = pg.denominazione;
				dest.LegaleRappresentante = this._datiRappresentanteMapper.Map(datiAnagrafici.datiRappresentante);
			}

			dest.IndirizzoResidenza = this._indirizzoMapper.Map(source.datiAnagrafici.datiDomicilio);
			dest.IndirizzoCorrispondenza = this._indirizzoMapper.Map(source.datiAnagrafici.datiRecapito);

			if (source.datiUtenzeAttive != null) 
			{
				if (source.datiUtenzeAttive.datiUtenzeCommerciali != null)
				{
					var utenzeCommerciali = source.datiUtenzeAttive.datiUtenzeCommerciali.Select( x => this._utenzeCommercialiMapper.Map(x));

					dest.AggiungiUtenzeCommerciali(utenzeCommerciali);
				}

				if (source.datiUtenzeAttive.datiUtenzeDomestiche != null)
				{
					var utenzeDomestiche = source.datiUtenzeAttive.datiUtenzeDomestiche.Select( x => this._utenzeDomesticheMapper.Map(x));

					dest.AggiungiUtenzeDomestiche(utenzeDomestiche);
				}
			}
			
			return dest;
		}
	}
}
