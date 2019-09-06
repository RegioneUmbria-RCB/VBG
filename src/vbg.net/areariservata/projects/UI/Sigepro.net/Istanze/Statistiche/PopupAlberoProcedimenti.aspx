<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="PopupAlberoProcedimenti.aspx.cs" Inherits="Sigepro.net.Istanze.Statistiche.PopupAlberoProcedimenti" Title="Ricerca interventi" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register TagPrefix="cc1" Namespace="SIGePro.Net.Controls" Assembly="Sigepro.net" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	

    <script type="text/javascript">
		function RitornaValore( pCodice , pDescrizione)
		{
			window.returnValue = { codice: pCodice ,descrizione: pDescrizione };
			window.close();
		}
    </script>
    
    <style type="text/css">
		A{   
			color: #696969;
			font-family: tahoma, arial, helvetica;
		}
    </style>

<div style="margin-left:10px">
	<cc1:alberoprocedimenti id="AlberoProcedimenti1" title="TIPOLOGIE INTERVENTO:" runat="server" AllowParentSelection="true" AllowTitles="false" ImagesRoot="~/Images/TreeView" OnProcedimentoSelezionato="AlberoProcedimenti1_ProcedimentoSelezionato">
	</cc1:alberoprocedimenti>
</div>
</asp:Content>