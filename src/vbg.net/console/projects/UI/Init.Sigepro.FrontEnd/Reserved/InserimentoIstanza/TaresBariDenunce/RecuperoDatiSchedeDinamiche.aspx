<%@ Page Title="Recupero dati schede dinamiche" ValidateRequest="false" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="RecuperoDatiSchedeDinamiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce.RecuperoDatiSchedeDinamiche" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <asp:TextBox TextMode="MultiLine" ReadOnly="true" style="width:900px; height: 600px" runat="server" ID="ltrOutput"></asp:TextBox>
</asp:Content>
