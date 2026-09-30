<%@ Page Title="Testo libero" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="testo-libero.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.testo_libero" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <div class="panel panel-default">
        <div class="panel-body">
            <%=this.Testo%>
        </div>
    </div>
</asp:Content>
