<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="G713ProtocolloStampa.aspx.cs" Inherits="Sigepro.net.Istanze.StampaProtocollo.G713ProtocolloStampa" Title="Stampa etichette" %>

<%@ Register Assembly="SIGePro.WebControls" Namespace="SIGePro.WebControls.UI" TagPrefix="cc1" %>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
<script type="text/javascript">
    var g_margineSuperiore = 0.1; // Impostare questo valore per effettuare aggiustamenti sul margine superiore
    
    var g_margineSinistro  = 0.1; // Impostare questo valore per effettuare aggiustamenti sul margine sinistro
    
    var g_calcolaCoordinateDallaBaseDelTesto = true;    // Impostare a true se le coordinate di stampa sono espresse 
                                                        // a partire dalla base del testo altrimenti le coordinate sono
                                                        // considerate al margine in alto a sinistra 


    // Funzione d'appoggio utilizzata per semplificare il posizionamento e la stampa del testo
	function StampaAxy( x , y , testo )
	{
		var controlloStampa = document.getElementById("controlloStampa");
	
	    controlloStampa.XCorrente = x - g_margineSinistro;
	    controlloStampa.YCorrente = y - g_margineSuperiore - (g_calcolaCoordinateDallaBaseDelTesto ? controlloStampa.GetAltezzaTesto(testo) : 0);
	    controlloStampa.Stampa( testo );
	}
	
	function StampaConEtichetta( x , y , etichetta , valore , larghezzaBoxValore)
	{
		
		var oldGrassetto = controlloStampa.Grassetto;
	
		controlloStampa.Grassetto = true;
		
		var larghezzaEtichetta = controlloStampa.GetLarghezzaTesto( etichetta );
		
		StampaAxy( x  , y , etichetta );
		
		controlloStampa.Grassetto = false;
		
		var larghezzaValore = controlloStampa.GetLarghezzaTesto( valore );
		
		StampaAxy( x + larghezzaBoxValore - larghezzaValore, y ,valore );
		
		controlloStampa.Grassetto = oldGrassetto;
	}
	
	function StampaCopia()
	{
		var controlloStampa = document.getElementById("controlloStampa");
	
		if (!controlloStampa.ImpostaStampante( StampanteSelezionata() ) )
        {
            alert("Impossibile effettuare la stampa sulla stampante selezionata (" + StampanteSelezionata() + ")");
            return;
        }
        
        var defaultFont = "Times New Roman";
        var defaultFontSize = 9;
        
        var topBase  = g_margineSuperiore + 0.1 + 0.2;
        var leftBase = g_margineSinistro + 0.1 + 0.4;
	
		// Intestazione (Comune di Pistoia)
		controlloStampa.NomeFont = defaultFont;
		controlloStampa.DimensioneFont = defaultFontSize+1;
		
		var testo = "Comune di Pistoia";
	
		var altezzaRiga   = controlloStampa.GetAltezzaTesto( testo );
		
		var top  = topBase + altezzaRiga;
		var left = leftBase;
		
		StampaAxy( left , top , testo );
		
		// Barcode		
		controlloStampa.NomeFont = "IDAutomationC128M";
		controlloStampa.DimensioneFont = 18;
	
		var barcode = '<%= GetBarcode().Replace("\\","\\\\").Replace("'","\\'") %>';
		
		altezzaRiga   = controlloStampa.GetAltezzaTesto( barcode );
		
		top  = topBase + 0.4 + altezzaRiga;
		left = leftBase;
		
		StampaAxy( left , top , barcode );
		
		// Software
		controlloStampa.NomeFont = defaultFont;
		controlloStampa.DimensioneFont = defaultFontSize;
		
		//testo = 'Ufficio <%=GetNomeSoftware() %>';
		testo = 'Ufficio Protocollo';
	
		altezzaRiga   = controlloStampa.GetAltezzaTesto( testo );
		
		top  = topBase + 1.4 + altezzaRiga;
		left = leftBase;
		
		StampaAxy( left , top , testo );
		
		// Numero/Data protocollo
		testo = 'Nr.<%=NumeroProtocollo %> Data <%=DataProtocollo %>';
	
		altezzaRiga  = controlloStampa.GetAltezzaTesto( testo );
		
		top  = topBase + 1.8 + altezzaRiga;
		left = leftBase;
		
		StampaAxy( left , top , testo );

		// tit.10.06.21 Arrivo
		testo = 'Tit.10.06.21 Arrivo';
	
		altezzaRiga  = controlloStampa.GetAltezzaTesto( testo );
		
		top  = topBase + 2.2 + altezzaRiga;
		left = leftBase;
		
		StampaAxy( left , top , testo );
		
		
		// Pratica n. xxxx del xxxxx
		controlloStampa.NomeFont = defaultFont;
		controlloStampa.DimensioneFont = defaultFontSize;
		
		testo = 'Prat.Ed.<%=GetNumeroPratica() %> Del <%=GetDataPratica() %>';
	
		altezzaRiga  = controlloStampa.GetAltezzaTesto( testo );
		var larghezzaRiga  = controlloStampa.GetLarghezzaTesto( testo );
		
		top  = topBase  + 2.7 + altezzaRiga;
		left = leftBase - 0.4 + ((5.1 - larghezzaRiga)/2);
		
		StampaAxy( left , top , testo );
	
	
	
	
		DisegnaBoxContenitore();
		
		controlloStampa.FineDocumento();
	}
	
	function DisegnaBoxContenitore()
	{
		var controlloStampa = document.getElementById("controlloStampa");
	
		var box = new Object();
		box.x = 0 + g_margineSuperiore;
		box.y = 0 + g_margineSinistro;
		box.width  = 5.1 + g_margineSuperiore;
		box.height = 3.4 + g_margineSinistro;
		
		var lineaSeparatore = new Object();
		lineaSeparatore.x = 0 + g_margineSuperiore;
		lineaSeparatore.y = 2.8 + g_margineSinistro;
		lineaSeparatore.x1 = 5.1 + g_margineSuperiore;
		lineaSeparatore.y1 = 2.8 + g_margineSinistro;
		
		controlloStampa.DisegnaLineaAbs( lineaSeparatore.x , lineaSeparatore.y , lineaSeparatore.x1 , lineaSeparatore.y1 , 0);
		controlloStampa.DisegnaBox( box.x , box.y , box.width , box.height , 0 );
		
		
	}

	function Stampa()
	{
		var ctrlNumEtichette = document.getElementById('<%= litbNumEtichette.InnerClientId %>');
		
		if(!ctrlNumEtichette) return;
			
		if(isNaN(ctrlNumEtichette.value))
		{
			alert("Numero di copie non valido");
			return;
		}
		
		for( var i = 0 ; i < parseInt(ctrlNumEtichette.value) ; i++ )
			StampaCopia();
	
		alert("Stampa effettuata");
	
		return false;		
	}
	

	function StampanteSelezionata()
	{
		var comboStampanti = document.getElementById('comboStampanti');
	
	    for( var i = 0 ; i < comboStampanti.options.length ; i++ )
	    {
            if ( comboStampanti.options[i].selected )
                return comboStampanti.options[i].text;
	    }
	    
	    return "";
	}
	
	function AggiornaListaStampanti()
	{
		var comboStampanti = document.getElementById('comboStampanti');
		var controlloStampa = document.getElementById("controlloStampa");
		if (controlloStampa.ListaStampanti)
        {
            var stampanti = controlloStampa.ListaStampanti.split("|");

            while(comboStampanti.options.length > 0)
                comboStampanti.options[ comboStampanti.options.length-1 ] = null;

            for (var i = 0 ; i < stampanti.length ; i++ )
                comboStampanti.options[comboStampanti.options.length] = new Option( stampanti[i] );
        }
        else
         {
             alert("Impossibile ottenere la lista di stampanti->" + controlloStampa.ListaStampanti);
         }
	}

