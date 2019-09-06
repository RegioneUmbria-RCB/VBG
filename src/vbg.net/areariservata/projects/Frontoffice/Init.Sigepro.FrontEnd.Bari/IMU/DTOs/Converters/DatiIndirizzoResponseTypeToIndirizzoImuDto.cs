// -----------------------------------------------------------------------
// <copyright file="DatiIndirizzoTypeToIndirizzoImuDto.cs" company="">
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
	using Init.Sigepro.FrontEnd.Bari.IMU.wsdl;
	using Init.Sigepro.FrontEnd.Bari.Core;


	public class DatiIndirizzoResponseTypeToIndirizzoImuDto : IMapTo<datiIndirizzoResponseType, IndirizzoImuDto>
	{
		IResolveViaDaCodviario _resolver;

		public DatiIndirizzoResponseTypeToIndirizzoImuDto(IResolveViaDaCodviario resolver)
		{
			this._resolver = resolver;
		}

		public IndirizzoImuDto Map(datiIndirizzoResponseType i)
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

			return new IndirizzoImuDto(cap, codiceVia, via, civico, esponente, palazzina, scala, piano, interno, suffisso);
		}
	}
}
