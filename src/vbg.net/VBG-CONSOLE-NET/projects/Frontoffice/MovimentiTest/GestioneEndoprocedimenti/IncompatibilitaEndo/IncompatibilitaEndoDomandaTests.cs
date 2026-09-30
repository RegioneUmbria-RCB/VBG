// -----------------------------------------------------------------------
// <copyright file="IncompatibilitaEndoDomanda.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest.GestioneEndoprocedimenti.IncompatibilitaEndo
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
	using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Microsoft.VisualStudio.TestTools.UnitTesting;

	[TestClass]
	public class IncompatibilitaEndoDomandaTests
	{
		public class FakeEndoIncompatibiliService : IEndoprocedimentiIncompatibiliService
		{
			public IEnumerable<CodiciEndoIncompatibili> Result;
            public string NaturaBase;

			public IEnumerable<CodiciEndoIncompatibili> GetEndoprocedimentiIncompatibili(IEnumerable<int> codiciEndo)
			{
				return this.Result;
			}

            public string GetNaturaBaseDaidEndoprocedimento(int codiceInventario)
            {
                return this.NaturaBase;
            }
        }

		EndoprocedimentiReadInterface _readInterface;
		PresentazioneIstanzaDbV2 _db;

		[TestInitialize]
		public void Initialize()
		{
			this._db = new PresentazioneIstanzaDbV2();
			this._db.ISTANZEPROCEDIMENTI.AddISTANZEPROCEDIMENTIRow(1, "Endo 1", false, 0, String.Empty, false, String.Empty, DateTime.Now, 0, 0, String.Empty, false, false, String.Empty, String.Empty, -1, false);
			this._db.ISTANZEPROCEDIMENTI.AddISTANZEPROCEDIMENTIRow(2, "Endo 2", false, 0, String.Empty, false, String.Empty, DateTime.Now, 0, 0, String.Empty, false, false, String.Empty, String.Empty, -1, true);

			this._readInterface = new EndoprocedimentiReadInterface(this._db);
		}

		[TestMethod]
		public void Esistono_endo_incompatibili_ottengo_un_risultato()
		{
			var fakeService = new FakeEndoIncompatibiliService();
			var codiciIncompatibili = new CodiciEndoIncompatibili(1);
			codiciIncompatibili.AggiungiEndoprocedimentoIncompatibile(2);


			fakeService.Result = new CodiciEndoIncompatibili[]{
					codiciIncompatibili
			};

			var result = this._readInterface.GetEndoprocedimentiIncompatibili(fakeService);

			Assert.AreEqual<int>(1, result.Count());
			Assert.AreEqual<string>("Endo 1", result.ElementAt(0).Endoprocedimento);
			Assert.AreEqual<int>(1, result.ElementAt(0).EndoprocedimentiIncompatibili.Count());
			Assert.AreEqual<string>("Endo 2", result.ElementAt(0).EndoprocedimentiIncompatibili.ElementAt(0));
		}

		[TestMethod]
		public void Non_esistono_endo_incompatibili_ottengo_lista_vuota()
		{
			var fakeService = new FakeEndoIncompatibiliService();

			fakeService.Result = new CodiciEndoIncompatibili[0];

			var result = this._readInterface.GetEndoprocedimentiIncompatibili(fakeService);

			Assert.AreEqual<int>( 0, result.Count());
		}
	}
}
