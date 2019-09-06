// -----------------------------------------------------------------------
// <copyright file="IBariCidService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.CID
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.Bari.CID.DTOs;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IBariCidService
	{
		DatiCidDto GetDatiCid(DatiOperatoreDto operatore, DatiRichiestaDto richiesta);
        DatiCidDto ValidaCidPin(string cid, string pin);
	}
}
