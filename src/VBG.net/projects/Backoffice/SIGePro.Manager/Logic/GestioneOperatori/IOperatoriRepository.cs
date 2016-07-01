// -----------------------------------------------------------------------
// <copyright file="IOperatoriRepository.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.GestioneOperatori
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.SIGePro.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IOperatoriRepository
	{
		Responsabili GetById(int id);
	}
}
