<%@ Page Title="" Language="C#" MasterPageFile="~/Sir/SirMaster.Master" AutoEventWireup="true" CodeBehind="default.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Sir._defaultSir" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="phContenuto" runat="server">

<div class="sir-sezione ">
	<div class="sir-benvenuto">
        <div class="testo">
            Benvenuti nello 
            <span class="upper">S</span>portello 
            <span class="upper">U</span>nico per le 
            <span class="upper">A</span>ttività 
            <span class="upper">P</span>roduttive e per l’
            <span class="upper">E</span>dilizia
            dove Cittadini e Imprese possono informarsi ed avviare le pratiche in rete,
            direttamente da casa o dall’ufficio, senza andare in Comune
        </div>
                    
        <div class="sir-benvenuto-links">
            <a href="<%=ResolveClientUrl(UrlRicercaInterventi) %>" class="sir-box-info">
                <img src='<%= ResolveClientUrl("~/sir/images/icon-info.png")%>' /><br />
                <span class="titolo">Informati</span><br />
                sui servizi del SUAPE
            </a>
                        
            <a href="<%=ResolveClientUrl(UrlAreaRiservata) %>" class="sir-box-suape">
				<img src='<%= ResolveClientUrl("~/sir/images/icon-suape.png")%>' class="icon-suape"/><br />
                            
                <span class="titolo">Accedi</span><br />
                ai servizi del SUAPE<br />
            </a>
        </div>
        <div class="clear"></div>
    </div>
</div>
</asp:Content>
