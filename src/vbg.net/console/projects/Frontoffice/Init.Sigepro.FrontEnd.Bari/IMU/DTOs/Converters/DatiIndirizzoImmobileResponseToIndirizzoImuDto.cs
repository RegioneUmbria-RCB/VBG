// -----------------------------------------------------------------------
// <copyright file="IndirizzoImmobileTypeToIndirizzoImuDto.cs" company="">
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
	using Init.Sigepro.FrontEnd.Bari.Core;
	
	public class DatiIndirizzoImmobileResponseToIndirizzoImuDto : IMapTo<datiIndirizzoImmobileResponse, IndirizzoImuDto>
	{
		IResolveViaDaCodviario _resolver;

		public DatiIndirizzoImmobileResponseToIndirizzoImuDto(IResolveViaDaCodviario resolver)
		{
			this._resolver = resolver;
		}

		public IndirizzoImuDto Map(datiIndirizzoImmobileResponse i)
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
            var suffisso = i.suffisso;

			return new IndirizzoImuDto(String.Empty, codiceVia, via, civico, esponente, palazzina, scala, piano, interno, suffisso);
		}
	}

}
