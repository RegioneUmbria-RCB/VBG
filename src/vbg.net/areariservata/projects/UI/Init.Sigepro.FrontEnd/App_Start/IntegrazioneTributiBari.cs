[assembly: WebActivatorEx.PostApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.IntegrazioneTributiBari), "Start")]

namespace Init.Sigepro.FrontEnd.App_Start
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web;
	using Init.Sigepro.FrontEnd.Bari.TARES;
	using Init.Sigepro.FrontEnd.Bari.TASI;

	public class IntegrazioneTributiBari
	{
		public static void Start()
		{
			TaresAutomapperConfiguration.Bootstrap();

		}
	}
}