</script>


<object id="controlloStampa" classid="CLSID:B43C02DA-BA07-4C33-ABBD-4E7B0EA3BDBC" codebase='<%= CabUrl %>'   height="1" width="1" viewastext></object>
<%--	<object id="controlloStampa" classid="CLSID:B43C02DA-BA07-4C33-ABBD-4E7B0EA3BDBC" codebase="ControlloStampa.CAB#version=1,0,0,6"  height="1" width="1" viewastext></object>--%>

	<div id="divVerifica" style="text-align:center;width:100%">
		Verifica del componente activex in corso...
	</div>
	<div id="divContenuto" style="display:none">
	
	<fieldset>
		<div>
			<label for="comboStampanti">Stampante</label>
			<select id="comboStampanti"></select>
		</div>

		<cc2:LabeledIntTextBox ID="litbNumEtichette" Descrizione="N° Etichette" Item-Columns="5" runat="server" Item-Text="1" />
		
		<div class="Bottoni">
			<cc1:SigeproButton ID="cmdStampa" runat="server" OnClientClick="return Stampa();" IdRisorsa="STAMPA" />
			<cc1:SigeproButton ID="cmdChiudi" runat="server" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
		</div>
	</fieldset>
	</div>



<script type="text/javascript">

function InizializzaListaStampanti()
    {
		try
		{
			var controlloStampa = document.getElementById("controlloStampa");
			//alert(controlloStampa);
			if(controlloStampa)
			{
				var divVer  = document.getElementById("divVerifica");
				var divCont = document.getElementById("divContenuto");
			
				divVer.style.display="none";
				divCont.style.display="";
				
			    if (controlloStampa.ListaStampanti)
			    {
			        AggiornaListaStampanti();
			        
			        return;
			    }
			}
        }catch(ex){}
        
        setTimeout( "InizializzaListaStampanti()",1000);
    }
	 
	 InizializzaListaStampanti();
</script>
</asp:Content>