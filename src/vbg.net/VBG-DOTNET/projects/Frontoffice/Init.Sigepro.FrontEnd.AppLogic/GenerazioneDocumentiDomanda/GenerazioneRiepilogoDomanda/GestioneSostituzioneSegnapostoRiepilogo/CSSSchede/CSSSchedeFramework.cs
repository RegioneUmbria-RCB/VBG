namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.CSSSchede
{
    internal static class CSSSchedeFramework
    {
        public const string CSS = @"<style type=""text/css"" media=""all"">
		
		TABLE
		{
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

		#datiDinamici p {
			margin-top: 12px;
			margin-bottom: 12px;
		}
        /*
		#datiDinamici tr>td:empty {
			display:none;
		}
        */
        #datiDinamici li {
            margin-bottom: 4px;
        }

		#datiDinamici .checkbox {
			display: inline-block;
			margin-right: 8px;
            /*font-weight: bold;*/

            color: transparent;
            width: 16px;
            height: 16px;
		}

		#datiDinamici .checkbox.checked {
            background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAARCAYAAADUryzEAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsIAAA7CARUoSoAAAAAYdEVYdFNvZnR3YXJlAFBhaW50Lk5FVCA1LjEuOBtp6qgAAAC2ZVhJZklJKgAIAAAABQAaAQUAAQAAAEoAAAAbAQUAAQAAAFIAAAAoAQMAAQAAAAIAAAAxAQIAEAAAAFoAAABphwQAAQAAAGoAAAAAAAAA8nYBAOgDAADydgEA6AMAAFBhaW50Lk5FVCA1LjEuOAADAACQBwAEAAAAMDIzMAGgAwABAAAAAQAAAAWgBAABAAAAlAAAAAAAAAACAAEAAgAEAAAAUjk4AAIABwAEAAAAMDEwMAAAAADMbhZ8SlPoHAAAAldJREFUOE+Fk01rE1EUht8z1xnSZDIznUmYEKg1pO00IFGhJQEluOnaTVz6E4RuhOrC6kbU/yDoSrBdiEKtUCjFgl0K7SrIEAstCSYhMMkkBjLHhbamk2IfOItz73M+Fvdibm6upOv6dwA8GkR0Jh+NSCTCiURi13GcG2Sa5td2u12Mx+PbkiR5QRCQ53kDZsZ5CCECRVHSvu/ftCxrE5qm/dJ1fbNcLisAsLq6SuGiMIVCQTMM41ssFvsJ27ZZ1/XXYekiZFn+pKpqTyIidDqdaFg4j9nZ2WuWZb3M5/NXDMOQut0uJAAYDodhd4xMJrN4fHy84XneAyHE5Wg0CmZmaVRaWlqibDZ7dWFhYWL0PJPJLNbr9Q++76fj8fjDlZWVXQAygD8bnFCtVrO1Wm3Xdd23uVxOA4BcLne90Wi87/f7Kdu2nzSbzedHR0cAQGMN5ufnf0Qikc+tVutOtVp95ThOwXXdtV6vl04mk49rtdrTv4P+1aVSKQbw7iQvFouqYRgbRMSyLPtCCLZt+9FpAYDl5eVL09PT2wD8MxsAwN7eXmdqaure5OTkDhFNWJb1ol6vPwt7J4w1AID9/f1mOp0up1Kp26VS6cz0MBIzQwgx9m4PDg4ah4eHO+vr60H4bjgcIggCBkCIxWJsmuabsHQR0Wj0oxCiT4qisKqqW47j3GXmbqVSIVmWw/4pRBQkEgnNdd0tIpom0zS/tFqtW6qqVpi51uv1QPTf/xQoimL2+/28aZprNDMz47Tb7WeDwaDkeV4sbIdhZsiyzJqm7SSTyfu/AT3u4KkuYflSAAAAAElFTkSuQmCC);
            background-size: 16px 16px;
            /* background-position: 0 0; */
		}

		#datiDinamici .checkbox.not-checked {
            background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAARCAYAAADUryzEAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsIAAA7CARUoSoAAAAAYdEVYdFNvZnR3YXJlAFBhaW50Lk5FVCA1LjEuOBtp6qgAAAC2ZVhJZklJKgAIAAAABQAaAQUAAQAAAEoAAAAbAQUAAQAAAFIAAAAoAQMAAQAAAAIAAAAxAQIAEAAAAFoAAABphwQAAQAAAGoAAAAAAAAA8nYBAOgDAADydgEA6AMAAFBhaW50Lk5FVCA1LjEuOAADAACQBwAEAAAAMDIzMAGgAwABAAAAAQAAAAWgBAABAAAAlAAAAAAAAAACAAEAAgAEAAAAUjk4AAIABwAEAAAAMDEwMAAAAADMbhZ8SlPoHAAAAZdJREFUOE+tkU2r00AUhs87k0zThHTaJOCmm0KhNSstQRfdCC5d+Yf6cwRd6UZcXD9wI4VuLgjNP6iLNoK1jU3MHDfeYsJt7+XiAy/MxzNn8Q4Nh8Ok1+tdSimZiI4BUNtfRQjBWuvfQRB8GAwGDxCG4UWWZU+11l+I6Acz03a7LZmZ6RqklKyUurff7x8HQfAenud9B3CZJMmzOI7z1WollsulybKMpJS1x4fDgbIsM0mSdNI0vbAs6z65rvur3W6/qJm3oNVqvfZ9nwUAIiJvNpuhKZ1iOp2iKAqUZUkCABdFgcVi0fROsl6viZkJAImrQ9u269YZ/u3mOOCu/JcBty7vOkRVVdXfn7gTIs/zkohQlmXz7iRVVR3XwrZtoZTiyWRSk87R6XQIABljiFzXrbTWL5vSTSilXkkpGY7jGNu2P43H4+f9fv/nfD6HUqrpHwFgwjD00jR9B+Ahoij6uNlsnnie9xXAt91uxzeUahzH6eV5/qjb7b6lOI7HURS98X1/C4ABmHMhIrYsywRB8Hk0GsV/AIvolUk7InpJAAAAAElFTkSuQmCC);
            background-size: 16px 16px;
            /* background-position: 0 0; */
		}

        
		#datiDinamici .checkbox-label>.EtichettaControllo {
			font-weight: normal;
		}
        

		#datiDinamici .tabellaDatiDinamici>tbody>tr>td:has(.checkbox),
		#datiDinamici .tabellaDatiDinamici>tbody>tr>td.checkbox-label		{
			padding-bottom: 4px;
		}
		/*
		#datiDinamici .bloccoMultiplo.panel {
			border: 0 !important;
			padding: 0 !important;
		}
        */
		#datiDinamici .bloccoMultiplo.panel:has(>.panel-body>table>tbody>tr>td:not(:empty)) {
			padding: 10px !important;
			border: 1px solid #cccccc !important;
		}
		
		#datiDinamici .righeMultiple.table  p{
			margin:2px 0 2px 0;
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
    }
}
