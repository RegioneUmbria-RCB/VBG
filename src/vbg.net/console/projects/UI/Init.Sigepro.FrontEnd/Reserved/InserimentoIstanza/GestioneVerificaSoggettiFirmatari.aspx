<%@ Page Title="Verifica soggetti firmatari" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneVerificaSoggettiFirmatari.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneVerificaSoggettiFirmatari" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <div class="inputForm validazione-soggetti-firmatari">
        <div class='alert <%=this.Bloccante ? "alert-error" : "alert-warning" %>'>
            <ul class="icone">
                <%foreach (var errore in this.EsitoVerifica.SoggettiNonTrovati) {%>

                <li>
                    Il documento <b>"<%=errore.NomeDocumento%>"</b> deve essere firmato da almeno uno dei seguenti soggetti:
                    <ul>
                        <%foreach (var soggetto in errore.TipiSoggettoRichiesti)
                          {%>
                        <li><%= soggetto%></li>
                        <%}%>
                    </ul>
                    Ma nessuno di tali soggetti è stato trovato all'interno della pratica
                </li>

                <%}%>

                <%foreach (var errore in this.EsitoVerifica.DocumentiNonPresenti) {%>

                <li>
                    Il documento <b>"<%=errore.NomeDocumento%>"</b> non è stato caricato
                </li>

                <%}%>

                <%foreach (var errore in this.EsitoVerifica.VerificheFallite) {%>

                <li>
                    Il documento <b>"<%=errore.NomeDocumento%>"</b> 
                    <i>
                        (<%=errore.NomeFile %>)
                    </i>
                    non risulta essere firmato da almeno uno dei seguenti soggetti:

                    <ul>
                        <%foreach (var soggetto in errore.FirmatariRichiestiPresenti)
                          {%>
                        <li><%= soggetto%></li>
                        <%}%>
                    </ul>
                </li>
                <%}%>
            </ul>
        </div>
    </div>
</asp:Content>
