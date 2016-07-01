using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo
{
	public static class GenerazioneHtmlDomandaConstants
	{
		private const string _wrappingDivHtml = "<div id='datiDinamici'>{0}</div>";
		public const string SegnapostoSchedeDinamiche1 = "<schedeDinamiche />";
		public const string SegnapostoSchedeDinamiche2 = "<schedeDinamiche></schedeDinamiche>";
		public const string HtmlWrapper = @"<!DOCTYPE html>
<html>
	<head runat=""server"">
		<meta http-equiv=""Content-Type"" content=""text/html; {0}"" />
	</head>
	<body>
	{1}
	</body>
</html>";

		private const string _cssModelliDinamici = @"<style type=""text/css"" media=""all"">
		body{font-family:Arial;font-size:12px;}
		TABLE
		{
			font-family:Arial;font-size:12px;
			border-collapse: collapse;
			width: 100%;
			border-color: #666666;
		}

		TD
		{
			border-color: #666666;
		}
		
		.Titolo
		{
			background-color: #cccccc;
			width:100%;
			font-weight: bold;
			margin-bottom: 10px;
			margin-top: 10px;
			padding: 5px;
			background-image: url('../../images/th_bck.gif');
			/*
			background-color: #ffffff;
			background-image: url('../../images/th_bck.gif');
			background-repeat: repeat-x;
			font-weight: bold;
			padding-bottom: 5px;
			padding-top: 5px;
			padding-left: 5px;
			border: 1px solid #cccccc;
			*/
		}
		
		.EtichettaControllo
		{
		    font-weight:bold;
		}
		
		#datiDinamici
		{
			width: 100%;
		}

		#datiDinamici .bloccoMultiplo
		{
			border: 1px solid #666;
			margin-top: 3px;
			margin-bottom: 5px;
		}

		#datiDinamici .bloccoMultiplo TABLE
		{
			border: 0px;
		}
		#datiDinamici img
		{
			display:none;
		}

		#datiDinamici .descrizioneCampoDinamico
		{
			display:none;
		}

		.titoloSchedaDinamica
		{
			font-size: 16px; 
			font-weight: bold;
		}
	</style>";

		private static bool IgnoraCssModelliDinamici
		{
			get
			{
				var setting = ConfigurationManager.AppSettings["generazionePdfDatiDinamici.ignoraCssModelliDinamici"];

				if (String.IsNullOrEmpty(setting) || setting.ToUpper() != "TRUE")
					return false;

				return true;
			}
		}

		public static string WrappingDivHtml 
		{
			get
			{
				if (IgnoraCssModelliDinamici)
				{
					return "{0}"; 
				}

				return _wrappingDivHtml;
			}
		}

		public static string CssModelliDinamici
		{
			get
			{
				if (IgnoraCssModelliDinamici)
				{
					return String.Empty;					
				}

				return _cssModelliDinamici;
			}
		}
	}
}
