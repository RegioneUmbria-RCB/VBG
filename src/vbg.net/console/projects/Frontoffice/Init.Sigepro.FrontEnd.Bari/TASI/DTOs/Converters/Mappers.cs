// -----------------------------------------------------------------------
// <copyright file="AutomapperConverters.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.TASI.DTOs.Converters
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Bari.TASI.wsdl;
	using AutoMapper;
	using System.Globalization;
	using Init.Sigepro.FrontEnd.Bari.Core.SharedDTOs;
	using Init.Sigepro.FrontEnd.Infrastructure.Mapping;
	using Init.Sigepro.FrontEnd.Bari.Core;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiImmobiliResponseTypeToDatiContribuenteTasiDto : IMapTo<datiImmobiliResponseType, DatiContribuenteTasiDto>
	{
		IMapTo<datiIndirizzoType, IndirizzoTasiDto> _indirizzoMapper;
		IMapTo<datiImmobileResponseType, ImmobileTasiDto> _immobiliMapper;
        IResolveComuneDaCodiceIstat _comuneResolver;

		public DatiImmobiliResponseTypeToDatiContribuenteTasiDto(IMapTo<datiIndirizzoType,IndirizzoTasiDto> indirizzoMapper, IMapTo<datiImmobileResponseType, ImmobileTasiDto> immobiliMapper, IResolveComuneDaCodiceIstat comuneResolver)
		{
			this._indirizzoMapper = indirizzoMapper;
			this._immobiliMapper = immobiliMapper;
            this._comuneResolver = comuneResolver;
		}

		public DatiContribuenteTasiDto Map(datiImmobiliResponseType source)
		{
			if (source == null) return null;

			var rVal = new DatiContribuenteTasiDto
			{
				CodiceContribuente = source.codContribuente,
				IdContribuente = source.idContribuente,
				TipoPersona = source.Item.GetType() == typeof(datiAnagraficiPersonaFisicaType) ? DatiContribuenteTasiDto.TipoPersonaEnum.Fisica : DatiContribuenteTasiDto.TipoPersonaEnum.Giuridica
			};

			if (rVal.TipoPersona == DatiContribuenteTasiDto.TipoPersonaEnum.Fisica)
			{
				var pf = source.Item as datiAnagraficiPersonaFisicaType;

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
				
				rVal.Sesso = pf.sesso == sessoType.M ? DatiContribuenteTasiDto.SessoEnum.Maschio : DatiContribuenteTasiDto.SessoEnum.Femmina;
			}

			if (rVal.TipoPersona == DatiContribuenteTasiDto.TipoPersonaEnum.Giuridica)
			{
				var pg = source.Item as datiAnagraficiPersonaGiuridicaType;

				rVal.CodiceFiscale = pg.partitaIva;
				rVal.Cognome = pg.denominazione;
			}

			rVal.Residenza = this._indirizzoMapper.Map(source.datiResidenzaContribuente);

            if (source.elencoImmobili != null)
            {

                var elencoImmobili = source.elencoImmobili.Select(x => this._immobiliMapper.Map(x)).ToArray();

                rVal.AggiungiListaImmobili(elencoImmobili);
            }

			return rVal;
		}
	}
	
	public class DatiIndirizzoTypeToIndirizzoTasiDto : IMapTo<datiIndirizzoType, IndirizzoTasiDto>
	{
		IResolveViaDaCodviario _resolver;

		public DatiIndirizzoTypeToIndirizzoTasiDto(IResolveViaDaCodviario resolver)
		{
			this._resolver = resolver;
		}

		public IndirizzoTasiDto Map(datiIndirizzoType i)
		{
			if (i == null) return null;

			var codiceVia = String.Empty;
			var via = i.Item;

			if (i.ItemElementName == ItemChoiceType1.viaCodificata)
			{
				codiceVia = via;
				via = this._resolver.GetNomeByCodViario(codiceVia);
			}

			var civico = i.numeroCivicoSpecified ? i.numeroCivico.ToString() : String.Empty;
			var esponente = i.esponente;
			var palazzina = i.palazzina;
			var scala = i.scala;
			var piano = i.piano;
			var interno = i.interno;
			var cap = i.cap;
			var suffisso = i.suffisso;

			return new IndirizzoTasiDto(cap, codiceVia, via, civico, esponente, palazzina, scala, piano, interno, suffisso);
		}
	}

	public class DatiImmobileResponseTypeToImmobileTasiDto : IMapTo<datiImmobileResponseType,ImmobileTasiDto>
	{
		IMapTo<indirizzoImmobileType, IndirizzoTasiDto> _ubicazioneMapper;
		IMapTo<datiCatastaliType, DatiCatastaliTasiDto> _datiCatastaliMapper;

		public DatiImmobileResponseTypeToImmobileTasiDto(IMapTo<indirizzoImmobileType,IndirizzoTasiDto> ubicazioneMapper, IMapTo<datiCatastaliType,DatiCatastaliTasiDto> datiCatastaliMapper)
		{
			this._ubicazioneMapper = ubicazioneMapper;
			this._datiCatastaliMapper = datiCatastaliMapper;
		}

		public ImmobileTasiDto Map(datiImmobileResponseType i)
		{
			if (i == null) return null;

			var ubicazione = this._ubicazioneMapper.Map(i.ubicazione);
			var riferimentiCatastali = this._datiCatastaliMapper.Map(i.datiCatastali);
			var percentualePossesso = i.percentualePossesso.ToString("0.0", CultureInfo.InvariantCulture);
			var idImmobile = i.idImmobile;

			return new ImmobileTasiDto(ubicazione, riferimentiCatastali, i.dataInizioDecorrenza, percentualePossesso, idImmobile);
		}
	}

	public class IndirizzoImmobileTypeToIndirizzoTasiDto : IMapTo<indirizzoImmobileType, IndirizzoTasiDto>
	{
		IResolveViaDaCodviario _resolver;

		public IndirizzoImmobileTypeToIndirizzoTasiDto(IResolveViaDaCodviario resolver)
		{
			this._resolver = resolver;
		}

		public IndirizzoTasiDto Map(indirizzoImmobileType i)
		{
			if (i == null) return null;

			var codiceVia = String.Empty;
			var via = i.Item;

			if (i.ItemElementName == ItemChoiceType2.viaCodificata)
			{
				codiceVia = via;
				via = this._resolver.GetNomeByCodViario(codiceVia);
			}

			var civico = i.civicoSpecified ? i.civico.ToString() : String.Empty;
			var esponente = i.esponente;
			var palazzina = i.palazzina;
			var scala = i.scala;
			var piano = i.piano;
			var interno = i.interno;
			var cap = String.Empty;
			var suffisso = String.Empty;

			return new IndirizzoTasiDto(cap, codiceVia, via, civico, esponente, palazzina, scala, piano, interno, suffisso);
		}
	}

	public class DatiCatastaliTypeToDatiCatastaliDto : IMapTo<datiCatastaliType, DatiCatastaliTasiDto>
	{
		public DatiCatastaliTasiDto Map(datiCatastaliType d)
		{
			if (d == null) return null;

			var sezione = d.sezione;
			var foglio = d.foglio;
			var particella = d.particella;
			var subalterno = d.subalterno;
			var categoriaCatastale = d.categoriaCatastale.ToString();

			return new DatiCatastaliTasiDto(sezione, foglio, particella, subalterno, categoriaCatastale);
		}
	}


}
