<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="GestioneEndoV2.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoV2" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="uc1" TagName="GrigliaEndo" Src="~/Reserved/InserimentoIstanza/GestioneEndoV2GrigliaEndo.ascx" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content runat="server" ContentPlaceHolderID="head">

    <style>
		/*.gruppoEndo {font-size: 11px;text-transform: uppercase}*/
		#alberoEndo ul
		{
			margin: 0px;
			padding: 0px;
		}
		#alberoEndo li
		{
			margin-left: 20px;
			list-style-type: none;
			margin-bottom: 5px;
		}
		#alberoEndo > ul > li
		{
			margin-left: 5px;
            padding-bottom: 16px;
		}
		#alberoEndo > ul > li > ul
		{
			/*margin-top: 5px;*/
		}
		#alberoEndo  ul
		{
			margin-top: 5px;
		}
		#alberoEndo li > input[type=checkbox]
		{
		}

        #alberoEndo span {
            font-weight: bold;
        }

        #alberoEndo .fa-question-circle {
            cursor: pointer;
            color: cornflowerblue;
        }

        .etichetta {
            font-weight: bold;
            padding-bottom: 8px;
            padding-top: 8px;
        }

        .modal-dialog {
            width: 60%;
        }

        #accordion h3 {
            font-size: 18px;
            background-image: none;
            background-color: #f5f5f5;
            border: 1px solid #ddd;
            border-top-left-radius: 3px;
            border-top-right-radius: 3px;

        }

        #accordion .ui-widget-content {
            border: 1px solid #ddd;
        }

	</style>

	<script type="text/javascript">
	    require(['app/gestione-endo-v2', 'jquery', 'jquery.ui'], function (GestioneEndoV2, $) {

	        function inizializzaContenutoAccordion(elName) {
	            $(elName + ' #accordion').accordion({ header: "h3", heightStyle: 'content' });
	            //$(elName + ' #accordion table TR:even').addClass('AlternatingItemStyle');
	            //$(elName + ' #accordion table TR:odd').addClass('ItemStyle');
	        }

	        $(function () {

                var modal = $("#<%=bmDettagliEndo.ClientID%>");


                $('.albero-endo').alberoEndoprocedimenti();


                function onDettagliEndoLoaded() {

                }

                function mostraDettagliEndo(sender, id) {
                    var url = '<%= ResolveClientUrl("~/Public/MostraDettagliEndo.aspx") + "?idComune=" + IdComune + "&software=" + Software + "&_ts=" + DateTime.Now.Millisecond + "&Id="%>' + id ;

                    sender.removeClass('fa-question-circle').addClass('fa-spinner').addClass('roteante');

                    modal.find(".modal-body>div").load(url, null, function (responseText, textStatus, XMLHttpRequest) {
                        sender.removeClass('roteante')
                            .removeClass('fa-spinner')
                            .addClass('fa-question-circle');

                        $(this).find('#accordion').accordion({ header: "h3", heightStyle: 'content' });

                        modal.modal('show');
                    });
                }


	            $('.fa-question-circle').click(function () {
	                var idEndo = $(this).attr('idEndo');

                    mostraDettagliEndo($(this), idEndo);
	            });
	        });
            
	    });
	</script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	

    <div class="gestione-endo">
		<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0" >
			<asp:View runat="server" ID="listaEndoView">
				<asp:Panel runat="server" ID="pnlEndoPrincipale" CssClass="pannello-endo">
					<fieldset class="blocco aperto">
					<legend>
						<asp:Literal runat="server" ID="ltrTitoloEndoPrincipale" Text="Procedimento principale" />
					</legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaEndoPrincipale" />
					</fieldset>
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlEndoAttivati" CssClass="pannello-endo">
					<fieldset class="blocco aperto">
					<legend>
						<asp:Literal runat="server" ID="ltrTitoloEndoAttivati" Text="Procedimenti attivati" />
					</legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaEndoAttivati" />
					</fieldset>
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlEndoAttivabili" CssClass="pannello-endo">
					<fieldset class="blocco aperto">
					<legend>
							<asp:Literal runat="server" ID="ltrTitoloEndoAttivabili" Text="Procedimenti attivabili" /></legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaEndoAttivabili" />
					</fieldset>
				</asp:Panel>
				<asp:Panel runat="server" ID="pnlAltriEndo" CssClass="pannello-endo">
					<fieldset class="blocco aperto">
					<legend>
							<asp:Literal runat="server" ID="ltrTitoloAltriEndo" Text="Altri endoprocedimenti attivati"></asp:Literal>
						</legend>
						<uc1:GrigliaEndo runat="server" ID="grigliaAltriEndo" ModificaProcedimentiProposti="false" />
					</fieldset>


				</asp:Panel>

				<div class="bottoni">
					<asp:Button runat="server" ID="cmdAttivaAltriEndo" Text="Altri endoprocedimenti" OnClick="cmdAttivaAltriEndo_click" />
				</div>
			</asp:View>

			<asp:View runat="server" ID="altriEndoView">

			<fieldset class="blocco aperto">
			<legend>
					<asp:Literal runat="server" ID="ltrTitoloAltriEndoAttivabili" Text="Altri endoprocedimenti attivabili"></asp:Literal>
				</legend>
				<uc1:GrigliaEndo runat="server" ID="grigliaAltriEndoAttivabili" />
			</fieldset>
			
			<div class="bottoni">
				<asp:Button runat="server" ID="cmdTornaAllaLista" Text="Torna alla lista degli endoprocedimenti" OnClick="cmdTornaAllaLista_click" />
			</div>
			</asp:View>
		</asp:MultiView>
    </div>

    <ar:BootstrapModal runat="server" ID="bmDettagliEndo"  ShowOkButton="false" Title="Dettagli endoprocedimento"></ar:BootstrapModal>
    
</asp:Content>
