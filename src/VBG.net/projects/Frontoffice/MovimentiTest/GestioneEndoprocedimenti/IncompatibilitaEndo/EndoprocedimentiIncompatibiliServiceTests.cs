// -----------------------------------------------------------------------
// <copyright file="EndoprocedimentiIncompatibiliServiceTests.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace MovimentiTest.GestioneEndoprocedimenti.IncompatibilitaEndo
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Microsoft.VisualStudio.TestTools.UnitTesting;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;
	using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

	[TestClass]
	public class EndoprocedimentiIncompatibiliServiceTests
	{
		public class EndoprocedimentiIncompatibiliRepository_Fake : IEndoprocedimentiIncompatibiliRepository
		{
			public IEnumerable<EndoprocedimentoIncompatibileDto> Result;

			public IEnumerable<EndoprocedimentoIncompatibileDto> GetEndoprocedimentiIncompatibili(int[] listaIdEndoAttivati)
			{
				return this.Result;
			}
		}

		EndoprocedimentiIncompatibiliRepository_Fake _repository;
		EndoprocedimentiIncompatibiliService _svc;

		[TestInitialize]
		public void Initialize()
		{
			this._repository = new EndoprocedimentiIncompatibiliRepository_Fake();
			this._svc = new EndoprocedimentiIncompatibiliService(this._repository);

		}

		[TestMethod]
		public void Quando_esistono_piu_endo_incompatibili_con_un_endo_questi_vengono_raggruppati()
		{
			_repository.Result = new EndoprocedimentoIncompatibileDto[]{
				new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 1 , CodiceEndoprocedimentoIncompatibile = 2},
				new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 1 , CodiceEndoprocedimentoIncompatibile = 3},
			};

			var listaEndoAttivati = new int[] { 1, 2, 3 };

			var result = this._svc.GetEndoprocedimentiIncompatibili(listaEndoAttivati);

			Assert.AreEqual<int>(1, result.Count());
			Assert.AreEqual<int>(1, result.ElementAt(0).Endo);
			Assert.AreEqual<int>(2, result.ElementAt(0).EndoIncompatibili.Count());
			Assert.AreEqual<int>(2, result.ElementAt(0).EndoIncompatibili.ElementAt(0));
			Assert.AreEqual<int>(3, result.ElementAt(0).EndoIncompatibili.ElementAt(1));
		}

		[TestMethod]
		public void Endo_incompatibili_con_endo_diversi_restituiscono_piu_di_un_elemento()
		{
			_repository.Result = new EndoprocedimentoIncompatibileDto[]{
				new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 1 , CodiceEndoprocedimentoIncompatibile = 2},
				new EndoprocedimentoIncompatibileDto{CodiceEndoprocedimento = 3 , CodiceEndoprocedimentoIncompatibile = 4},
			};

			var listaEndoAttivati = new int[] { 1, 2, 3, 4 };

			var result = this._svc.GetEndoprocedimentiIncompatibili(listaEndoAttivati);

			Assert.AreEqual<int>(2, result.Count());
			Assert.AreEqual<int>(1, result.ElementAt(0).Endo);
			Assert.AreEqual<int>(1, result.ElementAt(0).EndoIncompatibili.Count());
			Assert.AreEqual<int>(2, result.ElementAt(0).EndoIncompatibili.ElementAt(0));

			Assert.AreEqual<int>(3, result.ElementAt(1).Endo);
			Assert.AreEqual<int>(1, result.ElementAt(1).EndoIncompatibili.Count());
			Assert.AreEqual<int>(4, result.ElementAt(1).EndoIncompatibili.ElementAt(0));
		}
	}
}
