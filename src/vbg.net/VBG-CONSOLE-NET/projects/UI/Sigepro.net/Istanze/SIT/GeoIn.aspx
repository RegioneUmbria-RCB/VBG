<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="GeoIn.aspx.cs" Inherits="Sigepro.net.Istanze.SIT.GeoIn" Title="Plug in cartografico"%>

<%@ Register Assembly="Init.Utils" Namespace="Init.Utils.Web.UI" TagPrefix="cc2" %>

<%@ Register Assembly="SIGePro.WebControls" Namespace="SIGePro.WebControls.UI" TagPrefix="cc1" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
<link rel="stylesheet" type="text/css" href='<%=ResolveClientUrl("~/Istanze/SIT/GeoIn.css")%>' />
<link rel="stylesheet" type="text/css" href="http://webmap.comune.intranet/css/index.css" />
<link rel="stylesheet" type="text/css" href="http://webmap.comune.intranet/js_wc/4.0.0/css/web_components.css" />
<link rel="stylesheet" type="shortcut icon" href="imgs/app_icon.gif" />

<!--[if lt IE 7]>
<style type="text/css">
#Panel_header { 
	width:expression(document.body.clientWidth-20);
}

#Panel_sx {
	height:expression(document.body.clientHeight-220); 
}

#Panel_centrale {
	height:expression(document.body.clientHeight-220); 
	width:expression(document.body.clientWidth-478);
}

#Panel_dx {
	height:expression(document.body.clientHeight-220); 
}


</style>
<![endif]-->



<script type="text/javascript" src='<%=ResolveClientUrl("~/js/microajax.minified.js")%>'></script>
<script type="text/javascript" src='<%=ResolveClientUrl("~/js/jquery.min.js")%>'></script>
<script type="text/javascript" src="GeoIn.js"></script>
		<!-- CURRENT_PAGE_LIB -->
		<script type="text/javascript" src="http://webmap.comune.intranet/js_wc/4.0.0/web_components.js"></script>
		<!--[if IE]><script type="text/javascript" src="http://webmap.comune.intranet/js_wc/4.0.0/src/excanvas.js"></script><![endif]-->
		
		<script type="text/javascript" src="http://webmap.comune.intranet/js/index.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/i_signaler.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/com_signaler.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/debug_signaler.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/js_signaler.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/searcher.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/tabula_map.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/measure_viewer.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/server_checker.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/db_persister.js"></script>
		<script type="text/javascript" src="http://webmap.comune.intranet/js/signaler_proxy.js"></script>
		<!-- CURRENT_PAGE_LIB -->

	<script type="text/javascript">
		///<reference path="GeoIn.js" />

		var plugin = null;

		$(function () {
			//Ricavo i parametri in querystring
			plugin = new GeoInPlugin({
				param1: '<%=Via_Foglio %>',
				param2: '<%=Civico_Particella %>',
				param3: '<%=Colore_Catasto %>',
				contesto: '<%=Contesto %>',
				software: '<%=Software %>',
				token: '<%=Token %>',
				codice: '<%=Codice %>',
				modalita: '<%=Modalita %>',
				baseUrl: '<%=BaseUrl %>',
				returnTo: '<%=Request.QueryString["returnTo"] %>',
				serviceUrl: '<%=ResolveClientUrl("~/Istanze/Sit/Handlers/GeoInService.asmx") %>',
				idComune: '<%=IdComune %>'
			});
		});
			/*
			_loaded = true;
			//Creo l'oggetto
			_gis_plugin = new tabula_map();
			//Inizializzo l'oggetto
			_gis_plugin.init("Panel_centrale");
			//Setto l'url del prox
			var urlProxy;

			$.ajax({
				type: "GET",
				url: "/url,
				data: "Token=" + token + "&software=" + software",
				dataType: "json"
				success: function(data) {
        
				}
			});

			microAjax("http://" + location.host + location.pathname.replace('GeoIn.aspx', 'Handlers/GeoInGestioneVert.ashx') + "?Token=" + token + "&software=" + software, function (res) {
				urlProxy = getElement(res, 4, 10);
				_gis_plugin.set_proxy_url(urlProxy);
			});
			//Ridimensiono il controllo
			_gis_plugin.manage_layout();
			//attach event handler
			attach_signaler();


			//Visualizzo i parametri di editing in base ai permessi dell'operatore
			microAjax("http://" + location.host + location.pathname.replace('GeoIn.aspx', 'Handlers/GeoInGetPermessiEditing.ashx') + "?Token=" + token + "&software=" + software + "&Modalita=" + modalita, function (res) {
				if (res.match("Error") == null)
					_gis_plugin.edit_visibility((res == "False") ? false : true);
				else {
					alert(res);
					return false;
				}
			});


			switch (param4) {
				case "Stradario":
					if (param3 == 'R')
						param2 = param2 + ' ' + param3;
					//Evidenzio la via
					//_gis_plugin.find_toponomastica(param1,searcher.STRICT_MODE);
					//Evidenzio il civico in corrispondnza della via
					_gis_plugin.find_via_civico(param1, param2, searcher.STRICT_MODE);
					break;
				case "Mappale":
					_gis_plugin.find_foglio_particella(param1, param2, searcher.STRICT_MODE);
					break;
			}
		*/
	</script>
	<div id="Panel_header">
            <fieldset>
			    <legend>Ricerca</legend>
                <cc2:LabeledLabel ID="lblViaFoglio" runat="server" class="labeledlabel"/>
                <cc2:LabeledLabel ID="lblCivicoParticella" runat="server" class="labeledlabel"/>
                <cc2:LabeledLabel ID="lblColoreCatasto" runat="server" class="labeledlabel"/>
			    <cc2:LabeledCheckBox ID="lblClearFiltro" runat="server" Descrizione="Elimina filtro"/>
			</fieldset>
        </div>
        <div id="Panel_sx">
			<fieldset runat="server" id="Fldset_Layer">
			    <legend>Layer</legend>
			    <cc2:LabeledCheckBox ID="lblCART" runat="server" Descrizione="Layer Cartografia" Item-Checked="True"/>
			    <cc2:LabeledCheckBox ID="lblTOPO" runat="server" Descrizione="Layer Toponomastica" Item-Checked="True"/>
			</fieldset>
        </div>
        <div id="Panel_centrale">
        </div>
        <div id="Panel_dx">
            <fieldset>
			    <legend>Stato</legend>
                <cc2:LabeledCheckBox ID="lblAttive" runat="server" Descrizione="Attive"/>
                <cc2:LabeledCheckBox ID="lblCessate" runat="server" Descrizione="Cessate"/>
			</fieldset>
        </div>

</asp:Content>

