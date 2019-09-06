// -----------------------------------------------------------------------
// <copyright file="IAnagrafeRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneAnagrafiche
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.SIGePro.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IAnagrafeRepository
	{
		Anagrafe GetById(int codiceAnagrafe);
	}
}
