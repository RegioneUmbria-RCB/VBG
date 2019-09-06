<%@ Page Title="Dati istanza" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="DettaglioIstanzaV2.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.DettaglioIstanzaV2" %>

<%@ Register Src="VisuraCtrlV2.ascx" TagName="VisuraCtrlV2" TagPrefix="uc1" %>

<asp:Content ID="ContentHead" ContentPlaceHolderID="headPagina" runat="server">
    <style>
        .note-allegato{ font-style: italic }
    </style>
    <script type="text/javascript">
        $(function onReady() {

            $("#bnStampa").on("click", function Stampa(e) {
                e.preventDefault();
                $("header,nav,.alert,.bottoni").hide();
                window.print();
                $("header,nav,.alert,.bottoni").show();
            });
        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div>
        <asp:Literal runat="server" ID="ltrIntestazioneDettaglio" ></asp:Literal>
    </div>
	<uc1:VisuraCtrlV2 runat="server" ID="visuraCtrl" OnErroreRendering="VisuraCtrlV2_ErroreRendering" />
	<fieldset>
		<div class="bottoni">
			<a href="#" id="bnStampa" class="btn btn-primary">Stampa</a>
            <asp:Button runat="server" ID="cmdClose" Text="Chiudi" OnClick="cmdClose_Click" />
		</div>
	</fieldset>
</asp:Content>
