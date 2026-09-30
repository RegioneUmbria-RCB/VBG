<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="FileDownload.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.FileDownload" %>
<asp:HyperLink runat="server" 
                ID="hlFileDownload" 
                NavigateUrl='<%# UrlDownload(DataBinder.Eval(DataSource,"CodiceOggetto")) %>' 
                Text='<%#DataBinder.Eval(DataSource,"NomeFile") %>' 
                Target="_blank"/>
