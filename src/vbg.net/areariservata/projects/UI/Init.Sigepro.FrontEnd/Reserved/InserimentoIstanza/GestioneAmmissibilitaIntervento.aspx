<%@ Page Title="Gestione ammissibilità intervento" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneAmmissibilitaIntervento.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAmmissibilitaIntervento" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <div class="alert alert-danger">
        <b>Attenzione!</b>
        <div><%=this.MessaggioErroreDomandaPerInterventoPresentata %></div>
    </div>
</asp:Content>
